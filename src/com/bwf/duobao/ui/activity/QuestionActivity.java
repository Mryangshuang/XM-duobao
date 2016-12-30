package com.bwf.duobao.ui.activity;

import java.util.ArrayList;
import java.util.Map;

import com.bwf.duobao.R;
import com.bwf.duobao.adapter.ListView_Adapter_Question;
import com.bwf.duobao.instance.question.Question;
import com.bwf.duobao.instance.question.ResponseItem;
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
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

public class QuestionActivity extends Activity implements OnItemClickListener {
	private ListView_Adapter_Question adapter;
	@ViewInject(R.id.lv)
	private ListView listview;
	@ViewInject(R.id.backBtn2)
	private ImageView img;	
//	点击调回主页
	@OnClick({R.id.backBtn2,R.id.iv})
	public void onclick(View view){
		switch (view.getId()) {
		case R.id.backBtn2:
			onBackPressed();
			break;
//		刷新数据
		case R.id.iv:
			initDatas();
			break;
		}
	}
	private ArrayList<Question> list;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_question);
		ViewUtils.inject(this);
		initDatas();
	}

	// 获得数据 进行配置adapter
	private void initDatas() {
		HttpUtils mHttpUtils = MyHttpUtils.getMyHttpUtils();

		String url = "http://123.56.145.151:8080/Duobao-XM/questions";
		mHttpUtils.send(HttpMethod.GET, url, new RequestCallBack<String>() {
			@Override
			public void onFailure(HttpException arg0, String arg1) {
				Toast.makeText(QuestionActivity.this, "请求数据失败", Toast.LENGTH_LONG).show();
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
		ResponseItem items = new Gson().fromJson(content, ResponseItem.class);
		list = items.getResult().getList();
		adapter = new ListView_Adapter_Question(list, QuestionActivity.this);
		listview.setAdapter(adapter);
		listview.setOnItemClickListener(this);
	}

//设置当前被按的item 值
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		adapter.setItem(arg2);
	}
}
