package com.roboo.like.netease;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.roboo.like.netease.view.OverScrollListView;
import com.roboo.like.netease.view.OverScrollListView.RemoveDirection;
import com.roboo.like.netease.view.OverScrollListView.RemoveListener;

public class OverScrollListViewActivity extends BaseActivity
{
	private MyAdapter mAdapter;
	private ArrayList<String> mData;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setTVTitle(R.string.tv_overscroll_listview);
		initData();
		mAdapter = new MyAdapter(this, mData);
		OverScrollListView overScrollListView = new OverScrollListView(this);
		overScrollListView.setRemoveListener(new SimpleRemoveListenerImpl());
		overScrollListView.setAdapter(mAdapter);

		// TODO setContentView Tag
		setContentView(overScrollListView);
	}

	private void initData()
	{
		mData = new ArrayList<String>();
		for (String str : getResources().getStringArray(R.array.city_list))
		{
			mData.add(str);
		}
	}

	private class MyAdapter extends BaseAdapter
	{
		private List<String> data;
		private Context context;

		public MyAdapter(Context context, List<String> data)
		{
			super();
			this.context = context;
			this.data = data;
		}

		@Override
		public int getCount()
		{
			return null == data ? 0 : data.size();
		}

		@Override
		public String getItem(int position)
		{
			return null == data ? null : data.get(position);
		}

		@Override
		public long getItemId(int position)
		{

			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent)
		{
			TextView textView = (TextView) LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1, null);
			textView.setText(data == null ? "" : data.get(position));
			return textView;
		}

	}

	public class SimpleRemoveListenerImpl implements RemoveListener
	{
		// 滑动删除之后的回调方法

		@Override
		public void removeItem(RemoveDirection direction, int position)
		{
			if (mData.remove(mAdapter.getItem(position)))
			{
				mAdapter.notifyDataSetChanged();
			}
			switch (direction)
			{
			case RIGHT:
				Toast.makeText(getApplicationContext(), "向右删除  " + position, Toast.LENGTH_SHORT).show();
				break;
			case LEFT:
				Toast.makeText(getApplicationContext(), "向左删除  " + position, Toast.LENGTH_SHORT).show();
				break;

			default:
				break;
			}

		}
	}
}
