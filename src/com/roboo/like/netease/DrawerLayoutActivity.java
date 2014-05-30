package com.roboo.like.netease;

import java.util.Locale;

import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

@SuppressLint("NewApi")
public class DrawerLayoutActivity extends BaseActivity
{
	private DrawerLayout mDrawerLayout;
	private ListView mLeftDrawerList;
	private ListView mRightDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;

	private CharSequence mDrawerTitle;
	private CharSequence mTitle;
	private String[] mPlanetTitles;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setTVTitle(R.string.tv_drawer_layout);
		// TODO setContentView method Tag
		setContentView(R.layout.activity_drawer_layout);
		mPlanetTitles = getResources().getStringArray(R.array.planets_array);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerLayout.setMinimumWidth((int) (250 * getResources().getDisplayMetrics().density));
		mLeftDrawerList = (ListView) findViewById(R.id.left_drawer);
		mRightDrawerList = (ListView) findViewById(R.id.right_drawer);
	 
		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);

		mLeftDrawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_list_item, mPlanetTitles));
		mLeftDrawerList.setOnItemClickListener(new DrawerItemClickListener());
		mRightDrawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_list_item, mPlanetTitles));
		mRightDrawerList.setOnItemClickListener(new DrawerItemClickListener());

		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.drawable.ic_drawer, R.string.drawer_open, R.string.drawer_close)
		{
			public void onDrawerClosed(View view)
			{
				getActionBar().setTitle(mTitle);
				invalidateOptionsMenu(); 
			}
			public void onDrawerOpened(View drawerView)
			{
				getActionBar().setTitle(mDrawerTitle);
				invalidateOptionsMenu(); 
				if(drawerView == mLeftDrawerList)
				{
					System.out.println("mLeftDrawerList Opened");
					mDrawerLayout.closeDrawer(mRightDrawerList);
					
				}
				if(drawerView == mRightDrawerList)
				{
					mDrawerLayout.closeDrawer(mLeftDrawerList);
					System.out.println("mRightDrawerList Opened");
				}
			}
			@Override
			public void onDrawerSlide(View drawerView, float slideOffset)
			{
				 
				super.onDrawerSlide(drawerView, slideOffset);
			}
			
			
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);

		if (savedInstanceState == null)
		{
			selectItem(0);
		}
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.activity_drawer_layout, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu)
	{
		boolean drawerOpen = mDrawerLayout.isDrawerOpen(mLeftDrawerList);
		menu.findItem(R.id.action_websearch).setVisible(!drawerOpen);
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		if (mDrawerToggle.onOptionsItemSelected(item))
		{
			return true;
		}
		// Handle action buttons
		switch (item.getItemId())
		{
		case R.id.action_websearch:
			
			Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
			intent.putExtra(SearchManager.QUERY, getActionBar().getTitle());
	
			if (intent.resolveActivity(getPackageManager()) != null)
			{
				startActivity(intent);
			}
			else
			{
				Toast.makeText(this, R.string.app_not_available, Toast.LENGTH_LONG).show();
			}
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}	
	private class DrawerItemClickListener implements ListView.OnItemClickListener
	{
		public void onItemClick(AdapterView<?> parent, View view, int position, long id)
		{
			selectItem(position);
		}
	}

	private void selectItem(int position)
	{
		Fragment fragment = new PlanetFragment();
		Bundle args = new Bundle();
		args.putInt(PlanetFragment.ARG_PLANET_NUMBER, position);
		fragment.setArguments(args);

		FragmentManager fragmentManager = getSupportFragmentManager();
		fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();

		
		mLeftDrawerList.setItemChecked(position, true);
		mRightDrawerList.setItemChecked(position, true);
		setTitle(mPlanetTitles[position]);
		mDrawerLayout.closeDrawer(mLeftDrawerList);
		mDrawerLayout.closeDrawer(mRightDrawerList);
	}

	@Override
	public void setTitle(CharSequence title)
	{
		mTitle = title;
		setTVTitle(mTitle);
	}
 
	@Override
	protected void onPostCreate(Bundle savedInstanceState)
	{
		super.onPostCreate(savedInstanceState);
 
		mDrawerToggle.syncState(); 
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig)
	{
		super.onConfigurationChanged(newConfig);
		 
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

 
	public static class PlanetFragment extends Fragment
	{
		public static final String ARG_PLANET_NUMBER = "planet_number";

		public PlanetFragment()
		{}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
		{
			ImageView rootView = (ImageView) inflater.inflate(R.layout.fragment_planet, container, false);
			int i = getArguments().getInt(ARG_PLANET_NUMBER);
			String planet = getResources().getStringArray(R.array.planets_array)[i];

			int imageId = getResources().getIdentifier(planet.toLowerCase(Locale.getDefault()), "drawable", getActivity().getPackageName());

			rootView.setImageResource(imageId);
			getActivity().setTitle(planet);
			return rootView;
		}
	}
}
