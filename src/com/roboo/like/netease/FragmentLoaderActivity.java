package com.roboo.like.netease;

import com.roboo.like.netease.view.fragment.LoaderFragment;

import android.os.Bundle;
import android.widget.TextView;

public class FragmentLoaderActivity extends BaseActivity
{
 
 
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		 
		super.onCreate(savedInstanceState);
		 setTVTitle(R.string.tv_fragment_loader);
		 //TODO setContentView Tag
		 setContentView(R.layout.activity_fragment_loader);
		 getSupportFragmentManager().beginTransaction().add(R.id.frame_container, LoaderFragment.newInstance(null)).commit();
		 
	}
}
