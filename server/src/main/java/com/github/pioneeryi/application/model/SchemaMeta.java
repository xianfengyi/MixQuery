package com.github.pioneeryi.application.model;

public class SchemaMeta {

    private String tableSchema;

    private String tableCatalog;

    public SchemaMeta() {
    }

    public SchemaMeta(String tableSchema, String tableCatalog) {
        this.tableSchema = tableSchema;
        this.tableCatalog = tableCatalog;
    }

    public String getTableSchema() {
        return tableSchema;
    }

    public void setTableSchema(String tableSchema) {
        this.tableSchema = tableSchema;
    }

    public String getTableCatalog() {
        return tableCatalog;
    }

    public void setTableCatalog(String tableCatalog) {
        this.tableCatalog = tableCatalog;
    }
}
