package com.github.pioneeryi.application.model;

public class SchemaMeta {

    private String tableSchem;

    private String tableCatalog;

    public SchemaMeta() {
    }

    public SchemaMeta(String tableSchem, String tableCatalog) {
        this.tableSchem = tableSchem;
        this.tableCatalog = tableCatalog;
    }

    public String getTableSchem() {
        return tableSchem;
    }

    public void setTableSchem(String tableSchem) {
        this.tableSchem = tableSchem;
    }

    public String getTableCatalog() {
        return tableCatalog;
    }

    public void setTableCatalog(String tableCatalog) {
        this.tableCatalog = tableCatalog;
    }
}
