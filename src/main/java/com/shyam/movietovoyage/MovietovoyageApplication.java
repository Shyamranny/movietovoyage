package com.shyam.movietovoyage;

import org.apache.camel.component.servlet.CamelHttpTransportServlet;
import org.apache.mxnet.javaapi.NDArray$;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class MovietovoyageApplication {

	@Value("${webservice.api.path}")
	private String contextPath;

	static NDArray$ NDArray = NDArray$.MODULE$;

	public static void main(String[] args) {
		SpringApplication.run(MovietovoyageApplication.class, args);
	}


	@Bean
	ServletRegistrationBean servletRegistrationBean() {
		ServletRegistrationBean servlet = new ServletRegistrationBean(new CamelHttpTransportServlet(), contextPath+"/*");
		servlet.setName("CamelServlet");
		return servlet;
	}


}
