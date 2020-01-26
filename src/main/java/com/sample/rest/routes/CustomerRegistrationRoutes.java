package com.sample.rest.routes;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.stereotype.Component;

@Component
public class CustomerRegistrationRoutes extends RouteBuilder {


    @Override
    public void configure() throws Exception {

        // handle all exception and propagate to service layer
        onException(Exception.class)
                .handled(false);

        from("direct:CustomerRegistration")
                .routeId("customer-registration-route")
                .marshal().json(JsonLibrary.Jackson)    // Object message into json
                .to("direct:validateFrontendJson")      // defined in ValidateJsonRoutes
                .to("direct:frontendJsonToBackendJson") // defined in TransformJsonRoutes
                .to("direct:validateBackendJson")       // defined in ValidateJsonRoutes
                .to("direct:callBackendService") ;      // defined in BackendRestRoutes
    }
}
