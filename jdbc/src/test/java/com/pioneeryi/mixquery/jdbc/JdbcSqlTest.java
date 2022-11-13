package com.pioneeryi.mixquery.jdbc;

import org.junit.Test;

import java.sql.*;

public class JdbcSqlTest {

    @Test
    public void testMixQuery_simpleSQL() throws ClassNotFoundException {
        Class.forName("com.pioneeryi.mixquery.jdbc.Driver");

        try (Connection conn = DriverManager.getConnection("jdbc:mixquery:localhost:9093/mixquery")) {
            // 查询数据
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select * from db1.student");
            printRs(rs);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    @Test
    public void testMixQuery_joinSQL() throws ClassNotFoundException {
        Class.forName("com.pioneeryi.mixquery.jdbc.Driver");

        try (Connection conn = DriverManager.getConnection("jdbc:mixquery:localhost:9093/mixquery")) {
            // 查询数据
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select student.*,score.grade from db1.student as student join db2.score as score on student.id=score.student_id");
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
