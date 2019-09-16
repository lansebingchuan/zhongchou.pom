package com.aisouji.potal.service;

import com.aisouji.bean.MemberProjectTicket;

public interface MemberProjectTicketService {

    int insertSelective(MemberProjectTicket memberProjectTicket);//新增一个项目审批运行过程

	MemberProjectTicket getProjectTickeByMemberId(Integer id);

	void updateCurrenUrlByMemberId(String currenturl, Integer memberid);
}
