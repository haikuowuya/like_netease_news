package com.roboo.like.netease.view;

import android.R.integer;
import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Scroller;

public class ScrollerViewGroup extends ViewGroup
{
	private Context mContext;
	private Scroller mScroller;
	private int mCurrentScreen;
	// 两种状态: 是否处于滑屏状态
	private static final int TOUCH_STATE_REST = 0; // 什么都没做的状态
	private static final int TOUCH_STATE_SCROLLING = 1; // 开始滑屏的状态
	private int mTouchState = TOUCH_STATE_REST; // 默认是什么都没做的状态
	// 处理触摸事件 ~
	public static int SNAP_VELOCITY = 600; // 最小的滑动速率
	private int mTouchSlop = 0; // 最小滑动距离，超过了，才认为开始滑动
	private float mLastionMotionX = 0; // 记住上次触摸屏的位置
	private static int mWidth;
	private static int mHeight;
	// 处理触摸的速率
	private VelocityTracker mVelocityTracker = null;
	/**
	 * 触发滚动到下一屏的距离
	 */
	private int mToggleScrollDistance;

	public ScrollerViewGroup(Context context)
	{
		super(context);
		this.mContext = context;
		init();
	}

	public ScrollerViewGroup(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
		this.mContext = context;
		init();
	}

	public ScrollerViewGroup(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		this.mContext = context;
		init();
	}

	private void init()
	{
		this.mToggleScrollDistance = ViewConfiguration.get(mContext).getScaledTouchSlop();
		mHeight = getResources().getDisplayMetrics().heightPixels;
		mWidth = getResources().getDisplayMetrics().widthPixels;
		this.mScroller = new Scroller(mContext);
		LinearLayout firstLinear, secondLinear, thirdLinear;
		firstLinear = new LinearLayout(mContext);
		firstLinear.setBackgroundColor(Color.parseColor("#88FF2340"));
		secondLinear = new LinearLayout(mContext);
		secondLinear.setBackgroundColor(Color.parseColor("#8800FF30"));
		thirdLinear = new LinearLayout(mContext);
		thirdLinear.setBackgroundColor(Color.parseColor("#880000FF"));
		this.addView(firstLinear);
		this.addView(secondLinear);
		this.addView(thirdLinear);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
	{
		setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.getSize(heightMeasureSpec));
		for (int i = 0; i < getChildCount(); i++)
		{
			getChildAt(i).measure(mWidth, mHeight);
		}
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b)
	{
		for (int i = 0; i < getChildCount(); i++)
		{
			getChildAt(i).layout(mWidth * i, 0, mWidth * (i + 1), mHeight);
			// getChildAt(i).layout(160*i, 0, 160*(i+1), mHeight);
		}
	}

	@Override
	public void computeScroll()
	{
		super.computeScroll();
		// 如果返回true，表示动画还没有结束
		// 因为前面startScroll，所以只有在startScroll完成时 才会为false
		if (mScroller.computeScrollOffset())
		{
			// 产生了动画效果，根据当前值 每次滚动一点
			scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
			// 此时同样也需要刷新View ，否则效果可能有误差
			postInvalidate();
		}
	}

