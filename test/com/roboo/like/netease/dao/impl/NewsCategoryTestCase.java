package com.roboo.like.netease.dao.impl;

import java.util.LinkedList;

import com.roboo.like.netease.NewsApplication;
import com.roboo.like.netease.dao.INewsCategoryDao;
import com.roboo.like.netease.database.DBHelper;
import com.roboo.like.netease.model.NewsCategory;
import com.roboo.like.netease.utils.MD5Utils;

import android.test.AndroidTestCase;

public class NewsCategoryTestCase extends AndroidTestCase
{
	public void testInsert()
	{
		int insertSum = 0;
		INewsCategoryDao categoryDao = new NewsCategoryDaoImpl(new DBHelper(mContext));
		LinkedList<NewsCategory> data = generateData();
		for(int i = 0; i < data.size();i++)
		{
			 int tmp  = categoryDao.insert(data.get(i));
			 insertSum = insertSum+tmp;
		}
		System.out.println("insertSum = " + insertSum);
	}
	public void testGetCustomedList()
	{
		INewsCategoryDao categoryDao = new NewsCategoryDaoImpl(new DBHelper(mContext));
		LinkedList<NewsCategory> data =  categoryDao.getCustomedList();
		if(null != data)
		{
			System.out.println("data.size() = " + data.size());
		}
	}

	public LinkedList<NewsCategory> generateData()
	{
		LinkedList<NewsCategory> data = new LinkedList<NewsCategory>();
		NewsCategory category1 = new NewsCategory(MD5Utils.generate(NewsApplication.IT_CONSULTING), "IT咨询", 1, "备注", "1",NewsApplication.IT_CONSULTING);
		NewsCategory category2 = new NewsCategory(MD5Utils.generate(NewsApplication.IT_WIN7), "WIN7之家", 2, "备注", "1",NewsApplication.IT_WIN7);
		NewsCategory category3 = new NewsCategory(MD5Utils.generate(NewsApplication.IT_WIN8), "WIN8之家", 3, "备注", "1",NewsApplication.IT_WIN8);
		NewsCategory category4 = new NewsCategory(MD5Utils.generate(NewsApplication.IT_WIN9), "WIN9之家", 4, "备注", "1",NewsApplication.IT_WIN9);
		NewsCategory category5 = new NewsCategory(MD5Utils.generate(NewsApplication.IT_WINDOWSPHONE), "WINDOWSPHONE之家", 5, "备注", "1",NewsApplication.IT_WINDOWSPHONE);
		NewsCategory category6 = new NewsCategory(MD5Utils.generate(NewsApplication.IT_QQ), "QQ之家", 6, "备注", "1",NewsApplication.IT_QQ);
		NewsCategory category7 = new NewsCategory(MD5Utils.generate(NewsApplication.IT_SOFTWARE), "软件之家",7, "备注", "0",NewsApplication.IT_SOFTWARE);
		NewsCategory category8 = new NewsCategory(MD5Utils.generate(NewsApplication.IT_GAME), "游戏之家", 8, "备注", "0",NewsApplication.IT_GAME);
		NewsCategory category9 = new NewsCategory(MD5Utils.generate(NewsApplication.IT_DIGI), "数码之家", 9, "备注", "0",NewsApplication.IT_DIGI);
		NewsCategory category10 = new NewsCategory(MD5Utils.generate(NewsApplication.IT_ANDROID), "Android之家", 10, "备注", "1",NewsApplication.IT_ANDROID);
		NewsCategory category11 = new NewsCategory(MD5Utils.generate(NewsApplication.IT_CHROME), "Chrome之家", 11, "备注", "1",NewsApplication.IT_CHROME);
		NewsCategory category12 = new NewsCategory(MD5Utils.generate(NewsApplication.IT_THEME), "主题之家", 12, "备注", "0",NewsApplication.IT_THEME);
		NewsCategory category13 = new NewsCategory(MD5Utils.generate(NewsApplication.IT_IE), "IE之家", 13, "备注", "0",NewsApplication.IT_IE);
		NewsCategory category14 = new NewsCategory(MD5Utils.generate(NewsApplication.IT_IPAD), "IPAD之家", 14, "备注", "1",NewsApplication.IT_IPAD);
		NewsCategory category15 = new NewsCategory(MD5Utils.generate(NewsApplication.IT_IPHONE), "IPhone之家", 15, "备注", "1",NewsApplication.IT_IPHONE);
		NewsCategory category16 = new NewsCategory(MD5Utils.generate(NewsApplication.IT_OFFICE), "Office之家", 16, "备注", "0",NewsApplication.IT_OFFICE);
		NewsCategory category17 = new NewsCategory(MD5Utils.generate(NewsApplication.IT_IT), "IT之外", 17, "备注", "1",NewsApplication.IT_IT);
		NewsCategory category18 = new NewsCategory(MD5Utils.generate(NewsApplication.IT_VISTA), "Vista之家", 18, "备注", "0",NewsApplication.IT_VISTA);
		NewsCategory category19 = new NewsCategory(MD5Utils.generate(NewsApplication.IT_WALLPAPER), "壁纸之家", 19, "备注", "1",NewsApplication.IT_WALLPAPER);
		data.add(category1);
		data.add(category2);
		data.add(category3);
		data.add(category4);
		data.add(category5);
		data.add(category6);
		data.add(category7);
		data.add(category8);
		data.add(category9);
		data.add(category10);
		data.add(category11);
		data.add(category12);
		data.add(category13);
		data.add(category14);
		data.add(category15);
		data.add(category16);
		data.add(category17);
		data.add(category18);
		data.add(category19);

		return data;
	}
}
