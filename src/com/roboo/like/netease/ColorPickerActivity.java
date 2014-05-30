package com.roboo.like.netease;

import android.app.DialogFragment;
import android.os.Bundle;

public class ColorPickerActivity extends BaseActivity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setTVTitle(R.string.tv_color_picker);
		// TODO setContentView Tag
		setContentView(R.layout.activity_color_picker);
 
	}
}
