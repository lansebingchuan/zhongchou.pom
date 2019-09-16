package com.aisouji.common.dao;

import java.util.List;

import com.aisouji.bean.Cert;

public interface CertMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Cert record);

    int insertSelective(Cert record);

    Cert selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Cert record);

    int updateByPrimaryKey(Cert record);

	List<Cert> getInitCertList();

	List<String> getAccttypeList(Integer certId);
}