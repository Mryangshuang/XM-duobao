package com.bwf.duobao.adapter;

import java.util.ArrayList;

import com.bwf.duobao.R;
import com.bwf.duobao.instance.fenlei.Goods;
import com.lidroid.xutils.BitmapUtils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ListView_Adapter_Fenlei extends BaseAdapter{
private ArrayList<Goods> list;
private LayoutInflater inflater;
private BitmapUtils mBitmapUtils;

	public ListView_Adapter_Fenlei(ArrayList<Goods> list,Context context) {
	super();
	this.list = list;
	inflater=LayoutInflater.from(context);
	this.mBitmapUtils=new BitmapUtils(context);
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
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		ViewHolder viewholder=null;
		if(arg1==null){
			viewholder=new ViewHolder();
			arg1=inflater.inflate(R.layout.activity_fenlei_adapter, null);
			viewholder.titleTv=(TextView) arg1.findViewById(R.id.fenlei_textview);
			viewholder.imgView=(ImageView) arg1.findViewById(R.id.fenlei_imageview);
			arg1.setTag(viewholder);
		}else{
			viewholder=(ViewHolder) arg1.getTag();
		}
		Goods goods=list.get(arg0);
//		Log.d("mBitmapUtils", mBitmapUtils+"");
//		Log.d("viewholder.imgView", viewholder.imgView+"");
//		Log.d("viewholder", viewholder+"");
		mBitmapUtils.display(viewholder.imgView, goods.getImgUrl());
		viewholder.titleTv.setText(goods.getCategoryName());
		return arg1;
	}
	
	private class ViewHolder{
		ImageView imgView;
		TextView titleTv;
	}

}
