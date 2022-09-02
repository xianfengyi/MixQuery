package com.github.pioneeryi;

import org.junit.Test;

import java.sql.*;

public class JdbcSqlTest {

    @Test
    public void testTediSqlQuery() throws ClassNotFoundException {
        Class.forName("com.github.pioneeryi.Driver");

        try (Connection conn = DriverManager.getConnection("jdbc:mixquery:localhost:9093/test")) {
            // 查询数据
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select * from student");
            printRs(rs);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    private void printRs(ResultSet rs) throws SQLException {
        int count = rs.getMetaData().getColumnCount();
        while (rs.next()) {
            StringBuilder sb = new StringBuilder();
            for (int i = 1; i <= count; i++) {
                String label = rs.getMetaData().getColumnLabel(i);
                Object val = rs.getObject(i);
                String value = "null";
                if (val != null) {
                    value = val.toString();
                }
                sb.append(label + ":" + value);
                if (i != count) {
                    sb.append(" , ");
                }
            }
            System.out.println(sb);
        }
    }
}
