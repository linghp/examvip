package com.cqvip.mobilevers.view;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

import com.android.volley.Request.Method;
import com.android.volley.Response.Listener;
import com.cqvip.mobilevers.R;
import com.cqvip.mobilevers.adapter.DoingExamPaperListAdapter;
import com.cqvip.mobilevers.adapter.DoneExamPaperListAdapter;
import com.cqvip.mobilevers.adapter.ExamPaperAdapter;
import com.cqvip.mobilevers.config.ConstantValues;
import com.cqvip.mobilevers.entity.DoingExamPaper;
import com.cqvip.mobilevers.entity.DoneExamPaper;
import com.cqvip.mobilevers.http.HttpUtils;
import com.cqvip.mobilevers.http.VersStringRequest;
import com.cqvip.mobilevers.ui.base.BaseFragment;
import com.cqvip.mobilevers.widget.DropDownListView;

/**
 * 我正在做的/我做过的试卷/和我收藏的试卷
 * 
 * @author luojiang
 * 
 */
public class DoneExamPaperListFragment extends BaseFragment implements
		OnClickListener, OnItemClickListener {
	private static final String USERID = "userid";
	private static final String TITLE = "title";
	private static final String URL = "url";
	private static final String TYPE = "type";
	public static final String DONEEXAMPAPERLIST_TAG = "doneexampaperlist";
	public static final String FAVORITEEXAMPAPERLIST_TAG = "favoriteexampaperlist";
	public static final String DOINGEXAMPAPERLIST_TAG = "doingexampaperlist";
	private TextView tv_title;
	// private ImageView img_back;
	private DropDownListView listview;
	private Map<String, String> gparams;
	private int page;
	private DoneExamPaperListAdapter adapter;
	private DoingExamPaperListAdapter doing_adapter;
	private View noresult_rl;
	private int type;

	public static DoneExamPaperListFragment newInstance(String userid,
			String title, String url, int type) {
		DoneExamPaperListFragment f = new DoneExamPaperListFragment();

		// Supply num input as an argument.
		Bundle args = new Bundle();
		args.putString(USERID, userid);
		args.putString(TITLE, title);
		args.putString(URL, url);
		args.putInt(TYPE, type);
		f.setArguments(args);
		return f;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (reuseView()) {
			return view;
		}
		view = inflater.inflate(R.layout.fragment_done_paperlist, container,
				false);
		type = getArguments().getInt(TYPE);
		listview = (DropDownListView) view.findViewById(R.id.list_donepaper);
		listview.setOnItemClickListener(this);
		// img_back = (ImageView) view.findViewById(R.id.img_back);
		tv_title = (TextView) view.findViewById(R.id.tv_show_title);
		// img_back.setOnClickListener(this);
		tv_title.setText(getArguments().getString(TITLE));
		page = 1;
		getData(page, ConstantValues.GETFIRSTPAGE);
		listview.setOnBottomListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				getData(page + 1, ConstantValues.GETNEXTPAGE);
				page++;
			}

		});
		noresult_rl = view.findViewById(R.id.noresult_rl);

		return view;

	}

	private void getData(int page, int getWhichPage) {

		// 请求网络
		getDataFromNet(page, getWhichPage);

	}

	private void getDataFromNet(int page, int getWhichPage) {
		customProgressDialog.show();
		// SharedPreferences localUsers =
		// getActivity().getSharedPreferences("mobilevers",
		// getActivity().MODE_PRIVATE);
		// String userId = localUsers.getString("userid", "0");
		// if(!userId.equals("0")){
		gparams = new HashMap<String, String>();
		Listener<String> listner;
		if (getWhichPage == ConstantValues.GETFIRSTPAGE) {
			listner = backlistener;
		} else {
			listner = backlistenerMore;
		}
		gparams.put("page", page + "");
		gparams.put("pageSize", ConstantValues.DEFAULYPAGESIZE + "");

		switch (type) {
		case ConstantValues.DOING_PAPER:
			gparams.put("userId", getArguments().getString(USERID));
			gparams.put("statusInfo", ConstantValues.DEFAULSTATUSINFO + "");
			break;
		case ConstantValues.DONG_PAPER:
			gparams.put("userId", getArguments().getString(USERID));
			gparams.put("type", ConstantValues.DEFAULSEVERVALUE + "");
			gparams.put("kClassId", ConstantValues.DEFAULSEVERVALUE + "");

			break;
		case ConstantValues.FAVORITE_PAPER:
			gparams.put("userId", getArguments().getString(USERID));
			gparams.put("type", ConstantValues.DEFAULSEVERVALUE + "");
			break;

		default:
			break;
		}

		requestVolley(gparams, ConstantValues.SERVER_URL
				+ getArguments().getString(URL), listner, Method.POST);
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
					// 判断
					if (json.isNull("error")) {
						// 返回正常
						switch (type) {
						case ConstantValues.DOING_PAPER:
							List<DoingExamPaper> reallists = DoingExamPaper
									.formList(json);
							setDoingList(reallists);

							break;
						case ConstantValues.DONG_PAPER:
							List<DoneExamPaper> done_lists = DoneExamPaper
									.formList(json);
							setDoneList(done_lists);
							break;
						case ConstantValues.FAVORITE_PAPER:
							JSONObject jsonObject = json.getJSONArray("result")
									.getJSONObject(0);
							String count = jsonObject.getString("count");
							JSONArray jsonArray = jsonObject
									.getJSONArray("exampaperlist");
							List<DoneExamPaper> paperInfos = DoneExamPaper
									.formList_GetFavorites(jsonArray);
							setFavoriteList(paperInfos);
							break;

						default:
							break;
						}
					} else {
						// 返回错误
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
						switch (type) {
						case ConstantValues.DOING_PAPER:
							List<DoneExamPaper>  lists = DoneExamPaper.formList(json);
							setMoreList(lists);
							break;
						case ConstantValues.DONG_PAPER:
							List<DoingExamPaper> reallists = DoingExamPaper.formList(json);
							setMoreDoingList(reallists);
							break;
						case ConstantValues.FAVORITE_PAPER:
							JSONObject jsonObject = json.getJSONArray("result").getJSONObject(0);
							JSONArray jsonArray = jsonObject.getJSONArray("exampaperlist");
							List<DoneExamPaper> favorlists = DoneExamPaper.formList_GetFavorites(jsonArray);
							setMoreList(favorlists);
							break;

						default:
							break;
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

		private void setMoreDoingList(List<DoingExamPaper> lists) {
			if (lists != null
					&& !lists.isEmpty()
					&& lists.size() == ConstantValues.DEFAULYPAGESIZE) {
				doing_adapter.addMoreData(lists);
				listview.onBottomComplete();
			} else if (lists != null && lists.size() > 0) {
				doing_adapter.addMoreData(lists);
				listview.setHasMore(false);
				listview.onBottomComplete();
			} else {
				listview.setHasMore(false);
				listview.onBottomComplete();
			}
		}

		private void setMoreList(List<DoneExamPaper> lists) {
			if (lists != null
					&& !lists.isEmpty()
					&& lists.size() == ConstantValues.DEFAULYPAGESIZE) {
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
		}
	};
	private void setFavoriteList(List<DoneExamPaper> paperInfos) {
		if (paperInfos != null && !paperInfos.isEmpty()) {
			adapter = new DoneExamPaperListAdapter(
					getActivity(), paperInfos);
			listview.setTag(FAVORITEEXAMPAPERLIST_TAG);
			if (paperInfos.size() < ConstantValues.DEFAULYPAGESIZE) {
				listview.setHasMore(false);
				listview.setAdapter(adapter);
				listview.onBottomComplete();
			} else {
				listview.setHasMore(true);
				listview.setAdapter(adapter);
			}
		} else {
			listview.setVisibility(View.GONE);
			noresult_rl.setVisibility(View.VISIBLE);
		}
	}
	
	private void setDoneList(List<DoneExamPaper> done_lists) {
		if (done_lists != null && !done_lists.isEmpty()) {
			listview.setVisibility(View.VISIBLE);
			noresult_rl.setVisibility(View.GONE);
			adapter = new DoneExamPaperListAdapter(
					getActivity(), done_lists);
			listview.setTag(DONEEXAMPAPERLIST_TAG);
			if (done_lists.size() < ConstantValues.DEFAULYPAGESIZE) {
				listview.setHasMore(false);
				listview.setAdapter(adapter);
				listview.onBottomComplete();
			} else {
				listview.setHasMore(true);
				listview.setAdapter(adapter);
			}
		} else {
			listview.setVisibility(View.GONE);
			noresult_rl.setVisibility(View.VISIBLE);
		}
	}
	
	private void setDoingList(List<DoingExamPaper> reallists) {
		if (reallists != null && !reallists.isEmpty()) {
			listview.setVisibility(View.VISIBLE);
			noresult_rl.setVisibility(View.GONE);
			doing_adapter = new DoingExamPaperListAdapter(
					getActivity(), reallists);
			listview.setTag(DONEEXAMPAPERLIST_TAG);
			if (reallists.size() < ConstantValues.DEFAULYPAGESIZE) {
				listview.setHasMore(false);
				listview.setAdapter(doing_adapter);
				listview.onBottomComplete();
			} else {
				listview.setHasMore(true);
				listview.setAdapter(doing_adapter);
			}
		} else {
			listview.setVisibility(View.GONE);
			noresult_rl.setVisibility(View.VISIBLE);
		}
	}
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		switch (type) {
		case ConstantValues.DOING_PAPER:
			DoingExamPaper doingExam = doing_adapter.getList().get(position);
			Fragment doingFragment = ExamDetailFragment.newInstance(doingExam.getExampapername(),
					doingExam.getExampaperid());
			addFragmentToStack(doingFragment, android.R.id.content);
			break;

		default:
			DoneExamPaper info = adapter.getList().get(position);
			Fragment newFragment = ExamDetailFragment.newInstance(info.getName(),
					info.getSubjectid());
			addFragmentToStack(newFragment, android.R.id.content);
			break;
		}
		
	}

	private void addFragmentToStack(Fragment newFragment, int layoutid) {
		FragmentTransaction ft = getActivity().getSupportFragmentManager()
				.beginTransaction();
		ft.setCustomAnimations(R.anim.slide_right_in, R.anim.slide_left_out,
				R.anim.slide_left_in, R.anim.slide_right_out);
		ft.replace(layoutid, newFragment);
		ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
		ft.addToBackStack(null);
		ft.commit();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.img_back:
			// getFragmentManager().popBackStack();

			break;

		default:
			break;
		}
	}

}