	public boolean onTouchEvent(MotionEvent event)
	{
		super.onTouchEvent(event);
		// 获得VelocityTracker对象，并且添加滑动对象
		if (mVelocityTracker == null)
		{
			mVelocityTracker = VelocityTracker.obtain();
		}
		mVelocityTracker.addMovement(event);
		// 触摸点
		float x = event.getX();
		float y = event.getY();
		switch (event.getAction())
		{
		case MotionEvent.ACTION_DOWN:
			// 如果屏幕的动画还没结束，你就按下了，我们就结束上一次动画，即开始这次新ACTION_DOWN的动画
			if (mScroller != null)
			{
				if (!mScroller.isFinished())
				{
					mScroller.abortAnimation();
				}
			}
			mLastionMotionX = x; // 记住开始落下的屏幕点
			break;
		case MotionEvent.ACTION_MOVE:
			int detaX = (int) (mLastionMotionX - x); // 每次滑动屏幕，屏幕应该移动的距离
			scrollBy(detaX, 0);// 开始缓慢滑屏咯。 detaX > 0 向右滑动 ， detaX < 0 向左滑动 ，
			mLastionMotionX = x;
			break;
		case MotionEvent.ACTION_UP:
			final VelocityTracker velocityTracker = mVelocityTracker;
			velocityTracker.computeCurrentVelocity(1000);
			// 计算速率
			int velocityX = (int) velocityTracker.getXVelocity();
			// 滑动速率达到了一个标准(快速向右滑屏，返回上一个屏幕) 马上进行切屏处理
			if (velocityX > SNAP_VELOCITY && mCurrentScreen > 0)
			{
				snapToScreen(mCurrentScreen - 1);
			}
			// 快速向左滑屏，返回下一个屏幕)
			else if (velocityX < -SNAP_VELOCITY && mCurrentScreen < (getChildCount() - 1))
			{
				snapToScreen(mCurrentScreen + 1);
			}
			// 以上为快速移动的 ，强制切换屏幕
			else
			{
				// 我们是缓慢移动的，因此先判断是保留在本屏幕还是到下一屏幕
				snapToDestination();
			}
			// 回收VelocityTracker对象
			if (mVelocityTracker != null)
			{
				mVelocityTracker.recycle();
				mVelocityTracker = null;
			}
			// 修正mTouchState值
			mTouchState = TOUCH_STATE_REST;

			break;
		case MotionEvent.ACTION_CANCEL:
			mTouchState = TOUCH_STATE_REST;
			break;
		}
		return true;
	}

	// //我们是缓慢移动的，因此需要根据偏移值判断目标屏是哪个？
	private void snapToDestination()
	{
		// 当前的偏移位置
		int scrollX = getScrollX();
		int scrollY = getScrollY();
		// 判断是否超过下一屏的中间位置，如果达到就抵达下一屏，否则保持在原屏幕
		// 直接使用这个公式判断是哪一个屏幕 前后或者自己
		// 判断是否超过下一屏的中间位置，如果达到就抵达下一屏，否则保持在原屏幕
		// 这样的一个简单公式意思是：假设当前滑屏偏移值即 scrollCurX 加上每个屏幕一半的宽度，除以每个屏幕的宽度就是
		// 我们目标屏所在位置了。 假如每个屏幕宽度为320dip, 我们滑到了500dip处，很显然我们应该到达第二屏
		int destScreen = (getScrollX() + mWidth / 2) / mWidth;

		snapToScreen(destScreen);
	}

	// 真正的实现跳转屏幕的方法
	private void snapToScreen(int whichScreen)
	{
		// 简单的移到目标屏幕，可能是当前屏或者下一屏幕
		// 直接跳转过去，不太友好
		// scrollTo(mLastScreen * MultiScreenActivity.screenWidth, 0);
		// 为了友好性，我们在增加一个动画效果
		// 需要再次滑动的距离 屏或者下一屏幕的继续滑动距离

		mCurrentScreen = whichScreen;
		// 防止屏幕越界，即超过屏幕数
		if (mCurrentScreen > getChildCount() - 1)
		{
			// mCurrentScreen = getChildCount() - 1;
			// 循环到第一屏
			mCurrentScreen = 0;
			int dx = getChildCount() * getWidth() - getScrollX();
			mScroller.startScroll(-480, 0, 480, 0, Math.abs(dx) * 2);
		}
		else if (mCurrentScreen <= 0)
		{
			 
			// mCurrentScreen = getChildCount() -1;
			// 循环到最后一屏
			// int dx = getScrollX();
			// mScroller.startScroll(getChildCount() * getWidth()+dx, 0,
			// (getChildCount()-1) * getWidth(), 0, Math.abs(dx) * 2);
		}

		// 为了达到下一屏幕或者当前屏幕，我们需要继续滑动的距离.根据dx值，可能向左滑动，也可能向右滑动
		int dx = mCurrentScreen * getWidth() - getScrollX();
		mScroller.startScroll(getScrollX(), 0, dx, 0, Math.abs(dx) * 2);
 
		// 由于触摸事件不会重新绘制View，所以此时需要手动刷新View 否则没效果
		invalidate();
	}
}
