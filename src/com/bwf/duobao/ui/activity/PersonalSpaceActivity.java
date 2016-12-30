package com.bwf.duobao.ui.activity;

import java.util.ArrayList;
import java.util.List;

import com.bwf.duobao.R;
import com.bwf.duobao.adapter.ListView_Adapter_person1;
import com.bwf.duobao.adapter.ViewPager_Adapter_Person;
import com.bwf.duobao.instance.personalcenter.LuckInfo;
import com.bwf.duobao.instance.personalcenter.Personalitem;
import com.bwf.duobao.utils.BitMapHandler;
import com.bwf.duobao.utils.MyHttpUtils;
import com.google.gson.Gson;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.bitmap.BitmapDisplayConfig;
import com.lidroid.xutils.bitmap.callback.BitmapLoadCallBack;
import com.lidroid.xutils.bitmap.callback.BitmapLoadFrom;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.umeng.socialize.utils.Log;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class PersonalSpaceActivity extends Activity {
	private PagerAdapter adapter;
	private List<View> views;
	private HttpUtils mhttputil;
	private BitmapUtils mbitmaputils;
	private int uid;
	private String avatar;
	private String nickName;
	private ListView listview1,listview2;
	private ListView_Adapter_person1 adapter1;
	@ViewInject(R.id.viewpager)
	private ViewPager viewpager;
	// 头像 姓名
	@ViewInject(R.id.img)
	private ImageView img;
	@ViewInject(R.id.tv1)
	private TextView tv1;
	@ViewInject(R.id.tv2)
	private TextView tv2;

	@OnClick({ R.id.backBtn, R.id.img3 })
	public void click(View view) {
		switch (view.getId()) {
		case R.id.backBtn:
			onBackPressed();
			break;
		case R.id.img3:
			adapter1.notifyDataSetChanged();
			break;
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_personalspace);
		ViewUtils.inject(this);
		mhttputil = MyHttpUtils.getMyHttpUtils();
		mbitmaputils = new BitmapUtils(this);
		// 从上一个页面获得的三总数据
		Intent intent = getIntent();
		uid = intent.getIntExtra("uid", -1);
		avatar = intent.getStringExtra("avatar");
		nickName = intent.getStringExtra("nickName");
		Log.d("接收：uid", "" + uid);
		Log.d("接收：avatar", avatar);
		Log.d("接收：nickName", nickName);
		initViews();
	}

	/**
	 * 初始化viewpager listview 控件
	 */
	private void initViews() {
		// 在头像显示圆形的头像
		mbitmaputils.display(img, avatar, new BitmapLoadCallBack<ImageView>() {
			@Override
			public void onLoadCompleted(ImageView arg0, String arg1, Bitmap arg2, BitmapDisplayConfig arg3,
					BitmapLoadFrom arg4) {
				Bitmap bitmap = BitMapHandler.createCircleBitmap(arg2);
				arg0.setImageBitmap(bitmap);
			}

			@Override
			public void onLoadFailed(ImageView arg0, String arg1, Drawable arg2) {
			}
		});
		// 设置名称
		tv1.setText(nickName);
		// 设置ID
		tv2.setText("ID:" + uid);
		LayoutInflater inflater = LayoutInflater.from(this);
		// 像ViewPager 里面添加View
		views = new ArrayList<View>();
		View view1 = inflater.inflate(R.layout.viewpager_personal_1, null);
		listview1 = (ListView) view1.findViewById(R.id.listview_personal_1);
		
		View view2 = inflater.inflate(R.layout.viewpager_personal_1, null);
		listview2 = (ListView) view2.findViewById(R.id.listview_personal_1);

		View view3 = inflater.inflate(R.layout.viewpager_personal_2, null);
		View view4 = inflater.inflate(R.layout.viewpager_personal_2, null);

		views.add(view1);
		views.add(view2);
		views.add(view3);
		views.add(view4);
		initDatas();
	}

	/**
	 * 设置数据
	 */
	private void initDatas() {
		// viewpager 设置adapter
		adapter = new ViewPager_Adapter_Person(views);
		viewpager.setAdapter(adapter);

		// 处理ListView 第一个 从网络获取数据
		String url = "http://123.56.145.151:8080/Duobao-XM/duobao_record?searchUid=" + uid + "&pageNum=1&pageSize=20";
		mhttputil.send(HttpMethod.GET, url, new RequestCallBack<String>() {

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				Toast.makeText(PersonalSpaceActivity.this, "请求数据失败", 0).show();
			}

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				String result = arg0.result;
				Personalitem fromJson = new Gson().fromJson(result, Personalitem.class);
				ArrayList<LuckInfo> list = fromJson.getResult().getList();
				int uid2 = fromJson.getResult().getUid();
				setadapter(list, uid2);
			}
		});

	}

	/**
	 * 第一个Listview 设置adapter
	 * 
	 * @param list
	 */
	protected void setadapter(final ArrayList<LuckInfo> list, int uid) {
		adapter1 = new ListView_Adapter_person1(list, PersonalSpaceActivity.this, uid);
		listview1.setAdapter(adapter1);
		
		
//		点击事件被屏蔽
		listview1.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				if(arg3<0){
//					如果小于零  ，就不做处理
					return;
				}
				
				Toast.makeText(PersonalSpaceActivity.this, (int) arg3+"", 0).show();
				Intent intent = new Intent(PersonalSpaceActivity.this, DetailMessageActivity.class);
				LuckInfo luckInfo = list.get((int) arg3);
				long period = luckInfo.getPeriod();
				intent.putExtra("period", period);
				int status = luckInfo.getStatus();
				
				Integer tag=-1;
//				如果未开奖   则可以购买  默认值
				if (status == 0) {
					 tag = 0;
//					如果已经开奖  则跳到下一次购买
				} else if (status == 2) {
					 tag = 1;
				}else{
					return;
				}
				intent.putExtra("tag", tag);
				Log.d("发送：period:", "" + period);
				Log.d("发送：tag:", "" + tag);
				startActivity(intent);
			}
		});
	}
}
