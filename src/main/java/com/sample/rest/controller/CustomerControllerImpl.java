package com.sample.rest.controller;

import com.sample.rest.handler.ApiException;
import com.sample.rest.dto.ResponseMessage;
import com.sample.rest.dto.ResponseStatus;
import com.sample.rest.service.CustomerService;
import com.sample.rest.dto.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerControllerImpl implements CustomerController {

    Logger logger = LoggerFactory.getLogger(CustomerControllerImpl.class);

    @Autowired
    CustomerService customerService;

    @Override
    public ResponseEntity<ResponseMessage> createCustomer(@RequestBody(required = false) Customer customer) throws ApiException {

           /*NOTE:
           All the errors are being handled centralized exception handling across using
           ResponseEntityExceptionHandler and subclass ApiExceptionHandler.
           The errors from routes and services are propagated and handled through
           the global exception handler, which translates specific types of exceptions to HTTP response codes.*/

        // call the service for processing
        logger.info("calling service for processing");
        String responseBody = customerService.processCustomerRegistration(customer);

        // prepare response
        logger.info("preparing response - " + responseBody);
        return ResponseEntity
                .created(null)
                .body(new ResponseMessage(ResponseStatus.SUCCESS,responseBody));


    }


}
