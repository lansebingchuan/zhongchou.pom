package com.aisouji.serviceImpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aisouji.bean.Project;
import com.aisouji.common.dao.ProjectMapper;
import com.aisouji.service.ProjectService;
import com.aisouji.util.DateUtil;

@Service
public class ProjectServiceImpl implements ProjectService {

	@Autowired
	ProjectMapper projectMapper;
	
	public int insertSelective(Project record) {
		return projectMapper.insertSelective(record);
	}

	public Integer insertMemberProject(Project project) {
		return projectMapper.insertMemberProject(project);
	}

	public void updateFollowById(Integer followid, Integer id) {
		projectMapper.updateFollowById(followid, id);
	}

	public Integer updaeProjectStatusByMemberIdAndProjectId(Integer projectid, Integer memberid, String status) {
		return projectMapper.updaeProjectStatusByMemberIdAndProjectId(projectid, memberid,status);
	}

	public Map<String, Object> getProjectMoneyByProjectid(Integer projectid) {
		return projectMapper.getProjectMoneyByProjectid( projectid) ;
	}

	public List<Map<String, Object>> getProjrctSupporterMemberByProjectid(Integer projectid) {
		return projectMapper.getProjrctSupporterMemberByProjectid( projectid) ;
	}

	public Map<String, Object> getProjectReturnByResultid(Integer projectid, Integer resultid) {
		return projectMapper.getProjectReturnByResultid( projectid,  resultid) ;
	}

	public Integer updateCompletionByProjectId(Integer projectid, int completion) {
		return projectMapper.updateCompletionByProjectId( projectid,  completion);
	}

	public List<Map<String, Object>> getMemberSupportProjectBymemberId(Integer memberid) {
		List<Map<String, Object>> membersupport= projectMapper.getMemberSupportProjectBymemberId( memberid);
		for (Map<String, Object> mapsupport : membersupport) {
			String deploydate = (String) mapsupport.get("deploydate");
			LocalDateTime localDate = DateUtil.getLocalDate(deploydate);
			Integer day = (Integer) mapsupport.get("day");
			LocalDateTime endDays = localDate.plusDays(day);
			String remainingDay = DateUtil.getBetweenDateDay(DateUtil.getLocalDateNow(), endDays);
			mapsupport.put("remainingDay", remainingDay);
		}
		return membersupport;
	}

	public List<Map<String, Object>> getMemberReleaseProjectByMemberId(Integer memberid) {
		List<Map<String, Object>> memberinitiate= projectMapper.getMemberReleaseProjectByMemberId( memberid);
		for (Map<String, Object> mapinitiate : memberinitiate ) {
			String deploydate = (String) mapinitiate.get("deploydate");
			LocalDateTime localDate = DateUtil.getLocalDate(deploydate);
			Integer day = (Integer) mapinitiate.get("day");
			LocalDateTime endDays = localDate.plusDays(day);
			String remainingDay = DateUtil.getBetweenDateDay(DateUtil.getLocalDateNow(), endDays);
			mapinitiate.put("remainingDay", remainingDay);
		}
		return memberinitiate;
	}

}
