package com.roboo.like.netease;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class PinterestActivity extends BaseActivity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		 
		super.onCreate(savedInstanceState);
		 setTVTitle(R.string.tv_pinterest);
		 //TODO setContentView Tag
		  setContentView(R.layout.activity_pinterest);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		menu.add(R.string.tv_pinterest2);
		return true;
		
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		Intent intent = new Intent(this, PinterestActivity2.class);
		startActivity(intent);
		return true;
		
	}
}
