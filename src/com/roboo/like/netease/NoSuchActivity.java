package com.roboo.like.netease;

import android.graphics.Canvas;
import android.graphics.PathEffect;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.PathShape;
import android.graphics.drawable.shapes.Shape;
import android.os.Bundle;
import android.preference.Preference;
import android.widget.TextView;

public class NoSuchActivity extends BaseActivity
{
	private TextView mTVAboutVersion;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		 setTVTitle(R.string.tv_crash);
		 //TODO setContentView Tag
		 throw new NullPointerException();
	}
}
