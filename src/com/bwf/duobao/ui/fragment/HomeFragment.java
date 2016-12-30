package com.bwf.duobao.ui.fragment;

import java.util.ArrayList;
import java.util.List;

import com.bwf.duobao.Constants;
import com.bwf.duobao.R;
import com.bwf.duobao.adapter.GridView_Adapter_Home;
import com.bwf.duobao.adapter.ViewPager_Adapter_Home;
import com.bwf.duobao.entity.Channel;
import com.bwf.duobao.entity.GoodsItem;
import com.bwf.duobao.entity.ResponseChannel;
import com.bwf.duobao.entity.ResponseGoodsItem;
import com.bwf.duobao.ui.activity.CategoryActivity;
import com.bwf.duobao.ui.activity.DetailMessageActivity;
import com.bwf.duobao.ui.activity.Goods_ListView_Activity;
import com.bwf.duobao.ui.activity.QuestionActivity;
import com.bwf.duobao.ui.activity.SearchActivity;
import com.bwf.duobao.ui.view.GridViewWithHeaderAndFooter;
import com.bwf.duobao.ui.view.LoadingView;
import com.bwf.duobao.utils.MyHttpUtils;
import com.bwf.duobao.utils.UrlHandler;
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lidroid.xutils.view.annotation.event.OnItemClick;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class HomeFragment extends LazyFragment{
	private boolean isPrepared;
	private List<GoodsItem> list;
	private List<GoodsItem> allmessagelist;
	@ViewInject(R.id.gridView)
	private GridViewWithHeaderAndFooter mGridView;
	@ViewInject(R.id.viewPager)
	private ViewPager mHeadViewPager;
	@ViewInject(R.id.loadingview)
	private LoadingView mLoadingView;
	@OnClick(R.id.search)
	private void onclick(View view){
		startActivity(new Intent(getActivity(), SearchActivity.class));
	}
	
	@ViewInject(R.id.indicatorContainer)
	private LinearLayout mHeadIndicatorContainer;
	/**作为指示器的那些小圆点**/
	private ImageView[] mIndicators;
	private HttpUtils mHttpUtils;
	private GridView_Adapter_Home mGridAdapter;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		isPrepared=true;
		return inflater.inflate(R.layout.fragment_home, null);
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initViews();
		allmessagelist=new ArrayList<GoodsItem>();
//		帧动画
		ImageView img=(ImageView) getActivity().findViewById(R.id.anim);
		AnimationDrawable drawable = (AnimationDrawable) img.getBackground();
		drawable.start();

		//初始化HttpUtils
		mHttpUtils = MyHttpUtils.getMyHttpUtils();
		initDatas();
	}
	/**
	 * 初始化所有控件
	 */
	private void initViews() {
		ViewUtils.inject(this, getView());
		mGridView.addHeaderView(getHeader1());
		mGridView.addHeaderView(getHeader2());
		mGridView.addHeaderView(getHeader3());
		mGridView.addFooterView(getLoadMoreFooterView());
		
		
		List<GoodsItem> list = new ArrayList<GoodsItem>();
		mGridAdapter = new GridView_Adapter_Home(list , getActivity());
		mGridView.setAdapter(mGridAdapter);
		mGridView.setOnScrollListener(onScrollListener);
	}
	@ViewInject(R.id.categoryLayout)
	private View mHeadCategoryLayout;
	@ViewInject(R.id.tenLayout)
	private View mHeadTenLayout;
	@ViewInject(R.id.questionLayout)
	private View mHeadQuestionLayout;
//	点击跳转到分类的界面
	@OnClick(R.id.categoryLayout)
	public void onClick(View view){
		startActivity(new Intent(getActivity(), CategoryActivity.class));
	}

//	点击跳转到十元专区
	@OnClick(R.id.tenLayout)
	public void onclick2(View view){
		Intent intent=new Intent(getActivity(), Goods_ListView_Activity.class);
		intent.putExtra("categoryId", 1);
		intent.putExtra("categoryName", "十元专区");
		startActivity(intent);
	}
	
//	点击调到百元专区
	@OnClick(R.id.baiyuanzhuanqu)
	public void onclick3(View view){
		Intent intent=new Intent(getActivity(), Goods_ListView_Activity.class);
		intent.putExtra("categoryId", 100);
		intent.putExtra("categoryName", "百元专区");
		startActivity(intent);
	}
	
//	点击跳转到常见问题的界面
	@OnClick(R.id.questionLayout)
	public void onClick1(View view){
		Intent intent=new Intent(getActivity(), QuestionActivity.class);
		startActivity(intent);
	}
	
