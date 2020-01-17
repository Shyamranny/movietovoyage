package com.shyam.movietovoyage;

import com.shyam.movietovoyage.extractor.ImageExtractor;
import org.apache.camel.component.servlet.CamelHttpTransportServlet;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.DispatcherServlet;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

@SpringBootApplication
public class MovietovoyageApplication {

	@Value("${webservice.api.path}")
	String contextPath;

	public static void main(String[] args) throws Exception {
		SpringApplication.run(MovietovoyageApplication.class, args);

//		String videoFilePath = "/Users/shyam/Documents/work/movietovoyage/files/KISSMS.mp4";
//
//		ImageExtractor imageExtractor = new ImageExtractor(new File(videoFilePath), 10);
//		imageExtractor.extract(new ImageExtractor.Subscriber() {
//			@Override
//			public void subscribe(BufferedImage image) {
//				try {
//					ImageIO.write(image, "png", new File("files/output/video-frame-" + System.currentTimeMillis() + ".png"));
//
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//
//			@Override
//			public void finished() {
//				System.out.println("================DONE=================");
//			}
//
//			@Override
//			public void error(String message, Throwable exception) {
//				System.err.println(exception);
//			}
//		});


	}


	@Bean
	ServletRegistrationBean servletRegistrationBean() {
		ServletRegistrationBean servlet = new ServletRegistrationBean(new CamelHttpTransportServlet(), contextPath+"/*");
		servlet.setName("CamelServlet");
		return servlet;
	}


}
