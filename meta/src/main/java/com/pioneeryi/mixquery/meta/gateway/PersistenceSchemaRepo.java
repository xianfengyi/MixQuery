package com.pioneeryi.mixquery.meta.gateway;

import com.pioneeryi.mixquery.meta.domain.CalciteSchema;
import com.pioneeryi.mixquery.meta.gateway.dao.mapper.CalciteSchemaMapper;
import com.pioneeryi.mixquery.meta.gateway.dao.po.CalciteSchemaDO;
import com.pioneeryi.mixquery.meta.service.SchemaMetaRepo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * 持久化存储仓储.
 *
 * @author pioneeryi
 * @since 2022/5/18 19:07
 */
@Repository
public class PersistenceSchemaRepo implements SchemaMetaRepo {

    private final Gson gson = new Gson();

    @Autowired
    private CalciteSchemaMapper calciteSchemaMapper;

    @Override
    public void saveSchema(CalciteSchema schema) {
        CalciteSchemaDO record = new CalciteSchemaDO();
        record.setName(schema.getName());
        record.setType(schema.getType());
        record.setFactory(schema.getFactory());
        record.setOperand(gson.toJson(schema.getOperand()));
        calciteSchemaMapper.insert(record);
    }

    @Override
    public CalciteSchema loadSchema(String dbName) {
        CalciteSchemaDO calciteSchemaDO = calciteSchemaMapper.selectByName(dbName);
        Type operandType = new TypeToken<Map<String, String>>() {
        }.getType();
        Map<String, String> operand = gson.fromJson(calciteSchemaDO.getOperand(), operandType);
        return new CalciteSchema(calciteSchemaDO.getFactory(), calciteSchemaDO.getName(), calciteSchemaDO.getType(),
                operand);
    }
}
