package com.github.pioneeryi.client;

import com.github.pioneeryi.MixqMeta;
import com.github.pioneeryi.exception.MixqJdbcException;
import org.apache.calcite.avatica.AvaticaParameter;

import java.io.IOException;
import java.util.List;

public class MixQueryClient implements IRemoteClient {

    @Override
    public void connect() throws MixqJdbcException {

    }

    @Override
    public MixqMeta.MixMetaProject retrieveMetaData(String project) throws MixqJdbcException {
        return null;
    }

    @Override
    public QueryResult executeQuery(String sql, List<AvaticaParameter> params, List<Object> paramValues) throws MixqJdbcException {
        return null;
    }

    @Override
    public void close() throws IOException {

    }
}
