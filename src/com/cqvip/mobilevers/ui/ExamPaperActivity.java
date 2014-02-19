package com.cqvip.mobilevers.ui;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.AdapterView.OnItemClickListener;

import com.cqvip.mobilevers.R;
import com.cqvip.mobilevers.adapter.ExamPaperAdapter;
import com.cqvip.mobilevers.config.ConstantValues;
import com.cqvip.mobilevers.entity.ExamInfo;
import com.cqvip.mobilevers.entity.Paper;
import com.cqvip.mobilevers.http.HttpConnect;


public class ExamPaperActivity extends FragmentActivity {

//	private ViewPager mTabPager;	
//	private List<ExamInfo> tempList;
//	private Context context;
//	private ListView listview;
//	private ExamPaperAdapter adapter_real,adapter_simulate;
//	
//	private int currIndex = 0;// 当前页卡编号
//	
//	SectionsPagerAdapter mSectionsPagerAdapter;
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		requestWindowFeature(Window.FEATURE_NO_TITLE);
//		context = this;
//		setContentView(R.layout.activity_paper);
//		
//		mSectionsPagerAdapter = new SectionsPagerAdapter(
//				getSupportFragmentManager());
//		
//		  mTabPager = (ViewPager)findViewById(R.id.tabpager);
//		  refreshData(ConstantValues.paperurl);
//		  
//		  mTabPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//				@Override
//				public void onPageSelected(int position) {
//					// RadioButton点击 performClick()
////					if (rg_nav_content != null
////							&& rg_nav_content.getChildCount() > position) {
////						((RadioButton) rg_nav_content.getChildAt(position))
////								.performClick();
////					}
//				}
//
//				@Override
//				public void onPageScrolled(int arg0, float arg1, int arg2) {
//					// TODO Auto-generated method stub
//				}
//
//				@Override
//				public void onPageScrollStateChanged(int arg0) {
//					// TODO Auto-generated method stub
//				}
//			});
//
////		  rg_nav_content.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
////				@Override
////				public void onCheckedChanged(RadioGroup group, int checkedId) {
////						// 滑动动画
////					mTabPager.setCurrentItem(checkedId); // ViewPager
////																// 跟随一起 切换
////						// 记录当前 下标的距最左侧的 距离
////											}
////				}
////			});
////		}
//			
//			mTabPager.setAdapter(mSectionsPagerAdapter);
//	        
//		
//		//	refreshData(ConstantValues.paperurl, lv1, lv2);
//	        
//	}
//	
//	//
//	public   class SectionsPagerAdapter extends FragmentPagerAdapter {
//
//		public SectionsPagerAdapter(FragmentManager fm) {
//			super(fm);
//		}
//
//		@Override
//		public Fragment getItem(int position) {
//			// getItem is called to instantiate the fragment for the given page.
//			// Return a DummySectionFragment (defined as a static inner class
//			// below) with the page number as its lone argument.
//			Fragment fragment = new RealFragment();
//			Bundle args = new Bundle();
//			args.putInt(RealFragment.ARG_SECTION_NUMBER, position);
//			fragment.setArguments(args);
//			return fragment;
//		}
//		
//
//		@Override
//		public int getCount() {
//			// TODO Auto-generated method stub
//			return 2;
//		}
//	}
//	
//	public class RealFragment extends Fragment implements
//	OnItemClickListener {
///**
// * The fragment argument representing the section number for this
// * fragment.
// */
//		public static final String ARG_SECTION_NUMBER = "section_number";
//		
//		public RealFragment() {
//			
//		}
//		@Override
//				public View onCreateView(LayoutInflater inflater, ViewGroup container,
//						Bundle savedInstanceState) {
//					// TODO Auto-generated method stub
//			View v = inflater.inflate(R.layout.listview_paper1, container, false);
//
//			 //更新View
//			     
//			 	ListView listView = (ListView) v
//						.findViewById(R.id.lst_paper1);
//				int i = getArguments().getInt(ARG_SECTION_NUMBER);
//				if (i == 0) {
//				listView.setAdapter(adapter_real);
//				} else if (i == 1) {
//			    listView.setAdapter(adapter_simulate);
//				}
//
//				listView.setOnItemClickListener(this);
//			     
//			     
//					return v;
//					
//				}
//		@Override
//		public void onItemClick(AdapterView<?> parent, View view, int position,
//				long id) {
//			// TODO Auto-generated method stub
//			
//		}
//	}
//	
//	
//	private void refreshData(String url) {
//		GetNewsFromServer asyncTask=new GetNewsFromServer(0,0,0);
//		   asyncTask.execute(url);
//		   
//	   }	
//
//
//	
//	private class GetNewsFromServer extends AsyncTask<String, Integer, String>{
//			
////			private int startId;
////			private int categoryId;
////			private int type;
//			public GetNewsFromServer(int startId,int categoryId,int type){
//			
////				this.startId=startId;
////				this.categoryId=categoryId;
////				this.type=type;
//			}
//
//			@Override
//			protected String doInBackground(String... params) {
//				//向服务器发送请求的数据
////				List<NameValuePair> values=new ArrayList<NameValuePair>();
////				values.add(new BasicNameValuePair("startId", startId+""));
////				values.add(new BasicNameValuePair("categoryId", categoryId+""));
////				values.add(new BasicNameValuePair("type", type+""));
////				return HttpConnect.getNews(params[0],values);
//				return HttpConnect.getNews(params[0],null);
//			}
//
//			@Override
//			protected void onPostExecute(String result) {
//				super.onPostExecute(result);
//				System.out.println("==================");
//				if(result!=null){
//					System.out.println("=========result======");
//					Paper p =	Paper.parserJsonData(result);
//					//真题
//					List<ExamInfo> listreal = p.getReal();
//					if(listreal!=null&& !listreal.isEmpty()){
//					//	adapter_real =	new  ExamPaperAdapter(context, listreal);
//						adapter_real.notifyDataSetChanged();
//						
//					}
//					//模拟
//					List<ExamInfo> listSimulate = p.getSimulate();
//					if(listSimulate!=null&& !listSimulate.isEmpty()){
//					//	adapter_simulate = new  ExamPaperAdapter(context, listSimulate);
//						adapter_simulate.notifyDataSetChanged();
//					}
//					//更新数据
//				}else{
//				System.out.println("======no=============");
//			}
////				if(type==0){
////				   view.findViewById(R.id.pb).setVisibility(View.GONE);
////				}
//				
//			}
//
//			@Override
//			protected void onPreExecute() {
//				super.onPreExecute();
////				if(type==0){
//					//view.findViewById(R.id.lst_next_classy).setVisibility(View.VISIBLE);	
////				}
//				
//			}
//			
//		}
	
}
