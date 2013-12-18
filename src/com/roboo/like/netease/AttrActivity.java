package com.roboo.like.netease;

import android.os.AsyncTask;
import android.os.AsyncTask.Status;
import android.os.Bundle;
import android.widget.Toast;

import com.roboo.like.netease.view.RoundProgressBar;

public class AttrActivity extends BaseActivity
{

	private RoundProgressBar mProgressBar1;
	private RoundProgressBar mProgressBar2;
	private ProgressTask mProgressTask;
 
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{

		super.onCreate(savedInstanceState);
		setTVTitle(R.string.tv_attr);
		setContentView(R.layout.activity_attr);
		initView();
		this.mProgressBar1.setMax(100);
		this.mProgressBar2.setMax(100);
		mProgressTask = new ProgressTask();
		mProgressTask.execute();

	}
	@Override
	protected void onStop()
	{
		mProgressTask.flag  = false;
		 if(mProgressTask.getStatus() == Status.FINISHED)
		 {
			 Toast.makeText(this, "结束", Toast.LENGTH_SHORT).show();
		 }
	}
	private void initView()
	{
		this.mProgressBar1 = (RoundProgressBar) findViewById(R.id.roundProgressBar1);
		this.mProgressBar2 = (RoundProgressBar) findViewById(R.id.roundProgressBar2);
	}

	private class ProgressTask extends AsyncTask<Void, Integer, Void>
	{
		public boolean flag = true;
		@Override
		protected Void doInBackground(Void... params)
		{
			int progress1 = 0;
			int progress2 = 0;
			while (true && flag)
			{
				if (progress1 > mProgressBar1.getMax())
				{
					progress1 = 0;
				}
				if (progress2 > mProgressBar2.getMax())
				{
					progress2 = 0;
				}
				progress1 += 2;
				try
				{
					Thread.sleep(300);
				}
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}
				progress2 += 5;
				publishProgress(progress1, progress2);
			}
			return null;
		}

		@Override
		protected void onProgressUpdate(Integer... values)
		{
			super.onProgressUpdate(values);
			mProgressBar1.setProgress(values[0]);
			mProgressBar2.setProgress(values[1]);
			
		}

	}
}
