package com.roboo.like.netease;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.widget.Toast;

import com.roboo.like.netease.utils.FileCopyUtils;

public class NewsApplication extends Application
{
	/**苏州天气url*/
	public static final String SPECIFIC_WEATHER_URL ="http://m.weather.com.cn/data/101190401.html";
	public static final String IT_HOME_URL = "http://it.ithome.com/category/";
	public static final int SIMULATIVE_REFRESH_TIME = 5000;// millisecond
	// TODO QQ国内新闻
	public static final String QQ_NATIONAL_NEWS_URL = "http://news.qq.com/china_index.shtml";
	// TODO QQ国际新闻
	public static final String QQ_INTERNATION_NEWS_URL = "http://news.qq.com/world_index.shtml";
	public static final String UPDATE_URL = "http://hao.roboo.com/RobooNews.apk";

	public static List<Activity> mActivities = new LinkedList<Activity>();
	public static final long REFRESH_INTERVAL_1_MINUTE = 60 * 1000;
	public static final long REFRESH_INTERVAL_10_MINUTE = 10 * 60 * 1000;
	public static final long REFRESH_INTERVAL_1_HOUR = 60 * 60 * 1000;
	public static final long REFRESH_INTERVAL_3_HOUR = 3 * 60 * 60 * 1000;
	public static final long REFRESH_INTERVAL_12_HOUR = 12 * 60 * 60 * 1000;
	public static final long REFRESH_INTERVAL_1_DAY = 24 * 60 * 60 * 1000;

	/**
	 * 新浪APP_KEY
	 */
	// public static final String SINA_APP_KEY = "240282150";
	public static final String SINA_APP_KEY = "3694821204";
	/**
	 * 新浪的回调地址
	 */
	// public static final String SINA_REDIRECT_URL =
	// "http://www.libo123456.cn";
	public static final String SINA_REDIRECT_URL = "http://n.roboo.com";
	/**
	 * 与APP_KEY对应的APP_SECRET
	 */
	public static final String SINA_APP_SECRET = "2a0349d90e7302ff224077562196f389";
	// public static final String SINA_APP_SECRET =
	// "f558bf3401900d9d6636167b9395e623";

	/**
	 * 腾讯的APP_KEY
	 */
	public static final String QQ_APP_KEY = "801361297";
	public static final String QQ_REDIRECT_URL = "http://hao.roboo.com/news/index.htm";
	/**
	 * 与APP_KEY对应的APP_SECRET
	 */
	public static final String QQ_APP_SECRET = "713e318dea74a1554b08d35c4ad85fc3";
	/**
	 * 百度地图API的key是否有效
	 */
	public boolean is_baidu_api_key_validated = true;
	/**
	 * 里面的图标内容和新闻类别的id对应，在WelcomeActivity中添加对应内容
	 */
	public static Map<String, Integer> mNewsCategoryIcon = new HashMap<String, Integer>();

	public static int[] mWeatherImages = new int[32];
	public static final String WEATHER_URL = "http://m.weather.com.cn/data/101";
	public static final String DB_NAME = "news.db";
	/**
	 * 存放数据库的地址
	 */
	public static final String DB_PATH = "/data/data/com.roboo.like.netease/databases/";
	public static final String BAIDU_APP_KEY = "34F26739805259EE7B539389823F9F601B3D2635";
	public static final int DB_VERSION = 10;
	public static final String TABLE_NEWS_LIST = "news_list";
	public static final String TABLE_CITY_LIST = "city_list";
	public static final String TABLE_OAUTH_LIST = "oauth_list";
	public static final String TABLE_HOT_WORD_LIST = "hot_word_list";
	public static final String TABLE_NEWS_CATEGORY_LIST = "news_category_list";
	public static final String TABLE_NEWS_COLLECT_LIST = "news_collect_list";
	public static final int PER_LOADING_PAGESIZE = 10;
	/***
	 * 焦点新闻baseUrl
	 */
	public static final String FOCUS_NEWS = "http://n.roboo.com/proxy/news/focus_news_4_app.jsp?pagesize=" + PER_LOADING_PAGESIZE;
	/***
	 * 本地新闻baseUrl
	 */
	// &pagecount =1&city=;
	public static final String NATIVE_NEWS = "http://n.roboo.com/proxy/news/local_news_4_app.jsp?pagesize=" + PER_LOADING_PAGESIZE;
	/***
	 * 获取新闻类别的url
	 */
	public static final String NEWS_CATEGORY = "http://n.roboo.com/proxy/news/categorys_news_4_app.jsp";
	/***
	 * 热词的url
	 */
	public static final String HOT_WORD = "http://n.roboo.com/proxy/news/hotsearch_news_4_app.jsp?pagesize=" + PER_LOADING_PAGESIZE;
	public static final String HOT_SEARCH_LIST = "http://n.roboo.com/proxy/news/hotsearch_news_4_app.jsp?pagesize=" + PER_LOADING_PAGESIZE;
	/**
	 * 首页新闻列表url
	 */
	public static final String NEWS_LIST = "http://n.roboo.com/proxy/news/channels_picnews_4_app.jsp";
	/**
	 * 分类新闻
	 */
	public static final String NEWS_CATEGORY_LIST = "http://n.roboo.com/proxy/news/category_news_4_app.jsp?pagesize=" + PER_LOADING_PAGESIZE;
	/**
	 * 新闻图说 改为
	 * http://n.roboo.com/proxy/news/pic_news_recommend_4_app.jsp?pagesize
	 * =8&pagecount=3
	 */
	public static final String NEWS_DRAWING = "http://n.roboo.com/proxy/news/pic_news_recommend_4_app.jsp?pagesize=" + PER_LOADING_PAGESIZE;
	/**
	 * 搜索新闻
	 */
	public static final String NEWS_SEARCH = "http://n.roboo.com/proxy/news/query_news_4_app.jsp?pagesize=" + PER_LOADING_PAGESIZE;
	/**
	 * 搜索图说
	 */
	public static final String NEWS_PIC_SEARCH = "http://n.roboo.com/proxy/news/pic_news_4_app.jsp??pagesize=" + PER_LOADING_PAGESIZE;
	/**
	 * 新闻轮转
	 */
	public static final String NEWS_ROTATE = "http://n.roboo.com/proxy/news/rotate_news_4_app.jsp";
	public static final String PUBLISH_COMMENT = "http://comments.roboo.com/commit_4_app.jsp?id=";

