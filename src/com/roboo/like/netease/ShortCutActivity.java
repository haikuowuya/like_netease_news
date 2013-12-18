package com.roboo.like.netease;

import android.os.Bundle;
import android.widget.TextView;

public class ShortCutActivity extends BaseActivity
{
 
 
	 
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		
		super.onCreate(savedInstanceState);
		 setTVTitle(R.string.tv_about);
		 //TODO setContentView Tag
		 setContentView(R.layout.activity_short_cut);
	 
		 
	}
}
