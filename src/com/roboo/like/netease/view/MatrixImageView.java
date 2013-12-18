package com.roboo.like.netease.view;

import com.roboo.like.netease.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.widget.ImageView;

public class MatrixImageView extends ImageView
{
	private Bitmap mBitmap;
	private Matrix mMatrix;
	public MatrixImageView(Context context)
	{
		super(context);
		init();
	}

	public MatrixImageView(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
		init();
	 
	}

	public MatrixImageView(Context context, AttributeSet attrs)
	{
		this(context, attrs, 0);
	}
	private void init()
	{
		this.mMatrix = new Matrix();	
		this.setScaleType(ScaleType.MATRIX);
		this.mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_matrix);
	}
	@Override
	protected void onDraw(Canvas canvas)
	{
		//draw the bitmap resource to canvas 
		 canvas.drawBitmap(mBitmap, 0, 0, null);
		 //draw after matrix bitmap to canvas
		 canvas.drawBitmap(mBitmap, mMatrix, null);
		super.onDraw(canvas);	
	}
	@Override
	public void setImageMatrix(Matrix matrix)
	{
		 this.mMatrix.set(matrix);
		super.setImageMatrix(matrix);
		
	}
	public Bitmap getImageBitmap()
	{
		return this.mBitmap;
	}

}
