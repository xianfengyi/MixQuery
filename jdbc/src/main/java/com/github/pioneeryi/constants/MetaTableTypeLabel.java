package com.github.pioneeryi.constants;

import java.util.Arrays;

/**
 * 表类型元数据标记名 用于标记各个元数据在ResultSet中的字段
 */
public enum MetaTableTypeLabel {

    TABLE_TYPE;

    public static String[] getNames() {
        return Arrays.stream(values()).map(label -> label.name()).toArray(String[]::new);
    }
}
