package com.aisouji.potal.service;

import java.util.Map;

import com.aisouji.bean.Memberaddress;

public interface MemberaddressService {

	Integer alertMemberAddress(Memberaddress memberaddress);//

	Integer alertMemberAddressByMemberid(Memberaddress memberaddress);//根据memberid修改地址

	Map<String, Object> getAddressByMemberId(Integer memberid);//通过，会员获取邮寄地址 

}
