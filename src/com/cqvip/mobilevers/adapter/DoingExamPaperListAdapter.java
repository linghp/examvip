package com.cqvip.mobilevers.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cqvip.mobilevers.R;
import com.cqvip.mobilevers.adapter.base.AdapterBase;
import com.cqvip.mobilevers.entity.DoingExamPaper;

/**
 * 做过的试卷列表
 * @author luojiang
 *
 */
public class DoingExamPaperListAdapter extends AdapterBase<DoingExamPaper>{

private Context	context;;
	
	public DoingExamPaperListAdapter (Context context,List<DoingExamPaper> lists) {
		this.context = context;
		this.mList = lists;
	}
	
	/**
	 * 增加更多数据
	 * 
	 * @param moreStatus
	 */
	public void addMoreData(List<DoingExamPaper> moreStatus) {
		this.mList.addAll(moreStatus);// 把新数据增加到原有集合
		this.notifyDataSetChanged();
	}
	
	
	@Override
	protected View getExView(int position, View convertView, ViewGroup parent) {
		TextView title = null;
		TextView year = null;
		TextView addtime = null;
		View v;
		if(convertView==null){
			 v = LayoutInflater.from(context).inflate(R.layout.item_paper, null);
			//parent.getTag();
		}else{
			v = convertView;
		}
		title = (TextView) v.findViewById(R.id.txt_paper_title);
		year = (TextView) v.findViewById(R.id.txt_paper_year);
		addtime = (TextView) v.findViewById(R.id.txt_paper_adddate);
		
		title.setText(mList.get(position).getExampapername());
		year.setText("时间："+mList.get(position).getCreatetime());
		addtime.setVisibility(View.GONE);
		
		return v;
	}
	

	@Override
	protected void onReachBottom() {
		// TODO Auto-generated method stub
		
	}

	
	
	
}
