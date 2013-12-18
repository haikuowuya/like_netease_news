package com.roboo.like.netease;

import java.util.LinkedList;
import java.util.Random;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AlphabetIndexer;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.roboo.like.netease.dao.ICityDao;
import com.roboo.like.netease.dao.impl.CityDaoImpl;
import com.roboo.like.netease.database.DBHelper;
import com.roboo.like.netease.model.City;

@SuppressLint("DefaultLocale")
public class CityListActivity extends BaseActivity
{
	private ListView mListView;
	private AutoCompleteTextView mACTVTextView;
	private LinkedList<City> mAlwaysSelectedCityList;
	private MyListAdapter mAdapter;
	private LinkedList<City> mData;
	private AlphabetIndexer mIndexer;
	private int lastFirstVisibleItem = -1;
	private Button mBtnAZ;
	private TextView mTVToast;
	private TextView mTVTitle;
	private LinearLayout mHeaderView;
	private LinearLayout mLayout;
	private RelativeLayout mRelativeContainer;
	private String mAlphabet = "#ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private String[] cityArrays = { "南京", "天津", "南阳", "武汉" };
	private LocationClient mLocationClient;
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setTVTitle(R.string.tv_city_list);
		// TODO setContentView Tag
		setContentView(R.layout.activity_city_list);
		// TODO initView method Tag
		initView();
		setData();
		// TODO initListHeaderView method Tag
		initListHeaderView();
		this.mListView.addHeaderView(mHeaderView);
		this.mListView.setAdapter(getAdapter());
		this.mACTVTextView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, getHandleData()));
		// TODO setListener method Tag
		setListener();
		// 模拟定位城市改变
		// this.showCityNameBtn(true, cityArrays[new Random().nextInt(4)]);
		this.showMyProgressBar(true);
		initBaiduLocation();
	}
	private void initBaiduLocation()
	{
		mLocationClient = new LocationClient(this);
		mLocationClient.registerLocationListener(new BDLocationListener()
		{
			public void onReceiveLocation(BDLocation location)
			{
				if (location == null)
					return;
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
					CityListActivity.this.showMyProgressBar(false);
					CityListActivity.this.showCityNameBtn(true, cityName);
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
		//TODO 
		super.onResume();
		mLocationClient.start();
		
	}
	private void initListHeaderView()
	{
		this.mHeaderView = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.city_listview_header_view, null);
		if (null != mAlwaysSelectedCityList)
		{
			for (City city : mAlwaysSelectedCityList)
			{
				TextView textView = (TextView) LayoutInflater.from(this).inflate(android.R.layout.simple_list_item_1, null);
				textView.setText(city.getCityName());
				textView.setClickable(true);
				textView.setBackgroundResource(R.drawable.ibtn_selector);
				textView.setId(R.id.tv_always_selected_city);
				textView.setOnClickListener(this);
				this.mHeaderView.addView(textView, new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
			}
		}
		else
		{
			this.mHeaderView.setVisibility(View.GONE);
		}
	}
	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
		case R.id.tv_always_selected_city:
			ICityDao cityDao = new CityDaoImpl(new DBHelper(this));
			cityDao.setSelectedCity(cityDao.getCityCodeByCityName(((TextView)v).getText()+""));
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
	@SuppressWarnings("deprecation")
	private void setData()
	{
		ICityDao cityDao = new CityDaoImpl(new DBHelper(this));
		this.mData = cityDao.getCityList();
		this.mAlwaysSelectedCityList = cityDao.getAlwaysSelectedCityList();
		Cursor cursor = cityDao.getCityListCursor();
		startManagingCursor(cursor);
		mIndexer = new AlphabetIndexer(cursor, 2, mAlphabet);
	}
	private MyListAdapter getAdapter()
	{
		if (null != mData)
		{
			// A-Z索引与城市拼音关联起来
			for (City city : mData)
			{
				String sortKey = getSortKey(city);
				city.setSortKey(sortKey);
			}
			this.mAdapter = new MyListAdapter(mData, this, mIndexer);
		}
		return mAdapter;
	}
	private void initView()
	{
		this.mLayout = (LinearLayout) findViewById(R.id.linear_container);
		this.mACTVTextView = (AutoCompleteTextView) findViewById(R.id.actv_textview);
		this.mListView = (ListView) findViewById(R.id.lv_list);
		this.mTVToast = (TextView) findViewById(R.id.tv_toast);
		this.mBtnAZ = (Button) findViewById(R.id.btn_a_z);
		this.mRelativeContainer = (RelativeLayout) findViewById(R.id.relative_container);
		this.mTVTitle = (TextView) findViewById(R.id.tv_sort_key);

	}

	private class MyListAdapter extends BaseAdapter
	{
		private LinkedList<City> data;
		private Context context;
		private SectionIndexer indexer;
		public MyListAdapter(LinkedList<City> data, Context context, SectionIndexer indexer)
		{
			super();
			this.data = data;
			this.context = context;
			this.indexer = indexer;
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
		/**
		 * TODO getView
		 */
		public View getView(int position, View convertView, ViewGroup parent)
		{
			convertView = LayoutInflater.from(context).inflate(R.layout.city_list_item, null);
			ViewHolder holder = new ViewHolder();
			holder.mTVCityName = (TextView) convertView.findViewById(R.id.tv_city_name);
			holder.mTVSortKey = (TextView) convertView.findViewById(R.id.tv_sort_key);
			holder.mLinearLayout = (LinearLayout) convertView.findViewById(R.id.linear_sort_key_container);
			if (null != data)
			{
				holder.mTVCityName.setText(data.get(position).getCityName());
				int section = indexer.getSectionForPosition(position);
				if (position == indexer.getPositionForSection(section))
				{
					holder.mTVSortKey.setText(data.get(position).getSortKey());
					holder.mLinearLayout.setVisibility(View.VISIBLE);
				}
				else
				{
					holder.mLinearLayout.setVisibility(View.GONE);
				}
			}
			return convertView;
		}
		private class ViewHolder
		{
			public TextView mTVCityName;
			public LinearLayout mLinearLayout;
			public TextView mTVSortKey;
		}
	}
	private void setListener()
	{
		this.mBtnAZ.setOnTouchListener(new OnTouchListener()
		{
			public boolean onTouch(View v, MotionEvent event)
			{
				float y = event.getY();
				float mBtnAZheight = mBtnAZ.getHeight();
				int sectionPosition = (int) ((y / mBtnAZheight) / (1f / 27f));
				if (sectionPosition < 0)
				{
					sectionPosition = 0;
				}
				else if (sectionPosition > 26)
				{
					sectionPosition = 26;
				}
				String sectionLetter = String.valueOf(mAlphabet.charAt(sectionPosition));
				int position = mIndexer.getPositionForSection(sectionPosition);
				switch (event.getAction())
				{
				case MotionEvent.ACTION_DOWN:
					mBtnAZ.setBackgroundResource(R.drawable.a_z_click);
					mRelativeContainer.setVisibility(View.VISIBLE);
					mTVToast.setText(sectionLetter);
					mListView.setSelection(position);
					break;
				case MotionEvent.ACTION_MOVE:
					mTVToast.setText(sectionLetter);
					mListView.setSelection(position);
					break;
				default:
					mBtnAZ.setBackgroundResource(R.drawable.a_z);
					mRelativeContainer.setVisibility(View.GONE);
				}
				return true;
			}
		});
		this.mListView.setOnScrollListener(new OnScrollListener()
		{
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState)
			{}
			@TargetApi(Build.VERSION_CODES.HONEYCOMB)
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount)
			{
				//System.out.println("firstVisible = " + firstVisibleItem + " visibleItemCount = " + visibleItemCount + " totalItemCount = " + totalItemCount);
				int section = mIndexer.getSectionForPosition(firstVisibleItem);
				int nextSectionPosition = mIndexer.getPositionForSection(section + 1);
				System.out.println("section  = " + section + " nextSectionPosition = " + nextSectionPosition );
				if (firstVisibleItem != lastFirstVisibleItem)
				{
					
					MarginLayoutParams params = (MarginLayoutParams) mLayout.getLayoutParams();
					params.topMargin = 0;
					mLayout.setLayoutParams(params);
					String sortKey = String.valueOf(mAlphabet.charAt(section));
					mTVTitle.setText(sortKey);
				}
				if (nextSectionPosition == firstVisibleItem + 1)
				{
					View childView = view.getChildAt(1);
					if (childView != null)
					{
						int titleHeight = mLayout.getHeight();
						int bottom = childView.getBottom();
						MarginLayoutParams params = (MarginLayoutParams) mLayout.getLayoutParams();
						if (bottom < titleHeight)
						{
							float pushedDistance = bottom - titleHeight;
							params.topMargin = (int) pushedDistance;
							mLayout.setLayoutParams(params);
						}
						else
						{
							if (params.topMargin != 0)
							{
								params.topMargin = 0;
								mLayout.setLayoutParams(params);
							}
						}
					}
				}
				lastFirstVisibleItem = firstVisibleItem;
			}
		});
		// TODO ListView#setOnItemClickListener method Tag
		this.mListView.setOnItemClickListener(new OnItemClickListenerImpl(this.mListView));
		this.mACTVTextView.setOnItemClickListener(new OnItemClickListenerImpl(this.mACTVTextView));
		this.mACTVTextView.addTextChangedListener(new MyTextWatcher());
	}
	/**
	 * TODO getSortKey method
	 * 
	 * @param city
	 * @return
	 */
	private String getSortKey(City city)
	{
		if (null != city)
		{
			String key = city.getCityPinYinName().substring(0, 1).toUpperCase();
			if (key.matches("[A-Z]"))
			{
				return key;
			}
		}
		return "#";
	}
	private class MyTextWatcher implements TextWatcher
	{
		@Override
		public void beforeTextChanged(CharSequence s, int start, int count, int after)
		{}

		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count)
		{}

		@Override
		public void afterTextChanged(Editable s)
		{}

	}
	private class OnItemClickListenerImpl implements OnItemClickListener
	{
		private View mView;
		private ICityDao mCityDao;
		public OnItemClickListenerImpl(View mView)
		{
			super();
			this.mView = mView;
			this.mCityDao = new CityDaoImpl(new DBHelper(CityListActivity.this));
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
}
