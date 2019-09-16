package com.aisouji.manager.service;

import java.util.List;


import com.aisouji.bean.Permission;
public interface PermissionService {

	Permission getRootPermission();//获取根的permission

	List<Permission> getChilderPermission(Integer id);//根据pid查询

	List<Permission> getAllPermission();//获取所有的数据

	Permission getPermissionByid(Integer id);//根据id获取

	Integer addPermission(Permission permission);//添加许可

	List<String> getIcon();//获取所有的已有图标

	Integer deletePermission(Integer id);

	Integer updatePermission(Permission permission);

}
