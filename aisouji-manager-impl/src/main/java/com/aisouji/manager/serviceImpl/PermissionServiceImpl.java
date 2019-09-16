package com.aisouji.manager.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aisouji.bean.Permission;
import com.aisouji.manager.dao.PermissionMapper;
import com.aisouji.manager.service.PermissionService;

@Service
public class PermissionServiceImpl implements PermissionService{

	@Autowired
	PermissionMapper permissionMapper;

	public Permission getRootPermission() {
		return permissionMapper.getRootPermission();
	}

	public List<Permission> getChilderPermission(Integer id) {
		return permissionMapper.getChilderPermission(id);
	}

	public List<Permission> getAllPermission() {
		return permissionMapper.getAllPermission();
	}

	public Permission getPermissionByid(Integer id) {
		return permissionMapper.selectByPrimaryKey(id);
	}

	public Integer addPermission(Permission permission) {
		return permissionMapper.insert(permission);
	}

	public List<String> getIcon() {
		return permissionMapper.getIcon();
	}

	public Integer deletePermission(Integer id) {
		return permissionMapper.deleteByPrimaryKey(id);
	}

	public Integer updatePermission(Permission permission) {
		return permissionMapper.updateByPrimaryKeySelective(permission);
	}
}
