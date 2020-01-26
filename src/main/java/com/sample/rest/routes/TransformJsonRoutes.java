package com.sample.rest.routes;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class TransformJsonRoutes extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        // defined endpoint transformer for Front JSON to Backend JSON
        transformer()
                .fromType("json:frontend")
                .toType("json:backend")
                .withUri("jolt:{{trasformer.spec}}?inputType=JsonString&outputType=JsonString&contentCache=true");


        // Transform Frontend JSON to Backend Json
        from("direct:frontendJsonToBackendJson")
                .routeId("frontend-Json-To-backend-Json-route")
                .inputType("json:frontend")
                .log("Frontend JSON  Received: ${body}")
                .to("direct:transformToBackendJson");

        // Transform to Backend JSON using transformer defined
        from("direct:transformToBackendJson")
                .routeId("transform-to-backend-Json-route")
                .inputType("json:backend")
                .log("Backend JSON  Transformed: ${body}");
    }
}
