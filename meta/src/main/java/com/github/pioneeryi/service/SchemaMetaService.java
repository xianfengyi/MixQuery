package com.github.pioneeryi.service;

import com.github.pioneeryi.domain.CalciteSchema;
import com.github.pioneeryi.domain.SchemaType;
import com.github.pioneeryi.model.AddDatasourceCmd;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 * SchemaMetaService.
 *
 * @author yixianfeng
 * @since 2022/7/26 19:36
 */
@Service
public class SchemaMetaService {

    private SchemaMetaRepo schemaRepo;

    public SchemaMetaService() {
    }

    public SchemaMetaService(SchemaMetaRepo schemaRepo) {
        this.schemaRepo = schemaRepo;
    }

    /**
     * 添加数据源.
     *
     * @param cmd 添加数据源 CMD
     */
    public void addDatasource(AddDatasourceCmd cmd) {
        SchemaType schemaType = SchemaType.getSchemaType(cmd.getDatasourceType());
        CalciteSchema calciteSchema = new CalciteSchema(schemaType.getSchemaFactory(), cmd.getDbName(),
                "custom", cmd.getConnProperties());
        schemaRepo.saveSchema(calciteSchema);
    }

    /**
     * 查询 Schema 信息
     *
     * @param dbName DB 名
     *
     * @return Schema 信息
     */
    public CalciteSchema queryCalciteSchema(String dbName) {
        return schemaRepo.loadSchema(dbName);
    }

    /**
     * 根据DB名构造Calcite的Schema.
     *
     * @param dbNames 数据库名列表
     *
     * @return 一个完成的Calcite的Schema
     */
    public String constructCalciteSchema(List<String> dbNames) {
        List<CalciteSchema> schemas = new ArrayList<>();
        for (String dbName : dbNames) {
            CalciteSchema schema = queryCalciteSchema(dbName);
            schemas.add(schema);
        }
        return "inline:" + SchemaAssembler.reduceSchema(schemas);
    }
}
