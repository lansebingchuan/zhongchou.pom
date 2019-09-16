package com.aisouji.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aisouji.bean.Memberprojectfollow;
import com.aisouji.common.dao.MemberprojectfollowMapper;
import com.aisouji.service.MemberprojectfollowService;

@Service
public class MemberprojectfollowServiceImpl implements MemberprojectfollowService{

	@Autowired
	MemberprojectfollowMapper memberprojectfollowMapper;

	public int insertSelective(Memberprojectfollow memberprojectfollow) {
		return memberprojectfollowMapper.insertSelective(memberprojectfollow);
	}

	public Integer insertMemberprojectfollow(Memberprojectfollow memberprojectfollow) {
		return memberprojectfollowMapper.insertMemberprojectfollow(memberprojectfollow);
	}
}
