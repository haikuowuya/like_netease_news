package com.roboo.like.netease;

import java.util.LinkedList;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.util.LruCache;
import android.view.ActionMode;
import android.view.ActionProvider;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView.MultiChoiceModeListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.roboo.like.netease.adapter.DSLVAdapter;
import com.roboo.like.netease.dao.INewsCategoryDao;
import com.roboo.like.netease.dao.impl.NewsCategoryDaoImpl;
import com.roboo.like.netease.database.DBHelper;
import com.roboo.like.netease.model.NewsCategory;

@SuppressLint("NewApi")
public class AddSubscribeActivity extends BaseActivity
{
	private ListView mListView;
	private MyActionModeCallBack mActionModeCallBack;
	private DSLVAdapter mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setTVTitle(R.string.tv_subscribe_interested);
		// TODO setContentView Tag
		setContentView(R.layout.activity_add_subscribe);
		initView();
		this.mListView.setAdapter(getAdapter());
		setListener();
	 
	}
	private void initView()
	{
		this.mListView = (ListView) findViewById(R.id.lv_list);
	}
	public DSLVAdapter getAdapter()
	{
		INewsCategoryDao newsCategoryDao = new  NewsCategoryDaoImpl(new DBHelper(this));
		 LinkedList<NewsCategory> data = newsCategoryDao.getUnCustomedList();
		 if(null != data)
		 {
			 mAdapter = new DSLVAdapter(data, this);
		 }
		 return mAdapter;
	}

	private void setListener()
	{
		mActionModeCallBack = new MyActionModeCallBack();
		this.mListView.setMultiChoiceModeListener(mActionModeCallBack);
		this.mListView.setOnItemClickListener(new OnItemClickListener()
		{

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id)
			{
				NewsCategory newsCategory = (NewsCategory) parent.getAdapter().getItem(position);
				Intent intent = new Intent(AddSubscribeActivity.this,NewsActivity.class);
				intent.putExtra("newsCategory", newsCategory);
				startActivity(intent);
			}
		});
	}
	private void setSubtitle(ActionMode mode)
	{
		final int checkedCount = mListView.getCheckedItemCount();
		switch (checkedCount)
		{
		case 0:
			mode.setSubtitle(null);
			break;
		case 1:
			mode.setSubtitle("1个被选中");
			break;
		default:
			mode.setSubtitle("" + checkedCount + "个被选中");
			break;
		}
	}
	private class MyActionModeCallBack implements MultiChoiceModeListener
	{
		
		@Override
		public boolean onPrepareActionMode(ActionMode mode, Menu menu)
		{
			return true;
		}
		@Override
		public void onDestroyActionMode(ActionMode mode)
		{
			Toast.makeText(getBaseContext(), "完成", Toast.LENGTH_SHORT).show();
			finish();
		}

		@Override
		public boolean onCreateActionMode(ActionMode mode, Menu menu)
		{
			mode.setTitle("选中栏目");
			setSubtitle(mode);
			MenuInflater menuInflater = getMenuInflater();
			menuInflater.inflate(R.menu.action_mode_menu, menu);
			return true;
		}

		@Override
		public boolean onActionItemClicked(ActionMode mode, MenuItem item)
		{
			Toast.makeText(AddSubscribeActivity.this, "KKK", Toast.LENGTH_SHORT).show();
			return true;
		}

		@Override
		public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked)
		{
			INewsCategoryDao newsCategoryDao = new NewsCategoryDaoImpl(new DBHelper(AddSubscribeActivity.this));
			newsCategoryDao.updateNewsCategoryState(((NewsCategory)mAdapter.getItem(position)).getNewsCategoryMD5(), checked);
			setSubtitle(mode);
		}
	};
}
