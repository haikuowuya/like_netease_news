package com.roboo.like.netease;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.roboo.like.netease.adapter.TwoWayAdapter;
import com.roboo.like.netease.view.TwoWayView;

public class TwoWayActivity extends BaseActivity
{
	private static final String TAG="TwoWayActivity";
    private TwoWayView mListView;

    private Toast mToast;
    private String mClickMessage="";
    private String mScrollMessage="";
    private String mStateMessage="";

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		
		super.onCreate(savedInstanceState);
		setTVTitle(R.string.tv_twoway);
		// TODO setContentView Tag
		setContentView(R.layout.activity_twoway);
		mToast = Toast.makeText(this, "", Toast.LENGTH_SHORT);
        mToast.setGravity(Gravity.CENTER, 0, 0);

        mListView = (TwoWayView) findViewById(R.id.list);
        mListView.setItemMargin(10);
        mListView.setLongClickable(true);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View child, int position,
                    long id) {
                mClickMessage = "Item clicked: " + position;
                refreshToast();
            }
        });

        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View child,
                    int position, long id) {
                mClickMessage = "Item long pressed: " + position;
                refreshToast();
                return true;
            }
        });

        mListView.setOnScrollListener(new TwoWayView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(TwoWayView view, int scrollState) {
                String stateName = "Undefined";
                switch(scrollState) {
                case SCROLL_STATE_IDLE:
                    stateName = "Idle";
                    break;

                case SCROLL_STATE_TOUCH_SCROLL:
                    stateName = "Dragging";
                    break;

                case SCROLL_STATE_FLING:
                    stateName = "Flinging";
                    break;
                }

                mStateMessage = "Scroll state changed: " + stateName;
                refreshToast();
            }

            @Override
            public void onScroll(TwoWayView view, int firstVisibleItem,
                    int visibleItemCount, int totalItemCount) {
                mScrollMessage = "Scroll (first: " + firstVisibleItem + ", count = " + visibleItemCount + ")";
                refreshToast();
            }
        });

        mListView.setRecyclerListener(new TwoWayView.RecyclerListener() {
            @Override
            public void onMovedToScrapHeap(View view) {
                Log.d(TAG, "View moved to scrap heap");
            }
        });

        mListView.setAdapter(new TwoWayAdapter( this));
    }

    private void refreshToast() {
        StringBuffer buffer = new StringBuffer();

        if (!TextUtils.isEmpty(mClickMessage)) {
            buffer.append(mClickMessage);
        }

        if (!TextUtils.isEmpty(mScrollMessage)) {
            if (buffer.length() != 0) {
                buffer.append("\n");
            }

            buffer.append(mScrollMessage);
        }

        if (!TextUtils.isEmpty(mStateMessage)) {
            if (buffer.length() != 0) {
                buffer.append("\n");
            }

            buffer.append(mStateMessage);
        }

        mToast.setText(buffer.toString());
        mToast.show();
    }
}
