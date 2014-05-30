package com.roboo.like.netease.widget;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.widget.TextView;

import com.roboo.like.netease.BaseActivity;
import com.roboo.like.netease.R;

public class TextViewActivity extends BaseActivity
{
	private TextView mTvText;
	private TextView mTvCustomText;
	/**用于测试富文本*/
	private TextView mTvCustomText1;
	private static final String DEFAULT ="默认字体";
	private static final String CUSTOM ="华康字体";
	private static final String CUSTOM_1="这行文字中的中国是红色的";
	
	 
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		 setTVTitle(R.string.tv_textview);
		 //TODO setContentView Tag
		 setContentView(R.layout.activity_textview);
		 this.mTvText = (TextView) findViewById(R.id.tv_text);
		 this.mTvCustomText = (TextView) findViewById(R.id.tv_custom_text);
		 this.mTvCustomText1 = (TextView) findViewById(R.id.tv_custom_text_1);
		 Typeface typeface = Typeface.createFromAsset(getAssets(), "custom.ttf");
		 this.mTvText.setText(DEFAULT);
		 this.mTvCustomText.setTypeface(typeface);
		 this.mTvCustomText.setText(CUSTOM);
		SpannableString spannableString = new SpannableString(CUSTOM_1);
		ForegroundColorSpan redColorSpan = new ForegroundColorSpan(Color.RED);
		UnderlineSpan underlineSpan = new UnderlineSpan();
		spannableString.setSpan(redColorSpan, 6, 8, SpannableString.SPAN_INCLUSIVE_EXCLUSIVE);
		spannableString.setSpan(underlineSpan, 6, 8, SpannableString.SPAN_INCLUSIVE_EXCLUSIVE);
		
		this.mTvCustomText1.setMovementMethod(LinkMovementMethod.getInstance());
		this.mTvCustomText1.setText(spannableString);
	}
}
