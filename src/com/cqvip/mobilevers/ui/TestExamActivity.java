package com.cqvip.mobilevers.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.support.v4.view.ViewPager;
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

public class TestExamActivity extends FragmentActivity implements OnClickListener{
	static final int NUM_ITEMS = 10;

	MyAdapter mAdapter;

	ViewPager mPager;
	int currentpage=1;
	final static String TAG="TestExamActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test_exam);

		mAdapter = new MyAdapter(getSupportFragmentManager());

		mPager = (ViewPager) findViewById(R.id.pager);
		mPager.setAdapter(mAdapter);

		initView();

	}

	private void initView(){
		ViewGroup answercard=(ViewGroup) findViewById(R.id.answercard_ll);
		answercard.setOnClickListener(this);
		Button button = (Button) findViewById(R.id.goto_first);
		button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if(currentpage>1)
				mPager.setCurrentItem(--currentpage);
			}
		});
		button = (Button) findViewById(R.id.goto_last);
		button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if(currentpage<NUM_ITEMS)
				mPager.setCurrentItem(++currentpage);
			}
		});
	}
	
	public static class MyAdapter extends FragmentStatePagerAdapter {
		public MyAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public int getCount() {
			return NUM_ITEMS;
		}

		@Override
		public Fragment getItem(int position) {
			return ExamFragment.newInstance(position);
		}
	}
	
	public static class ExamFragment extends Fragment {
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
	    
		static ExamFragment newInstance(int num) {
			ExamFragment f = new ExamFragment();

			// Supply num input as an argument.
			Bundle args = new Bundle();
			args.putInt("num", num);
			f.setArguments(args);

			return f;
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.answercard_ll:
			addFragmentToStack();
			Log.i(TAG, "answercard_ll");
			break;

		default:
			break;
		}
	}

	void addFragmentToStack() {

        // Instantiate a new fragment.
        Fragment newFragment = new FragmentAnswerScard();

        // Add the fragment to the activity, pushing this transaction
        // on to the back stack.
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.anim.slide_right_in,
        		0,
        		R.anim.slide_right_out,
        		0);
        ft.replace(R.id.exam_fl, newFragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.addToBackStack(null);
        ft.commit();
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
