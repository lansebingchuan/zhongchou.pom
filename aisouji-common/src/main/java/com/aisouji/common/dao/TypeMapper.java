package com.aisouji.common.dao;

import java.util.List;

import com.aisouji.bean.Type;

public interface TypeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Type record);

    int insertSelective(Type record);

    Type selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Type record);

    int updateByPrimaryKey(Type record);

	List<Type> getAllProjectType();

}