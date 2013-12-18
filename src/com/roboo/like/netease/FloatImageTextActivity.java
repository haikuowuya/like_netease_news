package com.roboo.like.netease;

import com.roboo.like.netease.view.FloatImageTextView;

import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.ViewGroup.LayoutParams;

public class FloatImageTextActivity extends BaseActivity
{
	private FloatImageTextView mImageTextView ;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setTVTitle(R.string.tv_float_image_text);
		// TODO setContentView Tag
		this.mImageTextView = new FloatImageTextView(this);
		 Options options = new Options();
		 options.inSampleSize=2;
		this.mImageTextView.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.ic_matrix,options), 120, 50);
		String text="Android中并没有提供HTML图文环绕效果的View，最接近的算是TextView中的ImageSpan了，但并未完全实现图文环绕（图文混排）的效果。1、Android系统TextView的ImageSpan实现图文环绕";
		this.mImageTextView.setText(text);
		setContentView(mImageTextView, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		
	}

}
