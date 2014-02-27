package com.cqvip.mobilevers.http;

import android.content.Context;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RetryPolicy;
import com.cqvip.mobilevers.config.Configs;

public class HttpUtils {

	// 判断是否有网络

	// 判断mobile网络是否可用
	public static boolean isMobileDataEnable(Context context) {
		String TAG = "httpUtils.isMobileDataEnable()";
		try {
			return NetWorkHelper.isMobileDataEnable(context);
		} catch (Exception e) {
			Log.e(TAG, e.getMessage());
			e.printStackTrace();
			return false;
		}
	}

	// 判断wifi网络是否可用
	public static boolean isWifiDataEnable(Context context) {
		String TAG = "httpUtils.isWifiDataEnable()";
		try {
			return NetWorkHelper.isWifiDataEnable(context);
		} catch (Exception e) {
			Log.e(TAG, e.getMessage());
			e.printStackTrace();
			return false;
		}
	}


	// 判断是否为漫游
	public static boolean isNetworkRoaming(Context context) {
		return NetWorkHelper.isNetworkRoaming(context);
	}
	
	/**
	 * vollery设置超时20s
	 * @return
	 */
	public static RetryPolicy setTimeout(){
		RetryPolicy retryPolicy = new DefaultRetryPolicy(Configs.SOCKET_TIMEOUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
		return retryPolicy;
	}
}
