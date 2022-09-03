package com.github.pioneeryi.application;

import com.github.pioneeryi.application.model.*;
import com.github.pioneeryi.core.SqlExecutor;
import com.github.pioneeryi.gateway.MetaDataSourceInfo;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class QueryService {

    @Autowired
    private SqlExecutor sqlExecutor;

    @Autowired
    private MetaDataSourceInfo metaDataSourceInfo;

    public QueryService() {
    }

    public QueryService(SqlExecutor sqlExecutor) {
        this.sqlExecutor = sqlExecutor;
    }

    public QueryResult query(String sql) throws SQLException {

        List<SelectedColumnMeta> columnMetas = new ArrayList<>();

        List<List<String>> results = new ArrayList<>();

        try (Connection conn = sqlExecutor.getConnection(sql)) {
            Statement stmt = conn.createStatement();
            ResultSet resultSet = stmt.executeQuery(sql);
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();

            // Fill in selected column meta
            for (int i = 1; i <= columnCount; ++i) {
                columnMetas.add(new SelectedColumnMeta(metaData.isAutoIncrement(i), metaData.isCaseSensitive(i),
                        metaData.isSearchable(i), metaData.isCurrency(i), metaData.isNullable(i), metaData.isSigned(i),
                        metaData.getColumnDisplaySize(i), metaData.getColumnLabel(i), metaData.getColumnName(i),
                        metaData.getSchemaName(i), metaData.getCatalogName(i), metaData.getTableName(i),
                        metaData.getPrecision(i), metaData.getScale(i), metaData.getColumnType(i),
                        metaData.getColumnTypeName(i), metaData.isReadOnly(i), metaData.isWritable(i),
                        metaData.isDefinitelyWritable(i)));
            }

            // fill in results
            while (resultSet.next()) {
                List<String> oneRow = Lists.newArrayListWithCapacity(columnCount);
                for (int i = 0; i < columnCount; i++) {
                    oneRow.add((resultSet.getString(i + 1)));
                }

                results.add(oneRow);
            }
        }
        return new QueryResult(columnMetas, results);
    }

    public List<String> queryCatalogs(String dbName) throws SQLException {
        List<String> catalogs = new ArrayList<>();
        try (Connection conn = getConnection()) {
            DatabaseMetaData metaData = conn.getMetaData();
            ResultSet JDBCTableMeta = metaData.getTables(dbName, null, null, null);
            while (JDBCTableMeta.next()) {
                String catalogName = JDBCTableMeta.getString(1);
                catalogs.add(catalogName);
            }
        }
        return catalogs;
    }

    public List<SchemaMeta> querySchemaMetas(String dbName) throws SQLException {
        List<SchemaMeta> schemaMetas = new ArrayList<>();
        try (Connection conn = getConnection()) {
            DatabaseMetaData metaData = conn.getMetaData();
            ResultSet JDBCTableMeta = metaData.getTables(dbName, null, null, null);
            while (JDBCTableMeta.next()) {
                String catalogName = JDBCTableMeta.getString(1);
                String schemaName = JDBCTableMeta.getString(2);
                schemaMetas.add(new SchemaMeta(catalogName, schemaName));
            }
        }
        return schemaMetas;
    }

    /**
     * 查询表的元数据信息.
     *
     * @return 表元数据信息列表
     * @throws SQLException 如果获取表元信息失败，会抛出此异常
     */
    public List<TableMeta> queryTableMetas(String dbName) throws SQLException {
        List<TableMeta> tableMetas = new ArrayList<>();
        try (Connection conn = getConnection()) {
            DatabaseMetaData metaData = conn.getMetaData();
            ResultSet JDBCTableMeta = metaData.getTables(dbName, null, null, null);
            while (JDBCTableMeta.next()) {
                String catalogName = JDBCTableMeta.getString(1);
                String schemaName = JDBCTableMeta.getString(2);

                TableMeta tableMeta = new TableMeta();
                tableMeta.setTableCat(StringUtils.defaultString(catalogName, "defaultCatalog"));
                tableMeta.setTableSchema(StringUtils.defaultString(schemaName, "defaultSchema"));
                tableMeta.setTableName(JDBCTableMeta.getString(3));
                tableMeta.setTableType(JDBCTableMeta.getString(4));
                tableMeta.setRemarks(JDBCTableMeta.getString(5));
                tableMeta.setTypeCat(JDBCTableMeta.getString(6));
                tableMeta.setTypeSchema(JDBCTableMeta.getString(7));
                tableMeta.setTypeName(JDBCTableMeta.getString(8));
                tableMeta.setSelfReferencingColName(JDBCTableMeta.getString(9));
                tableMeta.setRefGeneration(JDBCTableMeta.getString(10));
                tableMetas.add(tableMeta);
            }
        }
        return tableMetas;
    }

    public List<String> queryTableTypes(String dbName) throws SQLException {
        List<String> tableTypes = new ArrayList<>();
        try (Connection conn = getConnection()) {
            DatabaseMetaData metaData = conn.getMetaData();
            ResultSet JDBCTableMeta = metaData.getTables(dbName, null, null, null);
            while (JDBCTableMeta.next()) {
                tableTypes.add(JDBCTableMeta.getString(4));
            }
        }
        return tableTypes;
    }

    public List<ColumnMeta> queryColumnMetas(String dbName, String tableName) throws SQLException {
        List<ColumnMeta> columnMetas = new ArrayList<>();
        try (Connection conn = getConnection()) {
            DatabaseMetaData metaData = conn.getMetaData();

            ResultSet columnMeta = metaData.getColumns(dbName, null, tableName, null);

            while (columnMeta.next()) {
                String catalogName = columnMeta.getString(1);
                String schemaName = columnMeta.getString(2);

                ColumnMeta colmnMeta = new ColumnMeta();
                colmnMeta.setTableCat(StringUtils.defaultString(catalogName, "defaultCatalog"));
                colmnMeta.setTableSchema(StringUtils.defaultString(schemaName, "defaultSchema"));
                colmnMeta.setTableName(columnMeta.getString(3));
                colmnMeta.setColumnName(columnMeta.getString(4));
                colmnMeta.setDataType(columnMeta.getInt(5));
                colmnMeta.setTypeName(columnMeta.getString(6));
                colmnMeta.setColumnSize(columnMeta.getInt(7));
                colmnMeta.setBufferLength(getInt(columnMeta.getString(8)));
                colmnMeta.setDecimalDigits(columnMeta.getInt(9));
                colmnMeta.setNumPrecRadix(columnMeta.getInt(10));
                colmnMeta.setNullable(columnMeta.getInt(11));
                colmnMeta.setRemarks(columnMeta.getString(12));
                colmnMeta.setColumnDef(columnMeta.getString(13));
                colmnMeta.setSqlDataType(getInt(columnMeta.getString(14)));
                colmnMeta.setSqlDatetimeSub(getInt(columnMeta.getString(15)));
                colmnMeta.setCharOctetLength(columnMeta.getInt(16));
                colmnMeta.setOrdinalPosition(columnMeta.getInt(17));
                colmnMeta.setIsNullable(columnMeta.getString(18));
                colmnMeta.setScopeCatalog(columnMeta.getString(19));
                colmnMeta.setScopeSchema(columnMeta.getString(20));
                colmnMeta.setScopeTable(columnMeta.getString(21));
                colmnMeta.setSourceDataType(getShort(columnMeta.getString(22)));
                colmnMeta.setIsAutoincrement(columnMeta.getString(23));
                columnMetas.add(colmnMeta);
            }
        }
        return columnMetas;
    }

    private Connection getConnection() throws SQLException {
        try {
            Class.forName(metaDataSourceInfo.getDriver());
        } catch (ClassNotFoundException e) {
            throw new SQLException("driver not found");
        }
        String url = metaDataSourceInfo.getJdbcUrl();
        return DriverManager.getConnection(url, metaDataSourceInfo.getUsername(), metaDataSourceInfo.getPassword());
    }

    protected int getInt(String content) {
        try {
            return Integer.parseInt(content);
        } catch (Exception e) {
            return -1;
        }
    }

    protected short getShort(String content) {
        try {
            return Short.parseShort(content);
        } catch (Exception e) {
            return -1;
        }
    }
}
