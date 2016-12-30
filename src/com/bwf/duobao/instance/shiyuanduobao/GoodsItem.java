package com.bwf.duobao.instance.shiyuanduobao;

import java.util.ArrayList;

public class GoodsItem {
	private int pageNum;
	private int pageSize;
	private int totalCnt;
	private ArrayList<Good> list;
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
	public ArrayList<Good> getList() {
		return list;
	}
	public void setList(ArrayList<Good> list) {
		this.list = list;
	}
	
	
	
}
