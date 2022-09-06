package com.pioneeryi.mixquery.jdbc.model;

/**
 * 服务端响应结构
 */
public class Response<T> {

    private Status status;

    private T result;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public static class Status {

        private String code;
        private String message;
        private Object extra;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public Object getExtra() {
            return extra;
        }

        public void setExtra(Object extra) {
            this.extra = extra;
        }

        @Override
        public String toString() {
            return new StringBuffer()
                    .append("Status{")
                    .append("code='")
                    .append(code).append('\'')
                    .append(", message='")
                    .append(message).append('\'')
                    .append(", extra=")
                    .append(extra)
                    .append('}')
                    .toString();
        }
    }

    @Override
    public String toString() {
        return new StringBuffer()
                .append("Response{")
                .append("status=")
                .append(status)
                .append(", result=")
                .append(result)
                .append('}')
                .toString();
    }
}