//	点击item  进入详情页面
	@OnItemClick(R.id.gridView)
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
//		携带数据跳转
		Intent intent=new Intent(getActivity(),DetailMessageActivity.class);
		long period = allmessagelist.get((int)arg3).getPeriod();
		intent.putExtra("period", period);
		Log.d("发送：period:", period+"");
		startActivity(intent);
	}
	private View getHeader2() {
		View view = LayoutInflater.from(getActivity()).inflate(R.layout.v_home_header2, null);
		ViewUtils.inject(this, view);
		return view;
	}
	
	
	@ViewInject(R.id.hotCheckedTv)
	private CheckedTextView mHotCheckedTv;
	@ViewInject(R.id.totalTimesCheckedTv)
	private CheckedTextView mTotalTimesCheckedTv;

	private View getHeader3() {
		View view = LayoutInflater.from(getActivity()).inflate(R.layout.v_home_header3, null);
		ViewUtils.inject(this, view);//初始化头部视图里的控件
		mHotCheckedTv.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(isLoadDatas){
					return;
				}
				mTotalTimesCheckedTv.setChecked(false);
				mHotCheckedTv.setChecked(true);
				mTotalTimesCheckedTv.setCompoundDrawablesWithIntrinsicBounds(
						0, 0, R.drawable.ic_home_total_disable, 0);
				currentTag = TAG_HOT;
				nextPage = 1;
				mGridAdapter.clearDatas();
				loadGoodsDatas();
			}
		});
		mTotalTimesCheckedTv.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(isLoadDatas){
					return;
				}
				switch (currentTag) {
				case TAG_HOT:
				case TAG_TOTAL_TIMES_ASC:
					mTotalTimesCheckedTv.setChecked(true);
					mHotCheckedTv.setChecked(false);
					currentTag = TAG_TOTAL_TIMES_DESC;
					mTotalTimesCheckedTv.setCompoundDrawablesWithIntrinsicBounds(
							0, 0, R.drawable.ic_home_total_down, 0);
					break;
				case TAG_TOTAL_TIMES_DESC:
					mTotalTimesCheckedTv.setChecked(true);
					mHotCheckedTv.setChecked(false);
					currentTag = TAG_TOTAL_TIMES_ASC;
					mTotalTimesCheckedTv.setCompoundDrawablesWithIntrinsicBounds(
							0, 0, R.drawable.ic_home_total_up, 0);
					break;
				}
				nextPage = 1;
				mGridAdapter.clearDatas();
				loadGoodsDatas();
			}
		});
		return view;
	}
	/**为GridView设置的 滚动监听器**/
	private OnScrollListener onScrollListener = new OnScrollListener() {
		@Override
		public void onScrollStateChanged(AbsListView view, int scrollState) {

		}
		@Override
		public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
			if(lastVisibleItem == visibleItemCount+firstVisibleItem-1){
				return;
			}
			lastVisibleItem = visibleItemCount+firstVisibleItem-1;
			if(!isLoading && firstVisibleItem + visibleItemCount == totalItemCount){
				//满足条件后，加载下一页数据
				loadGoodsDatas();
			}
		}
	};
	/**最后一个可见item的位置**/
	private int lastVisibleItem = 0;
	private boolean isLoading = true;
	@ViewInject(R.id.v_loadmore_progressBar)
	private ProgressBar mLoadmoreProgressBar;
	@ViewInject(R.id.v_loadmore_textView)
	private TextView mLoadmoreTv;
	@ViewInject(R.id.v_loadmore_failedTv)
	private TextView mLoadmoreFailedTv;
	/**
	 * 初始化并返回加载更多的那个脚视图
	 * @return
	 */
	private View getLoadMoreFooterView() {
		View mFooterView = LayoutInflater.from(getActivity()).inflate(R.layout.v_loadmore, null);
		ViewUtils.inject(this, mFooterView);
		mLoadmoreFailedTv.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				mLoadmoreFailedTv.setVisibility(View.GONE);
				loadGoodsDatas();
			}
		});
		return mFooterView;
	}
	/**
	 * 初始化数据
	 */
	private void initDatas() {
		initViewpagerHeadDatas();
		loadGoodsDatas();
	}
	private int nextPage = 1;
	private static final int TAG_HOT = 1;
	private static final int TAG_TOTAL_TIMES_ASC = 2;//升序
	private static final int TAG_TOTAL_TIMES_DESC = 3; //降序
	private int currentTag = TAG_HOT;
	/**
	 * 根据当前标签获取对应的接口地址(人气、总需次数)
	 * @return
	 */
	private String getUrl(){
		switch (currentTag) {
		case TAG_HOT:
			return UrlHandler.handlUrl(Constants.URL_HOME_GOODS_BY_HOT, nextPage,20);
		case TAG_TOTAL_TIMES_ASC:
			return UrlHandler.handlUrl(Constants.URL_HOME_GOODS_BY_TIMES_ASC, nextPage,20);
		case TAG_TOTAL_TIMES_DESC:
			return UrlHandler.handlUrl(Constants.URL_HOME_GOODS_BY_TIMES_DESC, nextPage,20);
		}
		return UrlHandler.handlUrl(Constants.URL_HOME_GOODS_BY_HOT, nextPage,20);
	}
	private boolean isLoadDatas;
	/**
	 * 加载商品的数据
	 */
	private void loadGoodsDatas() {
		isLoading = true;
		isLoadDatas = true;
		mLoadmoreFailedTv.setVisibility(View.GONE);
		String url = getUrl();
		Log.d("", "----------->开始加载第"+nextPage+"页数据");
		mHttpUtils.send(HttpMethod.GET, url, new RequestCallBack<String>() {

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				//加载失败怎么处理
				if(nextPage != 1){
					mLoadmoreFailedTv.setVisibility(View.VISIBLE);
				}
				isLoading = false;
				isLoadDatas = false;
			}
			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				String content = arg0.result;
				//用Gson解析服务器返回的json数据  填充gridview
				try{
					ResponseGoodsItem responseGoodsItem = new Gson().fromJson(content, ResponseGoodsItem.class);
					list = responseGoodsItem.getResult().list;
//					将所有的list集合加进数据
					allmessagelist.addAll(list);
					mGridAdapter.addDatas(list);
					nextPage++;
					isLoading = false;
					isLoadDatas = false;
					//判断数据是否已获取到所有数据
					if(responseGoodsItem.getResult().totalCnt <=  mGridAdapter.getCount()){
						mLoadmoreProgressBar.setVisibility(View.GONE);
						mLoadmoreTv.setText("没有更多数据了!");
						isLoading = true;
					}
				}catch(Exception e){
					isLoading = false;
					isLoadDatas = false;
				}

			}
		});
	}
	/**
	 * 为头部视图里的ViewPager 加载数据
	 */
	private void initViewpagerHeadDatas() {
		String url ="http://123.56.145.151:8080/Duobao-XM/channel";
		mHttpUtils.send(HttpMethod.GET, url, new RequestCallBack<String>() {

			@Override
			public void onFailure(HttpException arg0, String arg1) {

			}

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				String content = arg0.result;
				setHeaderViewPager(content);
			}
		});
	}

	private int mCurPos;
	/**
	 * 用数据来填充ViewPager   
	 * @param content
	 */
	protected void setHeaderViewPager(String content) {
		ResponseChannel responseChannel = new Gson().fromJson(content, ResponseChannel.class);
		List<Channel> list = responseChannel.result.list;
		List<ImageView> imgviewList = new ArrayList<ImageView>();
		for (int i = 0; i < list.size(); i++) {
			ImageView imgView = new ImageView(getActivity());
			imgView.setScaleType(ScaleType.CENTER_CROP);
			imgviewList.add(imgView);
		}
		ViewPager_Adapter_Home adapter = new ViewPager_Adapter_Home(getActivity(), imgviewList, list);
		mHeadViewPager.setAdapter(adapter);

		mIndicators = new ImageView[list.size()];
		for (int i = 0; i < mIndicators.length; i++) {
			mIndicators[i] = new ImageView(getActivity());
			mIndicators[i].setImageResource(R.drawable.guide_indicator_unselected);
			mHeadIndicatorContainer.addView(mIndicators[i]);
			//布局参数，在使用的时候要根据父容器的类型来选择对应的LayoutParams
			LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, 
					LayoutParams.WRAP_CONTENT);
			params.rightMargin = 12;
			mIndicators[i].setLayoutParams(params);
		}
		//默认让第一个ImageView选中
		mIndicators[0].setImageResource(R.drawable.guide_indicator_selected);
		mHeadViewPager.setOnPageChangeListener(new OnPageChangeListener() {

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
	 * 获取第一个头部视图
	 * @return
	 */
	private View getHeader1(){
		View view = LayoutInflater.from(getActivity()).inflate(R.layout.v_home_header1, null);
		ViewUtils.inject(this, view);//初始化头部视图里的控件
		return view;
	}
	@Override
	protected void lazyload() {
		if(!isPrepared||!isVisible()){
			return;
		}
		loadGoodsDatas();
		Log.d("mes", "刷新夺宝数据");
	}
	@Override
	protected void hideload() {
		// TODO Auto-generated method stub
		
	}
	public class NetworkReceiver extends BroadcastReceiver{
		@Override
		public void onReceive(Context arg0, Intent arg1) {
			if(arg1.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)){
				ConnectivityManager manager=(ConnectivityManager) arg0.getSystemService(arg0.CONNECTIVITY_SERVICE);
				NetworkInfo activeNetworkInfo = manager.getActiveNetworkInfo();
				if(activeNetworkInfo!=null&&activeNetworkInfo.isAvailable()){
					int type=activeNetworkInfo.getType();
					if (type==ConnectivityManager.TYPE_WIFI) {
						Toast.makeText(getActivity(), "无线网操作", 0).show();
					}else if(type==ConnectivityManager.TYPE_MOBILE){
						Toast.makeText(getActivity(), "无线网操作", 0).show();
					}else if(type==ConnectivityManager.TYPE_ETHERNET){
						Toast.makeText(getActivity(), "以太网网操作", 0).show();
					}
					if(nextPage==1){
						initDatas();
					}
					isNoNetwork=false;
				}else{
					Toast.makeText(getActivity(), "无网络链接", 0).show();
					if(nextPage==1){
//						TODO
					}
					isNoNetwork=true;
				}
			}
		}
		
	}
	private boolean isNoNetwork;
	
}
