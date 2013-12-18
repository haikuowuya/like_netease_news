package com.roboo.like.netease.utils;

import java.util.LinkedList;

import org.json.JSONException;

import android.test.AndroidTestCase;

import com.roboo.like.netease.dao.ICityDao;
import com.roboo.like.netease.dao.impl.CityDaoImpl;
import com.roboo.like.netease.database.DBHelper;
import com.roboo.like.netease.model.City;
import com.roboo.like.netease.service.CityService;

public class CityTestCase extends AndroidTestCase
{
	public void testGetCityJson()
	{
		CityService service = new CityService();
		String json = service.getCityJson();
		System.out.println("json = " + json);
	}
	public void testInsertCityData() throws JSONException 
	{
		LinkedList<City> data = JsonUtils.handleCityJson(new CityService().getCityJson());
		if (null != data)
		{
			System.out.println(data);
		}
		DBHelper helper = new DBHelper(mContext);
		ICityDao cityDao = new CityDaoImpl(helper);
		 int insertedCount = cityDao.insert(data);
		 System.out.println("插入" + insertedCount + "条记录");
	}
	public void testGetInsertedCityData()
	{
		ICityDao cityDao = new CityDaoImpl(new DBHelper(mContext));
		 LinkedList<City> data = cityDao.getCityList();
		 if(null != data)
		 {
			 for(City city :data)
			 {
				 System.out.println( city.getCityName() + " :: " + city.getCityCode());
			 }
		 }
	}
}
