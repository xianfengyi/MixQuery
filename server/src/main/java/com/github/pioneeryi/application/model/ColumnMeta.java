package com.github.pioneeryi.application.model;

public class ColumnMeta {

    private String tableCat;
    private String tableSchema;
    private String tableName;
    private String columnName;
    private int dataType;
    private String typeName;
    private int columnSize;
    private int bufferLength;
    private int decimalDigits;
    private int numPrecRadix;
    private int nullable;
    private String remarks;
    private String columnDef;
    private int sqlDataType;
    private int sqlDatetimeSub;
    private int charOctetLength;
    private int ordinalPosition;
    private String isNullable;
    private String scopeCatalog;
    private String scopeSchema;
    private String scopeTable;
    private short sourceDataType;
    private String isAutoincrement;
    private String isGeneratedcolumn;

    public void setTableCat(String tableCat) {
        this.tableCat = tableCat;
    }

    public void setTableSchema(String tableSchema) {
        this.tableSchema = tableSchema;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public void setDataType(int dataType) {
        this.dataType = dataType;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public void setColumnSize(int columnSize) {
        this.columnSize = columnSize;
    }

    public void setBufferLength(int bufferLength) {
        this.bufferLength = bufferLength;
    }

    public void setDecimalDigits(int decimalDigits) {
        this.decimalDigits = decimalDigits;
    }

    public void setNumPrecRadix(int numPrecRadix) {
        this.numPrecRadix = numPrecRadix;
    }

    public void setNullable(int nullable) {
        this.nullable = nullable;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public void setColumnDef(String columnDef) {
        this.columnDef = columnDef;
    }

    public void setSqlDataType(int sqlDataType) {
        this.sqlDataType = sqlDataType;
    }

    public void setSqlDatetimeSub(int sqlDatetimeSub) {
        this.sqlDatetimeSub = sqlDatetimeSub;
    }

    public void setCharOctetLength(int charOctetLength) {
        this.charOctetLength = charOctetLength;
    }

    public void setOrdinalPosition(int ordinalPosition) {
        this.ordinalPosition = ordinalPosition;
    }

    public void setIsNullable(String isNullable) {
        this.isNullable = isNullable;
    }

    public void setScopeCatalog(String scopeCatalog) {
        this.scopeCatalog = scopeCatalog;
    }

    public void setScopeSchema(String scopeSchema) {
        this.scopeSchema = scopeSchema;
    }

    public void setScopeTable(String scopeTable) {
        this.scopeTable = scopeTable;
    }

    public void setSourceDataType(short sourceDataType) {
        this.sourceDataType = sourceDataType;
    }

    public void setIsAutoincrement(String isAutoincrement) {
        this.isAutoincrement = isAutoincrement;
    }

    public void setIsGeneratedcolumn(String isGeneratedcolumn) {
        this.isGeneratedcolumn = isGeneratedcolumn;
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

    public String getColumnName() {
        return columnName;
    }

    public int getDataType() {
        return dataType;
    }

    public String getTypeName() {
        return typeName;
    }

    public int getColumnSize() {
        return columnSize;
    }

    public int getBufferLength() {
        return bufferLength;
    }

    public int getDecimalDigits() {
        return decimalDigits;
    }

    public int getNumPrecRadix() {
        return numPrecRadix;
    }

    public int getNullable() {
        return nullable;
    }

    public String getRemarks() {
        return remarks;
    }

    public String getColumnDef() {
        return columnDef;
    }

    public int getSqlDataType() {
        return sqlDataType;
    }

    public int getSqlDatetimeSub() {
        return sqlDatetimeSub;
    }

    public int getCharOctetLength() {
        return charOctetLength;
    }

    public int getOrdinalPosition() {
        return ordinalPosition;
    }

    public String getIsNullable() {
        return isNullable;
    }

    public String getScopeCatalog() {
        return scopeCatalog;
    }

    public String getScopeSchema() {
        return scopeSchema;
    }

    public String getScopeTable() {
        return scopeTable;
    }

    public short getSourceDataType() {
        return sourceDataType;
    }

    public String getIsAutoincrement() {
        return isAutoincrement;
    }

    public String getIsGeneratedcolumn() {
        return isGeneratedcolumn;
    }

    @Override
    public String toString() {
        return new StringBuffer()
                .append("ColumnMetaResult{")
                .append("tableCat='")
                .append(tableCat).append('\'')
                .append(", tableSchema='")
                .append(tableSchema).append('\'')
                .append(", tableName='")
                .append(tableName).append('\'')
                .append(", columnName='")
                .append(columnName).append('\'')
                .append(", dataType=")
                .append(dataType).append('\'')
                .append(", typeName='")
                .append(typeName).append('\'')
                .append(", columnSize=")
                .append(columnSize)
                .append(", bufferLength=")
                .append(bufferLength)
                .append(", decimalDigits=")
                .append(decimalDigits)
                .append(", numPrecRadix=")
                .append(numPrecRadix)
                .append(", nullable=")
                .append(nullable)
                .append(", remarks='")
                .append(remarks).append('\'')
                .append(", columnDef='")
                .append(columnDef).append('\'')
                .append(", sqlDataType=")
                .append(sqlDataType)
                .append(", sqlDatetimeSub=")
                .append(sqlDatetimeSub)
                .append(", charOctetLength=")
                .append(charOctetLength)
                .append(", ordinalPosition=")
                .append(ordinalPosition)
                .append(", isNullable='")
                .append(isNullable).append('\'')
                .append(", scopeCatlog='")
                .append(scopeCatalog).append('\'')
                .append(", scopeSchema='")
                .append(scopeSchema).append('\'')
                .append(", scopeTable='")
                .append(scopeTable).append('\'')
                .append(", sourceDataType=")
                .append(sourceDataType)
                .append(", isAutoincrement='")
                .append(isAutoincrement).append('\'')
                .append(", isGeneratedcolumn='")
                .append(isGeneratedcolumn).append('\'')
                .append('}')
                .toString();
    }
}
