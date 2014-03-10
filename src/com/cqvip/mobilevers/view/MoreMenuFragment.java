package com.cqvip.mobilevers.view;

import com.cqvip.mobilevers.R;
import com.cqvip.mobilevers.ui.ExamActivity;
import com.cqvip.mobilevers.widget.Switch;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class MoreMenuFragment extends Fragment{
	 private Switch mSwitch;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View v = inflater.inflate(R.layout.main_tab_more, null);
		
		 mSwitch = (Switch)v.findViewById(R.id.switchBtn);
		 mSwitch.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				//Log.i("MoreMenuFragment", "onCheckedChanged");
				if(isChecked){
					ExamActivity.isnight=true;
				}else{
					ExamActivity.isnight=false;
				}
			}
		});
		return v;
	}
		
	
}
