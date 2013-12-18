package com.roboo.like.netease.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.ProgressBar;

public class MyProgressBar extends ProgressBar
{
	private String text = "定位中……";
	public MyProgressBar(Context context)
	{
		super(context);
	}

	public MyProgressBar(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
	}

	public MyProgressBar(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}
	@Override
	protected synchronized void onDraw(Canvas canvas)
	{
		Paint paint = new Paint();
		paint.setColor(Color.WHITE);
		paint.setTextSize(10f);
		Rect rect = new Rect();
		paint.getTextBounds(text, 0, text.length(),rect);
		int x = getWidth()/2 - rect.centerX();
		 int y = getHeight()/2 - rect.centerY();
		canvas.drawText(text, x, y, paint);
		super.onDraw(canvas);
		
	}

}
