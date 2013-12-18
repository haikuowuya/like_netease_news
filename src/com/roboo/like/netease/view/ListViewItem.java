package com.roboo.like.netease.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ListViewItem extends RelativeLayout{  
  
    private View viewHolder;  
  
    private TextView tvEventName;  
   private Context context;
   
      
    public ListViewItem(Context context) {  
        super(context);  
        LayoutInflater flater = LayoutInflater.from(context);  
        viewHolder = flater.inflate(android.R.layout.simple_list_item_1, this);  
        getViewAndSetClick();  
        this.context = context;  
    }  
      
    private void getViewAndSetClick()  
    {  
  
        tvEventName = (TextView)viewHolder.findViewById(android.R.id.text1);  
         
  
    }  
      
    public void setEventName(String name)  
    {  
        tvEventName.setText(name);  
    }  
      
    public void updateView()  
    {  
        this.postInvalidate();  
    }  
  
  
    @Override  
    protected void dispatchDraw(Canvas canvas) {  
        super.dispatchDraw(canvas);   
        int grayColor = Color.GRAY;  
        int redColor = Color.parseColor("#FF0000");
        Paint mLinePaint = new Paint();  
        mLinePaint.setColor(redColor);  
        mLinePaint.setStyle(Paint.Style.STROKE);  
          
        mLinePaint.setStrokeWidth(2);  
          
        //画两条直线   
//        canvas.drawLine(leftFramepos+20, 0f, leftFramepos+20, getHeight(), mLinePaint);  
//        canvas.drawLine(leftFramepos+25, 0f, leftFramepos+25, getHeight(), mLinePaint);  
      
        // 画虚线   
        mLinePaint.setColor(grayColor);  
        DashPathEffect effect = new DashPathEffect(new float[] { 5,5, 5, 5, 5}, 3);  
        mLinePaint.setAntiAlias(true);  
        mLinePaint.setPathEffect(effect);  
  
        canvas.drawLine(0, getHeight(), getWidth(), getHeight(), mLinePaint);  
  
    }  
      
      
  
}  
