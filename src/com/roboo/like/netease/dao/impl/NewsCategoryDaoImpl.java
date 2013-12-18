package com.roboo.like.netease.dao.impl;

import java.util.LinkedList;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.roboo.like.netease.NewsApplication;
import com.roboo.like.netease.dao.INewsCategoryDao;
import com.roboo.like.netease.database.DBHelper;
import com.roboo.like.netease.model.NewsCategory;

public class NewsCategoryDaoImpl implements INewsCategoryDao
{
	private DBHelper helper;
	public NewsCategoryDaoImpl(DBHelper helper)
	{
		super();
		this.helper = helper;
	}

	@Override
	public int insert(NewsCategory data)
	{
		int insertSum = 0;
		if (null != data)
		{
			if (!isNewsCategoryExist(data.getNewsCategoryMD5()))
			{
				SQLiteDatabase db = helper.getReadableDatabase();
				ContentValues values = new ContentValues();
				values.put("news_category_md5", data.getNewsCategoryMD5());
				values.put("news_category_note", data.getNewsCategoryNote());
				values.put("news_category_name", data.getNewsCategoryName());
				values.put("news_category_is_customed", data.getNewsCateogryIsCustomed());
				values.put("news_category_order", data.getNewsCategoryOrder());
				values.put("news_category_url",data.getNewsCategoryUrl());
				db.insert(NewsApplication.TABLE_NEWS_CATEGORY_LIST, null, values);
				db.close();
				insertSum++;
			}
		}
		return insertSum;

	}

	@Override
	public LinkedList<NewsCategory> getCustomedList()
	{
		LinkedList<NewsCategory> data = null;
		SQLiteDatabase db = helper.getReadableDatabase();
		String[] columns = new String[] { "news_category_md5,news_category_name,news_category_order,news_category_url" };
		String selection = " news_category_is_customed = ?";
		String[] selectionArgs = new String[] { "1" };
		Cursor cursor = db.query(NewsApplication.TABLE_NEWS_CATEGORY_LIST, columns, selection, selectionArgs, null, null, "news_category_order ASC");
		if (null != cursor && cursor.getCount() > 0)
		{
			data = new LinkedList<NewsCategory>();
			for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext())
			{
				NewsCategory category = new NewsCategory();
				category.setNewsCategoryMD5(cursor.getString(0));
				category.setNewsCategoryName(cursor.getString(1));
				category.setNewsCategoryOrder(cursor.getInt(2));
				category.setNewsCategoryUrl(cursor.getString(3));
				
				data.add(category);
			}
			cursor.close();
			db.close();
		}
		return data;

	}

	@Override
	public boolean isNewsCategoryExist(String newsCategoryMd5)
	{
		boolean flag = false;
		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor cursor = db.query(NewsApplication.TABLE_NEWS_CATEGORY_LIST, new String[] { "news_category_md5" }, "news_category_md5 = ?", new String[] { newsCategoryMd5 }, null, null, null);
		if (cursor != null && cursor.getCount() > 0)
		{
			flag = true;
		}
		cursor.close();
		db.close();
		return flag;

	}

	@Override
	public LinkedList<NewsCategory> getNewsCategoryList()
	{
		LinkedList<NewsCategory> data = null;
		SQLiteDatabase db = helper.getReadableDatabase();
		String[] columns = new String[] { "news_category_md5,news_category_name,news_category_order,news_category_url" };
		Cursor cursor = db.query(NewsApplication.TABLE_NEWS_CATEGORY_LIST, columns, null, null, null, null, "news_category_order ASC");
		if (null != cursor && cursor.getCount() > 0)
		{
			data = new LinkedList<NewsCategory>();
			for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext())
			{
				NewsCategory category = new NewsCategory();
				category.setNewsCategoryMD5(cursor.getString(0));
				category.setNewsCategoryName(cursor.getString(1));
				category.setNewsCategoryOrder(cursor.getInt(2));
				category.setNewsCategoryUrl(cursor.getString(3));
				data.add(category);
			}
			cursor.close();
			db.close();
		}
		return data;

	}

	@Override
	public boolean updateNewsCategoryState(String newsCategoryMd5, boolean isCustomed)
	{
		boolean flag = false;
		SQLiteDatabase db = helper.getReadableDatabase();
		ContentValues values = new ContentValues();
		values.put("news_category_is_customed", isCustomed ? "1" : "0");
		int tmp = db.update(NewsApplication.TABLE_NEWS_CATEGORY_LIST, values, "news_category_md5= ?", new String[] { newsCategoryMd5 });
		if (tmp > 0)
		{
			flag = true;
		}
		db.close();
		return flag;
	}

	@Override
	public LinkedList<NewsCategory> getUnCustomedList()
	{
		LinkedList<NewsCategory> allData = getNewsCategoryList();
		LinkedList<NewsCategory> customedData = getCustomedList();
		if(null != customedData)
		{
			allData.removeAll(customedData);
		}
		return allData;
		
	}

	@Override
	public boolean updateNewsCategoryOrder(String newsCategoryMd5, int order)
	{
		boolean flag = false;
		SQLiteDatabase db = helper.getReadableDatabase();
		ContentValues values = new ContentValues();
		values.put("news_category_order", order+"");
		int tmp = db.update(NewsApplication.TABLE_NEWS_CATEGORY_LIST, values, "news_category_md5= ?", new String[] { newsCategoryMd5 });
		if (tmp > 0)
		{
			flag = true;
		}
		db.close();
		return flag;
		
	}

}
