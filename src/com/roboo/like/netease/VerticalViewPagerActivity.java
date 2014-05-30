package com.roboo.like.netease;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.roboo.like.netease.view.VerticalViewPager;

public class VerticalViewPagerActivity extends BaseActivity
{
	private static final float MIN_SCALE = 0.75f;
	private static final float MIN_ALPHA = 0.75f;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{

		super.onCreate(savedInstanceState);
		setTVTitle(R.string.tv_vertical_viewpager);
		FrameLayout frameLayout = new FrameLayout(this);
		//手动创建的ViewPager需要设置一个id 否则报错！
		VerticalViewPager verticalViewPager = new VerticalViewPager(this);
		verticalViewPager.setId(R.id.viewpager);
		frameLayout.addView(verticalViewPager, new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
		
		// TODO setContentView Tag
		setContentView(frameLayout);

		verticalViewPager.setAdapter(new DummyAdapter(getSupportFragmentManager()));
		verticalViewPager.setPageMargin(getResources().getDimensionPixelSize(R.dimen.pager_margin));
		verticalViewPager.setPageMarginDrawable(new ColorDrawable(0xFF669900));

		verticalViewPager.setPageTransformer(true, new ViewPager.PageTransformer()
		{
			@Override
			public void transformPage(View view, float position)
			{
				int pageWidth = view.getWidth();
				int pageHeight = view.getHeight();

				if (position < -1)
				{ // [-Infinity,-1)
					// This page is way off-screen to the left.
					view.setAlpha(0);

				}
				else if (position <= 1)
				{ // [-1,1]
					// Modify the default slide transition to shrink the page as
					// well
					float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
					float vertMargin = pageHeight * (1 - scaleFactor) / 2;
					float horzMargin = pageWidth * (1 - scaleFactor) / 2;
					if (position < 0)
					{
						view.setTranslationY(vertMargin - horzMargin / 2);
					}
					else
					{
						view.setTranslationY(-vertMargin + horzMargin / 2);
					}

					// Scale the page down (between MIN_SCALE and 1)
					view.setScaleX(scaleFactor);
					view.setScaleY(scaleFactor);

					// Fade the page relative to its size.
					view.setAlpha(MIN_ALPHA + (scaleFactor - MIN_SCALE) / (1 - MIN_SCALE) * (1 - MIN_ALPHA));

				}
				else
				{ // (1,+Infinity]
					// This page is way off-screen to the right.
					view.setAlpha(0);
				}
			}
		});
	}

	public class DummyAdapter extends FragmentPagerAdapter
	{

		public DummyAdapter(FragmentManager fm)
		{
			super(fm);
		}

		@Override
		public Fragment getItem(int position)
		{

			return PlaceholderFragment.newInstance(position + 1);
		}

		@Override
		public int getCount()
		{

			return 3;
		}

		@Override
		public CharSequence getPageTitle(int position)
		{
			switch (position)
			{
			case 0:
				return "PAGE 1";
			case 1:
				return "PAGE 2";
			case 2:
				return "PAGE 3";
			}
			return null;
		}

	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment
	{
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		private static final String ARG_SECTION_NUMBER = "section_number";

		/**
		 * Returns a new instance of this fragment for the given section number.
		 */
		public static PlaceholderFragment newInstance(int sectionNumber)
		{
			PlaceholderFragment fragment = new PlaceholderFragment();
			Bundle args = new Bundle();
			args.putInt(ARG_SECTION_NUMBER, sectionNumber);
			fragment.setArguments(args);
			return fragment;
		}

		public PlaceholderFragment()
		{
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
		{
			LinearLayout linearLayout = new LinearLayout(getActivity());
			linearLayout.setGravity(Gravity.CENTER);
			TextView textView = new TextView(getActivity());
			textView.setText(Integer.toString(getArguments().getInt(ARG_SECTION_NUMBER)));
			linearLayout.addView(textView);;
			return linearLayout;
		}

	}

}