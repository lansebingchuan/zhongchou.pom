package com.aisouji.potal.dao;

import java.util.List;
import java.util.Map;

import com.aisouji.bean.Member;
import com.aisouji.bean.Projecttype;
import com.aisouji.bean.Tag;
import com.aisouji.bean.Type;

public interface MemberMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Member record);

    int insertSelective(Member record);

    Member selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Member record);

    int updateByPrimaryKey(Member record);

	Member memberLogin(Map<String, String> map);//

	List<Integer> getAccounttypeList(String accttypeId);

	List<Type> getAllType();

	List<Tag> getAllTag();
}