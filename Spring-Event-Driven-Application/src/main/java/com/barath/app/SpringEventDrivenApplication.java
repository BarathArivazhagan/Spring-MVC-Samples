package com.barath.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import static reactor.bus.selector.Selectors.$;

import java.util.Arrays;
import java.util.List;

import reactor.Environment;
import reactor.bus.EventBus;

@SpringBootApplication
public class SpringEventDrivenApplication implements CommandLineRunner {
	
	private static final Logger logger=LoggerFactory.getLogger(SpringEventDrivenApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(SpringEventDrivenApplication.class, args);
	}
	
	@Bean
    Environment env() {
        return Environment.initializeIfEmpty()
                          .assignErrorJournal();
    }
	
	@Bean
    EventBus createEventBus(Environment env) {
	    return EventBus.create(env, Environment.THREAD_POOL);
    }
	
	@Autowired
	private EventBus eventBus;

	@Autowired
	private Receiver receiver;

	@Autowired
	private Publisher publisher;
	
	@Override
	public void run(String... arg0) throws Exception {
		
		eventBus.on($("name"), receiver);
		List<String> names=Arrays.asList("barath","barry");
		for(String name:names){
			publisher.publishEvent(name);
		}
		
	}
}
