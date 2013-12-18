package com.roboo.like.netease;

import android.app.ActionBar.LayoutParams;
import android.app.Fragment;
import android.app.FragmentManager.OnBackStackChangedListener;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.os.Bundle;
import android.util.FloatMath;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.Toast;

public class FragmentAnimationActivity extends BaseActivity implements OnBackStackChangedListener
{
	private boolean removeBackFragment = false;

	private Matrix matrix = new Matrix();
	private Matrix savedMatrix = new Matrix();
	
	private static final int NONE = 0;
	private static final int DRAG = 1;
	private static final int ZOOM = 2;
	private int mode = NONE;
	  // 第一个按下的手指的点  
    private PointF startPoint = new PointF();  
    // 两个按下的手指的触摸点的中点  
    private PointF midPoint = new PointF();  
    // 初始的两个手指按下的触摸点的距离  
    private float oriDis = 1f;  
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{

		super.onCreate(savedInstanceState);
		setTVTitle(R.string.tv_fragment_animation);
		// TODO setContentView Tag
		setContentView(R.layout.activity_fragment_animation);
		getFragmentManager().beginTransaction().add(R.id.frame_container, FrontFragment.newInstance()).commit();
		getFragmentManager().addOnBackStackChangedListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		menu.add("切换");
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switchoverFragment();
		Toast.makeText(this, "切换", Toast.LENGTH_SHORT).show();
		return true;
	}

	private void switchoverFragment()
	{
		if (removeBackFragment)
		{
			getFragmentManager().popBackStack();
			return;
		}
		getFragmentManager()
				.beginTransaction()
				.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
				.setCustomAnimations(R.animator.card_flip_right_in, R.animator.card_flip_right_out, R.animator.card_flip_left_in,
						R.animator.card_flip_left_out).replace(R.id.frame_container, BackFragment.newInstance()).addToBackStack("front").commit();
	}

	public static class FrontFragment extends Fragment
	{
		private static FrontFragment newInstance()
		{
			return new FrontFragment();
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
		{
			ImageView imageView = new ImageView(getActivity());
			imageView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
			imageView.setScaleType(ScaleType.MATRIX);
			imageView.setImageResource(R.drawable.ic_front_fragment);
			imageView.setOnTouchListener(((FragmentAnimationActivity)getActivity()).new OnTouchListenerImpl());
			return imageView;
		}

		@Override
		public void onPause()
		{
			System.out.println("FrontFragment :: onPause");
			super.onPause();
		}

		@Override
		public void onResume()
		{
			System.out.println("FrontFragment :: onResume");
			super.onResume();
		}
	}

	public static class BackFragment extends Fragment
	{
		private static BackFragment newInstance()
		{
			return new BackFragment();
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
		{
			ImageView imageView = new ImageView(getActivity());
			imageView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
			imageView.setScaleType(ScaleType.MATRIX);
			imageView.setImageResource(R.drawable.ic_back_fragment);
			imageView.setOnTouchListener(((FragmentAnimationActivity)getActivity()).new OnTouchListenerImpl());
			return imageView;
		}

		@Override
		public void onPause()
		{
			System.out.println("BackFragment :: onPause");
			super.onPause();
		}

		@Override
		public void onResume()
		{
			System.out.println("BackFragment :: onResume");
			super.onResume();
		}

	}

	@Override
	public void onBackStackChanged()
	{
		System.out.println("onBackStackChanged()");
		if (getFragmentManager().getBackStackEntryCount() > 0)
		{
			System.out.println("getFragmentManager().getBackStackEntryAt(0) = " + getFragmentManager().getBackStackEntryAt(0).getName());
			// 说明addToBackStack 已经执行，
			// FrontFargment已经添加到返回栈中，此时BackStack中只有FrontFragment
			removeBackFragment = true;
		}
		else
		{
			removeBackFragment = false;
		}
	}

	private class OnTouchListenerImpl implements OnTouchListener
	{
		@Override
		public boolean onTouch(View v, MotionEvent event)
		{
			ImageView view = (ImageView) v;
			switch (event.getAction() & MotionEvent.ACTION_MASK)
			{
			case MotionEvent.ACTION_DOWN:
				// 第一个手指按下事件
				matrix.set((view).getImageMatrix());
				savedMatrix.set(matrix);
				startPoint.set(event.getX(), event.getY());
				mode = DRAG;
				break;
			case MotionEvent.ACTION_POINTER_DOWN:
				// 第二个手指按下事件
				oriDis = distance(event);
				if (oriDis > 10f)
				{
					savedMatrix.set(matrix);
					midPoint = middle(event);
					mode = ZOOM;
				}
				break;
			case MotionEvent.ACTION_UP:
			case MotionEvent.ACTION_POINTER_UP:
				// 手指放开事件
				mode = NONE;
				break;
			case MotionEvent.ACTION_MOVE:
				// 手指滑动事件
				if (mode == DRAG)
				{
					// 是一个手指拖动
					matrix.set(savedMatrix);
					matrix.postTranslate(event.getX() - startPoint.x, event.getY() - startPoint.y);
				}
				else if (mode == ZOOM)
				{
					// 两个手指滑动
					float newDist = distance(event);
					if (newDist > 10f)
					{
						matrix.set(savedMatrix);
						float scale = newDist / oriDis;
						matrix.postScale(scale, scale, midPoint.x, midPoint.y);
					}
				}
				break;
			}

			// 设置ImageView的Matrix
			view.setImageMatrix(matrix);
			return true;
		}

		// 计算两个触摸点之间的距离
		private float distance(MotionEvent event)
		{
			float x = event.getX(0) - event.getX(1);
			float y = event.getY(0) - event.getY(1);
			return FloatMath.sqrt(x * x + y * y);
		}

		// 计算两个触摸点的中点
		private PointF middle(MotionEvent event)
		{
			float x = event.getX(0) + event.getX(1);
			float y = event.getY(0) + event.getY(1);
			return new PointF(x / 2, y / 2);
		}
	}

}
