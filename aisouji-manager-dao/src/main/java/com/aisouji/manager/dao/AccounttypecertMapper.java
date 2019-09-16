package com.aisouji.manager.dao;

import org.apache.ibatis.annotations.Param;

import com.aisouji.bean.Accounttypecert;

public interface AccounttypecertMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Accounttypecert record);

    int insertSelective(Accounttypecert record);

    Accounttypecert selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Accounttypecert record);

    int updateByPrimaryKey(Accounttypecert record);

	Integer delete_Account_CertID(@Param("certid")Integer certid,@Param("accttypeid") String accttypeid);

}