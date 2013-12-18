package com.roboo.like.netease.model;

import android.R.integer;

public class ShareType
{
	private int shareType;
	private String shareTypeName;
	private String shareTypeNote;
	private int shareTypeImage;
	
	public ShareType()
	{
		super();
	}
	


	public ShareType(int shareType, String shareTypeName, String shareTypeNote, int shareTypeImage)
	{
		super();
		this.shareType = shareType;
		this.shareTypeName = shareTypeName;
		this.shareTypeNote = shareTypeNote;
		this.shareTypeImage = shareTypeImage;
	}



	public int getShareTypeImage()
	{
		return shareTypeImage;
	}



	public void setShareTypeImage(int shareTypeImage)
	{
		this.shareTypeImage = shareTypeImage;
	}



	public int getShareType()
	{
		return shareType;
	}
	public void setShareType(int shareType)
	{
		this.shareType = shareType;
	}
	public String getShareTypeName()
	{
		return shareTypeName;
	}
	public void setShareTypeName(String shareTypeName)
	{
		this.shareTypeName = shareTypeName;
	}
	public String getShareTypeNote()
	{
		return shareTypeNote;
	}
	public void setShareTypeNote(String shareTypeNote)
	{
		this.shareTypeNote = shareTypeNote;
	}
	
}
