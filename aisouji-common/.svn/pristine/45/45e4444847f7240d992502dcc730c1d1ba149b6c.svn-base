package com.aisouji.serviceImpl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aisouji.bean.Member;
import com.aisouji.bean.Ticket;
import com.aisouji.common.dao.TicketMapper;
import com.aisouji.service.TicketService;

@Service
public class TicketServiceImpl implements TicketService {

	@Autowired
	TicketMapper ticketMapper;

	public Ticket getTicketByMemberId(Integer memberId) {
		return ticketMapper.getTicketByMemberId(memberId);
	}

	public Integer saveTicket(Ticket dataTicketMember) {
		return ticketMapper.insertSelective(dataTicketMember);
	}

	public Integer updateTicketOnPstep(Integer memberid,String pstep) {
		return ticketMapper.updateTicketOnPstep(memberid ,pstep);
	}

	public Integer updateTicketByMemberId(Ticket ticket) {
		return ticketMapper.updateTicketByMemberId(ticket);
	}

	public Member getMemberByInstanceId(String processInstanceId) {
		return ticketMapper.getMemberByInstanceId(processInstanceId) ;
	}

	public List<Map<String, Object>> getAuditMemberByMemberid(Integer memberid) {
		return ticketMapper.getAuditMemberByMemberid(memberid);
	}

	public Integer updaeMemberStatusByMemberId(Integer memberid, String status) {
		return ticketMapper.updaeMemberStatusByMemberId(memberid,status);
	}

	public Map<String, Object> getProjectByInstanceId(String processInstanceId) {
		return ticketMapper.getProjectByInstanceId(processInstanceId);
	}
}
