package com.roboo.like.netease;

import android.R.integer;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.widget.ImageView;

public class BitmapActivity extends BaseActivity
{
	private ImageView mIvNewImageView;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setTVTitle(R.string.tv_bitmap);
		setContentView(R.layout.activity_bitmap);
		initView();
		new ImageHandleTask().execute(R.drawable.ic_matrix);
	}
	private void initView()
	{
		this.mIvNewImageView = (ImageView) findViewById(R.id.iv_image_new);
	}
	private class ImageHandleTask extends AsyncTask<Integer, Void, Bitmap>
	{
		@Override
		protected Bitmap doInBackground(Integer... params)
		{
			 int resId = params[0];
			  Options options = new Options();
			  options.inJustDecodeBounds  = true;
			  BitmapFactory.decodeResource(getResources(), resId,options);
			  int imageWidth = options.outWidth;
			  int imageHeight =  options.outHeight;
			  System.out.println("imageWidth = " + imageWidth + " imageHeight = " + imageHeight);
			  options.inSampleSize = 2;
			  options.inJustDecodeBounds = false;
			  Bitmap newBitmap = BitmapFactory.decodeResource(getResources(), resId, options);
			return newBitmap ;
		}
		@Override
		protected void onPostExecute(Bitmap result)
		{
			super.onPostExecute(result);
			if(null != result)
			{
				mIvNewImageView.setImageBitmap(result);
			}
		}
		
	}
}
