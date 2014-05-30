package com.roboo.like.netease.view;

import com.roboo.like.netease.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Scroller;
import android.widget.Toast;

//public class OverScrollListView extends ListView implements OnScrollListener, View.OnTouchListener, android.widget.AdapterView.OnItemSelectedListener
//{
//
//	protected static float BREAKSPEED = 4f, ELASTICITY = 0.67f;
//	public Handler mHandler = new Handler();
//	public int nHeaders = 1, nFooters = 1, divHeight = 0, delay = 10;
//	private int firstVis, visibleCnt, lastVis, totalItems, scrollstate;
//	private boolean bounce = true, rebound = false, recalcV = false, trackballEvent = false;
//	private long flingTimestamp;
//	private float velocity;
//	private View measure;
//	private GestureDetector gesture;
//
//	public OverScrollListView(Context context)
//	{
//		super(context);
//		initialize(context);
//	}
//
//	public OverScrollListView(Context context, AttributeSet attrs)
//	{
//		super(context, attrs);
//		initialize(context);
//	}
//
//	public OverScrollListView(Context context, AttributeSet attrs, int defStyle)
//	{
//		super(context, attrs, defStyle);
//		initialize(context);
//	}
//
//	@Override
//	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount)
//	{
//		firstVis = firstVisibleItem;
//		visibleCnt = visibleItemCount;
//		totalItems = totalItemCount;
//		lastVis = firstVisibleItem + visibleItemCount;
//	}
//
//	@Override
//	public void onScrollStateChanged(AbsListView view, int scrollState)
//	{
//		scrollstate = scrollState;
//		if (scrollState != OnScrollListener.SCROLL_STATE_TOUCH_SCROLL)
//		{
//			rebound = true;
//			mHandler.postDelayed(checkListviewTopAndBottom, delay);
//		}
//	}
//
//	@Override
//	public void onItemSelected(AdapterView<?> av, View v, int position, long id)
//	{
//		rebound = true;
//		mHandler.postDelayed(checkListviewTopAndBottom, delay);
//	}
//
//	@Override
//	public void onNothingSelected(AdapterView<?> av)
//	{
//		rebound = true;
//		mHandler.postDelayed(checkListviewTopAndBottom, delay);
//	}
//
//	@Override
//	public boolean onTrackballEvent(MotionEvent event)
//	{
//		trackballEvent = true;
//		rebound = true;
//		mHandler.postDelayed(checkListviewTopAndBottom, delay);
//		return super.onTrackballEvent(event);
//	}
//
//	@Override
//	public boolean onTouch(View v, MotionEvent event)
//	{
//		gesture.onTouchEvent(event);
//		return false;
//	}
//
//	private class gestureListener implements OnGestureListener
//	{
//		@Override
//		public boolean onDown(MotionEvent e)
//		{
//			rebound = false;
//			recalcV = false;
//			velocity = 0f;
//			return false;
//		}
//
//		@Override
//		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY)
//		{
//			rebound = true;
//			recalcV = true;
//			velocity = velocityY / 25f;
//			flingTimestamp = System.currentTimeMillis();
//			return false;
//		}
//
//		@Override
//		public void onLongPress(MotionEvent e)
//		{
//		}
//
//		@Override
//		public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY)
//		{
//			return false;
//		}
//
//		@Override
//		public void onShowPress(MotionEvent e)
//		{
//		}
//
//		@Override
//		public boolean onSingleTapUp(MotionEvent e)
//		{
//			rebound = true;
//			recalcV = false;
//			velocity = 0f;
//			return false;
//		}
//	};
//
//	private void initialize(Context context)
//	{
//		View header = new View(context);
//		header.setMinimumHeight(((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getHeight());
//		addHeaderView(header, null, false);
//		addFooterView(header, null, false);
//
//		gesture = new GestureDetector(new gestureListener());
//		gesture.setIsLongpressEnabled(false);
//		flingTimestamp = System.currentTimeMillis();
//		setHeaderDividersEnabled(false);
//		setFooterDividersEnabled(false);
//		setOnTouchListener(this);
//		setOnScrollListener(this);
//		setOnItemSelectedListener(this);
//	}
//
//	public void initializeValues()
//	{
//		nHeaders = getHeaderViewsCount();
//		nFooters = getFooterViewsCount();
//		divHeight = getDividerHeight();
//		firstVis = 0;
//		visibleCnt = 0;
//		lastVis = 0;
//		totalItems = 0;
//		scrollstate = 0;
//		rebound = true;
//		setSelectionFromTop(nHeaders, divHeight);
//	}
//
//	/**
//	 * Turns bouncing animation on/off.
//	 * 
//	 * @param bouncing
//	 *            Default is true (on)
//	 */
//	public void setBounce(boolean bouncing)
//	{
//		bounce = bouncing;
//	}
//
//	/**
//	 * Sets how fast the animation will be. Higher value means faster animation.
//	 * Must be >= 1.05. Together with Elasticity <= 0.75 it will not bounce
//	 * forever.
//	 * 
//	 * @param breakspead
//	 *            Default is 4.0
//	 */
//	public void setBreakspeed(final float breakspeed)
//	{
//		if (Math.abs(breakspeed) >= 1.05f)
//		{
//			BREAKSPEED = Math.abs(breakspeed);
//		}
//	}
//
//	/**
//	 * Sets how much it will keep bouncing. Lower value means less bouncing.
//	 * Must be <= 0.75. Together with Breakspeed >= 1.05 it will not bounce
//	 * forever.
//	 * 
//	 * @param elasticity
//	 *            Default is 0.67
//	 */
//	public void setElasticity(final float elasticity)
//	{
//		if (Math.abs(elasticity) <= 0.75f)
//		{
//			ELASTICITY = Math.abs(elasticity);
//		}
//	}
//
//	public Runnable checkListviewTopAndBottom = new Runnable()
//	{
//		@Override
//		public void run()
//		{
//
//			mHandler.removeCallbacks(checkListviewTopAndBottom);
//
//			if (trackballEvent && firstVis < nHeaders && lastVis >= totalItems)
//			{
//				trackballEvent = false;
//				rebound = false;
//				return;
//			}
//
//			if (rebound)
//			{
//
//				if (firstVis < nHeaders)
//				{
//
//					// hack to avoid strange behaviour when there aren't enough
//					// items to fill the entire listview
//					if (lastVis >= totalItems)
//					{
//						smoothScrollBy(0, 0);
//						rebound = false;
//						recalcV = false;
//						velocity = 0f;
//					}
//
//					if (recalcV)
//					{
//						recalcV = false;
//						velocity /= (1f + ((System.currentTimeMillis() - flingTimestamp) / 1000f));
//					}
//					if (firstVis == nHeaders)
//					{
//						recalcV = false;
//					}
//					if (visibleCnt > nHeaders)
//					{
//						measure = getChildAt(nHeaders);
//						if (measure.getTop() + velocity < divHeight)
//						{
//							velocity *= -ELASTICITY;
//							if (!bounce || Math.abs(velocity) < BREAKSPEED)
//							{
//								rebound = false;
//								recalcV = false;
//								velocity = 0f;
//							}
//							else
//							{
//								setSelectionFromTop(nHeaders, divHeight + 1);
//							}
//						}
//					}
//					else
//					{
//						if (velocity > 0f)
//							velocity = -velocity;
//					}
//					if (rebound)
//					{
//						smoothScrollBy((int) -velocity, 0);
//						if (velocity > BREAKSPEED)
//						{
//							velocity *= ELASTICITY;
//							if (velocity < BREAKSPEED)
//							{
//								rebound = false;
//								recalcV = false;
//								velocity = 0f;
//							}
//						}
//						else
//							velocity -= BREAKSPEED;
//					}
//
//				}
//				else if (lastVis >= totalItems)
//				{
//
//					if (recalcV)
//					{
//						recalcV = false;
//						velocity /= (1f + ((System.currentTimeMillis() - flingTimestamp) / 1000f));
//					}
//					if (lastVis == totalItems - nHeaders - nFooters)
//					{
//						rebound = false;
//						recalcV = false;
//						velocity = 0f;
//					}
//					else
//					{
//						if (visibleCnt > (nHeaders + nFooters))
//						{
//							measure = getChildAt(visibleCnt - nHeaders - nFooters);
//							if (measure.getBottom() + velocity > getHeight() - divHeight)
//							{
//								velocity *= -ELASTICITY;
//								if (!bounce || Math.abs(velocity) < BREAKSPEED)
//								{
//									rebound = false;
//									recalcV = false;
//									velocity = 0f;
//								}
//								else
//								{
//									setSelectionFromTop(lastVis - nHeaders - nFooters, getHeight() - divHeight - measure.getHeight() - 1);
//								}
//							}
//						}
//						else
//						{
//							if (velocity < 0f)
//								velocity = -velocity;
//						}
//					}
//					if (rebound)
//					{
//						smoothScrollBy((int) -velocity, 0);
//						if (velocity < -BREAKSPEED)
//						{
//							velocity *= ELASTICITY;
//							if (velocity > -BREAKSPEED / ELASTICITY)
//							{
//								rebound = false;
//								recalcV = false;
//								velocity = 0f;
//							}
//						}
//						else
//							velocity += BREAKSPEED;
//					}
//
//				}
//				else if (scrollstate == OnScrollListener.SCROLL_STATE_IDLE)
//				{
//
//					rebound = false;
//					recalcV = false;
//					velocity = 0f;
//				}
//				mHandler.postDelayed(checkListviewTopAndBottom, delay);
//				return;
//			}
//
//			if (scrollstate != OnScrollListener.SCROLL_STATE_IDLE)
//				return;
//
//			if (totalItems == (nHeaders + nFooters) || firstVis < nHeaders)
//			{
//				setSelectionFromTop(nHeaders, divHeight);
//				smoothScrollBy(0, 0);
//			}
//			else if (lastVis == totalItems)
//			{
//				int offset = getHeight() - divHeight;
//				measure = getChildAt(visibleCnt - nHeaders - nFooters);
//				if (measure != null)
//					offset -= measure.getHeight();
//				setSelectionFromTop(lastVis - nHeaders - nFooters, offset);
//				smoothScrollBy(0, 0);
//			}
//		}
//	};
//}

