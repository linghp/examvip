package com.cqvip.mobilevers.view;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cqvip.mobilevers.R;

public class ExamFragment extends Fragment{
	int mNum;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_exampager, container,
				false);
		View tv = v.findViewById(R.id.text);
		((TextView) tv).setText("Fragment #" + mNum);
		tv.setBackgroundDrawable(getResources().getDrawable(
				android.R.drawable.gallery_thumb));

		return v;
	}

	public static ExamFragment newInstance(int num, Context context) {

		ExamFragment f = (ExamFragment) ExamFragment.instantiate(context,
				ExamFragment.class.getName());
		// ExamFragment f =new ExamFragment();
		f.mNum = num;
		// Supply num input as an argument.
		Bundle args = new Bundle();
		args.putInt("num", num);
		f.setArguments(args);

		return f;
	}
}
