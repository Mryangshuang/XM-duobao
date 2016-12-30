package com.bwf.duobao.instance.goodsdetails;

import java.util.ArrayList;

public class Result {
	/** w往期揭晓加上的 **/
	private int pageNum;
	private int pageSize;
	private int totalCut;
	private ArrayList<LuckInfo> list;

	/**个人中心添加**/
	private int uid;
	
	/**产品信息界面  未揭晓  待揭晓   已揭晓 三种状态**/
	private int goodsId;
	private String goodsName;
	private ArrayList<String> bigImgs;
	
	
	private String description;
	private long period;
	private int totalTimes;
	private int remainTimes;
	private int status;
	private int remainTime;
	private String revealedTime;
	private LuckUser luckUser;
	private int luckCode;
	private long startDuobaoTime;

	

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
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

	public int getRemainTime() {
		return remainTime;
	}

	public void setRemainTime(int remainTime) {
		this.remainTime = remainTime;
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

	public int getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(int goodsId) {
		this.goodsId = goodsId;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public ArrayList<String> getBigImgs() {
		return bigImgs;
	}

	public void setBigImgs(ArrayList<String> bigImgs) {
		this.bigImgs = bigImgs;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public long getPeriod() {
		return period;
	}

	public void setPeriod(long period) {
		this.period = period;
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

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public long getStartDuobaoTime() {
		return startDuobaoTime;
	}

	public void setStartDuobaoTime(long startDuobaoTime) {
		this.startDuobaoTime = startDuobaoTime;
	}

}
