package com.github.pioneeryi.service.model;

import java.util.List;

/**
 * QueryResult.
 *
 * @author yixianfeng
 * @since 2022/7/29 09:27
 */
public class QueryResult {

    private List<List<String>> datas;

    public QueryResult(List<List<String>> datas) {
        this.datas = datas;
    }

    public List<List<String>> getDatas() {
        return datas;
    }

    public void setDatas(List<List<String>> datas) {
        this.datas = datas;
    }
}
