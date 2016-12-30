/** 
* @author  yangshuang 
* @date 创建时间：2016年6月25日 上午11:29:35 
*/
package com.bwf.duobao.adapter;

import java.util.ArrayList;

import com.bwf.duobao.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * @author Administrator
 *
 */
public class ListView_Adapter_Goodsearch extends BaseAdapter {

	private ArrayList<String> list;
	private LayoutInflater inflater;

	public ListView_Adapter_Goodsearch(ArrayList<String> list, Context context) {
		super();
		this.list = list;
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

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		ViewHolder holder = null;
		if (arg1 == null) {
			holder = new ViewHolder();
			arg1 = inflater.inflate(R.layout.activity_goodsearch_adapter, null);
			holder.tv = (TextView) arg1.findViewById(R.id.tv);
			arg1.setTag(holder);
		} else {
			holder = (ViewHolder) arg1.getTag();
		}
		String string = list.get(arg0);
		holder.tv.setText(string);
		return arg1;
	}

	private class ViewHolder {
		public TextView tv;
	}
}
