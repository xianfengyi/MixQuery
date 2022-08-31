package com.github.pioneeryi;

import com.github.pioneeryi.client.IRemoteClient;
import com.github.pioneeryi.client.MixQueryClient;
import com.github.pioneeryi.exception.MixqJdbcException;
import org.apache.calcite.avatica.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

public class MixqConnection extends AvaticaConnection {

    private static final Logger logger = LoggerFactory.getLogger(MixqConnection.class);

    private final String baseUrl;
    private final String project;
    private final IRemoteClient remoteClient;

    protected MixqConnection(UnregisteredDriver driver, AvaticaFactory factory, String url, Properties info) throws SQLException {
        super(driver, factory, url, info);
        String odbcUrl = url;
        odbcUrl = odbcUrl.replace(Driver.CONNECTION_STRING_PREFIX + "//", "");
        String[] temps = odbcUrl.split("/");

        assert temps.length == 2;

        this.baseUrl = temps[0];
        this.project = temps[1];

        this.remoteClient = new MixQueryClient();

        try {
            this.remoteClient.connect();
        } catch (MixqJdbcException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public AvaticaStatement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability)
            throws SQLException {
        return super.createStatement(resultSetType, resultSetConcurrency, resultSetHoldability);
    }

    @Override
    public boolean getAutoCommit() throws SQLException {
        if (meta.connectionSync(handle, new ConnectionPropertiesImpl()).isAutoCommit() == null) {
            setAutoCommit(true);
        }
        return super.getAutoCommit();
    }

    @Override
    public boolean isReadOnly() throws SQLException {
        if (meta.connectionSync(handle, new ConnectionPropertiesImpl()).isReadOnly() == null) {
            setReadOnly(true);
        }
        return super.isReadOnly();
    }

    @Override
    public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency,
                                              int resultSetHoldability) throws SQLException {
        Meta.Signature sig = createSignature(sql);
        return factory().newPreparedStatement(this, null, sig, resultSetType, resultSetConcurrency,
                resultSetHoldability);
    }

    Meta.Signature createSignature(String sql) {
        List<AvaticaParameter> params = new ArrayList<AvaticaParameter>();
        int startIndex = 0;
        // 记录PrepareStatement里的预编译自断
        while (sql.indexOf("?", startIndex) >= 0) {
            AvaticaParameter param = new AvaticaParameter(false, 0, 0, 0, null, null, null);
            params.add(param);
            startIndex = sql.indexOf("?", startIndex) + 1;
        }

        ArrayList<ColumnMetaData> columns = new ArrayList<ColumnMetaData>();
        Map<String, Object> internalParams = Collections.<String, Object>emptyMap();

        // 构建Signature
        return new Meta.Signature(columns, sql, params, internalParams, Meta.CursorFactory.ARRAY, Meta.StatementType.SELECT);
    }

    @Override
    public void close() throws SQLException {
        super.close();
        remoteClient.close();
    }

    private AvaticaFactory factory() {
        return factory;
    }

    public IRemoteClient getRemoteClient() {
        return remoteClient;
    }
}
