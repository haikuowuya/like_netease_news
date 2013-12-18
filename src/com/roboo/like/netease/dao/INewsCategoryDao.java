package com.roboo.like.netease.dao;

import java.util.LinkedList;

import com.roboo.like.netease.model.NewsCategory;

public interface INewsCategoryDao
{
	/**
	 * TODO 
	 * @param data
	 * @return
	 */
	public int insert(NewsCategory  data);
	/**
	 * TODO 
	 * @return
	 */
	public LinkedList<NewsCategory> getCustomedList();
	public LinkedList<NewsCategory> getUnCustomedList();
	public LinkedList<NewsCategory> getNewsCategoryList();
	public boolean isNewsCategoryExist(String newsCategoryMd5);
	public boolean  updateNewsCategoryState(String newsCategoryMd5,boolean isCustomed);
	public boolean updateNewsCategoryOrder(String newsCategoryMd5,int order);
}
