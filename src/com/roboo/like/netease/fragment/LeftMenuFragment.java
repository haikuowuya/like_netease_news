package com.roboo.like.netease.fragment;

import java.util.LinkedList;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ListView;

import com.roboo.like.netease.MainActivity;
import com.roboo.like.netease.R;
import com.roboo.like.netease.adapter.LeftMenuAdapter;
import com.roboo.like.netease.model.ListItem;

public class LeftMenuFragment extends Fragment
{
	private LinkedList<ListItem> mLeftData;

	private LeftMenuAdapter mAdapter;

	public static LeftMenuFragment newInstance()
	{
		LeftMenuFragment fragment = new LeftMenuFragment();
		return fragment;
	}

	public static LeftMenuFragment newInstance(Bundle bundle)
	{
		LeftMenuFragment fragment = new LeftMenuFragment();
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
		ListView listView = new ListView(getActivity());
		listView.setLayoutParams(params);
		listView.setAdapter(getAdapter());
		listView.setOnItemClickListener(new OnItemClickListener()
		{
			@SuppressLint("NewApi")
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id)
			{
				getActivity().getActionBar().setDisplayUseLogoEnabled(true);
				getActivity().getActionBar().setLogo(mLeftData.get(position).resId);
				if (getActivity() instanceof MainActivity)
				{
					MainActivity activity = (MainActivity) getActivity();
					activity.showContent();
					activity.updateLeftTopImgAndTitle(mLeftData.get(position).resId, "网易" + mLeftData.get(position).name);

				}
			}
		});
		frameLayout.addView(listView);
		return frameLayout;
	}

	public LeftMenuAdapter getAdapter()
	{
		this.mAdapter = new LeftMenuAdapter(getActivity(), mLeftData);
		return this.mAdapter;
	}

	private void setData()
	{
		this.mLeftData = new LinkedList<ListItem>();
		int[] leftResIds = { R.drawable.ic_news, R.drawable.ic_native, R.drawable.ic_follow, R.drawable.ic_image, R.drawable.ic_topic, R.drawable.ic_vote };
		String[] leftNames = getResources().getStringArray(R.array.left_menu_content);
		for (int i = 0; i < (leftResIds.length > leftNames.length ? leftNames.length : leftResIds.length); i++)
		{
			ListItem item = new ListItem();
			item.resId = leftResIds[i];
			item.name = leftNames[i];
			this.mLeftData.add(item);
		}
	}

}
