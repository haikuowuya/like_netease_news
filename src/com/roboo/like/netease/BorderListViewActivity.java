package com.roboo.like.netease;

import java.util.Arrays;

import android.os.Bundle;

import com.roboo.like.netease.adapter.BroderListAdapter;
import com.roboo.like.netease.view.BorderListView;

public class BorderListViewActivity extends BaseActivity
{
	private BorderListView mBorderListView;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		
		super.onCreate(savedInstanceState);
		 setTVTitle(R.string.tv_border_listview);
		 //TODO setContentView Tag
		 setContentView(R.layout.activity_border_listview);
		 initView();
		 setAdapter();
	}
	private void setAdapter()
	{
		this.mBorderListView.setAdapter(new BroderListAdapter(this, Arrays.asList(getResources().getStringArray(R.array.titles))));
	}
	private void initView()
	{
		this.mBorderListView = (BorderListView) findViewById(R.id.blv_listview);
	}
}
