package com.cqvip.mobilevers.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.cqvip.mobilevers.R;
import com.cqvip.mobilevers.ui.ExamActivity;

/**
 * 交卷显示界面
 * @author luojiang
 *
 */
public class ResultFragment extends Fragment{
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		//View v = inflater.inflate(R.layout.fragment_result_exam, null);
		View view=inflater.inflate(R.layout.fragment_result_exam, container,false);
		
		
		
		
		Button viewAnsewer = (Button) view.findViewById(R.id.btn_view_desc);
		
		viewAnsewer.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				getFragmentManager().popBackStack();
				ExamActivity.isShowAnswer = true;
				((ExamActivity)getActivity()).updateView(1+"");
			}
		});
		
		
		
		
		
		
		
		
		
		
		
		
		return view;
	}

}
