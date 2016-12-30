package com.bwf.duobao.adapter;

import java.util.ArrayList;

import com.bwf.duobao.R;
import com.bwf.duobao.instance.personalcenter.LuckInfo;
import com.bwf.duobao.ui.activity.DuobaoDetailActivity;
import com.lidroid.xutils.BitmapUtils;
import com.umeng.socialize.utils.Log;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ListView_Adapter_person1 extends BaseAdapter {
	private ArrayList<LuckInfo> list;
	private LayoutInflater inflater;
	private BitmapUtils mbitmaputils;
	private LinearLayout ll0;
	private RelativeLayout rl3;
	private int Uid;
	private Context context;
	private LuckInfo luckInfo;
	public ListView_Adapter_person1(ArrayList<LuckInfo> list, Context context, int uid) {
		super();
		this.list = list;
		inflater = LayoutInflater.from(context);
		mbitmaputils = new BitmapUtils(context);
		this.Uid = uid;
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
		ViewHolder holder = null;
		if (arg1 == null) {
			holder = new ViewHolder();
			arg1 = inflater.inflate(R.layout.viewpager_personal_1_listview_adapter, null);
			holder.img1 = (ImageView) arg1.findViewById(R.id.img1);
			holder.img2 = (ImageView) arg1.findViewById(R.id.img2);
			holder.img3=(ImageView) arg1.findViewById(R.id.img3);
			holder.tv1 = (TextView) arg1.findViewById(R.id.tv1);
			holder.tv2 = (TextView) arg1.findViewById(R.id.tv2);
			holder.tv3 = (TextView) arg1.findViewById(R.id.tv3);
			holder.tv4 = (TextView) arg1.findViewById(R.id.tv4);
			holder.tv5 = (TextView) arg1.findViewById(R.id.tv5);
			holder.tv6 = (TextView) arg1.findViewById(R.id.tv6);
			holder.tv7 = (TextView) arg1.findViewById(R.id.tv7);
			holder.bar = (ProgressBar) arg1.findViewById(R.id.progressbar);
			holder.btn1 = (TextView) arg1.findViewById(R.id.bt1);
			holder.btn2 = (TextView) arg1.findViewById(R.id.bt2);
			holder.checkhim=(TextView) arg1.findViewById(R.id.checkhim);
//			通过status 来判断是否显示 或隐藏
			ll0 = (LinearLayout) arg1.findViewById(R.id.ll0);
			rl3 = (RelativeLayout) arg1.findViewById(R.id.rl3);
			arg1.setTag(holder);
		} else {
			holder = (ViewHolder) arg1.getTag();
		}
		
		
		luckInfo = list.get(arg0);
		// 设置产品图片
		mbitmaputils.display(holder.img1, luckInfo.getImg());
		int category = luckInfo.getCategory();
		if(category==1){
			holder.img3.setVisibility(View.VISIBLE);
			holder.img3.setImageResource(R.drawable.ic_ten_label);
		}else if(category==2){
			holder.img3.setVisibility(View.VISIBLE);
			holder.img3.setImageResource(R.drawable.ic_hundred_label);
		}else{
			holder.img3.setVisibility(View.GONE);
		}
		// 设置产品名称
		holder.tv1.setText(luckInfo.getGoodsName());
		// 设置期号
		holder.tv2.setText("参与期号：" + luckInfo.getPeriod());
		// 设置参与的次数
		holder.tv3.setText("TA已参与" + luckInfo.getDuoboaTimes() + "人次");

		int status = luckInfo.getStatus();
		// 如果是未公布
		if (status == 0) {
			ll0.setVisibility(View.VISIBLE);
			rl3.setVisibility(View.GONE);
//			获奖图片必须隐藏
			holder.img2.setVisibility(View.GONE);
			
			holder.tv6.setText("总需" + luckInfo.getTotalTimes());
			holder.tv7.setText("剩余" + luckInfo.getRemainTimes());
			holder.bar.setProgress(
					((luckInfo.getTotalTimes()-luckInfo.getRemainTimes()) * 100) / luckInfo.getTotalTimes());
//			已经公布
		} else if (status == 2) {
			ll0.setVisibility(View.GONE);
			rl3.setVisibility(View.VISIBLE);
			// 在已经公布的情况下 对是否为本人获奖进行判断
			if (luckInfo.getLuckUser().getUid() == Uid) {
				holder.img2.setVisibility(View.VISIBLE);
			} else {
				holder.img2.setVisibility(View.GONE);
			}
			holder.tv4.setText("获得者： " + luckInfo.getLuckUser().getNickname());
			holder.tv5.setText(luckInfo.getLuckUser().getDuobaoTimes() + "人次");
//			正在开奖
		} else {
			ll0.setVisibility(View.GONE);
			rl3.setVisibility(View.GONE);
//			获奖图片必须隐藏
			holder.img2.setVisibility(View.VISIBLE);
		}
		
		
		
		
//		查看号码 设置监听  跳转到夺宝详情界面
		holder.checkhim.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent=new Intent(context,DuobaoDetailActivity.class);
//				0代表调到夺宝详情界面
				intent.putExtra("tag", 0);
//				用来设置页面内容
				intent.putExtra("title", "夺宝详情");
//				用来设置用户ID
				intent.putExtra("uid", Uid);
				
				LuckInfo luckInfo2 = list.get(arg0);
//				设置商品期号
				intent.putExtra("period", luckInfo2.getPeriod());
//				设置商品名称
				intent.putExtra("goodname", luckInfo2.getGoodsName());
				
//				用来判断 是正在买 还是已经开奖了
				int status2 = luckInfo2.getStatus();
				intent.putExtra("status",status2);
//				如果已经开奖了  就把幸运号码 发过去
				if(status2==2){
					intent.putExtra("luckCode", luckInfo2.getLuckCode());
				}
//				代表是找夺宝详情  tag  0
				Log.d("发送:tag",0+"");
				Log.d("发送:title","夺宝详情");
//				以下三种用来作为头部三个内容
				Log.d("发送:period",luckInfo2.getPeriod()+"");
				Log.d("发送:uid",Uid+"");
				Log.d("发送:goodname",luckInfo2.getGoodsName()+"");
				Log.d("发送:status2",status2+"");
//				如果状态为2   在有中奖号码的情况下  进行传送  中奖号码
				Log.d("发送:luckCode","附加"+luckInfo2.getLuckCode());
				
				context.startActivity(intent);
			}
		});
		return arg1;
	}
	private class ViewHolder {
		private ImageView img1, img2,img3;
		private TextView tv1, tv2, tv3, tv4, tv5, tv6, tv7,btn1, btn2,checkhim;
		private ProgressBar bar;
	}
}

