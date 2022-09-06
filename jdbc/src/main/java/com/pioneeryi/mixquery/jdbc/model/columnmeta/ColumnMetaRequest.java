package com.pioneeryi.mixquery.jdbc.model.columnmeta;

/**
 * 列元数据请求体
 */
public class ColumnMetaRequest {

    private String catalog;

    private String schemaPattern;

    private String tableNamePattern;

    private String columnNamePattern;

    public ColumnMetaRequest(String catalog, String schemaPattern, String tableNamePattern,
                             String columnNamePattern) {
        this.catalog = catalog;
        this.schemaPattern = schemaPattern;
        this.tableNamePattern = tableNamePattern;
        this.columnNamePattern = columnNamePattern;
    }

    public String getCatalog() {
        return catalog;
    }

    public String getSchemaPattern() {
        return schemaPattern;
    }

    public String getTableNamePattern() {
        return tableNamePattern;
    }

    public String getColumnNamePattern() {
        return columnNamePattern;
    }

    @Override
    public String toString() {
        return new StringBuffer()
                .append("ColumnMetaRequest{")
                .append("catalog='")
                .append(catalog).append('\'')
                .append(", schemaPattern='")
                .append(schemaPattern).append('\'')
                .append(", tableNamePattern='")
                .append(tableNamePattern).append('\'')
                .append(", columnNamePattern='")
                .append(columnNamePattern).append('\'')
                .append('}')
                .toString();
    }
}