	public static final String QQ_OAUTH_URL = "https://open.t.qq.com/cgi-bin/oauth2/authorize?client_id=" + QQ_APP_KEY + "&response_type=token&"
			+ "redirect_uri=" + QQ_REDIRECT_URL;
	public static final String SINA_OAUTH_URL = "https://open.weibo.cn/oauth2/authorize?client_id=" + SINA_APP_KEY + "&response_type=token&"
			+ "redirect_uri=" + SINA_REDIRECT_URL + "&display=mobile";

	/**
	 * IT咨询
	 */
	public static final String IT_CONSULTING = "http://it.ithome.com/category/1_";
	/**
	 * WIN8之家
	 */
	public static final String IT_WIN8 = "http://it.ithome.com/category/2_";
	/**
	 * WIN7之家
	 */
	public static final String IT_WIN7 = "http://it.ithome.com/category/3_";

	/**
	 * VISTA之家
	 */
	public static final String IT_VISTA = "http://it.ithome.com/category/4_";
	/**
	 * IPHONE之家
	 */
	public static final String IT_IPHONE = "http://it.ithome.com/category/5_";
	/**
	 * IPAD之家
	 */
	public static final String IT_IPAD = "http://it.ithome.com/category/6_";

	/**
	 * IE之家
	 */
	public static final String IT_IE = "http://it.ithome.com/category/7_";
	/**
	 * OFFICE之家
	 */
	public static final String IT_OFFICE = "http://it.ithome.com/category/8_";
	/**
	 * CHROME之家
	 */
	public static final String IT_CHROME = "http://it.ithome.com/category/9_";
	/**
	 * ANDROID之家
	 */
	public static final String IT_ANDROID = "http://it.ithome.com/category/10_";
	/**
	 * SOFTWARE之家
	 */
	public static final String IT_SOFTWARE = "http://it.ithome.com/category/11_";
	/**
	 * WALLPAPER之家
	 */
	public static final String IT_WALLPAPER = "http://it.ithome.com/category/12_";

	/**
	 * THEME之家
	 */
	public static final String IT_THEME = "http://it.ithome.com/category/13_";
	/**
	 * GAME之家
	 */
	public static final String IT_GAME = "http://it.ithome.com/category/14_";

	/**
	 * DIGI之家
	 */
	public static final String IT_DIGI = "http://it.ithome.com/category/15_";
	/**
	 * QQ之家
	 */
	public static final String IT_QQ = "http://it.ithome.com/category/16_";

	/**
	 * IT之外
	 */
	public static final String IT_IT = "http://it.ithome.com/category/17_";

	/**
	 * WINDOWSPHONE之外
	 */
	public static final String IT_WINDOWSPHONE = "http://it.ithome.com/category/18_";
	/***
	 * WIN9之家
	 */
	public static final String IT_WIN9 = "http://it.ithome.com/category/19_";
	private static NewsApplication mInstance;

	public static NewsApplication getInstance()
	{
		return mInstance;
	}

	private ServiceConnection conn = new ServiceConnection()
	{
		@Override
		public void onServiceDisconnected(ComponentName name)
		{
			Toast.makeText(getApplicationContext(), "解除网络服务", Toast.LENGTH_SHORT).show();
			System.out.println("NewsApplication onServiceDisconnected  解除网络服务");

		}

		@Override
		public void onServiceConnected(ComponentName name, IBinder service)
		{
			System.out.println("NewsApplication onServiceConnected  开始网络服务");

		}
	};

	/**
	 * 退出应用程序
	 */
	@SuppressLint("NewApi")
	public void exitClient()
	{

		if (mActivities.size() > 0)
		{
			for (int i = 0; i < mActivities.size(); i++)
			{
				Activity activity = mActivities.get(i);
				if (null != activity)
				{
					activity.finish();
				}
			}
		}
	}

	@Override
	public void onCreate()
	{
		super.onCreate();
		mInstance = this;
		File dbFile = getDatabasePath(DB_NAME);
		if (dbFile.exists())
		{
			System.out.println("数据库文件存在");
		}
		else
		{
			System.out.println("数据库文件不存在");
//			DBHelper helper = new DBHelper(this);
//			helper.getReadableDatabase();
			InputStream in = getResources().openRawResource(R.raw.news);
			if (FileCopyUtils.copyDB(in))
			{
				System.out.println("复制成功");
			}
			else
			{
				System.out.println("复制失败");
			}
		}

	}
}
