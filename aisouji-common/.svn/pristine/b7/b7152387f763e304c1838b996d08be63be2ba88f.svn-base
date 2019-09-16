package com.aisouji.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BaseController {
      
	private ThreadLocal<Map<String, Object>> threadLocalmap = new ThreadLocal<Map<String,Object>>();
	
	Map<String, Object> map;
	
	protected void start() {
		map = new HashMap<String, Object>();
		threadLocalmap.set(map);
	}
	
	protected void message(String message) {
		map.put("message", message);
	}
	
	protected void flage(boolean flage) {
		map.put("flage", flage);
	}
	
	protected void put(String key , Object object) {
		map.put(key, object);
	}
	
	protected void putlist(String key , List<Object> listobject) {
		map.put(key, listobject);
	}
	
	protected void putList_Map(String key ,List<Map<String, Object>> list) {
		map.put(key, list);
	}
	
	protected Map<String, Object> end() {
		Map<String, Object> map = threadLocalmap.get();
		threadLocalmap.remove();
		return map;
	}
	
}
