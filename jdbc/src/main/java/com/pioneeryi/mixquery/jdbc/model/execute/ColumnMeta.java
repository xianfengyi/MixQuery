package com.pioneeryi.mixquery.jdbc.model.execute;

/**
 * 查询结果列 元数据信息
 */
public class ColumnMeta {

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

    public void setAutoIncrement(boolean autoIncrement) {
        isAutoIncrement = autoIncrement;
    }

    public void setCaseSensitive(boolean caseSensitive) {
        isCaseSensitive = caseSensitive;
    }

    public void setSearchable(boolean searchable) {
        isSearchable = searchable;
    }

    public void setCurrency(boolean currency) {
        isCurrency = currency;
    }

    public void setIsNullable(int isNullable) {
        this.isNullable = isNullable;
    }

    public void setSigned(boolean signed) {
        isSigned = signed;
    }

    public void setDisplaySize(int displaySize) {
        this.displaySize = displaySize;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSchemaName(String schemaName) {
        this.schemaName = schemaName;
    }

    public void setCatalogName(String catalogName) {
        this.catalogName = catalogName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public void setPrecision(int precision) {
        this.precision = precision;
    }

    public void setScale(int scale) {
        this.scale = scale;
    }

    public void setColumnType(int columnType) {
        this.columnType = columnType;
    }

    public void setColumnTypeName(String columnTypeName) {
        this.columnTypeName = columnTypeName;
    }

    public void setReadOnly(boolean readOnly) {
        isReadOnly = readOnly;
    }

    public void setWritable(boolean writable) {
        isWritable = writable;
    }

    public void setDefinitelyWritable(boolean definitelyWritable) {
        isDefinitelyWritable = definitelyWritable;
    }

    public boolean isAutoIncrement() {
        return isAutoIncrement;
    }

    public boolean isCaseSensitive() {
        return isCaseSensitive;
    }

    public boolean isSearchable() {
        return isSearchable;
    }

    public boolean isCurrency() {
        return isCurrency;
    }

    public int getIsNullable() {
        return isNullable;
    }

    public boolean isSigned() {
        return isSigned;
    }

    public int getDisplaySize() {
        return displaySize;
    }

    public String getLabel() {
        return label;
    }

    public String getName() {
        return name;
    }

    public String getSchemaName() {
        return schemaName;
    }

    public String getCatalogName() {
        return catalogName;
    }

    public String getTableName() {
        return tableName;
    }

    public int getPrecision() {
        return precision;
    }

    public int getScale() {
        return scale;
    }

    public int getColumnType() {
        return columnType;
    }

    public String getColumnTypeName() {
        return columnTypeName;
    }

    public boolean isReadOnly() {
        return isReadOnly;
    }

    public boolean isWritable() {
        return isWritable;
    }

    public boolean isDefinitelyWritable() {
        return isDefinitelyWritable;
    }

    @Override
    public String toString() {
        return new StringBuffer()
                .append("ColumnMeta{")
                .append("isAutoIncrement=")
                .append(isAutoIncrement)
                .append(", isCaseSensitive=")
                .append(isCaseSensitive)
                .append(", isSearchable=")
                .append(isSearchable)
                .append(", isCurrency=")
                .append(isCurrency)
                .append(", isNullable=")
                .append(isNullable)
                .append(", isSigned=")
                .append(isSigned)
                .append(", displaySize=")
                .append(displaySize)
                .append(", label='")
                .append(label).append('\'')
                .append(", name='")
                .append(name).append('\'')
                .append(", schemaName='")
                .append(schemaName).append('\'')
                .append(", catalogName='")
                .append(catalogName).append('\'')
                .append(", tableName='")
                .append(tableName).append('\'')
                .append(", precision=")
                .append(precision)
                .append(", scale=")
                .append(scale)
                .append(", columnType=")
                .append(columnType)
                .append(", columnTypeName='")
                .append(columnTypeName).append('\'')
                .append(", isReadOnly=")
                .append(isReadOnly)
                .append(", isWritable=")
                .append(isWritable)
                .append(", isDefinitelyWritable=")
                .append(isDefinitelyWritable)
                .append('}')
                .toString();
    }
}