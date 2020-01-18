package com.shyam.movietovoyage.camel;

import com.shyam.movietovoyage.core.Database;
import com.shyam.movietovoyage.core.ExtractRequest;
import com.shyam.movietovoyage.core.VideoImage;
import com.shyam.movietovoyage.extractor.ImageExtractor;
import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.io.File;
import java.util.List;

@Component
public class ImageExtractConfig extends RouteBuilder {

    private static final Logger LOGGER = LoggerFactory.getLogger(ImageExtractConfig.class);

    @Value("${imageExtractor.extractedImageFolder}")
    private String extractedImageFolder;

    @Override
    public void configure() throws Exception {

        from("seda:video-extract-request")
        .id("ImageExtractRoute")
        .process(exchange -> {

            ExtractRequest request = exchange.getIn().getBody(ExtractRequest.class);

            String videoUuid = exchange.getProperty(RestConfig.PROPERTY_VIDEO_UUID, String.class);

            Database.insert(videoUuid, request);

            LOGGER.debug("ExtractRequest received:{}", request);

            ImageExtractor imageExtractor = new ImageExtractor(request.getVideoUrl(), request.getFrameDurationInSecond());

            List<VideoImage> images = imageExtractor.extract();

            if (null != images && !images.isEmpty()) {
                exchange.getIn().setBody(images);
            }

            LOGGER.debug("Size of extracted image:{}", images.size());

        })
        .process(exchange -> {

            List<VideoImage> images = exchange.getIn().getBody(List.class);

            String videoUuid = exchange.getProperty(RestConfig.PROPERTY_VIDEO_UUID, String.class);

            if (null == images && images.isEmpty()){
                LOGGER.warn("Empty image list for video uuid:" + videoUuid);
                return;
            }

            File file = new File(new File(extractedImageFolder), videoUuid);
            if (!file.exists()){
                if(!file.mkdir()){
                    LOGGER.error("Unable to create folder:" + file.getPath());
                    LOGGER.warn("Stopping route because unable to create folder!!");
                    exchange.getContext().stop();
                }
            }

            for (int i = 0; i < images.size(); i++) {
                ImageIO.write(images.get(i).getBufferedImage(), "png", new File(file, videoUuid + "_" + i + "_" + images.get(i).getTimestamp() + ".png"));
            }


        })
        .log("Image saved to extractedImages");

    }
}
