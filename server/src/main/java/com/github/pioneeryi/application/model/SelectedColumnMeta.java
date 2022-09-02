package com.github.pioneeryi.application.model;

import java.io.Serializable;

public class SelectedColumnMeta implements Serializable {

    private boolean isAutoIncrement;
    private boolean isCaseSensitive;
    private boolean isSearchable;
    private boolean isCurrency;
    private int isNullable;// 0:nonull, 1:nullable, 2: nullableunknown
    private boolean isSigned;
    private int displaySize;
    private String label;// AS keyword
    private String name;
    private String schemaName;
    private String catalogName;
    private String tableName;
    private int precision;
    private int scale;
    private int columnType;// as defined in java.sql.Types
    private String columnTypeName;
    private boolean isReadOnly;
    private boolean isWritable;
    private boolean isDefinitelyWritable;

    public SelectedColumnMeta() {
    }

    public SelectedColumnMeta(boolean isAutoIncrement, boolean isCaseSensitive, boolean isSearchable,
                              boolean isCurrency, int isNullalbe, boolean isSigned, int displaySize, String label,
                              String name, String schemaName, String catalogName, String tableName, int precision,
                              int scale, int columnType, String columnTypeName, boolean isReadOnly, boolean isWritable,
                              boolean isDefinitelyWritable) {
        super();
        this.isAutoIncrement = isAutoIncrement;
        this.isCaseSensitive = isCaseSensitive;
        this.isSearchable = isSearchable;
        this.isCurrency = isCurrency;
        this.isNullable = isNullalbe;
        this.isSigned = isSigned;
        this.displaySize = displaySize;
        this.label = label;
        this.name = name;
        this.schemaName = schemaName;
        this.catalogName = catalogName;
        this.tableName = tableName;
        this.precision = precision;
        this.scale = scale;
        this.columnType = columnType;
        this.columnTypeName = columnTypeName;
        this.isReadOnly = isReadOnly;
        this.isWritable = isWritable;
        this.isDefinitelyWritable = isDefinitelyWritable;
    }

    public boolean isAutoIncrement() {
        return isAutoIncrement;
    }

    public void setAutoIncrement(boolean autoIncrement) {
        isAutoIncrement = autoIncrement;
    }

    public boolean isCaseSensitive() {
        return isCaseSensitive;
    }

    public void setCaseSensitive(boolean caseSensitive) {
        isCaseSensitive = caseSensitive;
    }

    public boolean isSearchable() {
        return isSearchable;
    }

    public void setSearchable(boolean searchable) {
        isSearchable = searchable;
    }

    public boolean isCurrency() {
        return isCurrency;
    }

    public void setCurrency(boolean currency) {
        isCurrency = currency;
    }

    public int getIsNullable() {
        return isNullable;
    }

    public void setIsNullable(int isNullable) {
        this.isNullable = isNullable;
    }

    public boolean isSigned() {
        return isSigned;
    }

    public void setSigned(boolean signed) {
        isSigned = signed;
    }

    public int getDisplaySize() {
        return displaySize;
    }

    public void setDisplaySize(int displaySize) {
        this.displaySize = displaySize;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSchemaName() {
        return schemaName;
    }

    public void setSchemaName(String schemaName) {
        this.schemaName = schemaName;
    }

    public String getCatalogName() {
        return catalogName;
    }

    public void setCatalogName(String catalogName) {
        this.catalogName = catalogName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public int getPrecision() {
        return precision;
    }

    public void setPrecision(int precision) {
        this.precision = precision;
    }

    public int getScale() {
        return scale;
    }

    public void setScale(int scale) {
        this.scale = scale;
    }

    public int getColumnType() {
        return columnType;
    }

    public void setColumnType(int columnType) {
        this.columnType = columnType;
    }

    public String getColumnTypeName() {
        return columnTypeName;
    }

    public void setColumnTypeName(String columnTypeName) {
        this.columnTypeName = columnTypeName;
    }

    public boolean isReadOnly() {
        return isReadOnly;
    }

    public void setReadOnly(boolean readOnly) {
        isReadOnly = readOnly;
    }

    public boolean isWritable() {
        return isWritable;
    }

    public void setWritable(boolean writable) {
        isWritable = writable;
    }

    public boolean isDefinitelyWritable() {
        return isDefinitelyWritable;
    }

    public void setDefinitelyWritable(boolean definitelyWritable) {
        isDefinitelyWritable = definitelyWritable;
    }
}
