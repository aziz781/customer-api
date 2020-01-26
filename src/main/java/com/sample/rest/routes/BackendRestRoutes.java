package com.sample.rest.routes;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.stereotype.Component;
import springfox.documentation.spring.web.scanners.MediaTypeReader;

@Component
public class BackendRestRoutes extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        // Config Netty HTTP as a HTTP server for Rest
        restConfiguration()
                .component("netty-http")
                .host("{{mock.service.host}}")
                .port("{{mock.service.port}}")
                .clientRequestValidation(true)
                .bindingMode(RestBindingMode.auto);


        // Defined Mock Backed endpoint
        // POST http://localhost:9001/registrations
        rest("{{mock.service.path}}")
                .post()
                .produces("text/plain")
                .consumes("application/json")
                .route()
                .marshal().json(JsonLibrary.Jackson)
                .log("Backend Service Received Payload : ${body}")
                .setHeader(Exchange.CONTENT_TYPE, constant("text/plain"))
                .transform().constant("Customer registered successfully");


        from("direct:callBackendService")
                .routeId("call-backend-service-route")
                .log("Backend Service Request Payload : ${body}")
                .setHeader(Exchange.HTTP_METHOD, constant("POST"))
                .setHeader(Exchange.CONTENT_TYPE, constant("application/json"))
                .to("netty-http:{{mock.service.url}}")
                .log("Backend Response Payload : ${body}");
    }
}
