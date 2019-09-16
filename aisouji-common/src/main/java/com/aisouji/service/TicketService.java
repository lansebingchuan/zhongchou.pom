package com.aisouji.service;

import java.util.List;
import java.util.Map;

import com.aisouji.bean.Member;
import com.aisouji.bean.Ticket;

public interface TicketService {

	Ticket getTicketByMemberId(Integer id);

	Integer saveTicket(Ticket dataTicketMember);

	Integer updateTicketOnPstep(Integer integer, String string);

	Integer updateTicketByMemberId(Ticket ticket);//不为空

	Member getMemberByInstanceId(String processInstanceId);

	List<Map<String, Object>> getAuditMemberByMemberid(Integer memberid);//取出需要审核的实名认证数据

	Integer updaeMemberStatusByMemberId(Integer memberid, String status);//对member进行审核，通过id 

	Map<String, Object> getProjectByInstanceId(String processInstanceId);////取出需要审核的项目数据

}
