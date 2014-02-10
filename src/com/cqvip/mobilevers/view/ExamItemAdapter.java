package com.cqvip.mobilevers.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cqvip.mobilevers.R;

public class ExamItemAdapter extends BaseAdapter {

	private String[] datas;
	private Context context;
	public ExamItemAdapter(Context context,String[] datas){
		this.datas = datas;
		this.context = context;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return datas.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return datas[position];
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = LayoutInflater.from(context).inflate(R.layout.item_exam_mount, null);
		TextView tv = (TextView) v.findViewById(R.id.tv_name);
		tv.setText(datas[position]);
		
		
		return v;
	}

}
