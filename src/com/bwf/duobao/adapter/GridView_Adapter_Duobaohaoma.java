package com.bwf.duobao.adapter;

import java.util.ArrayList;

import com.bwf.duobao.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class GridView_Adapter_Duobaohaoma extends BaseAdapter{
	private ArrayList<Integer> list;
	private LayoutInflater inflater;
	public GridView_Adapter_Duobaohaoma(ArrayList<Integer> list,Context context) {
		super();
		this.list = list;
		inflater=LayoutInflater.from(context);
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
		ViewHolder holder=null;
		if(arg1==null){
			holder=new ViewHolder();
			arg1=inflater.inflate(R.layout.activity_duobaodetail_haoma, null);
			holder.tv=(TextView) arg1.findViewById(R.id.tv);
			arg1.setTag(holder);
		}else{
			holder=(ViewHolder) arg1.getTag();
		}
		Integer integer = list.get(arg0);
		holder.tv.setText(""+integer);
		return arg1;
	}
	private class ViewHolder{
		public TextView tv;
	}
}
