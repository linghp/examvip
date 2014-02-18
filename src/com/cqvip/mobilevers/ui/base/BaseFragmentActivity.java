package com.cqvip.mobilevers.ui.base;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.Window;

public class BaseFragmentActivity extends FragmentActivity {

	private static final String TAG = "BaseFragmentActivity";
	protected GestureDetector mGestureDetector;
	private FragmentManager fManager;
	protected boolean isLeftFragment=true;//判断viewpager是否滑动到最左边的fragment

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		mGestureDetector = new GestureDetector(this,
				new MyGestrueListener(this));
		fManager=getSupportFragmentManager();
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
			if (isLeftFragment&&Math.abs(velocityX) > minVelocitx
					&& Math.abs(velocityX) > 1.5 * Math.abs(velocityY)
					&& Math.abs(e2.getY() - e1.getY())
							/ Math.abs(e2.getX() - e1.getX()) < 0.36// 角度<20度
					&& velocityX > 0) {
				backpage();
				return true;
			}
			return false;
		}

		private void backpage() {
			if(fManager.getBackStackEntryCount()>0){
				fManager.popBackStack();
			}else{
				finish();
			}
		}
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		if (mGestureDetector.onTouchEvent(ev)) {
			Log.i("mylinearlayout", "dispatchTouchEvent_true");
			return true;
		} else {
			boolean temp = super.dispatchTouchEvent(ev);
			Log.i(TAG, "dispatchTouchEvent_" + temp);
			return temp;
		}
	}
	
}
