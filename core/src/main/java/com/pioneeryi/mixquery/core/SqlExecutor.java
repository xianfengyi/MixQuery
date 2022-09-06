package com.pioneeryi.mixquery.core;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * SQL 执行器.
 *
 * @author yixianfeng
 * @since 2022/7/29 09:30
 */
public interface SqlExecutor {

    Connection getConnection(String sql) throws SQLException;

    /**
     * 执行 SQL
     *
     * @param sql SQL 语句
     *
     * @return 执行结果
     */
    ResultSet execute(String sql) throws SQLException;
}
