package com.bwf.duobao.ui.fragment;

import android.support.v4.app.Fragment;

public abstract class LazyFragment extends Fragment {
	@SuppressWarnings("unused")
	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		boolean isVisible;
		super.setUserVisibleHint(isVisibleToUser);
		if (getUserVisibleHint()) {
			isVisible = true;
			onVisible();
		} else {
			isVisible = false;
			onInvisible();
		}
	}

	protected void onVisible() {
		lazyload();
	}

	protected void onInvisible() {
		hideload();
	}

	/**
	 * 隐藏的时候调用方法
	 */
	protected abstract void hideload();

	/**
	 * 出现的时候调用方法
	 */
	protected abstract void lazyload();
}
