package com.barath.demo.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class SpringRestTemplateHttpsApplication implements CommandLineRunner{
	
	@Autowired
	private RestService restService;

	public static void main(String[] args) {
		SpringApplication.run(SpringRestTemplateHttpsApplication.class, args);
	}
	
	@GetMapping("/")
	public String getResponse(){
		return restService.invokeRESTService();
	}

	@Override
	public void run(String... arg0) throws Exception {
		
		
	}
}
