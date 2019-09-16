package com.aisouji.serviceImpl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aisouji.bean.ProjectOrder;
import com.aisouji.common.dao.ProjectOrderMapper;
import com.aisouji.service.ProjectOrderService;

@Service
public class ProjectOrderServiceImpl implements ProjectOrderService {

	@Autowired
	ProjectOrderMapper projectOrderMapper;

	public Integer addProjectOrder(ProjectOrder projectOrder) {
		return projectOrderMapper.insertSelective(projectOrder);
	}

	public ProjectOrder getProjectOrderByOrderNumber(String orderNumber) {
		return projectOrderMapper.getProjectOrderByOrderNumber( orderNumber);
	}

	public Integer updateProjectOrderByOrderNumber(ProjectOrder projectOrder) {
		return projectOrderMapper.updateProjectOrderByOrderNumber(projectOrder);
	}

	public Map<String, Object>  getProjectOrderMessageByOrderNumber(String orderNumber) {
		return projectOrderMapper.getProjectOrderMessageByOrderNumber( orderNumber);
	}
	
	
}
