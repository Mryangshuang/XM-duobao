package com.bwf.duobao.instance.goodsdetails;

public class LuckUser {
	
	/**产品信息界面  未揭晓  待揭晓   已揭晓 三种状态**/
	private int uid;
	private String nickname;
	private String IPAddress;
	private String IP;
	private String avatar;
	private int duobaoTimes;
	
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getIPAddress() {
		return IPAddress;
	}
	public void setIPAddress(String iPAddress) {
		IPAddress = iPAddress;
	}
	public String getIP() {
		return IP;
	}
	public void setIP(String iP) {
		IP = iP;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public int getDuobaoTimes() {
		return duobaoTimes;
	}
	public void setDuobaoTimes(int duobaoTimes) {
		this.duobaoTimes = duobaoTimes;
	}
	
}
