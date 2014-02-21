package com.cqvip.mobilevers.view;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.cqvip.mobilevers.R;
import com.cqvip.mobilevers.ui.ExamActivity;
import com.cqvip.mobilevers.ui.base.BaseFragment;

public class ExamDetailFragment extends BaseFragment implements OnClickListener{

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
		
		View startExam_btn=(Button) view.findViewById(R.id.btn_exam);
		startExam_btn.setOnClickListener(this);
		
		return view;
	}

	@Override
	public void onClick(View v) {
		startActivity(new Intent(getActivity(),ExamActivity.class));
	}
	
	  
	 @Override
	public void onDestroyView() {
		 Log.i("ExamDetailFragment", "onDestroyView");
		super.onDestroyView();
	}
	 @Override
	public void onDestroy() {
		 Log.i("ExamDetailFragment", "onDestroy");
		super.onDestroy();
	}
	  @Override
	public void onDetach() {
		  Log.i("ExamDetailFragment", "onDetach");
		super.onDetach();
	}
}
	