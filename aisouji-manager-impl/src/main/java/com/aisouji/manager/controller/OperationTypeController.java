package com.aisouji.manager.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aisouji.bean.Type;
import com.aisouji.service.TypeService;
import com.aisouji.util.BaseController;

@Controller
@RequestMapping("/type")
public class OperationTypeController extends BaseController{
	
	@Autowired
	TypeService typeService;
	
	@ResponseBody
	@RequestMapping("/initProjectType")
	public Object initProjectType() {
		start();
		List<Type> projectTypeList = typeService.getAllProjectType();
		put("projectTypeList", projectTypeList);
		flage(true);
		message("查询数据成功！");
		return end();
	}
	
	@RequestMapping("/toAddType")
	public String toAddType() {
		return "operation/type/addType";
	}
	
	@ResponseBody
	@RequestMapping("/addType")
	public Object addType(Type type) {
		start();
		Integer i = typeService.addType(type);
		if (i == 1) {
			flage(true);
			message("正价项目类型成功！正在跳转");
		}else {
			flage(false);
			message("正价项目类型失败！");
		}
		return end();
	}
	
	
	@ResponseBody
	@RequestMapping("/removeProjectType")
	public Object removeProjectType(Integer id) {
		start();
		Integer i = typeService.removeProjectTypeById(id);
		if (i == 1) {
			flage(true);
			message("类型删除成功！");
		}else {
			flage(false);
			message("类型删除失败！");
		}
		return end();
	}
}
