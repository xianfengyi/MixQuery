package com.github.pioneeryi;

import com.github.pioneeryi.client.IRemoteClient;
import com.github.pioneeryi.constants.MetaColumnLabel;
import com.github.pioneeryi.constants.MetaTableLabel;
import com.github.pioneeryi.constants.MetaTableTypeLabel;
import com.github.pioneeryi.exception.MixqJdbcException;
import org.apache.calcite.avatica.*;
import org.apache.calcite.avatica.remote.TypedValue;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Implementation of Avatica interface
 */
public class MixqMeta extends MetaImpl {

    public MixqMeta(AvaticaConnection connection) {
        super(connection);
    }

    private MixqConnection getConnection() {
        return (MixqConnection) connection;
    }

    private IRemoteClient getClient() {
        return ((MixqConnection) connection).getRemoteClient();
    }

    @Override
    public StatementHandle prepare(ConnectionHandle ch, String sql, long maxRowCount) {
        StatementHandle result = super.createStatement(ch);
        result.signature = getConnection().createSignature(sql);
        return result;
    }

    @Override
    public ExecuteResult prepareAndExecute(StatementHandle statementHandle, String sql, long maxRowCount, PrepareCallback prepareCallback) throws NoSuchStatementException {
        return getPreparedExecuteResult(statementHandle, sql, prepareCallback);
    }

    @Override
    public ExecuteResult prepareAndExecute(StatementHandle sh, String sql, long maxRowCount, int maxRowsInFirstFrame, PrepareCallback callback) throws NoSuchStatementException {
        return getPreparedExecuteResult(sh, sql, callback);
    }

