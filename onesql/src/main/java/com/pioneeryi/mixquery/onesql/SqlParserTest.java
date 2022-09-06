package com.pioneeryi.mixquery.onesql;

import org.apache.calcite.avatica.util.Casing;
import org.apache.calcite.avatica.util.Quoting;
import org.apache.calcite.sql.SqlNode;
import org.apache.calcite.sql.parser.SqlParseException;
import org.apache.calcite.sql.parser.SqlParser;
import org.apache.calcite.sql.parser.impl.SqlParserImpl;
import org.apache.calcite.sql.validate.SqlConformanceEnum;
import org.apache.calcite.tools.FrameworkConfig;
import org.apache.calcite.tools.Frameworks;
import org.junit.Test;

public class SqlParserTest {

    private final FrameworkConfig config = Frameworks.newConfigBuilder()
            .parserConfig(SqlParser.configBuilder()
                    .setParserFactory(SqlParserImpl.FACTORY)
                    .setCaseSensitive(false)
                    .setQuoting(Quoting.BACK_TICK)
                    .setQuotedCasing(Casing.TO_UPPER)
                    .setUnquotedCasing(Casing.TO_UPPER)
                    .setConformance(SqlConformanceEnum.ORACLE_12)
                    .build())
            .build();

    @Test
    public void testCalciteSqlParser() throws SqlParseException {
        String sql = "select ids, name from test where id < 5 and name = 'pioneeryi'";
        SqlParser parser = SqlParser.create(sql, config.getParserConfig());
        SqlNode sqlNode = parser.parseStmt();
        System.out.println(sqlNode.toString());
    }
}
