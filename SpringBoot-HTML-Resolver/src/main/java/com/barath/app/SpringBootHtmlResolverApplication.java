package com.barath.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@SpringBootApplication
@Controller
public class SpringBootHtmlResolverApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootHtmlResolverApplication.class, args);
	}
	
	@RequestMapping(value = "/sample")
    public String sample() {
		
        return "index";
    }
}
