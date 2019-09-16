package com.aisouji.manager.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aisouji.manager.dao.RolepermissionMapper;
import com.aisouji.manager.service.RolepermissionService;
import com.aisouji.util.IdDate;

@Service
public class RolepermissionServiceImpl implements RolepermissionService {

	@Autowired
	RolepermissionMapper mapper;

	public List<Integer> getPermissionIdsByRoleId(Integer roleId) {
		return mapper.getPermissionIdsByRoleId(roleId);
	}

	public Integer deleteAllRoleByRoleid(Integer roleid) {
		return mapper.deleteAllRoleByRoleid(roleid);
	}

	public Integer save_role_permission(Integer roleid, IdDate idDate) {
		if (idDate.getIdlist().isEmpty()) {
			return 1;
		}
		return mapper.save_role_permission(roleid, idDate.getIdlist());
	}
}
