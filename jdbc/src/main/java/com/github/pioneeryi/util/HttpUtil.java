package com.github.pioneeryi.util;

import com.github.pioneeryi.exception.MixqJdbcException;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.ServiceUnavailableRetryStrategy;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

public class HttpUtil {

    private static final Logger logger = LoggerFactory.getLogger(HttpUtil.class);

    private static final int MAX_RETIES = 3;

    public static <T> T post(String url, Object requestBody, int socketTimeoutMs, Class<T> responseClazz) {
        try {
            //构造http请求
            HttpPost post = new HttpPost(url);
            String jsonBody = requestBody == null ? "{}" : JsonUtil.objectToJsonStr(requestBody);
            StringEntity requestEntity = new StringEntity(jsonBody, ContentType.create("application/json", "UTF-8"));
            post.setEntity(requestEntity);
            //发送http请求
            return requestHttp(post, socketTimeoutMs, responseClazz);
        } catch (Exception e) {
            logger.error("Send http request with post method fail.url={},body={}", url, requestBody, e);
            throw new MixqJdbcException("Send http request fail.url:" + url, e);
        }
    }

    public static <T> T get(String url, String username, String password, int socketTimeoutMs,
                            Class<T> responseClazz) {
        try {
            //构造http请求
            HttpGet get = new HttpGet(url);

            //发送http请求
            return requestHttp(get, socketTimeoutMs, responseClazz);
        } catch (Exception e) {
            logger.error("Send http request with get method fail.url={}", url, e);
            throw new MixqJdbcException("Send http request fail.url:" + url, e);
        }
    }

    /**
     * 拼接url参数
     *
     * @param url
     * @param urlParams 参数列表
     * @return
     */
    public static String buildUrl(String url, Map<String, String> urlParams) {
        try {
            URIBuilder builder = new URIBuilder(url);
            urlParams.forEach((k, v) -> builder.setParameter(k, v));
            return builder.toString();
        } catch (URISyntaxException e) {
            logger.error("Error occur when build url.baseUrl={},params={}", url, urlParams, e);
            throw new MixqJdbcException("Build url fail.baseUrl: " + url + ", params: " + urlParams, e);
        }
    }

    /**
     * 提取url参数.
     *
     * @param url
     * @return 参数 MAP
     */
    public static Map<String, String> getUrlParams(String url) {
        try {
            Map<String, String> params = new HashMap<>();
            String[] urlParts = url.split("\\?");
            if (urlParts.length > 1) {
                String query = urlParts[1];
                for (String param : query.split("&")) {
                    String[] pair = param.split("=");
                    String key = URLDecoder.decode(pair[0], "UTF-8");
                    String value = "";
                    if (pair.length > 1) {
                        value = URLDecoder.decode(pair[1], "UTF-8");
                    }
                    params.put(key, value);
                }
            }

            return params;
        } catch (UnsupportedEncodingException ex) {
            logger.error("Error occur when decode url param.url={}", url);
            throw new MixqJdbcException("Get url params fail.url: " + url, ex);
        }

    }

    /**
     * 发送http请求
     *
     * @param requestBase     http请求
     * @param socketTimeoutMs http socket timeout,毫秒单位
     * @param responseClazz   响应体类型
     * @param <T>
     * @return
     * @throws IOException
     */
    private static <T> T requestHttp(HttpRequestBase requestBase, int socketTimeoutMs, Class<T> responseClazz)
            throws IOException {
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse httpResponse = null;
        try {
            RequestConfig conf = RequestConfig.custom()
                    .setSocketTimeout(60 * 1000)
                    .setSocketTimeout(socketTimeoutMs)
                    .build();
            httpClient = buildHttpClient(conf);
            httpResponse = httpClient.execute(requestBase);

            if (httpResponse.getStatusLine().getStatusCode() != 200
                    && httpResponse.getStatusLine().getStatusCode() != 201) {
                throw new MixqJdbcException(
                        requestBase.getMethod() + " failed, error code " + httpResponse.getStatusLine().getStatusCode()
                                + " and response: " + EntityUtils.toString(httpResponse.getEntity()));
            }

            return JsonUtil.jsonStreamToObject(httpResponse.getEntity().getContent(), responseClazz);
        } finally {
            HttpClientUtils.closeQuietly(httpClient);
            HttpClientUtils.closeQuietly(httpResponse);
        }
    }

    private static CloseableHttpClient buildHttpClient(RequestConfig conf) {

        CloseableHttpClient httpClient = HttpClientBuilder.create().setDefaultRequestConfig(conf)
                .setRetryHandler(new HttpRequestRetryHandler() {
                    @Override
                    public boolean retryRequest(IOException exception, int executionCount,
                                                HttpContext context) {
                        return executionCount <= MAX_RETIES;
                    }
                })
                .setServiceUnavailableRetryStrategy(new ServiceUnavailableRetryStrategy() {
                    int waitPeriod = 500;

                    @Override
                    public boolean retryRequest(HttpResponse response, int executionCount,
                                                HttpContext context) {
                        waitPeriod *= 2;
                        return executionCount <= MAX_RETIES
                                && response.getStatusLine().getStatusCode() >= 500
                                && response.getStatusLine().getStatusCode() < 600;//5XX错误码
                    }

                    @Override
                    public long getRetryInterval() {
                        return waitPeriod;
                    }
                })
                .build();
        return httpClient;
    }
}
