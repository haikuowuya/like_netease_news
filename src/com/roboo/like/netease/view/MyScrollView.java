package com.roboo.like.netease.view;

import java.io.File;
import java.util.LinkedList;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.support.v4.util.LruCache;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageCache;
import com.android.volley.toolbox.ImageLoader.ImageContainer;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.roboo.like.netease.R;

public class MyScrollView extends ScrollView implements OnTouchListener
{
	private static final int PAGE_SIZE = 20;
	private int mPage;
	private static String[] mImages = new String[] {
		"http://img.my.csdn.net/uploads/201309/01/1378037235_3453.jpg",
		"http://img.my.csdn.net/uploads/201309/01/1378037235_7476.jpg",
		"http://img.my.csdn.net/uploads/201309/01/1378037235_9280.jpg",
		"http://img.my.csdn.net/uploads/201309/01/1378037234_3539.jpg",
		"http://img.my.csdn.net/uploads/201309/01/1378037234_6318.jpg",
		"http://img.my.csdn.net/uploads/201309/01/1378037194_2965.jpg",
		"http://img.my.csdn.net/uploads/201309/01/1378037193_1687.jpg",
		"http://img.my.csdn.net/uploads/201309/01/1378037193_1286.jpg",
		"http://img.my.csdn.net/uploads/201309/01/1378037192_8379.jpg",
		"http://img.my.csdn.net/uploads/201309/01/1378037178_9374.jpg",
		"http://img.my.csdn.net/uploads/201309/01/1378037177_1254.jpg",
		"http://img.my.csdn.net/uploads/201309/01/1378037177_6203.jpg",
		"http://img.my.csdn.net/uploads/201309/01/1378037152_6352.jpg",
		"http://img.my.csdn.net/uploads/201309/01/1378037151_9565.jpg",
		"http://img.my.csdn.net/uploads/201309/01/1378037151_7904.jpg",
		"http://img.my.csdn.net/uploads/201309/01/1378037148_7104.jpg",
		"http://img.my.csdn.net/uploads/201309/01/1378037129_8825.jpg",
		"http://img.my.csdn.net/uploads/201309/01/1378037128_5291.jpg",
		"http://img.my.csdn.net/uploads/201309/01/1378037128_3531.jpg",
		"http://img.my.csdn.net/uploads/201309/01/1378037127_1085.jpg",
		"http://img.my.csdn.net/uploads/201309/01/1378037095_7515.jpg",
		"http://img.my.csdn.net/uploads/201309/01/1378037094_8001.jpg",
		"http://img.my.csdn.net/uploads/201309/01/1378037093_7168.jpg",
		"http://img.my.csdn.net/uploads/201309/01/1378037091_4950.jpg",
		"http://img.my.csdn.net/uploads/201308/31/1377949643_6410.jpg",
		"http://img.my.csdn.net/uploads/201308/31/1377949642_6939.jpg",
		"http://img.my.csdn.net/uploads/201308/31/1377949630_4505.jpg",
		"http://img.my.csdn.net/uploads/201308/31/1377949630_4593.jpg",
		"http://img.my.csdn.net/uploads/201308/31/1377949629_7309.jpg",
		"http://img.my.csdn.net/uploads/201308/31/1377949629_8247.jpg",
		"http://img.my.csdn.net/uploads/201308/31/1377949615_1986.jpg",
		"http://img.my.csdn.net/uploads/201308/31/1377949614_8482.jpg",
		"http://img.my.csdn.net/uploads/201308/31/1377949614_3743.jpg",
		"http://img.my.csdn.net/uploads/201308/31/1377949614_4199.jpg",
		"http://img.my.csdn.net/uploads/201308/31/1377949599_3416.jpg",
		"http://img.my.csdn.net/uploads/201308/31/1377949599_5269.jpg",
		"http://img.my.csdn.net/uploads/201308/31/1377949598_7858.jpg",
		"http://img.my.csdn.net/uploads/201308/31/1377949598_9982.jpg",
		"http://img.my.csdn.net/uploads/201308/31/1377949578_2770.jpg",
		"http://img.my.csdn.net/uploads/201308/31/1377949578_8744.jpg",
		"http://img.my.csdn.net/uploads/201308/31/1377949577_5210.jpg",
		"http://img.my.csdn.net/uploads/201308/31/1377949577_1998.jpg",
		"http://img.my.csdn.net/uploads/201308/31/1377949482_8813.jpg",
		"http://img.my.csdn.net/uploads/201308/31/1377949481_6577.jpg",
		"http://img.my.csdn.net/uploads/201308/31/1377949480_4490.jpg",
		"http://img.my.csdn.net/uploads/201308/31/1377949455_6792.jpg",
		"http://img.my.csdn.net/uploads/201308/31/1377949455_6345.jpg",
		"http://img.my.csdn.net/uploads/201308/31/1377949442_4553.jpg",
		"http://img.my.csdn.net/uploads/201308/31/1377949441_8987.jpg",
		"http://img.my.csdn.net/uploads/201308/31/1377949441_5454.jpg",
		"http://img.my.csdn.net/uploads/201308/31/1377949454_6367.jpg",
		"http://img.my.csdn.net/uploads/201308/31/1377949442_4562.jpg"

	};
	private LinkedList<ImageView> mImageViews = new LinkedList<ImageView>();
	private LinearLayout mLinearContainer;
	private LinearLayout mFirstLinearContainer;
	private LinearLayout mSecondLinearContainer;
	private LinearLayout mThirdLinearContainer;
	private int mFirstColumnHeight, mSecondColumnHeight, mThirdColumnHeight;
	private ImageLoader mImageLoader;
	private RequestQueue mRequestQueue;
	private Handler mHandler;
	private int mFirstLinearContainerWidth ;
	private int mLastScrollY = -1;
	/**
	 * MyScrollView height
	 */
	private int mScrollViewHeight;
	private boolean isFirstLoad = true;
	private BitmapImageCache mImageCache;

