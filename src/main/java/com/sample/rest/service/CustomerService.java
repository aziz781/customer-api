package com.sample.rest.service;

import com.sample.rest.handler.ApiException;
import com.sample.rest.dto.Customer;
import com.networknt.schema.ValidationMessage;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.ExchangePattern;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.ExchangeBuilder;
import org.apache.camel.component.jsonvalidator.JsonValidationException;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;


@Service
public class CustomerService {

    Logger logger = LoggerFactory.getLogger(CustomerService.class);

    public static final String CUSTOMER_REGISTRATION_ENDPOINT = "direct:CustomerRegistration";

    @Autowired
    private ProducerTemplate producer;

    @Autowired
    private CamelContext camelContext;

   public String processCustomerRegistration(final Customer customer) throws ApiException {

       // perform the following:
       // (1) Construct a message of Request-Reply (InOut) Message-Type
       // (2) Send the Exchange Message to the endpoint for processing and wait for the response
       // (3) retrieve the response and return to the controller

       // (1) Constructing a message of Request-Reply (InOut) Message-Type
       logger.info("Constructing a message of Request-Reply (InOut) Message-Type");
       final Exchange requestExchange =
               ExchangeBuilder
                       .anExchange(camelContext)
                       .withBody(customer)
                       .withPattern(ExchangePattern.InOut)
                       .build();

       // (2) Sending the Exchange Message to the endpoint for processing and wait for the response
       logger.info("Sending the Exchange Message to the endpoint for processing and wait for the response");
       final Exchange responseExchange = producer.send(CUSTOMER_REGISTRATION_ENDPOINT, requestExchange);

       // (3) retrieving the response
       logger.info("retrieving the response");
       return getResponseMessage(responseExchange);

    }

    private String  getResponseMessage(final Exchange exchange) throws ApiException{
          Exception exceptionResponse = exchange.getException();
           if( ObjectUtils.isEmpty(exceptionResponse))
           {
               // success response
               logger.info("success response");
               return exchange.getMessage().getBody(String.class);
           }
           else if(exceptionResponse instanceof JsonValidationException){

               // Error response
               logger.info("validation error response");

                // Json Validation error occurred so extract validation message which to show to the customer
               logger.info("Json Validation error occurred so extract validation message which to show to the customer");
               JsonValidationException validationException = (JsonValidationException)exceptionResponse;
                ValidationMessage validationMessage= validationException.getErrors().iterator().next();
                String errorMessage = validationMessage.getMessage();
                if(errorMessage!=null && errorMessage.startsWith("$.") && errorMessage.length()>2 ) {
                    errorMessage = errorMessage.substring(2);
                    logger.info(errorMessage);
                } else {
                    errorMessage = "Invalid Json Format";
                    logger.info(errorMessage);
                }
                // throw API Exception
                logger.info("throw API Exception");
                throw new ApiException(errorMessage);

            }  else{
               // some other error occurred
               logger.info("some other error occurred-"+exceptionResponse.getMessage());
               throw new ApiException(exceptionResponse.getMessage());
           }
    }

}
