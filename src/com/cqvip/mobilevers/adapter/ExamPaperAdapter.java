package com.cqvip.mobilevers.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cqvip.mobilevers.R;
import com.cqvip.mobilevers.adapter.base.AdapterBase;
import com.cqvip.mobilevers.entity.PaperInfo;

public class ExamPaperAdapter extends AdapterBase<PaperInfo>{

private LayoutInflater inflater;
	
	public ExamPaperAdapter (LayoutInflater inflater,List<PaperInfo> lists) {
		this.inflater = inflater;
		this.mList = lists;
	}
	
	@Override
	protected View getExView(int position, View convertView, ViewGroup parent) {
		TextView title = null;
		TextView year = null;
		TextView addtime = null;
		View v;
		if(convertView==null){
			 v = inflater.inflate(R.layout.item_paper, null);
			title = (TextView) v.findViewById(R.id.txt_paper_title);
			year = (TextView) v.findViewById(R.id.txt_paper_year);
			addtime = (TextView) v.findViewById(R.id.txt_paper_adddate);
			
			title.setText(mList.get(position).getTitile());
			year.setText("试卷年份："+mList.get(position).getPulishyear());
			addtime.setText("更新时间："+mList.get(position).getAdddate());
			
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
