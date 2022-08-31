package com.github.pioneeryi.request;

import java.io.Serializable;
import java.util.Map;

public class CreateDataSourceRequest implements Serializable {

    // 数据库名
    private String dbName;

    // 表名
    private String tableName;

    // 数据源 type
    private String datasourceType;

    // 属性
    private Map<String, String> connProperties;

    public CreateDataSourceRequest() {
    }

    public CreateDataSourceRequest(String dbName, String tableName, String datasourceType,
                                   Map<String, String> connProperties) {
        this.dbName = dbName;
        this.tableName = tableName;
        this.datasourceType = datasourceType;
        this.connProperties = connProperties;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getDatasourceType() {
        return datasourceType;
    }

    public void setDatasourceType(String datasourceType) {
        this.datasourceType = datasourceType;
    }

    public Map<String, String> getConnProperties() {
        return connProperties;
    }

    public void setConnProperties(Map<String, String> connProperties) {
        this.connProperties = connProperties;
    }
}
