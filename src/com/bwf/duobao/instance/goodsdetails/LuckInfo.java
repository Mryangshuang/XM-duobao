package com.bwf.duobao.instance.goodsdetails;

public class LuckInfo {
	
	/** 往期揭晓  加上的 **/
	private String period;
	private int status;
	private String startTime;
	private String cutoffTime;
	private String revealedTime;
	private LuckUser luckUser;
	private int luckCode;
	
	
	/*** * 夺宝详情添加*/
	
	private int rid;
	private int times;
	private String time;
	
	public int getRid() {
		return rid;
	}
	public void setRid(int rid) {
		this.rid = rid;
	}
	public int getTimes() {
		return times;
	}
	public void setTimesj(int times) {
		this.times = times;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getPeriod() {
		return period;
	}
	public void setPeriod(String period) {
		this.period = period;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getCutoffTime() {
		return cutoffTime;
	}
	public void setCutoffTime(String cutoffTime) {
		this.cutoffTime = cutoffTime;
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
	
}
