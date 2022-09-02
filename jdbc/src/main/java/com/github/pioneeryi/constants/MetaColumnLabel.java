package com.github.pioneeryi.constants;

import java.util.Arrays;

/**
 * 列元数据标记名 用于标记各个元数据在ResultSet中的字段
 */
public enum MetaColumnLabel {

    TABLE_CAT,
    TABLE_SCHEM,
    TABLE_NAME,
    COLUMN_NAME,
    DATA_TYPE,
    TYPE_NAME,
    COLUMN_SIZE,
    BUFFER_LENGTH,
    DECIMAL_DIGITS,
    NUM_PREC_RADIX,
    NULLABLE,
    REMARKS,
    COLUMN_DEF,
    SQL_DATA_TYPE,
    SQL_DATETIME_SUB,
    CHAR_OCTET_LENGTH,
    ORDINAL_POSITION,
    IS_NULLABLE,
    SCOPE_CATALOG,
    SCOPE_SCHEMA,
    SCOPE_TABLE,
    SOURCE_DATA_TYPE,
    IS_AUTOINCREMENT,
    IS_GENERATEDCOLUMN;

    public static String[] getNames() {
        return Arrays.stream(values()).map(label -> label.name()).toArray(String[]::new);
    }
}
