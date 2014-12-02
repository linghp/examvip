package com.cqvip.mobilevers.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Toast;

import com.cqvip.mobilevers.ui.base.BaseMainFragmentActivity;
import com.cqvip.mobilevers.view.AboutFragment;
import com.cqvip.mobilevers.view.MoreMenuFragment;
import com.cqvip.mobilevers.view.UserSuggestFragment;
import com.cqvip.mobilevers.widget.CustomProgressDialog;
import com.umeng.update.UmengUpdateAgent;
import com.umeng.update.UmengUpdateListener;
import com.umeng.update.UpdateResponse;
import com.umeng.update.UpdateStatus;

/**
 * 更多
 * 
 * @author luojiang
 * 
 */
public class FragmentMoreActivity extends BaseMainFragmentActivity {

	private static final String TAG = "FragmentMoreActivity";
	private CustomProgressDialog customProgressDialog;
	private Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = this;
		customProgressDialog = CustomProgressDialog.createDialog(this);
		if (getSupportFragmentManager().findFragmentByTag(TAG) == null) {
			final FragmentTransaction ft = getSupportFragmentManager()
					.beginTransaction();
			ft.add(android.R.id.content, new MoreMenuFragment(), TAG);
			ft.commit();
		}

	}

	public void about(View v) {
		Fragment newFragment = new AboutFragment();
		addFragmentToStack(newFragment);

	}

	public void suggest(View v) {
		Fragment newFragment = new UserSuggestFragment();
		addFragmentToStack(newFragment);

	}

	public void update(View v) {
		// customProgressDialog.show();
		// new AsyncTask<Void, Void, Void>() {
		// protected Void doInBackground(Void... params) {
		//
		// try {
		// Thread.sleep(2000);
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		// return null;
		// }
		//
		// @Override
		// protected void onPostExecute(Void result) {
		// if(customProgressDialog!=null&&customProgressDialog.isShowing()){
		// customProgressDialog.dismiss();
		// }
		// Toast.makeText(context, "当前是最新版本", Toast.LENGTH_SHORT).show();
		//
		// }
		//
		// }.execute(null, null);
		//
		// Log.i(TAG, "update");
		UmengUpdateAgent.setUpdateAutoPopup(false);
		UmengUpdateAgent.setUpdateListener(new UmengUpdateListener() {
			@Override
			public void onUpdateReturned(int updateStatus,
					UpdateResponse updateInfo) {
				switch (updateStatus) {
				case UpdateStatus.Yes: // has update
					UmengUpdateAgent.showUpdateDialog(context, updateInfo);
					break;
				case UpdateStatus.No: // has no update
					Toast.makeText(context, "没有更新", Toast.LENGTH_SHORT).show();
					break;
				case UpdateStatus.NoneWifi: // none wifi
					Toast.makeText(context, "没有wifi连接， 只在wifi下更新",
							Toast.LENGTH_SHORT).show();
					break;
				case UpdateStatus.Timeout: // time out
					Toast.makeText(context, "超时", Toast.LENGTH_SHORT).show();
					break;
				}
			}
		});
		UmengUpdateAgent.update(this);
		UmengUpdateAgent.forceUpdate(getApplicationContext());
	}

	private void addFragmentToStack(Fragment newFragment) {
		// mStackLevel++;
		// 判断下是那个fragment

		// Instantiate a new fragment.

		// Add the fragment to the activity, pushing this transaction
		// on to the back stack.
		replaceContainer(newFragment);

	}

	private void replaceContainer(Fragment newFragment) {
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ft.replace(android.R.id.content, newFragment);
		ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
		ft.addToBackStack(null);
		ft.commit();
	}

}
