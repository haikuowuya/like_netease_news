package com.roboo.like.netease;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.roboo.like.netease.model.ResideMenuItem;
import com.roboo.like.netease.view.ResideMenu;

public class ResideMenuActivity extends BaseActivity
{

	private ResideMenu resideMenu;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{

		super.onCreate(savedInstanceState);
		setTVTitle(R.string.tv_reside_menu);
		// TODO setContentView Tag
		setContentView(R.layout.activity_reside_menu);

		initView();
		setUpMenu();
	}

	private void initView()
	{
		Button btn_open = (Button) findViewById(R.id.btn_open_menu);
		btn_open.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				resideMenu.openMenu();
			}
		});
	}

	private void setUpMenu()
	{

		// attach to current activity;
		resideMenu = new ResideMenu(this);
	
		resideMenu.attachToActivity(this);
		resideMenu.setMenuListener(menuListener);

		// create menu items;
		String titles[] = { "网易新闻", "网易图片", "网易话题", "网易跟帖" };
		int icon[] = { R.drawable.ic_news, R.drawable.ic_image, R.drawable.ic_topic, R.drawable.ic_follow };

		for (int i = 0; i < titles.length; i++)
		{
			ResideMenuItem item = new ResideMenuItem(this, icon[i], titles[i]);
			item.setId(R.id.reside_menu);
			item.setOnClickListener(this);
			resideMenu.addMenuItem(item);
		}

		// add gesture operation's ignored views
		FrameLayout ignored_view = (FrameLayout) findViewById(R.id.ignored_view);
		resideMenu.addIgnoredView(ignored_view);
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev)
	{
		return resideMenu.onInterceptTouchEvent(ev) || super.dispatchTouchEvent(ev);
	}

	@Override
	public void onClick(View view)
	{
		switch (view.getId())
		{
		case R.id.reside_menu:
			resideMenu.closeMenu();			
			break;
		default:
			super.onClick(view);
			break;
		}
	}

	private ResideMenu.OnMenuListener menuListener = new ResideMenu.OnMenuListener()
	{
		@Override
		public void openMenu()
		{
			Toast.makeText(ResideMenuActivity.this, "Menu is opened!", Toast.LENGTH_SHORT).show();
		}

		@Override
		public void closeMenu()
		{
			Toast.makeText(ResideMenuActivity.this, "Menu is closed!", Toast.LENGTH_SHORT).show();
		}
	};
}
