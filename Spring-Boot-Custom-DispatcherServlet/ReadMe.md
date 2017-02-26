### Spring Boot Custom Dispatcher Servlet 


This demo project demonstrates the use of custom dispatcher servlet in spring boot application :

Overrding the DispatcherServletAutoConfiguration by creating a bean of DispatcherServlet and registering the servlet 
into the servlet context by creating a bean of ServletRegistrationBean 

```
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
```
