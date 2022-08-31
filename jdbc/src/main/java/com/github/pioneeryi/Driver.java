package com.github.pioneeryi;

import org.apache.calcite.avatica.AvaticaConnection;
import org.apache.calcite.avatica.DriverVersion;
import org.apache.calcite.avatica.Meta;
import org.apache.calcite.avatica.UnregisteredDriver;

import java.sql.DriverManager;
import java.sql.SQLException;

public class Driver extends UnregisteredDriver {

    public static final String CONNECTION_STRING_PREFIX = "jdbc:mixquery:";

    static {
        try {
            DriverManager.registerDriver(new Driver());
        } catch (SQLException e) {
            throw new RuntimeException("Error occurred while registering JDBC driver " + Driver.class.getName() + ": " + e.toString());
        }
    }

    @Override
    protected String getConnectStringPrefix() {
        return CONNECTION_STRING_PREFIX;
    }

    @Override
    protected DriverVersion createDriverVersion() {
        return DriverVersion.load(Driver.class, "mixquery-jdbc.properties", "MixQuery JDBC Driver", "unknown version", "MixQuery", "unknown version");
    }

    @Override
    protected String getFactoryClassName(JdbcVersion jdbcVersion) {
        switch (jdbcVersion) {
            case JDBC_30:
                throw new UnsupportedOperationException();
            case JDBC_40:
                return MixqJdbcFactory.Version40.class.getName();
            case JDBC_41:
            default:
                return MixqJdbcFactory.Version41.class.getName();
        }
    }

    @Override
    public Meta createMeta(AvaticaConnection connection) {
        return new MixqMeta((MixqConnection) connection);
    }
}
