package com.roboo.like.netease;

import java.util.LinkedList;

import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.roboo.like.netease.abs.ptr.PullToRefreshAttacher;
import com.roboo.like.netease.dao.INewsCategoryDao;
import com.roboo.like.netease.dao.impl.NewsCategoryDaoImpl;
import com.roboo.like.netease.database.DBHelper;
import com.roboo.like.netease.fragment.LeftMenuFragment;
import com.roboo.like.netease.fragment.MainFragment;
import com.roboo.like.netease.fragment.QuickContactFragment;
import com.roboo.like.netease.fragment.RightMenuFragment;
import com.roboo.like.netease.model.NewsCategory;
import com.roboo.like.netease.slidingmenu.SlidingFragmentActivity;
import com.roboo.like.netease.slidingmenu.SlidingMenu;
import com.roboo.like.netease.view.MyViewPager;
import com.roboo.like.netease.view.MyViewPager.TransitionEffect;
import com.roboo.like.netease.view.PagerSlidingTabStrip;

@SuppressLint("NewApi")
public class MainActivity extends SlidingFragmentActivity implements View.OnClickListener
{
	private ImageButton mIBtnSubscribe;
	private PullToRefreshAttacher mPullToRefreshAttacher;
	private final Handler handler = new Handler();
	private PagerSlidingTabStrip tabs;
	private MyViewPager mViewPager;
	private MyPagerAdapter adapter;
	protected SlidingMenu mSlidingMenu;

	private Drawable oldBackground = null;
	private int currentColor = 0X00FF0000;
	private LinkedList<NewsCategory> mCustomedData;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setTVTitle(R.string.app_name);
		// TODO setContentView Method Tag
		setContentView(R.layout.activity_main);
		testJsonRequest();

