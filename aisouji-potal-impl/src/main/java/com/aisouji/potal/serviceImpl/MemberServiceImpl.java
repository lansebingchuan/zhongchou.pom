package com.aisouji.potal.serviceImpl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aisouji.bean.Member;
import com.aisouji.bean.Projecttype;
import com.aisouji.bean.Tag;
import com.aisouji.bean.Type;
import com.aisouji.potal.dao.MemberMapper;
import com.aisouji.potal.service.MemberService;

@Service
public class MemberServiceImpl implements MemberService {

	@Autowired
	MemberMapper aMemberMapper;

	public Member memberLogin(Map<String, String> map) {
		try {
			Member memberLogin = aMemberMapper.memberLogin(map);
			if (memberLogin != null) {
				return memberLogin;
			}
		} catch (Exception e) {
			return null;
		}
		return null;

	}

	public Integer updateByPrimaryKeySelective(Member member) {
		return aMemberMapper.updateByPrimaryKeySelective(member);
	}

	public List<Integer> getAccounttypeList(String accttypeId) {
		return aMemberMapper.getAccounttypeList(accttypeId) ;
	}

	public Member getMemberByid(Integer id) {//通过id获取Member
		return aMemberMapper.selectByPrimaryKey(id);
	}

	public List<Type> getAllType() {
		return aMemberMapper.getAllType();
	}

	public List<Tag> getAllTag() {
		return aMemberMapper.getAllTag();
	}
}
