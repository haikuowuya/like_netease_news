package com.roboo.like.netease;

import com.roboo.like.netease.jni.JniClient;

import android.os.Bundle;
import android.widget.TextView;

public class JNIActivity extends BaseActivity
{
	private TextView mTVJNI;
	
	static
	{
		System.loadLibrary("JniTestNDK");
	}
	
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		 setTVTitle(R.string.tv_jni);
		 //TODO setContentView Tag
		 setContentView(R.layout.activity_jni);
		 this.mTVJNI = (TextView) findViewById(R.id.tv_jni);
		  String str = JniClient.addStr("", "");
		  int sum  = JniClient.addInt(10, 10);
		  this.mTVJNI.setText(str + " :: " + sum);
	}
}
