package com.aisouji.potal.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aisouji.bean.Membercert;
import com.aisouji.potal.dao.MembercertMapper;
import com.aisouji.potal.service.MembercertService;

@Service
public class MembercertServiceImpl implements MembercertService {

	@Autowired
	MembercertMapper membercertMapper;

	public Integer insertMembercert(List<Membercert> membercertList) {
		return membercertMapper.insertMembercert(membercertList) ;
	}
	
}
