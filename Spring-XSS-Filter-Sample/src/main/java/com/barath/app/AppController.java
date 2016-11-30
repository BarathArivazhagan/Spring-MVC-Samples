package com.barath.app;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class AppController {
	
	private static final String HOME_VIEW="/WEB-INF/form.jsp";
	private static final String WELCOME_VIEW="/WEB-INF/welcome.jsp";
	
	
	
	@GetMapping("/")
	public ModelAndView home(){
		return new ModelAndView(HOME_VIEW);
	}
	
	@PostMapping(value="/submit",consumes="application/x-www-form-urlencoded")
	public ModelAndView welcomeView(HttpServletRequest request){
		System.out.println("welcome called");
		
		String userName=request.getParameter("userName");
		String password=request.getParameter("password");
		User user=new User(userName, password);
		ModelAndView welcomeView=new ModelAndView(WELCOME_VIEW);
		welcomeView.addObject("userName", user.getUserName());
		welcomeView.addObject("password", user.getPassword());
		
		return welcomeView;
	}

}
