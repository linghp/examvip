package com.cqvip.mobilevers.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cqvip.mobilevers.R;
import com.cqvip.mobilevers.adapter.base.AdapterBase;
import com.cqvip.mobilevers.db.OneLevelType;
import com.cqvip.mobilevers.view.BaseListFragment;
import com.cqvip.mobilevers.view.ExamClassfyFragment;

public class ExamAClassfyAdapter extends AdapterBase<OneLevelType> implements OnClickListener {

	private Context context;
	private BaseListFragment baseListFragment;
	private int[] drawables;
	public ExamAClassfyAdapter (BaseListFragment baseListFragment,List<OneLevelType> lists,int[] drawables) {
		this.context = baseListFragment.getActivity();
		this.baseListFragment=baseListFragment;
		this.mList = lists;
		this.drawables = drawables;
	}
	
	
	private static class ViewHolder{
		private TextView title;
		private TextView count;
		private ImageView img;
	}
	
	@Override
	protected View getExView(int position, View convertView, ViewGroup parent) {
	
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(R.layout.item_a_classfy, null);
			holder.title = (TextView) convertView.findViewById(R.id.txt_aclassfy_title);
			holder.count = (TextView) convertView.findViewById(R.id.tx_aclassfy_arrow);
			holder.img = (ImageView) convertView.findViewById(R.id.img_aclassfy_book);
			holder.count.setOnClickListener(this);
			holder.count.setTag(mList.get(position));
			convertView.setTag(holder);
			
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		holder.title.setText(mList.get(position).getTitle());
		holder.count.setText(mList.get(position).getCount()+"");
		holder.img.setImageResource(drawables[position]);
		return convertView;
	}

	@Override
	protected void onReachBottom() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onClick(View v) {
		OneLevelType oneLevelType=(OneLevelType)v.getTag();
		baseListFragment.addFragmentToStack(ExamClassfyFragment.newInstance(oneLevelType.getExamtypeid()+"",oneLevelType.getTitle()),R.id.simple_fragment);
		//Toast.makeText(context, "click"+v.getTag(), 1).show();
	}

}
