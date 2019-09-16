package com.aisouji.manager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserAuthController {
	
	@RequestMapping("/auth_cert")//实名认证审核
	public String auth_cert() {
		return "user/auto/auth_cert";
	}
	@RequestMapping("/auth_adv")//广告审核
	public String auth_adv() {
		return "user/auto/auth_adv";
	}
	@RequestMapping("/auth_project")//项目审核
	public String auth_project() {
		return "user/auto/auth_project";
	}
}
