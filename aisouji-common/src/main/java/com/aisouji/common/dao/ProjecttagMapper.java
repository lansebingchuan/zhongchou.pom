package com.aisouji.common.dao;

import com.aisouji.bean.Projecttag;

public interface ProjecttagMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Projecttag record);

    int insertSelective(Projecttag record);

    Projecttag selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Projecttag record);

    int updateByPrimaryKey(Projecttag record);
}