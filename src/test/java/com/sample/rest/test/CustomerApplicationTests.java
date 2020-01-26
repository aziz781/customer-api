package com.sample.rest.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.sample.rest.dto.Customer;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class CustomerApplicationTests  {

    Logger LOG = Logger.getLogger(CustomerApplicationTests.class.getName());

    @Autowired
    private TestRestTemplate restClient;

    @LocalServerPort
    private int port;

    private String url;

    private ObjectMapper mapper;



    @DisplayName("happy path use case (End to End flow) - valid request json ")
    @Tags({
            @Tag("HappyPath"),
            @Tag("EndToEnd")
    })
    @Test
    void testValidRequestJsonPayload() throws  Exception{

        LOG.info("Test Valid Request JSON Payload");
        final  int expectedResponseStatusCode = 201;
        final  String expectedResponseStatus = "SUCCESS";
        final  String expectedResponseMessage = "Customer registered successfully";

        // set request headers
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        requestHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        // set request payload
        final String requestBody =  mapper
                .writeValueAsString(
                        new Customer("Test1", "camel-Spring","11112222333","tester@test.com"));

        // prepare request
        final HttpEntity<String> request = new HttpEntity<String>(requestBody ,requestHeaders);

        // invoke post request
        final ResponseEntity responseEntity = restClient.postForEntity(url, request, String.class);

        // validate response entity
        assertNotNull(responseEntity);

        // get response HTTP Code and payload
        final int responseStatusCode = responseEntity.getStatusCode().value();
        final String responseBody =  (String)responseEntity.getBody();

        // validate response status code
        assertEquals(expectedResponseStatusCode,responseStatusCode);

        // validate response payload
        assertNotNull(responseBody);
        assertAll("responseBody",
                () -> assertEquals(expectedResponseStatus,
                        JsonPath.parse(responseBody).read("$.status")),

                () -> assertEquals(expectedResponseMessage,
                        JsonPath.parse(responseBody).read("$.message"))
        );

    }


    @DisplayName("unhappy path use case (End to End flow) - required field missing")
    @Tags({
            @Tag("UnhappyPath"),
            @Tag("EndToEnd")
    })
    @Test
    void testRequiredFieldMissing() throws  Exception{

        LOG.info("Test required field missing");
        final  int expectedResponseStatusCode = 400;
        final  String expectedResponseStatus = "ERROR";
        final  String expectedResponseMessage = "firstName: is missing but it is required";

        // set request headers
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        requestHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        // set request payload
        final String requestBody = "{}";

        // prepare request
        final HttpEntity<String> request = new HttpEntity<String>(requestBody ,requestHeaders);

        // invoke post request
        final ResponseEntity responseEntity = restClient.postForEntity(url, request, String.class);

        // validate response entity
        assertNotNull(responseEntity);

        // get response HTTP Code and payload
        final int responseStatusCode = responseEntity.getStatusCode().value();
        final String responseBody =  (String)responseEntity.getBody();

        // validate response status code
        assertEquals(expectedResponseStatusCode,responseStatusCode);

        // validate response payload
        assertNotNull(responseBody);
        assertAll("name",
                () -> assertEquals(expectedResponseStatus,
                        JsonPath.parse(responseBody).read("$.status")),
                () -> assertEquals(expectedResponseMessage,
                        JsonPath.parse(responseBody).read("$.message"))
        );

    }


    @DisplayName("unhappy path use case (End to End flow) - required field value length")
    @Tags({
            @Tag("UnhappyPath"),
            @Tag("EndToEnd")
    })
    @Test
    void testRequiredFieldValueLength() throws  Exception{

        LOG.info("Test required filed value length");
        final  int expectedResponseStatusCode = 400;
        final  String expectedResponseStatus = "ERROR";
        final  String expectedResponseMessage = "firstName: must be at least 3 characters long";

        // set request headers
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        requestHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        // set request payload
        final String requestBody =  mapper
                .writeValueAsString(
                        new Customer("me", "my last name","11112222333","tester@test.com"));

        // prepare request
        final HttpEntity<String> request = new HttpEntity<String>(requestBody ,requestHeaders);

        // invoke post request
        final ResponseEntity responseEntity = restClient.postForEntity(url, request, String.class);

        // validate response entity
        assertNotNull(responseEntity);

        // get response HTTP Code and payload
        final int responseStatusCode = responseEntity.getStatusCode().value();
        final String responseBody =  (String)responseEntity.getBody();

        // validate response status code
        assertEquals(expectedResponseStatusCode,responseStatusCode);

        // validate response payload
        assertNotNull(responseBody);
        assertAll("name",
                () -> assertEquals(expectedResponseStatus,
                        JsonPath.parse(responseBody).read("$.status")),
                () -> assertEquals(expectedResponseMessage,
                        JsonPath.parse(responseBody).read("$.message"))
        );

    }

    @DisplayName("unhappy path use case (End to End flow) - Invalid Json format")
    @Tags({
           @Tag("unhappyPath"),
           @Tag("EndToEnd")
    })
    @Test
    void testInvalidJsonFormat() throws  Exception{

        LOG.info("Test Invalid Request JSON Format Payload");
        final  int expectedResponseStatusCode = 400;
        final  String expectedResponseStatus = "ERROR";
        final  String expectedResponseMessage = "Invalid Json Format";

        // set request headers
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        requestHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        // set request payload
        final String requestBody = "";

        // prepare request
        final HttpEntity<String> request = new HttpEntity<String>(requestBody ,requestHeaders);

        // invoke post request
        final ResponseEntity responseEntity = restClient.postForEntity(url, request, String.class);

        // validate response entity
        assertNotNull(responseEntity);

        // get response HTTP Code and payload
        final int responseStatusCode = responseEntity.getStatusCode().value();
        final String responseBody =  (String)responseEntity.getBody();

        // validate response status code
        assertEquals(expectedResponseStatusCode,responseStatusCode);

        // validate response payload
        assertNotNull(responseBody);
        assertAll("responseBody",
                () -> assertEquals(expectedResponseStatus,
                        JsonPath.parse(responseBody).read("$.status")),
                () -> assertEquals(expectedResponseMessage,
                        JsonPath.parse(responseBody).read("$.message"))
        );

    }


    @BeforeEach
    void init(){
        LOG.info("Init test");
        url = "http://localhost:" + port + "/api/v1/customers";
        mapper = new ObjectMapper();
    }


}
