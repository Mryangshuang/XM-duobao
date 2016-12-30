package com.bwf.duobao.adapter;

import java.util.ArrayList;

import com.bwf.duobao.R;
import com.bwf.duobao.instance.list.Good;
import com.lidroid.xutils.BitmapUtils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class ListView_Adapter_ListFragment extends BaseAdapter {
	private ArrayList<Good> bill_datas;
	private LayoutInflater inflater;
	private BitmapUtils myBitmapUtils;
	private ViewHolder holder;
	private Context context;
	private ArrayList<Boolean> showlist;
	private ArrayList<Boolean> checkedlist;
	private ArrayList<Integer> list_times;

	public ListView_Adapter_ListFragment(ArrayList<Good> bill_datas, ArrayList<Boolean> showlist, Context context) {
		super();
		this.bill_datas = bill_datas;
		inflater = LayoutInflater.from(context);
		myBitmapUtils = new BitmapUtils(context);
		this.context = context;
		this.showlist = showlist;
		
		initChecked(showlist);

	}
/**
 * 在每次进来的时候 都为false  复选框不会显示
 * @param showlist
 */
	private void initChecked(ArrayList<Boolean> showlist) {
		checkedlist = new ArrayList<Boolean>();
		list_times=new ArrayList<Integer>();
		for (int i = 0; i < showlist.size(); i++) {
			checkedlist.add(false);
			list_times.add(bill_datas.get(i).getJoinTimes());
		}
	}

	@Override
	public int getCount() {
		return bill_datas.size();
	}

	@Override
	public Good getItem(int arg0) {
		return bill_datas.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(final int arg0, View arg1, ViewGroup arg2) {
		if (arg1 == null) {
			holder = new ViewHolder();
			arg1 = inflater.inflate(R.layout.activity_listfragment_adapter, null);

			holder.cb = (CheckBox) arg1.findViewById(R.id.cbadapter);
			holder.cb.setClickable(true);

			holder.img = (ImageView) arg1.findViewById(R.id.img);
			holder.img2=(ImageView) arg1.findViewById(R.id.img2);
			holder.tv1 = (TextView) arg1.findViewById(R.id.tv1);
			holder.tv2 = (TextView) arg1.findViewById(R.id.tv2);
			holder.add = (ImageView) arg1.findViewById(R.id.add);
			holder.minus = (ImageView) arg1.findViewById(R.id.minus);
			holder.et = (EditText) arg1.findViewById(R.id.et);
			arg1.setTag(holder);
		} else {
			holder = (ViewHolder) arg1.getTag();
		}
		// 设置多选框的显示隐藏
		Boolean boolean1 = showlist.get(arg0);
		if (boolean1) {
			holder.cb.setVisibility(View.VISIBLE);
			holder.cb.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				@Override
				public void onCheckedChanged(CompoundButton button, boolean arg1) {
					checkedlist.set(arg0, arg1);
				}
			});
			// 从数组当中拿到数据 判断是否设置选中与不选中 全选按钮要用到
			holder.cb.setChecked(checkedlist.get(arg0));
		} else {
			holder.cb.setVisibility(View.GONE);
		}

		Good result = bill_datas.get(arg0);
		// 设置图片
		myBitmapUtils.display(holder.img, result.getImg());
		int category = result.getCategory();
		if (category==1) {
			holder.img2.setVisibility(View.VISIBLE);
			holder.img2.setImageResource(R.drawable.ic_ten_label);
		}else if(category==2){
			holder.img2.setVisibility(View.VISIBLE);
			holder.img2.setImageResource(R.drawable.ic_hundred_label);
		}else{
			holder.img2.setVisibility(View.GONE);
		}
		holder.tv1.setText(result.getTitle());
		holder.tv2.setText("总需" + result.getTotalTimes() + "人次，还剩" + result.getRemainTimes() + "人次");

		// 设置缩放动画
		int joinTimes = list_times.get(arg0);
		Animation animation = AnimationUtils.loadAnimation(context, R.anim.text_change);
		if (!holder.et.getText().toString().equals(joinTimes + "")) {
			holder.et.startAnimation(animation);
			holder.et.setText(joinTimes + "");
		}

		// 加号
		holder.add.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				add_minus(arg0, "add");
			}
		});
		
		// 减号
		holder.minus.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				add_minus(arg0, "minus");
			}
		});
		return arg1;
	}
/**
 * 加法或者是减法
 * @param arg0
 * @param string
 */
	protected void add_minus(int arg0, String string) {
		
		if(string.equals("add")){
			list_times.set(arg0, list_times.get(arg0)+1);
		}else if(string.equals("minus")){
			if(list_times.get(arg0)-1<0){
				return;
			}else{
				list_times.set(arg0, list_times.get(arg0)-1);
			}
		}
		notifyDataSetChanged();
	}

	public class ViewHolder {
		public CheckBox cb;
		public ImageView img, add, minus,img2;
		public TextView tv1, tv2;
		public EditText et;
	}

	public ViewHolder getHolder() {
		return holder;
	}

	/**
	 * 得到adapter 的checked list
	 * 
	 * @return
	 */
	public ArrayList<Boolean> getcheckedlist() {
		return checkedlist;
	}

	/**
	 * 设置adapter 的checkedlist 值
	 * 
	 * @param m
	 */
	public void setcheckedlist(boolean m) {
		checkedlist.clear();
		for (int i = 0; i < showlist.size(); i++) {
			checkedlist.add(m);
			notifyDataSetChanged();
		}
	}
	public ArrayList<Integer>  getdata(){
		return list_times;
	}
}
