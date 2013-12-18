package com.roboo.like.netease.view.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;
import android.preference.SwitchPreference;
import android.widget.Toast;

import com.roboo.like.netease.R;

@SuppressLint("NewApi")
public class SettingsPreferenceFragment extends PreferenceFragment implements OnPreferenceChangeListener, OnPreferenceClickListener
{
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preferences);

	}
	public static SettingsPreferenceFragment newInstance()
	{
		return new SettingsPreferenceFragment();
	}
	@Override
	public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference)
	{
		if (preference instanceof SwitchPreference)
		{
			System.out.println("preference = " + preference);
			System.out.println("   preference.getKey() =  " + preference.getKey());
		}
		preference.setOnPreferenceChangeListener(this);
		preference.setOnPreferenceClickListener(this);
		return false;

	}
	@Override
	public boolean onPreferenceClick(Preference preference)
	{
		Toast.makeText(getActivity(), "点击", Toast.LENGTH_SHORT).show();
		return false;

	}
	@Override
	public boolean onPreferenceChange(Preference preference, Object newValue)
	{
		Toast.makeText(getActivity(), "改变", Toast.LENGTH_SHORT).show();
		return true;

	}

}
