package com.barath.app;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.invoke.MethodHandles;
import java.util.Objects;

@RunWith(SpringRunner.class)
public class JacksonTests {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
	private static final ObjectMapper mapper = new ObjectMapper();
	private static final ObjectMapper mapperWithIgnorUnkownProps = new ObjectMapper();

	static {
		mapperWithIgnorUnkownProps.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}

	@Test
	public void testSerAndDeserialization() throws Exception {

		String empJson = "{\"employeeId\":1,\n" +
				"  \"employeeName\" : \"Barath\"\n" +
				"}";
		logger.info("employee json source {}",empJson);
		Employee employee = mapper.readValue(empJson, Employee.class);
		logger.info("employee {}", Objects.toString(employee));

		Assert.assertThat(empJson, CoreMatchers.containsString("Barath"));
		Assert.assertEquals("Barath",employee.getEmployeeName());
	}

	@Test
	public void testSerAndDeserializationWithoutAProperty() throws Exception {

		String empJson = "{ \"employeeName\" : \"Barath\"\n" +"}";
		logger.info("employee json source {}",empJson);
		Employee employee = mapper.readValue(empJson, Employee.class);
		logger.info("employee {}", Objects.toString(employee));

		Assert.assertThat(empJson, CoreMatchers.containsString("Barath"));
		Assert.assertEquals("Barath",employee.getEmployeeName());
	}

	@Test(expected = UnrecognizedPropertyException.class)
	public void testSerAndDeserializationWithInvalidPropertyName() throws Exception {

		String empJson = "{ \"employee\" : \"Barath\"\n" +"}";
		logger.info("employee json source {}",empJson);
		Employee employee = mapper.readValue(empJson, Employee.class);
		logger.info("employee {}", Objects.toString(employee));

		Assert.assertThat(empJson, CoreMatchers.containsString("Barath"));
		Assert.assertEquals("Barath",employee.getEmployeeName());
	}


	@Test
	public void testSerAndDeserializationWithPropertyNameIgnore() throws Exception {

		String empJson = "{ \"employee\" : \"Barath\"\n" +"}";
		logger.info("employee json source {}",empJson);
		Employee employee = mapperWithIgnorUnkownProps.readValue(empJson, Employee.class);
		logger.info("employee {}", Objects.toString(employee));

		Assert.assertNull(employee.getEmployeeName());
		Assert.assertNull(employee.getEmployeeId());
	}

}
