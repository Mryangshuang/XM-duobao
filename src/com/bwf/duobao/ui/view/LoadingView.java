package com.bwf.duobao.ui.view;

import com.bwf.duobao.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.TextView;

public class LoadingView extends FrameLayout implements View.OnClickListener{

	public LoadingView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}
	private View mLoadingImg;
	private RotateAnimation anim;
	
	private ViewGroup mLoadingLayout,mFaildeLayout;
	private TextView mTipsTv,mTryAgainTv;
	private void init() {
		View view=LayoutInflater.from(getContext()).inflate(R.layout.v_loading, null);
		FrameLayout.LayoutParams params=new FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
		params.gravity=Gravity.CENTER;
		addView(view,params);
		mLoadingImg=view.findViewById(R.id.img_loading_progress);
		
		mLoadingLayout=(ViewGroup) view.findViewById(R.id.layout_loading);
		mFaildeLayout=(ViewGroup) view.findViewById(R.id.layout_failed);
		
		mTipsTv=(TextView) view.findViewById(R.id.tv_tips);
		mTryAgainTv=(TextView) view.findViewById(R.id.tv_tryagain);
		mTryAgainTv.setOnClickListener(this);
//		旋转360度  以自己中心为中心
		anim=new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		anim.setDuration(1000);
		anim.setRepeatCount(-1);
		anim.setInterpolator(new LinearInterpolator());//匀速  
	}
	/**
	 * 开始加载调用的方法
	 */
	public void startLoading(){
		mLoadingLayout.setVisibility(View.VISIBLE);
		mFaildeLayout.setVisibility(View.GONE);
		mLoadingImg.startAnimation(anim);
	}
	/**
	 * 加载失败的时候调用的方法
	 */
	public void loadFailed(){
		mLoadingLayout.setVisibility(View.GONE);
		mFaildeLayout.setVisibility(View.VISIBLE);
		
		anim.cancel();
		mLoadingImg.clearAnimation();
		mTipsTv.setText("接收网络数据失败");
		
	}
	
	/**
	 * 加载成功调用的方法
	 */
	public void loadSuccess(){
		mLoadingLayout.setVisibility(View.GONE);
		mFaildeLayout.setVisibility(View.GONE);
		anim.cancel();
		mLoadingImg.clearAnimation();
	}
	
	/**
	 * 没有网络调用这个方法
	 */
	public void noNetwork(){
		mLoadingLayout.setVisibility(View.GONE);
		anim.cancel();
		mLoadingImg.clearAnimation();
		
		mFaildeLayout.setVisibility(View.VISIBLE);
		mTipsTv.setText("没有网络，请检查网络");
	}
	
	@Override
	public void onClick(View v) {
		if(v==mTryAgainTv){
			if(listener!=null){
				listener.onTryAgainClick();
			}
		}
	}
	
	private OnLoadingViewListener listener;
	public void setOnLoadingViewListener(OnLoadingViewListener listener){
		this.listener=listener;
	}
	public interface OnLoadingViewListener{
		void onTryAgainClick();
	}
}