		isFirstRun();
		this.mPullToRefreshAttacher = PullToRefreshAttacher.get(this);
		initView();
		customSlidingMenu();
		mViewPager.setPageMargin(getResources().getDimensionPixelSize(R.dimen.pager_margin));
		changeColor(currentColor);
		changeColor(Color.parseColor("#BC1100"));
		this.mIBtnSubscribe.setOnClickListener(this);
	}

	/**
	 * 判断是否是第一次启动，如果是的话，跳转到{@link WelcomeActivity}
	 */
	private void isFirstRun()
	{
		SharedPreferences preferences = getPreferences(Context.MODE_PRIVATE);
		System.out.println("preferences.getBoolean(isFirst) =" + preferences.getBoolean("isFirst", true));
		if (preferences.getBoolean("isFirst", true))
		{
			preferences.edit().putBoolean("isFirst", false).commit();
			Intent intent = new Intent(this, WelcomeActivity.class);
			startActivity(intent);
			finish();
		}
	}

	/**
	 * TODO init view
	 * 
	 */
	private void initView()
	{
		this.mIBtnSubscribe = (ImageButton) findViewById(R.id.ibtn_subscribe);
		this.tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
		this.mViewPager = (MyViewPager) findViewById(R.id.pager);
		this.mViewPager.setTransitionEffect(TransitionEffect.FlipHorizontal);
		this.mViewPager.setOffscreenPageLimit(1);
	}

	@Override
	protected void onResume()
	{
		super.onResume();
		ViewServer.get(this).setFocusedWindow(this);
		INewsCategoryDao newsCategoryDao = new NewsCategoryDaoImpl(new DBHelper(this));
		mCustomedData = newsCategoryDao.getCustomedList();
		this.adapter = new MyPagerAdapter(getSupportFragmentManager(), mCustomedData);
		mViewPager.setAdapter(adapter);
		adapter.notifyDataSetChanged();
		tabs.setViewPager(mViewPager);
		// setListener();
	}

	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		ViewServer.get(this).removeWindow(this);
	}

	public void testJsonRequest()
	{
		RequestQueue queue = Volley.newRequestQueue(this);
		Listener<JSONObject> listener = new Listener<JSONObject>()
		{
			@Override
			public void onResponse(JSONObject response)
			{
				Toast.makeText(MainActivity.this, "获取Json数据成功", Toast.LENGTH_SHORT).show();
				System.out.println("response.toString = " + response.toString());
			}
		};
		ErrorListener errorListener = new ErrorListener()
		{
			@Override
			public void onErrorResponse(VolleyError error)
			{
				Toast.makeText(MainActivity.this, "获取Json数据失败 错误原因 = " + error.getMessage(), Toast.LENGTH_SHORT).show();
			}
		};
		queue.add(new JsonObjectRequest(NewsApplication.SPECIFIC_WEATHER_URL, null, listener, errorListener));

	}

	/**
	 * TODO setListener method Tag
	 * 
	 */
	public void setListener()
	{
		this.mViewPager.setOnPageChangeListener(new OnPageChangeListener()
		{
			@Override
			public void onPageSelected(int arg0)
			{
				Object obj = adapter.instantiateItem(mViewPager, arg0);
				System.out.println("obj = " + obj);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2)
			{

			}

			@Override
			public void onPageScrollStateChanged(int arg0)
			{

			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{
		case android.R.id.home:
			this.mSlidingMenu.showMenu();
			break;
		case R.id.action_contact:
			QuickContactFragment dialog = new QuickContactFragment();
			dialog.show(getSupportFragmentManager(), "QuickContactFragment");
			break;
		case R.id.action_standard:
			this.mViewPager.setTransitionEffect(TransitionEffect.Standard);
			break;
		case R.id.action_tablet:
			this.mViewPager.setTransitionEffect(TransitionEffect.Tablet);
			break;
		case R.id.action_cube_in:
			this.mViewPager.setTransitionEffect(TransitionEffect.CubeIn);
			break;
		case R.id.action_cube_out:
			this.mViewPager.setTransitionEffect(TransitionEffect.CubeOut);
			break;
		case R.id.action_flip_horizontal:
			this.mViewPager.setTransitionEffect(TransitionEffect.FlipHorizontal);
			break;
		case R.id.action_flip_vertical:
			this.mViewPager.setTransitionEffect(TransitionEffect.FlipVertical);
			break;
		case R.id.action_stack:
			this.mViewPager.setTransitionEffect(TransitionEffect.Stack);
			break;
		case R.id.action_zoom_in:
			this.mViewPager.setTransitionEffect(TransitionEffect.ZoomIn);
			break;
		case R.id.action_zoom_out:
			this.mViewPager.setTransitionEffect(TransitionEffect.ZoomOut);
			break;
		case R.id.action_rotate_up:
			this.mViewPager.setTransitionEffect(TransitionEffect.RotateUp);
			break;
		case R.id.action_rotate_down:
			this.mViewPager.setTransitionEffect(TransitionEffect.RotateDown);
			break;
		case R.id.action_accordion:
			this.mViewPager.setTransitionEffect(TransitionEffect.Accordion);
			break;
		}
		return true;
	}

	private void changeColor(int newColor)
	{
		tabs.setIndicatorColor(newColor);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
		{
			Drawable colorDrawable = new ColorDrawable(newColor);
			Drawable bottomDrawable = getResources().getDrawable(R.drawable.actionbar_bottom);
			LayerDrawable ld = new LayerDrawable(new Drawable[] { colorDrawable, bottomDrawable });

			if (oldBackground == null)
			{

				if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN)
				{
					ld.setCallback(drawableCallback);
				}
				else
				{
					getActionBar().setBackgroundDrawable(ld);
				}

			}
			else
			{
				TransitionDrawable td = new TransitionDrawable(new Drawable[] { oldBackground, ld });
				if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1)
				{
					td.setCallback(drawableCallback);
				}
				else
				{
					getActionBar().setBackgroundDrawable(td);
				}

				td.startTransition(200);

			}
			oldBackground = ld;
			getActionBar().setDisplayShowTitleEnabled(false);
			getActionBar().setDisplayShowTitleEnabled(true);

		}
		currentColor = newColor;

	}

	public void onColorClicked(View v)
	{
		int color = Color.parseColor(v.getTag().toString());
		changeColor(color);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState)
	{
		super.onSaveInstanceState(outState);
		outState.putInt("currentColor", currentColor);
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState)
	{
		super.onRestoreInstanceState(savedInstanceState);
		currentColor = savedInstanceState.getInt("currentColor");
		changeColor(currentColor);
	}

	private Drawable.Callback drawableCallback = new Drawable.Callback()
	{
		@Override
		public void invalidateDrawable(Drawable who)
		{
			getActionBar().setBackgroundDrawable(who);
		}

		@Override
		public void scheduleDrawable(Drawable who, Runnable what, long when)
		{
			handler.postAtTime(what, when);
		}

		@Override
		public void unscheduleDrawable(Drawable who, Runnable what)
		{
			handler.removeCallbacks(what);

		}
	};

	public class MyPagerAdapter extends FragmentPagerAdapter
	{
		private LinkedList<NewsCategory> data;

		public MyPagerAdapter(FragmentManager fm, LinkedList<NewsCategory> data)
		{
			super(fm);
			this.data = data;
		}

		@Override
		public CharSequence getPageTitle(int position)
		{
			return null == data ? "" : data.get(position).getNewsCategoryName();
		}

		@Override
		public int getCount()
		{
			return null == data ? 0 : data.size();
		}

		@Override
		public int getItemPosition(Object object)
		{
			return PagerAdapter.POSITION_NONE;
		}

		public Fragment getItem(int position)
		{
			Fragment fragment = MainFragment.newInstance(null == data ? null : data.get(position));
			mViewPager.setObjectForPosition(fragment, position);
			return fragment;
		}
	}

	/**
	 * TODO customSlidingMenu
	 * 
	 */
	private void customSlidingMenu()
	{
		this.mSlidingMenu = this.getSlidingMenu();
		setSlidingActionBarEnabled(true);
		mSlidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
		mSlidingMenu.setMode(SlidingMenu.LEFT_RIGHT);
		// mSlidingMenu.setBehindOffset((int)(50*getResources().getDisplayMetrics().density));
		mSlidingMenu.setBehindWidth(getResources().getDisplayMetrics().widthPixels / 2);
		setBehindContentView(R.layout.menu_frame);
		this.mSlidingMenu.setSecondaryMenu(R.layout.menu_second_frame);
		getSupportFragmentManager().beginTransaction().replace(R.id.menu_frame, LeftMenuFragment.newInstance()).commit();
		getSupportFragmentManager().beginTransaction().replace(R.id.menu_second_frame, RightMenuFragment.newInstance()).commit();
	}

	public void showContent()
	{
		this.mSlidingMenu.showContent(true);
	}

	/**
	 * TODO {@link View.OnClickListener}
	 */
	public void onClick(View v)
	{
		switch (v.getId())
		{
		case R.id.ibtn_subscribe:
			Intent intent = new Intent(this, SubscribedActivity.class);
			startActivity(intent);
			break;
		case R.id.ibtn_left_top:
			this.mSlidingMenu.showMenu(true);
			break;
		default:
			super.onClick(v);
			break;
		}
	}

	public PullToRefreshAttacher getPullToRefreshAttacher()
	{
		return mPullToRefreshAttacher;
	}

	public void updateLeftTopImgAndTitle(int resId, String title)
	{
		this.mIbtnLeftTop.setImageResource(resId);
		setTVTitle(title);
	}
}