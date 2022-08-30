package com.github.pioneeryi;

import org.apache.calcite.avatica.*;
import org.apache.calcite.avatica.remote.TypedValue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

public class MixqMeta extends MetaImpl {

    public MixqMeta(AvaticaConnection connection) {
        super(connection);
    }

    @Override
    public StatementHandle prepare(ConnectionHandle connectionHandle, String s, long l) {
        return null;
    }

    @Override
    public ExecuteResult prepareAndExecute(StatementHandle statementHandle, String s, long l, PrepareCallback prepareCallback) throws NoSuchStatementException {
        return null;
    }

    @Override
    public ExecuteResult prepareAndExecute(StatementHandle statementHandle, String s, long l, int i, PrepareCallback prepareCallback) throws NoSuchStatementException {
        return null;
    }

    @Override
    public ExecuteBatchResult prepareAndExecuteBatch(StatementHandle statementHandle, List<String> list) throws NoSuchStatementException {
        return null;
    }

    @Override
    public ExecuteBatchResult executeBatch(StatementHandle statementHandle, List<List<TypedValue>> list) throws NoSuchStatementException {
        return null;
    }

    @Override
    public Frame fetch(StatementHandle statementHandle, long l, int i) throws NoSuchStatementException, MissingResultsException {
        return null;
    }

    @Override
    public ExecuteResult execute(StatementHandle statementHandle, List<TypedValue> list, long l) throws NoSuchStatementException {
        return null;
    }

    @Override
    public ExecuteResult execute(StatementHandle statementHandle, List<TypedValue> list, int i) throws NoSuchStatementException {
        return null;
    }

    @Override
    public void closeStatement(StatementHandle statementHandle) {

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

    public static interface NamedWithChildren extends Named {
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
