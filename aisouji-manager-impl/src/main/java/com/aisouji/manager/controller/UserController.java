package com.aisouji.manager.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aisouji.bean.User;
import com.aisouji.manager.service.UserService;
import com.aisouji.util.Page;
import com.aisouji.util.UserData;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	UserService userService;
	
	
	@RequestMapping("/user")
	public String user() {
		return "user/user";
	}
	@ResponseBody
	@RequestMapping("/weifuuser")
	public Page weifuuser(@RequestParam(value="pagePoint" ,required= false,defaultValue="0")Integer pagePoint ,
			@RequestParam(value="pageSize" ,required= false ,defaultValue="10")Integer pageSize ,
			String queryText  ,Map<String, Object> map) {//前台数据封装为map数据一起传输
		map.put("pagePoint", pagePoint);
		map.put("pageSize", pageSize);
		if (queryText != null && !queryText.equals("")) {
			if (queryText.contains("%")) {
				queryText=queryText.replace("%", "\\%");
			}
			map.put("queryText", queryText);
		}
		Page page = userService.queryPage(map);
		return page;
	}
	@RequestMapping("/newadd")
	public String newadd() {//增加页面
			return "user/add";
	}
	@RequestMapping("/update")
	public String update() {//修改页面
		return "user/edit";
	}
	@ResponseBody
	@RequestMapping("/upDateUserSession")
	public boolean upDateUserSession(Integer id ,HttpSession session) {
		User user = userService.getUpdateUser(id);
		session.setAttribute("edituser", user);
		return true;
	}
	@ResponseBody
	@RequestMapping("/destoryUser")
	public boolean destoryUser(HttpSession session) {//离开页面时候
		session.removeAttribute("edituser");
		System.out.println("销毁一个更改的对象");
		return true;
	}
	@ResponseBody
	@RequestMapping("/edituser")
	public Object edituser(HttpSession session) {//获取需要修改的用户
		Object attribute = session.getAttribute("edituser");
		return attribute;
	}
	@ResponseBody
	@RequestMapping("useradd")
	public boolean useradd(User user) {//进行添加，判断是否成功
		int saveUser = userService.saveUser(user);
		if (saveUser == 1) {
			return true;
		}
		return false;
	}
	@ResponseBody
	@RequestMapping("updateUser")
	public boolean updateUser(User user ,HttpSession session) {//进行用户的更新
		int i = -1;
		User sessionuser =(User) session.getAttribute("edituser");
		if (sessionuser != null&&sessionuser.getId() == user.getId()) {
			i = userService.updateByPrimaryKey(user);
			if (i == 1) {
				upDateUserSession(sessionuser.getId(), session);
				return true;
			}else {
				return false;
			}
		}
		return false;
	}
	@ResponseBody
	@RequestMapping("deleteUser")
	public boolean name(Integer id) {//删除单个id的用户
		Integer i = userService.deleteUser(id);
		if (i == 1) {
			return true;
		}else {
			return false;
		}
	}
	@ResponseBody
	@RequestMapping(value="deleteUsers" ,method = RequestMethod.POST)
	public boolean deleteUsers(Integer[] id) {//进行批量删除
		Integer i = userService.deleteUsers(id);
		if (i == id.length) {//如果等于全部删除了
			return true;//那么返回true 
		}else {
			return false;
		}
	}
	@ResponseBody
	@RequestMapping(value="deleteUserList" ,method = RequestMethod.POST)
	public boolean deleteUserList(UserData users) {//进行批量删除
		Integer i = userService.deleteUserList(users);
		if (i == users.getUserlist().size()) {//如果等于全部删除了
			return true;//那么返回true 
		}else {
			return false;
		}
	}
}