public class OverScrollListView extends ListView
{
	private static final int MAX_Y_OVERSCROLL_DISTANCE = 200;
	private int mMaxYOverscrollDistance;

	/**
	 * 当前滑动的ListView　position
	 */
	private int slidePosition;
	/**
	 * 手指按下X的坐标
	 */
	private int downY;
	/**
	 * 手指按下Y的坐标
	 */
	private int downX;
	/**
	 * 屏幕宽度
	 */
	private int screenWidth;
	/**
	 * ListView的item
	 */
	private View itemView;
	/**
	 * 滑动类
	 */
	private Scroller scroller;
	private static final int SNAP_VELOCITY = 600;
	/**
	 * 速度追踪对象
	 */
	private VelocityTracker velocityTracker;
	/**
	 * 是否响应滑动，默认为不响应
	 */
	private boolean isSlide = false;
	/**
	 * 认为是用户滑动的最小距离
	 */
	private int mTouchSlop;
	/**
	 * 移除item后的回调接口
	 */
	private RemoveListener mRemoveListener;
	/**
	 * 用来指示item滑出屏幕的方向,向左或者向右,用一个枚举值来标记
	 */
	private RemoveDirection removeDirection;

	// 滑动删除方向的枚举值
	public enum RemoveDirection
	{
		RIGHT, LEFT;
	}

