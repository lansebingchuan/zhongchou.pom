package com.aisouji.manager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.aisouji.manager.service.TestService;
import com.aisouji.manager.service.UserService;

@Controller
public class TestController {
	
	
	@Autowired()
//	@Qualifier(value="testServiceImpl")
	private TestService testService;
	
	@RequestMapping("/test.do")
	public String test() {
		System.out.println("TestController");
		testService.insert();
		return "success";
	}
}
