package com.barath.app;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

//@RunWith(SpringRunner.class)
//@SpringBootTest
public class TestSpringProivder {
	
	@Test
	public void testGet(){
		
		 HttpComponentsClientHttpRequestFactory requestFactory 
	      = new HttpComponentsClientHttpRequestFactory();
		RestTemplate restTemplate=new RestTemplate(requestFactory);
		
		//String response=restTemplate.getForObject("http://localhost:8080/get", String.class);
		//System.out.println(response);
		String get="http://localhost:8080/get/{customerid}";
		Map<String,String> uriVariables=new HashMap<>();
		uriVariables.put("customerid", "100000");
		System.out.println(UriComponentsBuilder.fromHttpUrl(get).buildAndExpand(uriVariables).toUri());
	}

}
