package com.github.pioneeryi.request;

import java.io.Serializable;

/**
 * TableMetaRequest.
 *
 * @author pioneeryi
 * @since 2022/5/17 11:07
 */
public class TableMetaRequest implements Serializable {

    private String dbName;

    public TableMetaRequest() {
    }

    public TableMetaRequest(String dbName) {
        this.dbName = dbName;
    }

    public String getDbName() {
        return dbName;
    }
}
