package com.aisouji.manager.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aisouji.bean.Tag;
import com.aisouji.service.TagService;
import com.aisouji.util.BaseController;

@Controller
@RequestMapping("/tag")
public class OperationTagController extends BaseController{

	@Autowired
	TagService tagService;
	
	@RequestMapping("/initTag")
	@ResponseBody
	public Object initTag() {
		start();
		List<Tag> rootTag = new ArrayList<Tag>();//Tag根
		Map<Integer, Tag> map = new HashMap<Integer, Tag>();
		List<Tag> lTags  = tagService.getAllTag();
		for (Tag tag : lTags) {
			map.put(tag.getId(), tag);
		}
		for (Tag tag : lTags) {
			if (tag.getPid() == null) {//如果为根
				tag.setOpen(true);
				rootTag.add(tag);
			}else {
				Tag parent = map.get(tag.getPid());//父亲
				parent.getChildren().add(tag);
			}
		}
		put("rootTag", rootTag);
		message("标签树加载成功！");
		flage(true);
		return end();
		
	}
	
}
