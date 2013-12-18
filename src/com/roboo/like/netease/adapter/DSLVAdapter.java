package com.roboo.like.netease.adapter;

import java.util.LinkedList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.TextView;

import com.roboo.like.netease.R;
import com.roboo.like.netease.model.NewsCategory;

public class DSLVAdapter extends BaseAdapter
{
	private LinkedList<NewsCategory> data;
	private Context context;
	private boolean isDSLV;
	public DSLVAdapter(LinkedList<NewsCategory> data, Context context, boolean isDSLV)
	{
		super();
		this.data = data;
		this.context = context;
		this.isDSLV = isDSLV;
	}
	public DSLVAdapter(LinkedList<NewsCategory> data, Context context)
	{
		this(data, context, false);

	}

	@Override
	public int getCount()
	{
		// TODO
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
		// TODO
		return position;

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		ViewHolder holder = null;
		if (isDSLV)
		{
			if (null == convertView)
			{
				holder = new ViewHolder();
				convertView = LayoutInflater.from(context).inflate(R.layout.list_item_click_remove, null);
				holder.mTVText = (TextView) convertView.findViewById(R.id.text);
				convertView.setTag(holder);
			}
			else
			{
				holder = (ViewHolder) convertView.getTag();
			}
			holder.mTVText.setText(data.get(position) == null ? "" : data.get(position).getNewsCategoryName());
		}
		else
		{
			if(null == convertView)
			{
				holder = new ViewHolder();
				convertView = LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_checked, null);
				holder.mCTVText = (CheckedTextView) convertView.findViewById(android.R.id.text1);
				convertView.setTag(holder);
			}
			else
			{
				holder = (ViewHolder) convertView.getTag();
			}
			holder.mCTVText.setText(data.get(position) == null ? "" : data.get(position).getNewsCategoryName());
		}	
		return convertView;

	}
	private class ViewHolder
	{
		public TextView mTVText;
		public CheckedTextView mCTVText;
	}
	public void remove(NewsCategory item)
	{
		data.remove(item);
		notifyDataSetChanged();
	}

	public void insert(NewsCategory item, int to)
	{
		data.add(to, item);
		notifyDataSetChanged();
	}

}
