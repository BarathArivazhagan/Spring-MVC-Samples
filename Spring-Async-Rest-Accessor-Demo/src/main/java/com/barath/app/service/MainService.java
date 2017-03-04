package com.barath.app.service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.client.AsyncRestTemplate;

import com.barath.app.model.Employee;
import com.barath.app.model.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class MainService {
	
	private static final String ADD_EMPLOYEE_URL="http://localhost:8085/addEmp";
	private static final String GET_EMPLOYEE_URL="http://localhost:8085/getEmp/{empId}";
	
	@Autowired
	private AsyncRestTemplate asyncTemp;
	
	private ObjectMapper objectMapper=new ObjectMapper();
	
	public ResponseEntity<?> invokeAddEmployee(Employee emp) throws JsonProcessingException, InterruptedException, ExecutionException{
		
		System.out.println("INVOKE ADD EMPLOYEE IS CALLED");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		System.out.println("WRITING EMPLOYEE OBJECT AS JSON STRING "+objectMapper.writeValueAsString(emp));
		HttpEntity<Object> requestEntity=new HttpEntity<Object>(objectMapper.writeValueAsString(emp));
		ListenableFuture<ResponseEntity<String>> future=asyncTemp.postForEntity(ADD_EMPLOYEE_URL,requestEntity,String.class);
		
		
		return future.get();
	}
	
	public ResponseEntity<?> invokeAddUser(User user){
		System.out.println("INVOKE ADD USER IS CALLED");
		return null;
	}
	
	public ResponseEntity<?> invokeGetEmployee(String empId) throws InterruptedException, ExecutionException{
		
		
		System.out.println("INVOKE GET EMPLOYEE IS CALLED");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
	
	
		Map<String,Object> urlVariables=new HashMap<String,Object>();
		urlVariables.put("empId", empId);
		ListenableFuture<ResponseEntity<String>> future=asyncTemp.getForEntity(GET_EMPLOYEE_URL, String.class, urlVariables);
		
		
		return future.get();
		
	}

}
