package com.cqvip.mobilevers.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cqvip.mobilevers.R;
import com.cqvip.mobilevers.adapter.base.AdapterBase;
import com.cqvip.mobilevers.entity.ExamInfo;

public class ExamPaperAdapter extends AdapterBase<ExamInfo>{

private LayoutInflater inflater;
	
	public ExamPaperAdapter (LayoutInflater inflater,List<ExamInfo> lists) {
		this.inflater = inflater;
		this.mList = lists;
	}
	
	@Override
	protected View getExView(int position, View convertView, ViewGroup parent) {
		TextView title = null;
		TextView count = null;
		View v;
		if(convertView==null){
			 v = inflater.inflate(R.layout.item_paper, null);
			title = (TextView) v.findViewById(R.id.txt_item_title);
			count = (TextView) v.findViewById(R.id.txt_item_count);
			title.setText(mList.get(position).getTitle());
			count.setText(mList.get(position).getCount());
		}else{
			v = convertView;
		}
		
		return v;
	}

	@Override
	protected void onReachBottom() {
		// TODO Auto-generated method stub
		
	}

	
	
	
}
