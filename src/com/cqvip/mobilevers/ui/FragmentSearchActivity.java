package com.cqvip.mobilevers.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.cqvip.mobilevers.R;
import com.cqvip.mobilevers.entity.PaperInfo;
import com.cqvip.mobilevers.ui.base.BaseMainFragmentActivity;
import com.cqvip.mobilevers.view.ExamDetailFragment;
import com.cqvip.mobilevers.view.SearchExamFragment;

/**
 * 搜索模块
 * @author luojiang
 *
 */
public class FragmentSearchActivity extends BaseMainFragmentActivity {

	private static final String TAG = "FragmentSearchActivity";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 if (getSupportFragmentManager().findFragmentByTag(TAG) == null) {
	            final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
	            ft.add(android.R.id.content, new SearchExamFragment(), TAG);
	            ft.commit();
	        }	
	     }

//	  public void mypassedexam(View v){
//			Fragment newFragment = new AboutFragment();
//			 addFragmentToStack(newFragment);
//			
//		}
//		private void addFragmentToStack(Fragment newFragment) {
//			// mStackLevel++;
//			//	判断下是那个fragment
//
//		        // Instantiate a new fragment.
//
//		        // Add the fragment to the activity, pushing this transaction
//		        // on to the back stack.
//		        replaceContainer(newFragment);
//			
//		}
//		private void replaceContainer(Fragment newFragment) {
//			FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//			ft.replace(android.R.id.content, newFragment);
//			ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
//			ft.addToBackStack(null);
//			ft.commit();
//		}
	
	public void onItemNextSelected(PaperInfo info) {
		if(info!=null){
		Fragment newFragment = ExamDetailFragment.newInstance(info.getName(),info.getSubjectid());
		this.addFragmentToStack(newFragment, android.R.id.content);
		}else{
			return;
		}
	}
	
	
}
