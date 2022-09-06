package com.pioneeryi.mixquery.server.request;

import java.io.Serializable;

/**
 * TableTypeRequest.
 *
 * @author pioneeryi
 * @since 2022/5/17 17:22
 */
public class TableTypeRequest implements Serializable {

    private String dbName;

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }
}
