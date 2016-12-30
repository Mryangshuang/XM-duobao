package com.bwf.duobao.ui.activity;

import java.util.ArrayList;

import com.bwf.duobao.R;
import com.bwf.duobao.adapter.ListView_Adapter_PictureDetails;
import com.bwf.duobao.instance.details.DeatilItems;
import com.bwf.duobao.instance.details.Items;
import com.bwf.duobao.utils.MyHttpUtils;
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class PictureDetailsActivity extends Activity {
	@ViewInject(R.id.pd_lv)
	private ListView pd_lv;
	private int goodsid;
	private TextView pd_tv;
	private ImageView img;
	private HttpUtils mHttputils;
	private LayoutInflater inflater;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_picturedetails);
		Intent intent=getIntent();
		goodsid=intent.getIntExtra("goodsid", 11);
		Log.d("接收goodsid:",goodsid+"");
		ViewUtils.inject(this);
		initDatas();
		inflater = LayoutInflater.from(this);
		img = (ImageView) findViewById(R.id.backBtn);
		img.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				onBackPressed();
			}
		});

	}

	/**
	 * 初始化数据  网络获取八张图片的信息 并进行显示处理
	 */
	private void initDatas() {
		mHttputils = MyHttpUtils.getMyHttpUtils();
		// 网上获取数据 为listview 和 footerview 加载数据做准备
		String url = "http://123.56.145.151:8080/Duobao-XM/goods-detail?goodsId="+goodsid;
		
		mHttputils.send(HttpMethod.GET, url, new RequestCallBack<String>() {
			@Override
			public void onFailure(HttpException arg0, String arg1) {
				Toast.makeText(PictureDetailsActivity.this, "请求超时", 0).show();
			}

			@Override
			public void  onSuccess(ResponseInfo<String> arg0) {
				String content = arg0.result;
				DeatilItems items = new Gson().fromJson(content, DeatilItems.class);
				Items item = items.getResult();
				setadapter(item);
			}
		});
	}

	/**
	 * 得到网络数据 为listview 和 textview 加载数据
	 * @param item
	 */
	private void setadapter(Items item) {
		View footerview = inflater.inflate(R.layout.activity_picturedetailsfootview, null);
		pd_lv.addFooterView(footerview);
		// 扎到脚部布局 并设置文字
		pd_tv = (TextView) footerview.findViewById(R.id.pd_tv);
//		// 为文本设置文字
		pd_tv.setText(item.getDeclaration());

		ArrayList<String> list = item.getList();
		ListView_Adapter_PictureDetails adapter = new ListView_Adapter_PictureDetails(list, PictureDetailsActivity.this);
		pd_lv.setAdapter(adapter);
	}
}
