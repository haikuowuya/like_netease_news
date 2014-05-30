package com.roboo.like.netease;

import com.roboo.like.netease.fragment.DSLVFragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;

public class SubscribedActivity extends BaseActivity implements OnClickListener
{
	private Button mBtnAddSubscribe;
	private ImageButton mIbtnSubscribe;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setTVTitle(R.string.tv_edit_subscribe);
		// TODO setContentView Tag
		setContentView(R.layout.activity_subscribed);
		initView();
		this.mIbtnSubscribe.setOnClickListener(this);
		this.mBtnAddSubscribe.setOnClickListener(this);
		getSupportFragmentManager().beginTransaction().add(R.id.frame_container, DSLVFragment.newInstance(0, 0), "DSLV").commit();
	}
	/**
	 * TODO init view
	 * 
	 */
	private void initView()
	{
		this.mIbtnSubscribe = (ImageButton) findViewById(R.id.ibtn_subscribe);
		this.mBtnAddSubscribe = (Button) findViewById(R.id.btn_add_subcribe);
	}
	/**
	 * TODO @see {@link View.OnClickListener}
	 */
	public void onClick(View v)
	{
		switch (v.getId())
		{
		case R.id.ibtn_subscribe:// ���
			this.finish();
			break;
		case R.id.btn_add_subcribe:// ��Ӷ���
			addSubcribe();
		default:
			super.onClick(v);
			break;
		}
	}
	private void addSubcribe()
	{
		Intent intent = new Intent(this, AddSubscribeActivity.class);
		startActivity(intent);
	}

}
