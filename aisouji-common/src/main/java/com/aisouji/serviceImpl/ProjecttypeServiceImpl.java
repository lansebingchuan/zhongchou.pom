package com.aisouji.serviceImpl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aisouji.bean.Projecttype;
import com.aisouji.common.dao.ProjecttypeMapper;
import com.aisouji.service.ProjecttypeService;
@Service
public class ProjecttypeServiceImpl implements ProjecttypeService {

	@Autowired
	ProjecttypeMapper projecttypeMapper;
	
	public int insertSelective(Projecttype projecttype) {
		return projecttypeMapper.insertSelective(projecttype);
	}

	public List<Projecttype> getAllProjectType() {
		return projecttypeMapper.getAllProjectType();
	}

	public List<Map<String, Object>> getProjecttypesMony() {

		return projecttypeMapper.getProjecttypesMony() ;
	}
	
}
