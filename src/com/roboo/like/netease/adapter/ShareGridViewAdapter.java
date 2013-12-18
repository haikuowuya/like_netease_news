package com.roboo.like.netease.adapter;

import java.util.LinkedList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.roboo.like.netease.R;
import com.roboo.like.netease.model.ShareType;

public class ShareGridViewAdapter extends BaseAdapter
{
	private Context context;
	private LinkedList<ShareType> data;

	public ShareGridViewAdapter(Context context, LinkedList<ShareType> data)
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
		if (null != data)
		{
			ShareType item = data.get(position);
			if (null == convertView)
			{
				holder = new ViewHolder();
				convertView = LayoutInflater.from(context).inflate(R.layout.share_gridview_item, null);
				holder.mIVImage = (ImageView) convertView.findViewById(R.id.iv_image);
				holder.mTVText = (TextView) convertView.findViewById(R.id.tv_text);
				convertView.setTag(holder);
			}
			else
			{
				holder = (ViewHolder) convertView.getTag();
			}
			holder.mIVImage.setImageResource(item.getShareTypeImage());
			holder.mTVText.setText(item.getShareTypeName());
		}
		return convertView;

	}
	private class ViewHolder
	{
		public ImageView mIVImage;
		public TextView mTVText;
	}
}
