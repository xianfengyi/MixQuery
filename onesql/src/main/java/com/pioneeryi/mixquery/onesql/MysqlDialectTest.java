package com.pioneeryi.mixquery.onesql;

import com.tencent.tedi.onesql.parser.impl.OneSqlParserImpl;
import org.apache.calcite.avatica.util.Casing;
import org.apache.calcite.avatica.util.Quoting;
import org.apache.calcite.sql.SqlNode;
import org.apache.calcite.sql.parser.SqlParseException;
import org.apache.calcite.sql.parser.SqlParser;
import org.junit.Assert;
import org.junit.Test;

public class MysqlDialectTest {

    private SqlParser.Config sqlParserConfig = SqlParser.config()
            .withParserFactory(OneSqlParserImpl.FACTORY)
            .withQuoting(Quoting.BACK_TICK)
            .withUnquotedCasing(Casing.UNCHANGED);

    @Test
    public void testSimpleSql() throws SqlParseException {
        String sql = "select id, name from t_test_table";
        SqlNode sqlNode = parseQuery(sql, sqlParserConfig);
        String expected = "SELECT `id`, `name`\n"
                + "FROM `t_test_table`";
        Assert.assertEquals(expected, sqlNode.toString());
    }

    private SqlNode parseQuery(String sql, SqlParser.Config config)
            throws SqlParseException {
        SqlParser sqlParser = SqlParser.create(sql, config);
        return sqlParser.parseStmt();
    }

}
