package com.github.pioneeryi.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.calcite.avatica.util.Casing;
import org.apache.calcite.avatica.util.Quoting;
import org.apache.calcite.sql.SqlAsOperator;
import org.apache.calcite.sql.SqlBasicCall;
import org.apache.calcite.sql.SqlCall;
import org.apache.calcite.sql.SqlDataTypeSpec;
import org.apache.calcite.sql.SqlDynamicParam;
import org.apache.calcite.sql.SqlIdentifier;
import org.apache.calcite.sql.SqlIntervalQualifier;
import org.apache.calcite.sql.SqlJoin;
import org.apache.calcite.sql.SqlLiteral;
import org.apache.calcite.sql.SqlNode;
import org.apache.calcite.sql.SqlNodeList;
import org.apache.calcite.sql.SqlOrderBy;
import org.apache.calcite.sql.SqlSelect;
import org.apache.calcite.sql.SqlWith;
import org.apache.calcite.sql.SqlWithItem;
import org.apache.calcite.sql.parser.SqlParseException;
import org.apache.calcite.sql.parser.SqlParser;
import org.apache.calcite.sql.util.SqlVisitor;

/**
 * Parse SQL line and extract the table name.
 *
 * @author pioneeryi
 * @since 2022/5/5 10:58
 */
public class TableNameExtractor implements SqlVisitor<List<String>> {

    private Set<String> withTempTables = new HashSet<>();
    private List<String> tableNames = new ArrayList<>();

    private SqlParser.Config config = SqlParser.configBuilder()
            .setQuoting(Quoting.BACK_TICK)
            .setQuotedCasing(Casing.UNCHANGED)
            .setUnquotedCasing(Casing.UNCHANGED)
            .build();

    @Override
    public List<String> visit(SqlLiteral sqlLiteral) {
        return tableNames;
    }

    @Override
    public List<String> visit(SqlCall sqlCall) {
        if (sqlCall instanceof SqlSelect) {
            ((SqlSelect) sqlCall).getSelectList().accept(this);
            if (((SqlSelect) sqlCall).getFrom() != null) {
                ((SqlSelect) sqlCall).getFrom().accept(this);
            }
            if (((SqlSelect) sqlCall).getWhere() instanceof SqlBasicCall) {
                List<SqlNode> operands = ((SqlBasicCall) ((SqlSelect) sqlCall).getWhere()).getOperandList();
                for (SqlNode operand : operands) {
                    if (!(operand instanceof SqlIdentifier)) {
                        operand.accept(this);
                    }
                }
            }
        }

        if (sqlCall instanceof SqlWith) {
            ((SqlWith) sqlCall).withList.accept(this);
            ((SqlWith) sqlCall).body.accept(this);
        }

        if (sqlCall instanceof SqlJoin) {
            ((SqlJoin) sqlCall).getLeft().accept(this);
            ((SqlJoin) sqlCall).getRight().accept(this);
        }

        if (sqlCall instanceof SqlBasicCall) {
            visitBasicCall((SqlBasicCall) sqlCall);
        }

        if (sqlCall instanceof SqlOrderBy) {
            ((SqlOrderBy) sqlCall).query.accept(this);
        }

        return tableNames;
    }

    private void visitBasicCall(SqlBasicCall sqlCall) {
        if (sqlCall.getOperator() instanceof SqlAsOperator && (sqlCall).operands.length == 2) {
            if ((sqlCall).operands[0] instanceof SqlIdentifier
                    && (sqlCall).operands[1] instanceof SqlIdentifier) {
                (sqlCall).operands[0].accept(this);
            } else if (!((sqlCall).operands[0] instanceof SqlIdentifier)) {
                (sqlCall).operands[0].accept(this);
            }
        } else {
            Arrays.stream((sqlCall).operands).forEach((node) -> {
                if (node instanceof SqlSelect) {
                    if (((SqlSelect) node).getFrom() != null) {
                        ((SqlSelect) node).getFrom().accept(this);
                    }
                }

                if (node instanceof SqlBasicCall) {
                    visitBasicCall((SqlBasicCall) node);
                }
            });
        }
    }

    @Override
    public List<String> visit(SqlNodeList sqlNodeList) {
        sqlNodeList.iterator().forEachRemaining((entry) -> {
            if (entry instanceof SqlSelect) {
                entry.accept(this);
            } else if (entry instanceof SqlWithItem) {
                //TODO caution db.table query
                List<String> names = ((SqlWithItem) entry).name.names;
                if (!names.isEmpty()) {
                    withTempTables.add(names.get(names.size() - 1));
                }
                ((SqlWithItem) entry).query.accept(this);
            } else if (entry instanceof SqlBasicCall) {
                if (((SqlBasicCall) entry).operand(0) instanceof SqlCall) {
                    entry.accept(this);
                }
                String kind = ((SqlBasicCall) entry).getOperator().getName();
                if ("AS".equalsIgnoreCase(kind)
                        && ((SqlBasicCall) entry).operand(0) instanceof SqlSelect) {
                    entry.accept(this);
                }
            }
        });
        return tableNames;
    }

    @Override
    public List<String> visit(SqlIdentifier sqlIdentifier) {
        if (sqlIdentifier.names.size() == 0) {
            return tableNames;
        }

        tableNames.add(sqlIdentifier.toString());
        return tableNames;
    }

    @Override
    public List<String> visit(SqlDataTypeSpec sqlDataTypeSpec) {
        return tableNames;
    }

    @Override
    public List<String> visit(SqlDynamicParam sqlDynamicParam) {
        return tableNames;
    }

    @Override
    public List<String> visit(SqlIntervalQualifier sqlIntervalQualifier) {
        return tableNames;
    }

    /**
     * Get table names from sql.
     *
     * @param sql SQL line
     *
     * @return List of TableName
     */
    public List<String> parseTableName(String sql) throws SqlParseException {
        SqlParser parser = SqlParser.create(sql, config);
        SqlNode sqlNode = parser.parseQuery();
        return validateTableName(sqlNode.accept(this));
    }

    private List<String> validateTableName(List<String> tableNames) {
        for (String tableName : tableNames) {
            if (tableName.split("\\.", -1).length > 3) {
                throw new RuntimeException("Psql only support structure like dbName.tableName,"
                        + " and there is a unsupported tableName here: " + tableName);
            }
        }
        tableNames.removeIf((item) -> withTempTables.contains(item));
        return tableNames;
    }
}
