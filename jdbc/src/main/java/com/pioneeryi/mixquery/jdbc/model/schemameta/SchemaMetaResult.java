package com.pioneeryi.mixquery.jdbc.model.schemameta;

/**
 * schema元数据结果体
 */
public class SchemaMetaResult {

    private String tableSchem;

    private String tableCatalog;

    public void setTableSchem(String tableSchem) {
        this.tableSchem = tableSchem;
    }

    public void setTableCatalog(String tableCatalog) {
        this.tableCatalog = tableCatalog;
    }

    public String getTableSchem() {
        return tableSchem;
    }

    public String getTableCatalog() {
        return tableCatalog;
    }

    @Override
    public String toString() {
        return new StringBuffer()
                .append("SchemaMeta{")
                .append("tableSchem='")
                .append(tableSchem).append('\'')
                .append(", tableCatalog='")
                .append(tableCatalog).append('\'')
                .append('}')
                .toString();
    }
}
