package com.bwf.duobao.adapter;

import java.util.ArrayList;
import java.util.List;

import com.bwf.duobao.R;
import com.bwf.duobao.application.MyApp;
import com.bwf.duobao.entity.GoodsItem;
import com.bwf.duobao.ui.view.CarAnimation;
import com.bwf.duobao.utils.MBitmapHolder;
import com.lidroid.xutils.BitmapUtils;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class GridView_Adapter_Home extends BaseAdapter{
	private List<GoodsItem> list;
	private LayoutInflater inflater;
	private BitmapUtils mBitmapUtils;
	private Context context;
	public GridView_Adapter_Home(List<GoodsItem> list, Context context) {
		super();
		this.list = list;
		this.inflater = LayoutInflater.from(context);
		mBitmapUtils = MBitmapHolder.getBitmapUtils(context);
		this.context=context;
	}
	@Override
	public int getCount() {
		return list.size();
	}
	@Override
	public GoodsItem getItem(int arg0) {
		return list.get(arg0);
	}
	@Override
	public long getItemId(int arg0) {
		return arg0;
	}
	@Override
	public View getView(final int arg0, View arg1, ViewGroup arg2) {
		final ViewHolder holder;
		if(arg1 == null){
			arg1 = inflater.inflate(R.layout.item_home_gridview	, null);
			holder = new ViewHolder();
			holder.titleTv = (TextView) arg1.findViewById(R.id.tvTitle);
			holder.imgView = (ImageView) arg1.findViewById(R.id.img);
			holder.progressTv = (TextView) arg1.findViewById(R.id.tvProgress);
			holder.progressBar = (ProgressBar) arg1.findViewById(R.id.progressBar);
			holder.imgViewten=(ImageView) arg1.findViewById(R.id.img1);
			holder.imgViewhundred=(ImageView) arg1.findViewById(R.id.img2);
			holder.btnAddList=(TextView) arg1.findViewById(R.id.btnAddList);
			arg1.setTag(holder);
		}else{
			holder = (ViewHolder) arg1.getTag();
		}
		GoodsItem item = getItem(arg0);
		holder.titleTv.setText(item.getTitle());
		int progress = (item.getTotalTimes()-item.getRemainTimes())*100/item.getTotalTimes();
		holder.progressTv.setText(progress+"%");
		holder.progressBar.setProgress(progress);
		mBitmapUtils.display(holder.imgView, item.getImg());
//		看是十元专区还是百元专区
		int category = item.getCategory();
		if(category==1){
			holder.imgViewten.setVisibility(View.VISIBLE);
			holder.imgViewhundred.setVisibility(View.GONE);
		}else if(category==2){
			holder.imgViewhundred.setVisibility(View.VISIBLE);
			holder.imgViewten.setVisibility(View.GONE);
		}else{
			holder.imgViewten.setVisibility(View.GONE);
			holder.imgViewhundred.setVisibility(View.GONE);
		}
		
		holder.btnAddList.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
//				点击加入清单
				long period = list.get(arg0).getPeriod();
				ArrayList<Long> list_period=new ArrayList<Long>();
				list_period.add(period);
				ArrayList<Integer> list_times=new ArrayList<Integer>();
				list_times.add(1);
				MyApp.addToList(context,list_period,list_times);
//				开启动画
				startAnimation(holder.imgView);
				
			}
		});
		return arg1;
	}

	private class ViewHolder{
		ImageView imgView,imgViewten,imgViewhundred;
		TextView titleTv,btnAddList,progressTv;
		ProgressBar progressBar;
	}
	/**
	 * 封装了一个向数据源中添加数据的方法
	 * @param list
	 */
	public void addDatas(List<GoodsItem> list) {
		this.list.addAll(list);
		notifyDataSetChanged();
	}
	public void clearDatas() {
		this.list.clear();
		notifyDataSetChanged();
	}
//	关于动画
	private CarAnimation carAnimation;
	private void startAnimation(ImageView img){
		if(carAnimation==null){
			carAnimation=new CarAnimation();
		}
		carAnimation.startAnimation((Activity)context, img, ((Activity)context).findViewById(R.id.layout_car));
	}
}
