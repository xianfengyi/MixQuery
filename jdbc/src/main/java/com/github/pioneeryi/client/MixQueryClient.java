package com.github.pioneeryi.client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.pioneeryi.MixqConnection;
import com.github.pioneeryi.MixqMeta;
import com.github.pioneeryi.constants.JdbcProperty;
import com.github.pioneeryi.exception.MixqJdbcException;
import com.github.pioneeryi.model.MixqConnectionInfo;
import com.github.pioneeryi.util.HttpUtil;
import com.github.pioneeryi.util.JsonUtil;
import com.google.common.base.Strings;
import org.apache.calcite.avatica.AvaticaParameter;
import org.apache.calcite.avatica.MetaImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

public class MixQueryClient implements IRemoteClient {

    private static final Logger logger = LoggerFactory.getLogger(MixQueryClient.class);

    //http socket超时时间
    private static final int HTTP_TIMEOUT = 3 * 60 * 1000;
    //同步类查询请求 http socket超时时间
    private static final int SYNC_HTTP_TIMEOUT = 15 * 60 * 1000;

    private MixqConnection connection;

    private MixqConnectionInfo connInfo;

    private String getBaseUrl() {
        return "http://" + connection.getBaseUrl();
    }

    @Override
    public void connect() throws MixqJdbcException {

    }

    @Override
    public MixqMeta.MixMetaProject retrieveMetaData(String project) throws MixqJdbcException {
        return null;
    }

    @Override
    public QueryResult executeQuery(String sql, List<AvaticaParameter> params, List<Object> paramValues) throws MixqJdbcException {
        return null;
    }

    @Override
    public QueryResult executeQuery(String sql, List<AvaticaParameter> params, List<Object> paramValues, int timeoutS) throws MixqJdbcException {
        return null;
    }

    @Override
    public void close() throws MixqJdbcException {

    }

    @Override
    public void close(int statementId) throws MixqJdbcException {

    }

    @Override
    public List<MetaImpl.MetaCatalog> getCatalogs() throws MixqJdbcException {
        String url = HttpUtil.buildUrl(getBaseUrl() + "/tedi-sql/jdbc/catalogs", buildRequestParams());

        List<String> result = postRequest(url, null, SYNC_HTTP_TIMEOUT, new TypeReference<List<String>>() {
        });

        //转换为avatica定义的类型
        List<MetaImpl.MetaCatalog> metaCatalogs = result.stream().map(MetaImpl.MetaCatalog::new).collect(Collectors.toList());

        return metaCatalogs;
    }

    private Map<String, String> buildRequestParams() {
        Map<String, String> clientProps = new HashMap<>();
        Properties connProps = connInfo.getConnectionProperties();

        if (connProps.containsKey(JdbcProperty.ENGINE_ID)) {
            clientProps.put(JdbcProperty.ENGINE_ID, connProps.getProperty(JdbcProperty.ENGINE_ID));
        }

        if (connProps.containsKey(JdbcProperty.ENGINE_TYPE)) {
            clientProps.put(JdbcProperty.ENGINE_TYPE, connProps.getProperty(JdbcProperty.ENGINE_TYPE));
        }

        if (!Strings.isNullOrEmpty(connInfo.getBaseDb())) {
            clientProps.put("project", connInfo.getBaseDb());
        }

        if (connProps.containsKey(JdbcProperty.CATALOG)) {
            clientProps.put(JdbcProperty.CATALOG, connProps.getProperty(JdbcProperty.CATALOG));
        }

        if (connProps.containsKey(JdbcProperty.BIZ_ID)) {
            clientProps.put(JdbcProperty.BIZ_ID, connProps.getProperty(JdbcProperty.BIZ_ID));
        }

        if (connProps.containsKey(JdbcProperty.DATA_TYPE)) {
            clientProps.put(JdbcProperty.DATA_TYPE, connProps.getProperty(JdbcProperty.DATA_TYPE));
        }

        return clientProps;
    }

    private <T> T postRequest(String url, Object requestBody, int socketTimeoutMs, TypeReference<T> valueTypeRef)
            throws MixqJdbcException {
        return null;
    }

    @Override
    public List<MetaImpl.MetaSchema> getSchemas(String catalog, String schemaPattern) throws MixqJdbcException {
        return null;
    }

    @Override
    public List<MetaImpl.MetaTable> getTables(String catalog, String schemaPattern, String tableNamePattern, List<String> typeList) throws MixqJdbcException {
        return null;
    }

    @Override
    public List<MetaImpl.MetaColumn> getColumns(String catalog, String schemaPattern, String tableNamePattern, String columnNamePattern) throws MixqJdbcException {
        return null;
    }

    @Override
    public List<MetaImpl.MetaTableType> getTableTypes() throws MixqJdbcException {
        return null;
    }
}
