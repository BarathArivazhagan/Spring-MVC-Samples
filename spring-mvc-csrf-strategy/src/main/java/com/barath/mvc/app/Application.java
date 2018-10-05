package com.barath.mvc.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.lang.invoke.MethodHandles;
import java.util.Arrays;

@SpringBootApplication
@Controller
public class Application {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public FilterRegistrationBean csrfFilter(){
		System.out.println("registring csrf filters");
		FilterRegistrationBean csrfFilter = new FilterRegistrationBean();
		csrfFilter.setFilter(new CsrfFilter(new CookieCsrfTokenRepository()));
		csrfFilter.setUrlPatterns(Arrays.asList("/"));
		return csrfFilter;
	}

	@GetMapping("/index")
	public String index(){
		return "welcome home";
	}

	@PostMapping(value="/submit",consumes="application/x-www-form-urlencoded")
	public ModelAndView welcomeView(HttpServletRequest request){


		if(logger.isInfoEnabled()) {
			logger.info("request to load welcome view");
		}

		String userName=request.getParameter("userName");
		String password=request.getParameter("password");
		User user=new User(userName, password);
		ModelAndView welcomeView=new ModelAndView(WELCOME_VIEW);
		welcomeView.addObject("userName", user.getUserName());
		welcomeView.addObject("password", user.getPassword());

		return welcomeView;
	}



	private static final String HOME_VIEW="form";
	private static final String WELCOME_VIEW="welcome";



	@GetMapping("/")
	public ModelAndView home(){
		return new ModelAndView(HOME_VIEW);
	}

	protected static class User{
		private String userName;

		private String password;

		public User(String userName, String password) {
			this.userName = userName;
			this.password = password;
		}

		public String getUserName() {
			return userName;
		}

		public void setUserName(String userName) {
			this.userName = userName;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		@Override
		public String toString() {
			return "User{" +
					"userName='" + userName + '\'' +
					", password='" + password + '\'' +
					'}';
		}
	}
}
