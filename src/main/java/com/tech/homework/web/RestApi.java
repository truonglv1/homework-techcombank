package com.tech.homework.web;

import com.tech.homework.model.Pool;
import com.tech.homework.model.Response;
import com.tech.homework.services.ServiceHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1")
public class RestApi {
    @Autowired
    private ServiceHandler serviceHandler;

    @PostMapping(value = "/pool")
    public ResponseEntity<?> handleEndpoint1(@RequestBody Pool pool) {
        String status = serviceHandler.createPool(pool);
        Response response = new Response();
        response.setStatus(status);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(value = "/quantile")
    public ResponseEntity<?> handleEndpoint2(@RequestBody Pool pool) {
        Response response = serviceHandler.quantile(pool);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
