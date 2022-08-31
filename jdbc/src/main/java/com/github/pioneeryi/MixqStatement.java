package com.github.pioneeryi;

import com.github.pioneeryi.exception.MixqJdbcException;
import org.apache.calcite.avatica.AvaticaConnection;
import org.apache.calcite.avatica.AvaticaStatement;
import org.apache.calcite.avatica.Meta;

import java.sql.SQLException;

public class MixqStatement extends AvaticaStatement {

    protected MixqStatement(AvaticaConnection connection, Meta.StatementHandle h, int resultSetType,
                            int resultSetConcurrency, int resultSetHoldability) {
        super(connection, h, resultSetType, resultSetConcurrency, resultSetHoldability);
    }

    @Override
    public synchronized void close() throws SQLException {
        super.close();
        try {
            ((MixqConnection) connection).getRemoteClient().close(getId());
        } catch (MixqJdbcException e) {
            throw new SQLException(e);
        }
    }
}
