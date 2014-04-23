package com.cqvip.mobilevers.ui;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.cqvip.mobilevers.MyApplication;
import com.cqvip.mobilevers.R;
import com.cqvip.mobilevers.db.OneLevelType;
import com.cqvip.mobilevers.db.OneLevelTypeDao;
import com.cqvip.mobilevers.ui.base.BaseMainFragmentActivity;
import com.cqvip.mobilevers.view.AClassfyFragment;
import com.cqvip.mobilevers.view.ExamDetailFragment.I_ExamDetail;

/**
 * 考试模块
 * @author luojiang
 *
 */
public class FragmentExamActivity extends BaseMainFragmentActivity implements I_ExamDetail{

	private static final String TAG = "FragmentExamActivity";
	public OneLevelTypeDao oneLevelTypeDao;
	public SQLiteDatabase db;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_stack);
	      if (getSupportFragmentManager().findFragmentByTag(TAG) == null) {
	            final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
	            ft.add(R.id.simple_fragment, new AClassfyFragment(), TAG);
	            ft.commit();
	        }	
	      getDB();
	    }
	
	private void getDB() {
		oneLevelTypeDao=((MyApplication)getApplication()).daoSession.getOneLevelTypeDao();
		db=((MyApplication)getApplication()).db;
	}

	@Override
	public void delfavorite() {
		// TODO Auto-generated method stub
		
	}
	
	

//	@Override
//	public void onItemSelected(String id) {
//		//传递参数
//		Fragment newFragment = BClassfyFragment.newInstance(id);
//		 addFragmentToStack(newFragment,id);
//		
//	}

	   
//	private void addFragmentToStack(Fragment newFragment,String id) {
//		// mStackLevel++;
//		//	判断下是那个fragment
//
//	        // Instantiate a new fragment.
//
//	        // Add the fragment to the activity, pushing this transaction
//	        // on to the back stack.
//	        replaceContainer(newFragment);
//		
//	}
//	private void replaceContainer(Fragment newFragment) {
//		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//		ft.replace(android.R.id.content, newFragment);
//		ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
//		ft.addToBackStack(null);
//		ft.commit();
//	}
//	@Override
//	public void onItemNextSelected(String id) {
//		Fragment newFragment = CClassfyFragment.newInstance(id);
//		 addFragmentToStack(newFragment,id);
//	}
//	@Override
//	public void onItemCNextSelected(String id) {
//		Fragment newFragment = DClassfyFragment.newInstance(id);
//		 addFragmentToStack(newFragment,id);
//	}
//	@Override
//	public void onItemDNextSelected(String id) {
//		//Toast.makeText(this, "试卷列表", 1).show();
//		startActivity(new Intent(this,ExamClassfyActivity.class));
//	}

//	@Override
//	public void onItemDNextSelected(String id) {
//		startActivity(new Intent(FragmentExamActivity.this,ExamClassfyActivity.class));
//		overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
//	}
	
//	@Override
//	protected void onDestroy() {
//		// TODO Auto-generated method stub
//		super.onDestroy();
//		android.os.Process.killProcess(android.os.Process.myPid());
//	}
//	
//	
//	@Override
//	public boolean onKeyDown(int keyCode, KeyEvent event) {
//		if (keyCode == KeyEvent.KEYCODE_BACK) {
//			exit("再按一次退出程序");
//			return false;
//		}
//		return super.onKeyDown(keyCode, event);
//	}
//	
//	private static boolean isExit = false;
//
//	Handler mHandler = new Handler() {
//
//		@Override
//		public void handleMessage(Message msg) {
//			super.handleMessage(msg);
//			isExit = false;
//		}
//	};
//	
//	private void exit(String tips) {
//		if (!isExit) {
//			isExit = true;
//			Toast.makeText(getApplicationContext(), tips, Toast.LENGTH_SHORT)
//					.show();
//			// 利用handler延迟发送更改状态信息
//			mHandler.sendEmptyMessageDelayed(0, 2000);
//		} else {
//			finish();
//			// System.exit(0);
//		}
//	}
}
