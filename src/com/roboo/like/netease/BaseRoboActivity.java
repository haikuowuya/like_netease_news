package com.roboo.like.netease;

import roboguice.activity.RoboActivity;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Debug;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.roboo.like.netease.commons.CrashException;
import com.roboo.like.netease.dao.ICityDao;
import com.roboo.like.netease.dao.impl.CityDaoImpl;
import com.roboo.like.netease.database.DBHelper;
import com.roboo.like.netease.view.MyProgressBar;
import com.roboo.like.netease.view.fragment.ShareDialogFragment;

@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
public class BaseRoboActivity extends RoboActivity implements OnClickListener
{
	protected ActionBar mActionBar;
	private View mCustomView;
	private MyProgressBar mProgressBar;
	private Button mBtnCityName;
	private ImageButton mIbtnLeftTop;
	private ImageButton mIBtnLocation;
	private ImageButton mIbtnShare;
	private TextView mTVTitle;
	private ImageButton mIbtnSearch;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		NewsApplication.mActivities.add(this);
		Thread.setDefaultUncaughtExceptionHandler(CrashException.getInstance(this));
		Debug.startMethodTracing("hello_trace");
		//getWindow().getDecorView() instanceOf FrameLayout
	    // System.out.println("getWindow().getDecorView() = "+getWindow().getDecorView());
		ViewServer.get(this).addWindow(this);
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		if (isActionBarAvailable())
		{
			customActionBar();
		}
	// throw new  NullPointerException();
	}
	/**
	 * TODO {@link ActionBar}
	 * 
	 */
	private void customActionBar()
	{
		initCustomView();
		this.mActionBar = getActionBar();
		this.mActionBar.setDisplayShowTitleEnabled(false);
		this.mActionBar.setDisplayShowHomeEnabled(false);
		this.mActionBar.setDisplayHomeAsUpEnabled(false);
		this.mActionBar.setDisplayShowCustomEnabled(true);
		this.mActionBar.setBackgroundDrawable(getResources().getDrawable(
			R.drawable.action_bar_background));
		this.mActionBar.setSplitBackgroundDrawable(getResources().getDrawable(
			R.drawable.action_bar_background));
		this.mActionBar.setCustomView(mCustomView);
		this.mIbtnLeftTop.setOnClickListener(this);
	}
	/**
	 * TODO init ActionBar's customView
	 * 
	 */
	private void initCustomView()
	{
		mCustomView = LayoutInflater.from(this).inflate(R.layout.actionbar_custom_view, null);
		this.mIbtnLeftTop = (ImageButton) mCustomView.findViewById(R.id.ibtn_left_top);
		this.mTVTitle = (TextView) mCustomView.findViewById(R.id.tv_title);
		this.mIBtnLocation = (ImageButton) mCustomView.findViewById(R.id.ibtn_location);
		this.mIbtnShare = (ImageButton) mCustomView.findViewById(R.id.ibtn_share);
		this.mBtnCityName = (Button) mCustomView.findViewById(R.id.btn_city_name);
		this.mProgressBar = (MyProgressBar) mCustomView.findViewById(R.id.mpb_progress);
		this.mIbtnSearch  = (ImageButton)mCustomView.findViewById(R.id.ibtn_search);
	}
	public void setTVTitle(int stringId)
	{
		this.mTVTitle.setText(stringId);
	}
	public void setTVTitle(CharSequence title)
	{
		this.mTVTitle.setText(title);
	}
	public void setLeftTopBtnSrc(int resId)
	{
		this.mIbtnLeftTop.setImageResource(resId);
	}
	public boolean isActionBarAvailable()
	{
		return !getWindow().hasFeature(Window.FEATURE_NO_TITLE);
	}
	/**
	 * TODO is show search ibtn
	 * 
	 * @param isShow
	 */
	public void showSearchBtn(boolean isShow)
	{
		if (isShow)
		{
			this.mIbtnSearch.setVisibility(View.VISIBLE);
			this.mIbtnSearch.setOnClickListener(this);
		}
		else
		{
			this.mIbtnSearch.setVisibility(View.GONE);
		}
	}
	/**
	 * TODO is show location btn
	 * 
	 * @param isShow
	 */
	public void showLocationBtn(boolean isShow)
	{
		if (isShow)
		{
			this.mIBtnLocation.setVisibility(View.VISIBLE);
			this.mIBtnLocation.setOnClickListener(this);
		}
		else
		{
			this.mProgressBar.setVisibility(View.GONE);
		}
	}
	/**
	 * TODO is show customed progress bar
	 * 
	 * @param isShow
	 */
	public void showMyProgressBar(boolean isShow)
	{
		if (isShow)
		{
			this.mProgressBar.setVisibility(View.VISIBLE);
		}
		else
		{
			this.mProgressBar.setVisibility(View.GONE);
		}
	}
	/**
	 * TODO is show city name btn
	 * 
	 * @param isShow
	 */
	public void showCityNameBtn(boolean isShow, CharSequence cityName)
	{
		if (isShow)
		{
			this.mBtnCityName.setText(cityName);
			this.mBtnCityName.setVisibility(View.VISIBLE);
			this.mBtnCityName.setOnClickListener(this);
		}
		else
		{
			this.mBtnCityName.setVisibility(View.GONE);
		}
	}
	/**
	 * TODO is show share btn
	 * 
	 * @param isShow
	 */
	public void showShareBtn(boolean isShow)
	{
		if (isShow)
		{
			this.mIbtnShare.setVisibility(View.VISIBLE);
			this.mIbtnShare.setOnClickListener(this);
		}
	}
	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
		case R.id.ibtn_left_top:
			setResult(RESULT_CANCELED);
			this.finish();
			break;
		case R.id.ibtn_location:// use startActivityForResult method
			// startActivity(new Intent(this,CityListActivity.class));
			break;
		case R.id.ibtn_share:
			ShareDialogFragment dialogFragment = ShareDialogFragment.newInstance();
			dialogFragment.show(getFragmentManager(), "ibtn_share");
			break;
		case R.id.btn_city_name:
			String cityName = this.mBtnCityName.getText() + "";
			ICityDao cityDao = new CityDaoImpl(new DBHelper(this));
			cityDao.setSelectedCity(cityDao.getCityCodeByCityName(cityName));
			setResult(RESULT_OK);
			this.finish();
			break;
		case R.id.ibtn_search:
			startSearch("",true, null, false);
			break;
		default:
			break;
		}

	}
	@Override
	public void onActionModeStarted(ActionMode mode)
	{
		 mode.setTitle(mTVTitle.getText());
		super.onActionModeStarted(mode);

	}
	@Override
	public void onActionModeFinished(ActionMode mode)
	{
		//TODO 
		super.onActionModeFinished(mode);
		
	}
	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		ViewServer.get(this).removeWindow(this);
	}
	@Override
	protected void onResume()
	{
		super.onResume();
		ViewServer.get(this).setFocusedWindow(this);
	}
	@Override
	protected void onStop()
	{
		//TODO 
		super.onStop();
		Debug.stopMethodTracing();
	}
}
