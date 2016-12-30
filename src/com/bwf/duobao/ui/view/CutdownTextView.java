package com.bwf.duobao.ui.view;

import java.text.SimpleDateFormat;

import android.content.Context;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.widget.TextView;

public class CutdownTextView extends TextView{

	public CutdownTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	private SimpleDateFormat dateFormat;
	private CountDownTimer countDownTimer;
	private String patternHour = "HH:mm:ss";
	private String patternMinute = "mm:ss.SS";
	private static final int STATE_HOUR = 1;
	private static final int STATE_MINUTE = 2;
	private int currentState;

	public void startCutdown(long time,long period){
		stopCutdown();
		if(time <= 0){
			if(listener != null){
				listener.onFinish();
			}
			return;
		}
		if(time >= 60*60*1000){
			dateFormat = new SimpleDateFormat(patternHour);
			currentState = STATE_HOUR;
		}else{
			dateFormat = new SimpleDateFormat(patternMinute);
			currentState = STATE_MINUTE;
		}
		if(countDownTimer == null){
			countDownTimer = new CountDownTimer(time, period) {

				@Override
				public void onTick(long millisUntilFinished) {
					if(currentState == STATE_HOUR && millisUntilFinished < 60*60*1000){
						dateFormat.applyPattern(patternMinute);
						currentState = STATE_MINUTE;
					}
					setText(dateFormat.format(millisUntilFinished+3600000*16));
				}
				@Override
				public void onFinish() {
					setText(dateFormat.format(0));
					setText("正在揭晓...");
					if(listener != null){
						listener.onFinish();
					}
					stopCutdown();
				}
			};
			countDownTimer.start();
		}
	}
	public void startCutdown(int position,long time,long period){
		stopCutdown();
		if(time <= 0){
			if(listener != null){
				listener.onFinish();
			}
			return;
		}
		if(time >= 60*60*1000){
			dateFormat = new SimpleDateFormat(patternHour);
			currentState = STATE_HOUR;
		}else{
			dateFormat = new SimpleDateFormat(patternMinute);
			currentState = STATE_MINUTE;
		}
		if(countDownTimer == null){
			countDownTimer = new CountDownTimer(time, period) {
				
				@Override
				public void onTick(long millisUntilFinished) {
					if(currentState == STATE_HOUR && millisUntilFinished < 60*60*1000){
						dateFormat.applyPattern(patternMinute);
						currentState = STATE_MINUTE;
					}
					setText(dateFormat.format(millisUntilFinished+3600000*16));
				}
				@Override
				public void onFinish() {
					setText(dateFormat.format(0));
					setText("正在揭晓...");
					if(listener != null){
						listener.onFinish();
					}
					stopCutdown();
				}
			};
			countDownTimer.start();
		}
	}
	public void stopCutdown(){
		if(countDownTimer != null){
			countDownTimer.cancel();
			countDownTimer = null;
		}
	}
	private OnCountDownFinishListener listener;
	public void setOnCountDownFinishListener(OnCountDownFinishListener listener){
		this.listener = listener;
	}
	public interface OnCountDownFinishListener{
		public void onFinish();
	}
	
	private void print(int position,String str){
		if(position != 0){
			System.out.println(str);
		}
	}
}
