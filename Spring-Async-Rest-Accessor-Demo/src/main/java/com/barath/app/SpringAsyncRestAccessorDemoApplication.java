package com.barath.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.AsyncRestTemplate;

@SpringBootApplication
public class SpringAsyncRestAccessorDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringAsyncRestAccessorDemoApplication.class, args);
	}
	
	@Bean
	public AsyncRestTemplate aysncTemplate(){
		return new AsyncRestTemplate();
	}
}
