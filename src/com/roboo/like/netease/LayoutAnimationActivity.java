package com.roboo.like.netease;

import java.util.Random;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.Keyframe;
import android.animation.LayoutTransition;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

@SuppressLint("NewApi")
public class LayoutAnimationActivity extends BaseActivity
{
	private TextView mTVEmpty;
	private LinearLayout mLinearContianer;
	private static String[] TITLES;
	private LayoutTransition mTransitioner;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setTVTitle(R.string.tv_layout_change_animation);
		// TODO setContentView Tag
		setContentView(R.layout.activity_layout_change_animation);
		TITLES = getResources().getStringArray(R.array.titles);
		initView();
		setLinearContainerAnimation();
	}
	private void setLinearContainerAnimation()
	{
		mTransitioner = new LayoutTransition();
 
		mTransitioner.setStagger(LayoutTransition.CHANGE_APPEARING, 30);
        mTransitioner.setStagger(LayoutTransition.CHANGE_DISAPPEARING, 30);
        setupCustomAnimations();
		this.mLinearContianer.setLayoutTransition(mTransitioner);

	}
	private void initView()
	{
		this.mTVEmpty = (TextView) findViewById(android.R.id.empty);
		this.mLinearContianer = (LinearLayout) findViewById(R.id.linear_container);

	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.activity_layout_change_animation, menu);
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{

		switch (item.getItemId())
		{
		case R.id.action_add_view:
			addNewView();
			break;

		default:
			super.onOptionsItemSelected(item);
			break;
		}
		return true;

	}
	private void addNewView()
	{
		mTVEmpty.setVisibility(View.GONE);
		final ViewGroup newView = (ViewGroup) LayoutInflater.from(this).inflate(
			R.layout.add_view_item, mLinearContianer, false);
		((TextView) newView.findViewById(android.R.id.text1)).setText(
			TITLES[new Random().nextInt(TITLES.length)]);

		newView.findViewById(R.id.ibtn_remove).setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				mLinearContianer.removeView(newView);
				if (mLinearContianer.getChildCount() == 0)
				{
					mTVEmpty.setVisibility(View.VISIBLE);
				}
			}
		});

		mLinearContianer.addView(newView, 0);
	}

	private void setupCustomAnimations()
	{
		// Changing while Adding
		PropertyValuesHolder pvhLeft =
			PropertyValuesHolder.ofInt("left", 0, 1);
		PropertyValuesHolder pvhTop =
			PropertyValuesHolder.ofInt("top", 0, 1);
		PropertyValuesHolder pvhRight =
			PropertyValuesHolder.ofInt("right", 0, 1);
		PropertyValuesHolder pvhBottom =
			PropertyValuesHolder.ofInt("bottom", 0, 1);
		PropertyValuesHolder pvhScaleX =
			PropertyValuesHolder.ofFloat("scaleX", 1f, 0f, 1f);
		PropertyValuesHolder pvhScaleY =
			PropertyValuesHolder.ofFloat("scaleY", 1f, 0f, 1f);
		final ObjectAnimator changeIn = ObjectAnimator.ofPropertyValuesHolder(
			this, pvhLeft, pvhTop, pvhRight, pvhBottom, pvhScaleX, pvhScaleY).
			setDuration(mTransitioner.getDuration(LayoutTransition.CHANGE_APPEARING));
		mTransitioner.setAnimator(LayoutTransition.CHANGE_APPEARING, changeIn);
		changeIn.addListener(new AnimatorListenerAdapter()
		{
			public void onAnimationEnd(Animator anim)
			{
				View view = (View) ((ObjectAnimator) anim).getTarget();
				view.setScaleX(1f);
				view.setScaleY(1f);
			}
		});

		// Changing while Removing
		Keyframe kf0 = Keyframe.ofFloat(0f, 0f);
		Keyframe kf1 = Keyframe.ofFloat(.9999f, 360f);
		Keyframe kf2 = Keyframe.ofFloat(1f, 0f);
		PropertyValuesHolder pvhRotation =
			PropertyValuesHolder.ofKeyframe("rotation", kf0, kf1, kf2);
		final ObjectAnimator changeOut = ObjectAnimator.ofPropertyValuesHolder(
			this, pvhLeft, pvhTop, pvhRight, pvhBottom, pvhRotation).
			setDuration(mTransitioner.getDuration(LayoutTransition.CHANGE_DISAPPEARING));
		mTransitioner.setAnimator(LayoutTransition.CHANGE_DISAPPEARING, changeOut);
		changeOut.addListener(new AnimatorListenerAdapter()
		{
			public void onAnimationEnd(Animator anim)
			{
				View view = (View) ((ObjectAnimator) anim).getTarget();
				view.setRotation(0f);
			}
		});

		// Adding
		ObjectAnimator animIn = ObjectAnimator.ofFloat(null, "rotationY", 90f, 0f).
			setDuration(mTransitioner.getDuration(LayoutTransition.APPEARING));
		mTransitioner.setAnimator(LayoutTransition.APPEARING, animIn);
		animIn.addListener(new AnimatorListenerAdapter()
		{
			public void onAnimationEnd(Animator anim)
			{
				View view = (View) ((ObjectAnimator) anim).getTarget();
				view.setRotationY(0f);
			}
		});

		// Removing
		ObjectAnimator animOut = ObjectAnimator.ofFloat(null, "rotationX", 0f, 90f).
			setDuration(mTransitioner.getDuration(LayoutTransition.DISAPPEARING));
		mTransitioner.setAnimator(LayoutTransition.DISAPPEARING, animOut);
		animOut.addListener(new AnimatorListenerAdapter()
		{
			public void onAnimationEnd(Animator anim)
			{
				View view = (View) ((ObjectAnimator) anim).getTarget();
				view.setRotationX(0f);
			}
		});

	}
}
