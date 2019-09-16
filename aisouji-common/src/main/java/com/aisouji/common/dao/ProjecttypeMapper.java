package com.aisouji.common.dao;

import java.util.List;
import java.util.Map;

import com.aisouji.bean.Projecttype;

public interface ProjecttypeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Projecttype record);

    int insertSelective(Projecttype record);

    Projecttype selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Projecttype record);

    int updateByPrimaryKey(Projecttype record);

	List<Projecttype> getAllProjectType();

	List<Map<String, Object>> getProjecttypesMony();
}