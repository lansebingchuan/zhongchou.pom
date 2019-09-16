package com.aisouji.manager.dao;

import java.util.List;

import com.aisouji.bean.Role;
import com.aisouji.bean.Userrole;

public interface UserroleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Userrole record);

    int insertSelective(Userrole record);

    Userrole selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Userrole record);

    int updateByPrimaryKey(Userrole record);

}