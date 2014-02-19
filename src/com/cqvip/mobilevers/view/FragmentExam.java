package com.cqvip.mobilevers.view;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.GetChars;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.cqvip.mobilevers.R;

public class FragmentExam extends Fragment implements OnClickListener,OnPageChangeListener{
	static final int NUM_ITEMS = 10;

	MyAdapter mAdapter;
private Context context;
	ViewPager mPager;
	int currentpage=0;
	final static String TAG="FragmentExam";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		context=getActivity();
		View view=inflater.inflate(R.layout.fragment_exam, container, false);
		mAdapter = new MyAdapter(getActivity().getSupportFragmentManager(),context);
		initView(view);
		return view;
	}
	
	@Override
	public void onResume() {
		mAdapter.notifyDataSetChanged();
		super.onResume();
	}


	private void initView(View view){
		mPager = (ViewPager) view.findViewById(R.id.pager);
		mPager.setOnPageChangeListener(this);
		mPager.setAdapter(mAdapter);
		//mPager.setOffscreenPageLimit(5);
//		ViewGroup answercard=(ViewGroup) view. findViewById(R.id.answercard_ll);
//		answercard.setOnClickListener(this);
		Button button = (Button)  view.findViewById(R.id.goto_first);
		button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if(currentpage>0)
				mPager.setCurrentItem(--currentpage);
			}
		});
		button = (Button)  view.findViewById(R.id.goto_last);
		button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if(currentpage<NUM_ITEMS-1)
				mPager.setCurrentItem(++currentpage);
			}
		});
	}
	
	public static class MyAdapter extends FragmentStatePagerAdapter {
		private Context context;
//		public MyAdapter(FragmentManager fm) {
//			super(fm);
//		}
		public MyAdapter(FragmentManager fm,Context context) {
			super(fm);
			this.context=context;
		}

		@Override
		public int getCount() {
			return NUM_ITEMS;
		}

		@Override
		public Fragment getItem(int position) {
			Log.i(TAG, "MyAdapter_getItem:"+position);
			return ExamFragment.newInstance(position,context);
		}
		
		@Override
		public int getItemPosition(Object object) {
			// TODO Auto-generated method stub
			return POSITION_NONE;
		}
	}
	
	public static class ExamFragment extends Fragment {
		public ExamFragment() {
		}
		int mNum;
		
		
		
	    @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	            Bundle savedInstanceState) {
	        View v = inflater.inflate(R.layout.fragment_exampager, container, false);
	        View tv = v.findViewById(R.id.text);
	        ((TextView)tv).setText("Fragment #" + mNum);
	        tv.setBackgroundDrawable(getResources().getDrawable(android.R.drawable.gallery_thumb));
	        
	        return v;
	    }
	    
		static ExamFragment newInstance(int num,Context context) {
			
			ExamFragment f =(ExamFragment) ExamFragment.instantiate(context, ExamFragment.class.getName());
			//ExamFragment f =new ExamFragment();
			f.mNum=num;
			// Supply num input as an argument.
			Bundle args = new Bundle();
			args.putInt("num", num);
			f.setArguments(args);

			return f;
		}
		
		@Override
		public void onResume() {
			Log.i(TAG, "ExamFragment_onResume"+mNum);
			super.onResume();
		}
		
		@Override
		public void onPause() {
			Log.i(TAG, "ExamFragment_onPause"+mNum);
			super.onPause();
		}
		
		@Override
		public void onStop() {
			Log.i(TAG, "ExamFragment_onStop"+mNum);
			super.onStop();
		}
		@Override
		public void onDestroyView() {
			Log.i(TAG, "ExamFragment_onDestroyView");
			super.onDestroyView();
		}
		@Override
		public void onDetach() {
			Log.i(TAG, "ExamFragment_onDetach");
			super.onDetach();
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.answercard_ll:
//			addFragmentToStack();
//			Log.i(TAG, "answercard_ll");
			break;

		default:
			break;
		}
	}

	@Override
	public void onPageScrolled(int position, float positionOffset,
			int positionOffsetPixels) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPageSelected(int position) {
		Log.i(TAG, "onPageSelected_position:"+position);
		currentpage=position;
	}

	@Override
	public void onPageScrollStateChanged(int state) {
		// TODO Auto-generated method stub
		
	}

	
	
//	public static class ArrayListFragment extends ListFragment {
//		int mNum;
//
//		/**
//		 * Create a new instance of CountingFragment, providing "num" as an
//		 * argument.
//		 */
//		static ArrayListFragment newInstance(int num) {
//			ArrayListFragment f = new ArrayListFragment();
//
//			// Supply num input as an argument.
//			Bundle args = new Bundle();
//			args.putInt("num", num);
//			f.setArguments(args);
//
//			return f;
//		}
//
//		/**
//		 * When creating, retrieve this instance's number from its arguments.
//		 */
//		@Override
//		public void onCreate(Bundle savedInstanceState) {
//			super.onCreate(savedInstanceState);
//			mNum = getArguments() != null ? getArguments().getInt("num") : 1;
//		}
//
//		/**
//		 * The Fragment's UI is just a simple text view showing its instance
//		 * number.
//		 */
//		@Override
//		public View onCreateView(LayoutInflater inflater, ViewGroup container,
//				Bundle savedInstanceState) {
//			View v = inflater.inflate(R.layout.fragment_pager_list, container,
//					false);
//			View tv = v.findViewById(R.id.text);
//			((TextView) tv).setText("Fragment #" + mNum);
//			return v;
//		}
//
//		@Override
//		public void onActivityCreated(Bundle savedInstanceState) {
//			super.onActivityCreated(savedInstanceState);
//			setListAdapter(new ArrayAdapter<String>(getActivity(),
//					android.R.layout.simple_list_item_1, Cheeses.sCheeseStrings));
//		}
//
//		@Override
//		public void onListItemClick(ListView l, View v, int position, long id) {
//			Log.i("FragmentList", "Item clicked: " + id);
//		}
//	}
}
