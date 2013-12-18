package com.roboo.like.netease;

import com.roboo.like.netease.view.BadgeView;

import android.content.AsyncQueryHandler;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;

public class SettingsActivity extends BaseActivity 
{
	private Button mBtnAccount;
	private Button mBtnBind;
	private Button mBtnFontSetting;
	private Button mBtnFontSizeSetting;
	private Button mBtnNewsPush;
	private Button mBtnWifiDownload;
	private Button mBtn2G3G;
	private Button mBtnAutoLoadMore;
	private Button mBtnClearCache;
	private Button mBtnPlugIn;
	private Button mBtnNewComerGuide;
	private Button mBtnFeedback;
	private Button mBtnAppRecommand;
	private Button mBtnAbout;
	private BadgeView mBadgeView ;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setTVTitle(R.string.tv_settings);
		//TODO setContentView Tag
		setContentView(R.layout.activity_settings);
		initView();
		setListener();
		
	}
	private void setListener()
	{
		this.mBtn2G3G.setOnClickListener(this);
		this.mBtnAbout.setOnClickListener(this);
		this.mBtnAccount.setOnClickListener(this);
		this.mBtnAppRecommand.setOnClickListener(this);
		this.mBtnAutoLoadMore.setOnClickListener(this);
		this.mBtnBind.setOnClickListener(this);
		this.mBtnClearCache.setOnClickListener(this);
		this.mBtnFeedback.setOnClickListener(this);
		this.mBtnFontSetting.setOnClickListener(this);
		this.mBtnFontSizeSetting.setOnClickListener(this);
		this.mBtnNewComerGuide.setOnClickListener(this);
		this.mBtnNewsPush.setOnClickListener(this);
		this.mBtnPlugIn.setOnClickListener(this);
		this.mBtnWifiDownload.setOnClickListener(this);
		
	}
	private void initView()
	{
		this.mBtn2G3G = (Button) findViewById(R.id.btn_2g_3g);
		this.mBtnAbout = (Button) findViewById(R.id.btn_about);
		this.mBtnAccount = (Button) findViewById(R.id.btn_account);
		this.mBtnAppRecommand  = (Button) findViewById(R.id.btn_app_recommand);
		this.mBtnAutoLoadMore = (Button) findViewById(R.id.btn_auto_load_more);
		this.mBtnBind = (Button) findViewById(R.id.btn_bind);
		this.mBtnClearCache = (Button) findViewById(R.id.btn_clear_cache);
		this.mBtnFeedback = (Button) findViewById(R.id.btn_feedback);
		this.mBtnFontSetting = (Button) findViewById(R.id.btn_font_setting);
		
		 mBadgeView = new BadgeView(SettingsActivity.this,this.mBtnFontSetting);
		mBadgeView.setText("New");
		mBadgeView.setBadgeBackgroundColor(Color.RED);
		mBadgeView.setTextSize(10f);
		mBadgeView.setTextColor(Color.WHITE);
		TranslateAnimation anim = new TranslateAnimation(-100, 0, 0, 0);
        anim.setInterpolator(new BounceInterpolator());
        anim.setDuration(1000);
		mBadgeView.show(anim);
		
		
		this.mBtnFontSizeSetting = (Button) findViewById(R.id.btn_font_size_setting);
		this.mBtnNewComerGuide = (Button) findViewById(R.id.btn_newcomer_guide);
		this.mBtnNewsPush = (Button) findViewById(R.id.btn_news_push);
		this.mBtnPlugIn = (Button) findViewById(R.id.btn_plug_in);
		this.mBtnWifiDownload = (Button) findViewById(R.id.btn_wifi_download);
	}
	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
		case R.id.btn_plug_in:
			myStartActivity(PlugInManagerActivity.class);
			break;
		case R.id.btn_2g_3g:
			break;
		case R.id.btn_about:
			 about();
	 
			break;
		case R.id.btn_account:
			account();
			break;
		case R.id.btn_app_recommand:
			break;
		case R.id.btn_auto_load_more:
			break;
		case R.id.btn_bind:
			bind();
			break;
		case R.id.btn_clear_cache:
			break;
		case R.id.btn_feedback:
			feedback();
			break;
		case R.id.btn_font_setting:
			fontSetting();
			break;
		case R.id.btn_font_size_setting:
			
			break;
		case R.id.btn_newcomer_guide:
			guide();
			break;
		case R.id.btn_news_push:
			break;
		case R.id.btn_wifi_download:
			break;
		default:
			super.onClick(v);
			break;
		}
		
	}
	private void fontSetting()
	{
		this.mBadgeView.toggle();
	}
	private void guide()
	{
		myStartActivity(WelcomeActivity.class);
		
	}
	private void account()
	{
		myStartActivity(AccountActivity.class);
	}
	private void feedback()
	{
		myStartActivity(FeedbackActivity.class);
		
	}
	private void bind()
	{
		myStartActivity(BindActivity.class);
	
	}
	private void about()
	{
		myStartActivity(AboutActivity.class);
	}
	private void myStartActivity(Class<?> activity)
	{
		startActivity(new Intent(this,activity));
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.activity_settings, menu);	
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		myStartActivity(SettingsPreferenceActivity.class);
		return true;
		
	}
}
