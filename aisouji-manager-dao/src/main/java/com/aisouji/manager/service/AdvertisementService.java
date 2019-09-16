package com.aisouji.manager.service;

import java.util.Map;

import com.aisouji.bean.Advertisement;
import com.aisouji.util.Page;

public interface AdvertisementService {

	Page queryPage(Map<String, Object> map);

	Advertisement getAdvertisementById(Integer id);

	Integer updateAdvertisement(Advertisement advertisement);

	Integer saveAdvertisement(Advertisement advertisement);

	Integer removeAdvertisementById(Integer id);


}
