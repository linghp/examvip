package com.cqvip.mobilevers.view;

import com.cqvip.mobilevers.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class UserSuggestFragment extends Fragment{

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View v = inflater.inflate(R.layout.more_usersuggest, null);
		TextView tv = (TextView) v.findViewById(R.id.tv_show_title);
		tv.setText("用户反馈");
		
		
		return v;
	}
		
}
