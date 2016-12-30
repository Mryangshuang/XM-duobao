/** 
* @author  yangshuang 
* @date 创建时间：2016年6月25日 上午9:43:54 
*/
package com.bwf.duobao.application;

import java.util.ArrayList;

import com.bwf.duobao.utils.AppSharedPreference;
import com.bwf.duobao.utils.MyHttpUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.utils.Log;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

/**
 * @author Administrator
 *
 */
public class MyApp extends Application {
	public ArrayList<String> Search_datas;
	private static HttpUtils mHttpUtils;
	private static String token;
	
	private static ArrayList<Long> List_periods;
	private static ArrayList<Integer> List_times;
	
	
//	5df46f99a7a7db6d8974ab53c8bd98fc  token
//	10001733  uid
//	125.69.45.187   ID
	
	
	@Override
	public void onCreate() {
		super.onCreate();
		mHttpUtils = MyHttpUtils.getMyHttpUtils();
		// 搜索相关
		Search_datas = new ArrayList<String>();
		List_periods=new ArrayList<Long>();
		List_times=new ArrayList<Integer>();
		
		// 注册所有的友盟 有微信 QQ 新浪
		// 微信 appid appsecret
		PlatformConfig.setWeixin("wx4a91ba5684596d6a", "d474431cebe4c07e7afe012390b52911");
		// QQ和Qzone appid appkey 英语上面的appid appkey
		PlatformConfig.setQQZone("1105449365", "4KbDFp4ivoB5bXvq");
	}

	public static ArrayList<Long> getList_periods() {
		return List_periods;
	}

	public static void setList_periods(ArrayList<Long> list_periods) {
		List_periods = list_periods;
	}

	public static ArrayList<Integer> getList_times() {
		return List_times;
	}

	public static void setList_times(ArrayList<Integer> list_times) {
		List_times = list_times;
	}

	/**
	 * 将传入 period 的产品加入到清单当中 服务器 返回result
	 * @param goodsid
	 */
	public static void addToList(final Context context,ArrayList<Long> list_periods,ArrayList<Integer> list_times) {
		token = AppSharedPreference.getStringValue(context, "app", "token");
//		登出的时候   或者没有值的时候  取到的值为  ""  空字符串
		if(!token.equals("")){
			Log.d("取出token",token);
			String url ="http://123.56.145.151:8080/Duobao-XM/add_to_list?periods="+list_periods.toString().replace(" ", "")+"&times="+list_times.toString().replace(" ", "")+"&token="+token;
			Log.d("url",url);
			mHttpUtils.send(HttpMethod.GET, url, new RequestCallBack<String>() {
				@Override
				public void onFailure(HttpException arg0, String arg1) {
				}
				@Override
				public void onSuccess(ResponseInfo<String> arg0) {
					Log.d("result",arg0.result.toString());
					Toast.makeText(context, "添加成功", Toast.LENGTH_LONG).show();
				}
			});
		}else if(token.equals("")){
			Log.d("token",token);
			List_periods.addAll(list_periods);
			List_times.addAll(list_times);
			Toast.makeText(context, "添加成功，请尽快登录", Toast.LENGTH_LONG).show();
		}
	}
	
	/**
	 * 
	 * @param context
	 * @param good
	 */
		public static void update_webdata(Context context,ArrayList<Long> List_periods,ArrayList<Integer> List_times) {
			token = AppSharedPreference.getStringValue(context, "app", "token");
		
			String url="http://123.56.145.151:8080/Duobao-XM/update_list?periods="+List_periods.toString().replace(" ", "")+
					"&times="+List_times.toString().replace(" ", "")+"&token="+token;
			
			mHttpUtils.send(HttpMethod.GET, url, new RequestCallBack<String>() {
				@Override
				public void onFailure(HttpException arg0, String arg1) {
				}
				@Override
				public void onSuccess(ResponseInfo<String> arg0) {
					Log.d(arg0.toString());
				}
			});
		}
		/**
		 * 删除购物车物品
		 * @param context
		 * @param list_periods
		 */
		public static void delete_webdata(final Context context,ArrayList<Long> list_periods){
			Log.d("选中的periods:",list_periods.toString());
			token = AppSharedPreference.getStringValue(context, "app", "token");
			String url ="http://123.56.145.151:8080/Duobao-XM/delete_from_list?periods="+list_periods.toString().replace(" ", "")+"&token="+token;
			mHttpUtils.send(HttpMethod.GET, url, new RequestCallBack<String>() {
				
				@Override
				public void onFailure(HttpException arg0, String arg1) {
					Toast.makeText(context, "删除出错", Toast.LENGTH_LONG).show();
				}
				@Override
				public void onSuccess(ResponseInfo<String> arg0) {
					Toast.makeText(context, "已移除", Toast.LENGTH_LONG).show();
				}
			});
		}
		/**
		 * 结算
		 * @param context
		 * @param list_periods
		 * @param list_times
		 */
		public static void buy(final Context context,final ArrayList<Long> list_periods,ArrayList<Integer> list_times){
			token = AppSharedPreference.getStringValue(context, "app", "token");
			for (int i = 0; i < list_periods.size(); i++) {
				String url="http://123.56.145.151:8080/Duobao-XM/duobao?times="
						+ list_times.get(i)+"&period="+list_periods.get(i)+"&token="+token+"&IP=125.69.45.187&IPAddress=成都--博为峰";
				Log.d(token);
				mHttpUtils.send(HttpMethod.GET, url, new RequestCallBack<String>() {
					@Override
					public void onFailure(HttpException arg0, String arg1) {
						Toast.makeText(context, "购买出现问题", Toast.LENGTH_LONG).show();
					}
					@Override
					public void onSuccess(ResponseInfo<String> arg0) {
						Toast.makeText(context, "谢谢再次光临", Toast.LENGTH_LONG).show();
						delete_webdata(context, list_periods);
					}
				});
			}
		}
}
