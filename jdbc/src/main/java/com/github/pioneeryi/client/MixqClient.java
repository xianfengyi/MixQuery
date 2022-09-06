package com.github.pioneeryi.client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.pioneeryi.constants.JdbcProperty;
import com.github.pioneeryi.exception.MixqJdbcException;
import com.github.pioneeryi.model.MixqConnectionInfo;
import com.github.pioneeryi.model.Response;
import com.github.pioneeryi.model.columnmeta.ColumnMetaRequest;
import com.github.pioneeryi.model.columnmeta.ColumnMetaResult;
import com.github.pioneeryi.model.execute.ColumnMeta;
import com.github.pioneeryi.model.execute.ExecuteRequest;
import com.github.pioneeryi.model.execute.ExecuteResult;
import com.github.pioneeryi.model.execute.StatementParameter;
import com.github.pioneeryi.model.schemameta.SchemaMetaRequest;
import com.github.pioneeryi.model.schemameta.SchemaMetaResult;
import com.github.pioneeryi.model.tablemeta.TableMetaRequest;
import com.github.pioneeryi.model.tablemeta.TableMetaResult;
import com.github.pioneeryi.util.HttpUtil;
import com.github.pioneeryi.util.JdbcTypeUtil;
import com.github.pioneeryi.util.JsonUtil;
import com.google.common.base.Strings;
import org.apache.calcite.avatica.AvaticaParameter;
import org.apache.calcite.avatica.ColumnMetaData;
import org.apache.calcite.avatica.MetaImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

public class MixqClient implements IRemoteClient {

    private static final Logger logger = LoggerFactory.getLogger(MixqClient.class);

    // http socket 超时时间
    private static final int HTTP_TIMEOUT = 3 * 60 * 1000;

    // 同步类查询请求 http socket 超时时间
    private static final int SYNC_HTTP_TIMEOUT = 15 * 60 * 1000;

    private MixqConnectionInfo connInfo;

    public MixqClient(MixqConnectionInfo connInfo) {
        this.connInfo = connInfo;
    }

    @Override
    public void connect() throws MixqJdbcException {
        // 暂时不做校验
    }

    @Override
    public QueryResult executeQuery(String sql, List<AvaticaParameter> params, List<Object> paramValues, int timeoutS)
            throws MixqJdbcException {
        //调用 MixQuery server Api 获取查询结果
        ExecuteResult executeResult = doExecuteQuery(sql, convertParameters(paramValues), timeoutS);

        //列元数据信息转换为 avatica 的类型
        List<ColumnMetaData> metas = convertColumnMeta(executeResult.getColumnMetas());

        //根据元数据信息把结果列表转换为java对象列表
        List<Object> data = convertResultData(executeResult.getResults(), metas);

        return new QueryResult(metas, data);
    }

    private List<StatementParameter> convertParameters(List<Object> paramValues) {
        if (paramValues == null) {
            return null;
        }
        return paramValues.stream().map(StatementParameter::new).collect(Collectors.toList());
    }

    private ExecuteResult doExecuteQuery(String sql, List<StatementParameter> params, int timeoutS)
            throws MixqJdbcException {
        String url = getBaseUrl() + "/tedi-sql/jdbc/query";

        ExecuteRequest requestBody = new ExecuteRequest(sql, params, timeoutS, isSync(), getDialect());

        //根据是否是同步请求设置超时时间
        int socketTimeout = isSync() ? SYNC_HTTP_TIMEOUT : HTTP_TIMEOUT;

        //请求api提交sql
        ExecuteResult executeResult = postRequest(url, requestBody, socketTimeout, ExecuteResult.class);

        return executeResult;
    }

    private boolean isSync() {
        return Boolean.parseBoolean(connInfo.getConnProperties().getProperty(JdbcProperty.SYNC_EXECUTION));
    }

    private String getDialect() {
        return connInfo.getConnProperties().getProperty(JdbcProperty.DIALECT);
    }

    private List<ColumnMetaData> convertColumnMeta(List<ColumnMeta> columnMetas) {
        List<ColumnMetaData> metas = new ArrayList<>();
        for (int i = 0; i < columnMetas.size(); i++) {
            ColumnMeta cm = columnMetas.get(i);
            Class columnClass = JdbcTypeUtil.jdbcTypeToClass(cm.getColumnType());
            ColumnMetaData.ScalarType type = ColumnMetaData.scalar(cm.getColumnType(), cm.getColumnTypeName(), ColumnMetaData.Rep.of(columnClass));

            ColumnMetaData meta = new ColumnMetaData(i, cm.isAutoIncrement(), cm.isCaseSensitive(),
                    cm.isSearchable(), cm.isCurrency(), cm.getIsNullable(), cm.isSigned(), cm.getDisplaySize(),
                    cm.getLabel(), cm.getName(), cm.getSchemaName(), cm.getPrecision(), cm.getScale(),
                    cm.getTableName(), cm.getSchemaName(), type, cm.isReadOnly(), cm.isWritable(), cm.isWritable(),
                    columnClass.getCanonicalName());

            metas.add(meta);
        }

        return metas;
    }

    private List<Object> convertResultData(List<List<String>> stringResults, List<ColumnMetaData> metas) {
        List<Object> data = new ArrayList<>(stringResults.size());
        for (List<String> result : stringResults) {
            Object[] row = new Object[result.size()];

            for (int i = 0; i < result.size(); i++) {
                ColumnMetaData meta = metas.get(i);
                row[i] = JdbcTypeUtil.jdbcStrToObject(result.get(i), meta.type.id);
            }

            data.add(row);
        }
        return data;
    }

