package com.bwf.duobao.adapter;

import java.util.ArrayList;
import java.util.HashMap;

import com.bwf.duobao.R;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.bitmap.BitmapDisplayConfig;
import com.lidroid.xutils.bitmap.callback.BitmapLoadCallBack;
import com.lidroid.xutils.bitmap.callback.BitmapLoadFrom;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;

public class ListView_Adapter_PictureDetails extends BaseAdapter{
	private ArrayList<String> list;
	private BitmapUtils mbitmaputils;
	private LayoutInflater inflater;
	
	public ListView_Adapter_PictureDetails(ArrayList<String> list,Context context	) {
		super();
		this.list = list;
		mbitmaputils=new BitmapUtils(context);
		inflater=LayoutInflater.from(context);
		mbitmaputils.configThreadPoolSize(1);
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
	public View getView(final int arg0, View arg1, ViewGroup arg2) {
		ViewHolder holder=null;
		if(arg1==null){
			holder=new ViewHolder();
			arg1=inflater.inflate(R.layout.activity_picturedetails_adapter, null);
			holder.img=(ImageView) arg1.findViewById(R.id.img);
			arg1.setTag(holder);
		}else{
			holder=(ViewHolder) arg1.getTag();
		}
		
		
		if(infos.containsKey(arg0)){
			LayoutParams params=(LayoutParams) holder.img.getLayoutParams();
			BitmapInfo info=infos.get(arg0);
//         只需要设置高就行了
			params.height=holder.img.getWidth()*info.height/info.width;
			holder.img.setLayoutParams(params);
		}
		String uri = list.get(arg0);
		mbitmaputils.display(holder.img, uri,new BitmapLoadCallBack<ImageView>() {
			@Override
			public void onLoadCompleted(ImageView view, String arg1, Bitmap arg2, BitmapDisplayConfig arg3,
					BitmapLoadFrom arg4) {
				if(!infos.containsKey(arg0)){
					infos.put(arg0, new BitmapInfo(arg2.getWidth(), arg2.getHeight()));
					LayoutParams params=(LayoutParams) view.getLayoutParams();
					BitmapInfo info= infos.get(arg0);
					params.height=view.getWidth()*info.height/info.width;
					view.setLayoutParams(params);
				}
				view.setImageBitmap(arg2);
			}
			@Override
			public void onLoadFailed(ImageView arg0, String arg1, Drawable arg2) {
			}
		});
		return arg1;
	}
	
	private class ViewHolder {
		private ImageView img;
	}

	private HashMap<Integer,BitmapInfo> infos=new HashMap<Integer,BitmapInfo>();
//	构建每个图片的宽高
	private class BitmapInfo {
		int width,height;

		public BitmapInfo(int width, int height) {
			super();
			this.width = width;
			this.height = height;
		}
	}
}
