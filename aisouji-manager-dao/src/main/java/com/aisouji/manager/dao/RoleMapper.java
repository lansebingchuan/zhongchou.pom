package com.aisouji.manager.dao;

import java.util.List;
import java.util.Map;

import com.aisouji.bean.Role;
import com.aisouji.bean.User;

public interface RoleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Role record);

    int insertSelective(Role record);

    Role selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);

	List<Role> getRoleList();

	Integer queryCount(Map<String, Object> map);

	List<User> queryPages(Map<String, Object> map);

	Integer deleteAllRoleByid(List<Integer> list);

}