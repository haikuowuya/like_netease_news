package com.roboo.like.netease.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.roboo.like.netease.R;

/**
 * 圆盘式的view
 * @author chroya
 *
 */
public class RoundSpinView extends View {
	private Paint mPaint = new Paint();
	
	//stone列表
	private BigStone[] mStones;
	//数目
	private static final int STONE_COUNT = 6;
	
	//圆心坐标
	private int mPointX=0, mPointY=0;
	//半径
	private int mRadius = 0;
	//每两个点间隔的角度
	private int mDegreeDelta;

	public RoundSpinView(Context context, int px, int py, int radius) {
		super(context);
		mPaint.setColor(Color.RED);
		mPaint.setStrokeWidth(2);
		setBackgroundResource(R.color.actionbar_color);
		
		mPointX = px;
		mPointY = py;
		mRadius = radius;
		
		setupStones();
		computeCoordinates();
	}
	
	/**
	 * 初始化每个点
	 */
	private void setupStones() {
		mStones = new BigStone[STONE_COUNT];
		BigStone stone;
		int angle = 0;
		mDegreeDelta = 360/STONE_COUNT;
		
		for(int index=0; index<STONE_COUNT; index++) {
			stone = new BigStone();
			stone.angle = angle;
			stone.bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.menu1+index);			
			angle += mDegreeDelta;
			
			mStones[index] = stone;
		}
	}
	
	/**
	 * 重新计算每个点的角度
	 */
	private void resetStonesAngle(float x, float y) {
		int angle = computeCurrentAngle(x, y);
		Log.d("RoundSpinView", "angle:"+angle);
		for(int index=0; index<STONE_COUNT; index++) {			
			mStones[index].angle = angle;		
			angle += mDegreeDelta;
		}
	}
	
	/**
	 * 计算每个点的坐标
	 */
	private void computeCoordinates() {
		BigStone stone;
		for(int index=0; index<STONE_COUNT; index++) {
			stone = mStones[index];
			stone.x = mPointX+ (float)(mRadius * Math.cos(stone.angle*Math.PI/180));
			stone.y = mPointY+ (float)(mRadius * Math.sin(stone.angle*Math.PI/180));
		}
	}
	
	/**
	 * 计算第一个点的角度
	 * @param x
	 * @param y
	 * @return
	 */
	private int computeCurrentAngle(float x, float y) {		
		float distance = (float)Math.sqrt(((x-mPointX)*(x-mPointX) + (y-mPointY)*(y-mPointY)));
		int degree = (int)(Math.acos((x-mPointX)/distance)*180/Math.PI);
		if(y < mPointY) {
			degree = -degree;
		}
		
		Log.d("RoundSpinView", "x:"+x+",y:"+y+",degree:"+degree);
		return degree;
	}
	
	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		resetStonesAngle(event.getX(), event.getY());
		computeCoordinates();
		invalidate();
		return true;
	}
	
	@Override
	public void onDraw(Canvas canvas) {
		canvas.drawPoint(mPointX, mPointY, mPaint);
		
		for(int index=0; index<STONE_COUNT; index++) {
			if(!mStones[index].isVisible) continue;
			drawInCenter(canvas, mStones[index].bitmap, mStones[index].x, mStones[index].y);
			//不想有红线，就注掉下面这句
//			canvas.drawLine(mPointX, mPointY, mStones[index].x, mStones[index].y, mPaint);
		}
	}
	
	/**
	 * 把中心点放到中心处
	 * @param canvas
	 * @param bitmap
	 * @param left
	 * @param top
	 */
	void drawInCenter(Canvas canvas, Bitmap bitmap, float left, float top) {
		canvas.drawPoint(left, top, mPaint);
		canvas.drawBitmap(bitmap, left-bitmap.getWidth()/2, top-bitmap.getHeight()/2, null);
	}	
	
	class BigStone {
		
		//图片
		Bitmap bitmap;
		
		//角度
		int angle;
		
		//x坐标
		float x;
		
		//y坐标
		float y;
		
		//是否可见
		boolean isVisible = true;
	}
}
