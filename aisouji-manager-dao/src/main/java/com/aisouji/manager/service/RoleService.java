package com.aisouji.manager.service;

import java.util.List;
import java.util.Map;

import com.aisouji.bean.Role;
import com.aisouji.util.IdDate;
import com.aisouji.util.Page;

public interface RoleService {

	List<Role> getRoleList();//获取所有的role

	Page queryPage(Map<String, Object> map);

	Role getRoleByid(Integer id);

	Integer updateRole(Role role);

	Integer saveRole(Role role);

	Integer deleteRoleByid(Integer id);

	Integer deleteAllRoleByid(IdDate idDate);

}
