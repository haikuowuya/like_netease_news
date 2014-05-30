package com.roboo.like.netease;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.roboo.like.netease.adapter.RecycleAdapter;

public class RecycleActivity extends BaseActivity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{

		super.onCreate(savedInstanceState);
		setTVTitle(R.string.tv_recycle);
		// TODO setContentView Tag
		ViewPager viewPager = new ViewPager(this);
		viewPager.setAdapter(new RecycleAdapter(this));
		setContentView(viewPager);
	}
}
