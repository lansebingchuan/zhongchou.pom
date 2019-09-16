package com.aisouji.potal.serviceImpl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aisouji.bean.Memberaddress;
import com.aisouji.potal.dao.MemberaddressMapper;
import com.aisouji.potal.service.MemberaddressService;

@Service
public class MemberaddressServiceImpl implements MemberaddressService {

	@Autowired
	MemberaddressMapper memberaddressMapper;
	

	public Integer alertMemberAddressByMemberid(Memberaddress memberaddress) {
		return memberaddressMapper.alertMemberAddressByMemberid( memberaddress);
	}


	public Integer alertMemberAddress(Memberaddress memberaddress) {
		return null;
	}


	public Map<String, Object> getAddressByMemberId(Integer memberid) {
		return memberaddressMapper.getAddressByMemberId( memberid);
	}

}
