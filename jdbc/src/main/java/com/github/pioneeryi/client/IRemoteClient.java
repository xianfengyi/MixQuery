package com.github.pioneeryi.client;

import com.github.pioneeryi.exception.MixqJdbcException;
import org.apache.calcite.avatica.ColumnMetaData;
import org.apache.calcite.avatica.MetaImpl;

import java.io.Closeable;
import java.util.List;

public interface IRemoteClient extends Closeable {

    class QueryResult {
        public final List<ColumnMetaData> columnMeta;
        public final Iterable<Object> iterable;

        public QueryResult(List<ColumnMetaData> columnMeta, Iterable<Object> iterable) {
            this.columnMeta = columnMeta;
            this.iterable = iterable;
        }
    }

    /**
     * Connect to mixquery restful service. IOException will be thrown if authentication failed.
     */
    void connect() throws MixqJdbcException;

    /**
     * Execute query remotely and get back result.
     */
    QueryResult executeQuery(String sql, List<Object> paramValues, int timeoutS) throws MixqJdbcException;

    /**
     * Close statement.
     *
     * @throws MixqJdbcException if fail, throw this exception
     */
    void close() throws MixqJdbcException;

    /**
     * Close statement.
     *
     * @param statementId statement id
     * @throws MixqJdbcException if fail, throw this exception
     */
    void close(int statementId) throws MixqJdbcException;

    /**
     * 获取数据库 catalog 信息
     *
     * @return catalog信息
     */
    List<MetaImpl.MetaCatalog> getCatalogs() throws MixqJdbcException;

    /**
     * 获取数据库 schema 元数据信息
     *
     * @param catalog       catalog名。用于匹配数据库中catalog下的schema。"" 表示匹配没有catalog的schema，null表示任意catalog下的schema
     * @param schemaPattern schema名pattern。用于匹配数据库中schema。null表示任意schema
     * @return schema元数据信息
     * @throws MixqJdbcException 鉴权失败，元数据过大 或 服务端发生异常时抛出
     */
    List<MetaImpl.MetaSchema> getSchemas(String catalog, String schemaPattern) throws MixqJdbcException;

    /**
     * 获取数据库表元数据信息
     *
     * @param catalog          catalog名。用于匹配数据库中catalog下的table。"" 表示匹配没有catalog的table，null表示任意catalog下的table
     * @param schemaPattern    schema名pattern。用于匹配数据库中schema下的table。"" 表示匹配没有schema的table，null表示任意schema下的table
     * @param tableNamePattern 表名pattern。用于匹配数据库中表。null表示任意表
     * @param typeList         指定表类型列表，必须取自从 getTableTypes() 返回的表类型中选择；null 表示返回任意类型
     * @return 表元数据信息
     * @throws MixqJdbcException 鉴权失败，元数据过大 或 服务端发生异常时抛出
     */
    List<MetaImpl.MetaTable> getTables(String catalog, String schemaPattern, String tableNamePattern, List<String> typeList)
            throws MixqJdbcException;

    /**
     * 获取数据库列元数据信息
     *
     * @param catalog           catalog名。用于匹配数据库中catalog下的列。"" 表示匹配没有catalog的列，null表示任意catalog下的列
     * @param schemaPattern     schema名pattern。用于匹配数据库中schema下的列。"" 表示匹配没有schema的列，null表示任意schema下的列
     * @param tableNamePattern  表名pattern。用于匹配数据库中表下的列。null表示任意表下的列
     * @param columnNamePattern 列名pattern。用于匹配数据库中的列。null表示任意列
     * @return 列元数据信息
     * @throws MixqJdbcException 鉴权失败，元数据过大 或 服务端发生异常时抛出
     */
    List<MetaImpl.MetaColumn> getColumns(String catalog, String schemaPattern, String tableNamePattern, String columnNamePattern)
            throws MixqJdbcException;

    /**
     * 获取数据库表类型，如table，view
     *
     * @return 数据库表类型
     * @throws MixqJdbcException 鉴权失败，元数据过大 或 服务端发生异常时抛出
     */
    List<MetaImpl.MetaTableType> getTableTypes() throws MixqJdbcException;

}
