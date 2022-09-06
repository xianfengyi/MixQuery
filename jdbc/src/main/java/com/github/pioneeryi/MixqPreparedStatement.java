package com.github.pioneeryi;

import org.apache.calcite.avatica.AvaticaConnection;
import org.apache.calcite.avatica.AvaticaPreparedStatement;
import org.apache.calcite.avatica.Meta;
import org.apache.calcite.avatica.remote.TypedValue;

import java.sql.SQLException;
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
}
