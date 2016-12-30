package com.bwf.duobao.ui.activity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.bwf.duobao.R;
import com.bwf.duobao.adapter.ListView_Adapter_Guest;
import com.bwf.duobao.adapter.ViewPager_Adapter_Detail;
import com.bwf.duobao.application.MyApp;
import com.bwf.duobao.instance.gid_period.GidPeriod;
import com.bwf.duobao.instance.goodsdetails.Gooditem;
import com.bwf.duobao.instance.goodsdetails.LuckUser;
import com.bwf.duobao.instance.goodsdetails.Result;
import com.bwf.duobao.instance.guest.GuestItems;
import com.bwf.duobao.instance.guest.User;
import com.bwf.duobao.instance.guest.Userinfo;
import com.bwf.duobao.utils.DownCountTime;
import com.bwf.duobao.utils.MyHttpUtils;
import com.bwf.duobao.utils.TimeFormat;
import com.google.gson.Gson;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class DetailMessageActivity extends Activity implements OnClickListener {
	private BitmapUtils mbitmaputils;
	private Timer mtimer;
	private TimerTask mtask;
	private Intent intent;
	private int remainTime;
	private long period;
	private int goodsid;
	private int remainTimes;
	private EditText et;
	private TextView tv_total_money;
	private PopupWindow window;
	private ArrayList<Userinfo> all_list;
	private ArrayList<Userinfo> list;
	private ViewPager_Adapter_Detail adapter;
	// 用来判定是否为第二种情况发送过来的
	private int tag;
	private LinearLayout linearlayout1, linearlayout2;
	private Button btn1, btn2, btn3;
	private Handler handler = new Handler(new Handler.Callback() {
		@Override
		public boolean handleMessage(Message arg0) {
			header2tv2.setText("剩余时间： " + arg0.obj);
			// Log.d("剩余时间： ",arg0.obj+"");
			return false;
		}
	});
	@ViewInject(R.id.listview)
	private ListView listview;
	@ViewInject(R.id.header1)
	private LinearLayout header1;
	@ViewInject(R.id.header2)
	private RelativeLayout header2;
	@ViewInject(R.id.header3)
	private LinearLayout header3;

	@ViewInject(R.id.header2tv1)
	private TextView header2tv1;
	@ViewInject(R.id.header2tv2)
	private TextView header2tv2;

	@ViewInject(R.id.header3img)
	private ImageView header3img;
	@ViewInject(R.id.header3tv1)
	private TextView header3tv1;
	@ViewInject(R.id.header3tv2)
	private TextView header3tv2;
	@ViewInject(R.id.header3tv3)
	private TextView header3tv3;
	@ViewInject(R.id.header3tv4)
	private TextView header3tv4;
	@ViewInject(R.id.header3tv5)
	private TextView header3tv5;
	@ViewInject(R.id.header3tv6)
	private TextView header3tv6;
	@ViewInject(R.id.header3tv7)
	private TextView header3tv7;

	@ViewInject(R.id.backBtn1)
	private ImageView img;

	@OnClick({ R.id.backBtn1, R.id.tvpicturedetails, R.id.tvwangqijiexiao, R.id.header3tv8, R.id.bt1, R.id.bt2,
			R.id.bt3, R.id.img3 })
	public void onclick(View view) {
		switch (view.getId()) {
		// 返回尖按钮
		case R.id.backBtn1:
			stoptask();
			onBackPressed();
			break;
		// 图片详情 找到图文详情按钮 点击进行跳转到新的明细界面 《八个图片加文字》
		case R.id.tvpicturedetails:
			Intent intent = new Intent(DetailMessageActivity.this, PictureDetailsActivity.class);
			intent.putExtra("goodsid", goodsid);
			Log.d("发送goodsId:", goodsid + "");
			// 停止计时
			stoptask();
			startActivity(intent);
			break;
		// 往期揭晓
		case R.id.tvwangqijiexiao:
			intent = new Intent(DetailMessageActivity.this, WangqijiexiaoActivity.class);
			startActivity(intent);
			break;
		// 计算结果
		case R.id.header3tv8:
			intent = new Intent(DetailMessageActivity.this, JisuanjieguoActivity.class);
			intent.putExtra("period", period);
			Log.d("发送period", "" + period);
			startActivity(intent);
			break;
		// 立即夺宝
		case R.id.bt1:
			window = new PopupWindow(android.view.WindowManager.LayoutParams.MATCH_PARENT,
					android.view.WindowManager.LayoutParams.WRAP_CONTENT);
			View contentView = LayoutInflater.from(DetailMessageActivity.this).inflate(R.layout.v_popupwindow, null);
			window.setContentView(contentView);
			// 设置焦点
			window.setFocusable(true);
			// 设置其他地方点击可以消失
			Bitmap bitmap = null;
			window.setBackgroundDrawable(new BitmapDrawable(getResources(), bitmap));
//			设置显示，退出动画
			window.setAnimationStyle(R.style.pop_anim_style);
			// 显示window
			window.showAtLocation(view, Gravity.BOTTOM, 0, 0);

			// 找到编辑框
			et = (EditText) contentView.findViewById(R.id.et);
			// 设置多少钱
			tv_total_money = (TextView) contentView.findViewById(R.id.tv_total_money);
			// 点击叉叉消失
			contentView.findViewById(R.id.back_btn).setOnClickListener(this);
			// 减号点击
			contentView.findViewById(R.id.minus_window).setOnClickListener(this);
			// 加号点击
			contentView.findViewById(R.id.add_window).setOnClickListener(this);
			// 选数量
			contentView.findViewById(R.id.tv_five).setOnClickListener(this);
			contentView.findViewById(R.id.tv_twenty).setOnClickListener(this);
			contentView.findViewById(R.id.tv_fifty).setOnClickListener(this);
			contentView.findViewById(R.id.tv_hundred).setOnClickListener(this);
			// 立即夺宝
			contentView.findViewById(R.id.tv_duobao).setOnClickListener(this);
			

			break;
		// 加入清单
		case R.id.bt2:
			ArrayList<Long> list_period = new ArrayList<Long>();
			list_period.add(period);
			ArrayList<Integer> list_times = new ArrayList<Integer>();
			list_times.add(1);
			MyApp.addToList(this, list_period, list_times);
			break;
		// 立即前往新一期
		case R.id.bt3:
			String url = "http://123.56.145.151:8080/Duobao-XM/latest-period?gid=" + goodsid;
			mHttpUtils.send(HttpMethod.GET, url, new RequestCallBack<String>() {
				@Override
				public void onFailure(HttpException arg0, String arg1) {
					Toast.makeText(DetailMessageActivity.this, "解析数据错误", 0).show();
				}

				@Override
				public void onSuccess(ResponseInfo<String> arg0) {
					String result2 = arg0.result;
					GidPeriod fromJson = new Gson().fromJson(result2, GidPeriod.class);
					period = fromJson.getResult().getPeriod();
					Log.d("发送period", "" + period);

					Intent intent = new Intent(DetailMessageActivity.this, DetailMessageActivity.class);
					intent.putExtra("period", period);
					startActivity(intent);
				}
			});
			break;
		// 分享
		case R.id.img3:
			// 得到图片的 umengimage
			UMImage image = new UMImage(this, R.drawable.ic_shit);
			// new
			// ShareAction(this).setPlatform(SHARE_MEDIA.WEIXIN).setCallback(umShareListener).withText("hello
			// 夺宝神器")
			// .withTargetUrl("http://www.baidu.com").withMedia(image).share();
			// 集成的分享
			final SHARE_MEDIA[] displaylist = new SHARE_MEDIA[] { SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE,
					SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE };
			new ShareAction(this).setDisplayList(displaylist).withText("想不想打开嘛？？").withTitle("辣鸡   有盐有味")
					.withTargetUrl("http://www.baidu.com").withMedia(image).setListenerList(umShareListener).open();
			break;
		}
	}

	UMShareListener umShareListener = new UMShareListener() {

		public void onResult(SHARE_MEDIA platform) {
			Toast.makeText(DetailMessageActivity.this, platform + " 分享成功啦", Toast.LENGTH_SHORT).show();
		}

		@Override
		public void onError(SHARE_MEDIA platform, Throwable t) {
			Toast.makeText(DetailMessageActivity.this, platform + " 分享失败啦", Toast.LENGTH_SHORT).show();
		}

		@Override
		public void onCancel(SHARE_MEDIA platform) {
			Toast.makeText(DetailMessageActivity.this, platform + " 分享取消了", Toast.LENGTH_SHORT).show();
		}
	};

	// 分享重写
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
	}

	private ViewPager viewpager;
	private View headerview;
	// 容器
	private LinearLayout container;
	private ImageView[] mIndicators;
	private HttpUtils mHttpUtils;
	private BitmapUtils mBitmapUtils;
	private TextView tv1, tv2, tv3, tv4, tv5, tv6, tv7, clickbtn;
	private ProgressBar pb;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detailmessage);

		ViewUtils.inject(this);
		intent = getIntent();
		// 头部图片 viewpager 及相关信息
		period = intent.getLongExtra("period", 11);
		if(period==11){
			return;
		}
		tag = intent.getIntExtra("tag", 0);
		Log.d("接收：period:", "" + period);
		Log.d("接收：tag:", "" + tag);

		mHttpUtils = MyHttpUtils.getMyHttpUtils();
		mBitmapUtils = new BitmapUtils(this);
		all_list = new ArrayList<Userinfo>();
		initViews();
	}

	/**
	 * 为listview 进行数据处理 评论
	 */
	private void initListviewDatas() {
		String url = "http://123.56.145.151:8080/Duobao-XM/record-list?period=" + period
				+ "&pageNum=1&pageSize=10&since=2466543468710";
		mHttpUtils.send(HttpMethod.GET, url, new RequestCallBack<String>() {
			@Override
			public void onFailure(HttpException arg0, String arg1) {
				Toast.makeText(DetailMessageActivity.this, "请求数据失败~~~", 1).show();
			}

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				String result = arg0.result;
				setlistview(result);

			}
		});
	}

	/**
	 * 处理listview 参与记录
	 * 
	 * @param result
	 * @param detailMessageActivity
	 */
	private void setlistview(String result) {
		GuestItems guestItems = new Gson().fromJson(result, GuestItems.class);
		list = guestItems.getResult().getList();
		// 在或得数据后点击查看夺宝记录
		clickbtn = (TextView) headerview.findViewById(R.id.clickbtn);
		clickbtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (list == null) {
					Toast.makeText(DetailMessageActivity.this, "list为空", 0).show();
					return;
				}
				clickbtn.setVisibility(View.GONE);
				tv7.setVisibility(View.VISIBLE);
				all_list.addAll(list);
				adapter.notifyDataSetChanged();
			}
		});
	}

	/**
	 * 初始化控件 添加头部视图
	 */
	private void initViews() {
		// 添加头部视图 让图片进行滑动 和显示一些配置信息
		headerview = LayoutInflater.from(this).inflate(R.layout.activity_detailmessage_headerview1, null);
		listview.addHeaderView(headerview);
		ViewUtils.inject(this, headerview);

		viewpager = (ViewPager) headerview.findViewById(R.id.viewPager);
		container = (LinearLayout) headerview.findViewById(R.id.container);
		tv1 = (TextView) headerview.findViewById(R.id.tv1);
		tv2 = (TextView) headerview.findViewById(R.id.tv2);
		tv3 = (TextView) headerview.findViewById(R.id.tv3);
		tv4 = (TextView) headerview.findViewById(R.id.tv4);
		tv5 = (TextView) headerview.findViewById(R.id.tv5);
		tv6 = (TextView) headerview.findViewById(R.id.tv6);
		tv7 = (TextView) headerview.findViewById(R.id.tv7);
		pb = (ProgressBar) headerview.findViewById(R.id.pb);

		ListAdapter adapter = new ListView_Adapter_Guest(all_list, DetailMessageActivity.this);
		listview.setAdapter(adapter);
		// 跳转到个人中心页面
		listview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				Intent intent = new Intent(DetailMessageActivity.this, PersonalSpaceActivity.class);
				User user = all_list.get((int) arg3).getUser();
				int uid = user.getUid();
				String avatar = user.getAvatar();
				String nickName = user.getNickName();
				intent.putExtra("uid", uid);
				intent.putExtra("avatar", avatar);
				intent.putExtra("nickName", nickName);
				Log.d("发送：uid", "" + uid);
				Log.d("发送：avatar", avatar);
				Log.d("发送：nickName", nickName);
				startActivity(intent);
			}
		});

		// 如果获取到默认的TAG 就是默认的第一种效果
		linearlayout1 = (LinearLayout) findViewById(R.id.ll1);
		linearlayout2 = (LinearLayout) findViewById(R.id.ll2);
		if (tag == 0) {
			linearlayout1.setVisibility(View.VISIBLE);
			linearlayout2.setVisibility(View.GONE);
			btn1 = (Button) findViewById(R.id.bt1);
			btn2 = (Button) findViewById(R.id.bt2);
			// 如果是接受到传过来的TAG 1 就把第一种效果隐藏 第二种效果显示
		} else if (tag == 1) {
			linearlayout1.setVisibility(View.GONE);
			linearlayout2.setVisibility(View.VISIBLE);
			btn3 = (Button) findViewById(R.id.bt3);
		}
		// 在所有的控件都找到后进行数据设置
		initViewPagerDatas();
		initListviewDatas();
	}

	private String result;

	/**
	 * 为viewpager加载数据
	 */
	private void initViewPagerDatas() {
		String url = "http://123.56.145.151:8080/Duobao-XM/period-detail?period=" + period;
		mHttpUtils.send(HttpMethod.GET, url, new RequestCallBack<String>() {
			@Override
			public void onFailure(HttpException arg0, String arg1) {
				Toast.makeText(DetailMessageActivity.this, "请求数据失败~~~", 1).show();
			}

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				result = arg0.result;
				setviewpager(result, DetailMessageActivity.this);
				setheader(result);
			}
		});

	}

	/**
	 * 初始话头部数据
	 * 
	 * @param result
	 */
	protected void setheader(String result) {
		Result items = new Gson().fromJson(result, Gooditem.class).getResult();
		// 得到good id 进行传递
		goodsid = items.getGoodsId();
		remainTimes = items.getRemainTimes();
		// Log.dln("result"+result);
		tv1.setText(items.getGoodsName());
		tv2.setText(items.getDescription());

		if (items.getStatus() == 0) {
			showheader1(items);
		} else if (items.getStatus() == 1) {
			showheader2(items);
		} else if (items.getStatus() == 2) {
			showheader3(items);
		}

		// 将得到的时间进行按格式转化 设置到相依文本
		String timeformat = TimeFormat.timeformat("" + items.getStartDuobaoTime(), "yyyy-MM-dd HH:mm:ss");
		tv6.setText("自" + timeformat + "开始");

		// 为评论上面 显示小圆日期 2016-06-24
		String timeformat2 = TimeFormat.timeformat("" + new Date().getTime(), "yyyy-MM-dd");
		tv7.setText(timeformat2);
		pb.setProgress(100 - (items.getRemainTimes() * 100) / (items.getTotalTimes()));
	}

	private int mCurPos;
	private LuckUser luckUser;

	/**
	 * viewpager 设置图片 自动跳转 画小圆点 为产品的属性 设置文字
	 * 
	 * @param result
	 * @param context
	 */
	protected void setviewpager(String result, Context context) {
		Result items = new Gson().fromJson(result, Gooditem.class).getResult();
		// 得到图片地址
		ArrayList<String> list = items.getBigImgs();
		List<ImageView> imgviewList = new ArrayList<ImageView>();
		for (int i = 0; i < list.size(); i++) {
			ImageView view = new ImageView(DetailMessageActivity.this);
			imgviewList.add(view);
		}
		adapter = new ViewPager_Adapter_Detail(context, imgviewList, list);
		viewpager.setAdapter(adapter);
		// 在每次进去的时候 移除之前所有的原点
		container.removeAllViews();
		mIndicators = new ImageView[list.size()];
		for (int i = 0; i < mIndicators.length; i++) {
			mIndicators[i] = new ImageView(context);
			mIndicators[i].setImageResource(R.drawable.guide_indicator_unselected);
			container.addView(mIndicators[i]);
			// 布局参数，在使用的时候要根据父容器的类型来选择对应的LayoutParams
			LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			params.rightMargin = 12;
			mIndicators[i].setLayoutParams(params);
		}
		// 默认让第一个ImageView选中
		mIndicators[0].setImageResource(R.drawable.guide_indicator_selected);
		viewpager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				mIndicators[mCurPos].setImageResource(R.drawable.guide_indicator_unselected);
				mIndicators[position].setImageResource(R.drawable.guide_indicator_selected);
				mCurPos = position;
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});
	}

	/**
	 * 显示第三种头
	 * 
	 * @param items
	 */
	private void showheader3(Result items) {
		mbitmaputils = new BitmapUtils(this);
		header1.setVisibility(View.GONE);
		header2.setVisibility(View.GONE);
		header3.setVisibility(View.VISIBLE);
		luckUser = items.getLuckUser();
		// 设置头像
		mbitmaputils.display(header3img, luckUser.getAvatar());
		header3tv1.setText("获奖者:" + luckUser.getNickname());
		header3tv2.setText("(" + luckUser.getIPAddress() + ")");
		header3tv3.setText("用户ID:" + luckUser.getUid());
		header3tv4.setText("期号:" + items.getPeriod());

		SpannableString str = new SpannableString("" + luckUser.getDuobaoTimes());
		str.setSpan(new ForegroundColorSpan(Color.RED), 0, str.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		header3tv5.setText("本次参与:" + str + "人次");
		header3tv6.setText("揭晓时间:" + TimeFormat.timeformat(items.getRevealedTime(), "yyyy-MM-dd HH:mm:ss.mmm"));
		header3tv7.setText("幸运号码:" + items.getLuckCode());
	}

	/**
	 * 显示第二种头
	 * 
	 * @param items
	 */
	private void showheader2(Result items) {
		header1.setVisibility(View.GONE);
		header2.setVisibility(View.VISIBLE);
		header2tv1.setText("期号：" + items.getPeriod());
		remainTime = items.getRemainTime();
		starttask();
	}

	/**
	 * 显示第一种头
	 * 
	 * @param items
	 */
	private void showheader1(Result items) {
		header1.setVisibility(View.VISIBLE);
		tv3.setText("期号：" + items.getPeriod());
		tv4.setText("总需" + items.getTotalTimes() + "人次");
		tv5.setText(items.getRemainTimes() + "");
	}

	/**
	 * 重复性的任务 减时间
	 */
	private void starttask() {
		mtimer = new Timer();

		mtask = new TimerTask() {
			@Override
			public void run() {
				remainTime -= 1;
				if (remainTime <= 0) {
					stoptask();
					// 重新获取数据
					initViewPagerDatas();
				}
				String timeres = DownCountTime.getremaintime(remainTime);
				handler.sendMessage(handler.obtainMessage(1, timeres));
			}
		};
		// 每一秒执行一次
		mtimer.schedule(mtask, 0, 1);
	}

	/**
	 * 取消计时任务
	 */
	private void stoptask() {
		if (mtask != null) {
			mtask.cancel();
			mtask = null;
		}
	}

	// popuwindow 的监听
	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.back_btn:
			window.dismiss();
			break;
		case R.id.minus_window:
			int result = Integer.parseInt(et.getText().toString()) - 1;
			if (result >= 0) {
				et.setText(result + "");
				et.startAnimation(AnimationUtils.loadAnimation(DetailMessageActivity.this,R.anim.text_change));
				tv_total_money.setText("共 "+result+"元");
			} else {
				Toast.makeText(DetailMessageActivity.this, "不能是负数", 0).show();
				return;
			}
			break;
		case R.id.add_window:
			result=Integer.parseInt(et.getText().toString()) + 1;
			et.setText( result+ "");
			tv_total_money.setText("共 "+result+"元");
			et.startAnimation(AnimationUtils.loadAnimation(DetailMessageActivity.this,R.anim.text_change));
			break;
		case R.id.tv_five:
			et.setText("5");
			et.startAnimation(AnimationUtils.loadAnimation(DetailMessageActivity.this,R.anim.text_change));
			tv_total_money.setText("共 5元");
			break;
		case R.id.tv_twenty:
			et.setText("20");
			et.startAnimation(AnimationUtils.loadAnimation(DetailMessageActivity.this,R.anim.text_change));
			tv_total_money.setText("共 20元");
			break;
		case R.id.tv_fifty:
			et.setText("50");
			et.startAnimation(AnimationUtils.loadAnimation(DetailMessageActivity.this,R.anim.text_change));
			tv_total_money.setText("共 50元");
			break;
		case R.id.tv_hundred:
			et.setText("100");
			et.startAnimation(AnimationUtils.loadAnimation(DetailMessageActivity.this,R.anim.text_change));
			tv_total_money.setText("共 100元");
			break;
		case R.id.tv_duobao:
			ArrayList<Long> list_periods = new ArrayList<Long>();
			list_periods.add(period);
			ArrayList<Integer> list_times = new ArrayList<Integer>();
			list_times.add(Integer.parseInt(et.getText().toString()));
			MyApp.addToList(DetailMessageActivity.this, list_periods, list_times);
			window.dismiss();
			break;
		}
	}
}
