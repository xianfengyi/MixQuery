package com.github.pioneeryi;

import org.apache.calcite.avatica.AvaticaConnection;
import org.apache.calcite.avatica.AvaticaPreparedStatement;
import org.apache.calcite.avatica.Meta;
import org.apache.calcite.avatica.remote.TypedValue;

import java.io.InputStream;
import java.io.Reader;
import java.sql.NClob;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLXML;
import java.util.ArrayList;
import java.util.List;

public class MixqPreparedStatement extends AvaticaPreparedStatement {

    protected MixqPreparedStatement(AvaticaConnection connection, Meta.StatementHandle h, Meta.Signature signature,
                                    int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
        super(connection, h, signature, resultSetType, resultSetConcurrency, resultSetHoldability);
        if (this.handle.signature == null) {
            this.handle.signature = signature;
        }
    }

    protected List<Object> getParameterJDBCValues() {
        List<TypedValue> typeValues = getParameterValues();
        List<Object> jdbcValues = new ArrayList<Object>(typeValues.size());
        for (TypedValue typeValue : typeValues) {
            jdbcValues.add(typeValue.toJdbc(getCalendar()));
        }
        return jdbcValues;
    }

    public void setRowId(int parameterIndex, RowId x) throws SQLException {
        getSite(parameterIndex).setRowId(x);
    }

    public void setNString(int parameterIndex, String value) throws SQLException {
        getSite(parameterIndex).setNString(value);
    }

    public void setNCharacterStream(int parameterIndex, Reader value, long length) throws SQLException {
        getSite(parameterIndex).setNCharacterStream(value, length);
    }

    public void setNClob(int parameterIndex, NClob value) throws SQLException {
        getSite(parameterIndex).setNClob(value);
    }

    public void setClob(int parameterIndex, Reader reader, long length) throws SQLException {
        getSite(parameterIndex).setClob(reader, length);
    }

    public void setBlob(int parameterIndex, InputStream inputStream, long length) throws SQLException {
        getSite(parameterIndex).setBlob(inputStream, length);
    }

    public void setNClob(int parameterIndex, Reader reader, long length) throws SQLException {
        getSite(parameterIndex).setNClob(reader, length);
    }

    public void setSQLXML(int parameterIndex, SQLXML xmlObject) throws SQLException {
        getSite(parameterIndex).setSQLXML(xmlObject);
    }

    public void setAsciiStream(int parameterIndex, InputStream x, long length) throws SQLException {
        getSite(parameterIndex).setAsciiStream(x, length);
    }

    public void setBinaryStream(int parameterIndex, InputStream x, long length) throws SQLException {
        getSite(parameterIndex).setBinaryStream(x, length);
    }

    public void setCharacterStream(int parameterIndex, Reader reader, long length) throws SQLException {
        getSite(parameterIndex).setCharacterStream(reader, length);
    }

    public void setAsciiStream(int parameterIndex, InputStream x) throws SQLException {
        getSite(parameterIndex).setAsciiStream(x);
    }

    public void setBinaryStream(int parameterIndex, InputStream x) throws SQLException {
        getSite(parameterIndex).setBinaryStream(x);
    }

    public void setCharacterStream(int parameterIndex, Reader reader) throws SQLException {
        getSite(parameterIndex).setCharacterStream(reader);
    }

    public void setNCharacterStream(int parameterIndex, Reader value) throws SQLException {
        getSite(parameterIndex).setNCharacterStream(value);
    }

    public void setClob(int parameterIndex, Reader reader) throws SQLException {
        getSite(parameterIndex).setClob(reader);
    }

    public void setBlob(int parameterIndex, InputStream inputStream) throws SQLException {
        getSite(parameterIndex).setBlob(inputStream);
    }

    public void setNClob(int parameterIndex, Reader reader) throws SQLException {
        getSite(parameterIndex).setNClob(reader);
    }
}
