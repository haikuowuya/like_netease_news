package com.roboo.like.netease;

import android.os.Bundle;

public class SurfaceViewActivity extends BaseActivity
{
		@Override
		protected void onCreate(Bundle savedInstanceState)
		{
			super.onCreate(savedInstanceState);
			setTVTitle(R.string.tv_surface_view);
			//TODO setContentView  Tag
			setContentView(R.layout.activity_surface_view);
			
		}

}	
