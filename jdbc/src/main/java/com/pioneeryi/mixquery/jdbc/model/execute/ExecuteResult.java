package com.pioneeryi.mixquery.jdbc.model.execute;

import java.util.List;

/**
 * 执行查询结果体
 */
public class ExecuteResult {

    private List<ColumnMeta> columnMetas;

    private List<List<String>> results;

    private String queryKey;

    private String queryState;

    private QueryInfo queryInfo;

    public void setColumnMetas(List<ColumnMeta> columnMetas) {
        this.columnMetas = columnMetas;
    }

    public void setResults(List<List<String>> results) {
        this.results = results;
    }

    public void setQueryKey(String queryKey) {
        this.queryKey = queryKey;
    }

    public void setQueryState(String queryState) {
        this.queryState = queryState;
    }

    public void setQueryInfo(QueryInfo queryInfo) {
        this.queryInfo = queryInfo;
    }

    public List<ColumnMeta> getColumnMetas() {
        return columnMetas;
    }

    public List<List<String>> getResults() {
        return results;
    }

    public String getQueryKey() {
        return queryKey;
    }

    public QueryInfo getQueryInfo() {
        return queryInfo;
    }

    public String getQueryState() {
        return queryState;
    }

    @Override
    public String toString() {
        return new StringBuffer()
                .append("ExecuteResult{")
                .append("columnMetas=")
                .append(columnMetas)
                .append(", results=")
                .append(results)
                .append(", queryKey=")
                .append(queryKey).append('\'')
                .append(", queryState='")
                .append(queryState).append('\'')
                .append(", queryInfo=")
                .append(queryInfo)
                .append('}')
                .toString();
    }
}
