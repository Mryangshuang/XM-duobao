package com.bwf.duobao.utils;

import com.lidroid.xutils.HttpUtils;

public class MyHttpUtils {
	/**
	 * 通过方法的到httputils  超时和缓存获取时间已经设置好了
	 * @return
	 */
	public static HttpUtils getMyHttpUtils(){
		HttpUtils myHttpUtils=new HttpUtils();
		myHttpUtils.configCurrentHttpCacheExpiry(0);
		myHttpUtils.configSoTimeout(4000);
		myHttpUtils.configTimeout(4000);
		return myHttpUtils;
	}
}
