package com.aisouji.common.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.aisouji.bean.Member;
import com.aisouji.bean.Ticket;

public interface TicketMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Ticket record);

    int insertSelective(Ticket record);

    Ticket selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Ticket record);

    int updateByPrimaryKey(Ticket record);

	Integer updateTicketOnPstep(@Param("memberid")Integer memberid,@Param("pstep") String pstep);

	Integer updateTicketByMemberId(Ticket ticket);

	Ticket getTicketByMemberId(Integer memberId);

	Member getMemberByInstanceId(String processInstanceId);//根据实例id查找会员

	List<Map<String, Object>> getAuditMemberByMemberid(Integer memberid);

	Integer updaeMemberStatusByMemberId(@Param("memberid")Integer memberid, @Param("status")String status);

	Map<String, Object> getProjectByInstanceId(String processInstanceId);
}