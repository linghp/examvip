package com.cqvip.mobilevers.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

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
import com.android.volley.Response.Listener;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.cqvip.mobilevers.R;
import com.cqvip.mobilevers.adapter.ExamAClassfyAdapter;
import com.cqvip.mobilevers.adapter.ExamBClassfyAdapter;
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
	private Map<String, String> gparams;

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
			String url = ConstantValues.SERVER_URL+ConstantValues.GetKnowledgeClassList_ADDR;// url
		  	String parentID = "-1";
			getDataFromNet(url,parentID);
		}
	}

	private void getDataFromNet(String url, String parentID) {
		customProgressDialog.show();
		gparams=new HashMap<String, String>();			
		gparams.put("parentId", parentID);
		requestVolley(url,
				back_ls, Method.POST);
		
	}
	 private void requestVolley(String addr, Listener<String> bl, int method) {
			try {
				VersStringRequest mys = new VersStringRequest(method, addr, bl, volleyErrorListener) {

					protected Map<String, String> getParams()
							throws com.android.volley.AuthFailureError {
						return gparams;
					};
				};
				mys.setRetryPolicy(HttpUtils.setTimeout());
				mQueue.add(mys);
			} catch (Exception e) {
				//onError(2);
			}
		}

		private Listener<String> back_ls = new Listener<String>() {

			@Override
			public void onResponse(String response) {
				if(customProgressDialog!=null&&customProgressDialog.isShowing())
				customProgressDialog.dismiss();
				//解析结果
				if (response != null) {
				try {
					JSONObject json = new JSONObject(response);
					//判断
					if(json.isNull("error")){
						//返回正常
						tempList = OneLevelType.parserJsonData(response);
						if (tempList != null && !tempList.isEmpty()) {
							mAdapter = new ExamAClassfyAdapter(
									getActivity(), tempList);
							listview.setAdapter(mAdapter);
							setDataToDatabase();
						}
						
					}else {
						//返回错误
						//TODO
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			 } else {
					Toast.makeText(getActivity(), "无数据",
							Toast.LENGTH_LONG).show();
				}
			}
		};

	private void cursorToList() {
		try {
			if (cursor != null) {
				cursor.moveToFirst();
				tempList = new ArrayList<OneLevelType>();
				while (!cursor.isAfterLast()) {
					OneLevelType oneLevelType = new OneLevelType(
							cursor.getLong(0), cursor.getInt(1),cursor.getInt(2),
							cursor.getString(3), cursor.getInt(4)==0?false:true);
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
	/**
	 * 插入数据库
	 */
	private void setDataToDatabase() {
		oneLevelTypeDao.insertInTx(tempList);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Fragment newFragment = BClassfyFragment.newInstance(tempList
				.get(position).getExamtypeid().toString(),2);
		addFragmentToStack(newFragment, R.id.simple_fragment);
	}

}
