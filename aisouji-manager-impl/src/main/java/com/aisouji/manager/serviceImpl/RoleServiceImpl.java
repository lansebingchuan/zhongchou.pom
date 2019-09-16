package com.aisouji.manager.serviceImpl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aisouji.bean.Role;
import com.aisouji.bean.User;
import com.aisouji.manager.dao.RoleMapper;
import com.aisouji.manager.service.RoleService;
import com.aisouji.util.IdDate;
import com.aisouji.util.Page;
@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    RoleMapper roleMapper;

	public List<Role> getRoleList() {
		return roleMapper.getRoleList();
	}

	public Page queryPage(Map<String, Object> map) {
		// TODO Auto-generated method stub
		Page page = new Page((Integer)map.get("pagePoint"),(Integer) map.get("pageSize"));
		Integer startIndex = page.getStartIndex();
		map.put("startIndex", startIndex);
		Integer totalSize = roleMapper.queryCount(map);
		page.setTotalSize(totalSize);
		List<User> list = roleMapper.queryPages(map);
		page.setLists(list);
		return page;
	}

	public Role getRoleByid(Integer id) {
		// TODO Auto-generated method stub
		return roleMapper.selectByPrimaryKey(id);
	}

	public Integer updateRole(Role role) {
		// TODO Auto-generated method stub
		return roleMapper.updateByPrimaryKeySelective(role);
	}

	public Integer saveRole(Role role) {
		return roleMapper.insert(role);
	}

	public Integer deleteRoleByid(Integer id) {
		return roleMapper.deleteByPrimaryKey(id);
	}

	public Integer deleteAllRoleByid(IdDate idDate) {
		// TODO Auto-generated method stub
		return roleMapper.deleteAllRoleByid(idDate.getIdlist());
	}
}
