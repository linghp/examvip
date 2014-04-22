package com.cqvip.mobilevers.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.cqvip.mobilevers.R;
import com.cqvip.mobilevers.adapter.base.AdapterBase;
import com.cqvip.mobilevers.config.ConstantValues;
import com.cqvip.mobilevers.entity.DoingExamPaper;

/**
 * 做过的试卷列表
 * @author luojiang
 *
 */
public class DoingExamPaperListAdapter extends AdapterBase<DoingExamPaper>{

private Context	context;
private OnClickListener onClickListener;
private int status;	
	public DoingExamPaperListAdapter (Context context,List<DoingExamPaper> lists,int status,OnClickListener onClickListener) {
		this.context = context;
		this.onClickListener=onClickListener;
		this.mList = lists;
		this.status = status;
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
		private ImageView del;
	}
	
	
	@Override
	protected View getExView(int position, View convertView, ViewGroup parent) {
		
		ViewHolder holder = null;
		if(convertView==null){
			holder = new ViewHolder();
				convertView = LayoutInflater.from(context).inflate(R.layout.item_paper_del, null);
				holder.title = (TextView) convertView.findViewById(R.id.txt_paper_title);
				holder.year = (TextView) convertView.findViewById(R.id.txt_paper_year);
				holder.addtime = (TextView) convertView.findViewById(R.id.txt_paper_adddate);
				holder.del = (ImageView) convertView.findViewById(R.id.img_del);
				convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
	


		holder.title.setText(mList.get(position).getExampapername());
		holder.year.setText("时间："+mList.get(position).getCreatetime());
		holder.del.setTag(position);
		holder.del.setOnClickListener(onClickListener);
		if(status == ConstantValues.SHOWDOING){
			holder.addtime.setVisibility(View.GONE);
		}else{
		holder.addtime.setText("分数："+mList.get(position).getScore());
		}
		
		return convertView;
	}
	

	@Override
	protected void onReachBottom() {
		// TODO Auto-generated method stub
		
	}

	
	
	
}
