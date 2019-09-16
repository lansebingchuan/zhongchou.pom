package com.aisouji.potal.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aisouji.bean.MemberProjectTicket;
import com.aisouji.potal.dao.MemberProjectTicketMapper;
import com.aisouji.potal.service.MemberProjectTicketService;

@Service
public class MemberProjectTicketServiceImpl implements MemberProjectTicketService {

	@Autowired
	MemberProjectTicketMapper memberProjectTicketMapper;

	public int insertSelective(MemberProjectTicket memberProjectTicket) {
		return memberProjectTicketMapper.insertSelective(memberProjectTicket);
	}

	public MemberProjectTicket getProjectTickeByMemberId(Integer memberid) {
		return memberProjectTicketMapper.getProjectTickeByMemberId(memberid) ;
	}
	
	public void updateCurrenUrlByMemberId(String currenturl, Integer memberid) {
		memberProjectTicketMapper.updateCurrenUrlByMemberId(currenturl,memberid);
	}
	
}
