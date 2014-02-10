package com.cqvip.mobilevers.ui.base;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;


public class BaseActivity extends Activity {

	private static final String TAG = "BaseActivity";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
	}

}
