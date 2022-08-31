package com.github.pioneeryi.util;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * 该工具用于JDBC类型和Java类型的转换 {@link Types}里定义了JDBC可用的类型代码
 */
public class JdbcTypeUtil {

    /**
     * 把JDBC类型转换为Java类型
     *
     * @param typeCode {@link Types}中定义的类型代码
     *
     * @return
     */
    public static Class jdbcTypeToClass(int typeCode) {
        Class result = Object.class;

        switch (typeCode) {
            case Types.CHAR:
            case Types.VARCHAR:
            case Types.LONGVARCHAR:
                result = String.class;
                break;
            case Types.NUMERIC:
            case Types.DECIMAL:
                result = BigDecimal.class;
                break;
            case Types.BIT:
            case Types.BOOLEAN:
                result = Boolean.class;
                break;
            case Types.TINYINT:
                result = Byte.class;
                break;
            case Types.SMALLINT:
                result = Short.class;
                break;
            case Types.INTEGER:
                result = Integer.class;
                break;
            case Types.BIGINT:
                result = Long.class;
                break;
            case Types.REAL:
                //与org.apache.calcite.avatica.AvaticaSite.get保持统一
                result = Float.class;
                break;
            case Types.FLOAT:
            case Types.DOUBLE:
                result = Double.class;
                break;
            case Types.BINARY:
            case Types.VARBINARY:
            case Types.LONGVARBINARY:
                result = Byte[].class;
                break;
            case Types.DATE:
                result = Date.class;
                break;
            case Types.TIME:
                result = Time.class;
                break;
            case Types.TIMESTAMP:
                result = Timestamp.class;
                break;
            default:
                //do nothing
                break;
        }

        return result;
    }

    /**
     * 根据JDBC类型 把字符串转换为Java对象
     *
     * @param value
     * @param typeCode {@link Types}中定义的类型代码
     *
     * @return
     */
    public static Object jdbcStrToObject(String value, int typeCode) {
        if (null == value) {
            return null;
        }

        switch (typeCode) {
            case Types.CHAR:
            case Types.VARCHAR:
            case Types.LONGVARCHAR:
                return value;
            case Types.NUMERIC:
            case Types.DECIMAL:
                return new BigDecimal(value);
            case Types.BIT:
            case Types.BOOLEAN:
                return booleanConvert(value);
            case Types.TINYINT:
                return Byte.valueOf(value);
            case Types.SMALLINT:
                return Short.valueOf(value);
            case Types.INTEGER:
                return Integer.valueOf(value);
            case Types.BIGINT:
                return Long.valueOf(value);
            case Types.REAL:
                return Float.valueOf(value);
            case Types.FLOAT:
            case Types.DOUBLE:
                return Double.valueOf(value);
            case Types.BINARY:
            case Types.VARBINARY:
            case Types.LONGVARBINARY:
                return value.getBytes(StandardCharsets.UTF_8);
            case Types.DATE:
                return dateConvert(value);
            case Types.TIME:
                return Time.valueOf(value);
            case Types.TIMESTAMP:
                return timestampConvert(value);
            default:
                //do nothing
                break;

        }

        return value;
    }


    private static Date dateConvert(String value) {
        ZoneId utc = ZoneId.of("UTC");
        LocalDate localDate = Date.valueOf(value).toLocalDate();
        return new Date(localDate.atStartOfDay(utc).toInstant().toEpochMilli());
    }

    private static Timestamp timestampConvert(String value) {
        ZoneId utc = ZoneId.of("UTC");
        LocalDateTime localDate = Timestamp.valueOf(value).toLocalDateTime();
        return new Timestamp(localDate.atZone(utc).toInstant().toEpochMilli());
    }

    private static Boolean booleanConvert(String value) {
        if ("1".equals(value)) {
            return true;
        } else if ("0".equals(value)) {
            return false;
        } else {
            return Boolean.valueOf(value);
        }
    }
}
