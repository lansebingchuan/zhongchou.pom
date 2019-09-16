package com.aisouji.manager.serviceImpl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aisouji.manager.dao.TestDao;
import com.aisouji.manager.service.TestService;
@Service
public class TestServiceImpl implements TestService{
	
	@Autowired
	private TestDao testDao;
	
	public void insert() {
		// TODO Auto-generated method stub
		Map<String, String> map = new HashMap<String, String>();
		map.put("name", "张海涛");
		testDao.insert(map);
	}

}
