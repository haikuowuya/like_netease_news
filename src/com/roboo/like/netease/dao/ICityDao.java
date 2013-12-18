package com.roboo.like.netease.dao;

import java.util.ArrayList;
import java.util.LinkedList;

import android.database.Cursor;

import com.roboo.like.netease.model.City;

public interface ICityDao
{
	/**
	 * 插入城市数据
	 * @param cities ：要插入的城市数据
	 * @return
	 */
	public int insert(LinkedList<City> cities);
	/**
	 * 获取用户选择的城市
	 * @return ：null 或者 <City>
	 */
	public City getSelectedCity();
	/**
	 * 根据城市码来设置选择的城市
	 * @param cityCode ：城市的城市码
	 */
	public void setSelectedCity(String cityCode);
	/**
	 * 在执行插入时判断当前要添加的城市是否存在
	 * @param cityCode：要插入城市的城市码
	 * @return ：ture 城市已经添加 false 没有添加
	 */
	public boolean checkCityIsAdded(String cityCode);
	/**
	 * 获取城市列表
	 * @return ：null 或者 LinkedList<City>
	 */
	public LinkedList<City> getCityList();
	/**
	 * 获取用户经常选择的城市列表
	 * @return ： null 或者 LinkedList<City>
	 */
	public LinkedList<City>getAlwaysSelectedCityList(); 
	/**
	 * 获取城市列表游标
	 * @return ：null 或者 cursor 
	 */
	public Cursor getCityListCursor();
	/**
	 * TODO 根据城市名称获取城市码
	 * @param cityName: city name 
	 * @return : 010100(bei jing  as default)  or city code 
	 */
	public String getCityCodeByCityName(String cityName);
	
	
	
}
