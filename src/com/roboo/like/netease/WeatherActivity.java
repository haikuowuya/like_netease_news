package com.roboo.like.netease;

import java.io.IOException;
import java.util.LinkedList;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.View;
import android.widget.ProgressBar;

import com.roboo.like.netease.dao.ICityDao;
import com.roboo.like.netease.dao.impl.CityDaoImpl;
import com.roboo.like.netease.database.DBHelper;
import com.roboo.like.netease.fragment.WeatherFragment;
import com.roboo.like.netease.model.City;
import com.roboo.like.netease.model.Weather;
import com.roboo.like.netease.service.WeatherService;
import com.roboo.like.netease.utils.JsonUtils;
import com.roboo.like.netease.view.MyViewPager;
import com.roboo.like.netease.view.MyViewPager.TransitionEffect;

public class WeatherActivity extends BaseActivity
{
	private static final int REQUEST_CODE = 2442;
	private MyViewPager mViewPager;
	private ProgressBar mProgressBar;
	private MyPagerAdapter mAdapter;
	private  LinkedList<Weather> mData = new LinkedList<Weather>();
	private City mSelectedCity;
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		ICityDao cityDao = new CityDaoImpl(new DBHelper(this));
		mSelectedCity = cityDao.getSelectedCity();
		setTVTitle(mSelectedCity.getCityName()+getString(R.string.tv_weather));
		showLocationBtn(true);
		showShareBtn(true);
		// TODO setContentView Tag
		setContentView(R.layout.activity_weather);
		initView();
		new WeatherAsyncTask().execute(mSelectedCity.getCityCode());
		System.out.println("onCreate");
	}
	/**
	 * TODO initView
	 * 
	 */
	private void initView()
	{
		this.mViewPager = (MyViewPager) findViewById(R.id.mvp_pager);
		this.mViewPager.setTransitionEffect(TransitionEffect.FlipHorizontal);
		this.mProgressBar = (ProgressBar) findViewById(R.id.pb_progress);
	}
	public class MyPagerAdapter extends FragmentStatePagerAdapter
	{
		public MyPagerAdapter(FragmentManager fm)
		{
			super(fm);
		}

		@Override
		public Fragment getItem(int position)
		{
			Fragment fragment = WeatherFragment.newInstance(mData.get(position));
			System.out.println("mData.get("+position+") = "+ mData.get(position));
			mViewPager.setObjectForPosition(fragment, position);
			return fragment;
		}

		@Override
		public int getCount()
		{
			return mData.size();
		}
		@Override
		public int getItemPosition(Object object)
		{
			return POSITION_NONE;		
		}
	}
	private class WeatherAsyncTask extends AsyncTask<String, Void, LinkedList<Weather>>
	{
		protected LinkedList<Weather> doInBackground(String... params)
		{
			if (null != params)
			{
				String cityCode = params[0];
				WeatherService service = new WeatherService();
				try
				{
					Thread.sleep(2000);
					return JsonUtils.handleWeatherJson(service.getWeatherJson(cityCode));
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
			return null;
		}
		@Override
		protected void onPostExecute(LinkedList<Weather> result)
		{
			mViewPager.setVisibility(View.VISIBLE);
			mProgressBar.setVisibility(View.GONE);
			super.onPostExecute(result);
			if (null != result)
			{
				mAdapter = new MyPagerAdapter(getSupportFragmentManager());
				mData = result;
				mViewPager.setAdapter(mAdapter);
				mAdapter.notifyDataSetChanged();
			}
		}
	}
	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
		case R.id.ibtn_location:
			startActivityForResult(new Intent(this, CityListActivity.class), REQUEST_CODE);
			break;
		default:
			super.onClick(v);
		}
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		if (requestCode == REQUEST_CODE && resultCode == RESULT_OK)
		{
			City city = new CityDaoImpl(new DBHelper(this)).getSelectedCity();
			if(mSelectedCity != city)
			{
				mSelectedCity = city ;
				setTVTitle(mSelectedCity.getCityName()+getString(R.string.tv_weather));
				mProgressBar.setVisibility(View.VISIBLE);
				mViewPager.setVisibility(View.GONE);
				new WeatherAsyncTask().execute(mSelectedCity.getCityCode());
				
			}
		}

	}
}
