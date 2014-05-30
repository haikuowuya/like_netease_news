package com.roboo.like.netease.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.roboo.like.netease.MainActivity;
import com.roboo.like.netease.NewsApplication;
import com.roboo.like.netease.R;
import com.roboo.like.netease.abs.ptr.PullToRefreshAttacher;

public class SuperAwesomeCardFragment extends Fragment implements PullToRefreshAttacher.OnRefreshListener
{
	private PullToRefreshAttacher mPullToRefreshAttacher;

	private static final String ARG_POSITION = "position";

	private int position;

	public static SuperAwesomeCardFragment newInstance(int position)
	{
		SuperAwesomeCardFragment f = new SuperAwesomeCardFragment();
		Bundle b = new Bundle();
		b.putInt(ARG_POSITION, position);
		f.setArguments(b);
		return f;
	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		position = getArguments().getInt(ARG_POSITION);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{

		/*
		 * LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,
		 * LayoutParams.MATCH_PARENT);
		 * 
		 * FrameLayout fl = new FrameLayout(getActivity());
		 * fl.setLayoutParams(params);
		 * 
		 * final int margin = (int)
		 * TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8,
		 * getResources() .getDisplayMetrics());
		 * 
		 * TextView v = new TextView(getActivity()); params.setMargins(margin,
		 * margin, margin, margin); v.setLayoutParams(params);
		 * v.setLayoutParams(params); v.setGravity(Gravity.CENTER);
		 * v.setBackgroundResource(R.drawable.background_card);
		 * v.setText("CARD " + (position + 1));
		 * 
		 * // fl.addView(v); fl.addView(listView);
		 */
		ListView listView = new ListView(getActivity());
		listView.setBackgroundResource(R.drawable.background_card);
		int tmp = position + 1;
		listView.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_activated_1, new String[] { "A" + tmp, "B" + tmp, "C" + tmp, "D" + tmp, "E" + tmp, "F" + tmp,
				"H" + tmp, "K" + tmp, "N" + tmp, "M" + tmp }));
		mPullToRefreshAttacher = ((MainActivity) getActivity()).getPullToRefreshAttacher();
		mPullToRefreshAttacher.addRefreshableView(listView, this);
		return listView;
	}

	@Override
	public void onRefreshStarted(View view)
	{
		new AsyncTask<Void, Void, Void>()
		{

			@Override
			protected Void doInBackground(Void... params)
			{
				try
				{
					Thread.sleep(NewsApplication.SIMULATIVE_REFRESH_TIME);
				}
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}
				return null;
			}

			@Override
			protected void onPostExecute(Void result)
			{
				super.onPostExecute(result);
				mPullToRefreshAttacher.setRefreshComplete();
			}
		}.execute();
	}

}