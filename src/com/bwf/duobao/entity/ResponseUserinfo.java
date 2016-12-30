package com.bwf.duobao.entity;

public class ResponseUserinfo {

	public int code;
	public Userinfo result;
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public Userinfo getResult() {
		return result;
	}
	public void setResult(Userinfo result) {
		this.result = result;
	}
	public static class Userinfo {
		public String uid;
		public String token;
		public String area;
		
		public String openid;
		public String nickname;
		public String province;
		public String city;
		public String sex;
		public String avatar;
		
		public int userType;
		public long loginDateTime;
		
		public String getUid() {
			return uid;
		}

		public void setUid(String uid) {
			this.uid = uid;
		}

		public String getOpenid() {
			return openid;
		}

		public void setOpenid(String openid) {
			this.openid = openid;
		}

		public String getToken() {
			return token;
		}

		public void setToken(String token) {
			this.token = token;
		}

		public String getNickname() {
			return nickname;
		}

		public void setNickname(String nickname) {
			this.nickname = nickname;
		}

		public String getProvince() {
			return province;
		}

		public void setProvince(String province) {
			this.province = province;
		}

		public String getCity() {
			return city;
		}

		public void setCity(String city) {
			this.city = city;
		}

		public String getArea() {
			return area;
		}

		public void setArea(String area) {
			this.area = area;
		}

		public String getSex() {
			return sex;
		}

		public void setSex(String sex) {
			this.sex = sex;
		}

		public String getAvatar() {
			return avatar;
		}

		public void setAvatar(String avatar) {
			this.avatar = avatar;
		}

		public int getUserType() {
			return userType;
		}

		public void setUserType(int userType) {
			this.userType = userType;
		}

		public long getLoginDateTime() {
			return loginDateTime;
		}

		public void setLoginDateTime(long loginDateTime) {
			this.loginDateTime = loginDateTime;
		}

		@Override
		public String toString() {
			return "Userinfo [uid=" + uid + ", openid=" + openid + ", token=" + token + ", nickname=" + nickname
					+ ", province=" + province + ", city=" + city + ", area=" + area + ", sex=" + sex + ", avatar="
					+ avatar + ", userType=" + userType + ", loginDateTime=" + loginDateTime + "]";
		}
		
		
		
	}

}
