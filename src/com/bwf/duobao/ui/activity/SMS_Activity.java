package com.bwf.duobao.ui.activity;

import com.bwf.duobao.R;
import com.bwf.duobao.entity.Obj;
import com.bwf.duobao.utils.MD5;
import com.bwf.duobao.utils.MyHttpUtils;
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

public class SMS_Activity extends Activity {
	private HttpUtils mHttpUtils;
	private String phone;

	@ViewInject(R.id.et_1)
	private EditText et1;
	@ViewInject(R.id.et_2)
	private EditText et2;
	

	@ViewInject(R.id.ll1)
	private LinearLayout ll1;
	@ViewInject(R.id.ll2)
	private LinearLayout ll2;
	@ViewInject(R.id.rl)
	private RelativeLayout rl;

	@ViewInject(R.id.nickname)
	private EditText nickName;
	@ViewInject(R.id.pwd)
	private EditText pwd;
	@ViewInject(R.id.pwd_1)
	private EditText pwd_1;

	@ViewInject(R.id.btn)
	private Button btn;
	
	
	@OnClick({ R.id.enter_1, R.id.enter_2, R.id.backBtn, R.id.btn })
	private void onclick(View view) {
		switch (view.getId()) {
		case R.id.backBtn:
			onBackPressed();
			break;
		case R.id.enter_1:
			StartCheck();
			break;
		case R.id.enter_2:
//			返回 验证码
			SMSSDK.submitVerificationCode("86", phone, et2.getText().toString());
			break;
		}
	}

	/**
	 * 服务器上注册
	 */
	private void regist() {
		/****
		 * ?phone=110&nickname=meng
		 */
		String url = "http://123.56.145.151:8080/Duobao-XM/regist/phone";
		RequestParams params = new RequestParams("UTF-8");

		params.addBodyParameter("phone", phone);
		params.addBodyParameter("nickname", nickName.getText().toString());
		params.addBodyParameter("password", MD5.encrypt(pwd.getText().toString()));
		
		mHttpUtils.send(HttpMethod.POST, url, params, new RequestCallBack<String>() {

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				Toast.makeText(SMS_Activity.this, "注册失败", 0).show();
			}

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				Toast.makeText(SMS_Activity.this, "注册成功,跳转中....", Toast.LENGTH_LONG).show();
				try {
					Thread.sleep(2000);
					onBackPressed();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * 检测电话号码是否可以注册 可注册就显示验证号码 如果不行就不行
	 */
	private void StartCheck() {
		phone = et1.getText().toString();
		String url = "http://123.56.145.151:8080/Duobao-XM/regist/check-phone?phone=" + phone;
		mHttpUtils.send(HttpMethod.GET, url, new RequestCallBack<String>() {
			@Override
			public void onFailure(HttpException arg0, String arg1) {
				Toast.makeText(SMS_Activity.this, "请求数据失败，请稍后再试", 0).show();
				return;
			}

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				Obj fromJson = new Gson().fromJson(arg0.result, Obj.class);
//				手机判断是否可以用
				if (fromJson.code == 0) {
					// 请求验证码
					SMSSDK.getVerificationCode("86", phone);
				} else {
					Toast.makeText(SMS_Activity.this, "号码已被注册，请直接登录", 0).show();
					return;
				}
			}
		});
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activtity_sms);
		ViewUtils.inject(this);
		mHttpUtils = MyHttpUtils.getMyHttpUtils();
		// 短信方面的
		SMSSDK.initSDK(SMS_Activity.this, "15363f4c9c80e", "63628648cb25cab49938bcedac996e65");
		init();
	}

	private void init() {

		// 短信方面的 注册
		EventHandler eh = new EventHandler() {
			@Override
			public void afterEvent(int event, int result, final Object data) {
				// 回调完成
				if (result == SMSSDK.RESULT_COMPLETE) {
					// 提交验证码成功
					if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								showRegist();
							}
						});
						// 获取验证码成功
					} else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								showNext();
							}
						});
					}
				} else {
					((Throwable) data).printStackTrace();
					runOnUiThread(new  Runnable() {
						public void run() {
							String string = data.toString();
							int indexOf = string.indexOf("il");
							int lastIndexOf = string.lastIndexOf("\"");
							Toast.makeText(SMS_Activity.this, data.toString().substring(indexOf+5, lastIndexOf), 0).show();
						}
					});
				}
			}

			
		};
		SMSSDK.registerEventHandler(eh);
	}

	/**
	 * 执行下一步操作
	 */
	private void showNext() {
		ll1.setVisibility(View.INVISIBLE);
		ll2.setVisibility(View.VISIBLE);
	}
	/**
	 * 调到注册界面
	 */
	private void showRegist() {
		ll2.setVisibility(View.INVISIBLE);
		rl.setVisibility(View.VISIBLE);
		pwd_1.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
			}
			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
			}
			@Override
			public void afterTextChanged(Editable arg0) {
				if(pwd.getText().toString().equals(pwd_1.getText().toString())){
//					一致的情况下  注册
					doClick("same");
				}else{
//					两次输入不一致  弹出toast
					doClick("");
				}
			}
			private void doClick(final String str) {
				btn.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						if(str.equals("same")){
							regist();
						}else{
							Toast.makeText(SMS_Activity.this, "两次密码不一致", 0).show();
						}
					}
				});
			}
		});
	}

	/**
	 * 退出是注销监听
	 */
	@Override
	protected void onDestroy() {
		super.onDestroy();
		SMSSDK.unregisterAllEventHandler();
	}
}
