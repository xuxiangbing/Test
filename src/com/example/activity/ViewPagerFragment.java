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
		// ��ʾͨ����Ϣ��
		DisplayMetrics metrics = new DisplayMetrics();
		// ��ȡ�õĿ�߷���DisplayMetrics��Ķ�����
		this.getWindowManager().getDefaultDisplay().getMetrics(metrics);
		// ��ȡ��Ļ���Ը߶�
		height = metrics.heightPixels;
		// ��ȡ��Ļ���Կ��
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
		// ��ʼ��SlidingMenu
		slidingMenu = new SlidingMenu(this);
		// ���ò���ģʽ--left(���˵�)
		slidingMenu.setMode(SlidingMenu.LEFT_RIGHT);
		// �趨������������
		slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		// �趨�˵��Ŀ��--�����趨
		int width = getWindowManager().getDefaultDisplay().getWidth();
		slidingMenu.setBehindOffset(width / 3);
		// �趨��Ӱ
		slidingMenu.setShadowDrawable(R.drawable.shadow);
		slidingMenu.setSecondaryShadowDrawable(R.drawable.shadow);
		slidingMenu.setShadowWidth(5);

		// �������˵�����ʽ
		LayoutInflater inflater = LayoutInflater.from(this);
		left = (LinearLayout) inflater.inflate(R.layout.sliding_left, null);
		slidingMenu.setMenu(left);
		// �����Ҳ�˵�
		slidingMenu.setSecondaryMenu(R.layout.sliding_right);
		// ���ù���Activity
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
		// viewpager��menu������ͻ�ı���
		fragment_viewpager.setOnTouchListener(new OnTouchListener() {
			private float x;

			@Override
			public boolean onTouch(View view, MotionEvent event) {
				ViewPager pager = (ViewPager) view;
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					view.getParent().requestDisallowInterceptTouchEvent(true);
					// ��ȡ���ʱX������
					x = event.getRawX();
				} else if (event.getAction() == MotionEvent.ACTION_MOVE) {
					float ux = event.getRawX();
					// �жϻ���������Ƿ��ǵ�һ��ҳ������һ��ҳ��
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
				// ֹͣ��һ��ҳ��
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
				// �����ʶ���Զ���ת��ָ����view
				fragment_viewpager.setCurrentItem(i);
			}
		}
	}
}
