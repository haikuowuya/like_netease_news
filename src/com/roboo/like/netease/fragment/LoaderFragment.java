package com.roboo.like.netease.fragment;

import java.util.LinkedList;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.TextView;

import com.roboo.like.netease.R;
import com.roboo.like.netease.WeatherActivity;
import com.roboo.like.netease.dao.ICityDao;
import com.roboo.like.netease.dao.impl.CityDaoImpl;
import com.roboo.like.netease.database.DBHelper;
import com.roboo.like.netease.model.City;

public class LoaderFragment extends ListFragment implements LoaderCallbacks<LinkedList<City>>,OnQueryTextListener
{
	private static LinkedList<City> mData;
	private CityAdapter mAdapter;
	private String mCurrentQueryString;
 

	public static LoaderFragment newInstance(Bundle bundle)
	{
		LoaderFragment fragment = new LoaderFragment();
		fragment.setArguments(bundle);
		return fragment;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);
		// 中间一个进度圈
		setListShown(false);
		setEmptyText(getString(R.string.tv_loading_city_data));
		getLoaderManager().initLoader(0, null, this);
		setHasOptionsMenu(true);
	}

	 @Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
	{
		 
		super.onCreateOptionsMenu(menu, inflater);
		 MenuItem item = menu.add(R.string.tv_search_city);
         item.setIcon(R.drawable.ic_search);
         item.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM
                 | MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
         SearchView sv = new SearchView(getActivity());
         sv.setOnQueryTextListener(this);
         item.setActionView(sv);
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id)
	{
		City city =  (City) mAdapter.getItem(position);
		
		ICityDao cityDao = new CityDaoImpl(new DBHelper(getActivity()));
		if (null != city)
		{
			cityDao.setSelectedCity(city.getCityCode());
		}
		Intent intent = new Intent(getActivity(), WeatherActivity.class);
		startActivity(intent);
	}

	private static class CityTaskLoader extends AsyncTaskLoader<LinkedList<City>>
	{
		private Context context;

		public CityTaskLoader(Context context)
		{
			super(context);
			this.context = context;
		}

		@Override
		public LinkedList<City> loadInBackground()
		{
			System.out.println("loadInBackground() ");
			ICityDao cityDao = new CityDaoImpl(new DBHelper(context));
			return cityDao.getCityList();
		}

		@Override
		protected void onForceLoad()
		{
			 System.out.println("onForceLoad");
			super.onForceLoad();
		}
		@Override
		public boolean cancelLoad()
		{
			System.out.println("cancleLoad()");
			return super.cancelLoad();
		}
		@Override
		public void onCanceled(LinkedList<City> data)
		{
			 System.out.println("onCancled");
			super.onCanceled(data);
		}

		@Override
		protected LinkedList<City> onLoadInBackground()
		{
			System.out.println("onLoadInBackground()");
			return super.onLoadInBackground();
		}
		@Override
		public void deliverResult(LinkedList<City> data)
		{
			 System.out.println("deliverResult()");
			super.deliverResult(data);
		}
		@Override
		protected void onStartLoading()
		{
			super.onStartLoading();
			System.out.println("onStartLoading()");
			if (null == mData)
			{
				System.out.println("forceLoad()");
				forceLoad();
			}
			else
			{
				System.out.println("deliverResult(mData)");
				deliverResult(mData);
			}
		}

	}

	@Override
	public Loader<LinkedList<City>> onCreateLoader(int id, Bundle args)
	{
		System.out.println("onCreateLoader");
		return new CityTaskLoader(getActivity());
	}

	@Override
	public void onLoadFinished(Loader<LinkedList<City>> loader, LinkedList<City> data)
	{
		System.out.println("onLoadFinished()");
		mAdapter = new CityAdapter(getActivity(), data);
		setListAdapter(mAdapter);
		System.out.println("data = " + data.size());
		if (isResumed())
		{
			setListShown(true);
		}
		else
		{
			setListShownNoAnimation(true);
		}

	}

	@Override
	public void onLoaderReset(Loader<LinkedList<City>> loader)
	{
		System.out.println("onLoaderReset()");
	}

	private static class CityAdapter extends BaseAdapter
	{
		private LinkedList<City> data;
		private Context context;

		public CityAdapter(Context context, LinkedList<City> data)
		{
			this.data = data;
			this.context = context;
		}

		@Override
		public int getCount()
		{

			return data.size();
		}

		@Override
		public Object getItem(int position)
		{
			return data.get(position);
		}

		@Override
		public long getItemId(int position)
		{
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent)
		{
			convertView = LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1, null);
			TextView textView = (TextView) convertView.findViewById(android.R.id.text1);
			textView.setText(data.get(position).getCityName());
			return convertView;
		}
	}

	@Override
	public boolean onQueryTextSubmit(String query)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onQueryTextChange(String newText)
	{
		 
		  mCurrentQueryString = !TextUtils.isEmpty(newText) ? newText : null;       
          return true;
	}
	//onActivityCreated#getLoaderManager.initLoader(0,null,LoaderManager.LoaderCallBacks)
	//onCreateLoader#AsyncTaskLoader onStartLoading forceLoad onForceLoad cancleLoad onLoadingInbackground loadinbackground
	//deliverResult onLoaderFinish 
}
