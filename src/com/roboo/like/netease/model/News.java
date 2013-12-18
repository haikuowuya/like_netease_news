package com.roboo.like.netease.model;

import java.io.Serializable;

public class News implements Serializable
{
	private static final long serialVersionUID = 2452345351L;
	private String md5 ;
	private String title;
	private String subTitle;
	private String url;
	private String src;
	private String note = "新闻备注";
	private String source;
	private long date = System.currentTimeMillis();
	private String newsCategoryMd5;
	public News()
	{
		super();
	}
	public News(String md5, String title, String subTitle, String url, String src)
	{
		super();
		this.md5 = md5;
		this.title = title;
		this.subTitle = subTitle;
		this.url = url;
		this.src = src;
	}
	public News(String md5, String title, String subTitle, String url, String src, String source)
	{
		super();
		this.md5 = md5;

		this.title = title;
		this.subTitle = subTitle;
		this.url = url;
		this.src = src;
		this.source = source;
	}
	public News(String md5, String title, String subTitle, String url, String src, String note, String source, long date)
	{
		super();
		this.md5 = md5;
		this.title = title;
		this.subTitle = subTitle;
		this.url = url;
		this.src = src;
		this.note = note;
		this.source = source;
		this.date = date;
	}
	
	public String getNewsCategoryMd5()
	{
		return newsCategoryMd5;
	}
	public void setNewsCategoryMd5(String newsCategoryMd5)
	{
		this.newsCategoryMd5 = newsCategoryMd5;
	}
	public String getMd5()
	{
		return md5;
	}
	public void setMd5(String md5)
	{
		this.md5 = md5;
	}
	
	public String getTitle()
	{
		return title;
	}
	public void setTitle(String title)
	{
		this.title = title;
	}
	public String getSubTitle()
	{
		return subTitle;
	}
	public void setSubTitle(String subTitle)
	{
		this.subTitle = subTitle;
	}
	public String getUrl()
	{
		return url;
	}
	public void setUrl(String url)
	{
		this.url = url;
	}
	public String getSrc()
	{
		return src;
	}
	public void setSrc(String src)
	{
		this.src = src;
	}
	public String getNote()
	{
		return note;
	}
	public void setNote(String note)
	{
		this.note = note;
	}
	public String getSource()
	{
		return source;
	}
	public void setSource(String source)
	{
		this.source = source;
	}
	public long getDate()
	{
		return date;
	}
	public void setDate(long date)
	{
		this.date = date;
	}
	@Override
	public boolean equals(Object o)
	{
		if(null != o && o instanceof News)
		{
			return this.md5.equals(((News)o).getMd5());
		}
		return false;
		
	}
	@Override
	public String toString()
	{
	
		return  "md5 = " + md5 + " url = " + url + " title = " + title
			+" subTitle = " + subTitle + " src = "+ src ;
		
	}
	
}
