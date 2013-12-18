package com.roboo.like.netease.adapter;

import java.util.LinkedList;
import java.util.Random;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.roboo.like.netease.R;
import com.roboo.like.netease.model.ListItem;

public class LeftMenuAdapter extends BaseAdapter
{
	private Context context;
	private LinkedList<ListItem> data;
	Random mRandom;
	public LeftMenuAdapter(Context context, LinkedList<ListItem> data)
	{
		super();
		this.context = context;
		this.data = data;
		this.mRandom = new Random();
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
			convertView = LayoutInflater.from(context).inflate(R.layout.left_menu_list_item, null);
			holder.mIVImage = (ImageView) convertView.findViewById(R.id.iv_image);
			holder.mTVText = (TextView) convertView.findViewById(R.id.tv_text);
//			int randomColor = 0XFF000000|mRandom.nextInt(0X0077FFFF);
//			convertView.setBackgroundColor(randomColor);
			convertView.setTag(holder);
		}
		else
		{
			holder = (ViewHolder) convertView.getTag();
		}
		holder.mIVImage.setImageResource(item.resId);
		holder.mTVText.setText(item.name);
		return convertView;

	}
	private class ViewHolder
	{
		public TextView mTVText;
		public ImageView mIVImage;
	}
}
