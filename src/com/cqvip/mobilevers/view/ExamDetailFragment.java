package com.cqvip.mobilevers.view;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cqvip.mobilevers.R;
import com.cqvip.mobilevers.ui.ExamActivity;
import com.cqvip.mobilevers.ui.base.BaseFragment;

public class ExamDetailFragment extends BaseFragment {

	public void begintest(View v){
		startActivity(new Intent(getActivity(),ExamActivity.class));
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if(reuseView()) {
			return view;
		}
		//Log.i(TAG, "onCreateView");
		view = inflater.inflate(R.layout.paper_info, null);
		final ActionBar actionBar = getActivity().getActionBar();
		actionBar.setNavigationMode(ActionBar.DISPLAY_SHOW_TITLE);
		actionBar.setTitle("试卷详情");
		// Show the Up button in the action bar.
		actionBar.setDisplayHomeAsUpEnabled(true);
		return view;
	}
}
	