package com.github.pioneeryi.constants;

import java.util.Arrays;

/**
 * 表元数据标记名 用于标记各个元数据在ResultSet中的字段
 */
public enum MetaTableLabel {

    TABLE_CAT,
    TABLE_SCHEM,
    TABLE_NAME,
    TABLE_TYPE,
    REMARKS,
    TYPE_CAT,
    TYPE_SCHEM,
    TYPE_NAME,
    SELF_REFERENCING_COL_NAME,
    REF_GENERATION;

    public static String[] getNames() {
        return Arrays.stream(values()).map(label -> label.name()).toArray(String[]::new);
    }
}
