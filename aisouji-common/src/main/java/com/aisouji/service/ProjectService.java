package com.aisouji.service;

import java.util.List;
import java.util.Map;

import com.aisouji.bean.Project;

public interface ProjectService {
	
	int insertSelective(Project record);

	Integer insertMemberProject(Project project);//发布一个项目，返回项目id

	void updateFollowById(Integer followid, Integer id);//根据项目id更新关联的字段followid(外键）

	//根据项目id和发起者id更新项目状态
	Integer updaeProjectStatusByMemberIdAndProjectId(Integer projectid, Integer memberid, String status);

	Map<String, Object> getProjectMoneyByProjectid(Integer projectid);//获取一个项目所有的回报设置

	List<Map<String, Object>> getProjrctSupporterMemberByProjectid(Integer projectid);//获取所有的项目支持

	Map<String, Object> getProjectReturnByResultid(Integer projectid, Integer resultid);////获取一个项目的一个回报设置，

	Integer updateCompletionByProjectId(Integer projectid, int completion);//更新会员的订单完成状态,1:完成，0：未完成

	List<Map<String, Object>> getMemberSupportProjectBymemberId(Integer memberid);//获取一个会员所支持的项目

	List<Map<String, Object>> getMemberReleaseProjectByMemberId(Integer memberid);//获取会员发布的项目
}
