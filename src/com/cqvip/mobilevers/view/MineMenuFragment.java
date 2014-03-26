package com.cqvip.mobilevers.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cqvip.mobilevers.R;
/**
 * 我的模块
 * @author ling
 *
 */
public class MineMenuFragment extends Fragment implements OnClickListener{

	
	private TextView tv_done_paper;//做过
	private TextView tv_doing_paper;//正在做
	private TextView tv_favorite_paper;//收藏的
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View v = inflater.inflate(R.layout.main_tab_mine, null);
		tv_done_paper = (TextView) v.findViewById(R.id.tv_done_paper);
		tv_doing_paper = (TextView) v.findViewById(R.id.tv_doing_paper);
		tv_favorite_paper = (TextView) v.findViewById(R.id.tv_favorite_paper);
		
//		tv_done_paper.setOnClickListener(this);
//		tv_doing_paper.setOnClickListener(this);
//		tv_favorite_paper.setOnClickListener(this);
//		
		return v;
	}


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_done_paper:
			//
			
			
			break;
		case R.id.tv_doing_paper:
			
			break;
		case R.id.tv_favorite_paper:
			
			break;
		}
		
	}
		
}
