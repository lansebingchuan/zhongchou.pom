package com.aisouji.manager.dao;

import java.util.List;

import com.aisouji.bean.Permission;

public interface PermissionMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Permission record);

    int insertSelective(Permission record);

    Permission selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Permission record);

    int updateByPrimaryKey(Permission record);

	Permission getRootPermission();

	List<Permission> getChilderPermission(Integer id);

	List<Permission> getAllPermission();

	List<String> getIcon();

}