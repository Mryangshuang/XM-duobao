package com.bwf.duobao.instance.personalcenter;

import com.bwf.duobao.instance.goodsdetails.LuckUser;


public class LuckInfo {
	private int status;
	private int goodsId;
	private int category;
	private String goodsName;
	private String img;
	private long period;
	private int duoboaTimes;
	private int totalTimes;
	private int remainTimes;
	private int luckCode;
	private LuckUser luckUser;
	
	
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(int goodsId) {
		this.goodsId = goodsId;
	}
	public int getCategory() {
		return category;
	}
	public void setCategory(int category) {
		this.category = category;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public long getPeriod() {
		return period;
	}
	public void setPeriod(long period) {
		this.period = period;
	}
	public int getDuoboaTimes() {
		return duoboaTimes;
	}
	public void setDuoboaTimes(int duoboaTimes) {
		this.duoboaTimes = duoboaTimes;
	}
	public int getTotalTimes() {
		return totalTimes;
	}
	public void setTotalTimes(int totalTimes) {
		this.totalTimes = totalTimes;
	}
	public int getRemainTimes() {
		return remainTimes;
	}
	public void setRemainTimes(int remainTimes) {
		this.remainTimes = remainTimes;
	}
	public int getLuckCode() {
		return luckCode;
	}
	public void setLuckCode(int luckCode) {
		this.luckCode = luckCode;
	}
	public LuckUser getLuckUser() {
		return luckUser;
	}
	public void setLuckUser(LuckUser luckUser) {
		this.luckUser = luckUser;
	}
	
}
