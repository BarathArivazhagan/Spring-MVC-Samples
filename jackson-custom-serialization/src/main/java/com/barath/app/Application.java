package com.barath.app;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.lang.invoke.MethodHandles;
import java.util.Objects;

@SpringBootApplication
public class Application {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
	private static final ObjectMapper mapper = new ObjectMapper();

	public static void main(String[] args) throws Exception {

		ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);
		String empJson = "{\"employeeId\":1,\n" +
				"  \"employeeName\" : \"Barath\"\n" +
				"}";
		logger.info("employee json source {}",empJson);
		Employee employee = mapper.readValue(empJson, Employee.class);
		logger.info("employee {}", Objects.toString(employee));

	}
}
