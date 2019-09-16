package com.aisouji.potal.dao;

import java.util.List;

import com.aisouji.bean.Membercert;

public interface MembercertMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Membercert record);

    int insertSelective(Membercert record);

    Membercert selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Membercert record);

    int updateByPrimaryKey(Membercert record);

	Integer insertMembercert(List<Membercert> membercertList);
}