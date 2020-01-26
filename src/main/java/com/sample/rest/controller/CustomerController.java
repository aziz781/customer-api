package com.sample.rest.controller;


import com.sample.rest.dto.Customer;
import com.sample.rest.handler.ApiException;
import com.sample.rest.dto.ResponseMessage;
import io.swagger.annotations.*;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Api(description =  "Customer Rest API to register customers", tags={"Customer API"})
@RequestMapping("/api/v1")
public interface CustomerController {

    @ApiOperation(value = "Register customer data", response = ResponseMessage.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Customer registered successfully"),
            @ApiResponse(code = 400, message = "firstName: null found, string expected")
    })
    @PostMapping(path = "/customers",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ResponseMessage> createCustomer(@RequestBody(required = false) Customer customer) throws ApiException ;

}
