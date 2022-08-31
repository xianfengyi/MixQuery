package com.github.pioneeryi.request;

import java.io.Serializable;

/**
 * ColumnMetaRequest.
 *
 * @author pioneeryi
 * @since 2022/5/17 11:07
 */
public class ColumnMetaRequest implements Serializable {

    private String dbName;

    private String tableName;

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
}
