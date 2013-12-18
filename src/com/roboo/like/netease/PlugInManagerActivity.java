package com.roboo.like.netease;

import java.util.LinkedList;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.roboo.like.netease.adapter.PlugInListViewAdapter;
import com.roboo.like.netease.model.ListItem;

public class PlugInManagerActivity extends BaseActivity
{
	private ListView mListView;
	private LinkedList<ListItem> mData;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setTVTitle(R.string.tv_plug_in);
		//TODO setContentView Tag
		setContentView(R.layout.activity_plug_in_manager);
		initView();
		setData();
		this.mListView.setAdapter(getAdapter());
		setListener();
	}
	private void setListener()
	{
		this.mListView.setOnItemClickListener(new OnItemClickListener()
		{

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id)
			{
				
			}
		});
	}
	private void initView()
	{
		this.mListView = (ListView) findViewById(R.id.lv_list);
	}
	private void setData()
	{
		this.mData = new LinkedList<ListItem>();	
		int[] rightResIds = {R.drawable.ic_setting,R.drawable.ic_weather,R.drawable.ic_wifi_download,R.drawable.ic_night,R.drawable.ic_search_news,R.drawable.ic_netease_mail};
		String[] rightNames = getResources().getStringArray(R.array.right_menu_content);
		for (int i = 0; i < (rightResIds.length > rightNames.length ? rightNames.length : rightResIds.length); i++)
		{
			ListItem item = new ListItem();
			item.resId = rightResIds[i];
			item.name = rightNames[i];
			this.mData.add(item);
		}	
	}
	private PlugInListViewAdapter getAdapter()
	{
		return new PlugInListViewAdapter(this, mData);
	}
}
