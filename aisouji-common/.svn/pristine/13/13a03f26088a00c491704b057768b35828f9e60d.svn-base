package com.aisouji.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aisouji.bean.Type;
import com.aisouji.common.dao.TypeMapper;
import com.aisouji.service.TypeService;

@Service
public class TypeServiceImpl implements TypeService {

	@Autowired
	TypeMapper typeMapper;

	public List<Type> getAllProjectType() {
		return typeMapper.getAllProjectType() ;
	}

	public Integer addType(Type type) {
		return typeMapper.insertSelective(type);
	}

	public Integer removeProjectTypeById(Integer id) {
		return typeMapper.deleteByPrimaryKey(id);
	}
	
	
	
}
