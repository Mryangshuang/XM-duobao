package com.bwf.duobao.ui.fragment;

import android.support.v4.app.Fragment;
import android.view.View;

public abstract class BaseFragment extends Fragment{
	protected boolean isInitData;
//	@Override
//	public void setUserVisibleHint(boolean isVisibleToUser) {
//		super.setUserVisibleHint(isVisibleToUser);
//		System.out.println(isVisibleToUser+","+isInitData);
//		if(isVisibleToUser && !isInitData){
//			initData();
//			isInitData = true;
//		}
//	}
	
	protected abstract void initData();
	protected abstract void initViews();
	protected View findViewById(int id) {
		return getView().findViewById(id);
	}
}
