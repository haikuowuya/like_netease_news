package com.roboo.like.netease;

import java.io.File;
import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.renderscript.Script;
import android.support.v4.util.LruCache;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageCache;

public class VolleyActivity extends BaseActivity
{
	private ListView mListView;
	private ListViewAdapter mAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		
		super.onCreate(savedInstanceState);
		 setTVTitle(R.string.tv_android_volley);
		  //TODO setContentView Tag
		 setContentView(R.layout.activity_volley);
		 initView();
		 this.mListView.setAdapter(getAdaper());
	}
	private ListAdapter getAdaper()
	{
		 this.mAdapter = new ListViewAdapter(this);
		return mAdapter;
		
	}
	/**
	 * TODO initView  
	 *
	 */
	private void initView()
	{
		this.mListView = (ListView) findViewById(android.R.id.list);
	}
	private class ListViewAdapter extends BaseAdapter 
	{
		private Context context ;
		private ImageLoader mImageLoader;
		private String[] mImageURLs = {
			"http://h.hiphotos.baidu.com/pic/w%3D310/sign=126f60f24610b912bfc1f0fff3fcfcb5/83025aafa40f4bfb2493b91d024f78f0f7361833.jpg",
			"http://e.hiphotos.baidu.com/pic/w%3D310/sign=ea488121d009b3deebbfe269fcbf6cd3/9345d688d43f879418c46e67d31b0ef41bd53abb.jpg",
			"http://e.hiphotos.baidu.com/pic/w%3D310/sign=dd22abe0a8014c08193b2ea43a7b025b/bf096b63f6246b603835943deaf81a4c510fa2bb.jpg",
			"http://g.hiphotos.baidu.com/pic/w%3D310/sign=0d4f6b89f636afc30e0c39648319eb85/6f061d950a7b020875f419a863d9f2d3572cc8bd.jpg",
			"http://a.hiphotos.baidu.com/pic/w%3D310/sign=b9eba9777aec54e741ec1c1f89399bfd/9d82d158ccbf6c8118de08a1bd3eb13532fa4089.jpg",
			"http://a.hiphotos.baidu.com/pic/w%3D310/sign=0f2f51ded833c895a67e9e7ae1137397/8ad4b31c8701a18b87dd34069f2f07082838fe40.jpg",
			"http://a.hiphotos.baidu.com/pic/w%3D310/sign=d936b29509fa513d51aa6adf0d6c554c/14ce36d3d539b600724a28fee850352ac75cb7f6.jpg",
			"http://b.hiphotos.baidu.com/pic/w%3D310/sign=e2ce8c44f9dcd100cd9cfe20428b47be/c995d143ad4bd113aaf9917b5bafa40f4bfb0546.jpg",
			"http://d.hiphotos.baidu.com/pic/w%3D310/sign=390e688d0bd162d985ee641d21dfa950/1b4c510fd9f9d72a5b5f3654d52a2834349bbbbb.jpg",
			"http://b.hiphotos.baidu.com/pic/w%3D310/sign=a6e1ab6bc9fcc3ceb4c0cf32a244d6b7/a50f4bfbfbedab64e2be6a89f636afc378311eae.jpg",
			"http://e.hiphotos.baidu.com/pic/w%3D310/sign=b351ab419a504fc2a25fb604d5dce7f0/18d8bc3eb13533fa1ef4b467a9d3fd1f41345b3c.jpg",
			"http://a.hiphotos.baidu.com/pic/w%3D310/sign=bfd8d84471cf3bc7e800cbede101babd/0e2442a7d933c8950992bc32d01373f08302008e.jpg",
			"http://c.hiphotos.baidu.com/pic/w%3D310/sign=6bea19e4b219ebc4c0787098b227cf79/7af40ad162d9f2d345f7026ea8ec8a136227cc48.jpg",
			"http://f.hiphotos.baidu.com/pic/w%3D310/sign=a7bcc3a70824ab18e016e73605fbe69a/728da9773912b31b458beba88718367adbb4e148.jpg",
			"http://e.hiphotos.baidu.com/pic/w%3D310/sign=424141d7d1160924dc25a41ae406359b/f703738da977391247a7a3e0f9198618377ae2aa.jpg",
			"http://b.hiphotos.baidu.com/pic/w%3D310/sign=47c6c188b7003af34dbada61052bc619/b8389b504fc2d562f420f16ce61190ef76c66c02.jpg",
			"http://e.hiphotos.baidu.com/album/h%3D1024%3Bcrop%3D0%2C0%2C1280%2C1024/sign=af09da5b14ce36d3bd04873008c701e4/f703738da977391247a7a3e0f9198618377ae2aa.jpg"
		};
		public ListViewAdapter(Context context)
		{
			super();
			this.context = context;
			RequestQueue queue = null; 
			//queue = Volley.newRequestQueue(context);//默认缓存图片存放在/data/data/pacakgename/cache/volley/目录下,需要手机root后才可以看到
			
			Cache cache = new DiskBasedCache(new File("/mnt/sdcard/android","volley"));
			Network network =  new BasicNetwork(new HurlStack());
			queue = new RequestQueue(cache, network);
			queue.start();
			ImageCache imageCache = new BitmapCache();
			this.mImageLoader = new ImageLoader(queue, imageCache);
		
			
		}

		@Override
		public int getCount()
		{
			return mImageURLs.length;
			
		}

		@Override
		public Object getItem(int position)
		{
			return mImageURLs[position];
			
		}

		@Override
		public long getItemId(int position)
		{
			return position;
			
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent)
		{
			ImageView imageView = new ImageView(context);
			
			mImageLoader.get(mImageURLs[position],ImageLoader.getImageListener(imageView, R.drawable.ic_stub, R.drawable.ic_error));
			imageView.setOnClickListener(new OnClickListener()
			{
				
				@Override
				public void onClick(View v)
				{
					Intent intent = new Intent(VolleyActivity.this,ShowImageActivity.class);				 
					intent.putExtra("srcs", mImageURLs);
					intent.putExtra("index", position);
					startActivity(intent);					
				}
			});
			return imageView;
			
		}
  
		
		private class BitmapCache implements ImageCache
		{
			private static final int MAX_CACHE_SIZE= 10*1024*1024;
			private LruCache<String	, Bitmap> mLruCache = new LruCache<String, Bitmap>(MAX_CACHE_SIZE);
			
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
}
