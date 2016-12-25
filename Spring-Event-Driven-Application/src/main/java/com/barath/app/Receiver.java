package com.barath.app;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import reactor.bus.Event;
import reactor.fn.Consumer;

@Service
public class Receiver implements Consumer<Event<String>> {	
	
	private static final Logger logger=LoggerFactory.getLogger(Receiver.class);

	public void accept(Event<String> ev) {
	
		logger.info("Receiver accepted");
		logger.info("Receiver name is "+ev.getData());
	
	}

}
