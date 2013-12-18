package com.roboo.like.netease;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;

public class LocalBroadCastManagerActivity extends BaseActivity
{
	private static final String ACTION_START ="service start";
	private static final String ACTION_DESTORY="service destory";
 
	private static final String ACTION_CREATE="service create";
	
	private  static LocalBroadcastManager  mLocalBroadcastManager;
	private BroadcastReceiver mReceiver = new BroadcastReceiver(){

		@Override
		public void onReceive(Context context, Intent intent)
		{
		 if(ACTION_START.equals(intent.getAction()))
		 {
			 System.out.println(ACTION_START);
		 }
		  
		 else if(ACTION_DESTORY.equals(intent.getAction()))
		 {
			 System.out.println(ACTION_DESTORY);
		 }
		 else if(ACTION_CREATE.equals(intent.getAction()))
		 {
			 System.out.println(ACTION_CREATE);
		 }
		}
		
	};
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setTVTitle(R.string.tv_local_broadcast_manager); 
		mLocalBroadcastManager = LocalBroadcastManager.getInstance(LocalBroadCastManagerActivity.this); 
		IntentFilter filter = new IntentFilter();
		filter.addAction(ACTION_CREATE);
		filter.addAction(ACTION_DESTORY);
		filter.addAction(ACTION_START);
 
		mLocalBroadcastManager.registerReceiver(mReceiver, filter);
		startService(new Intent(this,LocalBroadCastManagerService.class));
	}
	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		mLocalBroadcastManager.unregisterReceiver(mReceiver);
	}
	private static  class LocalBroadCastManagerService extends Service
	{
		@Override
		public IBinder onBind(Intent intent)
		{
			return null;
		}
		@Override
		public void onCreate()
		{
			super.onCreate();
			mLocalBroadcastManager.sendBroadcast(new Intent(ACTION_CREATE));
		}
		 @Override
		public int onStartCommand(Intent intent, int flags, int startId)
		{
				mLocalBroadcastManager.sendBroadcast(new Intent(ACTION_START));
			return super.onStartCommand(intent, flags, startId);
		}
		public void onDestroy()
		{
			mLocalBroadcastManager.sendBroadcast(new Intent(ACTION_DESTORY));
			super.onDestroy();
		}
	}
}
