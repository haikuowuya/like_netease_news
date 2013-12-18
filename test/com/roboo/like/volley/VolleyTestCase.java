package com.roboo.like.volley;

import org.json.JSONObject;

import android.test.AndroidTestCase;

import com.android.volley.RequestQueue;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

public class VolleyTestCase extends AndroidTestCase
{
	public void testVolley()
	{
		  RequestQueue queue = Volley.newRequestQueue(mContext);
		  System.out.println("mContext.getCacheDir().getAbsolutePath() = " + mContext.getCacheDir().getAbsolutePath());
	}
	public  void testJsonRequest()
	{
		String weatherUrl = "http://m.weather.com.cn/data/101190401.html";
		 RequestQueue queue = Volley.newRequestQueue(mContext);
		Listener<JSONObject> listener = new Listener<JSONObject>()
		{

			@Override
			public void onResponse(JSONObject response)
			{
				System.out.println("response = " + response);
			}
		};
		queue.add(new JsonObjectRequest(weatherUrl, null , listener, null));
		 queue.start();
		 System.out.println("KKKKKKKKKKKKKKKKKKKK");
	}
	public void getAndroidCacheDir()
	{
		//: /data/data/+packageName +/cache;// /data/data/com.roboo.like.netease/cache
		System.out.println("Android缓存目录 = "+ mContext.getCacheDir().getAbsolutePath());
	}
	
}
