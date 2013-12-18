package com.roboo.like.netease.listener;

import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.PointF;
import android.util.FloatMath;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

public class MulitPointTouchListener implements OnTouchListener
{

	// These matrices will be used to move and zoom image
	Matrix matrix = new Matrix();
	Matrix savedMatrix = new Matrix();

	public ImageView image;
	// We can be in one of these 3 states
	static final int NONE = 0;
	static final int DRAG = 1;
	static final int ZOOM = 2;
	int mode = NONE;

	// Remember some things for zooming
	PointF start = new PointF();
	PointF mid = new PointF();
	float oldDist = 1f;

	public MulitPointTouchListener(ImageView image)
	{
		 
		super();
		this.image = image;
	}

	@Override
	public boolean onTouch(View v, MotionEvent event)
	{
		this.image.setScaleType(ScaleType.MATRIX);

		ImageView view = (ImageView) v;
		// Log.e("view_width",
		// view.getImageMatrix()..toString()+"*"+v.getWidth());
		// Dump touch event to log
		dumpEvent(event);

		// Handle touch events here...
		switch (event.getAction() & MotionEvent.ACTION_MASK)
		{
		case MotionEvent.ACTION_DOWN:

			matrix.set(view.getImageMatrix());
			savedMatrix.set(matrix);
			start.set(event.getX(), event.getY());
			// Log.d(TAG, "mode=DRAG");
			mode = DRAG;

			/*
			 * float scaleWidth=180f/320; float scaleHeight=240f/480;
			 * matrix.reset(); matrix.postScale(scaleWidth, scaleHeight);
			 */
			// Log.d(TAG, "mode=NONE");
			break;
		case MotionEvent.ACTION_POINTER_DOWN:
			oldDist = spacing(event);
			// Log.d(TAG, "oldDist=" + oldDist);
			if (oldDist > 10f)
			{
				savedMatrix.set(matrix);
				midPoint(mid, event);
				mode = ZOOM;
				// Log.d(TAG, "mode=ZOOM");
			}
			break;
		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_POINTER_UP:
			mode = NONE;
			// Log.e("view.getWidth", view.getWidth() + "");
			// Log.e("view.getHeight", view.getHeight() + "");

			break;
		case MotionEvent.ACTION_MOVE:
			if (mode == DRAG)
			{
				// ...
				matrix.set(savedMatrix);
				float x = event.getX() - start.x;
				float y = event.getY() - start.y;
				matrix.postTranslate( x,y);
			
			}
			else if (mode == ZOOM)
			{
				float newDist = spacing(event);
				// Log.d(TAG, "newDist=" + newDist);
				if (newDist > 10f)
				{
					matrix.set(savedMatrix);
					float scale = newDist / oldDist;
					System.out.println("scale = " + scale);
					matrix.postScale(scale, scale, mid.x, mid.y);
				}
			}
			break;
		}

		view.setImageMatrix(matrix);
	//	return true; // indicate event was handled
		return false;
	}

	private void dumpEvent(MotionEvent event)
	{
		String names[] = { "DOWN", "UP", "MOVE", "CANCEL", "OUTSIDE",
			"POINTER_DOWN", "POINTER_UP", "7?", "8?", "9?" };
		StringBuilder sb = new StringBuilder();
		int action = event.getAction();
		int actionCode = action & MotionEvent.ACTION_MASK;
		sb.append("event ACTION_").append(names[actionCode]);
		if (actionCode == MotionEvent.ACTION_POINTER_DOWN
			|| actionCode == MotionEvent.ACTION_POINTER_UP)
		{
			sb.append("(pid ").append(
				action >> MotionEvent.ACTION_POINTER_ID_SHIFT);
			sb.append(")");
		}
		sb.append("[");
		for (int i = 0; i < event.getPointerCount(); i++)
		{
			sb.append("#").append(i);
			sb.append("(pid ").append(event.getPointerId(i));
			sb.append(")=").append((int) event.getX(i));
			sb.append(",").append((int) event.getY(i));
			if (i + 1 < event.getPointerCount())
				sb.append(";");
		}
		sb.append("]");
		// Log.d(TAG, sb.toString());
	}

	/** Determine the space between the first two fingers */
	private float spacing(MotionEvent event)
	{
		float x = event.getX(0) - event.getX(1);
		float y = event.getY(0) - event.getY(1);
		return FloatMath.sqrt(x * x + y * y);
	}

	/** Calculate the mid point of the first two fingers */
	private void midPoint(PointF point, MotionEvent event)
	{
		float x = event.getX(0) + event.getX(1);
		float y = event.getY(0) + event.getY(1);
		point.set(x / 2, y / 2);
	}
}