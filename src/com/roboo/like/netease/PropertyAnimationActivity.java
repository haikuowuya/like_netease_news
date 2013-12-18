package com.roboo.like.netease;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

@SuppressLint("NewApi")
public class PropertyAnimationActivity extends BaseActivity
{
	private ImageView mImageView;
	private ImageView mImageView2;
 
 
	@SuppressLint("NewApi")
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		 setTVTitle(R.string.tv_property_animation);
		 //TODO setContentView Tag
		 setContentView(R.layout.activity_property_animation);
		 initView();
		this.mImageView.setOnClickListener(this);
		this.mImageView2.setOnClickListener(this);
	}
	 
	private void initView()
	{
		this.mImageView = (ImageView) findViewById(R.id.iv_image);
		this.mImageView2 = (ImageView) findViewById(R.id.iv_image2);
	}
	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
		case R.id.iv_image:
			 ObjectAnimator animator1 = ObjectAnimator.ofFloat(this.mImageView,  "x", 240f);
			 ObjectAnimator animator2 = ObjectAnimator.ofFloat(this.mImageView,  "y", 0f);
			AnimatorSet set = new AnimatorSet();
			set.play(animator1).with(animator2);
			set.setDuration(1000);
			set.start();
			set.addListener(new AnimatorListener()
			{
				@Override
				public void onAnimationStart(Animator animation)
				{
				 Toast.makeText(getBaseContext(), "动画开始",Toast.LENGTH_SHORT).show();
					
				}
				
				@Override
				public void onAnimationRepeat(Animator animation)
				{
				 
					Toast.makeText(getBaseContext(), "动画重复",Toast.LENGTH_SHORT).show();
					
				}
				
				@Override
				public void onAnimationEnd(Animator animation)
				{
					Toast.makeText(getBaseContext(), "动画结束",Toast.LENGTH_SHORT).show();
					 
					
				}
				
				@Override
				public void onAnimationCancel(Animator animation)
				{
					  
					Toast.makeText(getBaseContext(), "动画取消",Toast.LENGTH_SHORT).show();
					
				}
			});
			break;
		case R.id.iv_image2:
			ObjectAnimator animator12 = ObjectAnimator.ofFloat(this.mImageView2,  "rotationX",  0f,300f);
			animator12.setDuration(1000);	 
			animator12.start();
		 
		default:
			break;
		} 
		super.onClick(v);
		
	}
}
