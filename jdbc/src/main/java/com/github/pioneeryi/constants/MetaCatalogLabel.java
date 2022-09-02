package com.github.pioneeryi.constants;

import java.util.Arrays;

/**
 * catalog元数据标记名 用于标记各个元数据在ResultSet中的字段
 */
public enum MetaCatalogLabel {

    TABLE_CAT;

    public static String[] getNames() {
        return Arrays.stream(values()).map(label -> label.name()).toArray(String[]::new);
    }
}
