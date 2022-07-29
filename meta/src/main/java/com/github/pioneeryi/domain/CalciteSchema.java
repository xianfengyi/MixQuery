package com.github.pioneeryi.domain;

import java.util.Map;

/**
 * Calcite schema info.
 *
 * <pre>
 * {
 *    "factory": "org.apache.calcite.adapter.jdbc.JdbcSchema$Factory",
 *    "name": "db1",
 *    "operand": {
 *      "jdbcDriver": "com.mysql.cj.jdbc.Driver",
 *      "jdbcPassword": "xxxx",
 *      "jdbcUrl": "jdbc:mysql://127.0.0.1:3306/xxx",
 *       "jdbcUser": "xxx"
 *    },
 *    "type": "custom"
 * }
 * </pre>
 *
 * @author yixianfeng
 * @since 2022/7/26 19:34
 */
public class CalciteSchema {

    private String factory;

    private String name;

    private String type;

    private Map<String, String> operand;

    public CalciteSchema(String factory, String name, String type, Map<String, String> operand) {
        this.factory = factory;
        this.name = name;
        this.type = type;
        this.operand = operand;
    }

    public String getFactory() {
        return factory;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public Map<String, String> getOperand() {
        return operand;
    }
}
