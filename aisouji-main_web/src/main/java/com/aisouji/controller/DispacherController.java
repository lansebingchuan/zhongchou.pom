package com.aisouji.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aisouji.bean.Member;
import com.aisouji.bean.Permission;
import com.aisouji.bean.User;
import com.aisouji.manager.service.PermissionService;
import com.aisouji.manager.service.UserService;
import com.aisouji.potal.service.MemberService;
import com.aisouji.util.Constent;
import com.aisouji.util.MD5Util;

@Controller
public class DispacherController {
	
	@Autowired
	private UserService userService;
	

	@Autowired
	MemberService memberService;
	
	
	@RequestMapping("/index")//进入首页
	public String index() {
		return "redirect:indexmain.htm";
	}
	@RequestMapping("/indexmain")//重定向首页
	public String indexmain() {
		return "indexmain";
	}
	@RequestMapping("/login")//登录页面
	public String login(HttpServletRequest request ,HttpSession session ,HttpServletResponse response) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("loginMessage")) {
					String loginMessage = cookie.getValue();//登录信息
					String[] split = loginMessage.split("&");
					if (split.length == 3) {
						String loginacct = split[0].split("=")[1];
						String pswrd = split[1].split("=")[1];
						String type = split[2].split("=")[1];
						boolean dologin = dologin(loginacct, pswrd, type, session, null, response ,true);
						if (dologin == true) {
							if (type.equals(Constent.USERTYPE)) {
								return "redirect:main.htm";
							}else {
								return "redirect:member.htm";
							}
						}else {
							return "login";
						}
					}
				}
			}
			return "login";
		}else {
			return "login";
		}

	}
	@RequestMapping("/reg")//注册页面
	public String reg() {
		return "reg";
	}
	@RequestMapping("/main")//后台管理页面
	public String main() {
		return "main";
	}
	@RequestMapping("/member")//会员管理登录后的页面
	public String member() {
		return "/member/member";
	}
	@RequestMapping("/logout")//退出登录
	public String logout(HttpSession session) {
		//session.invalidate();//销毁session
		return "redirect:indexmain.htm";
	}
	@ResponseBody
	@RequestMapping("/regUser")//注册member
	public boolean regUser(User user) {
		boolean addUser = userService.addUser(user);
		return addUser;
	}
	@ResponseBody
	@RequestMapping("/user")//注册member
	public boolean user(User user) {
		boolean addUser = userService.addUser(user);
		return addUser;
	}
	@RequestMapping("/menu")//注册member
	public String menu() {
		return "common/menu";
	}
	@RequestMapping("/topMenu")//注册member
	public String topMenu() {
		return "common/top";
	}
	@ResponseBody
	@RequestMapping("/dologin")//进行登录,如果抛异常了之后，那么交给Springmvc的配置异常区处理
	public boolean dologin(String loginacct ,String userpswd ,String type ,
			HttpSession session , String rememberme ,HttpServletResponse response,
			@RequestParam(value="login",required=false ,defaultValue ="false")boolean login) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("loginacct", loginacct);
		String digest = null;
		if (login == false) {//为false表示需要加密，客服端发出的，否则就是cookies登录
			digest = MD5Util.digest(userpswd);
		}else {
			digest = userpswd;
		}
		map.put("userpswd", digest);//加密
		List<Permission> permissions = null;
		if (type.equals(Constent.USERTYPE)) {//管理员登录
			session.removeAttribute(Constent.MEMBER);
			User user = userService.queryUserLogin(map);
			session.setAttribute(Constent.USER, user);
			permissions = userService.getPermissionsById(user.getId());//获取用户的许可
			Permission permissionsRoot = new Permission();
			Map<Integer, Permission> mapFu = new HashMap<Integer, Permission>();
			Set<String> permissionUserUrl = new HashSet<String>();//拥有的权限路径
			for (Permission permission : permissions) {
				mapFu.put(permission.getId(), permission);
				permissionUserUrl.add(permission.getUrl());
			}
			session.setAttribute(Constent.USERPERSSIOURL, permissionUserUrl);
			for (Permission permission : permissions) {
				Permission childern = permission;
				if (childern.getPid() == null) {
					permissionsRoot = childern;
				}else {
					Permission parent = mapFu.get(childern.getPid());
					parent.getChildren().add(childern);
				}

			}
			session.setAttribute(Constent.USERPERSSION, permissionsRoot);
			//session.setMaxInactiveInterval(600);//10分钟
			if (rememberme != null) {
				String loginMessage = "loginacct="+loginacct+"&pswd="+digest+"&type="+type;
				Cookie cookie = new Cookie("loginMessage", loginMessage);//登录信息
				cookie.setMaxAge(60*60*24);
				response.addCookie(cookie);
			}
			return true;
		}else if (type.equals(Constent.MEMBERTYPE)) {//会员登录
			session.removeAttribute(Constent.USER);
			Member member = memberService.memberLogin(map);
			if (member == null) {
				return false;
			}
			session.setAttribute(Constent.MEMBER, member);
			if (rememberme != null) {
				String loginMessage = "loginacct="+loginacct+"&pswd="+digest+"&type="+type;
				Cookie cookie = new Cookie("loginMessage", loginMessage);//登录信息
				response.addCookie(cookie);
			}
			return true;
		}
		return false;
	/*	登录的时候加载所有的许可信息
	 * List<Permission> allPermission = permissionService.getAllPermission();//获取所有的许可
		Set<String> permissionAllUrl = new HashSet<String>();//拥有的权限路径
		for (Permission permission : allPermission) {
			if (permission.getUrl() != null) {
				permissionAllUrl.add(permission.getUrl());
			}
		}
		session.setAttribute(Content.PERMISSIONAllURL, permissionAllUrl);*/
	}
	@ResponseBody
	@RequestMapping("/getUser")//返回session里面的用户对象
	public String getUser(HttpServletRequest request) {
		HttpSession session = request.getSession();
		User attribute = (User) session.getAttribute(Constent.USER);
		if (attribute == null) {
			System.out.println("姓名：");
			return null;
		}
		System.out.println("姓名："+attribute.getUsername());
		return attribute.getUsername();
	}
	@ResponseBody
	@RequestMapping("/isUser_Member_flage")//返回session里面的用户对象
	public boolean isUser_Member_flage(HttpServletRequest request) {
		Cookie[] session = request.getCookies();
		for (Cookie cookie : session) {
			if (cookie.getName().equals("loginMessage")) {//表示有登录的
				return true;
			}
		}
		return false;
	}
	@ResponseBody
	@RequestMapping("/rolePermission")//返回session里面的用户对象
	public Object rolePermission(HttpSession session) {
		Permission permission = (Permission) session.getAttribute(Constent.USERPERSSION);
		return permission;
	}

	@RequestMapping("/delLogin")//退出登录，删除cookies
	public String delLogin(HttpSession session ,HttpServletRequest request,HttpServletResponse response ,@RequestParam(value="change",required=false) Integer change) {
		session.invalidate();//消除所有的session
		Cookie[] cookies = request.getCookies();
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals("loginMessage")) {//表示有登录的
				cookie.setMaxAge(0);
				response.addCookie(cookie);
				if (change != null) {//点击切换
					return "redirect:login.htm";
				}
				return "redirect:indexmain.htm";
			}
		}
		return "redirect:indexmain.htm";
	}
	
	@RequestMapping("/toMyMian")//去我的主页，判断session是否存在，存在，那么直接进去
	public String toMyMian(HttpSession session) {
		Object user = session.getAttribute(Constent.USER);
		if (user != null) {
			return "redirect:main.htm";
		}
		Object member = session.getAttribute(Constent.MEMBER);
		if (member != null) {
			return "redirect:member.htm";
		}
		return "redirect:login.htm";
	}
}
