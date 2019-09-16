package com.aisouji.manager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class OperationController {//业务管理
	
	@RequestMapping("/cert")//资质维护
	public String cert() {
		return "operation/cert";
	}
	@RequestMapping("/certtype")//分类管理
	public String type() {
		return "operation/type";
	}
	@RequestMapping("/process")//流程管理
	public String process() {
		return "operation/process";
	}
	@RequestMapping("/advert")//广告管理
	public String advertisement() {
		return "operation/advertisement2";
	}
	@RequestMapping("/message")//消息模板
	public String message() {
		return "operation/message";
	}
	@RequestMapping("/projectType")//项目分类
	public String project_type() {
		return "operation/project_type";
	}
	@RequestMapping("/tag")//项目标签
	public String tag() {
		return "operation/tag";
	}
	@RequestMapping("/param")//参数管理
	public String param() {
		return "operation/param";
	}
}
