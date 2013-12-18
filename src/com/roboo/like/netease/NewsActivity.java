package com.roboo.like.netease;

import com.roboo.like.netease.abs.ptr.PullToRefreshAttacher;
import com.roboo.like.netease.model.NewsCategory;
import com.roboo.like.netease.view.fragment.DSLVFragment;
import com.roboo.like.netease.view.fragment.MainFragment;

import android.os.Bundle;

public class NewsActivity extends BaseActivity
{
	private PullToRefreshAttacher mPullToRefreshAttacher;
	private NewsCategory mNewsCategory;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		 mNewsCategory = (NewsCategory) getIntent().getSerializableExtra("newsCategory");
		 this.mPullToRefreshAttacher = PullToRefreshAttacher.get(this);
		 this.mPullToRefreshAttacher.setRefreshing(true);
		 setTVTitle(mNewsCategory.getNewsCategoryName());
		 setContentView(R.layout.activity_news);
		 getSupportFragmentManager().beginTransaction().add(R.id.frame_container, MainFragment.newInstance(mNewsCategory), "MainFragment").commit();
	}
	public PullToRefreshAttacher getPullToRefreshAttacher()
	{
		return mPullToRefreshAttacher;
	}
}
