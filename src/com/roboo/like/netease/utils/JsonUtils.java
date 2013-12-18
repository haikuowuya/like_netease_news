package com.roboo.like.netease.utils;

import java.util.LinkedList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.roboo.like.netease.model.City;
import com.roboo.like.netease.model.Weather;
import com.roboo.like.netease.model.WebSiteItem;

public class JsonUtils
{
	private static final String[] WEEK = { "星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期日" };
	private static int todayWeek = 0;
	/*
	 * public static List<News> handleNewsJson(String json) throws Exception {
	 * List<News> data = null; if (json != null) { data = new
	 * LinkedList<News>(); JSONArray jsonArray = new
	 * JSONObject(json).getJSONArray("news");
	 * System.out.println("jsonArray.length = " + jsonArray.length()); if
	 * (jsonArray != null) { int length = jsonArray.length(); if (length > 0) {
	 * for (int i = 0; i < length; i++) { News news = new News();
	 * news.setNewsId(jsonArray.getJSONObject(i).getString("id"));
	 * news.setNewsTitle(jsonArray.getJSONObject(i).getString("title"));
	 * news.setNewsSource(jsonArray.getJSONObject(i).getString("source"));
	 * news.setNewsDetailUrl
	 * (jsonArray.getJSONObject(i).getString("url")+"&st=1");
	 * news.setNewsPubDate(jsonArray.getJSONObject(i).getString("date"));
	 * JSONArray jImageArray = new
	 * JSONArray(jsonArray.getJSONObject(i).getString( "images"));
	 * System.out.println("arrayimages.lenght() = " + jImageArray.length() +
	 * " jsonArrayimages = " + jsonArray.getJSONObject(i).getString("images"));
	 * if (jImageArray != null && jImageArray.length() > 0) { StringBuffer sb =
	 * new StringBuffer(); for (int j = 0; j < jImageArray.length(); j++) {
	 * sb.append(jImageArray.getString(j)); sb.append(","); } sb =
	 * sb.deleteCharAt(sb.length() - 1); System.out.println("imgags = " +
	 * sb.toString()); news.setNewsImagesUrl(sb.toString()); } data.add(news); }
	 * } } } return data; } public static List<News> handleNewsJson(String json,
	 * NewsCategory category) throws Exception { List<News> data = null; if
	 * (json != null) { data = new LinkedList<News>(); if (category != null &&
	 * category.getNewsCategoryId() != -5) { JSONObject jsonObject = new
	 * JSONObject(json); if (jsonObject.has("news")) { JSONArray jsonArray =
	 * jsonObject.getJSONArray("news"); System.out.println("jsonArray.length = "
	 * + jsonArray.length()); if (jsonArray != null) { int length =
	 * jsonArray.length(); if (length > 0) { for (int i = 0; i < length; i++) {
	 * JSONObject tmpJObject = jsonArray.getJSONObject(i); if
	 * (tmpJObject.has("id") && tmpJObject.has("title") &&
	 * tmpJObject.has("source") && tmpJObject.has("url") &&
	 * tmpJObject.has("date") && tmpJObject.has("images")) { News news = new
	 * News(); news.setNewsId(tmpJObject.getString("id"));
	 * news.setNewsTitle(tmpJObject.getString("title"));
	 * news.setNewsSource(tmpJObject.getString("source"));
	 * news.setNewsDetailUrl(tmpJObject.getString("url")+"&st=1");
	 * news.setNewsPubDate(tmpJObject.getString("date")); JSONArray jImageArray
	 * = new JSONArray(tmpJObject .getString( "images")); if (jImageArray !=
	 * null && jImageArray.length() > 0) { StringBuffer sb = new StringBuffer();
	 * for (int j = 0; j < jImageArray.length(); j++) {
	 * sb.append(jImageArray.getString(j)); sb.append(","); } sb =
	 * sb.deleteCharAt(sb.length() - 1); news.setNewsImagesUrl(sb.toString()); }
	 * if (category != null) { news.setNewsType(category.getNewsCategoryId()); }
	 * data.add(news); } else { System.out.println("锟斤拷取锟斤拷锟斤拷json锟斤拷莞锟绞斤拷锟斤拷锟�); } } } } }
	 * } else if (category != null && category.getNewsCategoryId() == -5) {
	 * System.out.println("json ::::::" + json); Gson gson = new Gson(); Type
	 * type = new TypeToken<LinkedList<News>>() { }.getType(); data =
	 * gson.fromJson(json, type); } } return data; }
	 */
	public static LinkedList<City> handleCityJson(String json) throws JSONException 
	{
		LinkedList<City> data = null;
		if (json != null)
		{
			JSONArray jArray = new JSONObject(json).getJSONArray("province_city_info");
			System.out.println("jArray.length() = " + jArray.length());
			if (jArray != null)
			{
				data = new LinkedList<City>();
				for (int i = 0; i < jArray.length(); i++)
				{
					JSONArray jsonCitysArray =
						jArray.getJSONObject(i).getJSONArray("cities");
					for (int j = 0; j < jsonCitysArray.length(); j++)
					{
						City city = new City();
						String cityName =jsonCitysArray.getJSONObject(j).getString("city_name");
						String cityCode =jsonCitysArray.getJSONObject(j).getString("city_code");
						if (null != cityName)
						{
							city.setCityCode(cityCode);
							city.setCityName(cityName);
							city.setCityPinYinName(PinYinUtils.converterToSpell(cityName));
							data.add(city);
							System.out.println("cityName = " + cityName +
								"  cityCode = " + cityCode);
						}
					}
				}
			}
		}
		return data;
	}
	/*
	 * public static List<NewsCategory> handleNewsCategoryJson(String json)
	 * throws Exception { List<NewsCategory> data = null; if (json != null) {
	 * data = new LinkedList<NewsCategory>(); JSONArray jsonArray = new
	 * JSONObject(json).getJSONArray("catalogs"); if (jsonArray != null) { for
	 * (int i = 0; i < jsonArray.length(); i++) { NewsCategory category = new
	 * NewsCategory(); category.setNewsCategoryId((i + 1));
	 * category.setNewsCategoryName(jsonArray.getJSONObject(i).getString(
	 * "mainCategory")); category.setNewsCategoryNote("锟藉豹锟斤拷锟斤拷");
	 * category.setNewsCategoryAddDate(new Date().getTime() + "");
	 * data.add(category); } } } return data; }
	 */
	public static LinkedList<Weather> handleWeatherJson(String json) throws JSONException
	{
		LinkedList<Weather> weathers = null;
		if (json != null)
		{
			weathers = new LinkedList<Weather>();
			JSONObject jsonObject = new JSONObject(json).getJSONObject("weatherinfo");
			String weatherCityCode = jsonObject.getString("cityid");
			String week = jsonObject.getString("week");
			String weatherCityName = jsonObject.getString("city");
			for (int i = 0; i < WEEK.length; i++)
			{
				if (WEEK[i].equals(week))
				{
					todayWeek = i;
				}
			}
			String date = jsonObject.getString("date_y");
			for (int i = 1; i < 7; i++)
			{
				String temp = jsonObject.getString("temp" + i);// 
				String weatherDescription = jsonObject.getString("weather" + i);
				String wind = jsonObject.getString("wind" + i);
				String windLevel = jsonObject.getString("fl" + i);
				String weatherIcon = jsonObject.getString("img" + (i * 2 - 1)) + ","
					+ jsonObject.getString("img" + (i * 2 - 0));
				Weather weather = new Weather();
				weather.setWeatherCityCode(weatherCityCode);
				weather.setWeek(WEEK[(todayWeek + i - 1) % 7]);
				weather.setDate(WeatherUtils.handleWeatherDay(date, i));
				weather.setTemp(WeatherUtils.handleWeatherTempFromL2H(temp));
				weather.setWeatherDescription(weatherDescription);
				weather.setWeatherIcon(weatherIcon);
				weather.setWind(wind);
				weather.setWindLevel(windLevel);
				weather.setWeatherCityName(weatherCityName);
				weathers.add(weather);
			}
		}
		return weathers;

	}
	/*
	 * public static List<HotWord> handleHotWordJson(String json) throws
	 * Exception { List<HotWord> data = null; JSONObject jsonObject = new
	 * JSONObject(json); if (jsonObject.has("words")) { JSONArray jsonArray =
	 * jsonObject.getJSONArray("words"); if (jsonArray != null &&
	 * jsonArray.length() > 0) { data = new LinkedList<HotWord>(); for (int i =
	 * 0; i < jsonArray.length(); i++) { HotWord hotWord = new HotWord();
	 * hotWord.setHotWordAddDate(new Date().getTime() + "");
	 * hotWord.setHotWordName(jsonArray.getString(i));
	 * hotWord.setHotWordNote("锟饺达拷"); data.add(hotWord); } } } else {
	 * System.out.println("锟斤拷取锟斤拷萁涌诖锟斤拷螅秃锟教拷锟斤拷锟斤拷锟斤拷锟斤拷锟较�); } return data; }
	 */
	public static LinkedList<LinkedList<WebSiteItem>> handleJsonData(String json) throws JSONException
	{
		JSONArray jsonArray = new JSONArray(json);
		if (null != jsonArray && jsonArray.length() > 0)
		{
			LinkedList<LinkedList<WebSiteItem>> data = new LinkedList<LinkedList<WebSiteItem>>();
			System.out.println("jsonArray.length() = " + jsonArray.length());
			for (int i = 0; i < jsonArray.length(); i++)
			{
				JSONArray jsonArray2 = jsonArray.getJSONObject(i).getJSONArray("typechilds");
				if (jsonArray2 != null && jsonArray2.length() > 0)
				{
					System.out.println("jsonArray2.length() = " + jsonArray2.length());
					LinkedList<WebSiteItem> items = new LinkedList<WebSiteItem>();
					for (int ii = 0; ii < jsonArray2.length(); ii++)
					{
						String name = jsonArray2.optJSONObject(ii).optString("name");
						String img, url;
						WebSiteItem item = new WebSiteItem();
						item.setName(name);
						JSONArray siteclientsJsonArray = jsonArray2.optJSONObject(ii).optJSONArray("siteclients");
						if (null != siteclientsJsonArray && siteclientsJsonArray.length() > 0)
						{
							LinkedList<WebSiteItem> tmp1 = new LinkedList<WebSiteItem>();
							for (int tmp11 = 0; tmp11 < siteclientsJsonArray.length(); tmp11++)
							{
								name = siteclientsJsonArray.optJSONObject(tmp11).optString("name");
								img = siteclientsJsonArray.optJSONObject(tmp11).optString("indeximg");
								url = siteclientsJsonArray.optJSONObject(tmp11).optString("url");
								WebSiteItem tmp1Item = new WebSiteItem(img, name, url);
								tmp1.add(tmp1Item);
							}
							item.setSiteList(tmp1);
						}
						JSONArray childJsonArray = jsonArray2.optJSONObject(ii).optJSONArray("child");
						if (null != childJsonArray && childJsonArray.length() > 0)
						{
							LinkedList<WebSiteItem> tmp2 = new LinkedList<WebSiteItem>();
						 
							for (int tmp22 = 0; tmp22 < childJsonArray.length(); tmp22++)
							{
								name = childJsonArray.optJSONObject(tmp22).optString("name");
								img = childJsonArray.optJSONObject(tmp22).optString("indeximg");
								url = childJsonArray.optJSONObject(tmp22).optString("url");
								WebSiteItem tmp2Item  = new WebSiteItem(img, name, url);
								tmp2.add(tmp2Item);
							}
							item.setChildList(tmp2);
						}
						items.add(item);
					}
					data.add(items);
				}
			}
			return data;
		}
		return null;
	}
}
