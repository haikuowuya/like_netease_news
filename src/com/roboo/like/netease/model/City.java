package com.roboo.like.netease.model;



import java.io.Serializable;

public class City implements Serializable
{
	private static final long serialVersionUID = 1L;
	private String cityName;
	private String cityCode ;
	private String cityProvince ;
	private String cityNote;
	private String cityPinYinName ;
	private int citySelectedCount = 0 ;
	private String sortKey;
	
	public City()
	{
		super();
	}

	public String getSortKey()
	{
		return sortKey;
	}
	public void setSortKey(String sortKey)
	{
		this.sortKey = sortKey;
	}
	public City(String cityName, String cityCode, String cityProvince, String cityNote,
			String cityPinYinName, int citySelectedCount)
	{
		super();
		this.cityName = cityName;
		this.cityCode = cityCode;
		this.cityProvince = cityProvince;
		this.cityNote = cityNote;
		this.cityPinYinName = cityPinYinName;
		this.citySelectedCount = citySelectedCount;
	}



	public int getCitySelectedCount()
	{
		return citySelectedCount;
	}
	public void setCitySelectedCount(int citySelectedCount)
	{
		this.citySelectedCount = citySelectedCount;
	}
	public String getCityName()
	{
		return cityName;
	}

	public void setCityName(String cityName)
	{
		this.cityName = cityName;
	}

	public String getCityCode()
	{
		return cityCode;
	}

	public void setCityCode(String cityCode)
	{
		this.cityCode = cityCode;
	}

	public String getCityPinYinName()
	{
		return cityPinYinName;
	}


	public void setCityPinYinName(String cityPinYinName)
	{
		this.cityPinYinName = cityPinYinName;
	}


	public String getCityProvince()
	{
		return cityProvince;
	}

	public void setCityProvince(String cityProvince)
	{
		this.cityProvince = cityProvince;
	}

	public String getCityNote()
	{
		return cityNote;
	}

	public void setCityNote(String cityNote)
	{
		this.cityNote = cityNote;
	}




	@Override
	public String toString()
	{
		return "City [cityName=" + cityName + ", cityCode=" + cityCode + ", cityProvince="
				+ cityProvince + ", cityNote=" + cityNote + ", cityPinYinName=" + cityPinYinName
				+ ", citySelectedCount=" + citySelectedCount + "]";
	}



	@Override
	public boolean equals(Object o)
	{
		if(o instanceof City)
		{
			 return ((City) o).getCityCode().equals(this.cityCode);
		}
		return false;
	}
}
