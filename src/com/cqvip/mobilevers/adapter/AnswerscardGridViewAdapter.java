package com.cqvip.mobilevers.adapter;

import com.cqvip.mobilevers.R;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class AnswerscardGridViewAdapter extends BaseAdapter {

	private Context mContext;
	private int[] mLists;
	private int[] done_Lists;
	public AnswerscardGridViewAdapter(Context mContext2, int[] is) {
		this.mContext = mContext2;
		this.mLists = is;
	}
	public AnswerscardGridViewAdapter(Context mContext2, int[] is, int[] is2) {
		this.mContext = mContext2;
		this.mLists = is;
		this.done_Lists = is2;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mLists.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		GViewHolder holder;
		if(convertView ==null){
			holder = new GViewHolder();
			convertView = LayoutInflater.from(mContext).inflate(R.layout.answerscard_gridviewitem, null, false);
			holder.tv = (TextView) convertView.findViewById(R.id.tv);
			convertView.setTag(holder);
		}else{
			holder = (GViewHolder) convertView.getTag();
		}
		//holder.giv.setImageResource(mGist.get(position).get("grid"));
		Drawable drawble ;
		if(done_Lists[position]>0){
			drawble = mContext.getResources().getDrawable(R.drawable.grid_style_gree);
		}else{
			drawble = mContext.getResources().getDrawable(R.drawable.grid_style_gray);
		}
		holder.tv.setText(mLists[position]+"");
		holder.tv.setBackgroundDrawable(drawble);
		return convertView;
	}

	private  class GViewHolder{
		TextView tv;
	}
}
