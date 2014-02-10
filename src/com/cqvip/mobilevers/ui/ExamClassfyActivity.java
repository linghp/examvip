package com.cqvip.mobilevers.ui;

import java.util.List;
import java.util.Locale;
import java.util.zip.Inflater;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.cqvip.mobilevers.R;
import com.cqvip.mobilevers.adapter.ExamPaperAdapter;
import com.cqvip.mobilevers.config.ConstantValues;
import com.cqvip.mobilevers.entity.ExamInfo;
import com.cqvip.mobilevers.entity.Paper;
import com.cqvip.mobilevers.http.HttpConnect;

public class ExamClassfyActivity extends FragmentActivity implements
		ActionBar.TabListener {
	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a
	 * {@link android.support.v4.app.FragmentPagerAdapter} derivative, which
	 * will keep every loaded fragment in memory. If this becomes too memory
	 * intensive, it may be best to switch to a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	SectionsPagerAdapter mSectionsPagerAdapter;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;
	private static List<ExamInfo>  reallists;
	private static List<ExamInfo>  simulatelists;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_exam_classfy);
		// Set up the action bar.
		final ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setTitle("试题列表");
		// Show the Up button in the action bar.
		actionBar.setDisplayHomeAsUpEnabled(true);

		// Create the adapter that will return a fragment for each of the three
		// primary sections of the app.
		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getSupportFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);

		// When swiping between different sections, select the corresponding
		// tab. We can also use ActionBar.Tab#select() to do this if we have
		// a reference to the Tab.
		mViewPager
				.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
					@Override
					public void onPageSelected(int position) {
						actionBar.setSelectedNavigationItem(position);
					}
				});

		// For each of the sections in the app, add a tab to the action bar.
		for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
			// Create a tab with text corresponding to the page title defined by
			// the adapter. Also specify this Activity object, which implements
			// the TabListener interface, as the callback (listener) for when
			// this tab is selected.
			actionBar.addTab(actionBar.newTab()
					.setText(mSectionsPagerAdapter.getPageTitle(i))
					.setTabListener(this));
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.exam_classfy, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onTabSelected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
		// When the given tab is selected, switch to the corresponding page in
		// the ViewPager.
		mViewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	@Override
	public void onTabReselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			// getItem is called to instantiate the fragment for the given page.
			// Return a DummySectionFragment (defined as a static inner class
			// below) with the page number as its lone argument.
			Fragment fragment = new DummySectionFragment();
			Bundle args = new Bundle();
			args.putInt(DummySectionFragment.ARG_SECTION_NUMBER, position + 1);
			fragment.setArguments(args);
			return fragment;
		}

		@Override
		public int getCount() {
			// Show 3 total pages.
			return 2;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			Locale l = Locale.getDefault();
			switch (position) {
			case 0:
				return getString(R.string.title_section1).toUpperCase(l);
			case 1:
				return getString(R.string.title_section2).toUpperCase(l);
			}
			return null;
		}
	}

	/**
	 * A dummy fragment representing a section of the app, but that simply
	 * displays dummy text.
	 */
	public static class DummySectionFragment extends Fragment implements OnItemClickListener {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		public static final String ARG_SECTION_NUMBER = "section_number";

		public DummySectionFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(
					R.layout.fragment_exam_classfy_dummy, container, false);
			int select = getArguments().getInt(ARG_SECTION_NUMBER);
			ListView dummylist = (ListView) rootView
					.findViewById(R.id.lst_exam);
				refreshData(ConstantValues.paperurl,dummylist,inflater,select);
			dummylist.setOnItemClickListener(this);
			return rootView;
		}

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			int select = getArguments().getInt(ARG_SECTION_NUMBER);
			switch (select) {
			case 1:
				Toast.makeText(getActivity(), ""+reallists.get(position).getTitle(), 1).show();
				startActivity(new Intent(getActivity(),ExamDetailActivity.class));
				break;
			case 2:
				Toast.makeText(getActivity(), ""+simulatelists.get(position).getTitle(), 1).show();
				startActivity(new Intent(getActivity(),ExamDetailActivity.class));
				break;

			default:
				break;
			}
		}
	}
	
	
	
	private static void refreshData(String url,ListView v,LayoutInflater inflater,int select) {
		GetNewsFromServer asyncTask=new GetNewsFromServer(v,inflater,0,0,select);
		   asyncTask.execute(url);
		   
	   }	


	
	private static class GetNewsFromServer extends AsyncTask<String, Integer, String>{
			
//			private int startId;
//			private int categoryId;
			private int type;
			private ListView lv;
			private LayoutInflater inflater;
			public GetNewsFromServer(ListView v,LayoutInflater inflater,int startId,int categoryId,int type){
			
//				this.startId=startId;
//				this.categoryId=categoryId;
				this.type=type;
				this.inflater = inflater;
				this.lv = v;
			}

			@Override
			protected String doInBackground(String... params) {
				//向服务器发送请求的数据
//				List<NameValuePair> values=new ArrayList<NameValuePair>();
//				values.add(new BasicNameValuePair("startId", startId+""));
//				values.add(new BasicNameValuePair("categoryId", categoryId+""));
//				values.add(new BasicNameValuePair("type", type+""));
//				return HttpConnect.getNews(params[0],values);
				return HttpConnect.getNews(params[0],null);
			}

			@Override
			protected void onPostExecute(String result) {
				super.onPostExecute(result);
				if(result!=null){
					System.out.println("=========result======");
					Paper p =	Paper.parserJsonData(result);
					//真题
					System.out.println("type:"+type);
					switch (type) {
					case 1:
						List<ExamInfo> list_r = p.getReal();
						System.out.println(list_r);
						if(list_r!=null&& !list_r.isEmpty()){
							ExamPaperAdapter adapter =	new  ExamPaperAdapter(inflater, list_r);
							lv.setAdapter(adapter);
							reallists = list_r;
						//更新数据
					}		
						break;
					case 2:
						List<ExamInfo> list_s = p.getSimulate();
						System.out.println(list_s);
						if(list_s!=null&& !list_s.isEmpty()){
							ExamPaperAdapter adapter =	new  ExamPaperAdapter(inflater, list_s);
							lv.setAdapter(adapter);
							simulatelists = list_s;
						//更新数据
					}	
						break;

					default:
						break;
					}
				}else{
				System.out.println("======no=============");
			      }
//				if(type==0){
//				   view.findViewById(R.id.pb).setVisibility(View.GONE);
//				}
				
			}

			@Override
			protected void onPreExecute() {
				super.onPreExecute();
//				if(type==0){
					//view.findViewById(R.id.lst_next_classy).setVisibility(View.VISIBLE);	
//				}
				
			}
			
		}

}
