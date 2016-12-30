package com.bwf.duobao.entity;

import com.bwf.duobao.instance.goodsdetails.LuckUser;

/**
*@author Li gang
*@date  2016年6月6日上午2:36:29
*@declaration
*/
public class GoodsItem {
	private String title;
	private String img;
	private String progress;
	private int goodsId;
	private int price;
	private int remainTimes;
	private int totalTimes;
	private int category;
	private long period;
	
	/**WinFragment 添加**/
	
	private String goodsName;
	private String revealedTime;
	private String remainTime;
	private long enterTime;
	private LuckUser luckUser;
	private int status;
	private Integer luckCode;
	
	
	
	public int getCategory() {
		return category;
	}
	public void setCategory(int category) {
		this.category = category;
	}
	public void setLuckCode(Integer luckCode) {
		this.luckCode = luckCode;
	}
	public long getEnterTime() {
		return enterTime;
	}
	public void setEnterTime(long enterTime) {
		this.enterTime = enterTime;
	}
	public String getRevealedTime() {
		return revealedTime;
	}
	public void setRevealedTime(String revealedTime) {
		this.revealedTime = revealedTime;
	}
	public LuckUser getLuckUser() {
		return luckUser;
	}
	public void setLuckUser(LuckUser luckUser) {
		this.luckUser = luckUser;
	}
	public int getLuckCode() {
		return luckCode;
	}
	public void setLuckCode(int luckCode) {
		this.luckCode = luckCode;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getRemainTime() {
		return remainTime;
	}
	public void setRemainTime(String remainTime) {
		this.remainTime = remainTime;
	}
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
	public String getProgress() {
		return progress;
	}
	public void setProgress(String progress) {
		this.progress = progress;
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
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
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
	
}
