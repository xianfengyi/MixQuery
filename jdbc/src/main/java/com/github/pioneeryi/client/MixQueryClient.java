package com.github.pioneeryi.client;

import com.github.pioneeryi.MixQueryMeta;
import org.apache.calcite.avatica.AvaticaParameter;

import java.io.IOException;
import java.util.List;

public class MixQueryClient implements IRemoteClient {

    @Override
    public void connect() throws IOException {

    }

    @Override
    public MixQueryMeta.MixMetaProject retrieveMetaData(String project) throws IOException {
        return null;
    }

    @Override
    public QueryResult executeQuery(String sql, List<AvaticaParameter> params, List<Object> paramValues) throws IOException {
        return null;
    }

    @Override
    public void close() throws IOException {

    }
}
