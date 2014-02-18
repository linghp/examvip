package com.cqvip.mobilevers.view;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.main_tab_exam, null);
		final ListView listview = (ListView) view
				.findViewById(R.id.lst_next_classy);
		String url = ConstantValues.aurl;//获取分类路径
		getStringDate(listview,url);
		listview.setOnItemClickListener(this);
		return view;
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
								listview.setAdapter(new ExamAClassfyAdapter(
										getActivity(), tempList));
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
