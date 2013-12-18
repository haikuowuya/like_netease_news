package com.roboo.like.netease.view.fragment;

import java.util.LinkedList;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.roboo.like.netease.R;
import com.roboo.like.netease.adapter.DSLVAdapter;
import com.roboo.like.netease.dao.INewsCategoryDao;
import com.roboo.like.netease.dao.impl.NewsCategoryDaoImpl;
import com.roboo.like.netease.database.DBHelper;
import com.roboo.like.netease.dragdroplistview.DragSortController;
import com.roboo.like.netease.dragdroplistview.DragSortListView;
import com.roboo.like.netease.model.NewsCategory;

public class DSLVFragment extends ListFragment
{
	DSLVAdapter mAdapter;
	private DragSortListView.DropListener onDrop =
		new DragSortListView.DropListener()
		{
			@Override
			public void drop(int from, int to)
			{
				if (from != to)
				{
					NewsCategory item = (NewsCategory) mAdapter.getItem(from);
					item.setNewsCategoryOrder(to);
					mAdapter.remove(item);
					mAdapter.insert(item, to);
					INewsCategoryDao newsCategoryDao = new NewsCategoryDaoImpl(new DBHelper(getActivity()));
					newsCategoryDao.updateNewsCategoryOrder(item.getNewsCategoryMD5(), to);
					
				}
			}
		};

	private DragSortListView.RemoveListener onRemove =
		new DragSortListView.RemoveListener()
		{
			@Override
			public void remove(int which)
			{
				if (mAdapter.getCount() > 1)
				{
					NewsCategory item = (NewsCategory) mAdapter.getItem(which);
					mAdapter.remove(item);
					INewsCategoryDao newsCategoryDao = new NewsCategoryDaoImpl(new DBHelper(getActivity()));
					newsCategoryDao.updateNewsCategoryState(item.getNewsCategoryMD5(), false);
				}
				else
				{
					Toast.makeText(getActivity(), "至少保留一个栏目吧", Toast.LENGTH_SHORT).show();
					mAdapter.notifyDataSetChanged();
				}
			}

		};

	protected int getLayout()
	{
		return R.layout.fragment_dslv_main;
	}

	protected int getItemLayout()
	{
		/*
		 * if (removeMode == DragSortController.FLING_LEFT_REMOVE || removeMode
		 * == DragSortController.SLIDE_LEFT_REMOVE) { return
		 * R.layout.list_item_handle_right; } else
		 */
		if (removeMode == DragSortController.CLICK_REMOVE)
		{
			return R.layout.list_item_click_remove;
		}
		else
		{
			return R.layout.list_item_handle_left;
		}
	}

	private DragSortListView mDslv;
	private DragSortController mController;

	public int dragStartMode = DragSortController.ON_DOWN;
	public boolean removeEnabled = true;
	public int removeMode = DragSortController.CLICK_REMOVE;
	public boolean sortEnabled = true;
	public boolean dragEnabled = true;

	public static DSLVFragment newInstance(int headers, int footers)
	{
		DSLVFragment fragment = new DSLVFragment();
		Bundle args = new Bundle();
		args.putInt("headers", headers);
		args.putInt("footers", footers);
		fragment.setArguments(args);

		return fragment;
	}
	@Override
	public void onResume()
	{
		setListAdapter();
		super.onResume();

	}
	public DragSortController getController()
	{
		return mController;
	}
	public void setListAdapter()
	{
		INewsCategoryDao newsCategoryDao = new NewsCategoryDaoImpl(new DBHelper(getActivity()));
		LinkedList<NewsCategory> data = newsCategoryDao.getCustomedList();
		if (null != data)
		{
			mAdapter = new DSLVAdapter(data, getActivity(), true);
		}
		setListAdapter(mAdapter);
	}

	/**
	 * Called in onCreateView. Override this to provide a custom
	 * DragSortController.
	 */
	public DragSortController buildController(DragSortListView dslv)
	{
		DragSortController controller = new DragSortController(dslv);
		controller.setDragHandleId(R.id.drag_handle);
		controller.setClickRemoveId(R.id.click_remove);
		controller.setRemoveEnabled(removeEnabled);
		controller.setSortEnabled(sortEnabled);
		controller.setDragInitMode(dragStartMode);
		controller.setRemoveMode(removeMode);
		return controller;
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
		Bundle savedInstanceState)
	{
		mDslv = (DragSortListView) inflater.inflate(getLayout(), container, false);
		mController = buildController(mDslv);
		mDslv.setFloatViewManager(mController);
		mDslv.setOnTouchListener(mController);
		mDslv.setDragEnabled(dragEnabled);
		return mDslv;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);
		mDslv = (DragSortListView) getListView();
		mDslv.setDropListener(onDrop);
		mDslv.setRemoveListener(onRemove);
	}

}
