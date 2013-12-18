package com.roboo.like.netease.view.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.FrameLayout.LayoutParams;
import android.widget.TextView;

import com.roboo.like.netease.R;
import com.roboo.like.netease.model.Weather;

public class WeatherFragment extends Fragment
{

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
	}
	public static WeatherFragment newInstance(Weather weather)
	{
		WeatherFragment fragment = new WeatherFragment();
		Bundle bundle = new Bundle();
		bundle.putSerializable("weather", weather);
		fragment.setArguments(bundle);
		return fragment;
	}
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		Weather weather = (Weather) getArguments().getSerializable("weather");
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		FrameLayout frameLayout = new FrameLayout(getActivity());
		frameLayout.setLayoutParams(params);
		View view = null;
		view = inflater.inflate(R.layout.fragment_weather, null);

		ViewHolder holder = null;
		if (null != weather)
		{
			holder = new ViewHolder();
			holder.mTVTemp = (TextView) view.findViewById(R.id.tv_temp);
			holder.mTVWeek = (TextView) view.findViewById(R.id.tv_week);
			holder.mTVWind = (TextView) view.findViewById(R.id.tv_wind);
			holder.mIVImage = (ImageView) view.findViewById(R.id.iv_image);

			String weatherDescription = weather.getWeatherDescription();
			if (null != weatherDescription)
			{
				seWweatherBackgroud(weatherDescription, frameLayout, holder.mIVImage);
			}
			else
			{
				frameLayout.setBackgroundResource(R.drawable.ic_weather_background);
				holder.mIVImage.setBackgroundResource(R.drawable.ic_weather_na);
			}

			holder.mTVWeek.setText(weather.getWeek());
			holder.mTVTemp.setText(weather.getTemp());
			holder.mTVWind.setText(weather.getWeatherDescription() + " " + weather.getWind() + " " + weather.getWindLevel());
		}
		frameLayout.addView(view);
		return frameLayout;
	}
	private void seWweatherBackgroud(String weatherDescription, ViewGroup container, ImageView view)
	{
		if (weatherDescription.contains("晴"))
		{
			if (weatherDescription.contains("云"))
			{
				container.setBackgroundResource(R.drawable.ic_weather_background_cloudy);
				view.setBackgroundResource(R.drawable.ic_weather_cloudy);
			}
			else
			{
				container.setBackgroundResource(R.drawable.ic_weather_background_clear);
				view.setBackgroundResource(R.drawable.ic_weather_clear);
			}
		}
		else if (weatherDescription.contains("阴"))
		{
			if (weatherDescription.contains("云"))
			{
				container.setBackgroundResource(R.drawable.ic_weather_background_cloudy);
				view.setBackgroundResource(R.drawable.ic_weather_cloudy);
			}
			else
			{
				container.setBackgroundResource(R.drawable.ic_weather_background_cloudy_day);
				view.setBackgroundResource(R.drawable.ic_weather_cloudy_day);
			}
		}
		else if(weatherDescription.contains("多云"))
		{
			container.setBackgroundResource(R.drawable.ic_weather_background_cloudy);
			view.setBackgroundResource(R.drawable.ic_weather_cloudy);
		}
		else if (weatherDescription.contains("尘") || weatherDescription.contains("土"))
		{
			container.setBackgroundResource(R.drawable.ic_weather_background_dust);
			view.setBackgroundResource(R.drawable.ic_weather_dust);
		}
		else if (weatherDescription.contains("雨"))
		{
			container.setBackgroundResource(R.drawable.ic_weather_background_rain);
			view.setBackgroundResource(R.drawable.ic_weather_rain);
		}
		else if (weatherDescription.contains("雾"))
		{
			container.setBackgroundResource(R.drawable.ic_weather_background_fog);
			view.setBackgroundResource(R.drawable.ic_weather_fog);
		}
		else if (weatherDescription.contains("雪"))
		{
			container.setBackgroundResource(R.drawable.ic_weather_background_snow);
			view.setBackgroundResource(R.drawable.ic_weather_snow);
		}
	}
	private class ViewHolder
	{
		public TextView mTVWeek;
		public TextView mTVTemp;
		public TextView mTVWind;
		public ImageView mIVImage;
	}
}
