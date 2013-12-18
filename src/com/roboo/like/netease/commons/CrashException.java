package com.roboo.like.netease.commons;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.lang.Thread.UncaughtExceptionHandler;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnKeyListener;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;
import android.os.Process;
import android.view.KeyEvent;
import android.widget.Toast;

import com.roboo.like.netease.NewsApplication;

public class CrashException implements UncaughtExceptionHandler
{
	private static final String PATH = Environment.getExternalStorageDirectory().getPath() + "/crash/";
	private static final String FILE_NAME = "crash";

	// log文件的后缀名
	private static final String FILE_NAME_SUFFIX = ".trace";

	private Activity activity;

	public CrashException(Activity activity)
	{
		this.activity = activity;
	}

	@Override
	public void uncaughtException(Thread thread, Throwable ex)
	{
		System.out.println("程序出现异常！");
		System.out.println("线程Id = " + thread.getId() + " 线程名称 = " + thread.getName() + " 错误原因 = " + ex.toString());
		ex.printStackTrace();
		saveExceptionToSDCard(ex);
		showExceptionDialog();
	}

	/***
	 * 保存异常信息到sd卡，如果存在的话
	 * 
	 * @param ex
	 */
	@SuppressLint("SimpleDateFormat")
	private void saveExceptionToSDCard(Throwable ex)
	{
		if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
		{
			return;
		}
		File dir = new File(PATH);
		if (!dir.exists())
		{
			dir.mkdirs();
		}
		deleteCrashFile(dir);
		long currentTime = System.currentTimeMillis();
		String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(currentTime));
		String fileName = time +"_" + System.currentTimeMillis();
		 
		// 以当前时间创建log文件
		File file = new File(PATH + FILE_NAME + fileName + FILE_NAME_SUFFIX);

		try
		{
			PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file)));
			// 导出发生异常的时间
			pw.println(time);
			// 导出手机信息
			getPhoneInfo(pw);
			pw.println();
			// 导出异常的调用栈信息
			ex.printStackTrace(pw);
			pw.flush();
			pw.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * 删除以前记录的异常文件
	 * 
	 * @param dir
	 */
	private void deleteCrashFile(File dir)
	{
		if (dir != null)
		{
			for (File file : dir.listFiles())
			{
				file.delete();
			}
			System.out.println("清空所以的异常文件成功");
		}
	}

	/**
	 * 获取发生异常的手机信息
	 * 
	 * @param pw
	 * @throws NameNotFoundException
	 */
	private void getPhoneInfo(PrintWriter pw) throws NameNotFoundException
	{
		// 应用的版本名称和版本号
		PackageManager pm = activity.getPackageManager();
		PackageInfo pi = pm.getPackageInfo(activity.getPackageName(), PackageManager.GET_ACTIVITIES);
		pw.print("App Version: ");
		pw.print(pi.versionName);
		pw.print('_');
		pw.println(pi.versionCode);

		// android版本号
		pw.print("OS Version: ");
		pw.print(Build.VERSION.RELEASE);
		pw.print("_");
		pw.println(Build.VERSION.SDK_INT);

		// 手机制造商
		pw.print("Vendor: ");
		pw.println(Build.MANUFACTURER);

		// 手机型号
		pw.print("Model: ");
		pw.println(Build.MODEL);

		// cpu架构
		pw.print("CPU ABI: ");
		pw.println(Build.CPU_ABI);
		System.out.println("获取手机信息完毕");
	}

	/**
	 * 弹出异常对话框
	 */
	private void showExceptionDialog()
	{
		Runnable runnable = new Runnable()
		{
			public void run()
			{
				Looper.prepare();
				final AlertDialog dialog = new AlertDialog.Builder(activity).setTitle("提示").setMessage("程序崩溃了...").setNeutralButton("我知道了", new OnClickListener()
				{
					public void onClick(DialogInterface dialog, int which)
					{
						NewsApplication.getInstance().exitClient();
						Process.killProcess(Process.myPid());
						System.exit(10);
					}
				}).create();
				dialog.setCanceledOnTouchOutside(false);
				dialog.show();
				dialog.setOnKeyListener(new OnKeyListener()
				{
					public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event)
					{
						if (keyCode == KeyEvent.KEYCODE_BACK)
						{
							dialog.dismiss();
							Toast.makeText(activity, "返回", Toast.LENGTH_SHORT).show();
							NewsApplication.getInstance().exitClient();
							Process.killProcess(Process.myPid());
							System.exit(10);
							return true;
						}
						return false;

					}
				});
				Looper.loop();

			}
		};
		new Thread(runnable).start();
	}

	public static CrashException getInstance(Activity activity)
	{
		return new CrashException(activity);
	}

}
