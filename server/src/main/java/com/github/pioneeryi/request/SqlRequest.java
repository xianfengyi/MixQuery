package com.github.pioneeryi.request;

import java.io.Serializable;

/**
 * SqlRequest.
 *
 * @author pioneeryi
 * @since 2022/5/17 11:08
 */
public class SqlRequest implements Serializable {

    private String sql;

    public SqlRequest() {
    }

    public SqlRequest(String sql) {
        this.sql = sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public String getSql() {
        return sql;
    }
}
