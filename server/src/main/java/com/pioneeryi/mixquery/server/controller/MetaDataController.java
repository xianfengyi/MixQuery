package com.pioneeryi.mixquery.server.controller;

import com.pioneeryi.mixquery.meta.domain.CalciteSchema;
import com.pioneeryi.mixquery.meta.domain.DatasourceType;
import com.pioneeryi.mixquery.meta.model.AddDatasourceCmd;
import com.pioneeryi.mixquery.server.request.CreateDataSourceRequest;
import com.pioneeryi.mixquery.meta.service.SchemaMetaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tedi-sql/meta")
public class MetaDataController {

    @Autowired
    private SchemaMetaService metaDataService;

    @RequestMapping(value = "/datasource/add", method = RequestMethod.POST)
    public ResponseEntity<?> getCatalogs(@RequestBody CreateDataSourceRequest request) {
        AddDatasourceCmd cmd = new AddDatasourceCmd(request.getDbName(), request.getTableName(),
                DatasourceType.valueOf(request.getDatasourceType()),
                request.getConnProperties());
        try {
            metaDataService.addDatasource(cmd);
            return new ResponseEntity<>("success", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/datasource/get", method = RequestMethod.GET)
    public ResponseEntity<?> getSchema(@RequestParam(value = "dbName", required = true) String dbName) {
        try {
            CalciteSchema schema = metaDataService.queryCalciteSchema(dbName);
            return new ResponseEntity<>(schema, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
