package com.bwf.duobao.instance.jisuanjieguo;

import java.util.ArrayList;

public class Result {
	private int totalCnt;
	private String timeCountForCompute;
	private int luckCode;
	private int totalTimes;
	private ArrayList <Person>timesList;
	public int getTotalCnt() {
		return totalCnt;
	}
	public void setTotalCnt(int totalCnt) {
		this.totalCnt = totalCnt;
	}
	public String getTimeCountForCompute() {
		return timeCountForCompute;
	}
	public void setTimeCountForCompute(String timeCountForCompute) {
		this.timeCountForCompute = timeCountForCompute;
	}
	public int getLuckCode() {
		return luckCode;
	}
	public void setLuckCode(int luckCode) {
		this.luckCode = luckCode;
	}
	public int getTotalTimes() {
		return totalTimes;
	}
	public void setTotalTimes(int totalTimes) {
		this.totalTimes = totalTimes;
	}
	public ArrayList<Person> getTimesList() {
		return timesList;
	}
	public void setTimesList(ArrayList<Person> timesList) {
		this.timesList = timesList;
	}
	
}
