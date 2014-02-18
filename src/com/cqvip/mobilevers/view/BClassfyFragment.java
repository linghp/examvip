package com.cqvip.mobilevers.view;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request.Method;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.Volley;
import com.cqvip.mobilevers.R;
import com.cqvip.mobilevers.adapter.ExamBClassfyAdapter;
import com.cqvip.mobilevers.config.ConstantValues;
import com.cqvip.mobilevers.entity.ExamInfo;
import com.cqvip.mobilevers.exception.ErrorVolleyThrow;
import com.cqvip.mobilevers.http.VersStringRequest;

/**
 * 获取二级分类
 * 
 * @author luojiang
 * 
 */
public class BClassfyFragment extends BaseListFragment implements
		OnItemClickListener {

	private List<ExamInfo> tempList;
<<<<<<< HEAD
=======

	String mNum;

	private Map<String, String> params;

	public static BClassfyFragment newInstance(String num) {
		BClassfyFragment f = new BClassfyFragment();

		Bundle args = new Bundle();
		args.putString("num", num);
		f.setArguments(args);

		return f;
	}

>>>>>>> branch 'master' of https://github.com/linghp/examvip.git
	/**
	 * When creating, retrieve this instance's number from its arguments.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mNum = getArguments() != null ? getArguments().getString("num")
				: 0 + "";
		// 获取数据
	}

	/**
	 * The Fragment's UI is just a simple text view showing its instance number.
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.main_tab_exam, container, false);
	    listview = (ListView) v.findViewById(R.id.lst_next_classy);

		params = new HashMap<String, String>();
		params.put("id", mNum);
		getStringDate(ConstantValues.burl, listview);

		listview.setOnItemClickListener(this);
		return v;
	}

	private void getStringDate(String url, final ListView listview) {
		mQueue = Volley.newRequestQueue(getActivity());
		volleyErrorListener = new ErrorVolleyThrow(getActivity(), null);
		VersStringRequest myReq = new VersStringRequest(Method.GET, url,
				backlistener, volleyErrorListener) {

<<<<<<< HEAD
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
		if (view != null) {
			ViewGroup parent = (ViewGroup) view.getParent();
			if (parent != null) {
				parent.removeView(view);
			}
			return view;
		}
		Log.i("BClassfyFragment", "onCreateView");
        view = inflater.inflate(R.layout.main_tab_exam, container, false);
    	//view = inflater.inflate(R.layout.main_tab_exam, null);
	   	   ListView listview = (ListView)view.findViewById(R.id.lst_next_classy);
	   	   refreshData(ConstantValues.burl, listview);
	   	listview.setOnItemClickListener(this);
        return view;
    }
    
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	super.onActivityCreated(savedInstanceState);
    }

	private void refreshData(String url, ListView view) {
		GetNewsFromServer asyncTask=new GetNewsFromServer(view,0,0,0);
		   asyncTask.execute(url);
		   
	   }		   	   
	   
	   /**
	 		 * 去服务器获取数据
	 		 * @author Administrator
	 		 *
	 		 */
	 		@SuppressWarnings("unused")
	 		private class GetNewsFromServer extends AsyncTask<String, Integer, String>{
	 			private ListView view;
//	 			private int startId;
//	 			private int categoryId;
//	 			private int type;
	 			public GetNewsFromServer(ListView view,int startId,int categoryId,int type){
	 				this.view=view;
//	 				this.startId=startId;
//	 				this.categoryId=categoryId;
//	 				this.type=type;
	 			}

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
<<<<<<< HEAD
		Fragment newFragment = CClassfyFragment.newInstance(tempList.get(position).getId());
		//mCallbacks.onItemNextSelected(tempList.get(position).getId());
		addFragmentToStack(newFragment,R.id.simple_fragment);
		Toast.makeText(getActivity(), "11", 1).show();
=======
		// mCallbacks.onItemNextSelected(tempList.get(position).getId());
		
		Fragment newFragment = CClassfyFragment.newInstance(tempList.get(
				position).getId());
		addFragmentToStack(newFragment, android.R.id.content);
//		if(isNochildren()){
//			
//			Fragment newFragment = DClassfyFragment.newInstance(tempList.get(
//					position).getId());
//			addFragmentToStack(newFragment, android.R.id.content);
//		}else{
//			Fragment newFragment = BClassfyFragment.newInstance(tempList.get(
//					position).getId());
//			addFragmentToStack(newFragment, android.R.id.content);
//		}
	}

	private boolean isNochildren() {
		
		return false;
>>>>>>> branch 'master' of https://github.com/linghp/examvip.git
	}

}
