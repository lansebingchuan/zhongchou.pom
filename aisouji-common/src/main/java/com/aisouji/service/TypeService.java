package com.aisouji.service;

import java.util.List;

import com.aisouji.bean.Type;

public interface TypeService {

	List<Type> getAllProjectType();//获取所有的项目申请类型

	Integer addType(Type type);//增加项目类型,id为空

	Integer removeProjectTypeById(Integer id);

}
