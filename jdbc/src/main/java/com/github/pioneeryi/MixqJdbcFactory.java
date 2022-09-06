package com.github.pioneeryi;

import com.github.pioneeryi.model.MixqConnectionInfo;
import org.apache.calcite.avatica.*;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Properties;
import java.util.TimeZone;

public class MixqJdbcFactory implements AvaticaFactory {

    public static class Version40 extends MixqJdbcFactory {
        public Version40() {
            super(4, 0);
        }
    }

    public static class Version41 extends MixqJdbcFactory {
        public Version41() {
            super(4, 1);
        }
    }

    final int major;
    final int minor;

    /**
     * Creates a JDBC factory with given major/minor version number.
     */
    protected MixqJdbcFactory(int major, int minor) {
        this.major = major;
        this.minor = minor;
    }

    @Override
    public int getJdbcMajorVersion() {
        return 0;
    }

    @Override
    public int getJdbcMinorVersion() {
        return 0;
    }

    @Override
    public AvaticaConnection newConnection(UnregisteredDriver driver, AvaticaFactory factory, String url, Properties properties) throws SQLException {
        return new MixqConnection(driver, factory, new MixqConnectionInfo(url, properties));
    }

    @Override
    public AvaticaStatement newStatement(AvaticaConnection connection, Meta.StatementHandle statementHandle, int resultSetType,
                                         int resultSetConcurrency, int resultSetHoldability) throws SQLException {
        return new MixqStatement(connection, statementHandle, resultSetType, resultSetConcurrency,
                resultSetHoldability);
    }

    @Override
    public AvaticaPreparedStatement newPreparedStatement(AvaticaConnection connection, Meta.StatementHandle handle,
                                                         Meta.Signature signature, int resultSetType,
                                                         int resultSetConcurrency, int resultSetHoldability) throws SQLException {
        return new MixqPreparedStatement(connection, handle, signature, resultSetType, resultSetConcurrency,
                resultSetHoldability);
    }

    @Override
    public AvaticaResultSet newResultSet(AvaticaStatement statement, QueryState state, Meta.Signature signature,
                                         TimeZone timeZone, Meta.Frame firstFrame) throws SQLException {
        AvaticaResultSetMetaData resultSetMetaData = new AvaticaResultSetMetaData(statement, null, signature);
        return new MixqResultSet(statement, state, signature, resultSetMetaData, timeZone, firstFrame);
    }

    @Override
    public AvaticaSpecificDatabaseMetaData newDatabaseMetaData(AvaticaConnection avaticaConnection) {
        return new AvaticaDatabaseMetaData(avaticaConnection) {
        };
    }

    @Override
    public ResultSetMetaData newResultSetMetaData(AvaticaStatement statement, Meta.Signature signature) throws SQLException {
        return new AvaticaResultSetMetaData(statement, null, signature);
    }
}
