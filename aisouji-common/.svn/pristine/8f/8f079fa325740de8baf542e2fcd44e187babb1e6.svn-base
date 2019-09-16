package com.aisouji.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aisouji.bean.Projecttag;
import com.aisouji.common.dao.ProjecttagMapper;
import com.aisouji.service.ProjecttagService;

@Service
public class ProjecttagServiceImpl implements ProjecttagService {

	@Autowired
	ProjecttagMapper projecttagMapper;

	public int insertSelective(Projecttag projecttag) {
		return projecttagMapper.insertSelective(projecttag);
	}
	
	
	
}
