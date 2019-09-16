package com.aisouji.manager.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.aisouji.bean.Rolepermission;
import com.aisouji.util.IdDate;

public interface RolepermissionMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Rolepermission record);

    int insertSelective(Rolepermission record);

    Rolepermission selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Rolepermission record);

    int updateByPrimaryKey(Rolepermission record);

	List<Integer> getPermissionIdsByRoleId(Integer roleId);

	Integer save_role_permission(@Param("roleid")Integer roleid,@Param("idDate") List<Integer> list);

	Integer deleteAllRoleByRoleid(Integer roleId);
}