package com.aisouji.manager.service;

public interface AccounttypecertService {

	Integer delete_Account_CertID(Integer certid, String accttypeid);//根据两个资质id ，和 账户类型值，删除关系

	Integer insert(Integer certid, String accttypeid);

}
