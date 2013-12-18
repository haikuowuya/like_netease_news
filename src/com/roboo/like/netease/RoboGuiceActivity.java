package com.roboo.like.netease;

import roboguice.event.EventManager;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.inject.Inject;

@ContentView(R.layout.activity_roboguice)
public class RoboGuiceActivity extends BaseRoboActivity
{
	@InjectView(R.id.tv_roboguice)
	TextView mTVRoboguice;
	@Inject
	private EventManager eventManager;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{

		super.onCreate(savedInstanceState);
		setTVTitle(R.string.tv_roboguice);
		// TODO setContentView Tag // line 8
		// setContentView(R.layout.activity_roboguice);
		this.mTVRoboguice.setText(R.string.tv_roboguice);
		// 隐藏底部的导航
		// mTVRoboguice.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
		// DummyActivity :为系统home键的默认处理activity
	}
	 @Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.activity_roboguice, menu);
		return true;
		
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{
		case R.id.action_home:
			Intent intent = new Intent();
			intent.setAction(Intent.ACTION_MAIN);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			intent.addCategory(Intent.CATEGORY_HOME);
			startActivity(intent);
			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);

	}
}
