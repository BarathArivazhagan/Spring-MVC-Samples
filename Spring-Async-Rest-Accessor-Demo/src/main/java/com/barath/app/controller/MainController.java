package com.barath.app.controller;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.AsyncRestTemplate;

import com.barath.app.model.Employee;
import com.barath.app.model.User;
import com.barath.app.service.EmployeeService;
import com.barath.app.service.MainService;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class MainController {
	
	
	@Autowired
	private MainService mainService;
	
	private ObjectMapper objectMapper=new ObjectMapper();
	
	
	
	
	@RequestMapping(value="/add/{what}")
	public ResponseEntity<?> handleAdd(@PathVariable("what") String what,@RequestBody String jsonReq) throws JsonParseException, JsonMappingException, IOException, InterruptedException, ExecutionException{
		ResponseEntity<?>  response=null;
		System.out.println("HANDLE ADD  IS CALLED");
		System.out.println("WHAT IS "+what);
		System.out.println("JSON REQUEST "+jsonReq);
		if(!StringUtils.isEmpty(what) && "emp".equalsIgnoreCase(what) ){
			Employee emp=objectMapper.readValue(jsonReq, Employee.class);
			response=mainService.invokeAddEmployee(emp);			
		}else if(!StringUtils.isEmpty(what) && "user".equalsIgnoreCase(what)){
			User user=objectMapper.readValue(jsonReq, User.class);
			response=mainService.invokeAddUser(user);
		}
		
		return response;
		
	}
	
	@RequestMapping(value="/get/{what}")
	public ResponseEntity<?> handleGet(@PathVariable("empId") String employeeId) throws InterruptedException, ExecutionException{
		
		System.out.println("HANDLE GET  IS CALLED");
		if(!StringUtils.isEmpty(employeeId)){
			ResponseEntity<?> emp=mainService.invokeGetEmployee(employeeId);
			if(emp !=null){
				System.out.println("EMPLOYEE IS NOT NULL "+emp.toString());
				return emp;
			}
		}
		
		
		
		return null;
		
	}

}
