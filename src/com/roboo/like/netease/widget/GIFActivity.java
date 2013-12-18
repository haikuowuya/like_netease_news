package com.roboo.like.netease.widget;

import android.os.Bundle;

import com.roboo.like.netease.BaseActivity;
import com.roboo.like.netease.R;
import com.roboo.like.netease.view.GifImageView;

public class GIFActivity extends BaseActivity
{
	private GifImageView mGifImageView ;
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setTVTitle(R.string.tv_gif);
		 
		// TODO setContentView Tag
		setContentView(R.layout.activity_gif);
		initView();
	}
	private void initView()
	{
		 this.mGifImageView = (GifImageView) findViewById(R.id.giv_img);
	}
}
