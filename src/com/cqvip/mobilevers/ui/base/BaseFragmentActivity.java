package com.cqvip.mobilevers.ui.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.cqvip.mobilevers.exception.ErrorVolleyThrow;
import com.cqvip.mobilevers.widget.CustomProgressDialog;
import com.umeng.analytics.MobclickAgent;

public class BaseFragmentActivity extends FragmentActivity {

	private static final String TAG = "BaseFragmentActivity";
	public FragmentManager fManager;
	
	protected RequestQueue mQueue;
	protected ErrorVolleyThrow volleyErrorListener;
	protected CustomProgressDialog customProgressDialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// requestWindowFeature(Window.FEATURE_NO_TITLE);
		fManager = getSupportFragmentManager();
		mQueue = Volley.newRequestQueue(this);
		volleyErrorListener = new ErrorVolleyThrow(this, null);
		if(customProgressDialog==null){
			customProgressDialog=CustomProgressDialog.createDialog(this);
		}
	}



	public void addFragmentToStack(Fragment newFragment, int layoutid,String tag) {
		FragmentTransaction ft = fManager.beginTransaction();
		ft.replace(layoutid, newFragment,tag);
		ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
		ft.addToBackStack(null);
		ft.commit();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		MobclickAgent.onResume(this);
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		MobclickAgent.onPause(this);
	}
}
