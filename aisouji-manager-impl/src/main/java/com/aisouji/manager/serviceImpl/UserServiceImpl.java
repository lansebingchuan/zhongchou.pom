package com.aisouji.manager.serviceImpl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.h2.util.New;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aisouji.bean.Permission;
import com.aisouji.bean.Role;
import com.aisouji.bean.User;
import com.aisouji.exception.LoginException;
import com.aisouji.exception.MemberException;
import com.aisouji.manager.dao.UserMapper;
import com.aisouji.manager.dao.UserroleMapper;
import com.aisouji.manager.service.UserService;
import com.aisouji.util.Constent;
import com.aisouji.util.MD5Util;
import com.aisouji.util.Page;
import com.aisouji.util.UserData;
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private UserroleMapper userroleMapper;
	
	public User queryUserLogin(Map<String, String> map) {
		// TODO Auto-generated method stub
		User user = userMapper.queryUserLogin(map);
		if (user == null) {
			throw new LoginException("登录错误，用户名或者密码错误");
		}
		return user;
	}

	public boolean addUser(User user) {
		// TODO Auto-generated method stub
		user.setUserpswd(MD5Util.digest(user.getUserpswd()));
		int insertSelective = userMapper.insertSelective(user);
		if (insertSelective == 0) {
			throw new MemberException("注册失败！");
		}else {
			return true;
		}
	}

//	public Page queryPage(Integer pagePoint, Integer pageSize) {
//		// TODO Auto-generated method stub
//		Page page = new Page(pagePoint, pageSize);
//		Integer totalSize = userMapper.queryCount();
//		page.setTotalSize(totalSize);
//		List<User> list = userMapper.queryPages(page.getStartIndex(), pageSize);
//		page.setLists(list);
//		return page;
//	}

	public Page queryPage(Map<String, Object> map) {
		// TODO Auto-generated method stub
		Page page = new Page((Integer)map.get("pagePoint"),(Integer) map.get("pageSize"));
		Integer startIndex = page.getStartIndex();
		map.put("startIndex", startIndex);
		Integer totalSize = userMapper.queryCount(map);
		page.setTotalSize(totalSize);
		List<User> list = userMapper.queryPages(map);
		page.setLists(list);
		return page;
	}

	public int saveUser(User user) {
		// TODO Auto-generated method stub
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		user.setCreatetime(dateFormat.format(new Date()));
		user.setUserpswd(MD5Util.digest(Constent.PWD));
		int i = userMapper.insertSelective(user);
		return i;
	}

	public User getUpdateUser(Integer id) {
		User user = userMapper.selectByPrimaryKey(id);
		return user;
	}
	

	public int updateByPrimaryKey(User user) {
		// TODO Auto-generated method stub
		int i = userMapper.updateByPrimaryKeySelective(user);
		return i;
	}

	public Integer deleteUser(Integer id) {
		int i = userMapper.deleteByPrimaryKey(id);
		return i;
	}

	public Integer deleteUsers(Integer[] id) {//根据多个id的数组循环删除
		Integer flage = 0;
		for (Integer userid : id) {
			int i = userMapper.deleteByPrimaryKey(userid);
			flage += i;
		}
		return flage;
	}

	public Integer deleteUserList(UserData users) {//根据users工具类里面的集合，循环集合里面的user，来进行删除
		Integer integer = userMapper.deleteUserList(users);
		return integer;
	}

	public Integer deleteUserList_list(List<User> users) {//根据list集合，循环来删除数据
		Integer integer = userMapper.deleteUserList_list(users);
		return integer;
	}

	public List<Role> getAllRole() {
		List<Role> roles = userMapper.getAllRole();
		return roles;
	}

	public List<Integer> getIdRoles(Integer id) {
		// TODO Auto-generated method stub
		return userMapper.getIdRoles(id);
	}

	public Integer addUserRole(User user, UserData userData) {
		return userMapper.addUserRole(user.getId() , userData.getIds());

	}

	public Integer deleteUserRole(User user, UserData userData) {
		return userMapper.deleteUserRole(user.getId() , userData.getIds());
	}

	public List<Permission> getPermissionsById(Integer id) {
		// TODO Auto-generated method stub
		return userMapper.getPermissionsById(id);
	}
}