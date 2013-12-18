package com.roboo.like.netease.model;



import java.io.Serializable;

import android.os.Parcel;
import android.os.Parcelable;

public class Weather implements Parcelable,Serializable
{
	private static final long serialVersionUID = 123453453523L;
	private String weatherCityCode = "010100";// 北京
	private String week = "";
	private String date = "";
	private String temp = "";
	private String weatherDescription = "";// 晴到多云
	private String weatherIcon = ""; 
	private String wind = "";// 风速
	private String windLevel = "";// 风速级别
	private String weatherCityName = "北京";
	
	public Weather()
	{
		super();
		// TODO Auto-generated constructor stub
	}
	public String getWeatherCityName()
	{
		return weatherCityName;
	}
	public void setWeatherCityName(String weatherCityName)
	{
		this.weatherCityName = weatherCityName;
	}
	public String getWeatherCityCode()
	{
		return weatherCityCode;
	}
	public void setWeatherCityCode(String weatherCityCode)
	{
		this.weatherCityCode = weatherCityCode;
	}
	public String getWeek()
	{
		return week;
	}
	public void setWeek(String week)
	{
		this.week = week;
	}
	public String getDate()
	{
		return date;
	}
	public void setDate(String date)
	{
		this.date = date;
	}
	public String getTemp()
	{
		return temp;
	}
	public void setTemp(String temp)
	{
		this.temp = temp;
	}
	public String getWeatherDescription()
	{
		return weatherDescription;
	}
	public void setWeatherDescription(String weatherDescription)
	{
		this.weatherDescription = weatherDescription;
	}
	public String getWeatherIcon()
	{
		return weatherIcon;
	}
	public void setWeatherIcon(String weatherIcon)
	{
		this.weatherIcon = weatherIcon;
	}
	public String getWind()
	{
		return wind;
	}
	public void setWind(String wind)
	{
		this.wind = wind;
	}
	public String getWindLevel()
	{
		return windLevel;
	}
	public void setWindLevel(String windLevel)
	{
		this.windLevel = windLevel;
	}
	@Override
	public String toString()
	{
		return this.weatherCityName + " :: "+ this.date + " :: " + this.week + " :: " + this.weatherCityCode +
				" :: " + this.temp + " :: " + this.weatherDescription + " :: " +
				this.wind + " :: " + this.windLevel;
	}
	 
	      public int describeContents() {
	    	// 内容描述接口，基本不用管
	          return 0;
	      }
	  
	      @Override
	      public void writeToParcel(Parcel dest, int flags) {
	    	// 读取接口，目的是要从Parcel中构造一个实现了parcelable的类的实例处理。因为实现类在这里还是不可知的，所以需要用到模板的方式，继承类名通过模板参数传入。

	          // 1.必须按成员变量声明的顺序封装数据，不然会出现获取数据出错
	          // 2.序列化对象
//				private String weatherCityCode = "010100";// 北京
//				private String week = "";
//				private String date = "";
//				private String temp = "";
//				private String weatherDescription = "";// 晴到多云
//				private String weatherIcon = "";
//				private String wind = "";// 风速
//				private String windLevel = "";// 风速级别
	    	  dest.writeString(weatherCityCode);
	    	  dest.writeString(week);
	    	  dest.writeString(date);
	    	  dest.writeString(temp);
	    	  dest.writeString(weatherDescription);
	    	  dest.writeString(weatherIcon);
	    	  dest.writeString(wind);
	    	  dest.writeString(windLevel);
	    	  dest.writeString(weatherCityName);
	      }


	// 为了能够实现模板参数的传入，这里定义Creator嵌入接口,内含两个接口函数分别返回单个和多个继承类实例。
	public static final Parcelable.Creator<Weather> CREATOR = new Parcelable.Creator()
	{
		public Weather createFromParcel(Parcel source)
		{
			// TODO Auto-generated method stub
			// 必须按成员变量声明的顺序读取数据，不然会出现获取数据出错
			Weather weather = new Weather();			
//			private String weatherCityCode = "010100";// 北京
//			private String week = "";
//			private String date = "";
//			private String temp = "";
//			private String weatherDescription = "";// 晴到多云
//			private String weatherIcon = "";
//			private String wind = "";// 风速
//			private String windLevel = "";// 风速级别
			weather.setWeatherCityCode(source.readString());
			weather.setWeek(source.readString());
			weather.setDate(source.readString());
			weather.setTemp(source.readString());
			weather.setWeatherDescription(source.readString());
			weather.setWeatherIcon(source.readString());
			weather.setWind(source.readString());
			weather.setWindLevel(source.readString());
			weather.setWeatherCityName(source.readString());
			return weather;
		}

		public Weather[] newArray(int size)
		{
			// TODO Auto-generated method stub
			return new Weather[size];
		}
	};
}
