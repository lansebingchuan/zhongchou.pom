package com.aisouji.manager.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aisouji.bean.Userrole;
import com.aisouji.manager.dao.UserroleMapper;
import com.aisouji.manager.service.UserroleService;

@Service
public class UserroleServiceImpl implements UserroleService{

	@Autowired
	private UserroleMapper userroleMapper;
    
}