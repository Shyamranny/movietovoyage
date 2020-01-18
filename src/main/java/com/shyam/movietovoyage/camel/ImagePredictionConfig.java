package com.shyam.movietovoyage.camel;

import com.shyam.movietovoyage.core.PredictionResponse;
import com.shyam.movietovoyage.core.PredictionResult;
import org.apache.camel.builder.RouteBuilder;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Component
public class ImagePredictionConfig extends RouteBuilder {

    private static final Logger LOGGER = LoggerFactory.getLogger(ImagePredictionConfig.class);

    @Value("${imageExtractor.extractedImageFolder}")
    private String extractedImageFolder;

    @Value("${prediction.command}")
    private String predictionCommand;

    @Override
    public void configure() throws Exception {

        from("file://" + extractedImageFolder + "?delay=10000&recursive=true&delete=false&moveFailed=../../errors&move=../../processed")
                .process(exchange -> {

                    File file = exchange.getIn().getBody(File.class);
                    LOGGER.info("Processing file: " + file);

                    String[] commands = {"python", predictionCommand, file.toURI().toURL().toString()};
                    Process proc = Runtime.getRuntime().exec(commands);

                    InputStream stdIn = proc.getInputStream();
                    InputStreamReader isr = new InputStreamReader(stdIn);
                    BufferedReader br = new BufferedReader(isr);
                    BufferedReader errinput = new BufferedReader(new InputStreamReader(
                            proc.getErrorStream()));

                    List<PredictionResult> predictionResults = new ArrayList<>();

                    String line;
                    while ((line = br.readLine()) != null) {
                        if (line.startsWith("rank")){

                            predictionResults.add(PredictionResult.createFromResult(line));
                        }
                    }

                    while ((line = errinput.readLine()) != null) {
                        LOGGER.error(line);
                    }

                    int exitVal = proc.waitFor();
                    LOGGER.info("Process exitValue: " + exitVal);

                    if (!predictionResults.isEmpty()) {

                        String[] metadata = file.getName().split("_");

                        PredictionResponse predictionResponse = new PredictionResponse(
                                predictionResults,
                                metadata[0],
                                Long.parseLong(metadata[2].replace(".png", "")),
                                        file.getName()
                        );

                        exchange.getIn().setBody(predictionResponse);
                    } else {
                        LOGGER.warn("Stopping route because there are no predictions!!");
                        exchange.getContext().stop();
                    }

                })
        .to("seda:predictionResults")
        .log("PredictionResponse added");

    }
}
