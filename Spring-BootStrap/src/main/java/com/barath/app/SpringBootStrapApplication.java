package com.barath.app;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootStrapApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootStrapApplication.class, args);
	}
	
	@Value("${key1:defaultValue}")
	private String prop1;
	
	@Value("${key2:defaultValue}")
	private String prop2;
	
	
	@PostConstruct
	public void init(){
		
		System.out.println(" Prop 1"+prop1+" Prop 2 "+prop2);
		
	}
}
