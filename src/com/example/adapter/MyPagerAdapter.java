package com.example.adapter;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup;

public class MyPagerAdapter extends PagerAdapter {
	private List<Fragment> list;
	private FragmentManager manager;
	private ViewPager pager;

	public MyPagerAdapter(List<Fragment> list, FragmentManager manager,
			ViewPager pager) {
		super();
		this.list = list;
		this.manager = manager;
		this.pager = pager;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView(list.get(position).getView());

	}

	@Override
	public View instantiateItem(ViewGroup container, int position) {
		Fragment fragment = list.get(position);
		if (!fragment.isAdded()) {
			FragmentTransaction transaction = manager.beginTransaction();
			transaction.add(fragment, fragment.getClass().getSimpleName());
			transaction.commit();
			// ¡¢º¥Ã·Ωª
			manager.executePendingTransactions();
		}
		View view = fragment.getView();
		if (view.getParent() == null) {
			container.addView(view);
		}
		return view;
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// TODO Auto-generated method stub
		return arg0 == arg1;
	}

}
