package com.cqvip.mobilevers.view;



import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
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
import com.android.volley.Response.Listener;
import com.cqvip.mobilevers.R;
import com.cqvip.mobilevers.adapter.ExamBClassfyAdapter;
import com.cqvip.mobilevers.config.ConstantValues;
import com.cqvip.mobilevers.db.OneLevelType;
import com.cqvip.mobilevers.http.HttpUtils;
import com.cqvip.mobilevers.http.VersStringRequest;
import com.cqvip.mobilevers.ui.ExamClassfyActivity;

public class BClassfyFragment extends BaseListFragment implements OnItemClickListener{
	
	
	private Map<String, String> gparams;
	private List<OneLevelType> tempList;
	final static String TAG="BClassfyFragment";
	
    String superiorexamtypeid;

    
    
    /**
     * Create a new instance of CountingFragment, providing "num"
     * as an argument.
     */
    public static BClassfyFragment newInstance(String num) {
    	BClassfyFragment f = new BClassfyFragment();

        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putString("superiorexamtypeid", num);
        f.setArguments(args);

        return f;
    }

    /**
     * When creating, retrieve this instance's number from its arguments.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  superiorexamtypeid = getArguments() != null ? getArguments().getString("superiorexamtypeid") : 0+"";
        //获取数据
    }

    /**
     * The Fragment's UI is just a simple text view showing its
     * instance number.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
		if(reuseView()) {
			return view;
		}
		view = inflater.inflate(R.layout.main_tab_exam, container, false);
    	//view = inflater.inflate(R.layout.main_tab_exam, null);
	   	 listview = (ListView) view.findViewById(R.id.lst_next_classy);

	   	String url = ConstantValues.SERVER_URL+ConstantValues.GetKnowledgeClassList_ADDR;// url
	   	superiorexamtypeid = getArguments() != null ? getArguments().getString("superiorexamtypeid") : 0+""; //参数
	   	
	   	getData(url,superiorexamtypeid);
	   	
	   	listview.setOnItemClickListener(this);
        return view;
    }
    
    private void getData(String url,String superiorexamtypeid) {
		// TODO Auto-generated method stub
    	//数据库获取
    	
    	
    	//网络获取
    	getDataFromNet(url,superiorexamtypeid);
		
	}

	/**
     * 从网络获取数据
     * @param url
     * @param superiorexamtypeid
     */
	private void getDataFromNet(String url,String superiorexamtypeid) {
		customProgressDialog.show();
		gparams=new HashMap<String, String>();			
		gparams.put("parentId", superiorexamtypeid);
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
							mAdapter = new ExamBClassfyAdapter(
									getActivity(), tempList);
							listview.setAdapter(mAdapter);
							//setDataToDatabase();
						}
					
				}else {
					//登陆错误
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

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		String nextId = tempList.get(position).getExamtypeid().toString();
		if(tempList.get(position).getHaschildren()){
			Fragment newFragment = BClassfyFragment.newInstance(nextId);
			addFragmentToStack(newFragment, R.id.simple_fragment);
		}else{
			Intent intent = new Intent(getActivity(),ExamClassfyActivity.class);
			intent.putExtra("subjectId", nextId);
			startActivity(new Intent(getActivity(),ExamClassfyActivity.class));
			
		}
	}

}
