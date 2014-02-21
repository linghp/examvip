package com.cqvip.mobilevers.view;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.cqvip.mobilevers.R;
import com.cqvip.mobilevers.entity.PaperInfo;
import com.cqvip.mobilevers.ui.ExamActivity;
import com.cqvip.mobilevers.ui.base.BaseFragment;

public class ExamDetailFragment extends BaseFragment implements OnClickListener{

	private TextView  tyear,tadddate,ttotal,tscroe,ttime,tsize;
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
		
		Bundle bundle = getArguments();
		PaperInfo info = (PaperInfo) bundle.getSerializable("paper");
		
		tyear = (TextView) view.findViewById(R.id.txt_p_year);
		tadddate = (TextView) view.findViewById(R.id.txt_p_adddata);
		ttotal = (TextView) view.findViewById(R.id.txt_p_total);
		tscroe = (TextView) view.findViewById(R.id.txt_p_score);
		ttime = (TextView) view.findViewById(R.id.txt_p_time);
		tsize = (TextView) view.findViewById(R.id.txt_p_size);
			
		tyear.setText(info.getPulishyear());
		tadddate.setText(info.getAdddate());
		ttotal.setText(info.getItemcount());
		tscroe.setText(info.getScore());
		ttime.setText(info.getSpenttime());
		tsize.setText(info.getSize()/1024+"KB");
		
		return view;
	}

	@Override
	public void onClick(View v) {
		startActivity(new Intent(getActivity(),ExamActivity.class));
	}
}
	