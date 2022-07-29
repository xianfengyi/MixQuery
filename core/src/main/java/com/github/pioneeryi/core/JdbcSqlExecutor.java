package com.github.pioneeryi.core;

import com.github.pioneeryi.service.SchemaMetaService;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;
import org.apache.calcite.avatica.util.DateTimeUtils;
import org.apache.calcite.config.CalciteConnectionProperty;
import org.apache.calcite.jdbc.CalciteConnection;
import org.apache.calcite.runtime.FlatLists;
import org.apache.calcite.sql.parser.SqlParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 直接执行 SQL.
 *
 * @author pioneeryi
 * @since 2022/5/18 16:49
 */
@Component
public class JdbcSqlExecutor implements SqlExecutor {

    @Autowired
    private SchemaMetaService metaDataService;

    public JdbcSqlExecutor() {
    }

    public JdbcSqlExecutor(SchemaMetaService metaDataService) {
        this.metaDataService = metaDataService;
    }

    @Override
    public Connection getConnection(String sql) throws SQLException {
        String schema = getSchema(sql);
        return createCalciteConnection(schema);
    }

    @Override
    public ResultSet execute(String sql) throws SQLException {
        Connection connection = getConnection(sql);

        if (connection instanceof CalciteConnection) {
            CalciteConnection calciteConnection = (CalciteConnection) connection;

            calciteConnection.getProperties().setProperty(
                    CalciteConnectionProperty.MATERIALIZATIONS_ENABLED.camelName(),
                    Boolean.toString(false));

            calciteConnection.getProperties().setProperty(
                    CalciteConnectionProperty.CREATE_MATERIALIZATIONS.camelName(),
                    Boolean.toString(false));

            if (!calciteConnection.getProperties().containsKey(
                    CalciteConnectionProperty.TIME_ZONE.camelName())) {
                calciteConnection.getProperties().setProperty(
                        CalciteConnectionProperty.TIME_ZONE.camelName(),
                        DateTimeUtils.UTC_ZONE.getID());
            }
        }
        PreparedStatement statement = connection.prepareStatement(sql);
        return statement.executeQuery();
    }

    private String getSchema(String sql) {
        List<String> tableNames = getTableNames(sql);
        List<String> dbNames = tableNames.stream().map(item -> item.split("\\.")[0]).collect(Collectors.toList());

        return metaDataService.constructCalciteSchema(dbNames);
    }

    private List<String> getTableNames(String sql) {
        TableNameExtractor tableNameExtractor = new TableNameExtractor();
        try {
            return tableNameExtractor.parseTableName(sql);
        } catch (SqlParseException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public Connection createCalciteConnection(String json) throws SQLException {
        ConnectionFactory connectionFactory = new MapConnectionFactory(
                ImmutableMap.of("unquotedCasing", "unchanged", "caseSensitive", "true"),
                ImmutableList.of()
        ).with("model", json);

        Connection connection = connectionFactory.createConnection();
        return connection;
    }

    private abstract static class ConnectionFactory {

        public abstract Connection createConnection() throws SQLException;

        public ConnectionFactory with(String property, Object value) {
            throw new UnsupportedOperationException();
        }

        public ConnectionFactory with(ConnectionPostProcessor postProcessor) {
            throw new UnsupportedOperationException();
        }
    }

    private static class MapConnectionFactory extends ConnectionFactory {

        private final ImmutableMap<String, String> map;
        private final ImmutableList<ConnectionPostProcessor> postProcessors;

        private MapConnectionFactory(ImmutableMap<String, String> map,
                                     ImmutableList<ConnectionPostProcessor> postProcessors) {
            this.map = Preconditions.checkNotNull(map);
            this.postProcessors = Preconditions.checkNotNull(postProcessors);
        }

        @Override
        public Connection createConnection() throws SQLException {
            final Properties info = new Properties();
            for (Map.Entry<String, String> entry : map.entrySet()) {
                info.setProperty(entry.getKey(), entry.getValue());
            }

            Connection connection = DriverManager.getConnection("jdbc:calcite:", info);
            for (ConnectionPostProcessor postProcessor : postProcessors) {
                connection = postProcessor.apply(connection);
            }

            return connection;
        }

        @Override
        public ConnectionFactory with(String property, Object value) {
            return new MapConnectionFactory(FlatLists.append(this.map, property, value.toString()), postProcessors);
        }

        @Override
        public ConnectionFactory with(ConnectionPostProcessor postProcessor) {
            ImmutableList.Builder<ConnectionPostProcessor> builder = ImmutableList.builder();
            builder.addAll(postProcessors);
            builder.add(postProcessor);
            return new MapConnectionFactory(map, builder.build());
        }
    }

    public interface ConnectionPostProcessor {

        Connection apply(Connection connection) throws SQLException;
    }
}
