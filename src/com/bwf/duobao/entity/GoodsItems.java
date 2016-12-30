package com.bwf.duobao.entity;

import java.util.List;

/**
*@author Li gang
*@date  2016年6月6日上午3:54:20
*@declaration
*/
public class GoodsItems {
	public int pageNum;
	public int pageSize;
	public int totalCnt;
	public java.util.List<GoodsItem> list;
	public GoodsItems(int pageSize, int pageNum, int totalCnt, List<GoodsItem> list) {
		super();
		this.pageSize = pageSize;
		this.pageNum = pageNum;
		this.totalCnt = totalCnt;
		this.list = list;
	}
	public GoodsItems() {
		super();
	}
	public int getPageNum() {
		return pageNum;
	}
	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getTotalCnt() {
		return totalCnt;
	}
	public void setTotalCnt(int totalCnt) {
		this.totalCnt = totalCnt;
	}
	public java.util.List<GoodsItem> getList() {
		return list;
	}
	public void setList(java.util.List<GoodsItem> list) {
		this.list = list;
	}
	
}
