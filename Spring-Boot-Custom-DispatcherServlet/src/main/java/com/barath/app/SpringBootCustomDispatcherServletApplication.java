package com.barath.app;

import java.util.HashMap;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.XmlEmbeddedWebApplicationContext;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.context.support.XmlWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

@SpringBootApplication
@RestController
public class SpringBootCustomDispatcherServletApplication extends ServletInitializer {
	
	@Autowired
	private ApplicationContext rootContext;
	
	@Autowired
	private ServletContext servletContext;

	public static void main(String[] args) {
		SpringApplication.run(SpringBootCustomDispatcherServletApplication.class, args);
	}
	
	@Bean
	protected ServletRegistrationBean dispatcherServletBean(){
		ServletRegistrationBean dispatcherServlet= new ServletRegistrationBean(dispatcherServlet(), "/services/*");
		dispatcherServlet.setLoadOnStartup(0);
		return dispatcherServlet;
	}
	
	@Bean	
	public DispatcherServlet dispatcherServlet(){		
		XmlWebApplicationContext context=new XmlWebApplicationContext();
		context.setConfigLocation("classpath:/mvc-dispatcher-servlet.xml");
		System.out.println("dispatcher servlet is initialized ");
		return new DispatcherServlet(context);
	}
	
	@GetMapping("/")
	public String home(){
		for(String bean : rootContext.getBeanDefinitionNames()){
			System.out.println("bean name is "+bean);
		}
		return "WELCOME TO SPRING WEB MVC APPLICATION ";
	}
	
	@GetMapping("/web")
	public String inspectWebApplicationContext(){
		System.out.println("Web application context ");
		WebApplicationContext webContext=dispatcherServlet().getWebApplicationContext();
		for(String bean : webContext.getBeanDefinitionNames()){
			System.out.println("bean name is "+bean);
		}
		return "WELCOME TO SPRING WEB MVC APPLICATION ";
	}
}
