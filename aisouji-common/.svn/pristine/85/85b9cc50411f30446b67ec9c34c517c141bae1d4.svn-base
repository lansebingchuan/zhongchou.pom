package com.aisouji.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aisouji.bean.Return;
import com.aisouji.common.dao.ReturnMapper;
import com.aisouji.service.ReturnService;

@Service
public class ReturnServiceImpl implements ReturnService{

	@Autowired
	ReturnMapper returnMapper;

	public Integer insertSelective(Return record) {
		return returnMapper.insertSelective(record);
	}

	public List<Return> getProjectResultByProjectid(Integer projectid) {
		return returnMapper.getProjectResultByProjectid(projectid);
	}
}
