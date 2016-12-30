package com.bwf.duobao.adapter;

import java.util.ArrayList;

import com.bwf.duobao.R;
import com.bwf.duobao.instance.question.Question;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ListView_Adapter_Question extends BaseAdapter{
	private ArrayList<Question> list;
	private LayoutInflater Inflater;
	private ViewHolder holder;
	
	@ViewInject(R.id.tv5)
	private TextView tv;

	public ListView_Adapter_Question(ArrayList<Question> list, Context context) {
		super();
		ViewUtils.inject((Activity) context);
		this.list = list;
		Inflater = LayoutInflater.from(context);
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
		if (arg1 == null) {
			holder = new ViewHolder();
			arg1 = Inflater.inflate(R.layout.activity_question_adapter, null);
			holder.Item1 = (TextView) arg1.findViewById(R.id.tv1);
			holder.Item2 = (TextView) arg1.findViewById(R.id.tv5);
			arg1.setTag(holder);
		} else {
			holder = (ViewHolder) arg1.getTag();
		}

		holder.Item1.setText(arg0+1+". "+list.get(arg0).getTitle());
		if (showItem==arg0) {
			arg1.findViewById(R.id.tv5).setVisibility(View.VISIBLE);
			holder.Item2.setText(list.get(arg0).getContent());
		} else {
			arg1.findViewById(R.id.tv5).setVisibility(View.GONE);
		} // 重点，虽然默认为Gone，但复用的时候要加上，否则会混乱。
		return arg1;
	}

	private class ViewHolder {
		TextView Item1, Item2;
	}
	private int showItem=-1;
	public void setItem(int position){
		if(position==showItem){
			showItem=-1;
		}else{
			showItem=position;
		}
		notifyDataSetChanged();
	}

}
