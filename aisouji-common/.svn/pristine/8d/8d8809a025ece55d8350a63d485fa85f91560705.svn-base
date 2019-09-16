package com.aisouji.common.dao;

import java.util.Map;

import com.aisouji.bean.ProjectOrder;

public interface ProjectOrderMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ProjectOrder record);

    int insertSelective(ProjectOrder record);

    ProjectOrder selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ProjectOrder record);

    int updateByPrimaryKey(ProjectOrder record);

	ProjectOrder getProjectOrderByOrderNumber(String orderNumber);

	Integer updateProjectOrderByOrderNumber(ProjectOrder projectOrder);

	Map<String, Object> getProjectOrderMessageByOrderNumber(String orderNumber);
}