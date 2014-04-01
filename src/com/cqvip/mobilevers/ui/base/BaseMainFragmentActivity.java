package com.cqvip.mobilevers.ui.base;

import com.cqvip.mobilevers.view.ExamClassfyFragment;
import com.cqvip.mobilevers.view.ExamDetailFragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager.OnBackStackChangedListener;
import android.util.Log;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.widget.Toast;
/**
 * 为了使双击回退键退出程序能够在主页面（四个）有效，所以开辟此类
 * @author ling
 *
 */
public class BaseMainFragmentActivity extends BaseFragmentActivity  implements
OnBackStackChangedListener{
	private static final String TAG = "BaseMainFragmentActivity";
	long exitTime;
	private GestureDetector mGestureDetector;
	public boolean isLeftFragment = true;// 判断viewpager是否滑动到最左边的fragment
	public String tag="";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		mGestureDetector = new GestureDetector(this,
				new MyGestrueListener(this));
		fManager.addOnBackStackChangedListener(this);
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (fManager.getBackStackEntryCount() <= 0) {
			Log.i(TAG, "onKeyDown");
			if (keyCode == KeyEvent.KEYCODE_BACK) {
				exitApp();
				return false;
			}
		}
		return super.onKeyDown(keyCode, event);
	}

	/**
	 * 退出程序
	 */
	private void exitApp() {
		// 判断2次点击事件时间
		if ((System.currentTimeMillis() - exitTime) > 2000) {
			Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
			exitTime = System.currentTimeMillis();
		} else {
			finish();
		}
	}
	
/**
 * 回退 
 * @param v
 */
	public void back(View v){
		fManager.popBackStack();
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
//			Log.e("onFling",
//					"velocityY" + velocityY + "--velocityX" + velocityX
//							+ "  y/x" + (e2.getY() - e1.getY())
//							/ (e2.getX() - e1.getX()));
			Log.i("onFling", isLeftFragment+"");
			if (isLeftFragment
					&& Math.abs(velocityX) > minVelocitx
					&& Math.abs(velocityX) > 1.5 * Math.abs(velocityY)
					&& Math.abs(e2.getY() - e1.getY())
							/ Math.abs(e2.getX() - e1.getX()) < 0.36// 角度<20度
					&& velocityX > 0) {
				back(null);
				return true;
			}
			return false;
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
	
	boolean temp = true;
	/**
	 * 防止viewpager(全真模拟)滑动非第一页时，此时的isLeftFragment已经设为了false，造成后面的页面(试卷详细页面)右滑无响应。
	 */
	@Override
	public void onBackStackChanged() {
		Log.i(TAG, "onBackStackChanged--isLeftFragment:"+isLeftFragment);
		if (tag.equals(ExamDetailFragment.TAG)) {
			if (isLeftFragment) {

			} else {
				temp = isLeftFragment;
				isLeftFragment = true;
			}
		} else if(tag.equals(ExamClassfyFragment.TAG)) {
			if (isLeftFragment) {

			} else {
			isLeftFragment = temp;
			}
		}else{
			isLeftFragment = true;
		}
	}
}
