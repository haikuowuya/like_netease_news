package com.roboo.like.netease;

import android.os.Bundle;
import android.support.v4.widget.SlidingPaneLayout;
import android.view.WindowManager.LayoutParams;
import android.widget.TextView;

public class AboutActivity extends BaseActivity
{

	private TextView mTVAboutVersion;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setTVTitle(R.string.tv_about);
		// TODO setContentView Tag
		setContentView(R.layout.activity_about);
		this.mTVAboutVersion = (TextView) findViewById(R.id.tv_about_version);
		this.mTVAboutVersion.setText(getString(R.string.tv_about_version, "3.5.00"));
		String str = getString(R.string.tv_about_version);
		str = String.format(str, "3.555555");
		System.out.println("str = " + str);

	}
}
