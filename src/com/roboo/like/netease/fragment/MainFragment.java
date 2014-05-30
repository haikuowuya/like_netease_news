package com.roboo.like.netease.fragment;

import java.io.IOException;
import java.util.LinkedList;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.roboo.like.netease.MainActivity;
import com.roboo.like.netease.NewsActivity;
import com.roboo.like.netease.NewsApplication;
import com.roboo.like.netease.R;
import com.roboo.like.netease.WebViewActivity;
import com.roboo.like.netease.abs.ptr.PullToRefreshAttacher;
import com.roboo.like.netease.adapter.MainListViewAdapter;
import com.roboo.like.netease.dao.INewsDao;
import com.roboo.like.netease.dao.impl.NewsDaoImpl;
import com.roboo.like.netease.database.DBHelper;
import com.roboo.like.netease.model.News;
import com.roboo.like.netease.model.NewsCategory;
import com.roboo.like.netease.utils.NewsUtils;

public class MainFragment extends Fragment implements PullToRefreshAttacher.OnRefreshListener
{
	private int pageNo = 0;
	private PullToRefreshAttacher mPullToRefreshAttacher;
	private LinearLayout mFooterView;
	private ProgressBar mProgressBar;
	private LinkedList<News> mData;
	private TextView mTVText;

	private static final String ARG_NEWS_CATEGORY = "newsCategory";

	private ListView mListView;
	private NewsCategory mNewsCategory;
	private MainListViewAdapter mAdapter;
	private SharedPreferences mPreferences;

