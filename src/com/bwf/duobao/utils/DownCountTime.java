package com.bwf.duobao.utils;

public class DownCountTime {
	
	public static String getremaintime(int time){
	int remainTime=time;
		if(remainTime>=3600*1000){
//			按小时样式返回
			return(parsehour(time));
		}else{
//			按分样式返回
			return(parsemin(time));
		}
	
	}
	/**
	 * 按小时的形式返回
	 * @param time
	 * @return
	 */
		private static String parsehour(int time) {
			String HH;
			String mm;
			String ss;
			int result=time/1000;
			int a = time / 3600;
			int b = time % 3600 / 60;
			int c = time % 60;
			if (a < 10) {
				HH = "0" + a;
			} else {
				HH = "" + a;
			}
			if (b < 10) {
				mm = "0" + b;
			} else {
				mm = "" + b;
			}
			if (c < 10) {
				ss = "0" + c;
			} else {
				ss = "" + c;
			}
			return HH + ":" + mm + ":" + ss;
		}
	/**
	 * 按分返回
	 * @param time
	 * @return
	 */
		private static String parsemin(int time) {
			String mm;
			String ss;
			String sss;
			int a = time / 1000/60;
			int b = time / 1000 % 60;
			int c = time % 1000;
			if (a < 10) {
				mm = "0" + a;
			} else{
				mm = "" + a;
			}
			if (b < 10) {
				ss = "0" + b;
			}else{
				ss = "" + b;
			}
			if (10<=c &&c < 100) {
				sss = "0" + c;
			} else if(c<10){
				sss = "00" + c;
			}else{
				sss=""+c;
			}
			return mm + ":" + ss + ":" + sss;
		}
}
