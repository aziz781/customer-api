package com.sample.rest.routes;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class ValidateJsonRoutes extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        // defined endpoint validator for frontend json
        validator()
                .type("json:frontend")
                .withUri("json-validator:{{frontend.json.schema}}");

        // defined endpoint validator for backend json
        validator()
                .type("json:backend")
                .withUri("json-validator:{{backend.json.schema}}");


        // Validate Front JSON Route
        from("direct:validateFrontendJson")
                .routeId("validate-frontend-Json-route")
                .log("Frontend JSON  Received: ${body}")
                .inputTypeWithValidate("json:frontend")
                .log("Frontend JSON  Validation: SUCCESS");

        // Validate Backend JSON Route
        from("direct:validateBackendJson")
                .routeId("validate-backend-Json-route")
                .log("Backend JSON  Received: ${body}")
                .inputTypeWithValidate("json:backend")
                .log("Backend JSON  Validation: SUCCESS");

    }
}
