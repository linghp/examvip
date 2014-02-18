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

import com.cqvip.mobilevers.R;
import com.cqvip.mobilevers.adapter.ExamBClassfyAdapter;
import com.cqvip.mobilevers.config.ConstantValues;
import com.cqvip.mobilevers.entity.ExamInfo;
import com.cqvip.mobilevers.http.HttpConnect;

public class BClassfyFragment extends BaseListFragment implements OnItemClickListener{
	
	
	
	private List<ExamInfo> tempList;

	/**
	 * The fragment's current callback object, which is notified of list item
	 * clicks.
	 */
//	private NextCallbacks mCallbacks = sDummyCallbacks;
//	
//	public interface NextCallbacks {
//		/**
//		 * Callback for when an item has been selected.
//		 */
//		public void onItemNextSelected(String id);
//	}
//
//	private static NextCallbacks sDummyCallbacks = new NextCallbacks() {
//		@Override
//		public void onItemNextSelected(String id) {
//		}
//	};
	
    String mNum;

    
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		// Activities containing this fragment must implement its callbacks.
//		if (!(activity instanceof Callbacks)) {
//			throw new IllegalStateException(
//					"Activity must implement fragment's callbacks.");
//		}

		//mCallbacks = (NextCallbacks) activity;
	}

	@Override
	public void onDetach() {
		super.onDetach();

		// Reset the active callbacks interface to the dummy implementation.
	//	mCallbacks = sDummyCallbacks;
	}

    
    /**
     * Create a new instance of CountingFragment, providing "num"
     * as an argument.
     */
    public static BClassfyFragment newInstance(String num) {
    	BClassfyFragment f = new BClassfyFragment();

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
		view = inflater.inflate(R.layout.main_tab_exam, container, false);
    	//view = inflater.inflate(R.layout.main_tab_exam, null);
	   	   ListView listview = (ListView) view.findViewById(R.id.lst_next_classy);
	   	   refreshData(ConstantValues.burl, listview);
	   	listview.setOnItemClickListener(this);
        return view;
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
			}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Fragment newFragment = CClassfyFragment.newInstance(tempList.get(position).getId());
		//mCallbacks.onItemNextSelected(tempList.get(position).getId());
		addFragmentToStack(newFragment,android.R.id.content);
		Toast.makeText(getActivity(), "11", 1).show();
	}

}
