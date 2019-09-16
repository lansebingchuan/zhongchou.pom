package com.aisouji.manager.service;

import java.util.List;
import java.util.Map;

import com.aisouji.bean.Permission;
import com.aisouji.bean.Role;
import com.aisouji.bean.User;
import com.aisouji.util.Page;
import com.aisouji.util.UserData;

public interface UserService {

	User queryUserLogin(Map<String, String> map);

	boolean addUser(User user);//注册用户

//	Page queryPage(Integer pagePoint, Integer pageSize);

	Page queryPage(Map<String, Object> map);//获取分页

	int saveUser(User user);//保存新增用户

	User getUpdateUser(Integer id);//获取更新用户

	int updateByPrimaryKey(User user );//更新用户

	Integer deleteUser(Integer id);//根据id删除

	Integer deleteUsers(Integer[] id);//根据多个id的数组循环删除

	Integer deleteUserList(UserData users);//根据users工具类里面的集合，循环集合里面的user，来进行删除
	
	Integer deleteUserList_list(List<User> users);//根据list集合，循环来删除数据

	List<Role> getAllRole();//获取所有的权限

	List<Integer> getIdRoles(Integer id);//获取这个用户的权限

	Integer addUserRole(User user, UserData userData);//添加用户权限

	Integer deleteUserRole(User user, UserData userData);//删除用户权限

	List<Permission> getPermissionsById(Integer id);
    
}