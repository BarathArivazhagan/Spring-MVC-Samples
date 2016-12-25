package com.barath.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.bus.Event;
import reactor.bus.EventBus;


@Service
public class Publisher {
	
	private static final Logger logger=LoggerFactory.getLogger(Publisher.class);
	
	@Autowired
	EventBus eventBus;


	public void publishEvent(String name) throws InterruptedException {
			logger.info("publisher is called");		
			eventBus.notify("name", Event.wrap(name));
	}

	
	

}
