package com.roboo.like.netease;

import com.roboo.like.netease.adapter.GridViewCacheAdapter;

import android.os.Bundle;
import android.widget.GridView;

public class GridViewCacheActivity extends BaseActivity
{
	 
	private GridView mGridView;
	private GridViewCacheAdapter mAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{

		super.onCreate(savedInstanceState);
		setTVTitle(R.string.tv_gridview_cache_adapter);
		// TODO setContentView Tag
		setContentView(R.layout.activity_gridview_cache_adapter);	 
		initView();
		mAdapter = new GridViewCacheAdapter(this, mGridView);
		mGridView.setAdapter(mAdapter);
	}
	private void initView()
	{
		 mGridView = (GridView) findViewById(R.id.gv_gridview);
	}
	 @Override
	protected void onDestroy()
	{
		super.onDestroy();
		// 退出程序时结束所有的下载任务
		mAdapter.cancelAllTasks();
	}
}
