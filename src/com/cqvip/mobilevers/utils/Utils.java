package com.cqvip.mobilevers.utils;

import com.cqvip.mobilevers.R;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

public class Utils {

	public static String checkUserid(Context context) {
		SharedPreferences localUsers = context.getSharedPreferences(
				"mobilevers", context.MODE_PRIVATE);
		String userid = localUsers.getString("userid", "0");
		if (!userid.equals("0")) {
			return userid;
		}
		Toast.makeText(context,context.getResources().getString(R.string.hellotome), 0).show();
		return null;
	}

    /** 
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素) 
     */  
    public static int dip2px(Context context, float dpValue) {  
        final float scale = context.getResources().getDisplayMetrics().density;  
        return (int) (dpValue * scale + 0.5f);  
    }  
  
    /** 
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp 
     */  
    public static int px2dip(Context context, float pxValue) {  
        final float scale = context.getResources().getDisplayMetrics().density;  
        return (int) (pxValue / scale + 0.5f);  
    }  
    
}
