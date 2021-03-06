package com.cqvip.mobilevers.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cqvip.mobilevers.R;
import com.cqvip.mobilevers.adapter.base.AdapterBase;
import com.cqvip.mobilevers.entity.PaperInfo;

public class ExamPaperAdapter extends AdapterBase<PaperInfo>{

private Context	context;;
	
	public ExamPaperAdapter (Context context,List<PaperInfo> lists) {
		this.context = context;
		this.mList = lists;
	}
	
	/**
	 * 增加更多数据
	 * 
	 * @param moreStatus
	 */
	public void addMoreData(List<PaperInfo> moreStatus) {
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
		holder.title.setText(mList.get(position).getName());
		holder.year.setText("试卷年份："+mList.get(position).getPulishyear());
		holder.addtime.setText("更新时间："+mList.get(position).getAdddate());
		
		return convertView;
	}
	@Override
	protected void onReachBottom() {
		
		
	}
	
}