    @Override
    public List<MetaImpl.MetaCatalog> getCatalogs() throws MixqJdbcException {
        String url = HttpUtil.buildUrl(getBaseUrl() + "/tedi-sql/jdbc/catalogs", buildRequestParams());

        List<String> result = postRequest(url, null, SYNC_HTTP_TIMEOUT, new TypeReference<List<String>>() {
        });

        // 转换为 avatica 定义的类型
        return result.stream().map(MetaImpl.MetaCatalog::new).collect(Collectors.toList());
    }

    @Override
    public List<MetaImpl.MetaSchema> getSchemas(String catalog, String schemaPattern) throws MixqJdbcException {
        String url = HttpUtil.buildUrl(getBaseUrl() + "/tedi-sql/jdbc/schemas", buildRequestParams());

        SchemaMetaRequest requestBody = new SchemaMetaRequest(catalog, schemaPattern);

        //调用api获取结果
        List<SchemaMetaResult> result = postRequest(url, requestBody, SYNC_HTTP_TIMEOUT,
                new TypeReference<List<SchemaMetaResult>>() {
                });

        //转换为avatica定义的类型
        List<MetaImpl.MetaSchema> metaSchemas = result.stream()
                .map(res -> new MetaImpl.MetaSchema(res.getTableCatalog(), res.getTableSchem()))
                .collect(Collectors.toList());

        return metaSchemas;
    }

    @Override
    public List<MetaImpl.MetaTable> getTables(String catalog, String schemaPattern, String tableNamePattern, List<String> typeList) throws MixqJdbcException {
        String url = HttpUtil.buildUrl(getBaseUrl() + "/tedi-sql/jdbc/tables", buildRequestParams());

        TableMetaRequest requestBody = new TableMetaRequest(catalog, schemaPattern, tableNamePattern, typeList);

        //调用api获取结果
        List<TableMetaResult> result = postRequest(url, requestBody, SYNC_HTTP_TIMEOUT,
                new TypeReference<List<TableMetaResult>>() {
                });

        //转换为avatica定义的类型
        List<MetaImpl.MetaTable> metaTables = result.stream()
                .map(res -> new MetaImpl.MetaTable(res.getTableCat(), res.getTypeSchema(), res.getTableName(),
                        res.getTableType()))
                .collect(Collectors.toList());

        return metaTables;
    }

    @Override
    public List<MetaImpl.MetaColumn> getColumns(String catalog, String schemaPattern, String tableNamePattern, String columnNamePattern) throws MixqJdbcException {
        String url = HttpUtil.buildUrl(getBaseUrl() + "/tedi-sql/jdbc/columns", buildRequestParams());

        ColumnMetaRequest requestBody = new ColumnMetaRequest(catalog, schemaPattern, tableNamePattern,
                columnNamePattern);

        //调用api获取结果
        List<ColumnMetaResult> result = postRequest(url, requestBody, SYNC_HTTP_TIMEOUT,
                new TypeReference<List<ColumnMetaResult>>() {
                });

        //转换为avatica定义的类型
        List<MetaImpl.MetaColumn> metaColumns = result.stream()
                .map(res -> new MetaImpl.MetaColumn(res.getTableCat(), res.getTableSchem(), res.getTableName(),
                        res.getColumnName(), res.getDataType(), res.getTypeName(), res.getColumnSize(),
                        res.getDecimalDigits(), res.getNumPrecRadix(), res.getNullable(), res.getCharOctetLength(),
                        res.getOrdinalPosition(), res.getIsNullable()))
                .collect(Collectors.toList());

        return metaColumns;
    }

    @Override
    public List<MetaImpl.MetaTableType> getTableTypes() throws MixqJdbcException {
        String url = HttpUtil.buildUrl(getBaseUrl() + "/tedi-sql/jdbc/tableTypes", buildRequestParams());

        //调用api获取结果
        List<String> result = postRequest(url, null, SYNC_HTTP_TIMEOUT, new TypeReference<List<String>>() {
        });

        //转换为avatica定义的类型
        List<MetaImpl.MetaTableType> metaTableTypes =
                result.stream().map(MetaImpl.MetaTableType::new).collect(Collectors.toList());

        return metaTableTypes;
    }

    private String getBaseUrl() {
        return "http://" + connInfo.getBaseUrl();
    }

    private Map<String, String> buildRequestParams() {
        Map<String, String> clientProps = new HashMap<>();
        Properties connProps = connInfo.getConnProperties();

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
        logger.info("Post http request to BeaconServer.url={}", url);

        ExecuteResult result = HttpUtil.post(url, requestBody, socketTimeoutMs, ExecuteResult.class);

        return JsonUtil.objectToObject(result, valueTypeRef);
    }

    private <T> T postRequest(String url, Object requestBody, int socketTimeoutMs, Class<T> clazz)
            throws MixqJdbcException {
        logger.info("Post http request to BeaconServer.url={}", url);

        ExecuteResult result = HttpUtil.post(url, requestBody, socketTimeoutMs, ExecuteResult.class);

        return JsonUtil.objectToObject(result, clazz);
    }

    private Object postRequest(String url, Object requestBody, int httpTimeoutMs) throws MixqJdbcException {
        logger.info("Post http request to BeaconServer.url={}", url);
        Response response = HttpUtil.post(url, requestBody, httpTimeoutMs, Response.class);
        return response.getResult();
    }

    @Override
    public void close() throws MixqJdbcException {

    }

    @Override
    public void close(int statementId) throws MixqJdbcException {

    }
}