	public static MainFragment newInstance(NewsCategory category)
	{
		MainFragment mainFragment = new MainFragment();
		Bundle bundle = new Bundle();
		if (null != category)
		{
			bundle.putSerializable(ARG_NEWS_CATEGORY, category);
		}
		mainFragment.setArguments(bundle);
		return mainFragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		mPreferences = getActivity().getSharedPreferences(getActivity().getPackageName(), Context.MODE_PRIVATE);
		mNewsCategory = (NewsCategory) getArguments().getSerializable(ARG_NEWS_CATEGORY);
		if (getActivity() instanceof MainActivity)
		{
			mPullToRefreshAttacher = ((MainActivity) getActivity()).getPullToRefreshAttacher();
		}
		else if (getActivity() instanceof NewsActivity)
		{
			mPullToRefreshAttacher = ((NewsActivity) getActivity()).getPullToRefreshAttacher();
		}
		initData();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		return createView(inflater);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);
		setAdapter();
		setListItemClickListener();
		setFooterViewClickListener();
	}

	/** 判断数据源是否为空，并做相应的处理 */
	private void setAdapter()
	{
		if (null == mData || (System.currentTimeMillis() - mPreferences.getLong(mNewsCategory.getNewsCategoryMD5(), System.currentTimeMillis())) > NewsApplication.REFRESH_INTERVAL_1_MINUTE)
		{
			mFooterView.setVisibility(View.GONE);
			mPreferences.edit().putLong(mNewsCategory.getNewsCategoryMD5(), System.currentTimeMillis()).commit();
			mPullToRefreshAttacher.setRefreshing(true);
			onRefreshStarted(mListView);
		}
		else
		{
			mAdapter = new MainListViewAdapter(getActivity(), mData, ImageLoader.getInstance());
			mListView.setAdapter(mAdapter);
		}
	}

	/** ListView的FooterView的点击事件 */
	private void setFooterViewClickListener()
	{
		mFooterView.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				pageNo++;
				mTVText.setText(getActivity().getString(R.string.tv_progressing));
				mProgressBar.setVisibility(View.VISIBLE);
				new GetNewsDataAsyncTask().execute();
			}
		});

	}

	/** ListView列表项点击事件 */
	private void setListItemClickListener()
	{
		mListView.setOnItemClickListener(new OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id)
			{
				News news = (News) parent.getItemAtPosition(position);
				Intent intent = new Intent(getActivity(), WebViewActivity.class);
				intent.putExtra("newsUrl", news.getUrl());
				startActivity(intent);
			}
		});
	}

	private View createView(LayoutInflater inflater)
	{
		FrameLayout frameLayout = new FrameLayout(getActivity());
		frameLayout.setBackgroundResource(R.drawable.background_card);

		mListView = (ListView) inflater.inflate(R.layout.listview, null);
		mListView.addFooterView(generateFooterView(inflater));
		mPullToRefreshAttacher.addRefreshableView(mListView, this);

		frameLayout.addView(mListView, new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

		return frameLayout;
	}

	/** 生成ListView的FooterView */
	private View generateFooterView(LayoutInflater inflater)
	{
		mFooterView = (LinearLayout) inflater.inflate(R.layout.fragment_main_lisetview_footer_view, null);
		mProgressBar = (ProgressBar) mFooterView.findViewById(R.id.pb_progress);
		mTVText = (TextView) mFooterView.findViewById(R.id.tv_text);
		return mFooterView;
	}
	
	 

	@Override
	public boolean getUserVisibleHint()
	{
		System.out.println("getUserVisibleHint = " + super.getUserVisibleHint());
//		 if(super.getUserVisibleHint())
//		 {
//			 initData();
//		 }
		return super.getUserVisibleHint();
	}

	private void initData()
	{
		INewsDao newsDao = new NewsDaoImpl(new DBHelper(getActivity()));
		mData = newsDao.getNewsList(mNewsCategory.getNewsCategoryMD5());
		System.out.println("mdata.size()  = " + (null != mData ? mData.size() : null));
	}

	@Override
	public void onRefreshStarted(View view)
	{
		pageNo = 1;
		new GetNewsDataAsyncTask().execute();
	}

	private final class GetNewsDataAsyncTask extends AsyncTask<Void, Void, LinkedList<News>>
	{
		@Override
		protected LinkedList<News> doInBackground(Void... params)
		{
			try
			{
				LinkedList<News> data = null;
				if (null != mNewsCategory)
				{
					data = NewsUtils.getITHomeNews(mNewsCategory.getNewsCategoryUrl(), pageNo);
				}
				return data;
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
			catch (Exception e)
			{
			}
			pageNo--;
			return null;
		}

		@Override
		protected void onPostExecute(LinkedList<News> result)
		{
			super.onPostExecute(result);
			if (null != result)
			{
				System.out.println("result.size() = " + result.size());
				INewsDao newsDao = new NewsDaoImpl(new DBHelper(getActivity()));
				int insertSum = 0;
				if (pageNo == 1)
				{
					newsDao.delete(mNewsCategory.getNewsCategoryMD5());
					mData = null;
				}
				else
				{
					for (News news : result)
					{
						insertSum += newsDao.insert(news, mNewsCategory.getNewsCategoryMD5());
					}
				}
				System.out.println("insertSum = " + insertSum);
				if (null == mData)
				{
					mData = result;
					mAdapter = new MainListViewAdapter(getActivity(), mData, ImageLoader.getInstance());
					mListView.setAdapter(mAdapter);
				}
				else
				{
					for (News news : result)
					{
						if (!mData.contains(news))
						{
							mData.addLast(news);
						}
					}
				}
				if (null != mAdapter)
				{
					mAdapter.notifyDataSetChanged();
				}
				mFooterView.setVisibility(View.VISIBLE);
			}
			mTVText.setText(getActivity().getString(R.string.tv_click_load_more));
			mProgressBar.setVisibility(View.INVISIBLE);
			mPullToRefreshAttacher.setRefreshComplete();
		}
	}

	@Override
	public void setUserVisibleHint(boolean isVisibleToUser)
	{
		if (null != getView())
		{
			if (isVisibleToUser)
			{
				initData();
			}
		}
	}
}