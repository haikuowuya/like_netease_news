package com.roboo.like.netease;

import android.content.Intent;
import android.content.Intent.ShortcutIconResource;
import android.os.Bundle;

public class ShortCutActivity extends BaseActivity
{
	 
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		
		super.onCreate(savedInstanceState);
		 setTVTitle(R.string.tv_about);
		 //TODO setContentView Tag
		 setContentView(R.layout.activity_short_cut);
		 sendToDestop();
	}
	
	/** 发送到桌面 */
	private void sendToDestop()
	{
 
			// 安装的Intent
			Intent shortcut = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");
			// 快捷名称
			shortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME, "网易新闻");
			// 快捷图标是允许重复
			shortcut.putExtra("duplicate", false);
			Intent shortcutIntent = new Intent(Intent.ACTION_MAIN);
			shortcutIntent.setClassName(getPackageName(), getPackageName()+".MainActivity");
			shortcutIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			shortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);

			// 快捷图标
			ShortcutIconResource iconRes = Intent.ShortcutIconResource.fromContext(this, R.drawable.ic_launcher);
			shortcut.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, iconRes);

			// 发送广播
			 sendBroadcast(shortcut);
			// Toast.makeText(mActivity, "发送到桌面",
			// Toast.LENGTH_SHORT).show();
		}
}
