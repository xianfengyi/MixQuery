package com.github.pioneeryi;

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

    /** Creates a JDBC factory with given major/minor version number. */
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
    public AvaticaConnection newConnection(UnregisteredDriver unregisteredDriver, AvaticaFactory avaticaFactory, String s, Properties properties) throws SQLException {
        return null;
    }

    @Override
    public AvaticaStatement newStatement(AvaticaConnection avaticaConnection, Meta.StatementHandle statementHandle, int i, int i1, int i2) throws SQLException {
        return null;
    }

    @Override
    public AvaticaPreparedStatement newPreparedStatement(AvaticaConnection avaticaConnection, Meta.StatementHandle statementHandle, Meta.Signature signature, int i, int i1, int i2) throws SQLException {
        return null;
    }

    @Override
    public AvaticaResultSet newResultSet(AvaticaStatement avaticaStatement, QueryState queryState, Meta.Signature signature, TimeZone timeZone, Meta.Frame frame) throws SQLException {
        return null;
    }

    @Override
    public AvaticaSpecificDatabaseMetaData newDatabaseMetaData(AvaticaConnection avaticaConnection) {
        return null;
    }

    @Override
    public ResultSetMetaData newResultSetMetaData(AvaticaStatement avaticaStatement, Meta.Signature signature) throws SQLException {
        return null;
    }
}
