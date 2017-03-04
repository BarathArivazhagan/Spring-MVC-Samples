package com.barath.app;

import java.io.File;
import java.io.IOException;

import javax.net.ssl.SSLContext;


import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringRestConsumerApplicationTests {
	
	private static final String URL="https://abpsapi-exec-gcb-apac-uat2-abps.cfapps-gcg-mwdc-apac2.citipaas-uat.dyn.nsroot.net/private/v1/moneyMovement/cards/billPayments/enrollments/search";
	private static final String CONTENT_TYPE="Content-Type";
	private static final String DEFAULT_CONTENT_TYPE="application/json; charset=utf-8";
	private static final String ACCEPT_TYPE="Accept";
	private static final String DEFAULT_ACCEPT_TYPE="application/json; charset=utf-8";
	
//	static{
//		System.setProperty("javax.net.ssl.trustStore", "C://Users//ba53395//Desktop//CitirootCAkeystore.jks");
//		System.setProperty("javax.net.ssl.trustStorePassword", "1q2w3e");
//	}
	
	@Test
	public void testRest() throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper= new ObjectMapper();
//		SSLContext sslContext = (SSLContext) SSLContextHelper.getSSLContext("C://Users//ba53395//Desktop//CitirootCAkeystore.jks", "1q2w3e");
//		SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext);
//		CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
//		HttpComponentsClientHttpRequestFactory httpComponentsClientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
//		RestTemplate restTemplate=new RestTemplate(httpComponentsClientHttpRequestFactory);
		SSLContextHelper.disable();
		RestTemplate restTemplate=new RestTemplate();
		String body=mapper.writeValueAsString(mapper.readValue(new File("C://Users//ba53395//EclipseWorkspaces//NeonWs//LearningWS//Spring-REST-Consumer//src//test//java//com//barath//app//request.json"), Object.class));
		System.out.println("body "+body);
		HttpHeaders headers=new HttpHeaders();
		headers.add("client_id", "anup");
		headers.add(CONTENT_TYPE,DEFAULT_CONTENT_TYPE);
		headers.add(ACCEPT_TYPE,DEFAULT_ACCEPT_TYPE);
		ResponseEntity<String> responseEntity=restTemplate.exchange(URL, HttpMethod.POST, new HttpEntity<String>(body,headers), String.class);
		System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(responseEntity));
	}

}
