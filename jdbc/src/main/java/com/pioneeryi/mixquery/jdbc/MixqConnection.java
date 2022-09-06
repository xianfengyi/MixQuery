package com.pioneeryi.mixquery.jdbc;

import com.pioneeryi.mixquery.jdbc.client.IRemoteClient;
import com.pioneeryi.mixquery.jdbc.client.MixqClient;
import com.pioneeryi.mixquery.jdbc.model.MixqConnectionInfo;
import org.apache.calcite.avatica.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class MixqConnection extends AvaticaConnection {

    private static final Logger logger = LoggerFactory.getLogger(MixqConnection.class);

    private final IRemoteClient remoteClient;

    protected MixqConnection(UnregisteredDriver driver, AvaticaFactory factory, MixqConnectionInfo connInfo) {
        super(driver, factory, connInfo.getUrl(), connInfo.getConnProperties());
        logger.debug("Mixquery base url " + connInfo.getBaseUrl() + ", project name " + connInfo.getBaseDb());

        this.remoteClient = new MixqClient(connInfo);

        this.remoteClient.connect();
    }

    @Override
    public AvaticaStatement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
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
    public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
        Meta.Signature sig = createSignature(sql);
        return factory().newPreparedStatement(this, null, sig, resultSetType, resultSetConcurrency, resultSetHoldability);
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
