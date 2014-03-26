package com.cqvip.mobilevers.view;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.android.volley.Request.Method;
import com.android.volley.Response.Listener;
import com.cqvip.mobilevers.R;
import com.cqvip.mobilevers.adapter.ExamPaperAdapter;
import com.cqvip.mobilevers.config.ConstantValues;
import com.cqvip.mobilevers.entity.Paper;
import com.cqvip.mobilevers.entity.PaperInfo;
import com.cqvip.mobilevers.http.HttpUtils;
import com.cqvip.mobilevers.http.VersStringRequest;
import com.cqvip.mobilevers.ui.base.BaseFragment;
import com.cqvip.mobilevers.widget.DropDownListView;

public class ExamPaperListFragment extends BaseFragment implements OnItemClickListener {

	
	public static final String ARG_NUMBER = "number";
	public static final String ARG_ID = "subjectId";
	private DropDownListView listview;
    private Map<String, String> gparams;
    private ExamPaperAdapter adapter; 
    private int page;
   	private NextCallbacks mCallbacks = sDummyCallbacks;
	
	public interface NextCallbacks {
		/**
		 * Callback for when an item has been selected.
		 */
		public void onItemNextSelected(PaperInfo info);
	}

	private static NextCallbacks sDummyCallbacks = new NextCallbacks() {
		@Override
		public void onItemNextSelected(PaperInfo info) {
		}
	};
    
	
    /**
     * Create a new instance of ExamPaperListFragment, providing "num"
     * as an argument.
     */
    public static ExamPaperListFragment newInstance(int num,String id) {
    	ExamPaperListFragment f = new ExamPaperListFragment();

        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putInt(ARG_NUMBER, num);
        args.putString(ARG_ID, id);
        f.setArguments(args);
        return f;
    }

	
    @Override
    public void onSaveInstanceState(Bundle outState) {
    	// TODO Auto-generated method stub
    	super.onSaveInstanceState(outState);
    	Log.i("ExamPaperListFragment","========onSaveInstanceState==========");
    }

    
    
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		Log.i("ExamPaperListFragment","========onCreateView==========");
		
		View rootView = inflater.inflate(
				R.layout.fragment_exam_classfy_dummy, container, false);
		listview = (DropDownListView) rootView
				.findViewById(R.id.lst_exampaper);
		listview.setOnItemClickListener(this);
		Bundle bundle = getArguments();
		final int type = bundle.getInt(ARG_NUMBER);
		final String subjectId = bundle.getString(ARG_ID);
		page = 1;
		getData(type,subjectId,page,ConstantValues.GETFIRSTPAGE);
		listview.setOnBottomListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				getData(type,subjectId,page+1,ConstantValues.GETNEXTPAGE);
				page++;
			}
			
		});
		
		return rootView;
	}


	private void getData(int type, String subjectId,int page,int getWhichPage) {
		
		
		//请求网络
		getDataFromNet(type,subjectId,page,getWhichPage);
		
	}


	private void getDataFromNet(int type, String subjectId,int page,int getWhichPage) {
		gparams = new HashMap<String, String>();
		Listener<String> listner;
		if(getWhichPage==ConstantValues.GETFIRSTPAGE){
			listner = backlistener;
	     }else{
	    	 listner = backlistenerMore;
	     }
			
		switch (type) {
		case 0://请求真题
			gparams.put("type",ConstantValues.TYPE_EXAM_REAL+"");
			break;
		case 1://请求模拟
			gparams.put("type", ConstantValues.TYPE_EXAM_SIMULATE+"");
			break;

		default:
			break;
		}
		gparams.put("kClassId", subjectId);
		gparams.put("page", page+"");
		gparams.put("pageSize",ConstantValues.DEFAULYPAGESIZE+"");
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
					listview.setAdapter(adapter);

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
//				Toast.makeText(getActivity(), "无数据",
//						Toast.LENGTH_LONG).show();
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
		PaperInfo info = adapter.getList().get(position);
		if(info!=null){
			Fragment newFragment = ExamDetailFragment.newInstance(info);
			addFragmentToStack(newFragment, R.id.simple_fragment);
		}
	}  
}
