package com.pioneeryi.mixquery.jdbc.model;

import com.pioneeryi.mixquery.jdbc.Driver;
import com.pioneeryi.mixquery.jdbc.util.HttpUtil;
import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;

import java.util.Map;
import java.util.Properties;

public class MixqConnectionInfo {

    private String url;

    private String baseDb;

    private String baseUrl;

    private Properties properties;

    /**
     * 通过jdbc Url和属性，构建JdbcConnectionInfo对象
     * <p>
     * 例子,若用户在代码执行的是以下语句 Properties info = new Properties(); info.setProperty("user", ""user");
     * info.setProperty("password", "password");
     * Connection conn = driver.connect("jdbc:mixquery://xxx.xxx.com/projectName", info);
     * <p>
     * 经解析后 构建的JdbcConnectionInfo对象属性为 url = jdbc:tedi://xxx.xxx.com/default
     * baseUrl = xxx.xxx.com ,baseDb = default
     * connectionProperties = {"user":"user","password","password"}
     */
    public MixqConnectionInfo(String url, Properties properties) {
        this.url = url;

        String odbcUrl = url;
        //去掉jdbc:tedi:
        odbcUrl = odbcUrl.replaceAll(Driver.CONNECT_STRING_PREFIX, "");

        this.properties = properties;
        Map<String, String> urlParams = HttpUtil.getUrlParams(odbcUrl);
        urlParams.forEach((k, v) -> {
            properties.put(k, v);
        });

        //去掉参数
        String pureOdbcUrl = odbcUrl;
        if (pureOdbcUrl.contains("?")) {
            pureOdbcUrl = pureOdbcUrl.substring(0, pureOdbcUrl.indexOf("?"));
        }

        String[] temps = Iterables.toArray(Splitter.on("/").split(pureOdbcUrl), String.class);
        if (temps.length == 1) {
            this.baseUrl = pureOdbcUrl;
        } else {
            this.baseDb = temps[temps.length - 1];
            this.baseUrl = pureOdbcUrl.substring(0, pureOdbcUrl.lastIndexOf(baseDb) - 1);

        }
    }

    public String getUrl() {
        return url;
    }

    public String getBaseDb() {
        return baseDb;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public Properties getConnProperties() {
        return properties;
    }

}
