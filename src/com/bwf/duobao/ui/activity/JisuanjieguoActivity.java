package com.bwf.duobao.ui.activity;

import java.util.ArrayList;

import com.bwf.duobao.R;
import com.bwf.duobao.adapter.JisuanjieguoAdapter;
import com.bwf.duobao.instance.jisuanjieguo.Person;
import com.bwf.duobao.instance.jisuanjieguo.PersonItems;
import com.bwf.duobao.instance.jisuanjieguo.Result;
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
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckedTextView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class JisuanjieguoActivity extends Activity {
	private HttpUtils mhttputils;
	private LayoutInflater inflater; 
	private long period;
	private ArrayList<Person> timesList;
	private ArrayList<Person> AllList;
	private JisuanjieguoAdapter adapter ;
	@ViewInject(R.id.linearlayout)
	private LinearLayout linearlayout;
	/**
	 * 下面两个设置找头标文字    Listview
	 */
	
	@ViewInject(R.id.textview)
	private TextView textview;
	@ViewInject(R.id.lv)
	private ListView lv;
	/**倒计时**/
	@ViewInject(R.id.headercounttv)
	private TextView headercounttv;
	/**幸运号码**/
	@ViewInject(R.id.footerlucknumtv)
	private TextView footerlucknumtv;
	/**点击按钮**/
	@ViewInject(R.id.checktv)
	private CheckedTextView checkbtn;
//	跳转到个人信息界面
	@OnItemClick(R.id.lv)
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		if(arg3>=0){
			Intent intent=new Intent(JisuanjieguoActivity.this,PersonalSpaceActivity.class);
			startActivity(intent);
		}
	}
	@OnClick({R.id.backBtn2,R.id.checktv,R.id.iv})
	private void onclick(View view) {
		switch (view.getId()) {
		case R.id.backBtn2:
			onBackPressed();
			break;
		case R.id.checktv:
			boolean checked = checkbtn.isChecked();
			if(!checked){
				linearlayout.setVisibility(View.VISIBLE);
				AllList.addAll(timesList);
				adapter.notifyDataSetChanged();
				checkbtn.toggle();
				checkbtn.setText("关闭↓");
				Log.d("ischecked", checkbtn.isChecked()+"");
			}else{
				linearlayout.setVisibility(View.GONE);
				AllList.clear();
				adapter.notifyDataSetChanged();
				checkbtn.toggle();
				checkbtn.setText("展开↑");
				Log.d("ischecked", checkbtn.isChecked()+"");
			}
			break;
		case R.id.iv:
			adapter = new JisuanjieguoAdapter(AllList, JisuanjieguoActivity.this);
			lv.setAdapter(adapter);
			break;
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_question);
		ViewUtils.inject(this);
		inflater= LayoutInflater.from(JisuanjieguoActivity.this);
		textview.setText("计算结果");
		period = getIntent().getLongExtra("period", 12345);
		Log.d("接收period：", "" + period);
		AllList=new ArrayList<Person>();
		initdatas();
	}

	/**
	 * 从网上拿数据
	 */
	private void initdatas() {
		mhttputils = MyHttpUtils.getMyHttpUtils();
		String url = "http://123.56.145.151:8080/Duobao-XM/period_compute_detail?period=" + period;
		mhttputils.send(HttpMethod.GET, url, new RequestCallBack<String>() {
			@Override
			public void onFailure(HttpException arg0, String arg1) {
				Toast.makeText(JisuanjieguoActivity.this, "请求数据超时", 0).show();
			}

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				String result = arg0.result;
				ShowMethod1(result);
			}
		});
	}
	/**
	 * 通过listview setadapeter的方式得到视图
	 */
	private void ShowMethod1(String result) {
		PersonItems fromJson = new Gson().fromJson(result, PersonItems.class);
		Result result2 = fromJson.getResult();
		
		View view = inflater.inflate(R.layout.activity_jisuanjieguo_header1, null);
		ViewUtils.inject(this,view);
		lv.addHeaderView(view);
		View view1 = inflater.inflate(R.layout.activity_jisuanjieguo_foot, null);
		ViewUtils.inject(this,view1);
		lv.addFooterView(view1);
		
		
		
//		获取到剩余时间 进行设置
		String timeCountForCompute = result2.getTimeCountForCompute();
		headercounttv.setText(timeCountForCompute);
//		幸运号码  设置
		int luckCode = result2.getLuckCode();
		
		footerlucknumtv.setText("幸运号码："+luckCode+"");
//		获得 数据集合
		timesList = fromJson.getResult().getTimesList();
//		先放入一个空的list  进去
		adapter = new JisuanjieguoAdapter(AllList, JisuanjieguoActivity.this);
		lv.setAdapter(adapter);	
	}
}
