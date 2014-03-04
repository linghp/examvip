package com.cqvip.mobilevers.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.cqvip.mobilevers.R;
import com.cqvip.mobilevers.adapter.base.AdapterBase;
import com.cqvip.mobilevers.db.OneLevelType;

public class ExamAClassfyAdapter extends AdapterBase<OneLevelType> implements OnClickListener {

	private Context context;
	
	public ExamAClassfyAdapter (Context context,List<OneLevelType> lists) {
		this.context = context;
		this.mList = lists;
	}
	
	@Override
	protected View getExView(int position, View convertView, ViewGroup parent) {
		TextView title = null;
		TextView count = null;
		View v;
		ViewGroup viewGroup;
		if(convertView==null){
			 v = LayoutInflater.from(context).inflate(R.layout.item_a_classfy, null);
			 viewGroup=(ViewGroup) v.findViewById(R.id.ll_classifyexamlist);
			 viewGroup.setOnClickListener(this);
			 viewGroup.setTag(position);
		}else{
			v = convertView;
		}
		title = (TextView) v.findViewById(R.id.txt_item_title);
		count = (TextView) v.findViewById(R.id.tx_arrow);
		title.setText(mList.get(position).getTitle());
		count.setText(mList.get(position).getCount()+"");
		return v;
	}

	@Override
	protected void onReachBottom() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onClick(View v) {
		Toast.makeText(context, "click"+v.getTag(), 1).show();
	}

}
