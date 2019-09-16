package com.aisouji.manager.serviceImpl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aisouji.bean.Advertisement;
import com.aisouji.bean.User;
import com.aisouji.manager.dao.AdvertisementMapper;
import com.aisouji.manager.service.AdvertisementService;
import com.aisouji.util.Page;

@Service
public class AdvertisementServiceImpl implements AdvertisementService{

	@Autowired
	AdvertisementMapper aMapper;

	public Page queryPage(Map<String, Object> map) {
		// TODO Auto-generated method stub
		Page page = new Page((Integer)map.get("pagePoint"),(Integer) map.get("pageSize"));
		Integer startIndex = page.getStartIndex();
		map.put("startIndex", startIndex);
		Integer totalSize = aMapper.queryCount(map);
		page.setTotalSize(totalSize);
		List<User> list = aMapper.queryPages(map);
		page.setLists(list);
		return page;
	}

	public Advertisement getAdvertisementById(Integer id) {
		return aMapper.selectByPrimaryKey(id);
	}

	public Integer updateAdvertisement(Advertisement advertisement) {
		return aMapper.updateByPrimaryKeySelective(advertisement);
	}

	public Integer saveAdvertisement(Advertisement advertisement) {
		return aMapper.insert(advertisement);
	}

	public Integer removeAdvertisementById(Integer id) {
		return aMapper.deleteByPrimaryKey(id);
	}


}
