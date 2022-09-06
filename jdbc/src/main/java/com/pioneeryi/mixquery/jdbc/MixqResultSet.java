package com.pioneeryi.mixquery.jdbc;

import com.pioneeryi.mixquery.jdbc.client.IRemoteClient;
import org.apache.calcite.avatica.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.List;
import java.util.TimeZone;

public class MixqResultSet extends AvaticaResultSet {

    private static final Logger logger = LoggerFactory.getLogger(MixqResultSet.class);

    public MixqResultSet(AvaticaStatement statement, QueryState state, Meta.Signature signature,
                         ResultSetMetaData resultSetMetaData, TimeZone timeZone, Meta.Frame firstFrame) throws SQLException {
        super(statement, state, signature, resultSetMetaData, timeZone, firstFrame);
    }

    @Override
    protected AvaticaResultSet execute() throws SQLException {
        // skip execution if result is already there (case of meta data lookup)
        if (this.firstFrame != null) {
            return super.execute();
        }

        String sql = signature.sql;
        List<Object> paramValues = convertPrepareParams(signature.parameters);

        IRemoteClient client = ((MixqConnection) statement.connection).getRemoteClient();
        IRemoteClient.QueryResult result = null;
        try {
            result = client.executeQuery(sql, paramValues, statement.getQueryTimeout());
        } catch (Exception e) {
            throw new SQLException(e);
        }

        //设置列元数据
        columnMetaDataList.clear();
        columnMetaDataList.addAll(result.columnMeta);

        //生成游标对象(Style = ARRAY)
        cursor = MetaImpl.createCursor(signature.cursorFactory, result.iterable);
        return super.execute2(cursor, columnMetaDataList);
    }

    private List<Object> convertPrepareParams(List<AvaticaParameter> params) {
        List<Object> paramValues = null;
        if (statement instanceof MixqPreparedStatement && params != null && !params.isEmpty()) {
            //获取PreparedStatement的预编译字段
            paramValues = ((MixqPreparedStatement) statement).getParameterJDBCValues();
        }
        return paramValues;
    }
}
