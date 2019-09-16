package com.aisouji.manager.service;

import java.util.List;

import com.aisouji.util.IdDate;

public interface RolepermissionService {

	List<Integer> getPermissionIdsByRoleId(Integer id);

	Integer deleteAllRoleByRoleid(Integer id);//删除所有的已经分配的许可，根据role---id

	Integer save_role_permission(Integer id, IdDate idDate);

}
