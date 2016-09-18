package com.example.utils;

import com.example.test.R;
import com.example.test.R.color;

import android.content.Context;

import android.view.Gravity;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SetLine {
	private Context context;
	private LinearLayout layout;
	private int count;
	private LayoutParams params;
	private RelativeLayout line;
	private TextView view2;
	private int linelong;

	public SetLine(Context context, LinearLayout layout, int count,
			RelativeLayout line, int width) {
		super();
		this.context = context;
		this.layout = layout;
		this.count = count;
		this.line = line;
		setline();
		// 设置线条
		view2 = new TextView(context);
		view2.setBackgroundResource(R.color.black);
		linelong = width / 4;
		LayoutParams params = new LayoutParams(width / count,
				LayoutParams.WRAP_CONTENT);
		view2.setLayoutParams(params);
		line.addView(view2);
	}

	/**
	 * 用pager记录上一次滑动条位置
	 * 
	 * @param position
	 * @param pager
	 */
	public void changeline(int position, int pager) {
		TranslateAnimation animation;
		if (position > pager) {
			animation = new TranslateAnimation((pager) * linelong, (position)
					* linelong, 0, 0);
		} else {
			animation = new TranslateAnimation((pager) * linelong, (position)
					* linelong, 0, 0);
		}
		animation.setFillAfter(true);
		animation.setDuration(500);
		view2.startAnimation(animation);
	}

	private void setline() {
		TextView view = null;

		for (int i = 0; i < count; i++) {
			view = new TextView(context);
			if (i == 0) {
				view.setBackgroundResource(R.color.red);
			} else {
				view.setBackgroundResource(R.color.write);
			}
			params = new LayoutParams(LayoutParams.MATCH_PARENT,
					LayoutParams.MATCH_PARENT, 1f);
			view.setLayoutParams(params);
			view.setText("pager" + (i + 1));
			view.setGravity(Gravity.CENTER);
			layout.addView(view);
		}
	}

	public void changelin(int position) {
		TextView view = null;
		for (int i = 0; i < count; i++) {
			view = (TextView) layout.getChildAt(i);
			if (i == position) {
				view.setBackgroundResource(R.color.red);
			} else {
				view.setBackgroundResource(R.color.write);
			}
		}
	}
}
