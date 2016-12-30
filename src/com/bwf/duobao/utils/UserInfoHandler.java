package com.bwf.duobao.utils;

import com.bwf.duobao.entity.ResponseUserinfo.Userinfo;
import com.google.gson.Gson;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

public class UserInfoHandler {
/**
 * 	将获取的用户信息提交到内部存储
 * @param context
 * @param userinfo
 */
	public static void saveUserInfo(Context context,Userinfo userinfo){
		SharedPreferences preferences=context.getSharedPreferences("app", context.MODE_PRIVATE);
		preferences.edit().putString("user_info", new Gson().toJson(userinfo)).putBoolean("is_login", true).commit();
	}
	/**
	 * 取出之前存的用户信息
	 * @param context
	 * @return
	 */
	public static Userinfo getUserInfo(Context context){
		Userinfo userinfo=null;
		SharedPreferences preferences=context.getSharedPreferences("app", context.MODE_PRIVATE);
		String string = preferences.getString("user_info", null);
		if(!TextUtils.isEmpty(string)){
			userinfo=new Gson().fromJson(string,Userinfo.class);
		}
		return userinfo;
	}
	
	/**
	 * 清空之前保存的用户信息
	 */
	public static void clearUserInfo(Context context){
		SharedPreferences preferences=context.getSharedPreferences("app", context.MODE_PRIVATE);
		preferences.edit().putString("user_info", null).putBoolean("is_login", false).commit();
	}
	
	/**
	 * 获取用户是否登入
	 */
	public static boolean isLogin(Context context){
		SharedPreferences preferences=context.getSharedPreferences("app", context.MODE_PRIVATE);
//		默认未登入
		return preferences.getBoolean("is_login", false);
	}
}
