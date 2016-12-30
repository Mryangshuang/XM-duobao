package com.bwf.duobao.ui.activity;

import java.util.ArrayList;

import com.bwf.duobao.R;
import com.bwf.duobao.adapter.GridView_Adapter_Duobaohaoma;
import com.bwf.duobao.adapter.ListView_Adapter_Duobaoxiangqing;
import com.bwf.duobao.instance.ResponseRevealedPeriodInfo2.ResponseRevealedPeriodInfo2;
import com.bwf.duobao.instance.duobao_num.NumItems;
import com.bwf.duobao.instance.goodsdetails.Gooditem;
import com.bwf.duobao.instance.goodsdetails.LuckInfo;
import com.bwf.duobao.instance.goodsdetails.Result;
import com.bwf.duobao.utils.MyHttpUtils;
import com.bwf.duobao.utils.TimeFormat;
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.umeng.socialize.utils.Log;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings.SettingNotFoundException;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class DuobaoDetailActivity extends Activity {
	@OnClick({ R.id.backBtn })
	public void onclick(View view) {
		switch (view.getId()) {
		case R.id.backBtn:
			onBackPressed();
			break;
		}
	}

	@ViewInject(R.id.textview)
	private TextView titletextview;
	@ViewInject(R.id.tv1)
	private TextView nametv;
	@ViewInject(R.id.tv2)
	private TextView qihaotv;
	@ViewInject(R.id.tv3)
	private TextView timetv;
	@ViewInject(R.id.tv4)
	private TextView numtv;
	@ViewInject(R.id.luckimg)
	private ImageView luckimg;
	@ViewInject(R.id.totalnum)
	private TextView totalnum;

	@ViewInject(R.id.listview)
	private ListView listview;
	@ViewInject(R.id.gridview)
	private GridView gridview;

	@ViewInject(R.id.title)
	private LinearLayout titlelistview;

	private HttpUtils myHttputils;

	private Intent intent;
	private Long period;
	private String title, goodname;
	private int tag, rid, status, totalnums, luckcode,uid;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_duobaodetail);
		ViewUtils.inject(this);
		myHttputils = MyHttpUtils.getMyHttpUtils();
		intent = getIntent();
		// 为了识别是夺宝详情 0 还是夺宝号码 1
		tag = intent.getIntExtra("tag", -1);

		// 抬头 夺宝详情 夺宝号码
		title = intent.getStringExtra("title");
		titletextview.setText(title);
		// 设置产品的名称
		goodname = intent.getStringExtra("goodname");
		nametv.setText(goodname);
		
		
//		区分产品是处于什么状态   0正在卖   2已经开奖
		status = intent.getIntExtra("status", -1);

		// 以下两数据作为参数 用于得到 夺宝详情
		period = intent.getLongExtra("period", -1);
		qihaotv.setText("期号：" + period);
//		以period 为参数  ，所以要在period  数据之后
		setrevealedTime();
