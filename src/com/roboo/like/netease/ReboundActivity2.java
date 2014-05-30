package com.roboo.like.netease;

import android.os.Bundle;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class ReboundActivity2 extends BaseActivity 
{
	  private TableLayout mMainLayout;
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rebound_scrollview);
		setTVTitle(R.string.tv_rebound_scrollview);
		initView();
		showTable();
	}

	/**
	 * TODO initView
	 * 
	 */
	private void initView()
	{
		  mMainLayout = (TableLayout) findViewById(R.id.table_layout);
	}

	 public void showTable() {
	        TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(TableRow.LayoutParams
	                .MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT);
	        layoutParams.gravity = Gravity.CENTER;

	        for (int i = 0; i < 40; i++) {
	            TableRow tableRow = new TableRow(this);
	            TextView textView = new TextView(this);
	            textView.setText("Test pull scroll view " + i);
	            textView.setTextSize(20);
	            textView.setPadding(10, 10, 10, 10);

	            tableRow.addView(textView, layoutParams);
	            mMainLayout.addView(tableRow);
	        }
	    }
	}
