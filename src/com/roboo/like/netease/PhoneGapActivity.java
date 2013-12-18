package com.roboo.like.netease;

import org.apache.cordova.Config;
import org.apache.cordova.DroidGap;

import android.os.Bundle;

public class PhoneGapActivity extends DroidGap
{
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		//TODO 
		super.onCreate(savedInstanceState);
		super.loadUrl(Config.getStartUrl());
		//super.loadUrl("file:///android_asset/www/index.html");
		
	}
}
