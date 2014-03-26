package com.cqvip.mobilevers.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cqvip.mobilevers.R;
import com.cqvip.mobilevers.adapter.base.AdapterBase;
import com.cqvip.mobilevers.db.OneLevelType;
import com.cqvip.mobilevers.db.TwoLevelType;
import com.cqvip.mobilevers.entity.ExamInfo;
import com.cqvip.mobilevers.view.BaseListFragment;
import com.cqvip.mobilevers.view.ListViewFragment;

public class ExamBClassfyAdapter extends AdapterBase<TwoLevelType> implements OnClickListener{

	private Context context;
	private BaseListFragment baseListFragment;
	public ExamBClassfyAdapter (BaseListFragment baseListFragment,List<TwoLevelType> lists) {
		this.context = baseListFragment.getActivity();
		this.baseListFragment=baseListFragment;
		this.mList = lists;
	}
	
	@Override
	protected View getExView(int position, View convertView, ViewGroup parent) {
		TextView title = null;
		TextView count = null;
		View v;
		if(convertView==null){
			 v = LayoutInflater.from(context).inflate(R.layout.item_b_classfy, null);
		}else{
			v = convertView;
		}
		title = (TextView) v.findViewById(R.id.txt_item_title);
		count = (TextView) v.findViewById(R.id.tx_aclassfy_arrow);
		count.setOnClickListener(this);
		count.setTag(mList.get(position));
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
		TwoLevelType twoLevelType=(TwoLevelType)v.getTag();
		baseListFragment.addFragmentToStack(ListViewFragment.newInstance(twoLevelType.getExamtypeid()+"",twoLevelType.getTitle()),R.id.simple_fragment);
		//Toast.makeText(context, "click"+v.getTag(), 1).show();
	}

}
