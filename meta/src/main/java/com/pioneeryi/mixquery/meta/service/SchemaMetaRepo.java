package com.pioneeryi.mixquery.meta.service;

import com.pioneeryi.mixquery.meta.domain.CalciteSchema;

/**
 * SchemaMetaRepo.
 *
 * @author yixianfeng
 * @since 2022/7/26 19:36
 */
public interface SchemaMetaRepo {

    /**
     * save schema
     *
     * @param schema schema info
     */
    void saveSchema(CalciteSchema schema);

    /**
     * load schema
     *
     * @param dbName database name
     *
     * @return schema info
     */
    CalciteSchema loadSchema(String dbName);
}
