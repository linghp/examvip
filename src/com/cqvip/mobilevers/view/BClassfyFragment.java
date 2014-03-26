package com.cqvip.mobilevers.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request.Method;
import com.android.volley.Response.Listener;
import com.cqvip.mobilevers.MyApplication;
import com.cqvip.mobilevers.R;
import com.cqvip.mobilevers.adapter.ExamAClassfyAdapter;
import com.cqvip.mobilevers.adapter.ExamBClassfyAdapter;
import com.cqvip.mobilevers.config.ConstantValues;
import com.cqvip.mobilevers.db.OneLevelType;
import com.cqvip.mobilevers.db.OneLevelTypeDao;
import com.cqvip.mobilevers.db.TwoLevelType;
import com.cqvip.mobilevers.db.TwoLevelTypeDao;
import com.cqvip.mobilevers.http.HttpUtils;
import com.cqvip.mobilevers.http.VersStringRequest;
import com.cqvip.mobilevers.ui.ExamClassfyActivity;
import com.cqvip.mobilevers.ui.FragmentExamActivity;

public class BClassfyFragment extends BaseListFragment implements
		OnItemClickListener, OnClickListener {

	private Map<String, String> gparams;
	private List<TwoLevelType> tempList;
	final static String TAG = "BClassfyFragment";
	final static String TITLE = "title";

	private String superiorexamtypeid;
	private Cursor cursor;
	private SQLiteDatabase db;
	private TwoLevelTypeDao twoLevelTypeDao;
	private int level = 0;
	//private ImageView img_back;
	private TextView tv_title;
	private String title;
	/**
	 * Create a new instance of CountingFragment, providing "num" as an
	 * argument.
	 */
	public static BClassfyFragment newInstance(String num, int level,String title) {
		BClassfyFragment f = new BClassfyFragment();

		// Supply num input as an argument.
		Bundle args = new Bundle();
		args.putString("superiorexamtypeid", num);
		args.putInt("level", level);
		args.putString(TITLE, title);
		f.setArguments(args);

		return f;
	}

	/**
	 * When creating, retrieve this instance's number from its arguments.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (savedInstanceState != null) {
			superiorexamtypeid = savedInstanceState
					.getString("superiorexamtypeid");
			level = savedInstanceState.getInt("level");
		}
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		outState.putString("superiorexamtypeid", superiorexamtypeid);
		outState.putInt("level", level);
	}

	/**
	 * The Fragment's UI is just a simple text view showing its instance number.
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (reuseView()) {
			return view;
		}
		view = inflater.inflate(R.layout.main_btab_exam, container, false);
		// view = inflater.inflate(R.layout.main_tab_exam, null);
		title = getArguments().getString(TITLE);
		listview = (ListView) view.findViewById(R.id.lst_next_classy);
		//img_back = (ImageView) view.findViewById(R.id.img_back);
		//img_back.setOnClickListener(this);
		tv_title = (TextView) view.findViewById(R.id.tv_show_title);
		tv_title.setText(title);
		String url = ConstantValues.SERVER_URL
				+ ConstantValues.GetKnowledgeClassList_ADDR;// url
		superiorexamtypeid = getArguments() != null ? getArguments().getString(
				"superiorexamtypeid") : 0 + ""; // 参数
		level = getArguments() != null ? getArguments().getInt("level") : 0;
		db = ((FragmentExamActivity) getActivity()).db;
		twoLevelTypeDao = ((MyApplication) getActivity().getApplication()).daoSession
				.getTwoLevelTypeDao();
		getData(url, superiorexamtypeid);

		listview.setOnItemClickListener(this);
		return view;
	}

	private void getData(String url, String superiorexamtypeid) {
		// TODO Auto-generated method stub
		// 数据库获取
		String idColumn = TwoLevelTypeDao.Properties.Id.columnName;
		String orderBy = idColumn + " COLLATE LOCALIZED ASC";
		cursor = db.query(twoLevelTypeDao.getTablename(),
				twoLevelTypeDao.getAllColumns(), "superiorexamtypeid=?",
				new String[] { superiorexamtypeid }, null, null, orderBy);
		if (cursor.moveToNext()) {
			cursorToList();
			mAdapter = new ExamBClassfyAdapter(this, tempList);
			listview.setAdapter(mAdapter);
		} else {
			// 网络获取
			getDataFromNet(url, superiorexamtypeid);
		}
	}

	private void cursorToList() {
		try {
			if (cursor != null) {
				cursor.moveToFirst();
				tempList = new ArrayList<TwoLevelType>();
				while (!cursor.isAfterLast()) {
					TwoLevelType twoLevelType = new TwoLevelType(
							cursor.getLong(0), cursor.getInt(1),
							cursor.getInt(2), cursor.getInt(3),
							cursor.getString(4),cursor.getInt(5)==0?false:true,
									cursor.getInt(6)==0?false:true,
							cursor.getInt(7));
					Log.i("BClassfyFragment", twoLevelType.toString());
					Log.i("BClassfyFragment_haschildren", cursor.getInt(5) + "");

					tempList.add(twoLevelType);
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
	 * 从网络获取数据
	 * 
	 * @param url
	 * @param superiorexamtypeid
	 */
	private void getDataFromNet(String url, String superiorexamtypeid) {
		customProgressDialog.show();
		gparams = new HashMap<String, String>();
		gparams.put("parentId", superiorexamtypeid);
		requestVolley(url, back_ls, Method.POST);
	}

	private void requestVolley(String addr, Listener<String> bl, int method) {
		try {
			VersStringRequest mys = new VersStringRequest(method, addr, bl,
					volleyErrorListener) {

				protected Map<String, String> getParams()
						throws com.android.volley.AuthFailureError {
					return gparams;
				};
			};
			mys.setRetryPolicy(HttpUtils.setTimeout());
			mQueue.add(mys);
		} catch (Exception e) {
			// onError(2);
		}
	}

	private Listener<String> back_ls = new Listener<String>() {

		@Override
		public void onResponse(String response) {
			if (customProgressDialog != null
					&& customProgressDialog.isShowing())
				customProgressDialog.dismiss();
			// 解析结果
			if (response != null) {
				try {
					JSONObject json = new JSONObject(response);
					// 判断
					if (json.isNull("error")) {
						// 返回正常
						tempList = TwoLevelType.parserJsonData(response, level,
								superiorexamtypeid, false);
						if (tempList != null && !tempList.isEmpty()) {
							mAdapter = new ExamBClassfyAdapter(BClassfyFragment.this,
									tempList);
							listview.setAdapter(mAdapter);
							setDataToDatabase();
						}

					} else {
						// 登陆错误
						// TODO
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			} else {
				Toast.makeText(getActivity(), "无数据", Toast.LENGTH_LONG).show();
			}
		}
	};

	/**
	 * 插入数据库
	 */
	private void setDataToDatabase() {
		twoLevelTypeDao.insertInTx(tempList);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		TwoLevelType obj = tempList.get(position);
		String nextId = obj.getExamtypeid().toString();
		if (tempList.get(position).getHaschildren()) {
			Fragment newFragment = BClassfyFragment
					.newInstance(nextId, ++level,obj.getTitle());
			addFragmentToStack(newFragment, R.id.simple_fragment);
		} else {
			Fragment newFragment = ExamClassfyFragment
					.newInstance(nextId,obj.getTitle());
			addFragmentToStack(newFragment, R.id.simple_fragment);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.img_back:
			//getFragmentManager().popBackStack();
			break;

		default:
			break;
		}
		
	}

}
