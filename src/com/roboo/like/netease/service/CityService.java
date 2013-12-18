package com.roboo.like.netease.service;

import java.io.InputStream;

public class CityService
{
	public String getCityJson()
	{
		String json = null;
		InputStream in = getClass().getClassLoader().getResourceAsStream("city.json");
		int len = 0;
		byte[] buffer = new byte[1024];
		StringBuffer sb = new StringBuffer();
		try
		{
			while ((len = in.read(buffer)) != -1)
			{
				sb.append(new String(buffer, 0, len));
			}
			 json = sb.toString();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return json;
	}
}
