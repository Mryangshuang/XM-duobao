package com.bwf.duobao.ui.view;

import java.lang.reflect.Field;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Scroller;

public class AutoScrollViewPager extends ViewPager{
	
	public AutoScrollViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
		try {
			Field field = ViewPager.class.getDeclaredField("mScroller");
			field.setAccessible(true);
			Scroller scroller = new Scroller(getContext()){
				@Override
				public void startScroll(int startX, int startY, int dx, int dy, int duration) {
					super.startScroll(startX, startY, dx, dy, 1500);
				}
			};
			field.set(this, scroller);
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		
	}
	private Handler handler = new Handler(new Handler.Callback() {
		
		@Override
		public boolean handleMessage(Message msg) {
			//收到消息后直接切换到指定的页
			setCurrentItem(msg.arg1, true);
			return false;
		}
	});
	
	@Override
	public void setAdapter(PagerAdapter arg0) {
		super.setAdapter(arg0);
		startTask();
	}
	private int index;
	private Timer timer = new Timer();
	private TimerTask task;
	/**
	 * 开始自动滚动
	 */
	private void startTask(){
		if(task == null){
			task = new TimerTask() {
				
				@Override
				public void run() {
					//周期性执行的代码
					index++;
					if(index >= getAdapter().getCount()){
						index = 0;
					}
					handler.sendMessage(handler.obtainMessage(0, index, 0));
				}
			};
			timer.schedule(task, 2500, 2500);
		}
	}
	/**
	 * 停止自动滚动
	 */
	private void stopTask(){
		if(task != null){
			task.cancel();
			task = null;
		}
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent arg0) {
		switch (arg0.getAction()) {
		case MotionEvent.ACTION_DOWN:
			stopTask();
			break;
		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_CANCEL:
			index = getCurrentItem();
			startTask();
			break;
		}
		return super.onTouchEvent(arg0);
	}
}
