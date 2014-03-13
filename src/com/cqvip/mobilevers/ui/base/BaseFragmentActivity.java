package com.cqvip.mobilevers.ui.base;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.cqvip.mobilevers.exception.ErrorVolleyThrow;
import com.cqvip.mobilevers.widget.CustomProgressDialog;

import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentManager.OnBackStackChangedListener;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

public class BaseFragmentActivity extends FragmentActivity implements
		OnBackStackChangedListener {

	private static final String TAG = "BaseFragmentActivity";
	protected GestureDetector mGestureDetector;
	protected FragmentManager fManager;
	protected boolean isLeftFragment = true;// 判断viewpager是否滑动到最左边的fragment
	
	protected RequestQueue mQueue;
	protected ErrorVolleyThrow volleyErrorListener;
	protected CustomProgressDialog customProgressDialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// requestWindowFeature(Window.FEATURE_NO_TITLE);
		mGestureDetector = new GestureDetector(this,
				new MyGestrueListener(this));
		fManager = getSupportFragmentManager();
		fManager.addOnBackStackChangedListener(this);
		mQueue = Volley.newRequestQueue(this);
		volleyErrorListener = new ErrorVolleyThrow(this, null);
		if(customProgressDialog==null){
			customProgressDialog=CustomProgressDialog.createDialog(this);
		}
	}

	class MyGestrueListener extends SimpleOnGestureListener {
		private Context mContext;

		MyGestrueListener(Context context) {
			mContext = context;
		}

		private int verticalMinDistance = 150;
		private int horizontalMinDistance = 200;
		private int minVelocitx = 500;

		// private int minVelocity = 5000;

		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {
			Log.e("onFling",
					"velocityY" + velocityY + "--velocityX" + velocityX
							+ "  y/x" + (e2.getY() - e1.getY())
							/ (e2.getX() - e1.getX()));
			if (isLeftFragment
					&& Math.abs(velocityX) > minVelocitx
					&& Math.abs(velocityX) > 1.5 * Math.abs(velocityY)
					&& Math.abs(e2.getY() - e1.getY())
							/ Math.abs(e2.getX() - e1.getX()) < 0.36// 角度<20度
					&& velocityX > 0) {
				backpage();
				return true;
			}
			return false;
		}
	}

	protected void backpage() {
		if (fManager.getBackStackEntryCount() > 0) {
			fManager.popBackStack();
		} else {
			finish();
			// overridePendingTransition(R.anim.slide_left_in,R.anim.slide_right_out);
		}
	}
	
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		if (mGestureDetector.onTouchEvent(ev)) {
			return true;
		} else {
			boolean temp = super.dispatchTouchEvent(ev);
			return temp;
		}
	}

	public void addFragmentToStack(Fragment newFragment, int layoutid) {
		FragmentTransaction ft = fManager.beginTransaction();
		ft.replace(layoutid, newFragment);
		ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
		ft.addToBackStack(null);
		ft.commit();
	}

	// 防止viewpager滑动非第一页时，isLeftFragment设为false，造成后面的页面右滑无响应。
	boolean temp = true;

	@Override
	public void onBackStackChanged() {
		if (fManager.getBackStackEntryCount() > 0) {
			if (isLeftFragment) {

			} else {
				temp = isLeftFragment;
				isLeftFragment = true;
			}
		} else {
			isLeftFragment = temp;
		}
	}
}
