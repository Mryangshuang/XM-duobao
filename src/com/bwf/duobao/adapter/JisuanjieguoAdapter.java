package com.bwf.duobao.adapter;

import java.util.ArrayList;

import com.bwf.duobao.R;
import com.bwf.duobao.instance.jisuanjieguo.Person;
import com.bwf.duobao.utils.TimeFormat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class JisuanjieguoAdapter extends BaseAdapter {

	ArrayList<Person> timesList;
	private LayoutInflater inflater;
	
	public JisuanjieguoAdapter(ArrayList<Person> timesList,Context context) {
		super();
		this.timesList = timesList;
		inflater=LayoutInflater.from(context);
	}
	@Override
	public int getCount() {
		return timesList.size();
	}

	@Override
	public Object getItem(int arg0) {
		return timesList.get(arg0);
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
			arg1=inflater.inflate(R.layout.activity_jisuanjieguo_adapter, null);
			holder.tv1=(TextView) arg1.findViewById(R.id.tv1);
			holder.tv2=(TextView) arg1.findViewById(R.id.tv2);
			arg1.setTag(holder);
		}else{
			holder=(ViewHolder) arg1.getTag();
		}
		Person person = timesList.get(arg0);
		String dateTime = person.getDateTime();
		String timeformat = TimeFormat.timeformat(dateTime, "yyyy-MM-dd HH:mm:ss.SSS ");
		String timeForCompute = person.getTimeForCompute();
		
		holder.tv1.setText(timeformat+"→"+timeForCompute);
		holder.tv2.setText(person.getUserNickName());
		return arg1;
	}
	private class ViewHolder {
		private TextView tv1,tv2;
	}
}
