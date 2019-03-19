package com.barath.app.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import com.barath.app.controller.WelcomeController;

public class MVCWebInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
 
	 @Override
	    protected Class<?>[] getRootConfigClasses() {
	        return new Class[] { MVCConfiguration.class, WelcomeController.class,ActuatorConfig.class };
	    }
	  
	    @Override
	    protected Class<?>[] getServletConfigClasses() {
	        return null;
	    }
	  
	    @Override
	    protected String[] getServletMappings() {
	        return new String[] { "/" };
	    }
}