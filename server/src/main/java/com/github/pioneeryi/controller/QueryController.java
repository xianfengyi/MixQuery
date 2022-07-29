package com.github.pioneeryi.controller;

import com.github.pioneeryi.controller.request.SqlRequest;
import com.github.pioneeryi.service.QueryService;
import com.github.pioneeryi.service.model.QueryResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * provide query restful api.
 *
 * @author yixianfeng
 * @since 2022/7/29 09:17
 */
@RestController
@RequestMapping("/mix-query")
public class QueryController {

    private QueryService queryService;

    public QueryController(QueryService queryService) {
        this.queryService = queryService;
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public ResponseEntity<?> test() {
        try {
            return new ResponseEntity<>("hello world", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/jdbc/query", method = RequestMethod.POST)
    public ResponseEntity<?> query(@RequestBody SqlRequest request) {
        try {
            QueryResult queryResult = queryService.query(request.getSql());
            return new ResponseEntity<>(queryResult, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
