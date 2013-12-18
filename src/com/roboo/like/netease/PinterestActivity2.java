package com.roboo.like.netease;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class PinterestActivity2 extends BaseActivity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		 
		super.onCreate(savedInstanceState);
		 setTVTitle(R.string.tv_pinterest2);
		 //TODO setContentView Tag
		  setContentView(R.layout.activity_pinterest2);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		menu.add(R.string.tv_gridview_cache_adapter);
		return true;
		
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		Intent intent = new Intent(this, GridViewCacheActivity.class);
		startActivity(intent);
		return true;
		
	}
	 
}
