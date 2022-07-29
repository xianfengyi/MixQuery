package com.github.pioneeryi.service;

import com.github.pioneeryi.core.SqlExecutor;
import com.github.pioneeryi.service.model.QueryResult;
import com.google.common.collect.Lists;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 * QueryService.
 *
 * @author yixianfeng
 * @since 2022/7/29 09:27
 */
@Service
public class QueryService {

    private SqlExecutor sqlExecutor;

    public QueryService(SqlExecutor sqlExecutor) {
        this.sqlExecutor = sqlExecutor;
    }

    public QueryResult query(String sql) throws SQLException {
        List<List<String>> datas = new ArrayList<>();

        try (Connection conn = sqlExecutor.getConnection(sql)) {
            Statement stmt = conn.createStatement();
            ResultSet resultSet = stmt.executeQuery(sql);
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();

            // fill in results
            while (resultSet.next()) {
                List<String> oneRow = Lists.newArrayListWithCapacity(columnCount);
                for (int i = 0; i < columnCount; i++) {
                    oneRow.add((resultSet.getString(i + 1)));
                }
                datas.add(oneRow);
            }
        }
        return new QueryResult(datas);
    }
}
