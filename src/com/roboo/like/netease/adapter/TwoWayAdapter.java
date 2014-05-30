package com.roboo.like.netease.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.roboo.like.netease.R;

public class TwoWayAdapter  extends BaseAdapter {
	private final Context mContext;

	public TwoWayAdapter(Context context) {
		mContext = context;
	}

	@Override
	public int getCount() {
	    return 100;
	}

	@Override
	public Integer getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
	    ViewHolder holder = null;

		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(R.layout.twoway_list_item, parent, false);

			holder = new ViewHolder();
			holder.title = (TextView) convertView.findViewById(R.id.title);

			convertView.setTag(holder);
		} else {
		    holder = (ViewHolder) convertView.getTag();
		}

        holder.title.setText("Item: " + position);

		return convertView;
	}

	class ViewHolder {
	    public TextView title;
	}
}

