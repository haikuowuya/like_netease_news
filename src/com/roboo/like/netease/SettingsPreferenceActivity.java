package com.roboo.like.netease;

import android.os.Bundle;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceActivity;

import com.roboo.like.netease.fragment.SettingsPreferenceFragment;

public class SettingsPreferenceActivity extends BaseActivity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setTVTitle(R.string.tv_settings);
		getFragmentManager().beginTransaction().replace(android.R.id.content,   SettingsPreferenceFragment.newInstance()).commit();
		 
	}
}
