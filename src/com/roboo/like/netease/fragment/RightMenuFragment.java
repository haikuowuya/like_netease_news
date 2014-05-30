package com.roboo.like.netease.fragment;

import java.util.LinkedList;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ListView;

import com.roboo.like.netease.MailActivity;
import com.roboo.like.netease.PlugInManagerActivity;
import com.roboo.like.netease.R;
import com.roboo.like.netease.SearchNewsActivity;
import com.roboo.like.netease.SettingsActivity;
import com.roboo.like.netease.SettingsPreferenceActivity;
import com.roboo.like.netease.WeatherActivity;
import com.roboo.like.netease.adapter.LeftMenuAdapter;
import com.roboo.like.netease.model.ListItem;

public class RightMenuFragment extends Fragment
{

	private LeftMenuAdapter mAdapter;
	private LinkedList<ListItem> mRightData;
	private Class<?>[] mClasses = { SettingsActivity.class, WeatherActivity.class, null, null, SearchNewsActivity.class, MailActivity.class, SettingsPreferenceActivity.class };

	public static RightMenuFragment newInstance()
	{
		RightMenuFragment fragment = new RightMenuFragment();
		return fragment;
	}

	public static RightMenuFragment newInstance(Bundle bundle)
	{
		RightMenuFragment fragment = new RightMenuFragment();
		fragment.setArguments(bundle);
		return fragment;
	}

	@SuppressWarnings("unchecked")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		setData();
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		FrameLayout frameLayout = new FrameLayout(getActivity());
		frameLayout.setBackgroundResource(R.drawable.background_card);
		frameLayout.setLayoutParams(params);
		View view = inflater.inflate(R.layout.fragment_right_menu_listview, null);
		Button btnPlugIn = (Button) view.findViewById(R.id.btn_plug_in);
		ListView listView = (ListView) view.findViewById(R.id.lv_list);
		listView.setAdapter(getAdapter());
		listView.setOnItemClickListener(new OnItemClickListener()
		{
			public void onItemClick(AdapterView<?> parent, View view, int position, long id)
			{
				// Toast.makeText(getActivity(), mData.get(position).name+"",
				// Toast.LENGTH_SHORT).show();
				if (null != mClasses[position])
				{
					Intent intent = new Intent(getActivity(), mClasses[position]);
					if (position == 4)
					{
						intent.setAction(Intent.ACTION_SEARCH);
						intent.putExtra(SearchManager.QUERY, "新闻");
					}
					startActivity(intent);
				}
			}
		});
		btnPlugIn.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				Intent intent = new Intent(getActivity(), PlugInManagerActivity.class);
				startActivity(intent);
			}
		});
		frameLayout.addView(view);
		return frameLayout;
	}

	public LeftMenuAdapter getAdapter()
	{
		this.mAdapter = new LeftMenuAdapter(getActivity(), mRightData);
		return this.mAdapter;
	}

	private void setData()
	{
		this.mRightData = new LinkedList<ListItem>();
		int[] rightResIds = { R.drawable.ic_setting, R.drawable.ic_weather, R.drawable.ic_wifi_download, R.drawable.ic_night, R.drawable.ic_search_news, R.drawable.ic_netease_mail,
				R.drawable.ic_setting };
		String[] rightNames = getResources().getStringArray(R.array.right_menu_content);
		for (int i = 0; i < (rightResIds.length > rightNames.length ? rightNames.length : rightResIds.length); i++)
		{
			ListItem item = new ListItem();
			item.resId = rightResIds[i];
			item.name = rightNames[i];
			this.mRightData.add(item);
		}
	}

}
