package com.pioneeryi.mixquery.meta.service;

import com.pioneeryi.mixquery.meta.domain.CalciteModel;
import com.pioneeryi.mixquery.meta.domain.CalciteSchema;
import com.google.gson.Gson;

import java.util.List;

/**
 * Provide table related params and methods which can generate metadata Json based on those params.
 *
 * @author yixianfeng
 * @since 2022/7/26 19:41
 */
public class SchemaAssembler {

    private static final String DEFAULT_VERSION = "1.0";

    private static Gson gson = new Gson();

    /**
     * Reduce same Json schema if exists. Used when there are several tables in sql which are from one type of data
     * storage.
     *
     * @param schemas Same schemas
     *
     * @return Metadata json
     */
    public static String reduceSchema(List<CalciteSchema> schemas) {
        CalciteModel calciteModel = new CalciteModel(schemas, DEFAULT_VERSION);
        return gson.toJson(calciteModel);
    }
}
