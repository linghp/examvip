package com.cqvip.mobilevers.adapter;

import java.util.ArrayList;
import java.util.HashMap;

import com.cqvip.mobilevers.R;
import com.cqvip.mobilevers.view.FragmentAnswerScard;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

public class AnswerscardListViewAdapter extends BaseAdapter {

	//�п�
	private int cWidth = 20;
	//ˮƽ���
	private int hSpacing = 10;
	private Context mContext;
	private ArrayList<Integer> mList;
	private ArrayList<ArrayList<Integer>> mList_Gist = new ArrayList<ArrayList<Integer>>();

	public AnswerscardListViewAdapter(Context context,
			ArrayList<Integer> list,
			ArrayList<ArrayList<Integer>> gist) {

		this.mContext = context;
		this.mList = list;
		this.mList_Gist = gist;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mList.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return mList.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return mList.size();
	}

	@Override
	public View getView(int arg0, View convertView, ViewGroup arg2) {
		// TODO Auto-generated method stub
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.answerscard_listitem, arg2, false);
			//holder.iv = (ImageView) convertView.findViewById(R.id.iv);
			holder.gv = (GridView) convertView.findViewById(R.id.gridview);
			holder.gv.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					TextView tv=(TextView) arg1.findViewById(R.id.tv);
					Toast.makeText(mContext, tv.getText(), 1).show();
				}
				
			});
			
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		//holder.iv.setImageResource(mList.get(arg0).get("list"));

		AnswerscardGridViewAdapter ga = new AnswerscardGridViewAdapter(mContext, mList.get(arg0),mList_Gist);
		//int ii = ga.getCount();
//		LayoutParams params = new LayoutParams(ii * (48 + 10),
//				LayoutParams.WRAP_CONTENT);
//		holder.gv.setLayoutParams(params);
		holder.gv.setColumnWidth(FragmentAnswerScard.gridviewcolumnwidth);
		//holder.gv.setHorizontalSpacing(hSpacing);
		//holder.gv.setStretchMode(GridView.STRETCH_SPACING);
		//holder.gv.setNumColumns(ii);
		holder.gv.setAdapter(ga);

		return convertView;
	}

	public static class ViewHolder {

		//ImageView iv;
		GridView gv;
	}
}
