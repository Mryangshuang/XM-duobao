package com.bwf.duobao.ui.activity;

import java.util.ArrayList;

import com.bwf.duobao.R;
import com.bwf.duobao.adapter.ListView_Adapter_Goods;
import com.bwf.duobao.adapter.ListView_Adapter_Goodsearch;
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
import com.umeng.socialize.utils.Log;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

public class SearchActivity extends Activity {
	private MyApp app;
	private View view;
	private HttpUtils myHttpUtils;
	private ListView_Adapter_Goodsearch adapter;
	
	@ViewInject(R.id.addall_tolist)
	private TextView addall_tolist;

	@ViewInject(R.id.delbtn)
	private Button delbtn;
	@ViewInject(R.id.edittext)
	private EditText edittext;

	@ViewInject(R.id.ll)
	private LinearLayout ll;
	@ViewInject(R.id.goodslists)
	private LinearLayout ll1;

	@ViewInject(R.id.lv)
	private ListView listview;

	@ViewInject(R.id.TV1)
	private TextView tv1;
	// 物理键盘监听
	private OnEditorActionListener onEditorActionListener = new OnEditorActionListener() {
		@Override
		public boolean onEditorAction(TextView arg0, int arg1, KeyEvent arg2) {
			if (arg1 == EditorInfo.IME_ACTION_SEARCH) {
				// 将编辑框的东西拿出来 添加到集合里面
				String mes = edittext.getText().toString();
				clicksearch(mes);
			}
			return false;
		}
	};
	// 输入法按键监听
	private OnKeyListener onKeyListener = new OnKeyListener() {
		@Override
		public boolean onKey(View arg0, int arg1, KeyEvent arg2) {
			if (arg1 == KeyEvent.KEYCODE_ENTER && arg2.getAction() == KeyEvent.ACTION_UP) {
				// 将编辑框的东西拿出来 添加到集合里面
				String mes = edittext.getText().toString();
				clicksearch(mes);
			}
			return false;
		}
	};

	@OnClick({ R.id.backBtn1, R.id.tv_iPhone_1, R.id.tv_iPad_1, R.id.tv_hgsp_1, R.id.tv_coach_1, R.id.tv_gold_1,
			R.id.tv_danfan_1, R.id.tv_iPhone_2, R.id.tv_iPad_2, R.id.tv_hgsp_2, R.id.tv_coach_2, R.id.tv_gold_2,
			R.id.tv_danfan_2 })
	private void onclick(View view) {
		switch (view.getId()) {
		case R.id.backBtn1:
			finish();
			break;
			
		case R.id.tv_iPhone_1:
		case R.id.tv_iPhone_2:
			clicksearch("iPhone");
			break;
		case R.id.tv_iPad_1:
		case R.id.tv_iPad_2:
			clicksearch("iPad");
			break;
		case R.id.tv_hgsp_1:
		case R.id.tv_hgsp_2:
			clicksearch("海购商品");
			break;
		case R.id.tv_gold_1:
		case R.id.tv_gold_2:
			clicksearch("黄金");
			break;
		case R.id.tv_coach_1:
		case R.id.tv_coach_2:
			clicksearch("coach");
			break;
		case R.id.tv_danfan_1:
		case R.id.tv_danfan_2:
			clicksearch("单反");
			break;
		}
	}

	// 将东西添进集合
	private void clicksearch(String str) {
		if (!app.Search_datas.contains(str)) {
			app.Search_datas.add(0, str);
		} else {
			app.Search_datas.remove(str);
			app.Search_datas.add(0, str);
		}
		// 清空
		edittext.getText().clear();
		// 显示全部加入清单
		addall_tolist.setVisibility(View.VISIBLE);
		// 第一排搜索词
		ll.setVisibility(View.GONE);
		// 第二排搜索词
		ll1.setVisibility(View.GONE);
		setlist(str);
	}

	/**
	 * listview 设置adapter 呈现要搜索的东西
	 */
	private void setlist(final String str) {
		view.setVisibility(View.GONE);
		String url = "http://123.56.145.151:8080/Duobao-XM/search?key=" + str;
		myHttpUtils.send(HttpMethod.GET, url, new RequestCallBack<String>() {
			@Override
			public void onFailure(HttpException arg0, String arg1) {
			}

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				ShiyuanduobaoGoodsItem fromJson = new Gson().fromJson(arg0.result, ShiyuanduobaoGoodsItem.class);
				final ArrayList<Good> list = fromJson.getResult().getList();
				tv1.setText(str + " 共" + list.size() + "件商品");
				ListAdapter adapter = new ListView_Adapter_Goods(list, SearchActivity.this);
				listview.setAdapter(adapter);
//				跳转到相应商品明细
				listview.setOnItemClickListener(new OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
							Intent intent=new Intent(SearchActivity.this,DetailMessageActivity.class);
							long period = list.get((int)arg3).getPeriod();
							intent.putExtra("period", period);
							Log.d("发送：period",period+"");
							startActivity(intent);
					}
				});
			}
		});
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		app = (MyApp) getApplication();
		ViewUtils.inject(this);
//		
		view = LayoutInflater.from(this).inflate(R.layout.activity_search_headerview, null);
		
		myHttpUtils = MyHttpUtils.getMyHttpUtils();

		boolean isempty = app.Search_datas.isEmpty();
		Toast.makeText(SearchActivity.this, "数据有：" + app.Search_datas, 1).show();
		init(isempty);
		initViews();
	}

	/**
	 * 设置编辑框 和删除按钮的作用
	 */
	private void initViews() {
		edittext.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
			}

			@Override
			public void afterTextChanged(Editable arg0) {
				// 为edittext 设置监听 显示和隐藏删除控件
				if (TextUtils.isEmpty(edittext.getText().toString())) {
					delbtn.setVisibility(View.GONE);
				} else {
					delbtn.setVisibility(View.VISIBLE);
				}
			}
		});
		// 为删除控件 设置监听
		delbtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				edittext.getText().clear();
			}
		});
//		设置清空监听
		view.findViewById(R.id.set_empty).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				app.Search_datas.clear();
				adapter.notifyDataSetChanged();
				view.setVisibility(View.GONE);
				ll.setVisibility(View.GONE);
				ll1.setVisibility(View.VISIBLE);
				
			}
		});
		// 绑定输入法按键的监听
		edittext.setOnEditorActionListener(onEditorActionListener);
		// 绑定物理按键的监听
		edittext.setOnKeyListener(onKeyListener);
	}

	/**
	 * 判断app 数据库的信息是否为空 来做相应判断
	 * 
	 * @param isempty
	 */
	private void init(boolean isempty) {
		if (isempty) {
			ll.setVisibility(View.GONE);
		} else {
			// 如果有数据了 进去就会显示第一个LinearLayout 和第一个liesview
			ll.setVisibility(View.VISIBLE);
			ll1.setVisibility(View.GONE);
			showdata1();
		}
	}

	/**
	 * 为listview 加载数据  搜索的词汇
	 */
	private void showdata1() {
		adapter = new ListView_Adapter_Goodsearch(app.Search_datas, this);
		listview.addHeaderView(view);
		listview.setAdapter(adapter);
//		搜索相应商品
		listview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				setlist(app.Search_datas.get((int)arg3));
			}
		});
	}
}
