package com.aisouji.manager.dao;

import java.util.List;
import java.util.Map;

import com.aisouji.bean.Advertisement;
import com.aisouji.bean.User;

public interface AdvertisementMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Advertisement record);

    int insertSelective(Advertisement record);

    Advertisement selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Advertisement record);

    int updateByPrimaryKey(Advertisement record);

	Integer queryCount(Map<String, Object> map);

	List<User> queryPages(Map<String, Object> map);
}