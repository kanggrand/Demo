package com.a1000phone.android31myapp.Task;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.a1000phone.android31myapp.R;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.ArrayList;
import java.util.List;

public class CreateHeader implements OnSuccessListener {
	private Activity context;
	private ImageLoader imageLoader;
	private List<NetworkImageView> dataListPager = new ArrayList<NetworkImageView>();
	private MyPagerAdapter adapter;
	private ImageView[] imageViews;
	private ViewPager viewPager;

	public CreateHeader(Activity context, ImageLoader imageLoader) {
		this.context = context;
		this.imageLoader = imageLoader;
	}

	/**
	 * 接口回掉，用于得到ViewPager的数据源List<NetworkImageView>
	 */
	@Override
	public void onSuccess(List<String> result) {
		for (int i = 0; i < result.size(); i++) {
			NetworkImageView networkImageView = new NetworkImageView(context);
			networkImageView.setImageUrl(result.get(i), imageLoader);
			dataListPager.add(networkImageView);
			// 数据源发生改变，提醒适配器
			adapter.notifyDataSetChanged();
		}
	}

	public RelativeLayout getListViewHeader() {
		// =========ListView的头部，父布局是RelativeLayout=====================
		RelativeLayout relativeLayout = new RelativeLayout(context);
		// LayoutParams可以用来规定RelativeLayout的宽高
		LayoutParams layoutParamsRelative = new LayoutParams(LayoutParams.MATCH_PARENT, getWindowHeight() / 3);
		relativeLayout.setLayoutParams(layoutParamsRelative);

		// ==========RelativeLayout里面有一个ViewPager========================
		viewPager = new ViewPager(context);
		RelativeLayout.LayoutParams layoutParamsPager = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.MATCH_PARENT,
				RelativeLayout.LayoutParams.MATCH_PARENT);
		viewPager.setLayoutParams(layoutParamsRelative);
		adapter = new MyPagerAdapter(dataListPager);
		viewPager.setAdapter(adapter);
		viewPager.setBackgroundResource(R.mipmap.viewpager_default);
		relativeLayout.addView(viewPager);

		// ===========三个小圆点，在LinearLayout上===============================
		LinearLayout linearLayout = new LinearLayout(context);
		RelativeLayout.LayoutParams layoutParamsLinear = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);
		layoutParamsLinear.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		linearLayout.setLayoutParams(layoutParamsLinear);

		imageViews = new ImageView[3];
		for (int i = 0; i < imageViews.length; i++) {
			imageViews[i] = new ImageView(context);
			LinearLayout.LayoutParams layoutParamsImage = new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.WRAP_CONTENT,
					LinearLayout.LayoutParams.WRAP_CONTENT);
			imageViews[i].setLayoutParams(layoutParamsImage);
			// 设置小圆点背景图片，默认第一个小圆点选中
			if (i == 0) {
				imageViews[i].setBackgroundResource(R.mipmap.dot);
			} else {
				imageViews[i].setBackgroundResource(R.mipmap.dot_1);
			}

			imageViews[i].setId(i);
			imageViews[i].setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					viewPager.setCurrentItem(v.getId());
					for (int j = 0; j < 3; j++) {
						if (j == v.getId()) {
							imageViews[j].setBackgroundResource(R.mipmap.dot);
						} else {
							imageViews[j].setBackgroundResource(R.mipmap.dot_1);
						}
					}
				}
			});
			linearLayout.addView(imageViews[i]);
		}

		// ViewPager的滑动监听，滑动ViewPager小圆点跟着改变
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				for (int i = 0; i < 3; i++) {
					if (i == position) {
						imageViews[i].setBackgroundResource(R.mipmap.dot);
					} else {
						imageViews[i].setBackgroundResource(R.mipmap.dot_1);
					}
				}
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});

		relativeLayout.addView(linearLayout);

		return relativeLayout;
	}

	/**
	 * 
	 * @return 屏幕高度
	 */
	private int getWindowHeight() {
		WindowManager windowManager = context.getWindowManager();
		int windowHeight = windowManager.getDefaultDisplay().getHeight();
		return windowHeight;
	}

	class MyPagerAdapter extends PagerAdapter {
		private List<NetworkImageView> dataListPager;

		public MyPagerAdapter(List<NetworkImageView> dataListPager) {
			this.dataListPager = dataListPager;
		}

		@Override
		public int getCount() {
			return dataListPager.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			container.addView(dataListPager.get(position));
			return dataListPager.get(position);
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			if (object instanceof NetworkImageView) {
				container.removeView((View) object);
			}
		}

	}

}
