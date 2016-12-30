package com.bwf.duobao.instance.list;

public class Good {
	private String title;
	private String img;
	private int goodsId;
	private long period;
	private int remainTimes;
	private int totalTimes;
	private int category;
	private int joinTimes;
	private boolean updated;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	
	public int getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(int goodsId) {
		this.goodsId = goodsId;
	}
	public long getPeriod() {
		return period;
	}
	public void setPeriod(long period) {
		this.period = period;
	}
	public int getRemainTimes() {
		return remainTimes;
	}
	public void setRemainTimes(int remainTimes) {
		this.remainTimes = remainTimes;
	}
	public int getTotalTimes() {
		return totalTimes;
	}
	public void setTotalTimes(int totalTimes) {
		this.totalTimes = totalTimes;
	}
	public int getCategory() {
		return category;
	}
	public void setCategory(int category) {
		this.category = category;
	}
	public int getJoinTimes() {
		return joinTimes;
	}
	public void setJoinTimes(int joinTimes) {
		this.joinTimes = joinTimes;
	}
	public boolean isUpdated() {
		return updated;
	}
	public void setUpdated(boolean updated) {
		this.updated = updated;
	}
	
}
