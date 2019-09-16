package com.aisouji.common.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.aisouji.bean.Project;

public interface ProjectMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Project record);

    int insertSelective(Project record);

    Project selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Project record);

    int updateByPrimaryKey(Project record);

    Integer insertMemberProject(Project project);

	void updateFollowById(@Param("followerid")Integer followid,@Param("id") Integer id);

	Integer updaeProjectStatusByMemberIdAndProjectId(@Param("projectid")Integer projectid,@Param("memberid") Integer memberid, @Param("status")String status);

	Map<String, Object> getProjectMoneyByProjectid(Integer projectid);

	List<Map<String, Object>> getProjrctSupporterMemberByProjectid(Integer projectid);

	Map<String, Object> getProjectReturnByResultid(@Param("projectid")Integer projectid,@Param("resultid") Integer resultid);

	Integer updateCompletionByProjectId(@Param("projectid")Integer projectid,@Param("completion") int completion);

	List<Map<String, Object>> getMemberSupportProjectBymemberId(Integer memberid);

	List<Map<String, Object>> getMemberReleaseProjectByMemberId(Integer memberid);
}