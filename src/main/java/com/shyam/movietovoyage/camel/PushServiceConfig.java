package com.shyam.movietovoyage.camel;

import com.google.gson.Gson;
import com.shyam.movietovoyage.core.Database;
import com.shyam.movietovoyage.core.ExtractRequest;
import com.shyam.movietovoyage.core.PredictionResponse;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;


@Component
public class PushServiceConfig extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        from("seda:predictionResults")
                .process(exchange -> {

                    PredictionResponse predictionResponse = exchange.getIn().getBody(PredictionResponse.class);
                    if (null != predictionResponse) {
                        ExtractRequest request = Database.read(predictionResponse.getVideoUuid());
                        if (null != request) {
                            exchange.setProperty("OUT_URL", request.getPushUrl());

                            Gson gson = new Gson();
                            String payload = gson.toJson(predictionResponse);

                            exchange.getIn().setBody(payload);
                        }
                    }
                })
        .toD("${exchangeProperty.OUT_URL}");

    }
}
