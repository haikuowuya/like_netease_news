package com.roboo.like.netease.adapter;

import java.util.LinkedList;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.Animator.AnimatorListener;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.roboo.like.netease.R;
import com.roboo.like.netease.model.News;

public class MainListViewAdapter extends BaseAdapter
{
	private Context context;
	private LinkedList<News> data;
	private ImageLoader mImageLoader;

	public MainListViewAdapter(Context context, LinkedList<News> data, ImageLoader imageLoader)
	{
		super();
		this.context = context;
		this.data = data;
		this.mImageLoader = imageLoader;

		this.mImageLoader.init(ImageLoaderConfiguration.createDefault(context));

	}

	@Override
	public int getCount()
	{
		return null == data ? 0 : data.size();
	}

	@Override
	public Object getItem(int position)
	{
		return null == data ? null : data.get(position);
	}

	@Override
	public long getItemId(int position)
	{
		return position;
	}

	/**
	 * TODO getView
	 */
	public View getView(int position, View convertView, ViewGroup parent)
	{
		ViewHolder holder = null;
		News news = data.get(position);
		if (null == convertView)
		{
			holder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(R.layout.news_list_item, null);
			holder.mIVImage = (ImageView) convertView.findViewById(R.id.iv_image);
			holder.mTVTitle = (TextView) convertView.findViewById(R.id.tv_title);
			holder.mTVSubTitle = (TextView) convertView.findViewById(R.id.tv_sub_title);
			convertView.setTag(holder);
		}
		else
		{
			holder = (ViewHolder) convertView.getTag();
			ObjectAnimator translateX = ObjectAnimator.ofFloat(convertView, "translationX",0f, context.getResources().getDisplayMetrics().widthPixels );
			AnimatorSet set = new AnimatorSet();
			set.play(translateX);
			set.setStartDelay( 0);
			set.setDuration( 0);
			set.start();
		}
		holder.mTVTitle.setText(news.getTitle());
		holder.mTVSubTitle.setText(news.getSubTitle());
		DisplayImageOptions options = new DisplayImageOptions.Builder().showStubImage(R.drawable.ic_stub).showImageForEmptyUri(R.drawable.ic_empty)
				.showImageOnFail(R.drawable.ic_error).cacheInMemory().cacheOnDisc().bitmapConfig(Bitmap.Config.RGB_565).build();
		mImageLoader.displayImage(news.getSrc(), holder.mIVImage, options);
		ObjectAnimator scaleX = ObjectAnimator.ofFloat(convertView, "scaleX", 0f, 1f);
		ObjectAnimator scaleY = ObjectAnimator.ofFloat(convertView, "alpha", 0f, 1f);
		AnimatorSet set = new AnimatorSet();
		// set.playTogether(new Animator[]{scaleX,scaleY});
		ObjectAnimator translateX = ObjectAnimator.ofFloat(convertView, "translationX", context.getResources().getDisplayMetrics().widthPixels, 0f);
		set.play(translateX);
		set.setStartDelay(100);
		set.setDuration(300);
		set.start();
		set.addListener(new AnimatorListener()
		{

			@Override
			public void onAnimationStart(Animator animation)
			{
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationRepeat(Animator animation)
			{
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationEnd(Animator animation)
			{
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationCancel(Animator animation)
			{
				// TODO Auto-generated method stub

			}
		});
		return convertView;

	}

	private class ViewHolder
	{
		public TextView mTVTitle;
		public TextView mTVSubTitle;
		public ImageView mIVImage;
	}

	public LinkedList<News> getData()
	{
		return data;
	}
}
