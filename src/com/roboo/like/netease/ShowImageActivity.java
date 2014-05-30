package com.roboo.like.netease;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.Window;

import com.roboo.like.netease.fragment.ShowImageFragment;
import com.roboo.like.netease.view.CirclePageIndicator;
import com.roboo.like.netease.view.MyViewPager;

public class ShowImageActivity extends BaseActivity
{
	private MyViewPager mViewPager;
	private String[]  mData;
	private int mCurrentIndex;
	private CirclePageIndicator mIndicator;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		requestWindowFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
		super.onCreate(savedInstanceState);
		setTVTitle(R.string.tv_image_detail);
		// TODO setContentView Tag
		setContentView(R.layout.activity_show_image);
		// TODO initView method Tag
		initView();
		mData = getIntent().getStringArrayExtra("srcs");
		mCurrentIndex = getIntent().getIntExtra("index", 0);
		if(null != mData)
		{
			this.mViewPager.setAdapter(new MyViewPagerAdapter(getSupportFragmentManager()));
			this.mViewPager.setCurrentItem(mCurrentIndex);
			
		}
		this.mViewPager.setPageMargin(12);
		this.mViewPager.setPageMarginDrawable(new ColorDrawable(getResources().getColor(R.color.actionbar_color)));
		this.mIndicator.setViewPager(mViewPager);
	}

	private void initView()
	{
		this.mIndicator = (CirclePageIndicator) findViewById(R.id.cpi_indicator);
		this.mViewPager = (MyViewPager) findViewById(R.id.mvp_pager);
	}
	private class MyViewPagerAdapter extends FragmentPagerAdapter
	{
		public MyViewPagerAdapter(FragmentManager fm)
		{
			super(fm);
		}

		@Override
		public Fragment getItem(int arg0)
		{
			return ShowImageFragment.newInstance(mData[arg0]);
		}

		@Override
		public int getCount()
		{
			return null == mData ? 0 : mData.length;
		}
	}
}
