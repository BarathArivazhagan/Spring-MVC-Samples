package com.barath.app;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import  com.barath.app.Application.Model;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {

	@Test
	public void testJacksonFormat() throws JsonParseException, JsonMappingException, IOException, ParseException {
		
		ObjectMapper mapper=new ObjectMapper();
		String date_string = " 10-12-2016 10:00:00"; 
		SimpleDateFormat dt = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss"); 
		Date date = dt.parse(date_string); 
		
		Model model=mapper.readValue(mapper.writeValueAsString(new Model(date)),Model.class);
		System.out.println(mapper.writeValueAsString(model));
	}

}
