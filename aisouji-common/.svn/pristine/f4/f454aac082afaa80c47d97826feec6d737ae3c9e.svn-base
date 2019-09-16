package com.aisouji.util;

import java.util.List;

import com.aisouji.bean.User;

public class Page {
	
	private Integer pagePoint;//当前页
	private Integer pageSize;//每页个数
	private Integer totalSize;//总共的个数
	private Integer totalPage;//总共的页数
	private Integer startIndex;//开始的地方
	private List<Object> lists; 
	public Page(Integer pagePoint ,Integer pageSize) {
		// TODO Auto-generated constructor stub
		if (pagePoint <= 0) {
			this.pagePoint = 1;
		}else {
			this.pagePoint = pagePoint;
		}
		if (pageSize <= 0 ) {
			this.pageSize = 10;
		}else {
			this.pageSize = pageSize;
		}
	}
	
	public List getLists() {
		return lists;
	}

	public void setLists(List lists) {
		this.lists = lists;
	}

	public Integer getPagePoint() {
		return pagePoint;
	}
	public void setPagePoint(Integer pagePoint) {
		this.pagePoint = pagePoint;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public Integer getTotalSize() {
		return totalSize;
	}
	public void setTotalSize(Integer totalSize) {
		this.totalSize = totalSize;
		this.totalPage = (totalSize%pageSize) == 0 ? totalSize/pageSize : (totalSize/pageSize + 1);
	}
	public Integer getTotalPage() {
		return totalPage;
	}
	private void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}
	public Integer getStartIndex() {
		return (this.pagePoint - 1) * this.pageSize;
	}
	
}
