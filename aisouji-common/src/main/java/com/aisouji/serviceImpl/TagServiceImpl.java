package com.aisouji.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aisouji.bean.Tag;
import com.aisouji.common.dao.TagMapper;
import com.aisouji.service.TagService;


@Service
public class TagServiceImpl implements TagService {

	@Autowired
	TagMapper tagMapper;

	public List<Tag> getAllTag() {
		return tagMapper.getAllTag();
	}
}
