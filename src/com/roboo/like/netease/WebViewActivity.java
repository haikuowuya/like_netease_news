package com.roboo.like.netease;

import java.io.IOException;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.roboo.like.netease.utils.NewsUtils;
import com.roboo.like.netease.view.MyProgressBar;

public class WebViewActivity extends BaseActivity
{
	private String mNewsUrl;
	private WebView mWebView;
	private MyProgressBar mProgressBar;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setTVTitle(R.string.tv_news_detail);
		// TODO setContentView Tag
		setContentView(R.layout.activity_webview);
		// TODO initView method Tag
		initView();
		// TODO initWebView method Tag
		initWebView();
		mNewsUrl = getIntent().getStringExtra("newsUrl");
		new GetNewsContentAsyncTask().execute();
	}
	/**
	 * TODO initWebView
	 * 
	 */
	@SuppressLint("SetJavaScriptEnabled")
	private void initWebView()
	{
		mWebView.setInitialScale(100);		
	
		WebSettings settings = this.mWebView.getSettings();
		settings.setDefaultTextEncodingName("UTF-8");
		settings.setTextZoom(150);
		settings.setJavaScriptEnabled(true);
		
		settings.setUseWideViewPort(true);
		settings.setLoadWithOverviewMode(true);
		mWebView.addJavascriptInterface(new Object()
		{
			@SuppressWarnings("unused")
			public void toast(String imgSrc,int currentIndex)
			{
				Intent intent = new Intent(WebViewActivity.this, ShowImageActivity.class);
				if (null != imgSrc && imgSrc.length() > 0)
				{
					intent.putExtra("srcs", imgSrc.split(","));
					intent.putExtra("index", currentIndex);
					System.out.println("currentIndex = " + currentIndex);
					startActivity(intent);
				}
			}
		}, "android");
	}
	/**
	 * TODO initView
	 * 
	 */
	private void initView()
	{
		this.mProgressBar = (MyProgressBar) findViewById(R.id.pb_progress);
		this.mWebView = (WebView) findViewById(R.id.wv_webview);
	}
	private final class GetNewsContentAsyncTask extends AsyncTask<Void, Void, String>
	{

		@Override
		protected String doInBackground(Void... params)
		{
			if (null != mNewsUrl)
			{
				try
				{
					String data = NewsUtils.getITHomeNewsData(mNewsUrl);
					return data;
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
			return null;
		}
		@Override
		protected void onPostExecute(String result)
		{
			super.onPostExecute(result);
			if (null != result)
			{
				System.out.println("result = " + result);
				mWebView.loadData(result, "text/html; charset=UTF-8", null);
			}
			mProgressBar.setVisibility(View.GONE);
		}

	}
}
