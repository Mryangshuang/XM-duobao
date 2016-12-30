package com.bwf.duobao.instance.personalcenter;

import java.util.ArrayList;

public class Result {
	private int pageNum;
	private int pageSize;
	private int totalCut;
	private int uid;
	private ArrayList<LuckInfo> list;
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
	public int getTotalCut() {
		return totalCut;
	}
	public void setTotalCut(int totalCut) {
		this.totalCut = totalCut;
	}
	public ArrayList<LuckInfo> getList() {
		return list;
	}
	public void setList(ArrayList<LuckInfo> list) {
		this.list = list;
	}
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	
	
}
