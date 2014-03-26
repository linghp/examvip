package com.cqvip.mobilevers.ui.base;

import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;
/**
 * 为了使双击回退键退出程序能够在主页面（四个）有效，所以开辟此类
 * @author ling
 *
 */
public class BaseMainFragmentActivity extends BaseFragmentActivity {
	private static final String TAG = "BaseMainFragmentActivity";
	long exitTime;
	
	@Override
	protected void backpage() {
		if (fManager.getBackStackEntryCount() > 0) {
			fManager.popBackStack();
		} else {
			//finish();
			// overridePendingTransition(R.anim.slide_left_in,R.anim.slide_right_out);
		}
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
}
