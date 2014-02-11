package com.cqvip.mobilevers.view;

import android.widget.GridView;

public class AnswerscardGridView extends GridView  
{  
    public AnswerscardGridView(android.content.Context context,  
            android.util.AttributeSet attrs)  
    {  
        super(context, attrs);  
    }  

  /** 
     * ���ò����� 
     */  
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec)  
    {  
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,  
                MeasureSpec.AT_MOST);  
        super.onMeasure(widthMeasureSpec, expandSpec);  
  
    }  
  
}  
