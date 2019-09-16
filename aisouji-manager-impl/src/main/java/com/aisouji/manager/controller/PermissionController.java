package com.aisouji.manager.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aisouji.bean.Permission;
import com.aisouji.manager.service.PermissionService;
import com.aisouji.util.Constent;
import com.aisouji.util.JsonData;

@Controller
@RequestMapping("/user")
public class PermissionController {

	@Autowired
	PermissionService permissionService;

	@RequestMapping("/permission")
	public String permission() {
		return "permission/permission";
	}
	@RequestMapping("/Permission/permissionadd_htm")
	public String permissionadd_htm() {
		return "permission/addpermission";
	}
	@RequestMapping("/Permission/permissionupdate_htm")
	public String permissionupdate_htm() {
		return "permission/updatepermission";
	}
	@ResponseBody
	@RequestMapping("/Permission/getIcon")
	public Object getIcon() {//获取图标
		JsonData jsonData = new JsonData();
		try {
			List<String> icons = permissionService.getIcon();
			jsonData.setListString(icons);
			jsonData.setFlage(true);
			return jsonData;
		} catch (Exception e) {
			// TODO: handle exception
			jsonData.setMessage("获取图标信息失败!");
			jsonData.setFlage(false);
			return jsonData;
		}
	}
	@ResponseBody
	@RequestMapping("/Permission/delete")
	public Object delete(Integer id) {
		JsonData jsonData = new JsonData();
		try {
			Integer i = permissionService.deletePermission(id);
			if (i == 1) {
				jsonData.setMessage("移除许可成功!");
				jsonData.setFlage(true);
			}
			return jsonData;
		} catch (Exception e) {
			// TODO: handle exception
			jsonData.setMessage("移除许可失败!");
			jsonData.setFlage(false);
			return jsonData;
		}
	}
	@ResponseBody
	@RequestMapping("/Permission/update")
	public Object update(Integer id ,HttpSession session) {
		JsonData jsonData = new JsonData();
		try {
			Permission permission = permissionService.getPermissionByid(id);
			if (permission != null) {
				session.setAttribute(Constent.PERSSION, permission);
				session.setMaxInactiveInterval(60);
				jsonData.setFlage(true);
			}
			return jsonData;
		} catch (Exception e) {
			// TODO: handle exception
			jsonData.setMessage("查询许可失败!");
			jsonData.setFlage(false);
			return jsonData;
		}
	}
	@ResponseBody
	@RequestMapping("/Permission/permissionUpdate")
	public Object permissionUpdate(HttpSession session) {
		JsonData jsonData = new JsonData();
		Object attribute = session.getAttribute(Constent.PERSSION);
		if (attribute != null) {
			jsonData.setFlage(true);
			jsonData.setMessage("请更改");
			jsonData.setObject(attribute);
			return jsonData;
		}else {
			jsonData.setMessage("数据请求超时!");
			jsonData.setFlage(false);
			return jsonData;
		}

	}
	@ResponseBody
	@RequestMapping("/Permission/updatePermission")
	public Object updatePermission(Permission permission) {
		JsonData jsonData = new JsonData();
		Integer integer = permissionService.updatePermission(permission);
		if (integer == 1) {
			jsonData.setFlage(true);
			jsonData.setMessage("修改成功！");
			return jsonData;
		}else {
			jsonData.setMessage("数据错误!");
			jsonData.setFlage(false);
			return jsonData;
		}

	}
	///一次性加载全部数据 ， 递归
	@RequestMapping("getTreeJson4")
	@ResponseBody
	public Object getTreeJson4() {
		JsonData jsonData = new JsonData();
		List<Permission> root = new ArrayList<Permission>();
		List<Permission> lists = permissionService.getAllPermission();
		Map<Integer, Permission> map = new HashMap<Integer, Permission>();
		for (Permission permission : lists) {
			map.put(permission.getId(), permission);
		}
		for (Permission permission : lists) {
			if (permission.getPid() == null) {
				root.add(permission);
				permission.setOpen(true);
			}else {
				Permission parent = map.get(permission.getPid());//获取父
				parent.getChildren().add(permission);
			}
		}
		jsonData.setpList(root);

		return jsonData;
	}

	private void queryChilderPermission(Permission childer, List<Permission> lists) {
		for (Permission permission0 : lists) {
			if (permission0.getPid() == null) {
				childer = permission0;
			}
			if (permission0.getPid() == childer.getId()) {
				childer.getChildren().add(permission0);
				queryChilderPermission(permission0 ,lists);
			}
		}

	}
	//数据库动态的tree，一次性取全部的数据,子找父
	@RequestMapping("getTreeJson3")
	@ResponseBody
	public Object getTreeJson3() {
		JsonData jsonData = new JsonData();
		List<Permission> root = new ArrayList<Permission>();

		List<Permission> lists = permissionService.getAllPermission();
		for (Permission permission0 : lists) {
			if (permission0.getPid() == null) {//如果为root
				root.add(permission0);
				permission0.setOpen(true);
			}else {//不是root
				for (Permission permission1 : lists) {//子
					if (permission0.getPid() == permission1.getId()) {
						permission1.getChildren().add(permission0);
						break;
					}
				}
			}
		}

		jsonData.setpList(root);

		return jsonData;
	}

