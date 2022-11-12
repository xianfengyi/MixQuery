package com.pioneeryi.mixquery.server.controller;

import com.pioneeryi.mixquery.server.application.QueryService;
import com.pioneeryi.mixquery.server.application.model.ColumnMeta;
import com.pioneeryi.mixquery.server.application.model.QueryResult;
import com.pioneeryi.mixquery.server.application.model.SchemaMeta;
import com.pioneeryi.mixquery.server.application.model.TableMeta;
import com.pioneeryi.mixquery.server.request.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/mixquery")
public class QueryController {

    private static final Logger log = LoggerFactory.getLogger(QueryController.class);

    @Autowired
    private QueryService queryService;

    @RequestMapping(value = "/jdbc/catalogs", method = RequestMethod.POST)
    public ResponseEntity<?> getCatalogs(@RequestBody CatalogRequest request) {
        try {
            List<String> catalogs = queryService.queryCatalogs(request.getDbName());
            return new ResponseEntity<>(catalogs, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/jdbc/schemas", method = RequestMethod.POST)
    public ResponseEntity<?> getSchemas(@RequestBody SchemaRequest request) {
        try {
            List<SchemaMeta> schemaMetas = queryService.querySchemaMetas(request.getDbName());
            return new ResponseEntity<>(schemaMetas, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/jdbc/tables", method = RequestMethod.POST)
    public ResponseEntity<?> getTables(@RequestBody TableMetaRequest request) {
        try {
            List<TableMeta> tableMetas = queryService.queryTableMetas(request.getDbName());
            return new ResponseEntity<>(tableMetas, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/jdbc/columns", method = RequestMethod.POST)
    public ResponseEntity<?> getColumns(@RequestBody ColumnMetaRequest request) {
        try {
            List<ColumnMeta> columnMetas = queryService.queryColumnMetas(request.getDbName(), request.getTableName());
            return new ResponseEntity<>(columnMetas, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/jdbc/tableTypes", method = RequestMethod.POST)
    public ResponseEntity<?> getTableTypes(@RequestBody TableTypeRequest request) {
        try {
            List<String> catalogs = queryService.queryTableTypes(request.getDbName());
            return new ResponseEntity<>(catalogs, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/query", method = RequestMethod.POST)
    public ResponseEntity<?> query(@RequestBody SqlRequest request) {
        try {
            QueryResult queryResult = queryService.query(request.getSql());
            return new ResponseEntity<>(queryResult, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
