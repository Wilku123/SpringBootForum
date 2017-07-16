package com.netstore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.web.bind.annotation.RequestMapping;


@SpringBootApplication
public class NetstoreApplication extends SpringBootServletInitializer
 {
	public static void main(String[] args) {
		SpringApplication.run(NetstoreApplication.class, args);
	}
	 @Override
	 protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		 return application.sources(NetstoreApplication.class);
	 }
}