	public MyScrollView(Context context)
	{
		super(context);
		init(context);
	}
	public MyScrollView(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
		init(context);
	}
	public MyScrollView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		init(context);
	}
	private void init(Context context)
	{
		Cache cache = new DiskBasedCache(new File("/mnt/sdcard/android", "volley"));
		Network network = new BasicNetwork(new HurlStack());
		mRequestQueue = new RequestQueue(cache, network);
		mRequestQueue.start();
		mImageCache = new BitmapImageCache();
		mImageLoader = new ImageLoader(mRequestQueue, mImageCache);
		setOnTouchListener(this);
		mHandler = new Handler(new Callback()
		{
			@Override
			public boolean handleMessage(Message msg)
			{
				MyScrollView myScrollView = (MyScrollView) msg.obj;
				int scrollY = myScrollView.getScrollY();
				// 如果当前的滚动位置和上次相同，表示已停止滚动
				if (scrollY == mLastScrollY)
				{
					// 当滚动的最底部，并且当前没有正在下载的任务时，开始加载下一页的图片
					if (mScrollViewHeight + scrollY >= mLinearContainer.getHeight())
					{
						myScrollView.loadMoreImages();
					}
					myScrollView.checkVisibility();
				}
				else
				{
					mLastScrollY = scrollY;
					Message message = new Message();
					message.obj = myScrollView;
					// 5毫秒后再次对滚动位置进行判断
					mHandler.sendMessageDelayed(message, 5);
				}
				return false;

			}
		});

	}
	protected void checkVisibility()
	{
		for (int i = 0; i < mImageViews.size(); i++)
		{
			ImageView imageView = mImageViews.get(i);
			int borderTop = (Integer) imageView.getTag(R.string.border_top);
			int borderBottom = (Integer) imageView.getTag(R.string.border_bottom);
			if (borderBottom > getScrollY() && borderTop < getScrollY() + mScrollViewHeight)
			{
				String imageUrl = (String) imageView.getTag(R.string.image_url);
				Bitmap bitmap = mImageCache.getBitmap(imageUrl);

				if (bitmap != null)
				{
					imageView.setImageBitmap(bitmap);
				}
				else
				{
					mImageLoader.get(imageUrl, ImageLoader.getImageListener(imageView, R.drawable.ic_empty_photo, R.drawable.ic_stub));
				}
			}
			else
			{
				imageView.setImageResource(R.drawable.ic_stub);
			}
		}
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b)
	{
		super.onLayout(changed, l, t, r, b);
		if (changed && isFirstLoad)
		{
			mScrollViewHeight = getHeight();
			mLinearContainer = (LinearLayout) getChildAt(0);
			mFirstLinearContainer = (LinearLayout) mLinearContainer.findViewById(R.id.first_linear_container);
			mSecondLinearContainer = (LinearLayout) mLinearContainer.findViewById(R.id.second_linear_container);
			mThirdLinearContainer = (LinearLayout) mLinearContainer.findViewById(R.id.third_linear_container);
			mFirstLinearContainerWidth = mFirstLinearContainer.getWidth();
			isFirstLoad = false;
			loadMoreImages();
		}
	}

	private void loadMoreImages()
	{
		if (isHasSDCard())
		{
			int startIndex = mPage * PAGE_SIZE;
			int endIndex = (1 + mPage) * PAGE_SIZE;
			if (startIndex < mImages.length)
			{
				if (endIndex > mImages.length)
				{
					endIndex = mImages.length;
				}
				for (int i = startIndex; i < endIndex; i++)
				{
					addImageViewToContainer(i);
				}
			}

		}
	}

	private void addImageViewToContainer(final int i)
	{
		final ImageView imageView = new ImageView(getContext());
		imageView.setLayoutParams(new LinearLayout.LayoutParams(mFirstLinearContainerWidth, 160));
		imageView.setScaleType(ScaleType.FIT_XY);
		imageView.setPadding(5, 5, 5, 5);
		imageView.setTag(R.string.image_url, mImages[i]);
		mImageLoader.get(mImages[i], new ImageListener()
		{
			@Override
			public void onErrorResponse(VolleyError error)
			{
				imageView.setImageResource(R.drawable.ic_stub);
				findImageToAddWhichColumnLinearLayout(imageView, 160).addView(imageView);
			}
			@Override
			public void onResponse(ImageContainer response, boolean isImmediate)
			{
				if (null != response)
				{
					int height = 160;
					if (null != response.getBitmap())
					{
						Bitmap bitmap = response.getBitmap();
						mImageCache.mLruCache.put(mImages[i], bitmap);		
						imageView.setImageBitmap(bitmap);
						height =(int) (bitmap.getHeight() *((float)mFirstLinearContainerWidth/bitmap.getWidth()));
						imageView.setLayoutParams(new LinearLayout.LayoutParams(mFirstLinearContainerWidth,height));
						findImageToAddWhichColumnLinearLayout(imageView, height).addView(imageView);
					}

				}
			}
		});
		mImageViews.add(i, imageView);
	}
	private boolean isHasSDCard()
	{
		return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
	}
	private LinearLayout findImageToAddWhichColumnLinearLayout(ImageView imageView, int imageHeight)
	{
		if (mFirstColumnHeight < mSecondColumnHeight)
		{
			if (mFirstColumnHeight < mThirdColumnHeight)
			{
				imageView.setTag(R.string.border_top, mFirstColumnHeight);
				mFirstColumnHeight += imageHeight;
				imageView.setTag(R.string.border_bottom, mFirstColumnHeight);
				return mFirstLinearContainer;
			}
			else
			{
				imageView.setTag(R.string.border_top, mThirdColumnHeight);
				mThirdColumnHeight += imageHeight;
				imageView.setTag(R.string.border_bottom, mThirdColumnHeight);
				return mThirdLinearContainer;
			}
		}
		else
		{
			if (mSecondColumnHeight <= mThirdColumnHeight)
			{
				imageView.setTag(R.string.border_top, mSecondColumnHeight);
				mSecondColumnHeight += imageHeight;
				imageView.setTag(R.string.border_bottom, mSecondColumnHeight);

				return mSecondLinearContainer;
			}
			else
			{
				imageView.setTag(R.string.border_top, mThirdColumnHeight);
				mThirdColumnHeight += imageHeight;
				imageView.setTag(R.string.border_bottom, mThirdColumnHeight);
				return mThirdLinearContainer;
			}
		}

	}
	private class BitmapImageCache implements ImageCache
	{
		private static final int MAX_CACHE_SIZE = 10 * 1024 * 1024;
		private LruCache<String, Bitmap> mLruCache = new LruCache<String, Bitmap>(MAX_CACHE_SIZE);
		@Override
		public Bitmap getBitmap(String url)
		{
			return mLruCache.get(url);
		}

		@Override
		public void putBitmap(String url, Bitmap bitmap)
		{
			mLruCache.put(url, bitmap);

		}

	}
	@Override
	public boolean onTouch(View v, MotionEvent event)
	{
		if (event.getAction() == MotionEvent.ACTION_UP)
		{
			Message message = new Message();
			message.obj = this;
			mHandler.sendMessageDelayed(message, 5);
		}
		return false;

	}
}
