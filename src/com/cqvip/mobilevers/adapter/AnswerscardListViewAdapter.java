package com.cqvip.mobilevers.adapter;


import android.R.bool;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.cqvip.mobilevers.R;
import com.cqvip.mobilevers.ui.ExamActivity;
import com.cqvip.mobilevers.view.FragmentAnswerScard;

public class AnswerscardListViewAdapter extends BaseAdapter {

	private Context mContext;
	private FragmentManager fm;
	private int[][] mLists;// 记录所有的题目号
	private int[][] done_List;
	private int[][] right_List;
	private int[][] wrong_List;
	private boolean isShowRightWrong;
	public AnswerscardListViewAdapter(Context context,
			int[][] allAnswer, FragmentManager fm2) {
		    this.mContext = context;
			this.mLists = allAnswer;
			this.fm = fm2;
	}
	
	public AnswerscardListViewAdapter(Context context,
			int[][] allAnswer, int[][] donelists, FragmentManager fm2) {
		this.mContext = context;
		this.mLists = allAnswer;
		this.fm = fm2;
		this.done_List = donelists;
	}
	public AnswerscardListViewAdapter(Context context,
			int[][] allAnswer, int[][] donelists, int[][] rightlist, int[][] wronglist, FragmentManager fm2,boolean isShowRightWrong) {
		this.mContext = context;
		this.mLists = allAnswer;
		this.fm = fm2;
		this.done_List = donelists;
		this.right_List = rightlist;
		this.wrong_List = wronglist;
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
	public View getView(int position, View convertView, ViewGroup arg2) {
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
					TextView tv=(TextView) arg1.findViewById(R.id.tv_num);
					String id = tv.getText().toString();
					//Toast.makeText(mContext, tv.getText(), 1).show();
					fm.popBackStack();
					Log.i("select",id+"");
					((ExamActivity)mContext).updateView(id);
					
				}
				
			});
			
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		//holder.iv.setImageResource(mList.get(arg0).get("list"));

		//AnswerscardGridViewAdapter ga = new AnswerscardGridViewAdapter(mContext, mList.get(arg0),mList_Gist);
		AnswerscardGridViewAdapter ga = new AnswerscardGridViewAdapter(mContext, mLists[position],done_List[position],right_List[position],wrong_List[position],isShowRightWrong);
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
