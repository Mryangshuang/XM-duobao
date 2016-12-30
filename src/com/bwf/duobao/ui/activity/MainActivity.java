package com.bwf.duobao.ui.activity;

import java.util.ArrayList;
import java.util.List;

import com.bwf.duobao.R;
import com.bwf.duobao.ui.fragment.FindFragment;
import com.bwf.duobao.ui.fragment.HomeFragment;
import com.bwf.duobao.ui.fragment.ListFragment;
import com.bwf.duobao.ui.fragment.MineFragment;
import com.bwf.duobao.ui.fragment.WinFragment;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.umeng.socialize.UMShareAPI;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.ViewGroup;

public class MainActivity extends BaseTabActivity {
	@ViewInject(R.id.viewPager)
	private ViewPager mViewPager;
	private List<Fragment> fragments;
	public UMShareAPI mShareAPI;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab_activity);
		 mShareAPI = UMShareAPI.get(this);
		 
		ViewGroup tabContainer = (ViewGroup) findViewById(R.id.bottom_tab);
		fragments = new ArrayList<Fragment>();
		fragments.add(new HomeFragment());
		fragments.add(new WinFragment());
		fragments.add(new FindFragment());
		fragments.add(new ListFragment());
		fragments.add(new MineFragment());
		int[][] res = {
				{R.drawable.tab_home_normal,R.drawable.tab_home_press},
				{R.drawable.tab_announce_normal,R.drawable.tab_announce_press},
				{R.drawable.tab_rank_unselect,R.drawable.tab_rank_selected},
				{R.drawable.tab_list_normal,R.drawable.tab_list_press},
				{R.drawable.tab_me_normal,R.drawable.tab_me_press}};
		int[] textColors = {getResources().getColor(R.color.tab_text),getResources().getColor(R.color.tab_text_selected)};
		initViews();
		init(mViewPager,tabContainer, fragments,res,textColors );
//		默认为第几页  
		switchTab(0);
	}
	private void initViews() {
		ViewUtils.inject(this);
	}
	/**
	 * 微信  QQ 新浪   授权重写
	 */
	@Override
	public void onActivityResult(int arg0, int arg1, Intent arg2) {
		super.onActivityResult(arg0, arg1, arg2);
		 mShareAPI.onActivityResult(arg0, arg1, arg2);
	}
}
