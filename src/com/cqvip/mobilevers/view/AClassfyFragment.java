package com.cqvip.mobilevers.view;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.os.AsyncTask;
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
import com.android.volley.toolbox.Volley;
import com.android.volley.Response;
import com.cqvip.mobilevers.R;
import com.cqvip.mobilevers.adapter.ExamAClassfyAdapter;
import com.cqvip.mobilevers.config.ConstantValues;
import com.cqvip.mobilevers.entity.ExamInfo;
import com.cqvip.mobilevers.exception.ErrorVolleyThrow;
import com.cqvip.mobilevers.http.VersStringRequest;

/**
 * 获取总分类，fragment
 * @author luojiang
 *
 */
public class AClassfyFragment extends BaseListFragment implements
		OnItemClickListener {

	private List<ExamInfo> tempList;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.main_tab_exam, null);
		final ListView listview = (ListView) view
				.findViewById(R.id.lst_next_classy);
		String url = ConstantValues.aurl;//获取分类路径
		getStringDate(listview,url);
		listview.setOnItemClickListener(this);
		return view;
	}

	/**
	 * 从网络获取书籍
	 * @param listview
	 */
	private void getStringDate(final ListView listview,String url) {
		mQueue = Volley.newRequestQueue(getActivity());
		volleyErrorListener = new ErrorVolleyThrow(getActivity(), null);
		VersStringRequest myReq = new VersStringRequest(Method.GET,
				url, new Response.Listener<String>() {

					@Override
					public void onResponse(String result) {
						System.out.println(result);
						if (result != null) {
							tempList = ExamInfo.parserJsonData(result);
							if (tempList != null && !tempList.isEmpty()) {
								listview.setAdapter(new ExamAClassfyAdapter(
										getActivity(), tempList));
							}
						} else {
							Toast.makeText(getActivity(), "无数据",
									Toast.LENGTH_LONG).show();
						}

<<<<<<< HEAD
	@Override
	public void onDetach() {
		super.onDetach();

		// Reset the active callbacks interface to the dummy implementation.
//		mCallbacks = sDummyCallbacks;
	}

	
	
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
		   		view = inflater.inflate(R.layout.main_tab_exam, null);
		   	   ListView listview = (ListView) view.findViewById(R.id.lst_next_classy);
		   	   refreshData(ConstantValues.aurl, listview);
		   	listview.setOnItemClickListener(this);
		   //从网络上获取数据
           return view;
       }
	 
	   
	   private void refreshData(String url,ListView view) {
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
	 						mAdapter=new  ExamAClassfyAdapter(getActivity(), tempList);
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
=======
>>>>>>> branch 'master' of https://github.com/linghp/examvip.git
					}
				}, volleyErrorListener);
		mQueue.add(myReq);
	}

<<<<<<< HEAD
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				//System.out.println("=======onItemClick======AdapterView====");
				//mCallbacks.onItemSelected(tempList.get(position).getId());
				Fragment newFragment = BClassfyFragment.newInstance(tempList.get(position).getId());
				addFragmentToStack(newFragment,R.id.simple_fragment);
				Toast.makeText(getActivity(), "11", 1).show();
			}
			
=======
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {

		Fragment newFragment = BClassfyFragment.newInstance(tempList.get(
				position).getId());
		addFragmentToStack(newFragment, android.R.id.content);
	}

>>>>>>> branch 'master' of https://github.com/linghp/examvip.git
}
