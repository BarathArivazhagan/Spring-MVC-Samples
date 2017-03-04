package com.barath.app.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.AsyncRestTemplate;

import com.barath.app.model.Employee;
import com.barath.app.service.EmployeeService;

@RestController
public class AsynRestController {
	
	@Autowired
	private AsyncRestTemplate asyncTemp;
	
	@Autowired
	private EmployeeService empService;
	
	
	
	@RequestMapping(value="/addEmp",method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE)
	public String handleAddEmployee(@RequestBody Employee emp){
		
		System.out.println("HANDLE ADD EMPLOYEE IS CALLED");
		Assert.notNull(emp,"EMPLOYEE cannot be null in request body ");
		
		empService.addEmployee(emp);
		
		return "EMPLOYEE IS ADDED SUCCESSFULLY ADDED";
		
	}
	
	@RequestMapping(value="/getEmp/{empId}")
	public Employee handleGetEmployee(@PathVariable("empId") String employeeId){
		
		System.out.println("HANDLE GET EMPLOYEE IS CALLED");
		if(!StringUtils.isEmpty(employeeId)){
			Employee emp=empService.getEmployee(employeeId);
			if(emp !=null){
				System.out.println("EMPLOYEE IS NOT NULL "+emp.toString());
				return emp;
			}
		}
		
		
		
		return null;
		
	}

}
