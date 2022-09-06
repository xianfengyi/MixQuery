package com.pioneeryi.mixquery.jdbc.exception;

public class MixqJdbcException extends RuntimeException {
    public MixqJdbcException(String message) {
        super(message);
    }

    public MixqJdbcException(String message, Throwable t) {
        super(message, t);
    }
}
