package com.barath.app;

import java.util.concurrent.CountDownLatch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import reactor.bus.Event;
import reactor.fn.Consumer;

@Service
public class Receiver implements Consumer<Event<Integer>> {	

	public void accept(Event<Integer> ev) {
		
		System.out.println("Receiver accepted");
	
	}

}
