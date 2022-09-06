package com.pioneeryi.mixquery.meta.domain;

import java.util.Arrays;
import java.util.List;

/**
 * Provide different params adapt for type of data source.
 *
 * @author pioneeryi
 * @since 2022/4/29 11:29
 */
public enum SchemaType {

    JDBC("org.apache.calcite.adapter.jdbc.JdbcSchema$Factory",
            "org.apache.calcite.adapter.custom.JdbcTableFactory",
            Arrays.asList("dbName", "tableName", "dbType", "jdbcDriver", "jdbcUrl", "jdbcUser", "jdbcPassword"));

    private String schemaFactory;

    private String tableFactory;

    List<String> properties;

    SchemaType(String schemaFactory, String tableFactory, List<String> properties) {
        this.schemaFactory = schemaFactory;
        this.tableFactory = tableFactory;
        this.properties = properties;
    }

    public String getSchemaFactory() {
        return schemaFactory;
    }

    public String getTableFactory() {
        return tableFactory;
    }

    public List<String> getProperties() {
        return properties;
    }

    public static SchemaType getSchemaType(DatasourceType datasourceType) {
        if (datasourceType == DatasourceType.MYSQL || datasourceType == DatasourceType.POSTGRESQL) {
            return SchemaType.JDBC;
        }
        throw new RuntimeException("not support this datasource type");
    }
}
