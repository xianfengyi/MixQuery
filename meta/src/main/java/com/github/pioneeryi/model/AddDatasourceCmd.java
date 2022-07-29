package com.github.pioneeryi.model;

import com.github.pioneeryi.domain.DatasourceType;
import java.util.Map;

/**
 * Add datasource command.
 *
 * @author yixianfeng
 * @since 2022/7/26 19:35
 */
public class AddDatasourceCmd {

    private String dbName;

    private String tableName;

    private DatasourceType datasourceType;

    private Map<String, String> connProperties;

    public AddDatasourceCmd(String dbName, DatasourceType datasourceType, Map<String, String> connProperties) {
        this.dbName = dbName;
        this.datasourceType = datasourceType;
        this.connProperties = connProperties;
    }

    public AddDatasourceCmd(String dbName, String tableName, DatasourceType datasourceType,
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
