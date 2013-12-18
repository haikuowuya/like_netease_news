package com.roboo.like.netease.utils;



import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

public class WeatherUtils
{
	/**
	 * 
	 * @param day
	 *            :当前的日期
	 * @param pos
	 *            ：第二天、第三天日期
	 * @return
	 */
	public static String handleWeatherDay(String day, int offSet)
	{
		try
		{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
			SimpleDateFormat formater = new SimpleDateFormat("MM月dd日");
			Date d = sdf.parse(day);
			Date d1 = null;

			if (offSet == 1)// 第一天
			{
				return formater.format(d);
			}
			else 
			{
				d1 = new Date(d.getTime() + 24 * 3600 * 1000*(offSet-1));
				return formater.format(d1);
			}		
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	public static int handleWeatherIcon(String weatherIconString)
	{
		try
		{
			int weatherIcon1 = 0;
			int weatherIcon2 = 0;
			String[] tmp = weatherIconString.split(",");
			if (tmp.length > 1)
			{
				weatherIcon1 = Integer.parseInt(tmp[0]);
				weatherIcon2 = Integer.parseInt(tmp[1]);
			}
			GregorianCalendar ca = new GregorianCalendar();
			switch (ca.get(GregorianCalendar.AM_PM))
			{
			case 0: // AM
				if (weatherIcon1 >= 0 && weatherIcon1 <= 31)
				{
					return weatherIcon1;
				}
				break;
			case 1:// PM
				if ((weatherIcon2 == 99) && (weatherIcon1 >= 0 && weatherIcon1 <= 31))
				{
					return weatherIcon1;
				}
				else if (weatherIcon2 >= 0 && weatherIcon2 <= 31)
				{
					return weatherIcon2;
				}
				break;
			default:
				break;
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return 0;
	}
	/**
	 * 处理获取到的温度使其从低到高
	 * 
	 * @param weatherTemp
	 *            ：从气象局获取到的温度信息
	 * @return：从低到高显示 [-11℃~-3℃]
	 */
	public static String handleWeatherTempFromL2H(String weatherTemp)
	{
		StringBuffer result = null;
		if (weatherTemp != null)
		{
			result = new StringBuffer();
			String[] tmp = weatherTemp.split("~");
			int tempValue1 = 0;
			int tempValue2 = 0;
			if(tmp.length > 1)
			{
				 tempValue1 = Integer.parseInt(tmp[0].split("℃")[0]);
				 tempValue2 = Integer.parseInt(tmp[1].split("℃")[0]);
			}
			if(tempValue1 > tempValue2)
			{
				return tempValue2 + "℃ ~ "+ tempValue1+"℃";
			}
			else
			{
				return tempValue1 + "℃ ~ "+ tempValue2+"℃";
			}
		}
		return null;
	}
}
