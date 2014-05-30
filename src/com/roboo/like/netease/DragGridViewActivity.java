package com.roboo.like.netease;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;

import junit.framework.Test;

import android.R.integer;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.SimpleAdapter;

import com.roboo.like.netease.view.DragGridView;
import com.roboo.like.netease.view.DragGridView.OnChanageListener;

public class DragGridViewActivity extends BaseActivity
{

	DragGridView gridView;
	SimpleAdapter simpleAdapter;
	LinkedList<HashMap<String, Object>> data;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		test();
		setTVTitle(R.string.tv_drag_gridview);
		gridView = new DragGridView(this);
		gridView.setColumnWidth(GridView.STRETCH_COLUMN_WIDTH);
		gridView.setNumColumns(3);
		setContentView(gridView);
		gridView.setAdapter(getAdapter());
		gridView.setOnChangeListener(new OnChanageListener()
		{
			public void onChange(int from, int to)
			{
				HashMap<String, Object> temp = data.get(from);

				// 这里的处理需要注意下
				if (from < to)
				{
					for (int i = from; i < to; i++)
					{
						Collections.swap(data, i, i + 1);
					}
				}
				else if (from > to)
				{
					for (int i = from; i > to; i--)
					{
						Collections.swap(data, i, i - 1);
					}
				}
				data.set(to, temp);

				simpleAdapter.notifyDataSetChanged();

			}
		});

	}

	private void test()
	{
		 WindowManager windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE); 
		  
	}

	private SimpleAdapter getAdapter()
	{
		data = new LinkedList<HashMap<String, Object>>();
		for (int i = 0; i < 40; i++)
		{
			HashMap<String, Object> itemMap = new HashMap<String, Object>();
			itemMap.put("item_image", R.drawable.ic_launcher);
			itemMap.put("item_text", "第 " + (1 + i) + " 个");
			data.add(itemMap);
		}
		simpleAdapter = new SimpleAdapter(this, data, R.layout.share_gridview_item, new String[] { "item_image", "item_text" }, new int[] { R.id.iv_image, R.id.tv_text });
		return simpleAdapter;
	}

}
