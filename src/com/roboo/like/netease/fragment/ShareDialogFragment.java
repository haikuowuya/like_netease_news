package com.roboo.like.netease.fragment;

import java.util.LinkedList;

import android.annotation.SuppressLint;
import android.app.DialogFragment;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.roboo.like.netease.R;
import com.roboo.like.netease.adapter.ShareGridViewAdapter;
import com.roboo.like.netease.model.ShareType;

public class ShareDialogFragment extends DialogFragment
{
	private LinkedList<ShareType> mData;
	private int[] mShareTypeImages = { R.drawable.ic_share_netease, R.drawable.ic_share_sina, R.drawable.ic_share_qq_weibo, R.drawable.ic_share_renren, R.drawable.ic_share_sms,R.drawable.ic_share_email };
	private String[] mShareTypeName = {"网易微博","新浪微博","腾讯微博","人人网","短信","电子邮件"};
	public static ShareDialogFragment newInstance()
	{
		ShareDialogFragment fragment = new ShareDialogFragment();
		return fragment;
	}
	/*
	 * TODO @see android.app.Fragment#onCreateView(android.view.LayoutInflater,
	 * android.view.ViewGroup, android.os.Bundle)
	 */
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		if (getDialog() != null)
		{
			getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
			getDialog().getWindow().setBackgroundDrawableResource(android.R.color.transparent);
		}
		View view = inflater.inflate(R.layout.fragment_share_dialog, null);
		GridView gridView = (GridView) view.findViewById(R.id.gv_gridview);
		gridView.setAdapter(getAdatper());
		
		gridView.setOnItemClickListener(new OnItemClickListener()
		{

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id)
			{
				if(getDialog() != null)
				{
					getDialog().dismiss();
				}
					
			}
		});
		return view;
	}

	@SuppressLint("NewApi")
	public void onStart()
	{
		super.onStart();
		// change dialog width
		if (getDialog() != null)
		{
			int fullWidth = getDialog().getWindow().getAttributes().width;
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2)
			{
				Display display = getActivity().getWindowManager().getDefaultDisplay();
				Point size = new Point();
				display.getSize(size);
				fullWidth = size.x;
			}
			else
			{
				Display display = getActivity().getWindowManager().getDefaultDisplay();
				fullWidth = display.getWidth();
			}

			final int padding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources()
				.getDisplayMetrics());

			int w = fullWidth - padding;
			int h = getDialog().getWindow().getAttributes().height;

			getDialog().getWindow().setLayout(w, h);
		}
	}

	public ShareGridViewAdapter getAdatper()
	{
		mData = new LinkedList<ShareType>();
		for(int i = 0; i < mShareTypeImages.length;i++)
		{
			ShareType type = new ShareType();
			type.setShareTypeImage(mShareTypeImages[i]);
			type.setShareTypeName(mShareTypeName[i]);
			mData.add(type);
		}
		ShareGridViewAdapter adapter = new ShareGridViewAdapter(getActivity(), mData);
		return adapter;
	}
}
