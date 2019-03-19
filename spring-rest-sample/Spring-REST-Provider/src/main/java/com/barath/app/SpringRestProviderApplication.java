package com.barath.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class SpringRestProviderApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringRestProviderApplication.class, args);
	}
	
	@RequestMapping(value="/get",method=RequestMethod.GET)
	public String get(){
		return "Hello from Rest client GET METHOD";
	}
	
	
	@RequestMapping(value="/post",method=RequestMethod.POST)
	public String post(){
		return "Hello from Rest client POST METHOD";
	}
}
