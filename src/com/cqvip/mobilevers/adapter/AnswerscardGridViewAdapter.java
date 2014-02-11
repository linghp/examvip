package com.cqvip.mobilevers.adapter;

import java.util.ArrayList;
import java.util.HashMap;

import com.cqvip.mobilevers.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class AnswerscardGridViewAdapter extends BaseAdapter {

	//private ArrayList<HashMap<String, Integer>> mGist;
	private int rowindex;
	private Context mContext;
	private ArrayList<ArrayList<Integer>> mList_Gist;
	
	public AnswerscardGridViewAdapter(Context context,int rowindex,ArrayList<ArrayList<Integer>> gist){
	
		this.mContext = context;
		this.mList_Gist = gist;
		this.rowindex = rowindex;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mList_Gist.get(rowindex).size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
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
			holder.giv = (ImageView) convertView.findViewById(R.id.iv);
			holder.tv = (TextView) convertView.findViewById(R.id.tv);
			convertView.setTag(holder);
		}else{
			holder = (GViewHolder) convertView.getTag();
		}
		//holder.giv.setImageResource(mGist.get(position).get("grid"));
		holder.tv.setText(mList_Gist.get(rowindex).get(position)+"");
		return convertView;
	}

	public static class GViewHolder{
		ImageView giv;
		TextView tv;
	}
}
