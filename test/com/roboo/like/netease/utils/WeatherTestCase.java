package com.roboo.like.netease.utils;

import java.io.IOException;
import java.util.LinkedList;

import com.roboo.like.netease.model.Weather;
import com.roboo.like.netease.service.WeatherService;

import android.test.AndroidTestCase;

public class WeatherTestCase extends AndroidTestCase
{
	public void testGetWeatherJson() throws IOException, Exception
	{
		WeatherService service = new WeatherService();
		String json = service.getWeatherJson("180802");
		System.out.println("json = " + json );
		 LinkedList<Weather> data = JsonUtils.handleWeatherJson(json);
		 if(null != data)
		 {
			 for(Weather weather :data)
			 {
				 System.out.println(weather);
			 }
		 }
	}

}
