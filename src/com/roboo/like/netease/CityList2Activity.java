package com.roboo.like.netease;

import java.util.LinkedList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout.LayoutParams;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.roboo.like.netease.adapter.SectionListAdapter;
import com.roboo.like.netease.dao.ICityDao;
import com.roboo.like.netease.dao.impl.CityDaoImpl;
import com.roboo.like.netease.database.DBHelper;
import com.roboo.like.netease.model.City;
import com.roboo.like.netease.utils.PinYinUtils;
import com.roboo.like.netease.view.SectionListView;

@SuppressLint("DefaultLocale")
public class CityList2Activity extends BaseActivity
{
	private AutoCompleteTextView mACTVTextView;

	private LinkedList<City> mData;

	private LocationClient mLocationClient;
	private SectionListView mListView;
	private MyListAdapter mAdapter;
	private SectionListAdapter mSectionListAdapter;

	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setTVTitle(R.string.tv_city_list2);
		// TODO setContentView Tag
		setContentView(R.layout.activity_city_list2);
		// TODO initView method Tag
		initView();
		setData();
		// this.mACTVTextView.setAdapter(new ArrayAdapter<String>(this,
		// android.R.layout.simple_dropdown_item_1line, getHandleData()));
		setAdapter();
		this.mListView.setAdapter(mSectionListAdapter);
		// TODO setListener method Tag
		setListener();
		// 模拟定位城市改变
		// this.showCityNameBtn(true, cityArrays[new Random().nextInt(4)]);
		this.showMyProgressBar(true);
		initBaiduLocation();
	}

	private void setAdapter()
	{
		mAdapter = new MyListAdapter(mData, CityList2Activity.this);
		mSectionListAdapter = new SectionListAdapter(getLayoutInflater(), mAdapter);
	}

	private void initBaiduLocation()
	{
		mLocationClient = new LocationClient(this);
		mLocationClient.registerLocationListener(new BDLocationListener()
		{
			public void onReceiveLocation(BDLocation location)
			{
				if (location == null)
				{
					return;
				}
				StringBuffer sb = new StringBuffer(256);
				sb.append("time : ");
				sb.append(location.getTime());
				sb.append("\nerror code : ");
				sb.append(location.getLocType());
				sb.append("\nlatitude : ");
				sb.append(location.getLatitude());
				sb.append("\nlontitude : ");
				sb.append(location.getLongitude());
				sb.append("\nradius : ");
				sb.append(location.getRadius());
				if (location.getLocType() == BDLocation.TypeGpsLocation)
				{
					sb.append("\nspeed : ");
					sb.append(location.getSpeed());
					sb.append("\nsatellite : ");
					sb.append(location.getSatelliteNumber());
				}
				else if (location.getLocType() == BDLocation.TypeNetWorkLocation)
				{
					sb.append("\naddr : ");
					sb.append(location.getAddrStr());
				}
				sb.append("\ncity = ");
				String cityName = location.getCity();
				sb.append(cityName);
				sb.append("\ncityCode = ");
				sb.append(location.getCityCode());
				System.out.println(" onReceiveLocation  sb.toString = " + sb.toString());
				if (null != cityName)
				{
					if ("市".equals(cityName.substring(cityName.length() - 1, cityName.length())))
					{
						cityName = cityName.substring(0, cityName.length() - 1);
					}
					Toast.makeText(getBaseContext(), "定位成功", Toast.LENGTH_SHORT).show();
					CityList2Activity.this.showMyProgressBar(false);
					CityList2Activity.this.showCityNameBtn(true, cityName);
				}
			}

			public void onReceivePoi(BDLocation poiLocation)
			{
				if (poiLocation == null)
				{
					return;
				}
				StringBuffer sb = new StringBuffer(256);
				sb.append("Poi time : ");
				sb.append(poiLocation.getTime());
				sb.append("\nerror code : ");
				sb.append(poiLocation.getLocType());
				sb.append("\nlatitude : ");
				sb.append(poiLocation.getLatitude());
				sb.append("\nlontitude : ");
				sb.append(poiLocation.getLongitude());
				sb.append("\nradius : ");
				sb.append(poiLocation.getRadius());
				if (poiLocation.getLocType() == BDLocation.TypeNetWorkLocation)
				{
					sb.append("\naddr : ");
					sb.append(poiLocation.getAddrStr());
				}
				if (poiLocation.hasPoi())
				{
					sb.append("\nPoi:");
					sb.append(poiLocation.getPoi());
				}
				else
				{
					sb.append("noPoi information");
				}
				System.out.println(" onReceivePoi  sb.toString = " + sb.toString());
			}
		});

		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);
		option.setAddrType("all");
		option.setCoorType("bd09ll");
		option.setScanSpan(900);
		option.setPoiNumber(6);
		mLocationClient.setLocOption(option);
		mLocationClient.start();
		mLocationClient.requestLocation();

	}

	@Override
	protected void onPause()
	{
		mLocationClient.stop();
		super.onPause();
	}

	@Override
	protected void onResume()
	{
		// TODO
		super.onResume();
		mLocationClient.start();

	}

	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
		case R.id.tv_always_selected_city:
			ICityDao cityDao = new CityDaoImpl(new DBHelper(this));
			cityDao.setSelectedCity(cityDao.getCityCodeByCityName(((TextView) v).getText() + ""));
			setResult(RESULT_OK);
			this.finish();
			break;

		default:
			super.onClick(v);
			break;
		}
	}

	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		mLocationClient.stop();
	}

	private LinkedList<String> getHandleData()
	{
		ICityDao cityDao = new CityDaoImpl(new DBHelper(this));
		LinkedList<City> data = cityDao.getCityList();
		LinkedList<String> result = new LinkedList<String>();
		for (City city : data)
		{
			result.add(city.getCityName());
		}
		return result;
	}

	private void setData()
	{
		ICityDao cityDao = new CityDaoImpl(new DBHelper(this));
		this.mData = cityDao.getCityList();
	}

	private void initView()
	{
		this.mACTVTextView = (AutoCompleteTextView) findViewById(R.id.actv_textview);
		this.mListView = (SectionListView) findViewById(R.id.lv_list);
	}

	private class MyListAdapter extends BaseAdapter implements Filterable
	{
		private LinkedList<City> data;
		private Context context;

		public MyListAdapter(LinkedList<City> data, Context context)
		{
			this.data = data;
			this.context = context;

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

		public View getView(int position, View convertView, ViewGroup parent)
		{
			convertView = LayoutInflater.from(context).inflate(R.layout.city_list_item, null);
			ViewHolder holder = new ViewHolder();
			holder.mTVCityName = (TextView) convertView.findViewById(R.id.tv_city_name);
			holder.mLinearLayout = (LinearLayout) convertView.findViewById(R.id.linear_sort_key_container);
			if (null != data)
			{
				holder.mTVCityName.setText(data.get(position).getCityName());
			}
			if (null == holder.mTVSortKey)
			{
				holder.mLinearLayout.setVisibility(View.GONE);
			}
			return convertView;
		}

		private class ViewHolder
		{
			public TextView mTVCityName;
			public LinearLayout mLinearLayout;
			public TextView mTVSortKey;
		}

		@Override
		public Filter getFilter()
		{
			return new MyFilter();
		}
	}

	private void setListener()
	{
		this.mACTVTextView.setOnItemClickListener(new OnItemClickListenerImpl(this.mACTVTextView));
		this.mACTVTextView.addTextChangedListener(filterTextWatcher);
	}

	private class OnItemClickListenerImpl implements OnItemClickListener
	{
		private View mView;
		private ICityDao mCityDao;

		public OnItemClickListenerImpl(View mView)
		{
			super();
			this.mView = mView;
			this.mCityDao = new CityDaoImpl(new DBHelper(CityList2Activity.this));
		}

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id)
		{
			if (this.mView instanceof ListView)
			{
				City city = (City) parent.getItemAtPosition(position);
				this.mCityDao.setSelectedCity(city.getCityCode());
			}
			else if (this.mView instanceof AutoCompleteTextView)
			{
				String cityName = (String) parent.getItemAtPosition(position);
				this.mCityDao.setSelectedCity(this.mCityDao.getCityCodeByCityName(cityName));
			}
			setResult(RESULT_OK);
			finish();

		}

	}

	public class MyFilter extends Filter
	{
		@Override
		protected FilterResults performFiltering(CharSequence constraint)
		{
			// constraint = mACTVTextView.getText().toString();
			FilterResults result = new FilterResults();
			if (!TextUtils.isEmpty(constraint))
			{
				LinkedList<City> filt = new LinkedList<City>();
				LinkedList<City> Items = new LinkedList<City>();
				synchronized (this)
				{
					Items = mData;
				}
				for (int i = 0; i < Items.size(); i++)
				{
					City item = Items.get(i);
					handleFilter(constraint, filt, item);
				}
				result.count = filt.size();
				result.values = filt;
			}
			else
			{
				synchronized (this)
				{
					result.count = mData.size();
					result.values = mData;
				}
			}
			return result;
		}

		private void handleFilter(CharSequence constraint, LinkedList<City> filt, City item)
		{
			// for(int i = 0; i < constraint.length();i++)
			// {
			// CharSequence subStr = constraint.subSequence(i,
			// constraint.length());
			// String pinyin =
			// PinYinUtils.converterToSpell(item.getCityName().substring(i));
			// if
			// (pinyin.toLowerCase().startsWith(subStr.toString().toLowerCase())||item.getCityName().toLowerCase().startsWith(subStr.toString().toLowerCase()))
			// {
			// filt.add(item);
			// }
			// }
			if                (item.getCityPinYinName().toLowerCase().startsWith(constraint.toString().toLowerCase())
					|| item.getCityName().toLowerCase().startsWith(constraint.toString().toLowerCase()))
			{
				filt.add(item);
			}
		};

		@Override
		protected void publishResults(CharSequence constraint, FilterResults results)
		{
			@SuppressWarnings("unchecked")	
			LinkedList<City> filtered = (LinkedList<City>) results.values;
			if(null == imageView)
			{
				imageView = new ImageView(CityList2Activity.this);
			}
			imageView.setVisibility(View.GONE);
			mAdapter = new MyListAdapter(filtered, CityList2Activity.this);
			mSectionListAdapter = new SectionListAdapter(getLayoutInflater(), mAdapter);
			mListView.setAdapter(mSectionListAdapter);
			if (filtered.size() == 0)
			{
				Toast.makeText(getBaseContext(), "你要查找的城市在火星吗", Toast.LENGTH_SHORT).show();
				imageView.setVisibility(View.VISIBLE);
				imageView.setImageResource(R.drawable.ic_matrix);
				imageView.setLayoutParams(new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, Gravity.CENTER));
				if(imageView.getParent() == null)
				{
				((FrameLayout)mListView.getParent()).addView(imageView);
				}
				else
				{
					imageView.setVisibility(View.VISIBLE);
				}
			 
			}
		}
	}
	ImageView imageView =  null;
	private TextWatcher filterTextWatcher = new TextWatcher()
	{
		public void afterTextChanged(Editable s)
		{
			new MyListAdapter(mData, CityList2Activity.this).getFilter().filter(s.toString());
		}

		public void beforeTextChanged(CharSequence s, int start, int count, int after)
		{
		}

		public void onTextChanged(CharSequence s, int start, int before, int count)
		{
		}

	};
}
