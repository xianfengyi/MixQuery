package com.github.pioneeryi.gateway;

import com.github.pioneeryi.domain.CalciteSchema;
import com.github.pioneeryi.service.SchemaMetaRepo;
import java.util.ArrayList;
import java.util.List;

/**
 * 内存的形式的 Schema 仓储.
 *
 * @author pioneeryi
 * @since 2022/4/29 16:39
 */
public class MemorySchemaRepo implements SchemaMetaRepo {

    private List<CalciteSchema> schemas = new ArrayList<>();

    private static class MemorySchemaRepoHolder {

        private static final MemorySchemaRepo INSTANCE = new MemorySchemaRepo();
    }

    public static MemorySchemaRepo getInstance() {
        return MemorySchemaRepoHolder.INSTANCE;
    }

    @Override
    public void saveSchema(CalciteSchema schema) {
        schemas.add(schema);
    }

    @Override
    public CalciteSchema loadSchema(String dbName) {
        for (CalciteSchema schema : schemas) {
            if (schema.getName().equals(dbName)) {
                return schema;
            }
        }
        throw new RuntimeException("can not find schema fo this db");
    }
}
