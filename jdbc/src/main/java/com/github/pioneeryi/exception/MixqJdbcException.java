package com.github.pioneeryi.exception;

/**
 * MixqJdbcException.
 *
 * @Author bytedance
 * @Date 2022/8/30 11:16 PM
 */
public class MixqJdbcException extends RuntimeException {
    public MixqJdbcException(String message) {
        super(message);
    }

    public MixqJdbcException(String message, Throwable t) {
        super(message, t);
    }
}
