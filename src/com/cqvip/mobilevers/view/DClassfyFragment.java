package com.cqvip.mobilevers.view;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request.Method;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.Volley;
import com.cqvip.mobilevers.R;
import com.cqvip.mobilevers.adapter.ExamBClassfyAdapter;
import com.cqvip.mobilevers.config.ConstantValues;
import com.cqvip.mobilevers.entity.ExamInfo;
import com.cqvip.mobilevers.exception.ErrorVolleyThrow;
import com.cqvip.mobilevers.http.HttpConnect;
import com.cqvip.mobilevers.http.VersStringRequest;
import com.cqvip.mobilevers.ui.ExamClassfyActivity;

public class DClassfyFragment extends BaseListFragment implements OnItemClickListener{
	
	
	
	private List<ExamInfo> tempList;
    String mNum;
    private Map<String, String> params;
    

    
    /**
     * Create a new instance of CountingFragment, providing "num"
     * as an argument.
     */
    public static DClassfyFragment newInstance(String num) {
    	DClassfyFragment f = new DClassfyFragment();

        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putString("num", num);
        f.setArguments(args);

        return f;
    }

    /**
     * When creating, retrieve this instance's number from its arguments.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNum = getArguments() != null ? getArguments().getString("num") : 0+"";
        //获取数据
    }

    /**
     * The Fragment's UI is just a simple text view showing its
     * instance number.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.main_tab_exam, container, false);
	   	listview = (ListView) v.findViewById(R.id.lst_next_classy);

	   	params = new HashMap<String, String>();
		params.put("id", mNum);
		getStringDate(ConstantValues.durl, listview);
	   	   
	   	   listview.setOnItemClickListener(this);
        return v;
    }

    private void getStringDate(String url, final ListView listview) {
		mQueue = Volley.newRequestQueue(getActivity());
		volleyErrorListener = new ErrorVolleyThrow(getActivity(), null);
		VersStringRequest myReq = new VersStringRequest(Method.GET, url,
				backlistener, volleyErrorListener) {

<<<<<<< HEAD
	 			@Override
	 			protected String doInBackground(String... params) {
	 				//向服务器发送请求的数据
//	 				List<NameValuePair> values=new ArrayList<NameValuePair>();
//	 				values.add(new BasicNameValuePair("startId", startId+""));
//	 				values.add(new BasicNameValuePair("categoryId", categoryId+""));
//	 				values.add(new BasicNameValuePair("type", type+""));
//	 				return HttpConnect.getNews(params[0],values);
	 				return HttpConnect.getNews(params[0],null);
	 			}

	 			@Override
	 			protected void onPostExecute(String result) {
	 				super.onPostExecute(result);
	 				System.out.println("==================");
	 				if(result!=null){
	 					System.out.println("=========result======");
	 					tempList =	parserJsonData(result,view);
	 					if(tempList!=null&& !tempList.isEmpty()){
	 						mAdapter=new  ExamBClassfyAdapter(getActivity(), tempList);
	 						view.setAdapter(mAdapter);
	 					}
	 					//更新数据
	 				}else{
						System.out.println("======no=============");
					}
//	 				if(type==0){
//	 				   view.findViewById(R.id.pb).setVisibility(View.GONE);
//	 				}
	 				
	 			}

	 			@Override
	 			protected void onPreExecute() {
	 				super.onPreExecute();
//	 				if(type==0){
	 					//view.findViewById(R.id.lst_next_classy).setVisibility(View.VISIBLE);	
//	 				}
	 				
	 			}
	 			
	 		}
	 		private List<ExamInfo> parserJsonData(String data,View view){
				List<ExamInfo> mtempList=new ArrayList<ExamInfo>();
				try{
					JSONObject js = new JSONObject(data);
					JSONArray arrayList= js.getJSONArray("users");
					for(int i=0;i<arrayList.length();i++){
						JSONObject obj=arrayList.getJSONObject(i);
						ExamInfo detail=new ExamInfo();
						detail.setId(obj.getString("id"));
						detail.setTitle(obj.getString("title"));
						detail.setCount(obj.getString("count"));
						
						mtempList.add(detail);
					}
					System.out.println(mtempList.size());
					System.out.println(mtempList.toString());
					return mtempList;
				}catch(Exception e){
					e.printStackTrace();
				}
				return null;
=======
			@Override
			protected Map<String, String> getParams() throws AuthFailureError {
				return params;
>>>>>>> branch 'master' of https://github.com/linghp/examvip.git
			}
		};
		mQueue.add(myReq);
	}

	private Listener<String> backlistener = new Listener<String>() {
		@Override
		public void onResponse(String response) {
			System.out.println(response);
			if (response != null) {
				tempList = ExamInfo.parserJsonData(response);
				if (tempList != null && !tempList.isEmpty()) {
					listview.setAdapter(new ExamBClassfyAdapter(getActivity(),
							tempList));
				}
			} else {
				Toast.makeText(getActivity(), "无数据", Toast.LENGTH_LONG).show();
			}

		}
	};

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		//mCallbacks.onItemDNextSelected(tempList.get(position).getId());
		startActivity(new Intent(getActivity(),ExamClassfyActivity.class));
	}

}
