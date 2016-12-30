package com.bwf.duobao.adapter;

import java.util.ArrayList;

import com.bwf.duobao.R;
import com.bwf.duobao.instance.guest.Userinfo;
import com.bwf.duobao.utils.BitMapHandler;
import com.bwf.duobao.utils.TimeFormat;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.bitmap.BitmapDisplayConfig;
import com.lidroid.xutils.bitmap.callback.BitmapLoadCallBack;
import com.lidroid.xutils.bitmap.callback.BitmapLoadFrom;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ListView_Adapter_Guest extends BaseAdapter {
	private ArrayList<Userinfo> list;
	private LayoutInflater inflater;
	private BitmapUtils mBitmapUtils;
	public ListView_Adapter_Guest() {
		super();
	}

	public ListView_Adapter_Guest(ArrayList<Userinfo> list, Context context) {
		super();
		this.list = list;
		mBitmapUtils = new BitmapUtils(context);
		inflater = LayoutInflater.from(context);
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

	@SuppressLint("SimpleDateFormat")
	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		ViewHolder holder = null;
		if (arg1 == null) {
			holder = new ViewHolder();
			arg1 = inflater.inflate(R.layout.activity_guest_adapter, null);

			holder.tv1 = (TextView) arg1.findViewById(R.id.tv1);
			holder.tv2 = (TextView) arg1.findViewById(R.id.tv2);
			holder.tv3 = (TextView) arg1.findViewById(R.id.tv3);
			holder.tv4 = (TextView) arg1.findViewById(R.id.tv4);
			holder.imgView = (ImageView) arg1.findViewById(R.id.img);
			arg1.setTag(holder);
		} else {
			holder = (ViewHolder) arg1.getTag();
		}
		Userinfo userinfo = list.get(arg0);

		holder.tv3.setText(userinfo.getTimes() + "");
		// 设置显示的时间
		String timeformat = TimeFormat.timeformat(userinfo.getTime(), "yyyy-MM-dd HH:mm:ss.mmm");
		holder.tv4.setText(timeformat);

		holder.tv1.setText(userinfo.getUser().getNickName());
		String ipAddress = userinfo.getUser().getIPAddress();
		if (ipAddress == null) {
			holder.tv2.setText("(IP无法识别" + " IP:" + userinfo.getUser().getIP() + ")");
		} else {
			holder.tv2.setText("(" + ipAddress + " IP:" + userinfo.getUser().getIP() + ")");
		}
		mBitmapUtils.display(holder.imgView, userinfo.getUser().getAvatar(), new BitmapLoadCallBack<ImageView>() {

			@Override
			public void onLoadCompleted(ImageView arg0, String arg1, Bitmap arg2, BitmapDisplayConfig arg3,
					BitmapLoadFrom arg4) {
				Bitmap bitmap = BitMapHandler.createCircleBitmap(arg2);
				arg0.setImageBitmap(bitmap);
			}

			@Override
			public void onLoadFailed(ImageView arg0, String arg1, Drawable arg2) {
			}
		});
		return arg1;
	}

	private class ViewHolder {
		ImageView imgView;
		TextView tv1, tv2, tv3, tv4;
	}

}
