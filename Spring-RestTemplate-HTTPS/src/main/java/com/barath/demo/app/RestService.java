package com.barath.demo.app;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;



@Service
public class RestService {
	
	private RestTemplate restTemplate=null;
	private static final String TEST_URL="https://192.168.1.42:8082/RESTCLIENT/test";
	
	public RestService(){
		this.restTemplate=new RestTemplate();
	}
	
	public String invokeRESTService(){
		//SSLContextHelper.enable();
		
		SSLContextHelper.disable();
		String message=this.restTemplate.getForObject(TEST_URL, String.class);
		System.out.println("response "+message);
		
		return message;
	}

	public RestTemplate getRestTemplate() {
		return restTemplate;
	}

	public void setRestTemplate(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}
	
	
	
	
	
}
