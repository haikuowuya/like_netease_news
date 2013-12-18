package com.roboo.like.netease.model;

import java.util.LinkedList;

public class NewsContent
{
	private LinkedList<String > srcList;
	private LinkedList<String> contentList;
	
	public NewsContent()
	{
		super();
		// TODO Auto-generated constructor stub
	}

	public NewsContent(LinkedList<String> srcList, LinkedList<String> contentList)
	{
		super();
		this.srcList = srcList;
		this.contentList = contentList;
	}

	public LinkedList<String> getSrcList()
	{
		return srcList;
	}

	public void setSrcList(LinkedList<String> srcList)
	{
		this.srcList = srcList;
	}

	public LinkedList<String> getContentList()
	{
		return contentList;
	}

	public void setContentList(LinkedList<String> contentList)
	{
		this.contentList = contentList;
	}
	
	
}
