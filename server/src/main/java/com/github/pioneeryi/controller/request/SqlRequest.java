package com.github.pioneeryi.controller.request;

import java.io.Serializable;

/**
 * sql query resquest.
 *
 * @author yixianfeng
 * @since 2022/7/29 09:18
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
