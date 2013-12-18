package com.roboo.like.netease.adapter;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.roboo.like.netease.R;
import com.roboo.like.netease.pinterest.Images;

/**
 * GridView的适配器，负责异步从网络上下载图片展示在照片墙上。
 * 
 * @author guolin
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
public class GridViewCacheAdapter extends ArrayAdapter<String> implements OnScrollListener
{

	public final static String[] IMGS = new String[] {
		"https://lh6.googleusercontent.com/-55osAWw3x0Q/URquUtcFr5I/AAAAAAAAAbs/rWlj1RUKrYI/s160-c/A%252520Photographer.jpg",
		"https://lh4.googleusercontent.com/--dq8niRp7W4/URquVgmXvgI/AAAAAAAAAbs/-gnuLQfNnBA/s160-c/A%252520Song%252520of%252520Ice%252520and%252520Fire.jpg",
		"https://lh5.googleusercontent.com/-7qZeDtRKFKc/URquWZT1gOI/AAAAAAAAAbs/hqWgteyNXsg/s160-c/Another%252520Rockaway%252520Sunset.jpg",
		"https://lh3.googleusercontent.com/--L0Km39l5J8/URquXHGcdNI/AAAAAAAAAbs/3ZrSJNrSomQ/s160-c/Antelope%252520Butte.jpg",
		"https://lh6.googleusercontent.com/-8HO-4vIFnlw/URquZnsFgtI/AAAAAAAAAbs/WT8jViTF7vw/s160-c/Antelope%252520Hallway.jpg",
		"https://lh4.googleusercontent.com/-WIuWgVcU3Qw/URqubRVcj4I/AAAAAAAAAbs/YvbwgGjwdIQ/s160-c/Antelope%252520Walls.jpg",
		"https://lh6.googleusercontent.com/-UBmLbPELvoQ/URqucCdv0kI/AAAAAAAAAbs/IdNhr2VQoQs/s160-c/Apre%2525CC%252580s%252520la%252520Pluie.jpg",
		"https://lh3.googleusercontent.com/-s-AFpvgSeew/URquc6dF-JI/AAAAAAAAAbs/Mt3xNGRUd68/s160-c/Backlit%252520Cloud.jpg",
		"https://lh5.googleusercontent.com/-bvmif9a9YOQ/URquea3heHI/AAAAAAAAAbs/rcr6wyeQtAo/s160-c/Bee%252520and%252520Flower.jpg",
		"https://lh5.googleusercontent.com/-n7mdm7I7FGs/URqueT_BT-I/AAAAAAAAAbs/9MYmXlmpSAo/s160-c/Bonzai%252520Rock%252520Sunset.jpg",
		"https://lh6.googleusercontent.com/-4CN4X4t0M1k/URqufPozWzI/AAAAAAAAAbs/8wK41lg1KPs/s160-c/Caterpillar.jpg",
		"https://lh3.googleusercontent.com/-rrFnVC8xQEg/URqufdrLBaI/AAAAAAAAAbs/s69WYy_fl1E/s160-c/Chess.jpg",
		"https://lh5.googleusercontent.com/-WVpRptWH8Yw/URqugh-QmDI/AAAAAAAAAbs/E-MgBgtlUWU/s160-c/Chihuly.jpg",
		"https://lh5.googleusercontent.com/-0BDXkYmckbo/URquhKFW84I/AAAAAAAAAbs/ogQtHCTk2JQ/s160-c/Closed%252520Door.jpg",
		"https://lh3.googleusercontent.com/-PyggXXZRykM/URquh-kVvoI/AAAAAAAAAbs/hFtDwhtrHHQ/s160-c/Colorado%252520River%252520Sunset.jpg",
		"https://lh3.googleusercontent.com/-ZAs4dNZtALc/URquikvOCWI/AAAAAAAAAbs/DXz4h3dll1Y/s160-c/Colors%252520of%252520Autumn.jpg",
		"https://lh4.googleusercontent.com/-GztnWEIiMz8/URqukVCU7bI/AAAAAAAAAbs/jo2Hjv6MZ6M/s160-c/Countryside.jpg",
		"https://lh4.googleusercontent.com/-bEg9EZ9QoiM/URquklz3FGI/AAAAAAAAAbs/UUuv8Ac2BaE/s160-c/Death%252520Valley%252520-%252520Dunes.jpg",
		"https://lh6.googleusercontent.com/-ijQJ8W68tEE/URqulGkvFEI/AAAAAAAAAbs/zPXvIwi_rFw/s160-c/Delicate%252520Arch.jpg",
		"https://lh5.googleusercontent.com/-Oh8mMy2ieng/URqullDwehI/AAAAAAAAAbs/TbdeEfsaIZY/s160-c/Despair.jpg",
		"https://lh5.googleusercontent.com/-gl0y4UiAOlk/URqumC_KjBI/AAAAAAAAAbs/PM1eT7dn4oo/s160-c/Eagle%252520Fall%252520Sunrise.jpg",
		"https://lh3.googleusercontent.com/-hYYHd2_vXPQ/URqumtJa9eI/AAAAAAAAAbs/wAalXVkbSh0/s160-c/Electric%252520Storm.jpg",
		"https://lh5.googleusercontent.com/-PyY_yiyjPTo/URqunUOhHFI/AAAAAAAAAbs/azZoULNuJXc/s160-c/False%252520Kiva.jpg",
		"https://lh6.googleusercontent.com/-PYvLVdvXywk/URqunwd8hfI/AAAAAAAAAbs/qiMwgkFvf6I/s160-c/Fitzgerald%252520Streaks.jpg",
		"https://lh4.googleusercontent.com/-KIR_UobIIqY/URquoCZ9SlI/AAAAAAAAAbs/Y4d4q8sXu4c/s160-c/Foggy%252520Sunset.jpg",
		"https://lh6.googleusercontent.com/-9lzOk_OWZH0/URquoo4xYoI/AAAAAAAAAbs/AwgzHtNVCwU/s160-c/Frantic.jpg",
		"https://lh3.googleusercontent.com/-0X3JNaKaz48/URqupH78wpI/AAAAAAAAAbs/lHXxu_zbH8s/s160-c/Golden%252520Gate%252520Afternoon.jpg",
		"https://lh6.googleusercontent.com/-95sb5ag7ABc/URqupl95RDI/AAAAAAAAAbs/g73R20iVTRA/s160-c/Golden%252520Gate%252520Fog.jpg",
		"https://lh3.googleusercontent.com/-JB9v6rtgHhk/URqup21F-zI/AAAAAAAAAbs/64Fb8qMZWXk/s160-c/Golden%252520Grass.jpg",
		"https://lh4.googleusercontent.com/-EIBGfnuLtII/URquqVHwaRI/AAAAAAAAAbs/FA4McV2u8VE/s160-c/Grand%252520Teton.jpg",
		"https://lh4.googleusercontent.com/-WoMxZvmN9nY/URquq1v2AoI/AAAAAAAAAbs/grj5uMhL6NA/s160-c/Grass%252520Closeup.jpg",
		"https://lh3.googleusercontent.com/-6hZiEHXx64Q/URqurxvNdqI/AAAAAAAAAbs/kWMXM3o5OVI/s160-c/Green%252520Grass.jpg",
		"https://lh5.googleusercontent.com/-6LVb9OXtQ60/URquteBFuKI/AAAAAAAAAbs/4F4kRgecwFs/s160-c/Hanging%252520Leaf.jpg",
		"https://lh4.googleusercontent.com/-zAvf__52ONk/URqutT_IuxI/AAAAAAAAAbs/D_bcuc0thoU/s160-c/Highway%2525201.jpg",
		"https://lh6.googleusercontent.com/-H4SrUg615rA/URquuL27fXI/AAAAAAAAAbs/4aEqJfiMsOU/s160-c/Horseshoe%252520Bend%252520Sunset.jpg",
		"https://lh4.googleusercontent.com/-JhFi4fb_Pqw/URquuX-QXbI/AAAAAAAAAbs/IXpYUxuweYM/s160-c/Horseshoe%252520Bend.jpg",
		"https://lh5.googleusercontent.com/-UGgssvFRJ7g/URquueyJzGI/AAAAAAAAAbs/yYIBlLT0toM/s160-c/Into%252520the%252520Blue.jpg",
		"https://lh3.googleusercontent.com/-CH7KoupI7uI/URquu0FF__I/AAAAAAAAAbs/R7GDmI7v_G0/s160-c/Jelly%252520Fish%2525202.jpg",
		"https://lh4.googleusercontent.com/-pwuuw6yhg8U/URquvPxR3FI/AAAAAAAAAbs/VNGk6f-tsGE/s160-c/Jelly%252520Fish%2525203.jpg",
		"https://lh5.googleusercontent.com/-GoUQVw1fnFw/URquv6xbC0I/AAAAAAAAAbs/zEUVTQQ43Zc/s160-c/Kauai.jpg",
		"https://lh6.googleusercontent.com/-8QdYYQEpYjw/URquwvdh88I/AAAAAAAAAbs/cktDy-ysfHo/s160-c/Kyoto%252520Sunset.jpg",
		"https://lh4.googleusercontent.com/-vPeekyDjOE0/URquwzJ28qI/AAAAAAAAAbs/qxcyXULsZrg/s160-c/Lake%252520Tahoe%252520Colors.jpg",
		"https://lh4.googleusercontent.com/-xBPxWpD4yxU/URquxWHk8AI/AAAAAAAAAbs/ARDPeDYPiMY/s160-c/Lava%252520from%252520the%252520Sky.jpg",
		"https://lh3.googleusercontent.com/-897VXrJB6RE/URquxxxd-5I/AAAAAAAAAbs/j-Cz4T4YvIw/s160-c/Leica%25252050mm%252520Summilux.jpg",
		"https://lh5.googleusercontent.com/-qSJ4D4iXzGo/URquyDWiJ1I/AAAAAAAAAbs/k2pBXeWehOA/s160-c/Leica%25252050mm%252520Summilux.jpg",
		"https://lh6.googleusercontent.com/-dwlPg83vzLg/URquylTVuFI/AAAAAAAAAbs/G6SyQ8b4YsI/s160-c/Leica%252520M8%252520%252528Front%252529.jpg",
		"https://lh3.googleusercontent.com/-R3_EYAyJvfk/URquzQBv8eI/AAAAAAAAAbs/b9xhpUM3pEI/s160-c/Light%252520to%252520Sand.jpg",
		"https://lh3.googleusercontent.com/-fHY5h67QPi0/URqu0Cp4J1I/AAAAAAAAAbs/0lG6m94Z6vM/s160-c/Little%252520Bit%252520of%252520Paradise.jpg",
		"https://lh5.googleusercontent.com/-TzF_LwrCnRM/URqu0RddPOI/AAAAAAAAAbs/gaj2dLiuX0s/s160-c/Lone%252520Pine%252520Sunset.jpg",
		"https://lh3.googleusercontent.com/-4HdpJ4_DXU4/URqu046dJ9I/AAAAAAAAAbs/eBOodtk2_uk/s160-c/Lonely%252520Rock.jpg",
		"https://lh6.googleusercontent.com/-erbF--z-W4s/URqu1ajSLkI/AAAAAAAAAbs/xjDCDO1INzM/s160-c/Longue%252520Vue.jpg",
		"https://lh6.googleusercontent.com/-0CXJRdJaqvc/URqu1opNZNI/AAAAAAAAAbs/PFB2oPUU7Lk/s160-c/Look%252520Me%252520in%252520the%252520Eye.jpg",
		"https://lh3.googleusercontent.com/-D_5lNxnDN6g/URqu2Tk7HVI/AAAAAAAAAbs/p0ddca9W__Y/s160-c/Lost%252520in%252520a%252520Field.jpg",
		"https://lh6.googleusercontent.com/-flsqwMrIk2Q/URqu24PcmjI/AAAAAAAAAbs/5ocIH85XofM/s160-c/Marshall%252520Beach%252520Sunset.jpg",
		"https://lh4.googleusercontent.com/-Y4lgryEVTmU/URqu28kG3gI/AAAAAAAAAbs/OjXpekqtbJ4/s160-c/Mono%252520Lake%252520Blue.jpg",
		"https://lh4.googleusercontent.com/-AaHAJPmcGYA/URqu3PIldHI/AAAAAAAAAbs/lcTqk1SIcRs/s160-c/Monument%252520Valley%252520Overlook.jpg",
		"https://lh4.googleusercontent.com/-vKxfdQ83dQA/URqu31Yq_BI/AAAAAAAAAbs/OUoGk_2AyfM/s160-c/Moving%252520Rock.jpg",
		"https://lh5.googleusercontent.com/-CG62QiPpWXg/URqu4ia4vRI/AAAAAAAAAbs/0YOdqLAlcAc/s160-c/Napali%252520Coast.jpg",
		"https://lh6.googleusercontent.com/-wdGrP5PMmJQ/URqu5PZvn7I/AAAAAAAAAbs/m0abEcdPXe4/s160-c/One%252520Wheel.jpg",
		"https://lh6.googleusercontent.com/-6WS5DoCGuOA/URqu5qx1UgI/AAAAAAAAAbs/giMw2ixPvrY/s160-c/Open%252520Sky.jpg",
		"https://lh6.googleusercontent.com/-u8EHKj8G8GQ/URqu55sM6yI/AAAAAAAAAbs/lIXX_GlTdmI/s160-c/Orange%252520Sunset.jpg",
		"https://lh6.googleusercontent.com/-74Z5qj4bTDE/URqu6LSrJrI/AAAAAAAAAbs/XzmVkw90szQ/s160-c/Orchid.jpg",
		"https://lh6.googleusercontent.com/-lEQE4h6TePE/URqu6t_lSkI/AAAAAAAAAbs/zvGYKOea_qY/s160-c/Over%252520there.jpg",
		"https://lh5.googleusercontent.com/-cauH-53JH2M/URqu66v_USI/AAAAAAAAAbs/EucwwqclfKQ/s160-c/Plumes.jpg",
		"https://lh3.googleusercontent.com/-eDLT2jHDoy4/URqu7axzkAI/AAAAAAAAAbs/iVZE-xJ7lZs/s160-c/Rainbokeh.jpg",
		"https://lh5.googleusercontent.com/-j1NLqEFIyco/URqu8L1CGcI/AAAAAAAAAbs/aqZkgX66zlI/s160-c/Rainbow.jpg",
		"https://lh5.googleusercontent.com/-DRnqmK0t4VU/URqu8XYN9yI/AAAAAAAAAbs/LgvF_592WLU/s160-c/Rice%252520Fields.jpg",
		"https://lh3.googleusercontent.com/-hwh1v3EOGcQ/URqu8qOaKwI/AAAAAAAAAbs/IljRJRnbJGw/s160-c/Rockaway%252520Fire%252520Sky.jpg",
		"https://lh5.googleusercontent.com/-wjV6FQk7tlk/URqu9jCQ8sI/AAAAAAAAAbs/RyYUpdo-c9o/s160-c/Rockaway%252520Flow.jpg",
		"https://lh6.googleusercontent.com/-6cAXNfo7D20/URqu-BdzgPI/AAAAAAAAAbs/OmsYllzJqwo/s160-c/Rockaway%252520Sunset%252520Sky.jpg",
		"https://lh3.googleusercontent.com/-sl8fpGPS-RE/URqu_BOkfgI/AAAAAAAAAbs/Dg2Fv-JxOeg/s160-c/Russian%252520Ridge%252520Sunset.jpg",
		"https://lh6.googleusercontent.com/-gVtY36mMBIg/URqu_q91lkI/AAAAAAAAAbs/3CiFMBcy5MA/s160-c/Rust%252520Knot.jpg",
		"https://lh6.googleusercontent.com/-GHeImuHqJBE/URqu_FKfVLI/AAAAAAAAAbs/axuEJeqam7Q/s160-c/Sailing%252520Stones.jpg",
		"https://lh3.googleusercontent.com/-hBbYZjTOwGc/URqu_ycpIrI/AAAAAAAAAbs/nAdJUXnGJYE/s160-c/Seahorse.jpg",
		"https://lh3.googleusercontent.com/-Iwi6-i6IexY/URqvAYZHsVI/AAAAAAAAAbs/5ETWl4qXsFE/s160-c/Shinjuku%252520Street.jpg",
		"https://lh6.googleusercontent.com/-amhnySTM_MY/URqvAlb5KoI/AAAAAAAAAbs/pFCFgzlKsn0/s160-c/Sierra%252520Heavens.jpg",
		"https://lh5.googleusercontent.com/-dJgjepFrYSo/URqvBVJZrAI/AAAAAAAAAbs/v-F5QWpYO6s/s160-c/Sierra%252520Sunset.jpg",
		"https://lh4.googleusercontent.com/-Z4zGiC5nWdc/URqvBdEwivI/AAAAAAAAAbs/ZRZR1VJ84QA/s160-c/Sin%252520Lights.jpg",
		"https://lh4.googleusercontent.com/-_0cYiWW8ccY/URqvBz3iM4I/AAAAAAAAAbs/9N_Wq8MhLTY/s160-c/Starry%252520Lake.jpg",
		"https://lh3.googleusercontent.com/-A9LMoRyuQUA/URqvCYx_JoI/AAAAAAAAAbs/s7sde1Bz9cI/s160-c/Starry%252520Night.jpg",
		"https://lh3.googleusercontent.com/-KtLJ3k858eY/URqvC_2h_bI/AAAAAAAAAbs/zzEBImwDA_g/s160-c/Stream.jpg",
		"https://lh5.googleusercontent.com/-dFB7Lad6RcA/URqvDUftwWI/AAAAAAAAAbs/BrhoUtXTN7o/s160-c/Strip%252520Sunset.jpg",
		"https://lh5.googleusercontent.com/-at6apgFiN20/URqvDyffUZI/AAAAAAAAAbs/clABCx171bE/s160-c/Sunset%252520Hills.jpg",
		"https://lh4.googleusercontent.com/-7-EHhtQthII/URqvEYTk4vI/AAAAAAAAAbs/QSJZoB3YjVg/s160-c/Tenaya%252520Lake%2525202.jpg",
		"https://lh6.googleusercontent.com/-8MrjV_a-Pok/URqvFC5repI/AAAAAAAAAbs/9inKTg9fbCE/s160-c/Tenaya%252520Lake.jpg",
		"https://lh5.googleusercontent.com/-B1HW-z4zwao/URqvFWYRwUI/AAAAAAAAAbs/8Peli53Bs8I/s160-c/The%252520Cave%252520BW.jpg",
		"https://lh3.googleusercontent.com/-PO4E-xZKAnQ/URqvGRqjYkI/AAAAAAAAAbs/42nyADFsXag/s160-c/The%252520Fisherman.jpg",
		"https://lh4.googleusercontent.com/-iLyZlzfdy7s/URqvG0YScdI/AAAAAAAAAbs/1J9eDKmkXtk/s160-c/The%252520Night%252520is%252520Coming.jpg",
		"https://lh6.googleusercontent.com/-G-k7YkkUco0/URqvHhah6fI/AAAAAAAAAbs/_taQQG7t0vo/s160-c/The%252520Road.jpg",
		"https://lh6.googleusercontent.com/-h-ALJt7kSus/URqvIThqYfI/AAAAAAAAAbs/ejiv35olWS8/s160-c/Tokyo%252520Heights.jpg",
		"https://lh5.googleusercontent.com/-Hy9k-TbS7xg/URqvIjQMOxI/AAAAAAAAAbs/RSpmmOATSkg/s160-c/Tokyo%252520Highway.jpg",
		"https://lh6.googleusercontent.com/-83oOvMb4OZs/URqvJL0T7lI/AAAAAAAAAbs/c5TECZ6RONM/s160-c/Tokyo%252520Smog.jpg",
		"https://lh3.googleusercontent.com/-FB-jfgREEfI/URqvJI3EXAI/AAAAAAAAAbs/XfyweiRF4v8/s160-c/Tufa%252520at%252520Night.jpg",
		"https://lh4.googleusercontent.com/-vngKD5Z1U8w/URqvJUCEgPI/AAAAAAAAAbs/ulxCMVcU6EU/s160-c/Valley%252520Sunset.jpg",
		"https://lh6.googleusercontent.com/-DOz5I2E2oMQ/URqvKMND1kI/AAAAAAAAAbs/Iqf0IsInleo/s160-c/Windmill%252520Sunrise.jpg",
		"https://lh5.googleusercontent.com/-biyiyWcJ9MU/URqvKculiAI/AAAAAAAAAbs/jyPsCplJOpE/s160-c/Windmill.jpg",
		"https://lh4.googleusercontent.com/-PDT167_xRdA/URqvK36mLcI/AAAAAAAAAbs/oi2ik9QseMI/s160-c/Windmills.jpg",
		"https://lh5.googleusercontent.com/-kI_QdYx7VlU/URqvLXCB6gI/AAAAAAAAAbs/N31vlZ6u89o/s160-c/Yet%252520Another%252520Rockaway%252520Sunset.jpg",
		"https://lh4.googleusercontent.com/-e9NHZ5k5MSs/URqvMIBZjtI/AAAAAAAAAbs/1fV810rDNfQ/s160-c/Yosemite%252520Tree.jpg", };
	/**
	 * 记录所有正在下载或等待下载的任务。
	 */
	private Set<BitmapWorkerTask> taskCollection;

	/**
	 * 图片缓存技术的核心类，用于缓存所有下载好的图片，在程序内存达到设定值时会将最少最近使用的图片移除掉。
	 */
	private LruCache<String, Bitmap> mMemoryCache;

	/**
	 * GridView的实例
	 */
	private GridView mPhotoWall;

	/**
	 * 第一张可见图片的下标
	 */
	private int mFirstVisibleItem;

	/**
	 * 一屏有多少张图片可见
	 */
	private int mVisibleItemCount;

	/**
	 * 记录是否刚打开程序，用于解决进入程序不滚动屏幕，不会下载图片的问题。
	 */
	private boolean isFirstEnter = true;

	public GridViewCacheAdapter(Context context,GridView photoWall)
	{
		super(context, 0,  Images.imageUrls);
		mPhotoWall = photoWall;
		taskCollection = new HashSet<BitmapWorkerTask>();
		// 获取应用程序最大可用内存
		int maxMemory = (int) Runtime.getRuntime().maxMemory();
		int cacheSize = maxMemory / 8;
		// 设置图片缓存大小为程序最大可用内存的1/8
		mMemoryCache = new LruCache<String, Bitmap>(cacheSize)
		{
			@Override
			protected int sizeOf(String key, Bitmap bitmap)
			{
				return bitmap.getByteCount();
			}
		};
		mPhotoWall.setOnScrollListener(this);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		final String url = getItem(position);
		View view;
		if (convertView == null)
		{
			view = LayoutInflater.from(getContext()).inflate(R.layout.photo_layout, null);
		}
		else
		{
			view = convertView;
		}
		final ImageView photo = (ImageView) view.findViewById(R.id.photo);
		// 给ImageView设置一个Tag，保证异步加载图片时不会乱序
		photo.setTag(url);
		setImageView(url, photo);
		return view;
	}

	/**
	 * 给ImageView设置图片。首先从LruCache中取出图片的缓存，设置到ImageView上。如果LruCache中没有该图片的缓存，
	 * 就给ImageView设置一张默认图片。
	 * 
	 * @param imageUrl
	 *            图片的URL地址，用于作为LruCache的键。
	 * @param imageView
	 *            用于显示图片的控件。
	 */
	private void setImageView(String imageUrl, ImageView imageView)
	{
		Bitmap bitmap = getBitmapFromMemoryCache(imageUrl);
		if (bitmap != null)
		{
			imageView.setImageBitmap(bitmap);
		}
		else
		{
			imageView.setImageResource(R.drawable.empty_photo);
		}
	}

	/**
	 * 将一张图片存储到LruCache中。
	 * 
	 * @param key
	 *            LruCache的键，这里传入图片的URL地址。
	 * @param bitmap
	 *            LruCache的键，这里传入从网络上下载的Bitmap对象。
	 */
	public void addBitmapToMemoryCache(String key, Bitmap bitmap)
	{
		if (getBitmapFromMemoryCache(key) == null)
		{
			mMemoryCache.put(key, bitmap);
		}
	}

	/**
	 * 从LruCache中获取一张图片，如果不存在就返回null。
	 * 
	 * @param key
	 *            LruCache的键，这里传入图片的URL地址。
	 * @return 对应传入键的Bitmap对象，或者null。
	 */
	public Bitmap getBitmapFromMemoryCache(String key)
	{
		return mMemoryCache.get(key);
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState)
	{
		// 仅当GridView静止时才去下载图片，GridView滑动时取消所有正在下载的任务
		if (scrollState == SCROLL_STATE_IDLE)
		{
			loadBitmaps(mFirstVisibleItem, mVisibleItemCount);
		}
		else
		{
			cancelAllTasks();
		}
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount,
		int totalItemCount)
	{
		mFirstVisibleItem = firstVisibleItem;
		mVisibleItemCount = visibleItemCount;
		// 下载的任务应该由onScrollStateChanged里调用，但首次进入程序时onScrollStateChanged并不会调用，
		// 因此在这里为首次进入程序开启下载任务。
		if (isFirstEnter && visibleItemCount > 0)
		{
			loadBitmaps(firstVisibleItem, visibleItemCount);
			isFirstEnter = false;
		}
	}

	/**
	 * 加载Bitmap对象。此方法会在LruCache中检查所有屏幕中可见的ImageView的Bitmap对象，
	 * 如果发现任何一个ImageView的Bitmap对象不在缓存中，就会开启异步线程去下载图片。
	 * 
	 * @param firstVisibleItem
	 *            第一个可见的ImageView的下标
	 * @param visibleItemCount
	 *            屏幕中总共可见的元素数
	 */
	private void loadBitmaps(int firstVisibleItem, int visibleItemCount)
	{
		try
		{
			for (int i = firstVisibleItem; i < firstVisibleItem + visibleItemCount; i++)
			{
				String imageUrl = Images.imageUrls[i];
				Bitmap bitmap = getBitmapFromMemoryCache(imageUrl);
				if (bitmap == null)
				{
					BitmapWorkerTask task = new BitmapWorkerTask();
					taskCollection.add(task);
					task.execute(imageUrl);
				}
				else
				{
					ImageView imageView = (ImageView) mPhotoWall.findViewWithTag(imageUrl);
					if (imageView != null && bitmap != null)
					{
						imageView.setImageBitmap(bitmap);
					}
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * 取消所有正在下载或等待下载的任务。
	 */
	public void cancelAllTasks()
	{
		if (taskCollection != null)
		{
			for (BitmapWorkerTask task : taskCollection)
			{
				task.cancel(false);
			}
		}
	}

	/**
	 * 异步下载图片的任务。
	 * 
	 * @author guolin
	 */
	class BitmapWorkerTask extends AsyncTask<String, Void, Bitmap>
	{

		/**
		 * 图片的URL地址
		 */
		private String imageUrl;

		@Override
		protected Bitmap doInBackground(String... params)
		{
			imageUrl = params[0];
			// 在后台开始下载图片
			Bitmap bitmap = downloadBitmap(params[0]);
			if (bitmap != null)
			{
				// 图片下载完成后缓存到LrcCache中
				addBitmapToMemoryCache(params[0], bitmap);
			}
			return bitmap;
		}

		@Override
		protected void onPostExecute(Bitmap bitmap)
		{
			super.onPostExecute(bitmap);
			// 根据Tag找到相应的ImageView控件，将下载好的图片显示出来。
			ImageView imageView = (ImageView) mPhotoWall.findViewWithTag(imageUrl);
			if (imageView != null && bitmap != null)
			{
				imageView.setImageBitmap(bitmap);
			}
			taskCollection.remove(this);
		}

		/**
		 * 建立HTTP请求，并获取Bitmap对象。
		 * 
		 * @param imageUrl
		 *            图片的URL地址
		 * @return 解析后的Bitmap对象
		 */
		private Bitmap downloadBitmap(String imageUrl)
		{
			Bitmap bitmap = null;
			HttpURLConnection con = null;
			try
			{
				URL url = new URL(imageUrl);
				con = (HttpURLConnection) url.openConnection();
				con.setConnectTimeout(5 * 1000);
				con.setReadTimeout(10 * 1000);
				con.setDoInput(true);
				con.setDoOutput(true);
				bitmap = BitmapFactory.decodeStream(con.getInputStream());
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			finally
			{
				if (con != null)
				{
					con.disconnect();
				}
			}
			return bitmap;
		}

	}

}
