package com.aisouji.manager.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aisouji.bean.Accounttypecert;
import com.aisouji.manager.dao.AccounttypecertMapper;
import com.aisouji.manager.service.AccounttypecertService;

@Service
public class AccounttypecertServiceImpl implements AccounttypecertService {
 
	@Autowired
	AccounttypecertMapper accounttypecertMapper;

	public Integer delete_Account_CertID(Integer certid, String accttypeid) {
		return accounttypecertMapper.delete_Account_CertID(certid , accttypeid);
	}

	public Integer insert(Integer certid, String accttypeid) {
		// TODO Auto-generated method stub
		Accounttypecert accounttypecert = new Accounttypecert(certid , accttypeid);
		return accounttypecertMapper.insertSelective(accounttypecert);
	}
}
