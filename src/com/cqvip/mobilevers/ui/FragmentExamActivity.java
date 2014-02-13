package com.cqvip.mobilevers.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Toast;

import com.cqvip.mobilevers.view.AClassfyFragment;
import com.cqvip.mobilevers.view.BClassfyFragment;
import com.cqvip.mobilevers.view.CClassfyFragment;
import com.cqvip.mobilevers.view.DClassfyFragment;

public class FragmentExamActivity extends FragmentActivity implements  AClassfyFragment.Callbacks,BClassfyFragment.NextCallbacks,
CClassfyFragment.NextCallbacks,DClassfyFragment.NextCallbacks{

	private static final String TAG = "FragmentExamActivity";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.fragment_stack);
	      if (getSupportFragmentManager().findFragmentByTag(TAG) == null) {
	            final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
	            ft.add(android.R.id.content, new AClassfyFragment(), TAG);
	            ft.commit();
	        }	
		
	    }

	   
	@Override
	public void onItemSelected(String id) {
		//传递参数
		Fragment newFragment = BClassfyFragment.newInstance(id);
		 addFragmentToStack(newFragment,id);
		
	}
	private void addFragmentToStack(Fragment newFragment,String id) {
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
	@Override
	public void onItemNextSelected(String id) {
		Fragment newFragment = CClassfyFragment.newInstance(id);
		 addFragmentToStack(newFragment,id);
	}
	@Override
	public void onItemCNextSelected(String id) {
		Fragment newFragment = DClassfyFragment.newInstance(id);
		 addFragmentToStack(newFragment,id);
	}
	@Override
	public void onItemDNextSelected(String id) {
		//Toast.makeText(this, "试卷列表", 1).show();
		startActivity(new Intent(this,ExamClassfyActivity.class));
	}


	@Override
	public void onExamListSelect(String id) {
		// TODO Auto-generated method stub
		
	}
	
}
