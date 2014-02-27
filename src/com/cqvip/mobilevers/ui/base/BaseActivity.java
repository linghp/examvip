package com.cqvip.mobilevers.ui.base;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.Window;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.toolbox.Volley;
import com.cqvip.mobilevers.exception.ErrorVolleyThrow;
import com.cqvip.mobilevers.widget.CustomProgressDialog;


public class BaseActivity extends Activity {

	private static final String TAG = "BaseActivity";
	protected RequestQueue mQueue;
	protected ErrorListener volleyErrorListener;
	protected CustomProgressDialog customProgressDialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		mQueue=Volley.newRequestQueue(this);
		volleyErrorListener = new  ErrorVolleyThrow(this, null);
		if(customProgressDialog==null){
			customProgressDialog=CustomProgressDialog.createDialog(this);
		}
	}

}
