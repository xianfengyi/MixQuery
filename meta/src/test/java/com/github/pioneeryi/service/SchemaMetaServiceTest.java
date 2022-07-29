package com.github.pioneeryi.service;

import com.github.pioneeryi.domain.DatasourceType;
import com.github.pioneeryi.gateway.MemorySchemaRepo;
import com.github.pioneeryi.model.AddDatasourceCmd;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.Test;

public class SchemaMetaServiceTest {

    private SchemaMetaService metaDataService = new SchemaMetaService(new MemorySchemaRepo());

    @Test
    public void testConstructCalciteSchema() {
        Map<String, String> prop = new LinkedHashMap<>();
        prop.put("jdbcDriver", "com.mysql.cj.jdbc.Driver");
        prop.put("jdbcUser", "xxx");
        prop.put("jdbcPassword", "xxx");
        prop.put("jdbcUrl", "jdbc:mysql://localhost:3306/xxx");
        AddDatasourceCmd cmd1 = new AddDatasourceCmd("db1", DatasourceType.MYSQL, prop);
        metaDataService.addDatasource(cmd1);
        AddDatasourceCmd cmd2 = new AddDatasourceCmd("db2", DatasourceType.MYSQL, prop);
        metaDataService.addDatasource(cmd2);

        String schema = metaDataService.constructCalciteSchema(Arrays.asList("db1", "db2"));

        String expected = "inline:{\"defaultSchema\":\"db1\",\"schemas\":"
                + "[{\"factory\":\"org.apache.calcite.adapter.jdbc.JdbcSchema$Factory\","
                + "\"name\":\"db1\",\"type\":\"custom\","
                + "\"operand\":{\"jdbcDriver\":\"com.mysql.cj.jdbc.Driver\","
                + "\"jdbcUser\":\"xxx\",\"jdbcPassword\":\"xxx\","
                + "\"jdbcUrl\":\"jdbc:mysql://localhost:3306/xxx\"}},"
                + "{\"factory\":\"org.apache.calcite.adapter.jdbc.JdbcSchema$Factory\","
                + "\"name\":\"db2\",\"type\":\"custom\","
                + "\"operand\":{\"jdbcDriver\":\"com.mysql.cj.jdbc.Driver\","
                + "\"jdbcUser\":\"xxx\",\"jdbcPassword\":\"xxx\","
                + "\"jdbcUrl\":\"jdbc:mysql://localhost:3306/xxx\"}}],"
                + "\"version\":\"1.0\"}";
        Assert.assertEquals(expected, schema);
    }
}