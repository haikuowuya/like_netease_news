package com.roboo.like.jsoup;

import java.io.IOException;
import java.util.LinkedList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.roboo.like.netease.model.News;
import com.roboo.like.netease.model.NewsContent;
import com.roboo.like.netease.utils.NewsUtils;

import android.test.AndroidTestCase;

public class JsoupTestCase extends AndroidTestCase
{
	// QQ国内新闻
	private static final String QQ_NATIONAL_NEWS_URL = "http://news.qq.com/china_index.shtml";
	// QQ国际新闻
	private static final String QQ_INTERNATION_NEWS_URL = "http://news.qq.com/world_index.shtml";
	private static final String QQ_NEWS_CONTENT_URL = "http://news.qq.com/a/20130814/002986.htm";
	private static final String IT_HOME_URL = "http://it.ithome.com/category/";
	public void testGetNewsInfoFromHTML() throws IOException
	{
		Document document = Jsoup.connect(QQ_INTERNATION_NEWS_URL).get();
		Elements elements = document.getElementsByClass("Q-tpWrap");

		// System.out.println(elements);
		Element element = elements.get(0);
		System.out.println(element);
		String src = element.getElementsByTag("img").get(0).attr("src");
		String title = element.getElementsByClass("linkto").get(0).text();
		String subTitle = element.getElementsByTag("p").get(0).text();
		String url = element.getElementsByClass("linkto").get(0).attr("href");
		System.out.println(" src = " + src
			+ "\n title = " + title
			+ "\n subTitle = " + subTitle
			+ " \n url = " + url
			);

	}
	public void testGetNewsList() throws IOException
	{
		LinkedList<News> data = NewsUtils.getNewsList(QQ_INTERNATION_NEWS_URL, 0);
		if (null != data)
		{
			for (News news : data)
			{
				System.out.println(news);
			}
		}
	}
	public void testGetNewsData() throws IOException
	{
		String data = NewsUtils.getNewsData(QQ_NEWS_CONTENT_URL);
		System.out.println(data);
	}
	public void testGetNewsContent() throws IOException
	{
		NewsContent data = NewsUtils.getNewsContent(QQ_NEWS_CONTENT_URL);
		if (null != data)
		{
			LinkedList<String> srcList = data.getSrcList();
			LinkedList<String> contentList = data.getContentList();
			if (null != srcList)
			{
				for (String src : srcList)
				{
					System.out.println("src = " + src);
				}
			}
			if (null != contentList)
			{
				for (String str : contentList)
				{
					System.out.println("str = " + str);
				}
			}
		}
	}
	public void testGetITHomeNews() throws IOException
	{
		 LinkedList<News> data = NewsUtils.getITHomeNews(IT_HOME_URL,1,1);
		 for(News news:data)
		 {
			 System.out.println("news = " + news);
		 }
	}
}
