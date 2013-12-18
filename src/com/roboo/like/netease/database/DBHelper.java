package com.roboo.like.netease.database;

import java.io.InputStream;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

import com.roboo.like.netease.NewsApplication;

public class DBHelper extends SQLiteOpenHelper
{
	private Context context;
	public DBHelper(Context context)
	{
		super(context, NewsApplication.DB_NAME, null, NewsApplication.DB_VERSION);
		this.context = context;
	}

	public DBHelper(Context context, String name, CursorFactory factory, int version)
	{
		super(context, name, factory, version);
		this.context = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db)
	{
		InputStream in = getClass().getClassLoader().getResourceAsStream("db.json");
		int len = 0;
		byte[] buffer = new byte[1024];
		StringBuffer sb = new StringBuffer();
		try
		{		
			while ((len = in.read(buffer)) != -1)
			{
				sb.append(new String(buffer, 0, len));
			}
			String json = sb.toString();
			System.out.println("json = " + json);
			JSONArray jArray = new JSONObject(json).getJSONArray("create_table_sql");
			for (int i = 0; i < jArray.length(); i++)
			{
				String sql = jArray.getJSONObject(i).getString("table");
				System.out.println("sql = " + sql);
				db.execSQL(sql);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{
		String sql = "DROP TABLE if exists " + NewsApplication.TABLE_NEWS_CATEGORY_LIST;
		String sql2 = "CREATE TABLE news_category_list " +
				" (_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
				" news_category_md5 TEXT, " +
				" news_category_name VARCHAR(100),  " +
				" news_category_note TEXT, " +
				" news_category_order INTEGER, " +
				" news_category_is_customed INTEGER DEFAULT (0) )";
		db.execSQL(sql);
		db.execSQL(sql2);
	}
}
