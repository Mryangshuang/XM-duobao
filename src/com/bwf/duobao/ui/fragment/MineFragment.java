package com.bwf.duobao.ui.fragment;

import java.util.ArrayList;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.bwf.duobao.R;
import com.bwf.duobao.application.MyApp;
import com.bwf.duobao.entity.ResponseUserinfo;
import com.bwf.duobao.entity.ResponseUserinfo.Userinfo;
import com.bwf.duobao.entity.ResponseUserinfo2;
import com.bwf.duobao.instance.phoneuser.Phone_useable;
import com.bwf.duobao.ui.activity.MainActivity;
import com.bwf.duobao.ui.activity.SMS_Activity;
import com.bwf.duobao.utils.AppSharedPreference;
import com.bwf.duobao.utils.MD5;
import com.bwf.duobao.utils.MyHttpUtils;
import com.bwf.duobao.utils.UserInfoHandler;
import com.google.gson.Gson;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.utils.Log;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MineFragment extends Fragment {
	private UMShareAPI mShareAPI;
	private HttpUtils mHttpUtils;
	private BitmapUtils mBitmapUtils;
	private View view;

	@ViewInject(R.id.layout_unlogin)
	private View mUnloginLayout;
	@ViewInject(R.id.layout_logined)
	private View mLoginedLayout;
	/** 当已经登入成功后的效果 设置 **/
	private Userinfo userinfo;
	@ViewInject(R.id.img_avatar)
	private ImageView mAvatarImg;

	@ViewInject(R.id.tv_nickname)
	private TextView mNicknameTv;

	@ViewInject(R.id.tv_province)
	private TextView provinceTv;

	@ViewInject(R.id.tv_city)
	private TextView cityTv;

	@ViewInject(R.id.tv_uid)
	private TextView mUidTv;

	@ViewInject(R.id.tv_token)
	private TextView tokenTv;

	@ViewInject(R.id.tv_loginCancel)
	private TextView mLoginCancelTv;

	@ViewInject(R.id.et_phone)
	private EditText et_phone;
	@ViewInject(R.id.et_pwd)
	private EditText et_pwd;

	@OnClick({ R.id.tv_login_weixin, R.id.tv_login_sina, R.id.tv_login_qq, R.id.tv_loginCancel, R.id.forget,
			R.id.phone_regist, R.id.phone_login })
	public void onclick(View view) {
		switch (view.getId()) {
		case R.id.tv_login_weixin:
			mShareAPI.doOauthVerify(getActivity(), SHARE_MEDIA.WEIXIN, arg2);
			break;
		case R.id.tv_login_sina:
			mShareAPI.doOauthVerify(getActivity(), SHARE_MEDIA.SINA, arg2);
			break;
		case R.id.tv_login_qq:
			mShareAPI.doOauthVerify(getActivity(), SHARE_MEDIA.QQ, arg2);
			break;
		// 注销
		case R.id.tv_loginCancel:
			UserInfoHandler.clearUserInfo(getActivity());
			mUnloginLayout.setVisibility(View.VISIBLE);
			mLoginedLayout.setVisibility(View.GONE);
			// uid token 的值设为null
			AppSharedPreference.putValue(getActivity(), "app", "token", "");
			AppSharedPreference.putValue(getActivity(), "app", "uid", "");
			// 清空本地清单信息 避免每次登入都会重复的加入清单
			MyApp.setList_periods(new ArrayList<Long>());
			MyApp.setList_times(new ArrayList<Integer>());
			break;
		case R.id.forget:
			Toast.makeText(getActivity(), "忘了干求藤", 0).show();
			break;
		case R.id.phone_regist:
			Intent intent = new Intent(getActivity(), SMS_Activity.class);
			startActivity(intent);
			break;
		case R.id.phone_login:
			String phone = et_phone.getText().toString();
			String pwd = et_pwd.getText().toString();
			doLogin_byphone(phone, pwd);
			break;
		}
	}

	/**
	 * 以手机的形式登录
	 */
	private void doLogin_byphone(String phone, String pwd) {
		/*
		 * ?phone=10086&password=12
		 */
		String url = "http://123.56.145.151:8080/Duobao-XM//login/phone";

		RequestParams params = new RequestParams("UTF-8");
		params.addBodyParameter("phone", phone);
		String hexdigest = MD5.encrypt(pwd);
		Log.d("pas", MD5.encrypt("123456"));
		params.addBodyParameter("password", hexdigest);

		mHttpUtils.send(HttpMethod.POST, url, params, new RequestCallBack<String>() {
			@SuppressLint("ShowToast")
			@Override
			public void onFailure(HttpException arg0, String arg1) {
				Toast.makeText(getActivity(), "登录出错，请稍后再试", 0).show();
			}

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				JSONObject object;
				try {
					object = new JSONObject(arg0.result);
					int code = object.getInt("code");
					if (code == 0) {
						// 登录成功 做下一步
						login_Phone(arg0);
					} else {
						// 登录出错 密码错误 没有此人
						Phone_useable fromJson2 = new Gson().fromJson(arg0.result, Phone_useable.class);
						Toast.makeText(getActivity(), fromJson2.getResult(), 0).show();
						return;
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * 授权
	 */
	private UMAuthListener arg2 = new UMAuthListener() {
		@Override
		public void onError(SHARE_MEDIA arg0, int arg1, Throwable arg2) {
			Toast.makeText(getActivity(), "授权 fail", 0).show();
		}

		@Override
		public void onComplete(SHARE_MEDIA arg0, int arg1, Map<String, String> arg2) {
			Toast.makeText(getActivity(), "授权 succeed", 0).show();
			mShareAPI.getPlatformInfo(getActivity(), arg0, getInfoListener);
		}

		@Override
		public void onCancel(SHARE_MEDIA arg0, int arg1) {
			Toast.makeText(getActivity(), "授权 cancel", 0).show();
		}
	};
	/**
	 * 授权成功后 服务器返回一个data 包括姓名 年龄 地址
	 */
	UMAuthListener getInfoListener = new UMAuthListener() {

		@Override
		public void onError(SHARE_MEDIA arg0, int arg1, Throwable arg2) {

		}

		@Override
		public void onComplete(SHARE_MEDIA arg0, int arg1, Map<String, String> data) {
			Log.d("data", data.toString());
			/**
			 * {is_yellow_year_vip=0, vip=0, level=0, yellow_vip_level=0,
			 * is_yellow_vip=0, msg=，
			 * 
			 * province=重庆, gender=女, openid=63DCF23C9516839C202375A08773FB82,
			 * screen_name=最初的记忆,
			 * profile_image_url=http://q.qlogo.cn/qqapp/1105449365/
			 * 63DCF23C9516839C202 375A08773FB82/100, city=开县}
			 */
			userinfo = new Userinfo();
			userinfo.openid = data.get("openid");
			userinfo.province = data.get("province");
			userinfo.sex = data.get("gender");
			userinfo.nickname = data.get("screen_name");
			userinfo.avatar = data.get("profile_image_url");
			userinfo.city = data.get("city");

			int userType = 0;
			if (arg0 == SHARE_MEDIA.WEIXIN) {
				userType = 1;
			} else if (arg0 == SHARE_MEDIA.QQ) {
				userType = 2;
			} else if (arg0 == SHARE_MEDIA.SINA) {
				userType = 3;
			}
			/**
			 * 1、向通讯平台请求链接 链接成功后 会返回一个数据 data 包含 头像 姓名 地址等信息
			 * 
			 * 2、再利用data数据来访问自己的服务器 服务器会给返回一个token 作为你的唯一标志 买东西等操作
			 */
			String url = "http://123.56.145.151:8080/Duobao-XM/platform-login";
			RequestParams params = new RequestParams("UTF-8");
			params.addBodyParameter("openid", userinfo.openid);
			params.addBodyParameter("nickname", userinfo.nickname);
			params.addBodyParameter("sex", userinfo.sex);
			params.addBodyParameter("province", userinfo.province);
			params.addBodyParameter("city", userinfo.city);
			params.addBodyParameter("headImg", userinfo.avatar);
			params.addBodyParameter("userType", userType + "");

			mHttpUtils.send(HttpMethod.POST, url, params, callBack);
		}

		@Override
		public void onCancel(SHARE_MEDIA arg0, int arg1) {

		}
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_mine, null);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		ViewUtils.inject(this, getView());
		MainActivity activity = (MainActivity) getActivity();
		mShareAPI = activity.mShareAPI;
		mHttpUtils = MyHttpUtils.getMyHttpUtils();
		mBitmapUtils = new BitmapUtils(getActivity());
		initViews();
	}

	private void initViews() {
		if (isLogin()) {
			mUnloginLayout.setVisibility(View.GONE);
			mLoginedLayout.setVisibility(View.VISIBLE);
			setLoginedViewData(UserInfoHandler.getUserInfo(getActivity()));
		} else {
			mUnloginLayout.setVisibility(View.VISIBLE);
			mLoginedLayout.setVisibility(View.GONE);
		}
	}

	/**
	 * 获取用户是否已经登入
	 * 
	 * @return
	 */
	private boolean isLogin() {
		Userinfo userInfo = UserInfoHandler.getUserInfo(getActivity());
		return userInfo != null;
	}

	RequestCallBack<String> callBack = new RequestCallBack<String>() {
		@Override
		public void onFailure(HttpException arg0, String arg1) {
		}

		@Override
		public void onSuccess(ResponseInfo<String> arg0) {
			login_QQ(arg0);
		}
	};

	/**
	 * QQ登录
	 * 
	 * @param arg0
	 */
	private void login_QQ(ResponseInfo<String> arg0) {
		ResponseUserinfo fromJson = new Gson().fromJson(arg0.result, ResponseUserinfo.class);
		Userinfo result = fromJson.getResult();
		// 确认token 存储在sharedpreferences 中
		AppSharedPreference.putValue(getActivity(), "app", "token", result.getToken());
		AppSharedPreference.putValue(getActivity(), "app", "uid", result.getUid());
		Log.d("存入token", result.getToken());
		Log.d("存入uid", result.getUid());
		poatformLoginSuccess(result);
	}

	/**
	 * Phone 登录
	 * 
	 * @param arg0
	 */
	private void login_Phone(ResponseInfo<String> arg0) {
		ResponseUserinfo2 fromJson = new Gson().fromJson(arg0.result, ResponseUserinfo2.class);
		com.bwf.duobao.entity.ResponseUserinfo2.Userinfo result = fromJson.getResult();
		// 确认token 存储在sharedpreferences 中
		AppSharedPreference.putValue(getActivity(), "app", "token", result.getToken());
		AppSharedPreference.putValue(getActivity(), "app", "uid", result.getUid() + "");
		Log.d("存入token", result.getToken());
		Log.d("存入uid", result.getUid() + "");

		/*
		 * 后面 uid 改成String 就可以改回来了
		 * 
		 */
		// 将用户信息 存入 sharedpreference
		SharedPreferences preferences = getActivity().getSharedPreferences("app", Context.MODE_PRIVATE);
		preferences.edit().putString("user_info", new Gson().toJson(userinfo)).putBoolean("is_login", true).commit();

		mUnloginLayout.setVisibility(View.GONE);
		mLoginedLayout.setVisibility(View.VISIBLE);
		// 清空编辑框的内容
		et_phone.setText("");
		et_pwd.setText("");

		// 将 保存在App中的清单 提交系统
		MyApp.addToList(getActivity(), MyApp.getList_periods(), MyApp.getList_times());

		ImageView img = (ImageView) view.findViewById(R.id.anim);
		AnimationDrawable background = (AnimationDrawable) img.getBackground();
		background.start();

		mBitmapUtils.display(mAvatarImg, result.getAvatar());
		mNicknameTv.setText(result.getNickname());
		mUidTv.setText("uid:" + result.getUid());
		tokenTv.setText("token:" + result.getToken());
		provinceTv.setText(result.getProvince());
		cityTv.setText(result.getCity());
	}

	/**
	 * 将登入成功的信息 存起来 并跳转页面
	 * 
	 * @param result
	 */
	protected void poatformLoginSuccess(Userinfo result) {
		// 将用户信息 存入 sharedpreference
		UserInfoHandler.saveUserInfo(getActivity(), result);

		mUnloginLayout.setVisibility(View.GONE);
		mLoginedLayout.setVisibility(View.VISIBLE);
		// 将 保存在App中的清单 提交系统
		MyApp.addToList(getActivity(), MyApp.getList_periods(), MyApp.getList_times());
		setLoginedViewData(result);
	}

	/**
	 * 设置头像 名称
	 */
	private void setLoginedViewData(Userinfo userinfo2) {
		// 帧动画启动
		ImageView img = (ImageView) view.findViewById(R.id.anim);
		AnimationDrawable background = (AnimationDrawable) img.getBackground();
		background.start();

		mBitmapUtils.display(mAvatarImg, userinfo2.getAvatar());
		mNicknameTv.setText(userinfo2.getNickname());
		mUidTv.setText("uid:" + userinfo2.getUid());
		tokenTv.setText("token:" + userinfo2.getToken());
		provinceTv.setText(userinfo2.getProvince());
		cityTv.setText(userinfo2.getCity());
	}
}
