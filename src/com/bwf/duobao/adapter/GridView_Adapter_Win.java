package com.bwf.duobao.adapter;

import java.util.Date;
import java.util.List;

import com.bwf.duobao.R;
import com.bwf.duobao.entity.GoodsItem;
import com.bwf.duobao.instance.ResponseRevealedPeriodInfo2.ResponseRevealedPeriodInfo2;
import com.bwf.duobao.ui.view.CutdownTextView;
import com.bwf.duobao.ui.view.CutdownTextView.OnCountDownFinishListener;
import com.bwf.duobao.utils.MyHttpUtils;
import com.bwf.duobao.utils.TimeFormat;
import com.google.gson.Gson;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class GridView_Adapter_Win extends BaseAdapter {
	private List<GoodsItem> list;
	private LayoutInflater inflater;
	private BitmapUtils mbitmaputils;
	private HttpUtils mHttpUtils;

	public GridView_Adapter_Win(List<GoodsItem> list, Context context) {
		super();
		this.list = list;
		inflater = LayoutInflater.from(context);
		mbitmaputils = new BitmapUtils(context);
		mHttpUtils = MyHttpUtils.getMyHttpUtils();
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public GoodsItem getItem(int arg0) {
		return list.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(final int arg0, View arg1, ViewGroup arg2) {
		ViewHolder holder = null;
		int itemViewType = getItemViewType(arg0);
		if (arg1 == null) {
			holder = new ViewHolder();
			switch (itemViewType) {
			case 0:
				arg1 = inflater.inflate(R.layout.activity_winframent_status1, null);
				holder.img1 = (ImageView) arg1.findViewById(R.id.s1img1);
				holder.img2 = (ImageView) arg1.findViewById(R.id.s1img2);
				holder.tv1 = (TextView) arg1.findViewById(R.id.s1tv1);
				holder.tv2 = (TextView) arg1.findViewById(R.id.s1tv2);
				holder.cutdownview = (CutdownTextView) arg1.findViewById(R.id.s1tv3);
				break;
			case 1:
				arg1 = inflater.inflate(R.layout.activity_winframent_status2, null);
				holder.img1 = (ImageView) arg1.findViewById(R.id.s2img1);
				holder.img2 = (ImageView) arg1.findViewById(R.id.s2img2);
				holder.tv1 = (TextView) arg1.findViewById(R.id.s2tv1);
				holder.tv2 = (TextView) arg1.findViewById(R.id.s2tv2);
				holder.tv3 = (TextView) arg1.findViewById(R.id.s2tv3);
				holder.tv4 = (TextView) arg1.findViewById(R.id.s2tv4);
				holder.tv5 = (TextView) arg1.findViewById(R.id.s2tv5);
				holder.tv6 = (TextView) arg1.findViewById(R.id.s2tv6);
				break;
			}
			arg1.setTag(holder);
		} else {
			holder = (ViewHolder) arg1.getTag();
		}

		GoodsItem goodsItem = list.get(arg0);
		switch (itemViewType) {
		case 0:
			// 大图片
			mbitmaputils.display(holder.img1, goodsItem.getImg());
			// 标题
			holder.tv1.setText(goodsItem.getGoodsName());
			// 期号
			holder.tv2.setText("期   号:" + goodsItem.getPeriod());
			// 小图片
			int category = goodsItem.getCategory();
			if (category == 1) {
				holder.img2.setVisibility(View.VISIBLE);
				holder.img2.setImageResource(R.drawable.ic_ten_label);
			} else if (category == 2) {
				holder.img2.setVisibility(View.VISIBLE);
				holder.img2.setImageResource(R.drawable.ic_hundred_label);
			} else {
				holder.img2.setVisibility(View.GONE);
			}

			
			// 倒计时
			holder.cutdownview.startCutdown(
					Long.parseLong(goodsItem.getRemainTime()) - (System.currentTimeMillis() - goodsItem.getEnterTime()),
					50);
			
			
			holder.cutdownview.setOnCountDownFinishListener(new OnCountDownFinishListener() {
				@Override
				public void onFinish() {
					String url = "http://123.56.145.151:8080/Duobao-XM/period-info?period="
							+ getItem(arg0).getPeriod();
					mHttpUtils.send(HttpMethod.GET, url, new RequestCallBack<String>() {
						@Override
						public void onFailure(HttpException arg0, String arg1) {
						}

						@Override
						public void onSuccess(ResponseInfo<String> position) {
//						用try  catch  防止出错
							try {
							String result = position.result;
							ResponseRevealedPeriodInfo2 fromJson = new Gson().fromJson(result,
									ResponseRevealedPeriodInfo2.class);
//							在时间倒计时完成后 将数据的信息进行改变  并进行刷新   （状态不同了：揭晓→已揭晓）
							list.set(arg0, fromJson.getResult());
							notifyDataSetChanged();
							} catch (Exception e) {
							}
						}
					});
				}
			});
			break;

		case 1:
			// 大图片
			mbitmaputils.display(holder.img1, goodsItem.getImg());
			// 小图片
			category = goodsItem.getCategory();
			if (category == 1) {
				holder.img2.setVisibility(View.VISIBLE);
				holder.img2.setImageResource(R.drawable.ic_ten_label);
			} else if (category == 2) {
				holder.img2.setVisibility(View.VISIBLE);
				holder.img2.setImageResource(R.drawable.ic_hundred_label);
			} else {
				holder.img2.setVisibility(View.GONE);
			}
			// 标题
			holder.tv1.setText(goodsItem.getGoodsName());
			// 期号
			holder.tv2.setText("期   号:" + goodsItem.getPeriod());
			// 获得者
			holder.tv3.setText("获得者:" + goodsItem.getLuckUser().getNickname());
			// 参与人数
			holder.tv4.setText("参与人数:" + goodsItem.getLuckUser().getDuobaoTimes());
			// 幸运号码
			holder.tv5.setText("幸运号码:" + goodsItem.getLuckCode());
			// 获取网站公布时间
			String revealedTime = goodsItem.getRevealedTime();
			String timeformat = TimeFormat.timeformat(revealedTime, "yyyy-MM-dd HH:mm:ss");
			// 获取当前时间
			Date date = new Date();
			long time = date.getTime();
			String timeformat2 = TimeFormat.timeformat("" + time, "yyyy-MM-dd HH:mm:ss");
			// 进行三种判断
			String web = timeformat.substring(8, 10);
			String tdy = timeformat2.substring(8, 10);
			if (web.equals(tdy)) {
				holder.tv6.setText("揭晓时间：今天" + timeformat.substring(11, 16));
			} else if (Integer.parseInt(tdy) - Integer.parseInt(web) == 1) {
				holder.tv6.setText("揭晓时间：昨天" + timeformat.substring(11, 16));
			} else {
				holder.tv6.setText("揭晓时间：" + timeformat.substring(5, 16));
			}
			break;
		}
		return arg1;
	}

	private class ViewHolder {
		private ImageView img1, img2;
		private CutdownTextView cutdownview;
		private TextView tv1, tv2, tv3, tv4, tv5, tv6;
	}

	@Override
	public int getItemViewType(int position) {
		GoodsItem goodsItem = list.get(position);
		if (goodsItem.getStatus() == 1) {
			return 0;
		} else {
			return 1;
		}
	}

	@Override
	public int getViewTypeCount() {
		return 2;
	}

	/**
	 * 封装一个 向数据源中添加数据
	 */
	public void addDatas(List<GoodsItem> list) {
		long enterTime = System.currentTimeMillis();
		for (GoodsItem goodsItem : list) {
			if (goodsItem.getStatus() == 1) {
				goodsItem.setEnterTime(enterTime);
			}
		}
		this.list.addAll(list);
		notifyDataSetChanged();
	}

	public void clearDatas() {
		this.list.clear();
		notifyDataSetChanged();
	}
}
