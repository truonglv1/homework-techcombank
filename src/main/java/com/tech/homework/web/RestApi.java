package com.tech.homework.web;

import com.tech.homework.model.Pool;
import com.tech.homework.model.Response;
import com.tech.homework.services.ServiceHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
@RequestMapping("api/v1")
public class RestApi {
    @Autowired
    private ServiceHandler serviceHandler;

    @PostMapping(value = "/pool")
    public ResponseEntity<?> handleEndpoint1(@Valid @RequestBody Pool pool) {
        Response response = new Response();

        if (pool.getPoolValues() == null || pool.getPoolValues().size() == 0){
            response.setError("values is invalid!");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        String status = serviceHandler.createPool(pool);

        response.setStatus(status);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(value = "/quantile")
    public ResponseEntity<?> handleEndpoint2(@Valid @RequestBody Pool pool) {
        Response response = new Response();

        if (pool.getPercentile() == null
                || pool.getPercentile() < 0
                || pool.getPercentile() > 100){
            response.setError("percentile is invalid!");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        response = serviceHandler.quantile(pool);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
