package com.roboo.like.netease.adapter;

import java.util.LinkedList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.roboo.like.netease.R;
import com.roboo.like.netease.model.ListItem;

public class PlugInListViewAdapter extends BaseAdapter
{
	private Context context;
	private LinkedList<ListItem> data;

	public PlugInListViewAdapter(Context context, LinkedList<ListItem> data)
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
	public Object getItem(int position)
	{
		return null == data ? null : data.get(position);
	}

	@Override
	public long getItemId(int position)
	{
		return position;
	}

	/**
	 * TODO getView
	 */
	public View getView(int position, View convertView, ViewGroup parent)
	{
		ViewHolder holder = null;
		ListItem item = data.get(position);
		if (null == convertView)
		{
			holder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(R.layout.plug_in_list_item, null);
			holder.mTVText = (TextView) convertView.findViewById(R.id.tv_text);
		
			convertView.setTag(holder);
		}
		else
		{
			holder = (ViewHolder) convertView.getTag();
		}
		holder.mTVText.setCompoundDrawablePadding(10);
		holder.mTVText.setCompoundDrawablesWithIntrinsicBounds(item.resId, 0, R.drawable.ic_plug_in_open, 0);
		holder.mTVText.setText(item.name);
		return convertView;

	}
	private class ViewHolder
	{
		public TextView mTVText;
	}
}
