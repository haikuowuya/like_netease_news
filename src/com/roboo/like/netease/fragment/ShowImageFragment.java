package com.roboo.like.netease.fragment;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader.ImageCache;
import com.android.volley.toolbox.Volley;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.roboo.like.netease.R;
import com.roboo.like.netease.listener.MulitPointTouchListener;

public class ShowImageFragment extends Fragment
{
	private static final String ARG_IMAGE_URL = "arg_image_url";
	private ImageLoader mImageLoader = ImageLoader.getInstance();
	private com.android.volley.toolbox.ImageLoader mVolleyImageLoader;
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		mImageLoader.init(ImageLoaderConfiguration.createDefault(getActivity()));
		RequestQueue queue = Volley.newRequestQueue(getActivity());
		ImageCache imageCache = new ImageCacheImpl();
		mVolleyImageLoader = new com.android.volley.toolbox.ImageLoader(queue, imageCache);
	}
	public static ShowImageFragment newInstance(String imageUrl)
	{
		ShowImageFragment fragment = new ShowImageFragment();
		Bundle bundle = new Bundle();
		bundle.putString(ARG_IMAGE_URL, imageUrl);
		fragment.setArguments(bundle);
		return fragment;
	}
	@SuppressLint("NewApi")
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		String imageUrl = getArguments().getString(ARG_IMAGE_URL);
		View view = inflater.inflate(R.layout.fragment_show_image_imageview, null);
		final ImageView imageView = (ImageView) view.findViewById(R.id.iv_image);
		System.out.println("imageUrl = " + imageUrl);
		if (null != imageUrl)
		{
			// mImageLoader.displayImage(imageUrl, imageView);
			if (mVolleyImageLoader.isCached(imageUrl, 0, 0))
			{
				System.out.println("图片已经缓存 :: " + imageUrl);
			}
			mVolleyImageLoader.get(imageUrl, com.android.volley.toolbox.ImageLoader.getImageListener(imageView, R.drawable.ic_stub, R.drawable.ic_error));
		}
		imageView.setOnTouchListener(new MulitPointTouchListener(imageView));
		imageView.setOnClickListener(new OnClickListener()
		{
			@SuppressLint("NewApi")
			@Override
			public void onClick(View v)
			{
				
				if (getActivity().getActionBar().getCustomView().getVisibility() == View.VISIBLE)
				{
					getActivity().getActionBar().getCustomView().setVisibility(View.GONE);
					getActivity().getActionBar().hide();
				}
				else
				{
					getActivity().getActionBar().show();
					getActivity().getActionBar().getCustomView().setVisibility(View.VISIBLE);
				}
				ObjectAnimator translationRight = ObjectAnimator.ofFloat(imageView, "X", imageView.getWidth());
				ObjectAnimator translationLeft = ObjectAnimator.ofFloat(imageView, "X", 0f);
				ObjectAnimator translationDown = ObjectAnimator.ofFloat(imageView, "Y",
					imageView.getHeight());
				ObjectAnimator translationUp = ObjectAnimator.ofFloat(imageView, "Y", 0);

				AnimatorSet as = new AnimatorSet();
				as.play(translationRight).before(translationLeft);
				as.play(translationRight).with(translationDown);
				as.play(translationLeft).with(translationUp);

				// 和上边四句等效，另一种写法
		        /*
				AnimatorSet as = new AnimatorSet();
				as.playTogether(translationRight, translationDown);
				as.playSequentially(translationRight, translationLeft);
				as.playTogether(translationLeft, translationUp);
		        */
				as.setDuration(1500);
				as.start();
			}
		});
		return view;
	}
	private class ImageCacheImpl implements ImageCache
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

}
