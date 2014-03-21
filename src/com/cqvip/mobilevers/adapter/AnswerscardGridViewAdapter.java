package com.cqvip.mobilevers.adapter;

import com.cqvip.mobilevers.R;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class AnswerscardGridViewAdapter extends BaseAdapter {

	private Context mContext;
	private int[] mLists;
	private int[] done_Lists;
	private int[] right_Lists;
	private int[] wrong_Lists;
	private boolean isShowRightWrong;
	public AnswerscardGridViewAdapter(Context mContext2, int[] is) {
		this.mContext = mContext2;
		this.mLists = is;
	}
	public AnswerscardGridViewAdapter(Context mContext2, int[] is, int[] is2) {
		this.mContext = mContext2;
		this.mLists = is;
		this.done_Lists = is2;
	}
	public AnswerscardGridViewAdapter(Context mContext2, int[] is, int[] is2,int[] rightis,int[] wrongis, boolean isShowRightWrong) {
		this.mContext = mContext2;
		this.mLists = is;
		this.done_Lists = is2;
		this.right_Lists = rightis;
		this.wrong_Lists = wrongis;
		this.isShowRightWrong = isShowRightWrong;
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
			holder.rl = (RelativeLayout) convertView.findViewById(R.id.rl_card);
			holder.tv_num = (TextView) convertView.findViewById(R.id.tv_num);
			holder.tv_rightwrong = (TextView) convertView.findViewById(R.id.tv_rightwrong);
			convertView.setTag(holder);
		}else{
			holder = (GViewHolder) convertView.getTag();
		}
		//holder.giv.setImageResource(mGist.get(position).get("grid"));
		int resid;
		if(done_Lists[position]>0){
			resid = R.drawable.grid_style_gree;
		}else{
			resid = R.drawable.grid_style_gray;
		}
		holder.tv_num.setText(mLists[position]+"");
		if(isShowRightWrong){
		if(right_Lists[position]>0){
		holder.tv_rightwrong.setText("√");
		}
		if(wrong_Lists[position]>0){
			holder.tv_rightwrong.setText("×");
		}
		}
		holder.rl.setBackgroundResource(resid);
		return convertView;
	}

	private  class GViewHolder{
		RelativeLayout rl;
		TextView tv_num;
		TextView tv_rightwrong;
		
	}
}
