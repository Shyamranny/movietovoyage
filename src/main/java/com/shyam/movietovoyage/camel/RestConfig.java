package com.shyam.movietovoyage.camel;

import com.shyam.movietovoyage.core.ExtractRequest;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class RestConfig extends RouteBuilder {

    static final String PROPERTY_VIDEO_UUID = "VIDEO_UUID";

    @Value("${webservice.api.path}")
    private String contextPath;

    @Value("${server.port}")
    private String serverPort;

    @Override
    public void configure() throws Exception {
        CamelContext context = new DefaultCamelContext();

        restConfiguration()
                .contextPath(contextPath)
                .port(serverPort)
                .enableCORS(true)
                .apiContextPath("/api-doc")
                .apiProperty("api.title", "Test REST API")
                .apiProperty("api.version", "v1")
                .apiContextRouteId("doc-api")
                .component("servlet")
                .bindingMode(RestBindingMode.json);

        rest("/extract/")
                .id("api-route")
                .consumes("application/json")
                .post("/video")
                .bindingMode(RestBindingMode.json)
                .type(ExtractRequest.class)
                .to("direct:remoteService");

        from("direct:remoteService")
                .setProperty(PROPERTY_VIDEO_UUID, simple(UUID.randomUUID().toString()))
                .wireTap("seda:video-extract-request")
                .routeId("direct-route")
                .log(">>> ${body.videoUrl}")
                .log(">>> ${body.pushUrl}")
                .log(">>> ${body.frameDurationInSecond}")
                .setBody(simple("${exchangeProperty.VIDEO_UUID}"))
                .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(200));

    }
}
