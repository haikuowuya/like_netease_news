package com.roboo.like.netease.utils;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.Intent.ShortcutIconResource;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ProviderInfo;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;

/**
 * 桌面快捷方式有关的工具类
 * 
 * @author xiaanming
 * 
 */
public class ShortCutUtils
{
	/**
	 * 快捷方式添加的action
	 */
	private final static String SHORTCUT_ADD_ACTION = "com.android.launcher.action.INSTALL_SHORTCUT";
	/**
	 * 快捷方式删除的action
	 */
	private final static String SHORTCUT_DEL_ACTION = "com.android.launcher.action.UNINSTALL_SHORTCUT";
	/**
	 * 读取数据库需要的权限
	 */
	private final static String READ_SETTINGS_PERMISSION = "com.android.launcher.permission.READ_SETTINGS";

	/**
	 * 添加快捷方式到桌面，添加快捷方式需要添加用户权限 <uses-permission
	 * android:name="com.android.launcher.permission.INSTALL_SHORTCUT" />
	 * 
	 * @param context
	 *            当前的context对象
	 * @param resourceId
	 *            快捷方式的图标资源id
	 */
	public static void addShortCut(Context context, int resourceId)
	{
		Intent shortCutIntent = new Intent(SHORTCUT_ADD_ACTION);
		// 添加快捷方式的名字
		// 获取当前应用名称
		String appName = null;
		try
		{
			appName = obtatinAppName(context);
		}
		catch (NameNotFoundException e)
		{
			e.printStackTrace();
		}
		shortCutIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, appName);
		// 不允许重复添加
		shortCutIntent.putExtra("duplicate", false);

		shortCutIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT,
				new Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_LAUNCHER).setClassName(context.getPackageName(),
						context.getClass().getName()));
		// 添加快捷方式的图标
		ShortcutIconResource iconRes = Intent.ShortcutIconResource.fromContext(context, resourceId);
		shortCutIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, iconRes);

		context.sendBroadcast(shortCutIntent);
	}

	/**
	 * 删除桌面上的快捷方式，需要添加权限 <uses-permission
	 * 
	 * 
	 * @param context
	 */
	public static void delShortcut(Context context, Activity activity)
	{
		Intent shortcut = new Intent(SHORTCUT_DEL_ACTION);
		// 获取当前应用名称
		String appName = null;
		try
		{
			appName = obtatinAppName(context);
		}
		catch (NameNotFoundException e)
		{
			e.printStackTrace();
		}
		// 快捷方式名称
		shortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME, appName);
		shortcut.putExtra(
				Intent.EXTRA_SHORTCUT_INTENT,
				new Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_LAUNCHER).setClassName(context.getPackageName(),
						context.getClass().getName()));
		context.sendBroadcast(shortcut);
	}

	/**
	 * 判断桌面上是否有快捷方式，调用此方法需要添加权限 <uses-permission
	 * android:name="com.android.launcher.permission.READ_SETTINGS" />
	 * 
	 * @param context
	 * @return
	 * @throws NameNotFoundException
	 */
	public static boolean hasShortcut(Context context)
	{
		String AUTHORITY = getAuthorityFromPermission(context, READ_SETTINGS_PERMISSION);

		System.out.println(AUTHORITY);

		if (AUTHORITY == null)
		{
			return false;
		}
		Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/favorites?notify=true");
		String appName = null;
		try
		{
			appName = obtatinAppName(context);
		}
		catch (NameNotFoundException e)
		{
			e.printStackTrace();
		}
		Cursor c = context.getContentResolver().query(CONTENT_URI, new String[] { "title" }, "title=?", new String[] { appName }, null);
		if (c != null && c.getCount() > 0)
		{
			return true;
		}
		return false;
	}

	/**
	 * android系统桌面的基本信息由一个launcher.db的Sqlite数据库管理，里面有三张表
	 * 其中一张表就是favorites。这个db文件一般放在data
	 * /data/com.android.launcher(launcher2)文件的databases下 但是对于不同的rom会放在不同的地方
	 * 例如MIUI放在data/data/com.miui.home/databases下面
	 * htc放在data/data/com.htc.launcher/databases下面
	 * 
	 * @param context
	 * @param permission
	 *            读取设置的权限 READ_SETTINGS_PERMISSION
	 * @return
	 */
	private static String getAuthorityFromPermission(Context context, String permission)
	{
		if (TextUtils.isEmpty(permission))
		{
			return null;
		}
		List<PackageInfo> packs = context.getPackageManager().getInstalledPackages(PackageManager.GET_PROVIDERS);
		if (packs == null)
		{
			return null;
		}
		for (PackageInfo pack : packs)
		{
			ProviderInfo[] providers = pack.providers;
			if (providers != null)
			{
				for (ProviderInfo provider : providers)
				{
					if (permission.equals(provider.readPermission) || permission.equals(provider.writePermission))
					{
						return provider.authority;
					}
				}
			}
		}
		return null;
	}

	/**
	 * 获取应用的名称
	 * 
	 * @param context
	 * @return
	 * @throws NameNotFoundException
	 */
	private static String obtatinAppName(Context context) throws NameNotFoundException
	{
		PackageManager packageManager = context.getPackageManager();
		return packageManager.getApplicationLabel(packageManager.getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA))
				.toString();
	}

}