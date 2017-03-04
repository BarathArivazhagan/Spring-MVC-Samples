package com.barath.app.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.barath.app.model.Employee;


@Service
public class EmployeeService {
	
	private Map<String,Employee> empMap=new HashMap<String,Employee>();
	
	public void addEmployee(Employee emp){
		System.out.println("ADDING EMLOYEE WITH EMPLOYEE ID"+emp.getEmpId());
		empMap.put(emp.getEmpId(), emp);
	}
	
	public Employee getEmployee(String empId){
	
		Employee emp=empMap.get(empId);
		if(emp != null){
			return emp;
		}
		return null;	
	}
	
	public void updateEmployee(Employee emp){
		System.out.println("UPDATING the employee with emploee id "+emp.getEmpId());
		if(empMap.containsKey(emp.getEmpId())){
			empMap.put(emp.getEmpId(), emp);			
		}
	}
	
	
	public void deleteEmployee(Employee emp){
		System.out.println("DELETING the employee with emploee id "+emp.getEmpId());
		if(empMap.containsKey(emp.getEmpId())){
			empMap.remove(emp);			
		}
	}

}
