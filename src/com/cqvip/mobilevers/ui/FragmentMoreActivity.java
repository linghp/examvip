package com.cqvip.mobilevers.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.cqvip.mobilevers.view.AboutFragment;
import com.cqvip.mobilevers.view.MoreMenuFragment;
import com.cqvip.mobilevers.view.UserSuggestFragment;

public class FragmentMoreActivity extends FragmentActivity {

	private static final String TAG = "FragmentMoreActivity";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.main_tab_more);
//		 
		 if (getSupportFragmentManager().findFragmentByTag(TAG) == null) {
	            final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
	            ft.add(android.R.id.content, new MoreMenuFragment(), TAG);
	            ft.commit();
	        }	
		 
	        }

	  public void about(View v){
			Fragment newFragment = new AboutFragment();
			 addFragmentToStack(newFragment);
			
		}
	  public void suggest(View v){
		  Fragment newFragment = new UserSuggestFragment();
		  addFragmentToStack(newFragment);
		  
	  }
		private void addFragmentToStack(Fragment newFragment) {
			// mStackLevel++;
			//	判断下是那个fragment

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
