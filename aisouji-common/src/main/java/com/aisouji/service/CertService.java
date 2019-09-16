package com.aisouji.service;

import java.util.List;

import com.aisouji.bean.Cert;

public interface CertService {

	List<Cert> getInitCertList();//获取所有的资质

	List<String> getAccttypeList(Integer id);//获取所有的与资质对应的账户类型

}
