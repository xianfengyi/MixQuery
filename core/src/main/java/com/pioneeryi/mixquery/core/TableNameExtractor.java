package com.pioneeryi.mixquery.core;

import org.apache.calcite.avatica.util.Casing;
import org.apache.calcite.avatica.util.Quoting;
import org.apache.calcite.sql.*;
import org.apache.calcite.sql.parser.SqlParseException;
import org.apache.calcite.sql.parser.SqlParser;
import org.apache.calcite.sql.util.SqlVisitor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
        if (sqlCall.getOperator() instanceof SqlAsOperator && (sqlCall).getOperandList().size() == 2) {
            List<SqlNode> operandList = (sqlCall).getOperandList();
            if (operandList.get(0) instanceof SqlIdentifier && operandList.get(1) instanceof SqlIdentifier) {
                operandList.get(0).accept(this);
            } else if (!(operandList.get(0) instanceof SqlIdentifier)) {
                operandList.get(0).accept(this);
            }
        } else {
            List<SqlNode> operandList = (sqlCall).getOperandList();
            operandList.forEach((node) -> {
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
