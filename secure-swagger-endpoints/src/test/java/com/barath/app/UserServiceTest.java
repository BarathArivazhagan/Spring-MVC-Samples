package com.barath.app;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class UserServiceTest {
	
	@Mock
	UserRepository userRepository;
	
	@Before 
	public void setup(){
		
		
		when(userRepository.findByUsername("jonki97")).thenReturn("jonki97");
	}
	
		@Test
	    public void testExistsUsername() {
	        String user = userRepository.findByUsername("jonki97");
	        assertEquals("jonki97", user);

	    }

}
