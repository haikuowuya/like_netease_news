package com.roboo.like.netease.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.ListView;

public class BorderListView extends ListView
{

	public BorderListView(Context context) {  
        super(context);  
    }  
      
    public BorderListView(Context context, AttributeSet attrs)  
    {  
        super(context, attrs);  
    }  
  
      
    @Override  
    protected void onDraw(Canvas canvas) {  
  
        float width = getWidth();  
        float height= getHeight();  
        int lineWidth = 10; // 线宽十个像素   
        int grayColor = Color.GRAY;  
        Paint mLinePaint = new Paint();  
        mLinePaint.setColor(grayColor);  
        mLinePaint.setStyle(Paint.Style.STROKE);  
          
        mLinePaint.setAntiAlias(true);  
        mLinePaint.setStrokeWidth(lineWidth);  
  
        // 画四周的边框 注意下面的 lineWidth/2 不加的话四周的线可能不一样粗    
        canvas.drawLine(0f, 0+lineWidth/2, width, 0+lineWidth/2, mLinePaint);  
        canvas.drawLine(width-lineWidth/2, 0, width-lineWidth/2, height, mLinePaint);  
        canvas.drawLine(width-lineWidth/2, height-lineWidth/2, 0, height-lineWidth/2, mLinePaint);  
        canvas.drawLine(0+lineWidth/2, height, 0+lineWidth/2, 0,mLinePaint);  
        super.onDraw(canvas);  
          
    }  
  
}  
