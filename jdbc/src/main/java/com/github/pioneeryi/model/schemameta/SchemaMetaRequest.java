package com.github.pioneeryi.model.schemameta;

/**
 * schema元数据请求体
 */
public class SchemaMetaRequest {

    private String catalog;

    private String schemaPattern;

    public SchemaMetaRequest(String catalog, String schemaPattern) {
        this.catalog = catalog;
        this.schemaPattern = schemaPattern;
    }

    public String getCatalog() {
        return catalog;
    }

    public String getSchemaPattern() {
        return schemaPattern;
    }

    @Override
    public String toString() {
        return new StringBuffer()
                .append("SchemaMetaRequest{")
                .append("catalog='")
                .append(catalog).append('\'')
                .append(", schemaPattern=")
                .append(schemaPattern).append('\'')
                .append('}')
                .toString();
    }
}
