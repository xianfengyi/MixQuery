package com.github.pioneeryi.client;

import com.github.pioneeryi.MixqMeta;
import com.github.pioneeryi.exception.MixqJdbcException;
import org.apache.calcite.avatica.AvaticaParameter;
import org.apache.calcite.avatica.ColumnMetaData;

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
     * Retrieve meta data of given project.
     */
    MixqMeta.MixMetaProject retrieveMetaData(String project) throws MixqJdbcException;

    /**
     * Execute query remotely and get back result.
     */
    QueryResult executeQuery(String sql, List<AvaticaParameter> params, List<Object> paramValues) throws MixqJdbcException;

}
