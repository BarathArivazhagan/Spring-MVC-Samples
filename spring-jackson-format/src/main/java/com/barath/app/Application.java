package com.barath.app;

import java.util.Date;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
	@PostMapping("/format")
	public Model postModel(@RequestBody Model model){
		System.out.println("Model model"+model.toString());
		Date date=model.getDate();
		System.out.println("Date "+date);
		return model;
		
	}
	
	
	
	
	protected static class Model{
		
		private Date date;

		public Date getDate() {
			return date;
		}

		public void setDate(Date date) {
			this.date = date;
		}

		public Model(Date date) {
			super();
			this.date = date;
		}

		public Model() {
			super();
			
		}

		@Override
		public String toString() {
			return "Model [date=" + date + "]";
		}	
		
		
	}
}
