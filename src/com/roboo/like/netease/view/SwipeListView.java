package com.roboo.like.netease.view;

import android.R.anim;
import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.ListView;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorListenerAdapter;
import com.nineoldandroids.animation.ValueAnimator;
import com.nineoldandroids.animation.ValueAnimator.AnimatorUpdateListener;
import com.nineoldandroids.view.ViewHelper;

public class SwipeListView extends ListView implements OnTouchListener
{

	private int mSlop;
	/** 最小手指滑动速率 */
	private int mMinFlingVelocity;
	/** 最大手指滑动速率 */
	private int mMaxFlingVelocity;
	/** 滑动动画持续时间 */
	private long mAnimationDurationTime;
	/** 手指触摸屏幕时记录X位置 */
	private float mDownX;
	/** 是否在滑动的标志 */
	private boolean mIsSwipping;
	/** 移动速率测量器 */
	private VelocityTracker mVelocityTracker;
	/** 手指按下时对应View的位置 */
	private int mDownPosition;
	/** 手指按下时对应的View */
	private View mDownView;
	private OnDismissCallback mCallback;
	private int mViewWidth = 1;

	public SwipeListView(Context context)
	{
		this(context, null);
	}

	public SwipeListView(Context context, AttributeSet attrs)
	{
		this(context, attrs, 0);
	}

	public SwipeListView(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
		init();
	}

	public void init()
	{
		ViewConfiguration configuration = ViewConfiguration.get(getContext());
		mSlop = configuration.getScaledTouchSlop();
		mMinFlingVelocity = configuration.getScaledMinimumFlingVelocity();
		mMaxFlingVelocity = configuration.getScaledMaximumFlingVelocity();
		mAnimationDurationTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

	}

	@Override
	public boolean onTouch(View v, MotionEvent motionEvent)
	{
		if (mViewWidth < 2)
		{
			mViewWidth = getWidth();
		}

		switch (motionEvent.getActionMasked())
		{
		case MotionEvent.ACTION_DOWN:
			Rect rect = new Rect();
			int childCount = getChildCount();
			int[] coords = new int[2];
			getLocationOnScreen(coords);
			int x = (int) (motionEvent.getRawX() - coords[0]);
			int y = (int) (motionEvent.getRawY() - coords[1]);
			View childView;
			for (int i = 0; i < childCount; i++)
			{
				childView = getChildAt(i);
				childView.getHitRect(rect);
				if (rect.contains(x, y))
				{
					mDownView = childView;
					break;
				}
			}
			if (null != mDownView)
			{
				mDownX = motionEvent.getRawX();
				mDownPosition = getPositionForView(mDownView);
				mVelocityTracker = VelocityTracker.obtain();
				mVelocityTracker.addMovement(motionEvent);

			}
			v.onTouchEvent(motionEvent);
			return true;
		case MotionEvent.ACTION_UP:
		{
			if (mVelocityTracker == null)
			{
				break;
			}
			float deltaX = motionEvent.getRawX() - mDownX;
			mVelocityTracker.addMovement(motionEvent);
			mVelocityTracker.computeCurrentVelocity(1000);
			float velocityX = Math.abs(mVelocityTracker.getXVelocity());
			float velocityY = Math.abs(mVelocityTracker.getYVelocity());
			boolean dismiss = false;
			boolean dismissRight = false;
			if (Math.abs(deltaX) > mViewWidth / 2)
			{
				dismiss = true;
				dismissRight = deltaX > 0;
			}
			//当在X方向上的速率大于最小阀值，并且小于最大阀值，并且在大于在Y方向上的速率时，认为满足X方向滑动操作
			else if(mMinFlingVelocity <=velocityX && velocityX <=mMaxFlingVelocity && velocityY < velocityX)
			{
				dismiss = true;
				dismissRight = mVelocityTracker.getXVelocity() > 0;
			}
			if(dismiss)
			{
				com.nineoldandroids.view.ViewPropertyAnimator.animate(mDownView).translationX(dismissRight?mViewWidth:-mViewWidth)
				.alpha(0).setDuration(mAnimationDurationTime)
				.setListener(new com.nineoldandroids.animation.AnimatorListenerAdapter()
				{
					 public void onAnimationEnd(Animator animation) {
                         performDismiss(mDownView, mDownPosition);
                     }
				});
			}
			else 
			{
				com.nineoldandroids.view.ViewPropertyAnimator.animate(mDownView).translationX(0)
				.alpha(1).setDuration(mAnimationDurationTime).setListener(null);
			}
			mVelocityTracker = null;
			mDownX =0;
			mDownView =null;
			mDownPosition = ListView.INVALID_POSITION;
			mIsSwipping = false;
		}
		break;
		case MotionEvent.ACTION_MOVE:
		{
			if(mVelocityTracker == null)
			{
				break;
				
			}
			mVelocityTracker.addMovement(motionEvent);
			float deltaX = motionEvent.getRawX() - mDownX;
			if(Math.abs(deltaX) > mSlop)
			{
				mIsSwipping = true;
				requestDisallowInterceptTouchEvent(true);
				MotionEvent cancleEvent = MotionEvent.obtain(motionEvent);
				cancleEvent.setAction(MotionEvent.ACTION_CANCEL|(motionEvent.getActionIndex() << MotionEvent.ACTION_POINTER_INDEX_SHIFT));
				onTouchEvent(cancleEvent);
			}
			if(mIsSwipping)
			{
				 ViewHelper.setTranslationX(mDownView, deltaX);
                 ViewHelper.setAlpha(mDownView, Math.max(0f, Math.min(1f,
                         1f - 2f * Math.abs(deltaX) / mViewWidth)));
                 return true;
			}
			break;
		}
		default:
			break;
		}
		return false;
	}
	private void performDismiss(final View dismissView ,final int dismissPosition)
	{
		final ViewGroup.LayoutParams params = dismissView.getLayoutParams();
		final int originHeight = dismissView.getHeight();
		ValueAnimator animator = ValueAnimator.ofInt(originHeight,1).setDuration(mAnimationDurationTime);
		animator.addListener(new AnimatorListenerAdapter()
		{
			@Override
			public void onAnimationEnd(Animator animation)
			{
				super.onAnimationEnd(animation);
			}
		});
		animator.addUpdateListener(new AnimatorUpdateListener()
		{
			
			@Override
			public void onAnimationUpdate(ValueAnimator animation)
			{
				 params.height = (Integer) animation.getAnimatedValue();
				 dismissView.setLayoutParams(params);
			}
		});
		animator.start();
		
		
		
		
		
		
		
		
		
		
		
		
		
	}
	public void setOnDismissCallback(OnDismissCallback callback)
	{
		this.mCallback = callback;
	}

	public interface OnDismissCallback
	{
		public void onDismiss();
	}

}
