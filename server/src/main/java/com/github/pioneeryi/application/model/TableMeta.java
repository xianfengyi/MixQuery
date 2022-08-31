package com.github.pioneeryi.application.model;

public class TableMeta {
    private String tableCat;
    private String tableSchem;
    private String tableName;
    private String tableType;
    private String remarks;
    private String typeCat;
    private String typeSchem;
    private String typeName;
    private String selfReferencingColName;
    private String refGeneration;

    public void setTableCat(String tableCat) {
        this.tableCat = tableCat;
    }

    public void setTableSchem(String tableSchem) {
        this.tableSchem = tableSchem;
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

    public void setTypeSchem(String typeSchem) {
        this.typeSchem = typeSchem;
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

    public String getTableSchem() {
        return tableSchem;
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

    public String getTypeSchem() {
        return typeSchem;
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
                .append(", tableSchem='")
                .append(tableSchem).append('\'')
                .append(", tableName='")
                .append(tableName).append('\'')
                .append(", tableType='")
                .append(tableType).append('\'')
                .append(", remarks='")
                .append(remarks).append('\'')
                .append(", typeCat='")
                .append(typeCat).append('\'')
                .append(", typeSchem='")
                .append(typeSchem).append('\'')
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
