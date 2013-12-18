package com.roboo.like.netease.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.http.HttpStatus;
import org.apache.http.protocol.HTTP;

import com.roboo.like.netease.NewsApplication;

public class WeatherService
{
	public String getWeatherJson(String cityCode) throws Exception, IOException
	{
		String json = null;
		HttpURLConnection conn = (HttpURLConnection) new URL(NewsApplication.WEATHER_URL + cityCode + ".html")
				.openConnection();
		conn.setConnectTimeout(5000);
		conn.setReadTimeout(2000);
		// 服务器端返回的响应码
		int responseCode = conn.getResponseCode();
		if (responseCode == HttpStatus.SC_OK)
		{
			InputStream in = conn.getInputStream();
			InputStreamReader isr = new InputStreamReader(in, HTTP.UTF_8);
			char[] buffer = new char[1024];
			int len = 0;
			StringBuffer sb = new StringBuffer();
			while ((len = isr.read(buffer)) != -1)
			{
				sb.append(buffer, 0, len);
			}
			if ((json = sb.toString()) != null)
				return json.trim();
		}
		else if (responseCode == HttpStatus.SC_INTERNAL_SERVER_ERROR)
		{
			System.out.println("服务器端正在维护……");
		}
		System.out.println("responseCode = " + responseCode);

		return json;
	}
}
