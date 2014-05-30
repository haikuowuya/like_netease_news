package com.roboo.like.netease.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView.MultiChoiceModeListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.GridView;
import android.widget.ImageView;

import com.roboo.like.netease.MainActivity;
import com.roboo.like.netease.R;
import com.roboo.like.netease.adapter.WelcomeGridViewAdapter;

public class WelcomeFragment extends Fragment  
{
	//private int mCurrentResId;
	GridView gridView;
	private int[] mSelectedData = { R.drawable.ic_yule_selected, R.drawable.ic_tiyu_selected, R.drawable.ic_caijing_selected, R.drawable.ic_keji_selected, R.drawable.ic_lingyimian_selected, R.drawable.ic_zhongchao_selected, R.drawable.ic_junshi_selected, R.drawable.ic_nba_selected, R.drawable.ic_qiche_selected };
	private int[] mUnSelectedData = { R.drawable.ic_yule_normal, R.drawable.ic_tiyue_normal, R.drawable.ic_caijing_normal, R.drawable.ic_keji_normal, R.drawable.ic_lingyimian_normal, R.drawable.ic_zhongchao_normal, R.drawable.ic_junshi_normal, R.drawable.ic_nba_normal, R.drawable.ic_qiche_normal };
	public static WelcomeFragment newInstance(int position)
	{
		WelcomeFragment fragment = new WelcomeFragment();
		Bundle bundle = new Bundle();
		bundle.putInt("position", position);
		fragment.setArguments(bundle);
		return fragment;
	}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		int positon = getArguments().getInt("position");
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		FrameLayout frameLayout = new FrameLayout(getActivity());
		frameLayout.setBackgroundResource(R.drawable.background_card);
		frameLayout.setLayoutParams(params);
		View view = null;
		if (positon == 0)
		{
			view = inflater.inflate(R.layout.fragment_welcome_imageview, null);
		}
		else if (positon == 1)
		{
			view = inflater.inflate(R.layout.fragment_welcome_gridview, null);
			 gridView = (GridView) view.findViewById(R.id.gv_gridview);
			gridView.setAdapter(getAdatper());
			gridView.setOnItemClickListener(new OnItemClickListener()
			{
				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id)
				{
					ImageView imageView = (ImageView) view.findViewById(R.id.iv_image);
				
				}
			});
			Button btnStartExperience = (Button) view.findViewById(R.id.btn_start_experience);
			btnStartExperience.setOnClickListener(new OnClickListener()
			{
				@Override
				public void onClick(View v)
				{
					//Toast.makeText(getActivity(), "开始体验", Toast.LENGTH_SHORT).show();
					Intent intent = new Intent(getActivity(), MainActivity.class);
					startActivity(intent);
					getActivity().finish();
				}
			});
		}
		frameLayout.addView(view);
		return frameLayout;
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);
		//多选模式
		gridView.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE_MODAL);
		 
	}
	public WelcomeGridViewAdapter getAdatper()
	{
		WelcomeGridViewAdapter adapter = new WelcomeGridViewAdapter(getActivity(), mSelectedData);
		return adapter;
	}

	 
}
