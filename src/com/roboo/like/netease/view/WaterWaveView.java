package com.roboo.like.netease.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.roboo.like.netease.R;

public class WaterWaveView extends View
{
	private Shader mShader;
	private Bitmap mBitmap;
	private Paint mPaint;
	private ShapeDrawable mShapeDrawable;
	public WaterWaveView(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
		init();
	}

	public WaterWaveView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		init();
	}

	public WaterWaveView(Context context)
	{
		super(context);
		init();
	}
	private void init()
	{
		Bitmap tmpBitmap = ((BitmapDrawable) getResources().getDrawable(R.drawable.ic_surface_view_image)).getBitmap();
		mBitmap = Bitmap.createScaledBitmap(tmpBitmap, getResources().getDisplayMetrics().widthPixels, getResources().getDisplayMetrics().heightPixels, true);
		mPaint = new Paint();
		mShader = new BitmapShader(mBitmap, Shader.TileMode.REPEAT, Shader.TileMode.MIRROR);
		// 将图片裁剪为椭圆型
		// 创建ShapeDrawable object，并定义形状为椭圆
		mShapeDrawable = new ShapeDrawable(new OvalShape());
		//mShapeDrawable.setAlpha(0x00000000);
	}
	@Override
	protected void onDraw(Canvas canvas)
	{
		super.onDraw(canvas);
		canvas.drawBitmap(mBitmap, 0, 0, mPaint);
		// 设置要绘制的椭圆形的东西为ShapeDrawable图片
		mShapeDrawable.getPaint().setShader(mShader);
		// 设置显示区域
		mShapeDrawable.setBounds(0, 0, mBitmap.getWidth(),
			mBitmap.getHeight());
		// 绘制ShapeDrawable
		mShapeDrawable.draw(canvas);
		if (mShader != null)
		{
			mPaint.setShader(mShader);
			canvas.drawCircle(0, 0, 1000, mPaint);
		}
	}
	// 覆写触摸屏事件
	public boolean onTouchEvent(MotionEvent event)
	{
		// @设置alpha通道（透明度）
		mPaint.setAlpha(400);
		mShader = new RadialGradient(event.getX(), event.getY(), 48,
			new int[] { Color.WHITE, Color.TRANSPARENT }, null, Shader.TileMode.REPEAT);
		// @重绘
		postInvalidate();
		return true;
	}
}
