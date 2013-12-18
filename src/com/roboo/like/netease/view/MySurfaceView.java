package com.roboo.like.netease.view;

import com.roboo.like.netease.R;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Path.FillType;
import android.graphics.drawable.BitmapDrawable;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback
{
	private SurfaceHolder mHolder;
	public MySurfaceView(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
		init();
	}

	public MySurfaceView(Context context, AttributeSet attrs)
	{
		this(context, attrs, 0);
	}

	public MySurfaceView(Context context)
	{
		this(context, null);
	}
	private void init()
	{

		this.mHolder = getHolder();
		this.mHolder.addCallback(this);

	}
	@Override
	public void surfaceCreated(SurfaceHolder holder)
	{
		new Thread(new MyThread()).start();
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
	{

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder)
	{

	}
	// 内部类的内部类
	class MyThread implements Runnable
	{
		@Override
		public void run()
		{
			Canvas canvas = mHolder.lockCanvas(null);// 获取画布
			canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.ic_surface_view_image), 0, 0, null);
			Paint mPaint = new Paint();
			mPaint.setColor(Color.BLUE);
			Rect rect = new Rect(100, 100, getWidth() - 100, 250);
			canvas.drawRect(rect, mPaint);
			Paint paint = new Paint();
			paint.setColor(Color.RED);

			Path path = new Path();
			path.moveTo(150, 200);
			path.lineTo(200, 200);
			path.lineTo(175, 175);
			path.close();
			drawWaterMask("添加图片水印 -- 123456",canvas);
			path.setFillType(FillType.WINDING);
			canvas.drawPath(path, paint);
			mHolder.unlockCanvasAndPost(canvas);// 解锁画布，提交画好的图像
			System.out.println("执行");
		}
		/**
		 * TODO draw water mask
		 * 
		 * @param canvas
		 */
		private void drawWaterMask(String text, Canvas canvas)
		{
			int fontSize = 45;
			int width = canvas.getWidth();
			int height = canvas.getHeight();
			Path path = new Path();
			path.moveTo(0,height);
			path.lineTo(width, 0);
			path.close();

			Paint paint = new Paint();
			paint.setColor(0x88ff0000);
			paint.setTextSize(fontSize);
			paint.setAntiAlias(true);
			paint.setDither(true);
			Rect bounds = new Rect();
			paint.getTextBounds(text, 0, text.length(), bounds);

			int length = (int) Math.sqrt(width * width + height * height);
			int hOffset = (length - (bounds.right - bounds.left)) / 2;
			canvas.drawTextOnPath(text, path, hOffset, fontSize / 2, paint);
		}

	}

}
