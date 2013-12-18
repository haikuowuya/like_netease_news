package com.roboo.like.netease.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.roboo.like.netease.view.ListViewItem;

public class BroderListAdapter extends BaseAdapter
{

	private List<String> list;
	private Context c;

	public BroderListAdapter(Context c, List<String> list)
	{
		this.list = list;
		this.c = c;
	}
	@Override
	public int getCount()
	{
		return list.size();
	}

	@Override
	public Object getItem(int i)
	{

		return list.get(i);
	}

	@Override
	public long getItemId(int i)
	{

		return i;
	}

	@Override
	public View getView(int i, View view, ViewGroup viewgroup)
	{

		ListViewItem itemView = null;
		if (view == null)
		{
			itemView = new ListViewItem(c);
		}
		else
		{
			itemView = (ListViewItem) view;
		}
		itemView.setEventName(list.get(i));
		return itemView;
	}

}
