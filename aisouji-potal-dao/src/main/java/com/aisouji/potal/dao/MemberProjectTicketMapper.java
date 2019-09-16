package com.aisouji.potal.dao;

import org.apache.ibatis.annotations.Param;

import com.aisouji.bean.MemberProjectTicket;

public interface MemberProjectTicketMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MemberProjectTicket record);

    int insertSelective(MemberProjectTicket memberProjectTicket);

    MemberProjectTicket selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MemberProjectTicket record);

    int updateByPrimaryKey(MemberProjectTicket record);

	MemberProjectTicket getProjectTickeByMemberId(Integer memberid);

	void updateCurrenUrlByMemberId(@Param("currenturl")String currenturl,@Param("memberid") Integer memberid);
}