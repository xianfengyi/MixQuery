package com.github.pioneeryi.client;

import com.github.pioneeryi.MixQueryMeta;
import org.apache.calcite.avatica.AvaticaParameter;
import org.apache.calcite.avatica.ColumnMetaData;

import java.io.Closeable;
import java.io.IOException;
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
     * Connect to Kylin restful service. IOException will be thrown if authentication failed.
     */
    void connect() throws IOException;

    /**
     * Retrieve meta data of given project.
     */
    MixQueryMeta.MixMetaProject retrieveMetaData(String project) throws IOException;

    /**
     * Execute query remotely and get back result.
     */
    QueryResult executeQuery(String sql, List<AvaticaParameter> params, List<Object> paramValues) throws IOException;

}
