package com.shyam.movietovoyage.camel;

import com.shyam.movietovoyage.core.ExtractRequest;
import com.shyam.movietovoyage.extractor.ImageExtractor;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

@Component
public class ImageExtractConfig extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        from("seda:video-extract-request")
        .process(exchange -> {

            ExtractRequest request = exchange.getIn().getBody(ExtractRequest.class);

            ImageExtractor imageExtractor = new ImageExtractor(request.getVideoUrl(), request.getFrameDurationInSecond());

            List<BufferedImage> list = imageExtractor.extract();
            exchange.getIn().setBody(list);

        })
        .split().body()
        .to("seda:extractedImages")
        .log("Image saved to extractedImages");

    }
}