//		用于夺宝号码 接口参数
		rid = intent.getIntExtra("rid", -1);
		luckcode = 0;
		initviews();
	}

	/**
	 * 
	 */
	private void initviews() {
		
		// 夺宝详情
		if (tag == 0) {
			uid = intent.getIntExtra("uid", -1);
			Log.d("接收:tag", tag + "");
			Log.d("接收:title", title);
			Log.d("接收:period", period + "");
			Log.d("接收:uid", intent.getIntExtra("uid", -1) + "");
			Log.d("接收:goodname", intent.getStringExtra("goodname"));
			Log.d("接收:status2", intent.getIntExtra("status", -1) + "");
			Log.d("接收:luckCode", "附加" + intent.getIntExtra("luckCode", 0));

			// 正在夺宝
			if (status == 0) {
				
				
				// 已经开奖
			} else if (status == 2) {
				luckcode = intent.getIntExtra("luckCode", -1);
				numtv.setVisibility(View.VISIBLE);
				numtv.setText("本期的幸运号码： " + luckcode);
			}
			String url = "http://123.56.145.151:8080/Duobao-XM/duobao-detail?period=" + period + "&uid=" + uid;
			myHttputils.send(HttpMethod.GET, url, new RequestCallBack<String>() {

				@Override
				public void onFailure(HttpException arg0, String arg1) {
					Toast.makeText(DuobaoDetailActivity.this, "请求数据超时", 0).show();
				}

				@Override
				public void onSuccess(ResponseInfo<String> arg0) {
					String result = arg0.result;
					Gooditem fromJson = new Gson().fromJson(result, Gooditem.class);
					Result result2 = fromJson.getResult();
					setlistview_adapter(result2);
				}
			});

			// 夺宝号码
		} else if (tag == 1) {
			Log.d("接收：rid", rid+"");
			Log.d("接收：tag", tag+"");
			Log.d("接收：period", period+"");
			Log.d("接收：title",title);
			Log.d("接收：goodname", goodname);
			Log.d("接收：luckcode", luckcode+"");
			
		
			// 从listview 中传过来的luckcode 如果不为0 就显示出来 ，如果为0 就隐藏 因为最初 luckcode 赋值为0
			int luckcode = intent.getIntExtra("luckcode", 0);
			if ( luckcode!= 0) {
				numtv.setVisibility(View.VISIBLE);
				numtv.setText("本期的幸运号码： " + luckcode);
			} 
			String url = "http://123.56.145.151:8080/Duobao-XM/codes?rid=" + rid;
			myHttputils.send(HttpMethod.GET, url, new RequestCallBack<String>() {

				@Override
				public void onFailure(HttpException arg0, String arg1) {
					Toast.makeText(DuobaoDetailActivity.this, "请求数据失败", 0).show();
				}

				@Override
				public void onSuccess(ResponseInfo<String> arg0) {
					String result = arg0.result;
					NumItems fromJson = new Gson().fromJson(result, NumItems.class);
					com.bwf.duobao.instance.duobao_num.Result result2 = fromJson.getResult();
					setgridview_adapter(result2);
				}
			});
		}
	}
/**
 * 设置开奖时间  如果有就显示  如果没有  就不显示
 */
	private void setrevealedTime() {
		String url="http://123.56.145.151:8080/Duobao-XM/period-info?period="+period;
		myHttputils.send(HttpMethod.GET, url, new RequestCallBack<String>() {
			@Override
			public void onFailure(HttpException arg0, String arg1) {
				Toast.makeText(DuobaoDetailActivity.this, "无法设置开奖时间", 0).show();
			}
			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				String revealedTime = new Gson().fromJson(arg0.result, ResponseRevealedPeriodInfo2.class).getResult().getRevealedTime();
				if(revealedTime!=null){
					timetv.setVisibility(View.VISIBLE);
					timetv.setText("揭晓时间"+TimeFormat.timeformat(revealedTime, "yyyy-MM-dd HH:mm:ss.SSS"));
				}
			}
		});
	}

	/**
	 * 为gridview 设置适配器
	 * 
	 * @param fromJson
	 */
	protected void setgridview_adapter(com.bwf.duobao.instance.duobao_num.Result result2) {
		totalnums = result2.getTotalCnt();
		totalnum.setText("该用户已参与了" + totalnums + "次，以下是夺宝记录");
		ArrayList<Integer> list = result2.getList();
		GridView_Adapter_Duobaohaoma adapter = new GridView_Adapter_Duobaohaoma(list, DuobaoDetailActivity.this);
		gridview.setAdapter(adapter);
	}

	/**
	 * listview 设置适配器
	 * @param result2
	 */
	protected void setlistview_adapter(Result result2) {
		ArrayList<LuckInfo> list = result2.getList();
		for (int i = 0; i < list.size(); i++) {
			totalnums += list.get(i).getTimes();
		}
		totalnum.setText("该用户已参与了" + totalnums + "次，以下是夺宝记录");
		ListView_Adapter_Duobaoxiangqing adapter = new ListView_Adapter_Duobaoxiangqing(list, DuobaoDetailActivity.this,
				period, goodname, luckcode);
		listview.setAdapter(adapter);
	}
}
