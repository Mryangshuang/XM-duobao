package com.bwf.duobao.ui.fragment;

import java.util.ArrayList;

import com.bwf.duobao.R;
import com.bwf.duobao.adapter.ListView_Adapter_ListFragment;
import com.bwf.duobao.application.MyApp;
import com.bwf.duobao.instance.list.Good;
import com.bwf.duobao.instance.list.ListItems;
import com.bwf.duobao.ui.activity.DetailMessageActivity;
import com.bwf.duobao.utils.AppSharedPreference;
import com.bwf.duobao.utils.MyHttpUtils;
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.umeng.socialize.utils.Log;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ListFragment extends LazyFragment {
	private ListView listview;
	private TextView totaltv, tv1, editbtn, enterbtn,  jiesuanbtn, delbtn;
	private RelativeLayout rl1, rl2;
	private CheckBox cbmain;
	private ListView_Adapter_ListFragment adapter;
	private boolean isPrepared;
	private HttpUtils mhttputils;
	public ArrayList<Good> list;
	private ArrayList<Boolean> showlist;
	private View emptyView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_list, null);
		emptyView = view.findViewById(R.id.emptyview);
		isPrepared = true;
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mhttputils = MyHttpUtils.getMyHttpUtils();
		initVeiws();
	}

	/**
	 * 初始化控件 以及监听
	 */
	private void initVeiws() {
		View view = getView();
		editbtn = (TextView) getView().findViewById(R.id.editbtn);
		enterbtn = (TextView) view.findViewById(R.id.enterbtn);
		rl1 = (RelativeLayout) view.findViewById(R.id.rl1);
		rl2 = (RelativeLayout) view.findViewById(R.id.rl2);

		tv1 = (TextView) view.findViewById(R.id.tv1);
		totaltv = (TextView) view.findViewById(R.id.tvtotal);

		jiesuanbtn = (TextView) view.findViewById(R.id.bt1);
		delbtn = (TextView) view.findViewById(R.id.bt2);
		cbmain = (CheckBox) view.findViewById(R.id.cbmain);
		listview = (ListView) view.findViewById(R.id.listview);

		// 编辑 按钮设置监听 显示checkbox 和更改下面视图
		editbtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				editbtn.setVisibility(View.GONE);
				enterbtn.setVisibility(View.VISIBLE);

				rl1.setVisibility(View.GONE);
				rl2.setVisibility(View.VISIBLE);
				cbmain.setChecked(false);
				cbmain.setVisibility(View.VISIBLE);

				add_true_or_false(true);
//TODO
				ArrayList<Boolean> getcheckedlist = adapter.getcheckedlist();
				Log.d("getcheckedlist", getcheckedlist + "");
				CheckBox cb = adapter.getHolder().cb;
				cb.setOnCheckedChangeListener(new OnCheckedChangeListener() {
					@Override
					public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
						Log.d("---------->", arg1 + "");
					}
				});
				Log.d("cb", cb.toString());
			}
		});
		// 完成
		enterbtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				hideViews();
				add_true_or_false(false);
				rl1.setVisibility(View.VISIBLE);
				editbtn.setVisibility(View.VISIBLE);
			}
		});

		// 删除键设置监听
		delbtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				ArrayList<Long> list_periods = new ArrayList<Long>();
				ArrayList<Boolean> getchecklist = adapter.getcheckedlist();

				for (int i = 0; i < getchecklist.size(); i++) {
					if (getchecklist.get(i)) {
						list_periods.add(list.get(i).getPeriod());
					}
				}
				setBlanket(list_periods);

				editbtn.setVisibility(View.GONE);
				enterbtn.setVisibility(View.GONE);
				rl1.setVisibility(View.GONE);
				rl2.setVisibility(View.GONE);
			}
		});
		// 总复选框设置监听
		cbmain.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				adapter.setcheckedlist(arg1);
			}
		});
		// 结算框设置监听
		jiesuanbtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				ArrayList<Long> list_periods = new ArrayList<Long>();
				ArrayList<Integer> list_times = adapter.getdata();
				for (int i = 0; i < list.size(); i++) {
					list_periods.add(list.get(i).getPeriod());
				}
				Log.d(list_periods.toString());
				Log.d(list_times.toString());
				// 购买
				MyApp.buy(getActivity(), list_periods, list_times);
				rl1.setVisibility(View.GONE);
				setBlanket(list_periods);
			}
		});
	}

	/**
	 * 设置空白页
	 * 
	 * @param list_periods
	 */
	protected void setBlanket(ArrayList<Long> list_periods) {
		list.clear();
		// 刷新数据
		adapter.notifyDataSetChanged();
		// 删除数据
		MyApp.delete_webdata(getActivity(), list_periods);
	}

	/**
	 * 刚进入时要对某些控件进行隐藏 初始化状态
	 */
	protected void hideViews() {
		editbtn.setVisibility(View.GONE);
		enterbtn.setVisibility(View.GONE);

		rl1.setVisibility(View.GONE);
		rl2.setVisibility(View.GONE);

	}

	/**
	 * 控制复选框隐藏还是显示
	 * 
	 * @param mes
	 */
	protected void add_true_or_false(boolean mes) {
		newchecklist(mes);
		adapter.notifyDataSetChanged();
	}

	/**
	 * 只是放了boolean 值在里面 没有用adapter的对象 不会产生空指针
	 * 
	 * @param mes
	 */
	private void newchecklist(boolean mes) {
		showlist.clear();
		for (int i = 0; i < list.size(); i++) {
			showlist.add(mes);
		}
	}

	/**
	 * 拉取购物清单
	 */
	private void getBilldatas() {
		String uid = AppSharedPreference.getStringValue(getActivity(), "app", "uid");
		if (uid.equals("")) {
			// 如果为空 就传一个空的集合进去
			list = new ArrayList<Good>();
			setadapter(list);
		} else {
			String url = "http://123.56.145.151:8080/Duobao-XM/unpay_list?uid=" + uid;
			mhttputils.send(HttpMethod.GET, url, new RequestCallBack<String>() {
				@Override
				public void onFailure(HttpException arg0, String arg1) {
					Toast.makeText(getActivity(), "拉取清单出错", 0).show();
				}

				@Override
				public void onSuccess(ResponseInfo<String> arg0) {
					ListItems fromJson = new Gson().fromJson(arg0.result, ListItems.class);
					// 购物清单列表
					list = fromJson.getResult().getList();
					setadapter(list);
				}
			});
		}
	}

	/**
	 * listview 加载adapter
	 * 
	 * @param bill_datas2
	 */
	private void setadapter(final ArrayList<Good> list) {
		if (list.size() == 0) {
			editbtn.setVisibility(View.GONE);
			rl1.setVisibility(View.GONE);
		} else {
			editbtn.setVisibility(View.VISIBLE);
			rl1.setVisibility(View.VISIBLE);
			tv1.setText("共" + list.size() + "件商品，总计" + list.size() * 10 + "多金币");
		}
	
		listview.setEmptyView(emptyView);
		// 处理默认效果 不显示复选框
		showlist = new ArrayList<Boolean>();
		newchecklist(false);

		// listview 设置数据
		adapter = new ListView_Adapter_ListFragment(list, showlist, getActivity());
		// listview 设置adapter
		listview.setAdapter(adapter);
		// 设置跳转
		listview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				Intent intent = new Intent(getActivity(), DetailMessageActivity.class);
				Good good = list.get((int) arg3);

				intent.putExtra("period", good.getPeriod());
				Log.d("发送：period", good.getPeriod() + "");
				startActivity(intent);
			}
		});
	}

	/**
	 * 延迟加载 的抽象方法重写 在每次出现的时候 就对数据进行加载
	 */
	@Override
	protected void lazyload() {
		if (!isPrepared || !isVisible()) {
			return;
		}
		Log.d("mes", "刷新清单");
		hideViews();
		getBilldatas();
	}

	/**
	 * 每次隐藏是调用的方法
	 */
	@Override
	protected void hideload() {
		if (!isPrepared || !isVisible()) {
			return;
		}
		ArrayList<Long> List_periods = new ArrayList<Long>();
		if (list == null) {
			return;
		}
		Log.d("hideload");
		for (int i = 0; i < list.size(); i++) {
			List_periods.add(list.get(i).getPeriod());
		}
		ArrayList<Integer> List_times = adapter.getdata();
		MyApp.update_webdata(getActivity(), List_periods, List_times);
	}

}
