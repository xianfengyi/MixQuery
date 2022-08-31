package com.github.pioneeryi.response;

import com.github.pioneeryi.domain.DatasourceType;

import java.io.Serializable;
import java.util.Map;

public class GetDataSourceResponse implements Serializable {

    // 数据库名
    private String dbName;

    // 表名
    private String tableName;

    // 数据源 type
    private DatasourceType datasourceType;

    // 属性
    private Map<String, String> connProperties;

    public GetDataSourceResponse() {
    }

    public GetDataSourceResponse(String dbName, String tableName,
                                 DatasourceType datasourceType,
                                 Map<String, String> connProperties) {
        this.dbName = dbName;
        this.tableName = tableName;
        this.datasourceType = datasourceType;
        this.connProperties = connProperties;
    }

    public String getDbName() {
        return dbName;
    }

    public String getTableName() {
        return tableName;
    }

    public DatasourceType getDatasourceType() {
        return datasourceType;
    }

    public Map<String, String> getConnProperties() {
        return connProperties;
    }
}
