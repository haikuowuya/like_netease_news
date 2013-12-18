package com.roboo.like.netease.dao.impl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.roboo.like.netease.NewsApplication;
import com.roboo.like.netease.dao.ICityDao;
import com.roboo.like.netease.database.DBHelper;
import com.roboo.like.netease.model.City;
import com.roboo.like.netease.utils.PinYinUtils;

public class CityDaoImpl implements ICityDao
{
	private DBHelper helper;

	public CityDaoImpl(DBHelper helper)
	{
		super();
		this.helper = helper;
	}

	@Override
	public int insert(LinkedList<City> cities)
	{
		int insertCount = 0;
		if (null != cities)
		{
			SQLiteDatabase db = this.helper.getWritableDatabase();
			for (int i = 0; i < cities.size(); i++)
			{
				ContentValues values = new ContentValues();
				City city = cities.get(i);
				if (!checkCityIsAdded(city.getCityCode()))
				{
					values.put("city_code", city.getCityCode());
					values.put("city_name", city.getCityName());
					values.put("city_pinyin_name", PinYinUtils.converterToSpell(city.getCityName()));
					if ("010100".equals(city.getCityCode()))
					{
						values.put("city_is_selected", 1);
					}
					else
					{
						values.put("city_is_selected", 0);
					}
					db.insert(NewsApplication.TABLE_CITY_LIST, null, values);
					insertCount++;
				}
				else
				{

				}
			}
			db.close();
		}
		return insertCount;
	}

	@Override
	public City getSelectedCity()
	{
		City city = null;
		SQLiteDatabase db = helper.getReadableDatabase();
		String sql = "SELECT city_name, city_code  FROM " + NewsApplication.TABLE_CITY_LIST
			+ " WHERE city_is_selected = 1";
		Cursor cursor = db.rawQuery(sql, null);
		if (cursor.getCount() > 0)
		{
			cursor.moveToFirst();
			city = new City();
			city.setCityName(cursor.getString(0));
			city.setCityCode(cursor.getString(1));
		}
		cursor.close();
		db.close();
		return city;
	}

	@Override
	public void setSelectedCity(String cityCode)
	{
		City city = getSelectedCity();
		if (city != null)
		{
			String cityCode1 = city.getCityCode();
			if (cityCode1 != null)
			{
				SQLiteDatabase db = this.helper.getWritableDatabase();
				String sql1 = "UPDATE " + NewsApplication.TABLE_CITY_LIST
					+ " SET city_is_selected  = 0 WHERE city_code = ?";

				String sql2 = "UPDATE "
					+ NewsApplication.TABLE_CITY_LIST
					+ " SET city_is_selected  = 1, city_selected_count = city_selected_count + 1 WHERE city_code = ?";
				db.execSQL(sql1, new String[] { cityCode1 });
				db.execSQL(sql2, new String[] { cityCode });
				db.close();
			}
		}
	}

	@Override
	public boolean checkCityIsAdded(String cityCode)
	{
		boolean result = false;
		SQLiteDatabase db = this.helper.getReadableDatabase();
		Cursor cursor = db.query(NewsApplication.TABLE_CITY_LIST, null, " city_code = ?",
			new String[] { cityCode }, null, null, null);
		if (cursor.getCount() > 0)
		{
			result = true;
		}
		cursor.close();
		return result;
	}

	@Override
	public LinkedList<City> getCityList()
	{
		LinkedList<City> data = null;
		SQLiteDatabase db = helper.getReadableDatabase();
		String sql = "SELECT DISTINCT city_name, city_code,city_pinyin_name FROM  "
			+ NewsApplication.TABLE_CITY_LIST + " ORDER BY city_pinyin_name ASC";
		Cursor cursor = db.rawQuery(sql, null);
		if (null != cursor && cursor.getCount() > 0)
		{
			data = new LinkedList<City>();
			for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext())
			{
				City city = new City();
				city.setCityName(cursor.getString(0));
				city.setCityCode(cursor.getString(1));
				city.setCityPinYinName(cursor.getString(2));
				data.add(city);
			}
			cursor.close();
			db.close();
		}
		return data;
	}

	@Override
	public LinkedList<City> getAlwaysSelectedCityList()
	{
		LinkedList<City> data = null;
		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor cursor = db.query(NewsApplication.TABLE_CITY_LIST, new String[] { "city_name", "city_code" }, "city_selected_count > 0", null, null, null, "city_selected_count DESC");
		if (null != cursor && cursor.getCount() > 0)
		{
			data = new LinkedList<City>();
			for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext())
			{
				City city = new City();
				city.setCityName(cursor.getString(0));
				city.setCityCode(cursor.getString(1));
//				if (data != null && data.size() < 2) //限定两个
				if(data != null && data.size() < 5)
				{
					data.add(city);
				}
			}
			cursor.close();
			db.close();
		}
		return data;
	}

	@Override
	public Cursor getCityListCursor()
	{
		SQLiteDatabase db = helper.getReadableDatabase();
		String sql = "SELECT DISTINCT city_name, city_code, city_pinyin_name FROM  "
			+ NewsApplication.TABLE_CITY_LIST + " ORDER BY city_pinyin_name ASC";
		Cursor cursor = db.rawQuery(sql, null);
		return cursor;

	}

	@Override
	public String getCityCodeByCityName(String cityName)
	{
		SQLiteDatabase db = helper.getReadableDatabase();
		String sql = "SELECT city_code from " + NewsApplication.TABLE_CITY_LIST
			+ " where city_name = ?";
		String[] selectionArgs = new String[] { cityName };
		Cursor cursor = db.rawQuery(sql, selectionArgs);
		cursor.moveToFirst();
		String cityCode = cursor.getString(0);
		db.close();
		cursor.close();
		return cityCode;

	}

}
