package com.github.pioneeryi.application.model;

import java.util.List;

public class QueryResult {

    private List<SelectedColumnMeta> columnMetas;

    private List<List<String>> results;

    public QueryResult() {
    }

    public QueryResult(List<SelectedColumnMeta> columnMetas, List<List<String>> results) {
        this.columnMetas = columnMetas;
        this.results = results;
    }

    public List<SelectedColumnMeta> getColumnMetas() {
        return columnMetas;
    }

    public void setColumnMetas(List<SelectedColumnMeta> columnMetas) {
        this.columnMetas = columnMetas;
    }

    public List<List<String>> getResults() {
        return results;
    }

    public void setResults(List<List<String>> results) {
        this.results = results;
    }

    @Override
    public String toString() {
        return "QueryResult{" +
                "columnMetas=" + columnMetas +
                ", results=" + results +
                '}';
    }
}
