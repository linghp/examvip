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
	
	private static class ViewHolder{
		private TextView title;
		private TextView year;
		private TextView addtime;
	}
	
	
	@Override
	protected View getExView(int position, View convertView, ViewGroup parent) {
		
		ViewHolder holder = null;
		if(convertView==null){
			holder = new ViewHolder();
				convertView = LayoutInflater.from(context).inflate(R.layout.item_paper, null);
				convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
	
		holder.title = (TextView) convertView.findViewById(R.id.txt_paper_title);
		holder.year = (TextView) convertView.findViewById(R.id.txt_paper_year);
		holder.addtime = (TextView) convertView.findViewById(R.id.txt_paper_adddate);

		holder.title.setText(mList.get(position).getExampapername());
		holder.year.setText("时间："+mList.get(position).getCreatetime());
		holder.addtime.setVisibility(View.GONE);
		
		return convertView;
	}
	

	@Override
	protected void onReachBottom() {
		// TODO Auto-generated method stub
		
	}

	
	
	
}
