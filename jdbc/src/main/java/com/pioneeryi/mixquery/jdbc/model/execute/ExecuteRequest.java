package com.pioneeryi.mixquery.jdbc.model.execute;

import java.util.List;

/**
 * 执行查询请求体
 */
public class ExecuteRequest {

    private String sql;
    private List<StatementParameter> preparedStatementParams;
    private int timeout;
    private boolean isSync;
    private String dialect;

    public ExecuteRequest(String sql,
                          List<StatementParameter> preparedStatementParams, int timeout, boolean isSync,
                          String dialect) {
        this.sql = sql;
        this.preparedStatementParams = preparedStatementParams;
        this.timeout = timeout;
        this.isSync = isSync;
        this.dialect = dialect;
    }

    public String getSql() {
        return sql;
    }

    public List<StatementParameter> getPreparedStatementParams() {
        return preparedStatementParams;
    }

    public int getTimeout() {
        return timeout;
    }

    public boolean isSync() {
        return isSync;
    }

    public String getDialect() {
        return dialect;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("ExecuteRequest{");
        sb.append("sql='").append(sql).append('\'');
        sb.append(", preparedStatementParams=").append(preparedStatementParams);
        sb.append(", timeout=").append(timeout);
        sb.append(", isSync=").append(isSync);
        sb.append('}');
        return sb.toString();
    }
}
