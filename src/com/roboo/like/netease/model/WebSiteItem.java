package com.roboo.like.netease.model;

import java.util.LinkedList;

public class WebSiteItem
{
	private String img;
	private String name;
	private String url;
	private LinkedList<WebSiteItem> childList;
	private LinkedList<WebSiteItem> siteList;
	public String getImg()
	{
		return img;
	}
	public void setImg(String img)
	{
		this.img = img;
	}
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public String getUrl()
	{
		return url;
	}
	public void setUrl(String url)
	{
		this.url = url;
	}
	public LinkedList<WebSiteItem> getChildList()
	{
		return childList;
	}
	public void setChildList(LinkedList<WebSiteItem> childList)
	{
		this.childList = childList;
	}
	
	public LinkedList<WebSiteItem> getSiteList()
	{
		return siteList;
	}
	public void setSiteList(LinkedList<WebSiteItem> siteList)
	{
		this.siteList = siteList;
	}
	public WebSiteItem()
	{
		super();
	}
	public WebSiteItem(String img, String name, String url)
	{
		super();
		this.img = img;
		this.name = name;
		this.url = url;
	}
	@Override
	public boolean equals(Object o)
	{
		if (null != o && o instanceof WebSiteItem)
		{
			return this.url.equals(((WebSiteItem) o).getUrl());
		}
		return false;
	}
	@Override
	public String toString()
	{
		return "url = " + this.url + " img =  " + this.img + " name = " + this.name
			+ " siteList.size()  = " + (null == siteList ? 0:siteList.size()) + " siteList  = " +siteList
			+"  childList.size() = " +(null == childList ? 0: childList.size()) + " childList = "+ childList;
	}
}
