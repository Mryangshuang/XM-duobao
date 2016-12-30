package com.bwf.duobao.ui.activity;

import java.util.ArrayList;

import com.bwf.duobao.R;
import com.bwf.duobao.adapter.ListView_Adapter_wangqijiexiao;
import com.bwf.duobao.instance.goodsdetails.Gooditem;
import com.bwf.duobao.instance.goodsdetails.LuckInfo;
import com.bwf.duobao.utils.MyHttpUtils;
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class WangqijiexiaoActivity extends Activity{
	private HttpUtils mhttputils;
	@ViewInject(R.id.lv1)
	private ListView lv;
	
	@OnClick({R.id.backBtn,R.id.refresh,R.id.ll1,R.id.tv2,R.id.ll3})
	public void onclick(View view) {
		switch (view.getId()) {
		case R.id.backBtn:
			onBackPressed();
			break;
		case R.id.refresh:
			initDatas();
			break;
//			跳转到任务详情
		case R.id.ll3:
			Toast.makeText(WangqijiexiaoActivity.this, "我是骚猪", 0).show();
			break;
		}
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_wangqijiexiao);
		ViewUtils.inject(this);
		initDatas();
	}
	/**
	 * 初始化数据
	 */
	private void initDatas() {
		mhttputils=MyHttpUtils.getMyHttpUtils();
		String url="http://123.56.145.151:8080/Duobao-XM/winhistory?gid=102";
		mhttputils.send(HttpMethod.GET, url, new RequestCallBack<String>() {
			@Override
			public void onFailure(HttpException arg0, String arg1) {
				Toast.makeText(WangqijiexiaoActivity.this, "请求数据失败~~", 0).show();
			}

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				String result = arg0.result;
				Gooditem gooditem = new Gson().fromJson(result, Gooditem.class);
				initViews(gooditem);
			}
		});
	}
	private ArrayList<LuckInfo> list;
	/**
	 * 初始话控件
	 */
	private void initViews(Gooditem gooditem) {
		list = gooditem.getResult().getList();
		ListAdapter adapter=new ListView_Adapter_wangqijiexiao(list,WangqijiexiaoActivity.this);
		lv.setAdapter(adapter);
		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				Intent intent=new Intent(WangqijiexiaoActivity.this,DetailMessageActivity.class);
				String period = list.get((int)arg3).getPeriod();
				Long Period=Long.valueOf(period);
				intent.putExtra("period", Period);
				intent.putExtra("tag", 1);
				Log.d("发送:period：",period);
				Log.d("发送:tag：",1+"");
				startActivity(intent);
			}
		});
	}
}
