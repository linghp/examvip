package com.cqvip.mobilevers.view;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request.Method;
import com.android.volley.Response.Listener;
import com.cqvip.mobilevers.R;
import com.cqvip.mobilevers.adapter.ExamPaperAdapter;
import com.cqvip.mobilevers.config.ConstantValues;
import com.cqvip.mobilevers.entity.Paper;
import com.cqvip.mobilevers.entity.PaperDetail;
import com.cqvip.mobilevers.entity.PaperInfo;
import com.cqvip.mobilevers.entity.TagInfo;
import com.cqvip.mobilevers.exam.Exam;
import com.cqvip.mobilevers.exam.SubjectExam;
import com.cqvip.mobilevers.http.HttpUtils;
import com.cqvip.mobilevers.http.VersStringRequest;
import com.cqvip.mobilevers.ui.ExamActivity;
import com.cqvip.mobilevers.ui.FragmentExamActivity;
import com.cqvip.mobilevers.ui.base.BaseFragment;
import com.cqvip.mobilevers.utils.DateUtil;
import com.cqvip.mobilevers.widget.DropDownListView;

public class ListViewFragment extends BaseListFragment implements OnItemClickListener{

	private static final String DETAL_INFO = "detail";
	private DropDownListView dropDownListView;
	private Map<String, String> gparams;
	private ExamPaperAdapter adapter; 
	private int page;
	
	  public static ListViewFragment newInstance(String examtypeid) {
		  ListViewFragment f = new ListViewFragment();

	        // Supply num input as an argument.
	        Bundle args = new Bundle();
	        args.putString(DETAL_INFO, examtypeid);
	        f.setArguments(args);
	        return f;
	    }
	
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if(reuseView()) {
			return view;
		}
		//Log.i(TAG, "onCreateView");
		view = inflater.inflate(R.layout.fragment_exam_classfy_dummy, null);
		dropDownListView=(DropDownListView) view.findViewById(R.id.lst_exampaper);
		dropDownListView.setOnItemClickListener(this);
		page = 1;
		final String examtypeid =  getArguments().getString(DETAL_INFO);
		getData(examtypeid,page,ConstantValues.GETFIRSTPAGE);
		dropDownListView.setOnBottomListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				getData(examtypeid,page+1,ConstantValues.GETNEXTPAGE);
			}
		});
		
		return view;
	}
	

	private void getData(String examtypeid,int page,int getWhichPage) {
		customProgressDialog.show();
		gparams = new HashMap<String, String>();
		gparams.put("kClassId", "95");
		gparams.put("type", "-1");
		gparams.put("page", page+"");
		gparams.put("pageSize", "15");
		Listener<String> listner;
		if(getWhichPage==ConstantValues.GETFIRSTPAGE){
			listner = backlistener;
	     }else{
	    	 listner = backlistenerMore;
	     }
		requestVolley(gparams, ConstantValues.SERVER_URL + ConstantValues.GETEXAMPAPER_ADDR,
				listner, Method.POST);
		
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
					Paper p = Paper.parserJsonData(json);
					List<PaperInfo> reallists = p.getReal();
					if(reallists!=null&&!reallists.isEmpty())
					adapter = new ExamPaperAdapter(getActivity(), reallists);
					dropDownListView.setAdapter(adapter);

				}else {
					//登陆错误
					//TODO
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		 } else {
//				Toast.makeText(getActivity(), "无数据",
//						Toast.LENGTH_LONG).show();
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
					Paper p = Paper.parserJsonData(json);
					List<PaperInfo> lists = p.getReal();
					if (lists != null && !lists.isEmpty()&&lists.size()==ConstantValues.DEFAULYPAGESIZE) {
						System.out.println(lists.toString());
						adapter.addMoreData(lists);
						dropDownListView.onBottomComplete();
					} else if(lists != null &&lists.size()>0){
						adapter.addMoreData(lists);
						dropDownListView.setHasMore(false);
						dropDownListView.onBottomComplete();	
					}else
					{
						dropDownListView.setHasMore(false);
						dropDownListView.onBottomComplete();
					}
					page++;
				}else {
					//错误
					//TODO
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		 } else {
//				Toast.makeText(getActivity(), "无数据",
//						Toast.LENGTH_LONG).show();
			}
		}
	};
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		PaperInfo info = adapter.getList().get(position);
		if(info!=null){
		//((FragmentExamActivity)getActivity()).onItemNextSelected(info);
			Fragment newFragment = ExamDetailFragment.newInstance(info);
			addFragmentToStack(newFragment, R.id.simple_fragment);
		}
	}

}
	
