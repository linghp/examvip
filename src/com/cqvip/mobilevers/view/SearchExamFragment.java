package com.cqvip.mobilevers.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Request.Method;
import com.android.volley.Response.Listener;
import com.cqvip.mobilevers.R;
import com.cqvip.mobilevers.adapter.ExamPaperAdapter;
import com.cqvip.mobilevers.config.ConstantValues;
import com.cqvip.mobilevers.entity.Paper;
import com.cqvip.mobilevers.entity.PaperInfo;
import com.cqvip.mobilevers.http.HttpUtils;
import com.cqvip.mobilevers.http.VersStringRequest;
import com.cqvip.mobilevers.ui.FragmentSearchActivity;
import com.cqvip.mobilevers.ui.base.BaseFragment;
import com.cqvip.mobilevers.widget.DropDownListView;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class SearchExamFragment extends BaseFragment implements
		OnItemClickListener {
	private EditText editText;
	private DropDownListView listview;
	private TextView totalsearch_tv;
	private View noresult_rl;
	
	private int page;
	private ExamPaperAdapter adapter;
	private String key;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.i("SearchExamFragment", "onCreateView");
		if (reuseView()) {
			return view;
		}
		view = inflater.inflate(R.layout.main_tab_search, null);
		initview(view);
		return view;
	}

	private void initview(View v) {
		editText = (EditText) v.findViewById(R.id.editText1);
		listview = (DropDownListView) v.findViewById(R.id.list_search);
		editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {
				key = editText.getText().toString().trim();
				if (TextUtils.isEmpty(key)) {
					return true;
				}
				// 隐藏键盘
				hideKeybord();
				// 检查网络
				if (!HttpUtils.checkNetWork(getActivity())) {
					return false;
				}
				// 网络访问,获取首页
				customProgressDialog.show();
				page = 1;
				getData(key, page, ConstantValues.GETFIRSTPAGE);
				Log.i("SearchExamFragment_onEditorAction", "onEditorAction");
				return true;
			}
		});

		listview.setOnItemClickListener(this);
		listview.setOnBottomListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				getData(key, ++page, ConstantValues.GETNEXTPAGE);
				Log.i("SearchExamFragment_setOnBottomListener",
						"setOnBottomListener");
				page++;
			}

		});
	
		totalsearch_tv=(TextView) v.findViewById(R.id.totalsearch_tv);
		noresult_rl=v.findViewById(R.id.noresult_rl);
	}

	private void getData(String key, int page, int getWhichPage) {
		Map<String, String> gparams = new HashMap<String, String>();
		Listener<String> listner;
		if (getWhichPage == ConstantValues.GETFIRSTPAGE) {
			listner = backlistener;
		} else {
			listner = backlistenerMore;
		}
		gparams.put("keyWord", key);
		gparams.put("page", page + "");
		gparams.put("pageSize", ConstantValues.DEFAULYPAGESIZE + "");
		requestVolley(gparams, ConstantValues.SERVER_URL
				+ ConstantValues.SEARCH_PAPERINFO, listner, Method.POST);
	}

	private void requestVolley(final Map<String, String> gparams, String url,
			Listener<String> listener, int post) {
		VersStringRequest mys = new VersStringRequest(post, url, listener,
				volleyErrorListener) {
			protected Map<String, String> getParams()
					throws com.android.volley.AuthFailureError {
				return gparams;
			};
		};
		mys.setRetryPolicy(HttpUtils.setTimeout());
		mQueue.add(mys);
	}

	private Listener<String> backlistener = new Listener<String>() {
		@Override
		public void onResponse(String response) {
			if (customProgressDialog != null
					&& customProgressDialog.isShowing())
				customProgressDialog.dismiss();
			// 解析结果
			if (response != null) {
				try {
					JSONObject json = new JSONObject(response);

					JSONObject jsonObject = (JSONObject) json.getJSONArray(
							"result").get(0);
					int count = Integer.parseInt(jsonObject.getString("count"));
					//Toast.makeText(getActivity(), count + "", 1).show();
					if (count != 0) {
						listview.setVisibility(View.VISIBLE);
						totalsearch_tv.setVisibility(View.VISIBLE);
						String temp="搜索到与 \"<font face=\"arial\" color=\"red\">"+key+"</font>\"  相关的试卷 <font face=\"arial\" color=\"red\">"+count+"</font> 个";
						totalsearch_tv.setText(Html.fromHtml(temp));
						noresult_rl.setVisibility(View.GONE);
						// 判断
						if (json.isNull("error")) {
							// 返回正常
							Paper p = Paper.parserJsonData(json);
							List<PaperInfo> reallists = p.getReal();
							if (reallists != null && !reallists.isEmpty())
								adapter = new ExamPaperAdapter(getActivity(),
										reallists);
							listview.setAdapter(adapter);
						} else {
							// 登陆错误
							// TODO
						}
					} else {
						adapter = new ExamPaperAdapter(getActivity(),
								new ArrayList<PaperInfo>());
						listview.setAdapter(adapter);
						listview.setVisibility(View.GONE);
						totalsearch_tv.setVisibility(View.GONE);
						noresult_rl.setVisibility(View.VISIBLE);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			} else {
				// Toast.makeText(getActivity(), "无数据",
				// Toast.LENGTH_LONG).show();
			}
		}
	};
	private Listener<String> backlistenerMore = new Listener<String>() {
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
						Paper p = Paper.parserJsonData(json);
						List<PaperInfo> lists = p.getReal();
						if (lists != null
								&& !lists.isEmpty()
								&& lists.size() == ConstantValues.DEFAULYPAGESIZE) {
							System.out.println(lists.toString());
							adapter.addMoreData(lists);
							listview.onBottomComplete();
						} else if (lists != null && lists.size() > 0) {
							adapter.addMoreData(lists);
							listview.setHasMore(false);
							listview.onBottomComplete();
						} else {
							listview.setHasMore(false);
							listview.onBottomComplete();
						}

					} else {
						// 错误
						// TODO
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			} else {
				// Toast.makeText(getActivity(), "无数据",
				// Toast.LENGTH_LONG).show();
			}
		}
	};

	/**
	 * 隐藏键盘
	 */
	private void hideKeybord() {
		InputMethodManager imm = (InputMethodManager) getActivity()
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		if (imm.isActive()) {
			imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		PaperInfo info = adapter.getList().get(position);
		if (info != null) {
			((FragmentSearchActivity) getActivity()).onItemNextSelected(info);
		} else {
			return;
		}
	}

	@Override
	public void onAttach(Activity activity) {
		Log.i("SearchExamFragment", "onAttach");
		super.onAttach(activity);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		Log.i("SearchExamFragment", "onCreate");
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		Log.i("SearchExamFragment", "onActivityCreated");
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onStart() {
		Log.i("SearchExamFragment", "onStart");
		super.onStart();
	}

	@Override
	public void onResume() {
		Log.i("SearchExamFragment", "onResume");
		super.onResume();
	}

	@Override
	public void onPause() {
		Log.i("SearchExamFragment", "onPause");
		super.onPause();
	}

	@Override
	public void onStop() {
		Log.i("SearchExamFragment", "onStop");
		super.onStop();
	}

	@Override
	public void onDestroyView() {
		Log.i("SearchExamFragment", "onDestroyView");
		super.onDestroyView();
	}

	@Override
	public void onDestroy() {
		Log.i("SearchExamFragment", "onDestroy");
		super.onDestroy();
	}

}
