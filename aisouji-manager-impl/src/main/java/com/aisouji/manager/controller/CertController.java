package com.aisouji.manager.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aisouji.bean.Cert;
import com.aisouji.manager.service.AccounttypecertService;
import com.aisouji.service.CertService;
import com.aisouji.util.BaseController;

@Controller
@RequestMapping("/cert")
public class CertController extends BaseController{
	
	@Autowired
	CertService certService;
	
	@Autowired
	AccounttypecertService accounttypecertService;
	
	@ResponseBody
	@RequestMapping("/initCertList")
	public Object initCertList() {//初始化资质的类型
		start();
		try {
			List<Cert> certList = certService.getInitCertList();
			flage(true);
			message("资质数据加载成功！");
			put("certList", certList);
		} catch (Exception e) {
			flage(false);
			message("资质数据加载失败！");
			e.printStackTrace();
		}
		return end();
	}
	@ResponseBody
	@RequestMapping("/initCert_Accttype")
	public Object initCert_Accttype() {//初始化资质与账户类型之间的关系
		start();
		try {
			List<Cert> certList = certService.getInitCertList();
			List<List<String>> listAccttypes = new ArrayList<List<String>>();
			for (Cert cert : certList) {
				List<String> listAccttype = certService.getAccttypeList(cert.getId());
				listAccttypes.add(listAccttype);
			}
			flage(true);
			message("资质数据加载成功！");
			put("certList", certList);
			put("listAccttypes", listAccttypes);
		} catch (Exception e) {
			flage(false);
			message("资质数据加载失败！");
			e.printStackTrace();
		}
		return end();
	}
	@ResponseBody
	@RequestMapping("/delCert_AcctType")
	public Object delCert_AcctType(Integer certid , String accttypeid , boolean flage) {//添加 、  删除  初始化资质与账户类型之间的关系
		start();////flage  --- 为false表示删除
		try {
			if (!flage) {//删除
				Integer i = accounttypecertService.delete_Account_CertID(certid , accttypeid);
				if (i >= 0) {
					flage(true);
					message("资质数据更新成功！");
				}
			}else {//添加
				Integer i = accounttypecertService.insert(certid , accttypeid);
				if (i >= 0) {
					flage(true);
					message("资质数据更新成功！");
				}
			}
		} catch (Exception e) {
			flage(false);
			message("资质数据更新失败！");
			e.printStackTrace();
		}
		return end();
	}
}
