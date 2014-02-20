package com.cqvip.mobilevers.view;

import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request.Method;
import com.android.volley.toolbox.Volley;
import com.android.volley.Response;
import com.cqvip.mobilevers.R;
import com.cqvip.mobilevers.adapter.ExamAClassfyAdapter;
import com.cqvip.mobilevers.config.ConstantValues;
import com.cqvip.mobilevers.entity.ExamInfo;
import com.cqvip.mobilevers.exception.ErrorVolleyThrow;
import com.cqvip.mobilevers.http.VersStringRequest;

/**
 * 获取总分类，fragment
 * @author luojiang
 *
 */
public class AClassfyFragment extends BaseListFragment implements
		OnItemClickListener {

	private List<ExamInfo> tempList;
	final static String TAG="AClassfyFragment";

	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if(reuseView()) {
			return view;
		}
		Log.i(TAG, "onCreateView");
		view = inflater.inflate(R.layout.main_tab_exam, null);
		final ListView listview = (ListView) view
				.findViewById(R.id.lst_next_classy);
		String url = ConstantValues.aurl;//获取分类路径
		getStringDate(listview,url);
		listview.setOnItemClickListener(this);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		Log.i(TAG, "onActivityCreated");
	}
	
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		Log.i(TAG, "onPause");
	}
	
	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		Log.i(TAG, "onStop");
	}
	
	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
		Log.i(TAG, "onDestroyView");
	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Log.i(TAG, "onDestroy");
	}
	
	@Override
	public void onDetach() {
		// TODO Auto-generated method stub
		super.onDetach();
		Log.i(TAG, "onDetach");
	}
	/**
	 * 从网络获取书籍
	 * @param listview
	 */
	private void getStringDate(final ListView listview,String url) {
		mQueue = Volley.newRequestQueue(getActivity());
		volleyErrorListener = new ErrorVolleyThrow(getActivity(), null);
		VersStringRequest myReq = new VersStringRequest(Method.GET,
				url, new Response.Listener<String>() {

					@Override
					public void onResponse(String result) {
						System.out.println(result);
						if (result != null) {
							tempList = ExamInfo.parserJsonData(result);
							if (tempList != null && !tempList.isEmpty()) {
								mAdapter=new ExamAClassfyAdapter(
										getActivity(), tempList);
								listview.setAdapter(mAdapter);
							}
						} else {
							Toast.makeText(getActivity(), "无数据",
									Toast.LENGTH_LONG).show();
						}

					}
				}, volleyErrorListener);
		mQueue.add(myReq);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Fragment newFragment = BClassfyFragment.newInstance(tempList.get(
				position).getId());
		addFragmentToStack(newFragment, android.R.id.content);
	}

}
