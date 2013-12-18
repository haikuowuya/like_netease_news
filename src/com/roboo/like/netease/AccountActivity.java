package com.roboo.like.netease;

import android.os.Bundle;

public class AccountActivity extends BaseActivity
{
		@Override
		protected void onCreate(Bundle savedInstanceState)
		{
			super.onCreate(savedInstanceState);
			setTVTitle(R.string.tv_account);
			//TODO setContentView Tag
			setContentView(R.layout.activity_account);
		}

}	
