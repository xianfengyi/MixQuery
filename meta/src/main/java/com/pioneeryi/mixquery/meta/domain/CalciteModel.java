package com.pioneeryi.mixquery.meta.domain;

import java.util.List;

/**
 * Apache calcite model info.
 *
 * model like this:
 * <pre>
 * {
 *     "defaultSchema": "db1",
 *     "schemas": [
 *         {
 *             "factory": "org.apache.calcite.adapter.jdbc.JdbcSchema$Factory",
 *             "name": "db1",
 *             "operand": {
 *                 "jdbcDriver": "com.mysql.cj.jdbc.Driver",
 *                 "jdbcPassword": "xxxx",
 *                 "jdbcUrl": "jdbc:mysql://127.0.0.1:3306/xxx",
 *                 "jdbcUser": "xxxx"
 *             },
 *             "type": "custom"
 *         }
 *     ],
 *     "version": "1.0"
 * }
 * </pre>
 *
 * @author yixianfeng
 * @since 2022/7/26 19:33
 */
public class CalciteModel {

    private String defaultSchema;

    private List<CalciteSchema> schemas;

    private String version;

    public CalciteModel(List<CalciteSchema> schemas, String version) {
        this.defaultSchema = schemas.get(0).getName();
        this.schemas = schemas;
        this.version = version;
    }

    public String getDefaultSchema() {
        return defaultSchema;
    }

    public List<CalciteSchema> getSchemas() {
        return schemas;
    }

    public String getVersion() {
        return version;
    }
}
