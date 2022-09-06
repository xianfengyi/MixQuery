package com.pioneeryi.mixquery.jdbc.model.tablemeta;

/**
 * 表元数据结果体
 */
public class TableMetaResult {

    private String tableCat;
    private String tableSchema;
    private String tableName;
    private String tableType;
    private String remarks;
    private String typeCat;
    private String typeSchema;
    private String typeName;
    private String selfReferencingColName;
    private String refGeneration;

    public void setTableCat(String tableCat) {
        this.tableCat = tableCat;
    }

    public void setTableSchema(String tableSchema) {
        this.tableSchema = tableSchema;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public void setTableType(String tableType) {
        this.tableType = tableType;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public void setTypeCat(String typeCat) {
        this.typeCat = typeCat;
    }

    public void setTypeSchema(String typeSchema) {
        this.typeSchema = typeSchema;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public void setSelfReferencingColName(String selfReferencingColName) {
        this.selfReferencingColName = selfReferencingColName;
    }

    public void setRefGeneration(String refGeneration) {
        this.refGeneration = refGeneration;
    }

    public String getTableCat() {
        return tableCat;
    }

    public String getTableSchema() {
        return tableSchema;
    }

    public String getTableName() {
        return tableName;
    }

    public String getTableType() {
        return tableType;
    }

    public String getRemarks() {
        return remarks;
    }

    public String getTypeCat() {
        return typeCat;
    }

    public String getTypeSchema() {
        return typeSchema;
    }

    public String getTypeName() {
        return typeName;
    }

    public String getSelfReferencingColName() {
        return selfReferencingColName;
    }

    public String getRefGeneration() {
        return refGeneration;
    }

    @Override
    public String toString() {
        return new StringBuffer()
                .append("TableMetaResult{")
                .append("tableCat='")
                .append(tableCat).append('\'')
                .append(", tableSchema='")
                .append(tableSchema).append('\'')
                .append(", tableName='")
                .append(tableName).append('\'')
                .append(", tableType='")
                .append(tableType).append('\'')
                .append(", remarks='")
                .append(remarks).append('\'')
                .append(", typeCat='")
                .append(typeCat).append('\'')
                .append(", typeSchema='")
                .append(typeSchema).append('\'')
                .append(", typeName='")
                .append(typeName).append('\'')
                .append(", selfReferencingColName='")
                .append(selfReferencingColName).append('\'')
                .append(", refGeneration='")
                .append(refGeneration).append('\'')
                .append('}')
                .toString();
    }
}
