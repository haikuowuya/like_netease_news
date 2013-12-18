package com.roboo.like.netease.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.roboo.like.netease.R;

public class WelcomeGridViewAdapter extends BaseAdapter
{
	private Context context;
	private int[] data;

	public WelcomeGridViewAdapter(Context context, int[] data)
	{
		super();
		this.context = context;
		this.data = data;
	}

	@Override
	public int getCount()
	{
		return null == data ? 0 : data.length;
	}

	@Override
	public Object getItem(int position)
	{
		return null == data ? null : data[position];
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
		if (null != data)
		{
			if (null == convertView)
			{
				holder = new ViewHolder();
				convertView = LayoutInflater.from(context).inflate(R.layout.gridview_item, null);
				holder.mIVImage = (ImageView) convertView.findViewById(R.id.iv_image);
				convertView.setTag(holder);
			}
			else
			{
				holder = (ViewHolder) convertView.getTag();
			}
			holder.mIVImage.setImageResource(data[position]);

		}
		return convertView;

	}
	private class ViewHolder
	{
		public ImageView mIVImage;
	}
	
}
