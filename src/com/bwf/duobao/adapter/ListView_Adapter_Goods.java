package com.bwf.duobao.adapter;

import java.util.ArrayList;

import com.bwf.duobao.R;
import com.bwf.duobao.application.MyApp;
import com.bwf.duobao.instance.shiyuanduobao.Good;
import com.bwf.duobao.ui.view.CarAnimation;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.ViewUtils;

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

public class ListView_Adapter_Goods extends BaseAdapter {
	private ArrayList<Good> list;
	private LayoutInflater Inflater;
	private BitmapUtils mBitmapUtils;
	private Context context;
	public void startmove(){
//		startAnimation(holder.imageview);
	}
	public ListView_Adapter_Goods(ArrayList<Good> list, Context context) {
		super();
		this.list = list;
		Inflater = LayoutInflater.from(context);
		mBitmapUtils = new BitmapUtils(context);
		this.context=context;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		return list.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(final int arg0, View arg1, ViewGroup arg2) {
		final ViewHolder holder;
		if (arg1 == null) {
			holder=new ViewHolder();
			arg1 = Inflater.inflate(R.layout.activity_suzq_adapter, null);
			ViewUtils.inject(this,arg1);
			holder.imageview = (ImageView) arg1.findViewById(R.id.imageview);
			holder.imageview1 = (ImageView) arg1.findViewById(R.id.img1);
			holder.imageview2 = (ImageView) arg1.findViewById(R.id.img2);
			holder.textview = (TextView) arg1.findViewById(R.id.tv1);
			holder.zongxutextview = (TextView) arg1.findViewById(R.id.tv2);
			holder.shengyutextview = (TextView) arg1.findViewById(R.id.tv3);
			holder.progressbar = (ProgressBar) arg1.findViewById(R.id.progressbar);
			holder.textview_addlist=(TextView) arg1.findViewById(R.id.tv4);
			arg1.setTag(holder);
		} else {
			holder = (ViewHolder) arg1.getTag();
		}

		com.bwf.duobao.instance.shiyuanduobao.Good goods = list.get(arg0);
		mBitmapUtils.display(holder.imageview, goods.getImg());
		holder.textview.setText(goods.getTitle());
		holder.shengyutextview.setText("剩余" + goods.getRemainTimes());
		holder.zongxutextview.setText("总需" + goods.getTotalTimes());
		int progress=(goods.getTotalTimes()-goods.getRemainTimes())*100/goods.getTotalTimes();
		
		holder.progressbar.setProgress(progress);
		int category = goods.getCategory();
		if(category==1){
			holder.imageview1.setVisibility(View.VISIBLE);
			holder.imageview2.setVisibility(View.GONE);
		}else if(category==2){
			holder.imageview1.setVisibility(View.GONE);
			holder.imageview2.setVisibility(View.VISIBLE);
		}else{
			holder.imageview1.setVisibility(View.GONE);
			holder.imageview2.setVisibility(View.GONE);
		}
		
		holder.textview_addlist.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				long period = list.get(arg0).getPeriod();
//				加入清单
				ArrayList<Long> list_period=new ArrayList<Long>();
				list_period.add(period);
				ArrayList<Integer> list_times=new ArrayList<Integer>();
				list_times.add(1);
				MyApp.addToList(context,list_period,list_times);
//				
				startAnimation(holder.imageview);
				
			}
		});
		return arg1;
	}

	private class ViewHolder {
		ImageView imageview,imageview1,imageview2;
		ProgressBar progressbar;
		TextView textview, zongxutextview, shengyutextview ,textview_addlist;
	}
//	关于动画
	private CarAnimation carAnimation;
	private void startAnimation(ImageView img){
		if(carAnimation==null){
			carAnimation=new CarAnimation();
		}
		carAnimation.startAnimation((Activity)context, img, ((Activity)context).findViewById(R.id.imgcar));
	}
}