	//数据库动态的tree，采用递归的方式
	@RequestMapping("getTreeJson2")
	@ResponseBody
	public Object getTreeJson2() {
		JsonData jsonData = new JsonData();
		List<Permission> listFu0 = new ArrayList<Permission>();

		Permission root = permissionService.getRootPermission();
		root.setOpen(true);

		queryChilderPermission(root);


		listFu0.add(root);
		jsonData.setpList(listFu0);

		return jsonData;
	}
	//得到需要增加许可的节点
	@RequestMapping("/Permission/add")
	@ResponseBody
	public Object add(Integer id , HttpSession session) {
		JsonData jsonData = new JsonData();
		try {
			Permission permission = permissionService.getPermissionByid(id);
			session.setAttribute("permission", permission);
			jsonData.setFlage(true);
			return jsonData;
		} catch (Exception e) {
			// TODO: handle exception
			jsonData.setFlage(false);
			jsonData.setMessage("数据库加载错误，或者存取错误！");
			return jsonData;
		}
	}
	@RequestMapping("Permission/permissionadd")
	@ResponseBody
	public Object permissionadd(HttpSession session) {//跳转到add    permissionadd页面
		JsonData jsonData = new JsonData();
		try {
			Permission permission = (Permission)session.getAttribute("permission");
			if (permission == null) {
				jsonData.setFlage(false);
				jsonData.setMessage("数据超时，请重新加载！");
				return jsonData;
			}
			jsonData.setObject(permission);
			jsonData.setFlage(true);
			jsonData.setMessage("数据请求成功！请修改！");
			return jsonData;
		} catch (Exception e) {
			// TODO: handle exception
			jsonData.setFlage(false);
			jsonData.setMessage("数据库加载错误，或者存取错误！");
			return jsonData;
		}
	}
	@RequestMapping("Permission/addpermission")
	@ResponseBody
	public Object addpermission(Permission permission , HttpSession session) {//跳转到add    permissionadd页面
		JsonData jsonData = new JsonData();
		Permission sessionpermission = (Permission)session.getAttribute("permission");
		if (sessionpermission == null) {
			jsonData.setFlage(false);
			jsonData.setMessage("数据超时，请重新加载！");
			return jsonData;
		}
		try {
			permission.setPid(sessionpermission.getId());
			Integer integer = permissionService.addPermission(permission);
			if (integer == 0) {
				jsonData.setFlage(false);
				jsonData.setMessage("添加许可失败！");
				return jsonData;
			}
			jsonData.setObject(permission);
			jsonData.setFlage(true);
			jsonData.setMessage("添加许可成功！");
			return jsonData;
		} catch (Exception e) {
			// TODO: handle exception
			jsonData.setFlage(false);
			jsonData.setMessage("数据库加载错误，或者存取错误！");
			return jsonData;
		}
	}
	private void queryChilderPermission(Permission root) {
		List<Permission> childerPermission = permissionService.getChilderPermission(root.getId());
		root.setChildren(childerPermission);
		for (Permission permission : childerPermission) {
			queryChilderPermission(permission);
		}

	}

	//数据库动态的tree
	@RequestMapping("getTreeJson1")
	@ResponseBody
	public Object getTreeJson1() {
		JsonData jsonData = new JsonData();

		List<Permission> listFu0 = new ArrayList<Permission>();

		Permission fuper0 = permissionService.getRootPermission();
		fuper0.setOpen(true);
		List<Permission> childerList = permissionService.getChilderPermission(fuper0.getId());

		for (Permission permission : childerList) {
			List<Permission> innerchilderList = permissionService.getChilderPermission(permission.getId());
			permission.setChildren(innerchilderList);
		}
		fuper0.setChildren(childerList);

		listFu0.add(fuper0);
		jsonData.setpList(listFu0);

		return jsonData;
	}


	//静态的tree
	@RequestMapping("getTreeJson")
	@ResponseBody
	public Object getTreeJson() {
		JsonData jsonData = new JsonData();

		List<Permission> list = new ArrayList<Permission>();

		Permission perFu0 = new Permission();
		perFu0.setName("父节点一");
		perFu0.setOpen(true);

		Permission perFu1 = new Permission();
		perFu1.setName("父节点二");
		perFu1.setOpen(true);

		list.add(perFu1);
		list.add(perFu0);

		/*Permission perZi0 = new Permission();
		perZi0.setName("子节点一");

		Permission perZi1 = new Permission();
		perZi1.setName("子节点一");

		perFu0.getChildren().add(perZi0);
		perFu0.getChildren().add(perZi1);

		perFu1.getChildren().add(perZi0);
		perFu1.getChildren().add(perZi1);*/

		jsonData.setpList(list);
		return jsonData;
	}
}
