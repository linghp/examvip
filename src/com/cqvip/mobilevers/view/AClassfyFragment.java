package com.cqvip.mobilevers.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.cqvip.mobilevers.R;
import com.cqvip.mobilevers.adapter.ExamAClassfyAdapter;
import com.cqvip.mobilevers.config.ConstantValues;
import com.cqvip.mobilevers.db.OneLevelType;
import com.cqvip.mobilevers.db.OneLevelTypeDao;
import com.cqvip.mobilevers.exception.ErrorVolleyThrow;
import com.cqvip.mobilevers.http.HttpUtils;
import com.cqvip.mobilevers.http.VersStringRequest;
import com.cqvip.mobilevers.ui.FragmentExamActivity;

/**
 * 获取总分类，fragment
 * 
 * @author luojiang
 * 
 */
public class AClassfyFragment extends BaseListFragment implements
		OnItemClickListener {

	private List<OneLevelType> tempList;
	final static String TAG = "AClassfyFragment";
	private OneLevelTypeDao oneLevelTypeDao;
	private Cursor cursor;
	private SQLiteDatabase db;
	private ListView listview;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (reuseView()) {
			return view;
		}
		Log.i(TAG, "onCreateView");
		view = inflater.inflate(R.layout.main_tab_exam, null);
		listview = (ListView) view
				.findViewById(R.id.lst_next_classy);

		oneLevelTypeDao = ((FragmentExamActivity) getActivity()).oneLevelTypeDao;
		db = ((FragmentExamActivity) getActivity()).db;
		getData();

		listview.setOnItemClickListener(this);
		return view;
	}

	private void getData() {
		String idColumn = OneLevelTypeDao.Properties.Id.columnName;
		String orderBy = idColumn + " COLLATE LOCALIZED ASC";
		cursor = db.query(oneLevelTypeDao.getTablename(),
				oneLevelTypeDao.getAllColumns(), null, null, null, null,
				orderBy);
		if (cursor.moveToNext()) {
			cursorToList();
			mAdapter = new ExamAClassfyAdapter(getActivity(), tempList);
			listview.setAdapter(mAdapter);
		}else{
			Toast.makeText(getActivity(), "网络获取", 1).show();
			String url = ConstantValues.EXAMTYPEURL;// 获取分类路径
			getStringDate(listview, url);
		}
	}

	private void cursorToList() {
		try {
			if (cursor != null) {
				cursor.moveToFirst();
				tempList = new ArrayList<OneLevelType>();
				while (!cursor.isAfterLast()) {
					OneLevelType oneLevelType = new OneLevelType(
							cursor.getLong(0), cursor.getInt(1),cursor.getInt(2),
							cursor.getString(3), Boolean.parseBoolean(cursor
									.getString(4)));
					Log.i("AClassfyFragment", oneLevelType.toString());
					tempList.add(oneLevelType);
					cursor.moveToNext();
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
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
	 * 
	 * @param listview
	 */
	private Map<String, String> gparams;

	private void getStringDate(final ListView listview, String url) {
		mQueue = Volley.newRequestQueue(getActivity());
		volleyErrorListener = new ErrorVolleyThrow(getActivity(), null);
		gparams = new HashMap<String, String>();
		gparams.put("parentId", "-1");
		VersStringRequest myReq = new VersStringRequest(Method.POST, url,
				new Response.Listener<String>() {

					@Override
					public void onResponse(String result) {
						System.out.println(result);
						if (result != null) {
							tempList = OneLevelType.parserJsonData(result);
							if (tempList != null && !tempList.isEmpty()) {
								mAdapter = new ExamAClassfyAdapter(
										getActivity(), tempList);
								listview.setAdapter(mAdapter);
								setDataToDatabase();
							}
						} else {
							Toast.makeText(getActivity(), "无数据",
									Toast.LENGTH_LONG).show();
						}

					}

					private void setDataToDatabase() {
						oneLevelTypeDao.insertInTx(tempList);
					}
				}, volleyErrorListener) {
			@Override
			protected Map<String, String> getParams() throws AuthFailureError {
				// TODO Auto-generated method stub
				return gparams;
			}
		};
		myReq.setRetryPolicy(HttpUtils.setTimeout());
		mQueue.add(myReq);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Fragment newFragment = BClassfyFragment.newInstance(tempList
				.get(position).getExamtypeid().toString());
		addFragmentToStack(newFragment, R.id.simple_fragment);
	}

}
