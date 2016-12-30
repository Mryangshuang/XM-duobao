package com.bwf.duobao.ui.fragment;

import java.util.List;

import com.bwf.duobao.R;
import com.bwf.duobao.adapter.GridView_Adapter_Win;
import com.bwf.duobao.entity.GoodsItem;
import com.bwf.duobao.entity.ResponseGoodsItem;
import com.bwf.duobao.ui.activity.DetailMessageActivity;
import com.bwf.duobao.utils.MyHttpUtils;
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

public class WinFragment extends LazyFragment {
	private HttpUtils mhttputils;
	private GridView gridview;
	private ImageView img;
	private View view;
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		view = LayoutInflater.from(activity).inflate(R.layout.fragment_win, null);
	}
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		gridview=(GridView) view.findViewById(R.id.gridview);
		img=(ImageView) view.findViewById(R.id.img);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		img.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				initData();
			}
		});
	}

	/** 从网上拿数据 **/
	protected void initData() {
		mhttputils = MyHttpUtils.getMyHttpUtils();
		String url = "http://123.56.145.151:8080/Duobao-XM/revealed-lastest?pageNum=1&pageSize=20";
		mhttputils.send(HttpMethod.GET, url, new RequestCallBack<String>() {

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				Toast.makeText(getActivity(), "请求数据超时~~", 0).show();
			}

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				String result = arg0.result;
				initViews(result);
			}
		});

	}

	/**
	 * 得到网络数据 进行解析
	 * 
	 * @param result
	 */
	protected void initViews(String result) {
		ResponseGoodsItem fromJson = new Gson().fromJson(result, ResponseGoodsItem.class);
		final List<GoodsItem> list = fromJson.getResult().getList();
		
		GridView_Adapter_Win adapter=new GridView_Adapter_Win(list,getActivity());
		gridview.setAdapter(adapter);
		gridview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				Intent intent=new Intent(getActivity(),DetailMessageActivity.class);
				intent.putExtra("period", list.get((int)arg3).getPeriod());
				Log.d("发送period", ""+list.get((int)arg3).getPeriod());
				intent.putExtra("tag", 1);
				startActivity(intent);
			}
		});
	}
	@Override
	protected void lazyload() {
		if(isVisible()){
			initData();
			Log.d("mes", "刷新最新揭晓");
		}
	}
	@Override
	protected void hideload() {
		// TODO Auto-generated method stub
		
	}
}
