package com.bwf.duobao.adapter;

import java.util.ArrayList;

import com.bwf.duobao.R;
import com.bwf.duobao.instance.goodsdetails.LuckInfo;
import com.bwf.duobao.ui.activity.DuobaoDetailActivity;
import com.bwf.duobao.utils.TimeFormat;
import com.umeng.socialize.utils.Log;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ListView_Adapter_Duobaoxiangqing extends BaseAdapter {
	private ArrayList<LuckInfo> list;
	private LayoutInflater inflater;
	private ViewHolder holder;
	private Context context;
	private Long period;
	private String goodname;
	private int luckcode;
	public ListView_Adapter_Duobaoxiangqing(ArrayList<LuckInfo> list, Context context,Long period,String goodname ,int luckcode ) {
		super();
		this.list = list;
		inflater = LayoutInflater.from(context);
		this.context=context;
		this.period=period;
		this.goodname=goodname;
		this.luckcode=luckcode;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public LuckInfo getItem(int arg0) {
		return list.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(final int arg0, View arg1, ViewGroup arg2) {
		holder = null;
		if(arg1==null){
			holder=new ViewHolder();
			arg1=inflater.inflate(R.layout.activity_duobaodetail_xiangqing, null);
			holder.tv1=(TextView) arg1.findViewById(R.id.tvtime);
			holder.tv2=(TextView) arg1.findViewById(R.id.tvperson);
			holder.tvcheck=(TextView) arg1.findViewById(R.id.tvcheck);
			arg1.setTag(holder);
		}else{
			holder=(ViewHolder) arg1.getTag();
		}
		LuckInfo luckInfo = list.get(arg0);
		String time = luckInfo.getTime();
		String timeformat = TimeFormat.timeformat(time, "yyyy-MM-dd \n HH:mm:ss.SSS");
		holder.tv1.setText(timeformat);
		holder.tv2.setText(luckInfo.getTimes()+"人次");
		holder.tvcheck.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				int rid = list.get(arg0).getRid();
				Intent intent=new Intent(context,DuobaoDetailActivity.class);
				intent.putExtra("rid", rid);
//				夺宝号码
				intent.putExtra("tag", 1);
				intent.putExtra("period", period);
				intent.putExtra("title", "夺宝号码");
				intent.putExtra("goodname", goodname);
				intent.putExtra("luckcode", luckcode);
				Log.d("发送：rid", rid+"");
				Log.d("发送：tag", 1+"");
				Log.d("发送：period", period+"");
				Log.d("发送：title", "夺宝号码");
				Log.d("发送：goodname", goodname);
				Log.d("发送：luckcode", luckcode+"");
				context.startActivity(intent);
			}
		});
		return arg1;
	}
	private class ViewHolder{
		public TextView tv1,tv2,tvcheck;
	}
}
