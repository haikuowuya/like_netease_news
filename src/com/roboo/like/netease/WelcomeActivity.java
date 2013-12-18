package com.roboo.like.netease;

import com.roboo.like.netease.view.CirclePageIndicator;
import com.roboo.like.netease.view.MyViewPager;
import com.roboo.like.netease.view.MyViewPager.TransitionEffect;
import com.roboo.like.netease.view.fragment.WelcomeFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.Window;

public class WelcomeActivity extends BaseActivity
{
	private MyViewPager mViewPager;
	private CirclePageIndicator mIndicator;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		// TODO setContentView Tag
		setContentView(R.layout.activity_welcome);
		initView();
		this.mViewPager.setAdapter(new MyPageAdapter(getSupportFragmentManager()));
		this.mIndicator.setViewPager(mViewPager);
	}
	/**
	 * TODO init view
	 * 
	 */
	private void initView()
	{
		this.mViewPager = (MyViewPager) findViewById(R.id.mvp_pager);
		this.mViewPager.setTransitionEffect(TransitionEffect.Stack);
		this.mIndicator = (CirclePageIndicator) findViewById(R.id.cpi_indicator);
		this.mViewPager.setTransitionEffect(TransitionEffect.FlipHorizontal);
	}

	private class MyPageAdapter extends FragmentPagerAdapter
	{

		public MyPageAdapter(FragmentManager fm)
		{
			super(fm);
		}

		@Override
		public Fragment getItem(int position)
		{
			Fragment fragment = WelcomeFragment.newInstance(position);
			mViewPager.setObjectForPosition(fragment, position);
			return  fragment;
		}

		@Override
		public int getCount()
		{
			return 2;

		}

	}
}
