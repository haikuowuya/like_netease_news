package com.roboo.like.netease;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

import com.roboo.like.netease.view.ScrollerViewGroup;

public class ScrollerActivity extends BaseActivity
{
	private ScrollerViewGroup mViewGroup;
	private int mCurrentScreen = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setTVTitle(R.string.tv_scroller);
		// TODO setContentView Tag
		mViewGroup = new ScrollerViewGroup(this);
		setContentView(mViewGroup);
//		mViewGroup.setOnTouchListener(new OnTouchListener()
//		{
//			@Override
//			public boolean onTouch(View v, MotionEvent event)
//			{
//				mCurrentScreen++;
//				mViewGroup.scrollTo(480*(mCurrentScreen%mViewGroup.getChildCount()==0?0:mCurrentScreen%mViewGroup.getChildCount()), 0);
//				
//				return false;
//				
//			}
//		});
	}

}
