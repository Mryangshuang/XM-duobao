package com.bwf.duobao.ui.activity;

import java.util.ArrayList;

import com.bwf.duobao.R;
import com.bwf.duobao.adapter.ListView_Adapter_Fenlei;
import com.bwf.duobao.instance.fenlei.FenleiGoodsItem;
import com.bwf.duobao.instance.fenlei.Goods;
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
import com.umeng.socialize.utils.Log;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

public class CategoryActivity extends Activity {
	private ArrayList<Goods> list;

	@ViewInject(R.id.listview)
	private ListView listview;

	@ViewInject(R.id.backBtn1)
	private ImageView backbtn;

	// item设置点击事件 跳转
	@OnItemClick(R.id.listview)
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

		Intent intent = new Intent(this, Goods_ListView_Activity.class);
		if (arg3 >= 0) {
			int categoryId = list.get((int) arg3).getCategoryId();
			String categoryName = list.get((int) arg3).getCategoryName();
			intent.putExtra("categoryId", categoryId);
			intent.putExtra("categoryName", categoryName);
			Log.d("发送categoryId：",categoryId+"");
			Log.d("发送categoryName：",categoryName+"");
			startActivity(intent);
		}
	}

	// 点击跳转到搜索的界面
	@OnClick({ R.id.search, R.id.textview1 })
	public void onClick(View view) {
		switch (view.getId()) {
		// 搜索图标点击事件
		case R.id.search:
			startActivity(new Intent(CategoryActivity.this, SearchActivity.class));
			break;
		// 跳转全部商品
		case R.id.textview1:
			Intent intent = new Intent(CategoryActivity.this, Goods_ListView_Activity.class);
			intent.putExtra("categoryId", 0);
			intent.putExtra("categoryName", "全部商品");
			startActivity(intent);
			break;
		}
	}

	// 点击返回主界面的 Fragment
	@OnClick(R.id.backBtn1)
	public void onClick1(View view) {
		onBackPressed();
	}

	private HttpUtils mHttpUtils;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fenlei);
		backbtn = (ImageView) findViewById(R.id.backBtn1);
		ViewUtils.inject(this);
//		利用方法得到httputils
		mHttpUtils=MyHttpUtils.getMyHttpUtils();
		mHttpUtils = new HttpUtils();
		loadinfomation();
	}

	// 为listView加载数据
	private void loadinfomation() {
		String url = "http://123.56.145.151:8080/Duobao-XM/category";
		mHttpUtils.send(HttpMethod.GET, url, new RequestCallBack<String>() {
			@Override
			public void onFailure(HttpException arg0, String arg1) {
				Toast.makeText(CategoryActivity.this, "请求数据失败", 1).show();
			}

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				String result = arg0.result;
				SetAdapter(result);
			}

			// 为 listview 设置布局和数据
			private void SetAdapter(String result) {
				FenleiGoodsItem fenleiGoodsItem = new Gson().fromJson(result, FenleiGoodsItem.class);
				list = fenleiGoodsItem.getResult().getList();

				ListView_Adapter_Fenlei adapter = new ListView_Adapter_Fenlei(list, CategoryActivity.this);
				listview.setAdapter(adapter);
			}
		});

	}
}
