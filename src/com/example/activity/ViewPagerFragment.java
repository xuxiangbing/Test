package com.example.activity;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.adapter.MyPagerAdapter;
import com.example.fragment.Fragment1;
import com.example.fragment.Fragment2;
import com.example.fragment.Fragment3;
import com.example.fragment.Fragment4;
import com.example.test.R;
import com.example.utils.SetLine;
import com.slidingmenu.lib.SlidingMenu;

public class ViewPagerFragment extends FragmentActivity implements
		OnClickListener {
	private ViewPager fragment_viewpager;
	private LinearLayout text_lin;
	private SetLine line;
	private ArrayList<Fragment> list;
	private RelativeLayout line_lin;
	private int height;
	private int width;
	private SlidingMenu slidingMenu;
	private LinearLayout left;
	private TextView share_text;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		// 显示通用信息类
		DisplayMetrics metrics = new DisplayMetrics();
		// 将取得的宽高放入DisplayMetrics类的对象中
		this.getWindowManager().getDefaultDisplay().getMetrics(metrics);
		// 获取屏幕绝对高度
		height = metrics.heightPixels;
		// 获取屏幕绝对宽度
		width = metrics.widthPixels;
		setContentView(R.layout.fragment);
		initUI();
		initData();
		setLinener();
	}

	private void initUI() {
		// TODO Auto-generated method stub
		share_text = (TextView) findViewById(R.id.share_text);
		line_lin = (RelativeLayout) findViewById(R.id.line_lin);
		text_lin = (LinearLayout) findViewById(R.id.text_lin);
		fragment_viewpager = (ViewPager) findViewById(R.id.fragment_viewpager);
		// 初始化SlidingMenu
		slidingMenu = new SlidingMenu(this);
		// 设置侧拉模式--left(左侧菜单)
		slidingMenu.setMode(SlidingMenu.LEFT_RIGHT);
		// 设定触摸侧拉区域
		slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		// 设定菜单的宽度--反向设定
		int width = getWindowManager().getDefaultDisplay().getWidth();
		slidingMenu.setBehindOffset(width / 3);
		// 设定阴影
		slidingMenu.setShadowDrawable(R.drawable.shadow);
		slidingMenu.setSecondaryShadowDrawable(R.drawable.shadow);
		slidingMenu.setShadowWidth(5);

		// 设置左侧菜单的样式
		LayoutInflater inflater = LayoutInflater.from(this);
		left = (LinearLayout) inflater.inflate(R.layout.sliding_left, null);
		slidingMenu.setMenu(left);
		// 设置右侧菜单
		slidingMenu.setSecondaryMenu(R.layout.sliding_right);
		// 设置关联Activity
		slidingMenu.attachToActivity(ViewPagerFragment.this,
				SlidingMenu.SLIDING_CONTENT);
	}

	private void initData() {
		// TODO Auto-generated method stub
		list = new ArrayList<Fragment>();
		list.add(new Fragment1());
		list.add(new Fragment2());
		list.add(new Fragment3());
		list.add(new Fragment4());
		fragment_viewpager.setAdapter(new MyPagerAdapter(list,
				getSupportFragmentManager(), fragment_viewpager));
		line = new SetLine(this, text_lin, list.size(), line_lin, width);
		for (int i = 0; i < list.size(); i++) {
			text_lin.getChildAt(i).setOnClickListener(this);
		}
	}

	private void setLinener() {
		// viewpager与menu滑动冲突的避免
		fragment_viewpager.setOnTouchListener(new OnTouchListener() {
			private float x;

			@Override
			public boolean onTouch(View view, MotionEvent event) {
				ViewPager pager = (ViewPager) view;
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					view.getParent().requestDisallowInterceptTouchEvent(true);
					// 获取点击时X的坐标
					x = event.getRawX();
				} else if (event.getAction() == MotionEvent.ACTION_MOVE) {
					float ux = event.getRawX();
					// 判断滑动方向和是否是第一个页面和最后一个页面
					if (ux - x > 0 && pager.getCurrentItem() == 0 || ux - x < 0
							&& pager.getCurrentItem() == (list.size() - 1)) {
						view.getParent().requestDisallowInterceptTouchEvent(
								false);
					}
				}
				return false;
			}
		});
		fragment_viewpager.setOnPageChangeListener(new OnPageChangeListener() {
			private int index;
			private int pager;

			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				// 停止第一个页面
				list.get(index).onPause();
				Fragment fragment = list.get(arg0);
				if (fragment.isAdded()) {
					fragment.onResume();
				}
				index = arg0;
				line.changelin(arg0);
				line.changeline(arg0, pager);
				pager = arg0;
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub

			}
		});
		share_text.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(ViewPagerFragment.this,
						ShareActivity.class);
				startActivity(intent);
			}
		});
	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		for (int i = 0; i < list.size(); i++) {
			if (view == text_lin.getChildAt(i)) {
				// 点击标识，自动跳转到指定的view
				fragment_viewpager.setCurrentItem(i);
			}
		}
	}
}
