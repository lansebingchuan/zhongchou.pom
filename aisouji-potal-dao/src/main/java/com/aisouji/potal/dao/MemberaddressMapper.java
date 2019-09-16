package com.aisouji.potal.dao;

import java.util.Map;

import com.aisouji.bean.Memberaddress;

public interface MemberaddressMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Memberaddress record);

    int insertSelective(Memberaddress record);

    Memberaddress selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Memberaddress record);

    int updateByPrimaryKey(Memberaddress record);

	Integer alertMemberAddressByMemberid(Memberaddress memberaddress);

	Map<String, Object> getAddressByMemberId(Integer memberid);
}