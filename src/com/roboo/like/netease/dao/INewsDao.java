package com.roboo.like.netease.dao;

import java.util.LinkedList;

import com.roboo.like.netease.model.News;

public interface INewsDao
{
	public int insert(News news,String newsCategoryMd5);
	public boolean isNewsExist(String newsMd5);
	public LinkedList<News> getNewsList(String newsCategoryMd5);
	public boolean delete(String newsCategoryMd5);
}
