package com.roboo.like.netease;

import javax.crypto.Mac;

import com.nineoldandroids.animation.ObjectAnimator;

import android.app.ActionBar;
import android.app.ActionBar.OnNavigationListener;
import android.os.Bundle;
import android.widget.ArrayAdapter;

/**
 * 
 * @author bo.li
 * 
 *         2013-12-16 下午4:41:47
 * 
 *         TODO 测试ActionBar的List导航模式
 */
public class ListNavModeActivity extends BaseActivity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{

		super.onCreate(savedInstanceState);
		setTVTitle(R.string.tv_list_nav);
		// TODO setContentView Tag
		setContentView(R.layout.activity_list_nav);
		mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
		mActionBar.setDisplayHomeAsUpEnabled(false);
		mActionBar.setDisplayUseLogoEnabled(false);
		mActionBar.setDisplayShowHomeEnabled(false);
		mActionBar.setDisplayShowTitleEnabled(false);
		mActionBar.setDisplayShowCustomEnabled(false);
		mActionBar.setListNavigationCallbacks(ArrayAdapter.createFromResource(this, R.array.titles, android.R.layout.simple_list_item_1), new OnNavigationListener()
		{
			public boolean onNavigationItemSelected(int itemPosition, long itemId)
			{
				rotate();
				return false;
			}

		});
	}
	private void rotate()
	{
		ObjectAnimator.ofFloat(findViewById(R.id.view1), "rotationY", 0, 180).setDuration(2000).start();
	}
}
