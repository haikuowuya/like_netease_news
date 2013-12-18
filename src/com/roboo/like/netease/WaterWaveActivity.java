package com.roboo.like.netease;

import com.roboo.like.netease.view.WaterWaveView;

import android.os.Bundle;

public class WaterWaveActivity extends BaseActivity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
	 
		super.onCreate(savedInstanceState);
		setTVTitle(R.string.tv_water_wave);
		WaterWaveView view = new WaterWaveView(this);
		setContentView(view);
	}
}
