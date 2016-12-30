package com.bwf.duobao.adapter;

import java.util.ArrayList;

import com.bwf.duobao.R;
import com.bwf.duobao.instance.goodsdetails.LuckInfo;
import com.bwf.duobao.instance.goodsdetails.LuckUser;
import com.bwf.duobao.ui.activity.PersonalSpaceActivity;
import com.bwf.duobao.utils.BitMapHandler;
import com.bwf.duobao.utils.TimeFormat;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.bitmap.BitmapDisplayConfig;
import com.lidroid.xutils.bitmap.callback.BitmapLoadCallBack;
import com.lidroid.xutils.bitmap.callback.BitmapLoadFrom;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.umeng.socialize.utils.Log;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ListView_Adapter_wangqijiexiao extends BaseAdapter {
	private ArrayList<LuckInfo> list;
	private LayoutInflater inflater;
	private BitmapUtils mbitmaputil;
	@ViewInject(R.id.ll1)
	private LinearLayout ll1;
	@ViewInject(R.id.ll2)
	private LinearLayout ll2;
	private Context context;
	private LinearLayout ll3;
	
	public ListView_Adapter_wangqijiexiao(ArrayList<LuckInfo> list,Context context) {
		super();
		this.list = list;
		inflater=LayoutInflater.from(context);
		mbitmaputil=new BitmapUtils(context);
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
	public View getView(final int positon, View arg1, ViewGroup arg2) {
		ViewHolder holder=null;
		if(arg1==null){
			holder=new ViewHolder();
			arg1=inflater.inflate(R.layout.activity_wangqijiexiao_adapter, null);
			ViewUtils.inject(this,arg1);
			holder.img=(ImageView) arg1.findViewById(R.id.img);
			holder.tv1=(TextView) arg1.findViewById(R.id.tv1);
			holder.tv2=(TextView) arg1.findViewById(R.id.tv2);
			holder.tv3=(TextView) arg1.findViewById(R.id.tv3);
			holder.tv4=(TextView) arg1.findViewById(R.id.tv4);
			holder.tv5=(TextView) arg1.findViewById(R.id.tv5);
			holder.tv6=(TextView) arg1.findViewById(R.id.tv6);
			holder.tv7=(TextView) arg1.findViewById(R.id.tv7);
			arg1.setTag(holder);
		}else{
			holder=(ViewHolder) arg1.getTag();
		}
		LuckInfo luckInfo = list.get(positon);
		LuckUser luckUser = luckInfo.getLuckUser();
		int luckCode = luckInfo.getLuckCode();
		String period = luckInfo.getPeriod();
		
		if(luckUser==null){
			ll1.setVisibility(View.VISIBLE);
			ll2.setVisibility(View.GONE);
			holder.tv1.setText("期号： "+period+" 即将揭晓，正在倒计时......");
		}else if(luckUser!=null){
			ll1.setVisibility(View.GONE);
			ll2.setVisibility(View.VISIBLE);
			String avatar = luckUser.getAvatar();
			mbitmaputil.display(holder.img, avatar,new BitmapLoadCallBack<ImageView>() {
				@Override
				public void onLoadCompleted(ImageView arg0, String arg1, Bitmap arg2, BitmapDisplayConfig arg3,
						BitmapLoadFrom arg4) {
					Bitmap bitmap=BitMapHandler.createRoundBitmap(arg2);
					arg0.setImageBitmap(bitmap);
				}

				@Override
				public void onLoadFailed(ImageView arg0, String arg1, Drawable arg2) {
				}
			});
//			期号  揭晓时间
			String revealedTime = luckInfo.getRevealedTime();
			String timeformat = TimeFormat.timeformat(revealedTime, "yyyy-MM-dd HH-mm-ss");
			holder.tv2.setText("期号： "+period+"(揭晓时间： "+timeformat+")");
//			姓名
			String nickname = luckUser.getNickname();
			SpannableString str=new SpannableString(nickname);
			str.setSpan(new ForegroundColorSpan(Color.BLUE), 0, str.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			holder.tv3.setText("获得者： "+str);
//			IP
			String ip = luckUser.getIP();
			String ipAddress = luckUser.getIPAddress();
			if(ipAddress==null){
				holder.tv4.setText("(IP无法识别"+" IP: "+ip+")");
			}else{
				holder.tv4.setText("("+ipAddress+" IP: "+ip+")");
			}
			int uid = luckUser.getUid();
			holder.tv5.setText("用户ID： "+uid+"(唯一不变标识)");
			holder.tv6.setText("幸运号码： "+luckCode);
			
			int duobaoTimes = luckUser.getDuobaoTimes();
			holder.tv7.setText("本期参与： "+duobaoTimes+"人次");
		}
		ll3=(LinearLayout) arg1.findViewById(R.id.ll3);
		ll3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				showinfo(positon);
			}
		});
		return arg1;
	}
	/**
	 * 获取新数据进行跳转
	 * @param positon
	 */
	protected void showinfo(int positon) {
		Intent intent = new Intent(context, PersonalSpaceActivity.class);
		LuckUser luckUser = list.get(positon).getLuckUser();
		int uid = luckUser.getUid();
		String avatar = luckUser.getAvatar();
		String nickName = luckUser.getNickname();
		intent.putExtra("uid", uid);
		intent.putExtra("avatar", avatar);
		intent.putExtra("nickName", nickName);
		Log.d("发送：uid", ""+uid);
		Log.d("发送：avatar", avatar);
		Log.d("发送：nickName", nickName);
		context.startActivity(intent);
	}


	private class ViewHolder{
		private ImageView img;
		private TextView tv1,tv2,tv3,tv4,tv5,tv6,tv7;
	}
}