	public OverScrollListView(Context context)
	{
		this(context, null);
	}

	public OverScrollListView(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
		init();
	}

	public OverScrollListView(Context context, AttributeSet attrs)
	{
		this(context, attrs, 0);
	}

	private void init()
	{

		final DisplayMetrics metrics = getContext().getResources().getDisplayMetrics();
		final float density = metrics.density;
		mMaxYOverscrollDistance = (int) (density * MAX_Y_OVERSCROLL_DISTANCE);
		screenWidth = metrics.widthPixels;
		scroller = new Scroller(getContext());
		mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
	}

	protected boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX, int scrollRangeY, int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent)
	{
		// This is where the magic happens, we have replaced the incoming
		// maxOverScrollY with our own custom variable mMaxYOverscrollDistance;
		return super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX, scrollRangeY, maxOverScrollX, mMaxYOverscrollDistance, isTouchEvent);
	}

	/**
	 * 设置滑动删除的回调接口
	 * 
	 * @param removeListener
	 */
	public void setRemoveListener(RemoveListener removeListener)
	{ 
		this.mRemoveListener = removeListener;	 
	}

	/**
	 * 分发事件，主要做的是判断点击的是那个item, 以及通过postDelayed来设置响应左右滑动事件
	 */
	@Override
	public boolean dispatchTouchEvent(MotionEvent event)
	{
		switch (event.getAction())
		{
		case MotionEvent.ACTION_DOWN:
		{
			addVelocityTracker(event);

			// 假如scroller滚动还没有结束，我们直接返回
			if (!scroller.isFinished())
			{
				return super.dispatchTouchEvent(event);
			}
			downX = (int) event.getX();
			downY = (int) event.getY();

			slidePosition = pointToPosition(downX, downY);

			// 无效的position, 不做任何处理
			if (slidePosition == AdapterView.INVALID_POSITION)
			{
				return super.dispatchTouchEvent(event);
			}

			// 获取我们点击的item view
			itemView = getChildAt(slidePosition - getFirstVisiblePosition());
			break;
		}
		case MotionEvent.ACTION_MOVE:
		{
			if (Math.abs(getScrollVelocity()) > SNAP_VELOCITY || (Math.abs(event.getX() - downX) > mTouchSlop && Math.abs(event.getY() - downY) < mTouchSlop))
			{
				isSlide = true;
			}
			break;
		}
		case MotionEvent.ACTION_UP:
			recycleVelocityTracker();
			break;
		}

		return super.dispatchTouchEvent(event);
	}

	/**
	 * 往右滑动，getScrollX()返回的是左边缘的距离，就是以View左边缘为原点到开始滑动的距离，所以向右边滑动为负值
	 */
	private void scrollRight()
	{
		removeDirection = RemoveDirection.RIGHT;
		final int delta = (screenWidth + itemView.getScrollX());
		// 调用startScroll方法来设置一些滚动的参数，我们在computeScroll()方法中调用scrollTo来滚动item
		scroller.startScroll(itemView.getScrollX(), 0, -delta, 0, Math.abs(delta));
		postInvalidate(); // 刷新itemView
	}

	/**
	 * 向左滑动，根据上面我们知道向左滑动为正值
	 */
	private void scrollLeft()
	{
		removeDirection = RemoveDirection.LEFT;
		final int delta = (screenWidth - itemView.getScrollX());
		// 调用startScroll方法来设置一些滚动的参数，我们在computeScroll()方法中调用scrollTo来滚动item
		scroller.startScroll(itemView.getScrollX(), 0, delta, 0, Math.abs(delta));
		postInvalidate(); // 刷新itemView
	}

	/**
	 * 根据手指滚动itemView的距离来判断是滚动到开始位置还是向左或者向右滚动
	 */
	private void scrollByDistanceX()
	{
		// 如果向左滚动的距离大于屏幕的三分之一，就让其删除
		if (itemView.getScrollX() >= screenWidth / 3)
		{
			scrollLeft();
		}
		else if (itemView.getScrollX() <= -screenWidth / 3)
		{
			scrollRight();
		}
		else
		{
			// 滚回到原始位置,为了偷下懒这里是直接调用scrollTo滚动
			itemView.scrollTo(0, 0);
		}

	}

	/**
	 * 处理我们拖动ListView item的逻辑
	 */
	@Override
	public boolean onTouchEvent(MotionEvent ev)
	{
		if (isSlide && slidePosition != AdapterView.INVALID_POSITION)
		{
			addVelocityTracker(ev);
			final int action = ev.getAction();
			int x = (int) ev.getX();
			switch (action)
			{
			case MotionEvent.ACTION_MOVE:
				int deltaX = downX - x;
				downX = x;

				// 手指拖动itemView滚动, deltaX大于0向左滚动，小于0向右滚
				itemView.scrollBy(deltaX, 0);
				break;
			case MotionEvent.ACTION_UP:
				int velocityX = getScrollVelocity();
				if (velocityX > SNAP_VELOCITY)
				{
					scrollRight();
				}
				else if (velocityX < -SNAP_VELOCITY)
				{
					scrollLeft();
				}
				else
				{
					scrollByDistanceX();
				}

				recycleVelocityTracker();
				// 手指离开的时候就不响应左右滚动
				isSlide = false;
				break;
			}

			return true; // 拖动的时候ListView不滚动
		}

		// 否则直接交给ListView来处理onTouchEvent事件
		return super.onTouchEvent(ev);
	}

	@Override
	public void computeScroll()
	{
		// 调用startScroll的时候scroller.computeScrollOffset()返回true，
		if (scroller.computeScrollOffset())
		{
			// 让ListView item根据当前的滚动偏移量进行滚动
			itemView.scrollTo(scroller.getCurrX(), scroller.getCurrY());

			postInvalidate();

			// 滚动动画结束的时候调用回调接口
			if (scroller.isFinished())
			{
				if (mRemoveListener == null)
				{
//					mRemoveListener = new SimpleRemoveListenerImpl();
					throw new NullPointerException("RemoveListener is null, we should called setRemoveListener()");
				}

				itemView.scrollTo(0, 0);
				mRemoveListener.removeItem(removeDirection, slidePosition);
			}
		}
	}

	/**
	 * 添加用户的速度跟踪器
	 * 
	 * @param event
	 */
	private void addVelocityTracker(MotionEvent event)
	{
		if (velocityTracker == null)
		{
			velocityTracker = VelocityTracker.obtain();
		}

		velocityTracker.addMovement(event);
	}

	/**
	 * 移除用户速度跟踪器
	 */
	private void recycleVelocityTracker()
	{
		if (velocityTracker != null)
		{
			velocityTracker.recycle();
			velocityTracker = null;
		}
	}

	/**
	 * 获取X方向的滑动速度,大于0向右滑动，反之向左
	 * 
	 * @return
	 */
	private int getScrollVelocity()
	{
		velocityTracker.computeCurrentVelocity(1000);
		int velocity = (int) velocityTracker.getXVelocity();
		return velocity;
	}

	/**
	 * 
	 * 当ListView item滑出屏幕，回调这个接口 我们需要在回调方法removeItem()中移除该Item,然后刷新ListView
	 * 
	 * @author xiaanming
	 * 
	 */
	public interface RemoveListener
	{
		public void removeItem(RemoveDirection direction, int position);
	}

	@Override
	protected void onDraw(Canvas canvas)
	{
        canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.image2), 0, 0, null);
		super.onDraw(canvas);
	}
}
