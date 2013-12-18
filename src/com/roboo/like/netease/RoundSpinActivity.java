package com.roboo.like.netease;

import com.roboo.like.netease.view.RoundSpinView;

import android.os.Bundle;

public class RoundSpinActivity extends BaseActivity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		//TODO 
		super.onCreate(savedInstanceState);
		setTVTitle(R.string.tv_round_spin);
		setContentView(new RoundSpinView(getApplicationContext(), 300, 300, 200));
		
	}
}
