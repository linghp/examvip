package com.cqvip.mobilevers.view;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request.Method;
import com.android.volley.Response.Listener;
import com.cqvip.mobilevers.R;
import com.cqvip.mobilevers.adapter.DoneExamPaperListAdapter;
import com.cqvip.mobilevers.config.ConstantValues;
import com.cqvip.mobilevers.entity.DoneExamPaper;
import com.cqvip.mobilevers.entity.Paper;
import com.cqvip.mobilevers.entity.PaperInfo;
import com.cqvip.mobilevers.http.HttpUtils;
import com.cqvip.mobilevers.http.VersStringRequest;
import com.cqvip.mobilevers.ui.base.BaseFragment;
import com.cqvip.mobilevers.widget.DropDownListView;
/**
 * 我做过的试卷
 * @author luojiang
 *
 */
public class DoneExamPaperListFragment extends BaseFragment implements OnClickListener, OnItemClickListener{

	private TextView tv_title;
	private ImageView img_back;
	private DropDownListView listview;
    private Map<String, String> gparams;
    private int page;
    private DoneExamPaperListAdapter adapter;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (reuseView()) {
			return view;
		}
		view = inflater.inflate(R.layout.fragment_done_paperlist, container, false);
		listview = (DropDownListView) view
				.findViewById(R.id.list_donepaper);
		listview.setOnItemClickListener(this);
		img_back = (ImageView) view.findViewById(R.id.img_back);
		tv_title = (TextView) view.findViewById(R.id.tv_show_title);
		img_back.setOnClickListener(this);
		tv_title.setText("我做过的试卷");
		page = 1;
		getData(page,ConstantValues.GETFIRSTPAGE);
		listview.setOnBottomListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				getData(page+1,ConstantValues.GETNEXTPAGE);
				page++;
			}
			
		});
	return view;
	
	
	
}


private void getData(int page,int getWhichPage) {
	
	
	//请求网络
	getDataFromNet(page,getWhichPage);
	
}


private void getDataFromNet(int page,int getWhichPage) {
	//customProgressDialog.show();
	 SharedPreferences localUsers =	getActivity().getSharedPreferences("mobilevers", getActivity().MODE_PRIVATE);
     String userId = localUsers.getString("userid", "0");
     //if(!userId.equals("0")){
	gparams = new HashMap<String, String>();
	Listener<String> listner;
	if(getWhichPage==ConstantValues.GETFIRSTPAGE){
		listner = backlistener;
     }else{
    	 listner = backlistenerMore;
     }
	
	//gparams.put("userId", userId);
	gparams.put("userId", "e019edfd295b4a8a910948a3d4b115f7");
	gparams.put("type", ConstantValues.DEFAULSEVERVALUE+"");
	gparams.put("kClassId", ConstantValues.DEFAULSEVERVALUE+"");
	gparams.put("page", page+"");
	gparams.put("pageSize",ConstantValues.DEFAULYPAGESIZE+"");
	
	requestVolley(gparams, ConstantValues.SERVER_URL + ConstantValues.GETMYPASTEXAMLIST,
			listner, Method.POST);
//     }else{
//    	 //TODO
//     }
}
private void requestVolley(final Map<String, String> gparams, String url,
		Listener<String> listener, int post) {
	VersStringRequest mys = new VersStringRequest(post, url, listener, volleyErrorListener) {
		protected Map<String, String> getParams()
				throws com.android.volley.AuthFailureError {
			return gparams;
		};
	};
	mys.setRetryPolicy(HttpUtils.setTimeout());
	mQueue.add(mys);

}
private  Listener<String> backlistener = new Listener<String>() {
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
//				Paper p = Paper.parserJsonData(json);
				List<DoneExamPaper> reallists = DoneExamPaper.formList(json);
				Log.i("DoneExamPaperListFragment", reallists.toString());
				if(reallists!=null&&!reallists.isEmpty()){
				adapter = new DoneExamPaperListAdapter(getActivity(), reallists);
			    listview.setAdapter(adapter);
				}
				
				

			}else {
				//登陆错误
				//TODO
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	 } else {
//			Toast.makeText(getActivity(), "无数据",
//					Toast.LENGTH_LONG).show();
		}
	}
};  
private  Listener<String> backlistenerMore = new Listener<String>() {
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
				List<DoneExamPaper> lists = DoneExamPaper.formList(json);
				if (lists != null && !lists.isEmpty()&&lists.size()==ConstantValues.DEFAULYPAGESIZE) {
					adapter.addMoreData(lists);
					listview.onBottomComplete();
				} else if(lists != null &&lists.size()>0){
					adapter.addMoreData(lists);
					listview.setHasMore(false);
					listview.onBottomComplete();	
				}else
				{
					listview.setHasMore(false);
					listview.onBottomComplete();
				}

			}else {
				//错误
				//TODO
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	 } else {
//			Toast.makeText(getActivity(), "无数据",
//					Toast.LENGTH_LONG).show();
		}
	}
};


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
public void onItemClick(AdapterView<?> parent, View view, int position,
		long id) {
	DoneExamPaper info = adapter.getList().get(position);
//	if(info!=null){
//		Fragment newFragment = ExamDetailFragment.newInstance(info);
//		addFragmentToStack(newFragment, R.id.simple_fragment);
//	}
	Fragment newFragment = ExamDetailFragment.newInstance(info.getName(),info.getSubjectid());
	addFragmentToStack(newFragment, android.R.id.content);
}  
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.img_back:
			getFragmentManager().popBackStack();
			
			break;

		default:
			break;
		}
	}
	
	
}
