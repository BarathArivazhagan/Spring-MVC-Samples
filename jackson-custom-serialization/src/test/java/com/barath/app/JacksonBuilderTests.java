package com.barath.app;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.Objects;

public class JacksonBuilderTests {

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Test
    public void testSerAndDeserialization() throws Exception {

        String empJson = "{\"employeeId\":1,\n" +
                "  \"employeeName\" : \"Barath\"\n" +
                "}";
        logger.info("employee json source {}",empJson);
        Employee employee = ObjectMapperBuilder.build().readValue(empJson, Employee.class);
        logger.info("employee {}", Objects.toString(employee));

        Assert.assertThat(empJson, CoreMatchers.containsString("Barath"));
        Assert.assertEquals("Barath",employee.getEmployeeName());
    }

    @Test
    public void testSerAndDeserializationWithoutAProperty() throws Exception {

        String empJson = "{ \"employeeName\" : \"Barath\"\n" +"}";
        logger.info("employee json source {}",empJson);
        Employee employee = ObjectMapperBuilder.build().readValue(empJson, Employee.class);
        logger.info("employee {}", Objects.toString(employee));

        Assert.assertThat(empJson, CoreMatchers.containsString("Barath"));
        Assert.assertEquals("Barath",employee.getEmployeeName());
    }

    @Test(expected = UnrecognizedPropertyException.class)
    public void testSerAndDeserializationWithInvalidPropertyName() throws Exception {

        String empJson = "{ \"employee\" : \"Barath\"\n" +"}";
        logger.info("employee json source {}",empJson);
        Employee employee = ObjectMapperBuilder.build().readValue(empJson, Employee.class);
        logger.info("employee {}", Objects.toString(employee));

        Assert.assertThat(empJson, CoreMatchers.containsString("Barath"));
        Assert.assertEquals("Barath",employee.getEmployeeName());
    }


    @Test
    public void testSerAndDeserializationWithPropertyNameIgnore() throws Exception {

        String empJson = "{ \"employee\" : \"Barath\"\n" +"}";
        logger.info("employee json source {}",empJson);
        Employee employee = ObjectMapperBuilder.with(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES).build().readValue(empJson, Employee.class);
        logger.info("employee {}", Objects.toString(employee));

        Assert.assertNull(employee.getEmployeeName());
        Assert.assertNull(employee.getEmployeeId());
    }

    @Test
    public void testWithJsonPropertyAnnotation() throws Exception  {

        String teamJson = "{ \"teamName\":\"INDIA\"}";
        logger.info("team json source {}",teamJson);
        Team team = ObjectMapperBuilder.with(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES).build().readValue(teamJson, Team.class);
        logger.info("team {}", Objects.toString(team));
    }
}
