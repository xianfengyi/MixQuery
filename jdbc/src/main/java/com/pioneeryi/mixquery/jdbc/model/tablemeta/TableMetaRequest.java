package com.pioneeryi.mixquery.jdbc.model.tablemeta;

import java.util.List;

/**
 * 表元数据请求体
 */
public class TableMetaRequest {

    private String catalog;

    private String schemaPattern;

    private String tableNamePattern;

    private List<String> typeList;

    public TableMetaRequest(String catalog, String schemaPattern, String tableNamePattern,
                            List<String> typeList) {
        this.catalog = catalog;
        this.schemaPattern = schemaPattern;
        this.tableNamePattern = tableNamePattern;
        this.typeList = typeList;
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

    public List<String> getTypeList() {
        return typeList;
    }

    @Override
    public String toString() {
        return new StringBuffer()
                .append("TableMetaRequest{")
                .append("catalog='")
                .append(catalog).append('\'')
                .append(", schemaPattern='")
                .append(schemaPattern).append('\'')
                .append(", tableNamePattern='")
                .append(tableNamePattern).append('\'')
                .append(", typeList=")
                .append(typeList)
                .append('}')
                .toString();
    }
}