    private ExecuteResult getPreparedExecuteResult(StatementHandle sh, String sql, PrepareCallback callback) {
        try {
            synchronized (callback.getMonitor()) {
                callback.clear();
                sh.signature = getConnection().createSignature(sql);
                callback.assign(sh.signature, null, -1);
            }
            callback.execute();
            final MetaResultSet metaResultSet = MetaResultSet.create(sh.connectionId, sh.id, false, sh.signature, null);
            return new ExecuteResult(Collections.singletonList(metaResultSet));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ExecuteBatchResult prepareAndExecuteBatch(StatementHandle statementHandle, List<String> list) throws NoSuchStatementException {
        return new ExecuteBatchResult(new long[]{});
    }

    @Override
    public ExecuteBatchResult executeBatch(StatementHandle statementHandle, List<List<TypedValue>> list) throws NoSuchStatementException {
        return new ExecuteBatchResult(new long[]{});
    }

    @Override
    public Frame fetch(StatementHandle statementHandle, long l, int i) throws NoSuchStatementException, MissingResultsException {
        return null;
    }

    @Override
    public ExecuteResult execute(StatementHandle sh, List<TypedValue> parameterValues, long maxRowCount) throws NoSuchStatementException {
        final MetaResultSet metaResultSet = MetaResultSet.create(sh.connectionId, sh.id, false, sh.signature, null);
        return new ExecuteResult(Collections.singletonList(metaResultSet));
    }

    @Override
    public ExecuteResult execute(StatementHandle sh, List<TypedValue> parameterValues, int maxRowsInFirstFrame) throws NoSuchStatementException {
        final MetaResultSet metaResultSet = MetaResultSet.create(sh.connectionId, sh.id, false, sh.signature, null);
        return new ExecuteResult(Collections.singletonList(metaResultSet));
    }

    @Override
    public void closeStatement(StatementHandle statementHandle) {
        // nothing to do
    }

    @Override
    public boolean syncResults(StatementHandle statementHandle, QueryState queryState, long l) throws NoSuchStatementException {
        return false;
    }

    @Override
    public void commit(ConnectionHandle connectionHandle) {
    }

    @Override
    public void rollback(ConnectionHandle connectionHandle) {
    }

    @Override
    public MetaResultSet getTableTypes(ConnectionHandle ch) {
        try {
            List<MetaTableType> tableTypes = getClient().getTableTypes();
            return createResultSet(tableTypes, MetaTableType.class, MetaTableTypeLabel.getNames());
        } catch (Exception e) {
            throw new MixqJdbcException(e.getMessage());
        }
    }

    @Override
    public MetaResultSet getCatalogs(ConnectionHandle ch) {
        List<MetaCatalog> catalogs = getClient().getCatalogs();
        return createResultSet(catalogs, MetaCatalog.class, "TABLE_CAT");
    }

    @Override
    public MetaResultSet getSchemas(ConnectionHandle ch, String catalog, Pat schemaPattern) {
        List<MetaSchema> schemas = getClient().getSchemas(catalog, schemaPattern.s);
        return createResultSet(schemas, MetaSchema.class, "TABLE_SCHEM", "TABLE_CATALOG");
    }

    @Override
    public MetaResultSet getTables(ConnectionHandle ch, String catalog, Pat schemaPattern, Pat tableNamePattern,
                                   List<String> typeList) {
        try {
            List<MetaTable> tables = getClient().getTables(catalog, schemaPattern.s, tableNamePattern.s, typeList);
            return createResultSet(tables, MetaTable.class, MetaTableLabel.getNames());
        } catch (Exception e) {
            throw new MixqJdbcException(e.getMessage());
        }
    }

    @Override
    public MetaResultSet getColumns(ConnectionHandle ch, String catalog, Pat schemaPattern, Pat tableNamePattern,
                                    Pat columnNamePattern) {
        try {
            List<MetaColumn> metaColumns = getClient()
                    .getColumns(catalog, schemaPattern.s, tableNamePattern.s, columnNamePattern.s);
            return createResultSet(metaColumns, MetaColumn.class, MetaColumnLabel.getNames());

        } catch (Exception e) {
            throw new MixqJdbcException(e.getMessage());
        }
    }

    private <T> MetaResultSet createResultSet(List<T> results, Class<T> resultClass,
                                              String... resultFieldLabels) {
        //生成resultSet中列的标记
        Signature signature = createSignature(resultClass, resultFieldLabels);

        StatementHandle sh = createStatement(getConnection().handle);

        //生成resultSet中行的集合
        Frame frame = new Frame(0, true, new ArrayList<>(results));

        return MetaResultSet.create(getConnection().id, sh.id, true, signature, frame);
    }

    private <T> Signature createSignature(Class<T> clazz, String... fieldLabels) {

        //获取结果类型的所有字段
        final Field[] fields = clazz.getFields();

        if (fields.length != fieldLabels.length) {
            throw new MixqJdbcException("Illegal argument error.Field length is not match with label length");
        }

        final List<String> fieldNames = new ArrayList<>();
        final List<ColumnMetaData> columns = new ArrayList<>();

        for (int i = 0; i < fields.length; i++) {
            columns.add(columnMetaData(fieldLabels[i], i, fields[i].getType(), true));
            fieldNames.add(fields[i].getName());
        }

        CursorFactory cursorFactory = CursorFactory.record(clazz, Arrays.asList(fields), fieldNames);
        Signature signature = new Signature(columns, "", null, Collections.emptyMap(), cursorFactory,
                StatementType.SELECT);

        return signature;
    }

    public interface NamedWithChildren extends Named {
        List<? extends NamedWithChildren> getChildren();
    }

    public static class MixMetaProject implements NamedWithChildren {
        public final String projectName;
        public final List<MixMetaCatalog> catalogs;

        public MixMetaProject(String projectName, List<MixMetaCatalog> catalogs) {
            this.projectName = projectName;
            this.catalogs = catalogs;
        }

        @SuppressWarnings("unchecked")
        public List<MixMetaSchema> getSchemas(String catalog, Pat schemaPattern) {
            return (List<MixMetaSchema>) searchByPatterns(this, Pat.of(catalog), schemaPattern);
        }

        @SuppressWarnings("unchecked")
        public List<MixMetaTable> getTables(String catalog, Pat schemaPattern, Pat tableNamePattern, List<String> typeList) {
            return (List<MixMetaTable>) searchByPatterns(this, Pat.of(catalog), schemaPattern, tableNamePattern);
        }

        @SuppressWarnings("unchecked")
        public List<MixMetaColumn> getColumns(String catalog, Pat schemaPattern, Pat tableNamePattern, Pat columnNamePattern) {
            return (List<MixMetaColumn>) searchByPatterns(this, Pat.of(catalog), schemaPattern, tableNamePattern, columnNamePattern);
        }

        @Override
        public String getName() {
            return projectName;
        }

        @Override
        public List<? extends NamedWithChildren> getChildren() {
            return catalogs;
        }
    }

    public static class MixMetaCatalog implements NamedWithChildren {
        public final String tableCat;
        public final List<MixMetaSchema> schemas;

        public MixMetaCatalog(String tableCatalog, List<MixMetaSchema> schemas) {
            this.tableCat = tableCatalog;
            this.schemas = schemas;
        }

        @Override
        public String getName() {
            return tableCat;
        }

        @Override
        public List<? extends NamedWithChildren> getChildren() {
            return schemas;
        }
    }

    public static class MixMetaSchema extends MetaSchema implements NamedWithChildren {
        public final List<MixMetaTable> tables;

        public MixMetaSchema(String tableCatalog, String tableSchem, List<MixMetaTable> tables) {
            super(tableCatalog, tableSchem);
            this.tables = tables;
        }

        @Override
        public List<? extends NamedWithChildren> getChildren() {
            return tables;
        }
    }

    public static class MixMetaTable extends MetaTable implements NamedWithChildren {
        public final List<MixMetaColumn> columns;

        public MixMetaTable(String tableCat, String tableSchem, String tableName, String tableType, List<MixMetaColumn> columns) {
            super(tableCat, tableSchem, tableName, tableType);
            this.columns = columns;
        }

        @Override
        public List<? extends NamedWithChildren> getChildren() {
            return columns;
        }
    }

    public static class MixMetaColumn extends MetaColumn implements NamedWithChildren {

        public MixMetaColumn(String tableCat, String tableSchem, String tableName, String columnName, int dataType, String typeName, int columnSize, Integer decimalDigits, int numPrecRadix, int nullable, int charOctetLength, int ordinalPosition, String isNullable) {
            super(tableCat, tableSchem, tableName, columnName, dataType, typeName, columnSize, decimalDigits, numPrecRadix, nullable, charOctetLength, ordinalPosition, isNullable);
        }

        @Override
        public List<NamedWithChildren> getChildren() {
            return Collections.<NamedWithChildren>emptyList();
        }
    }

    public static List<? extends NamedWithChildren> searchByPatterns(NamedWithChildren parent, Pat... patterns) {
        assert patterns != null && patterns.length > 0;

        List<? extends NamedWithChildren> children = findChildren(parent, patterns[0]);
        if (patterns.length == 1) {
            return children;
        } else {
            List<NamedWithChildren> result = new ArrayList<NamedWithChildren>();
            Pat[] subPatterns = Arrays.copyOfRange(patterns, 1, patterns.length);
            for (NamedWithChildren c : children) {
                result.addAll(searchByPatterns(c, subPatterns));
            }
            return result;
        }
    }

    private static List<? extends NamedWithChildren> findChildren(NamedWithChildren parent, Pat pattern) {
        if (null == pattern.s || pattern.s.equals("%")) {
            return parent.getChildren();
        }

        List<NamedWithChildren> result = new ArrayList<NamedWithChildren>();
        Pattern regex = likeToRegex(pattern);

        for (NamedWithChildren c : parent.getChildren()) {
            if (regex.matcher(c.getName()).matches()) {
                result.add(c);
            }
        }
        return result;
    }

    /**
     * Converts a LIKE-style pattern (where '%' represents a wild-card,
     * escaped using '\') to a Java regex.
     */
    private static Pattern likeToRegex(Pat pattern) {
        StringBuilder buf = new StringBuilder("^");
        char[] charArray = pattern.s.toCharArray();
        int slash = -2;
        for (int i = 0; i < charArray.length; i++) {
            char c = charArray[i];
            if (slash == i - 1) {
                buf.append('[').append(c).append(']');
            } else {
                switch (c) {
                    case '\\':
                        slash = i;
                        break;
                    case '%':
                        buf.append(".*");
                        break;
                    case '[':
                        buf.append("\\[");
                        break;
                    case ']':
                        buf.append("\\]");
                        break;
                    default:
                        buf.append('[').append(c).append(']');
                }
            }
        }
        buf.append("$");

        return Pattern.compile(buf.toString());
    }

}
