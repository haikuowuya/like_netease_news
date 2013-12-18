package com.roboo.like.netease.model;

import java.io.Serializable;

public class NewsCategory implements Serializable
{

	private static final long serialVersionUID = 1567546241L;
	private String newsCategoryMD5;
	private String newsCategoryName;
	private int newsCategoryOrder = -1;
	private String newsCategoryUrl ;
	private int _id;
	private String newsCategoryNote ;
	private String newsCateogryIsCustomed ="0"; //0 no  1 yes;
	public String getNewsCategoryMD5()
	{
		return newsCategoryMD5;
	}
	public void setNewsCategoryMD5(String newsCategoryMD5)
	{
		this.newsCategoryMD5 = newsCategoryMD5;
	}
	public String getNewsCategoryName()
	{
		return newsCategoryName;
	}
	
	public String getNewsCategoryUrl()
	{
		return newsCategoryUrl;
	}
	public void setNewsCategoryUrl(String newsCategoryUrl)
	{
		this.newsCategoryUrl = newsCategoryUrl;
	}
	public void setNewsCategoryName(String newsCategoryName)
	{
		this.newsCategoryName = newsCategoryName;
	}
	public int getNewsCategoryOrder()
	{
		return newsCategoryOrder;
	}
	public void setNewsCategoryOrder(int newsCategoryOrder)
	{
		this.newsCategoryOrder = newsCategoryOrder;
	}

	public String getNewsCategoryNote()
	{
		return newsCategoryNote;
	}
	public void setNewsCategoryNote(String newsCategoryNote)
	{
		this.newsCategoryNote = newsCategoryNote;
	}
	public String getNewsCateogryIsCustomed()
	{
		return newsCateogryIsCustomed;
	}
	public void setNewsCateogryIsCustomed(String newsCateogryIsCustomed)
	{
		this.newsCateogryIsCustomed = newsCateogryIsCustomed;
	}
	public NewsCategory()
	{
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	public NewsCategory(String newsCategoryMD5, String newsCategoryName, int newsCategoryOrder, String newsCategoryNote, String newsCateogryIsCustomed, String newsCategoryUrl)
	{
		super();
		this.newsCategoryMD5 = newsCategoryMD5;
		this.newsCategoryName = newsCategoryName;
		this.newsCategoryOrder = newsCategoryOrder;
		this.newsCategoryNote = newsCategoryNote;
		this.newsCateogryIsCustomed = newsCateogryIsCustomed;
		this.newsCategoryUrl = newsCategoryUrl;
	}
	@Override
	public boolean equals(Object obj)
	{
		if(null != obj && obj instanceof NewsCategory)
		{
			return this.newsCategoryMD5.equals(((NewsCategory)obj).getNewsCategoryMD5())
				&&this.newsCategoryName.equals(((NewsCategory)obj).getNewsCategoryName());
		}
		return false;
	}
	
}
