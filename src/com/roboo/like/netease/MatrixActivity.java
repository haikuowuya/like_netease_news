package com.roboo.like.netease;

import roboguice.inject.InjectView;
import android.graphics.Matrix;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

import com.roboo.like.netease.view.MatrixImageView;

public class MatrixActivity extends BaseRoboActivity implements OnTouchListener
{
	@InjectView(R.id.miv_image)
	MatrixImageView mImageView;
	private Matrix mMatrix;
	private static int mMode = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setTVTitle(R.string.tv_matrix);
		// TODO setContentView Tag
		setContentView(R.layout.activity_matrix);
		this.mImageView.setOnTouchListener(this);
	}
	private void setMode(int mode)
	{
		switch (mode)
		{
		case 0:
			translate();
			break;
		case 1:
			rotateRoundCenter();
			break;
		case 2:
			rotateAndTranslate();
			break;
		case 3:
			scale();
			break;
		case 4:
			skewHorizonal();
			break;
		case 5:
			skewVertical();
			break;
		case 6:
			skewHorizonalAndVertical();
			break;
		case 7:
			symmetrical();
			break;
		case 8:
			symmetricalVertical();
			break;
		case 9:
			symmetricalXY();
			break;
		default:
			break;
		}
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.activity_matrix, menu);
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{
		case R.id.action_translate:
			mMode = 0;
			break;
		case R.id.action_rotate_round_center:
			mMode = 1;
			break;
		case R.id.action_rotate_round_horizontal:
			mMode = 2;
			break;
		case R.id.action_scale:
			mMode = 3;
			break;
		case R.id.action_skew_horizontal:
			mMode = 4;
			break;
		case R.id.action_skew_vertical:
			mMode = 5;
			break;
		case R.id.action_skew_horizontal_vertical:
			mMode = 6;
			break;
		case R.id.action_symmetrical_horizontal:
			mMode = 7;
			break;
		case R.id.action_symmetrical_vertical:
			mMode = 8;
			break;
		case R.id.action_symmetrical_x_y:
			mMode = 9;
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);

	}
	@Override
	public boolean onTouch(View v, MotionEvent e)
	{
		if (e.getAction() == MotionEvent.ACTION_UP)
		{
			this.mMatrix = new Matrix();
			setMode(mMode);
			mImageView.invalidate();
		}
		return true;
	}
	/**
	 * TODO 对称(对称轴为直线y = x)
	 * 
	 */
	private void symmetricalXY()
	{
		float matrix_values[] = { 0f, -1f, 0f, -1f, 0f, 0f, 0f, 0f, 1f };
		mMatrix.setValues(matrix_values);
		seeMatrix();

		// 做下面的平移变换，纯粹是为了让变换后的图像和原图像不重叠
		mMatrix.postTranslate(mImageView.getImageBitmap().getHeight() +
			mImageView.getImageBitmap().getWidth(),
			mImageView.getImageBitmap().getHeight() +
				mImageView.getImageBitmap().getWidth());
		mImageView.setImageMatrix(mMatrix);
		seeMatrix();
	}
	/**
	 * TODO 对称 - 垂直
	 * 
	 */
	private void symmetricalVertical()
	{
		float matrix_values[] = { -1f, 0f, 0f, 0f, 1f, 0f, 0f, 0f, 1f };
		mMatrix.setValues(matrix_values);
		seeMatrix();
		// 做下面的平移变换，纯粹是为了让变换后的图像和原图像不重叠
		mMatrix.postTranslate(mImageView.getImageBitmap().getWidth() * 2f, 0f);
		mImageView.setImageMatrix(mMatrix);

		seeMatrix();
	}
	/**
	 * TODO 对称 (水平对称)
	 * 
	 */
	private void symmetrical()
	{

		float matrix_values[] = { 1f, 0f, 0f, 0f, -1f, 0f, 0f, 0f, 1f };
		mMatrix.setValues(matrix_values);
		seeMatrix();
		// 做下面的平移变换，纯粹是为了让变换后的图像和原图像不重叠
		mMatrix.postTranslate(0f, mImageView.getImageBitmap().getHeight() * 2f);
		mImageView.setImageMatrix(mMatrix);

		seeMatrix();
	}
	/**
	 * TODO 错切 - 水平 + 垂直
	 * 
	 */
	private void skewHorizonalAndVertical()
	{
		mMatrix.setSkew(0.5f, 0.5f);
		seeMatrix();
		// 做下面的平移变换，纯粹是为了让变换后的图像和原图像不重叠
		mMatrix.postTranslate(0f, mImageView.getImageBitmap().getHeight());
		mImageView.setImageMatrix(mMatrix);
		seeMatrix();
	}
	/**
	 * TODO 错切 - 垂直
	 * 
	 */
	private void skewVertical()
	{
		mMatrix.setSkew(0f, 0.5f);
		seeMatrix();
		// 做下面的平移变换，纯粹是为了让变换后的图像和原图像不重叠
		mMatrix.postTranslate(0f, mImageView.getImageBitmap().getHeight());
		mImageView.setImageMatrix(mMatrix);
		seeMatrix();
	}
	/**
	 * TODO 错切 - 水平
	 * 
	 */
	private void skewHorizonal()
	{

		mMatrix.setSkew(0.5f, 0f);
		seeMatrix();
		// 做下面的平移变换，纯粹是为了让变换后的图像和原图像不重叠
		mMatrix.postTranslate(mImageView.getImageBitmap().getWidth(), 0f);
		mImageView.setImageMatrix(mMatrix);
		seeMatrix();
	}
	/**
	 * TODO 缩放
	 * 
	 */
	private void scale()
	{
		mMatrix.setScale(2f, 2f);
		seeMatrix();
		// 做下面的平移变换，纯粹是为了让变换后的图像和原图像不重叠
		mMatrix.postTranslate(mImageView.getImageBitmap().getWidth(),
			mImageView.getImageBitmap().getHeight());
		mImageView.setImageMatrix(mMatrix);

		seeMatrix();
	}
	/**
	 * TODO 旋转(围绕坐标原点) + 平移(效果同2)
	 * 
	 */
	private void rotateAndTranslate()
	{

		mMatrix.setRotate(45f);
		mMatrix.preTranslate(-1f * mImageView.getImageBitmap().getWidth() / 2f,
			-1f * mImageView.getImageBitmap().getHeight() / 2f);
		mMatrix.postTranslate((float) mImageView.getImageBitmap().getWidth() /
			2f, (float) mImageView.getImageBitmap().getHeight() / 2f);

		// 做下面的平移变换，纯粹是为了让变换后的图像和原图像不重叠
		mMatrix.postTranslate((float) mImageView.getImageBitmap().getWidth() *
			1.5f, 0f);
		mImageView.setImageMatrix(mMatrix);
		seeMatrix();

	}
	/**
	 * TODO 旋转(围绕图像的中心点)
	 * 
	 */
	private void rotateRoundCenter()
	{

		mMatrix.setRotate(45f, mImageView.getImageBitmap().getWidth() / 2f,
			mImageView.getImageBitmap().getHeight() / 2f);

		// 做下面的平移变换，纯粹是为了让变换后的图像和原图像不重叠
		mMatrix.postTranslate(mImageView.getImageBitmap().getWidth() * 1.5f,
			0f);
		mImageView.setImageMatrix(mMatrix);
		seeMatrix();
	}
	/**
	 * TODO 平移
	 * 
	 */
	private void translate()
	{
		// 输出图像的宽度和高度(240 x 240)
		 System.out.println("right = " + mImageView.getRight() + "");
		mMatrix.postTranslate(mImageView.getImageBitmap().getWidth(), mImageView.getImageBitmap().getHeight());
		// 在x方向平移view.getImageBitmap().getWidth()，在y轴方向view.getImageBitmap().getHeight()
		mImageView.setImageMatrix(mMatrix);
		seeMatrix();
	}
	private void seeMatrix()
	{
		// 下面的代码是为了查看matrix中的元素
		float[] matrixValues = new float[9];
		mMatrix.getValues(matrixValues);
		for (int i = 0; i < 3; ++i)
		{
			String temp = new String();
			for (int j = 0; j < 3; ++j)
			{
				temp += matrixValues[3 * i + j] + "  \t  ";
			}
			Log.e("TestTransformMatrixActivity", temp);
		}
	}
}