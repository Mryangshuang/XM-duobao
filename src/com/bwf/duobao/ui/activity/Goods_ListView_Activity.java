package com.bwf.duobao.ui.activity;

import java.util.ArrayList;

import com.bwf.duobao.R;
import com.bwf.duobao.adapter.ListView_Adapter_Goods;
import com.bwf.duobao.application.MyApp;
import com.bwf.duobao.instance.shiyuanduobao.Good;
import com.bwf.duobao.instance.shiyuanduobao.ShiyuanduobaoGoodsItem;
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
import com.lidroid.xutils.view.annotation.event.OnItemClick;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Goods_ListView_Activity extends Activity {
	private int categoryId;
	private String categoryName;
	private HttpUtils mHttpUtils;
	private ListView_Adapter_Goods adapter;
//	十元专区
	@ViewInject(R.id.textview)
	private TextView textview;
	
	@ViewInject(R.id.listview)
	private ListView listview;
	@ViewInject(R.id.tv)
	private TextView tv;
	private ArrayList<Good> list;
	@ViewInject(R.id.backBtn1)
	private ImageView img;
	@OnClick({R.id.backBtn1,R.id.add_all_tolist})
	public void onclick(View view){
		switch (view.getId()) {
//	点击调回主界面Fragment
		case R.id.backBtn1:
			finish();
			break;
//全部加入清单
		case R.id.add_all_tolist:
			ArrayList<Long> list_periods=new ArrayList<Long>();
			ArrayList<Integer> list_times=new ArrayList<Integer>();
			if(list==null){
				Toast.makeText(Goods_ListView_Activity.this, "加载数据中....稍后再试", Toast.LENGTH_LONG).show();
				return;
			}else if(list.size()==0){
				Toast.makeText(Goods_ListView_Activity.this, "无清单", Toast.LENGTH_LONG).show();
				return;
			}else{
				for (int i = 0; i < list.size(); i++) {
					list_periods.add(list.get(i).getPeriod());
					list_times.add(1);
				}
				adapter.startmove();
				MyApp.addToList(Goods_ListView_Activity.this, list_periods, list_times);
			}
			
			
			break;
		}
	}
//	点击跳转到详情界面   
	@OnItemClick(R.id.listview)
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Intent intent=new Intent(Goods_ListView_Activity.this,DetailMessageActivity.class);
		if(arg3>=0){
			long period = list.get((int)arg3).getPeriod();
			intent.putExtra("period", period);
			Log.d("发送：period:",period+"");
			startActivity(intent);
		}
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_syzq);
		categoryId=getIntent().getIntExtra("categoryId", 1);
		categoryName=getIntent().getStringExtra("categoryName");
		Log.d("接收categoryId：",categoryId+"");
		Log.d("接收categoryName：",categoryName);
		ViewUtils.inject(this);
		initDatas();
		textview.setText(categoryName);
	}

	// 获得数据 进行配置adapter
	private void initDatas() {
		mHttpUtils = MyHttpUtils.getMyHttpUtils();
		String url = "http://123.56.145.151:8080/Duobao-XM/list?category="+categoryId;
		mHttpUtils.send(HttpMethod.GET, url, new RequestCallBack<String>() {
			@Override
			public void onFailure(HttpException arg0, String arg1) {
				Toast.makeText(Goods_ListView_Activity.this, "请求数据失败", Toast.LENGTH_LONG).show();
			}

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				String content = arg0.result;
				addadapter(content);
			}
		});
	}

	/**
	 * 加载数据
	 * 
	 * @param content
	 */
	private void addadapter(String content) {
		ShiyuanduobaoGoodsItem shiyuanduobaoGoodsItem = new Gson().fromJson(content, ShiyuanduobaoGoodsItem.class);
		list = shiyuanduobaoGoodsItem.getResult().getList();
		tv.setText("共"+list.size()+"件商品");
		adapter = new ListView_Adapter_Goods(list, Goods_ListView_Activity.this);
		listview.setAdapter(adapter);
	}

}
