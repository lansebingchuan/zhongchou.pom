package com.aisouji.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aisouji.bean.Cert;
import com.aisouji.common.dao.CertMapper;
import com.aisouji.service.CertService;

@Service
public class CertServiceImpl implements CertService {

	@Autowired
	CertMapper certMapper;

	public List<Cert> getInitCertList() {
		return certMapper.getInitCertList();
	}

	public List<String> getAccttypeList(Integer certId) {
		return certMapper.getAccttypeList(certId);
	}
}
