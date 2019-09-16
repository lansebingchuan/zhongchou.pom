package com.aisouji.manager.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.aisouji.bean.Permission;
import com.aisouji.bean.Role;
import com.aisouji.bean.User;
import com.aisouji.util.UserData;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);//根据主键更新user

    int updateByPrimaryKey(User record);

	User queryUserLogin(Map<String, String> map);

//	List<User> queryPages(@Param("pagePoint")Integer pagePoint, @Param("pageSize")Integer pageSize);//传入多个参数的时候需要指定具体名字，一个不需要
//
//	Integer queryCount();

	Integer queryCount(Map<String, Object> map);

	List<User> queryPages(Map<String, Object> map);

	Integer deleteUserList(UserData users);

	Integer deleteUserList_list(List<User> users);

	List<Role> getAllRole();

	List<Integer> getIdRoles(Integer id);

	Integer addUserRole(@Param("id")Integer id, @Param("ids")List<Integer> ids);

	Integer deleteUserRole(@Param("id")Integer id, @Param("ids")List<Integer> ids);

	List<Permission> getPermissionsById(Integer id);
}