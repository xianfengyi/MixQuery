package com.github.pioneeryi.client;

import com.github.pioneeryi.MixqMeta;
import com.github.pioneeryi.exception.MixqJdbcException;
import org.apache.calcite.avatica.AvaticaParameter;
import org.apache.calcite.avatica.MetaImpl;

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
    public QueryResult executeQuery(String sql, List<AvaticaParameter> params, List<Object> paramValues, int timeoutS) throws MixqJdbcException {
        return null;
    }

    @Override
    public void close() throws MixqJdbcException {

    }

    @Override
    public void close(int statementId) throws MixqJdbcException {

    }

    @Override
    public List<MetaImpl.MetaCatalog> getCatalogs() throws MixqJdbcException {
        return null;
    }

    @Override
    public List<MetaImpl.MetaSchema> getSchemas(String catalog, String schemaPattern) throws MixqJdbcException {
        return null;
    }

    @Override
    public List<MetaImpl.MetaTable> getTables(String catalog, String schemaPattern, String tableNamePattern, List<String> typeList) throws MixqJdbcException {
        return null;
    }

    @Override
    public List<MetaImpl.MetaColumn> getColumns(String catalog, String schemaPattern, String tableNamePattern, String columnNamePattern) throws MixqJdbcException {
        return null;
    }

    @Override
    public List<MetaImpl.MetaTableType> getTableTypes() throws MixqJdbcException {
        return null;
    }
}
