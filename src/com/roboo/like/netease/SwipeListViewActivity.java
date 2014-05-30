package com.roboo.like.netease;

import com.roboo.like.netease.view.SwipeListView;

import android.os.Bundle;
import android.view.WindowManager.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

public class SwipeListViewActivity extends BaseActivity
{
	private SwipeListView mSwipeListView;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setTVTitle(R.string.tv_swipe_listview);
		// TODO setContentView Tag
		mSwipeListView = new SwipeListView(this);
		setContentView(mSwipeListView);
		mSwipeListView.setAdapter(getAdapter());
	}

	private ArrayAdapter<String> getAdapter()
	{
		return new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, getResources().getStringArray(R.array.titles));
	}
}
