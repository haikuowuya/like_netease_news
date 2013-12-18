package com.roboo.like.netease.dao.impl;

import java.util.LinkedList;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.roboo.like.netease.NewsApplication;
import com.roboo.like.netease.dao.INewsDao;
import com.roboo.like.netease.database.DBHelper;
import com.roboo.like.netease.model.News;
import com.roboo.like.netease.utils.MD5Utils;

public class NewsDaoImpl implements INewsDao
{
	private DBHelper helper;

	public NewsDaoImpl(DBHelper helper)
	{
		this.helper = helper;
	}

	@Override
	public int insert(News news, String newsCategoryMd5)
	{

		int insertSum = 0;
		if (null != news)
		{
			if (!isNewsExist(news.getMd5()) && null != newsCategoryMd5)
			{
				SQLiteDatabase db = helper.getReadableDatabase();
				ContentValues values = new ContentValues();
				values.put("news_category_md5", newsCategoryMd5);
				values.put("news_url", news.getUrl());
				values.put("news_src", news.getSrc());
				values.put("news_title", news.getTitle());
				values.put("news_sub_title", news.getSubTitle());
				values.put("news_source", news.getSource());
				values.put("news_note", news.getNote());
				values.put("news_md5", MD5Utils.generate(news.getUrl()));
				db.insert(NewsApplication.TABLE_NEWS_LIST, null, values);
				db.close();
				insertSum++;
			}
		}
		return insertSum;

	}

	@Override
	public boolean isNewsExist(String newsMd5)
	{
		boolean flag = false;
		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor cursor = db.query(NewsApplication.TABLE_NEWS_LIST, new String[] { "news_md5" }, "news_md5 = ?", new String[] { newsMd5 }, null, null, null);
		if (cursor != null && cursor.getCount() > 0)
		{
			flag = true;
		}
		cursor.close();
		db.close();
		return flag;

	}

	public LinkedList<News> getNewsList(String newsCategoryMd5)
	{
		LinkedList<News> data = null;
		SQLiteDatabase db = helper.getWritableDatabase();
		String[] columns = new String[] { "news_md5,news_url,news_title,news_sub_title,news_source,news_src,news_note" };
		String selection = " news_category_md5 = ?";
		String[] selectionArgs = new String[] { newsCategoryMd5 };
		Cursor cursor = db.query(NewsApplication.TABLE_NEWS_LIST, columns, selection, selectionArgs, null, null, null);
		if (null != cursor && cursor.getCount() > 0)
		{
			data = new LinkedList<News>();
			for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext())
			{
				News news = new News();
				news.setNewsCategoryMd5(newsCategoryMd5);
				news.setMd5(cursor.getString(0));
				news.setUrl(cursor.getString(1));
				news.setTitle(cursor.getString(2));
				news.setSubTitle(cursor.getString(3));
				news.setSource(cursor.getString(4));
				news.setSrc(cursor.getString(5));
				news.setNote(cursor.getString(6));
				data.add(news);
			}
		}
		cursor.close();
		db.close();
		return data;

	}

	@Override
	public boolean delete(String newsCategoryMd5)
	{
		boolean flag = false;
		SQLiteDatabase db = helper.getReadableDatabase();
		int effectCount = db.delete(NewsApplication.TABLE_NEWS_LIST, "news_category_md5 = ?", new String[] { newsCategoryMd5 });
		flag = effectCount > 0;
		db.close();
		return flag;
	}

}
