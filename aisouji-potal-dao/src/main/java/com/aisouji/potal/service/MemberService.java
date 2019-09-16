package com.aisouji.potal.service;

import java.util.List;
import java.util.Map;

import com.aisouji.bean.Member;
import com.aisouji.bean.Projecttype;
import com.aisouji.bean.Tag;
import com.aisouji.bean.Type;

public interface MemberService {

	Member memberLogin(Map<String, String> map);//登录判断

	Integer updateByPrimaryKeySelective(Member member);

	List<Integer> getAccounttypeList(String accttype);//根据资质，获取所有的资质id

	Member getMemberByid(Integer id);//通过id获取Member

	List<Type> getAllType();///获取所有的项目类型

	List<Tag> getAllTag();//获取所有的项目标签

}
