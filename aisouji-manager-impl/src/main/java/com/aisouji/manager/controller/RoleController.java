package com.aisouji.manager.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.activiti.engine.impl.util.json.HTTP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aisouji.bean.Permission;
import com.aisouji.bean.Role;
import com.aisouji.bean.User;
import com.aisouji.manager.service.PermissionService;
import com.aisouji.manager.service.RoleService;
import com.aisouji.manager.service.RolepermissionService;
import com.aisouji.manager.service.UserService;
import com.aisouji.util.BaseController;
import com.aisouji.util.Constent;
import com.aisouji.util.IdDate;
import com.aisouji.util.JsonData;
import com.aisouji.util.Page;
import com.aisouji.util.UserData;

@Controller
@RequestMapping("/user")
public class RoleController extends BaseController{
	
	@Autowired
	UserService userService;
	
	@Autowired
	RoleService roleService;
	
	@Autowired
	PermissionService permissionService;
	
	@Autowired
	RolepermissionService rolepermissionService;
	
	@ResponseBody
	@RequestMapping("/userRole")//点击分配时，保存需要分配的角色
	public boolean userRole(Integer id ,HttpSession session) {//用户权限
		try {
			User user = userService.getUpdateUser(id);
			session.setAttribute("userRole", user);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return false;
	}
	@ResponseBody
	@RequestMapping("/initUserRole")//点击分配时，保存需要分配的角色
	public Map<String, List<Object>> initUserRole(Map<String, List<Object>> map ,HttpSession session) {//用户权限
		User user =(User) session.getAttribute("userRole");
		if (user == null) {
			return null;
		}
		List<Role> listAllRole = userService.getAllRole();
		List<Integer> listIdRoles = userService.getIdRoles(user.getId());
		
		List<Object> leftRoles = new ArrayList<Object>();
		List<Object> rightRoles = new ArrayList<Object>();
		for(Role role : listAllRole) {
			if (listIdRoles.contains(role.getId())) {//包含
				rightRoles.add(role);
			}else {
				leftRoles.add(role);
			}
		}
		map.put("leftRoles", leftRoles);
		map.put("rightRoles", rightRoles);
		return map;
	}
	@RequestMapping("/assignRole")
	public String assignRole() {//角色维护
		return "role/assignRole";
	}
	@RequestMapping("/role")
	public String role() {//角色维护
		return "role/role";
	}
	@ResponseBody
	@RequestMapping("/addUserRole")
	public Integer addUserRole(UserData userData ,HttpSession session) {//角色权限修改
		User user =(User) session.getAttribute("userRole");
		if (user == null) {
			return Constent.ISENTITY;
		}
		try {
			Integer count = userService.addUserRole(user, userData);
			if (count == Constent.ISSUCCESS) {
				return Constent.ISSUCCESS;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
			return Constent.ISERROR;		
	}
	@ResponseBody
	@RequestMapping("/deleteUserRole")
	public Integer deleteUserRole(UserData userData ,HttpSession session) {//角色权限修改
		User user =(User) session.getAttribute("userRole");
		if (user == null) {
			return Constent.ISENTITY;
		}
		try {
			Integer count = userService.deleteUserRole(user, userData);
			if (count == userData.getIds().size()) {
				return Constent.ISSUCCESS;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
			return Constent.ISERROR;		
	}
	
/*	@ResponseBody
	@RequestMapping("/role/initRole")
	public Object initRole() {//获取全部的角色
		start();
		try {
			List<Role> roles = roleService.getRoleList();
			put("roles", roles);
			message("获取数据成功！");
			flage(true);
			return end();
		} catch (Exception e) {
			message("获取数据失败！");
			flage(false);
			return end();
		}

	}*/
	
	@ResponseBody
	@RequestMapping("/role/initRole")
	public Object initRole(@RequestParam(value="pagePoint" ,required= false,defaultValue="0")Integer pagePoint ,
			@RequestParam(value="pageSize" ,required= false ,defaultValue="5")Integer pageSize ,
			String queryText  ,Map<String, Object> map) {//前台数据封装为map数据一起传输
		Page page = null;
		start();
		try {
			map.put("pagePoint", pagePoint);
			map.put("pageSize", pageSize);
			if (queryText != null && !queryText.equals("")) {
				if (queryText.contains("%")) {//特殊字符处理
					queryText = queryText.replace("%", "\\%");
				}
				map.put("queryText", queryText);
			}
			page = roleService.queryPage(map);
			message("请求数据成功！");
			flage(true);
			put("page", page);
			return end();
		} catch (Exception e) {
			// TODO: handle exception
			message("请求数据失败！");
			flage(false);
			return end();
		}
	}
	
	@ResponseBody
	@RequestMapping({"/role/toupdate" , "/role/setrole_Permission"})
	public Object toupdate(Integer id , HttpSession  session) {//前台数据封装为map数据一起传输
		start();
		try {
			Role role = roleService.getRoleByid(id);
			session.setAttribute(Constent.Role, role);
			session.setMaxInactiveInterval(120);//两分钟
			message("请求数据成功！");
			flage(true);
			put("role", role);
			return end();
		} catch (Exception e) {
			// TODO: handle exception
			message("请求数据失败！");
			flage(false);
			return end();
		}
	}
	
	
	@ResponseBody
	@RequestMapping("/role/getupdate")
	public Object getupdate( HttpSession  session) {//前台数据封装为map数据一起传输
		start();
		try {
			Object role = session.getAttribute(Constent.Role);
			if (role == null) {
				message("请求数据超时！");
				flage(false);
				return end();
			}
			session.setMaxInactiveInterval(120);//两分钟
			message("数据已加载，请更改！");
			flage(true);
			put("role", role);
			return end();
		} catch (Exception e) {
			// TODO: handle exception
			message("请求数据失败！");
			flage(false);
			return end();
		}
	}
	
	
	@ResponseBody
	@RequestMapping("/role/roleUpdate")
	public Object roleUpdate(Role role) {//前台数据封装为map数据一起传输
		start();
		try {
			Integer integer = roleService.updateRole(role);
			if (integer == 1) {
				message("数据已更新！");
				flage(true);
				put("role", role);
				return end();
			}else {
				message("更新失败！");
				flage(false);
				return end();
			}
		} catch (Exception e) {
			// TODO: handle exception
			message("更新失败！");
			flage(false);
			return end();
		}
	}
	
	
	@ResponseBody
	@RequestMapping("/role/addRole")
	public Object addRole(Role role) {//前台数据封装为map数据一起传输
		start();
		try {
			Integer integer = roleService.saveRole(role);
			if (integer == 1) {
				message("数据已保存！");
				flage(true);
				return end();
			}else {
				message("保存数据失败！");
				flage(false);
				return end();
			}
		} catch (Exception e) {
			// TODO: handle exception
			message("数据库错误！");
			flage(false);
			return end();
		}
	}
	
	
	@ResponseBody
	@RequestMapping("/role/removeRole")
	public Object removeRole(Integer id) {//前台数据封装为map数据一起传输
		start();
		try {
			Integer integer = roleService.deleteRoleByid(id);
			if (integer == 1) {
				message("数据删除！");
				flage(true);
				return end();
			}else {
				message("删除数据失败！");
				flage(false);
				return end();
			}
		} catch (Exception e) {
			// TODO: handle exception
			message("数据库错误！");
			flage(false);
			return end();
		}
	}
	
	
	
	@ResponseBody
	@RequestMapping("/role/updateRolePermission")
	public Object updateRolePermission(IdDate idDate ,HttpSession session) {//更新角色的许可
		start();
		Role role =(Role) session.getAttribute(Constent.Role);
		if (role == null) {
			message("操作超时！");
			flage(false);
			return end();
		}
		try {
			Integer integer = rolepermissionService.deleteAllRoleByRoleid(role.getId());
			Integer integer2 = rolepermissionService.save_role_permission(role.getId() , idDate);
			if (integer < 0) {
				message("数据删除失败！");
				flage(true);
				return end();
			}else if (integer2 != Constent.ISSUCCESS) {
				message("数据添加失败！");
				flage(false);
				return end();
			}else {
				message("许可修改成功！");
				flage(true);
				return end();
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			message("数据库错误！");
			flage(false);
			return end();
		}
	}
	
	@ResponseBody
	@RequestMapping("/role/deleteRoleList")
	public Object deleteRoleList(IdDate idDate) {//前台数据封装为map数据一起传输
		start();
		try {
			Integer integer = roleService.deleteAllRoleByid(idDate);
			if (integer == idDate.getIdlist().size()) {
				message("数据删除！");
				flage(true);
				return end();
			}else {
				message("删除数据失败！");
				flage(false);
				return end();
			}
		} catch (Exception e) {
			// TODO: handle exception
			message("数据库错误！");
			flage(false);
			return end();
		}
	}
	
	@RequestMapping("/role/update_htm")
	public String toupdate() {//前台数据封装为map数据一起传输
		return "role/updateRole";
	}
	
	@RequestMapping("/role/roleAdd")
	public String roleAdd() {//前台数据封装为map数据一起传输
		return "role/addRole";
	}
	@RequestMapping("/role/to_assignPermission")
	public String to_assignPermission() {//前台数据封装为map数据一起传输
		return "role/assignPermission";
	}
	
	///一次性加载全部数据 ， 递归
	@RequestMapping("/role/getRole_Permissions")
	@ResponseBody
	public Object getRole_Permissions(HttpSession session) {
		Role role =(Role) session.getAttribute(Constent.Role);
		if (role == null) {
			return null;
		}
		List<Permission> root = new ArrayList<Permission>();//创建根
		
		List<Permission> lists = permissionService.getAllPermission();//获取所有的许可
		
		List<Integer> lIntegers = rolepermissionService.getPermissionIdsByRoleId(role.getId());
		
		Map<Integer, Permission> map = new HashMap<Integer, Permission>();
		for (Permission permission : lists) {//添加所有的节点
			map.put(permission.getId(), permission);
			if (lIntegers.contains(permission.getId())) {
				permission.setChecked(true);
			}
		}
		for (Permission permission : lists) {
			if (permission.getPid() == null) {
				root.add(permission);
				permission.setOpen(true);
			}else {
				Permission parent = map.get(permission.getPid());//获取父
				parent.getChildren().add(permission);//然后添加到它的父亲里面
			}
		}
		return root;
	}
}
