package com.cqvip.mobilevers.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.cqvip.mobilevers.R;
import com.cqvip.mobilevers.ui.base.BaseFragmentActivity;
import com.cqvip.mobilevers.view.FragmentAnswerScard;

public class ExamActivity extends BaseFragmentActivity implements
		OnPageChangeListener {

	final static String TAG = "ExamActivity";
	static final int NUM_ITEMS = 10;
	MyAdapter mAdapter;
	private Context context;
	ViewPager mPager;
	int currentpage = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_exam);
		context = this;
		// Fragment newFragment = new FragmentExam();
		// FragmentTransaction ft =
		// getSupportFragmentManager().beginTransaction();
		// ft.add(R.id.exam_fl, newFragment).commit();
		mAdapter = new MyAdapter(getSupportFragmentManager(), context);
		initView();
	}

	private void initView() {
		mPager = (ViewPager) findViewById(R.id.pager);
		mPager.setOnPageChangeListener(this);
		mPager.setAdapter(mAdapter);
		// mPager.setOffscreenPageLimit(5);
		// ViewGroup answercard=(ViewGroup) view.
		// findViewById(R.id.answercard_ll);
		// answercard.setOnClickListener(this);
		Button button = (Button) findViewById(R.id.goto_first);
		button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (currentpage > 0)
					mPager.setCurrentItem(--currentpage);
			}
		});
		button = (Button) findViewById(R.id.goto_last);
		button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (currentpage < NUM_ITEMS - 1)
					mPager.setCurrentItem(++currentpage);
			}
		});
	}

	// @Override
	// public void onBackPressed() {
	// Log.i(TAG, getSupportFragmentManager().getBackStackEntryCount()+"");
	// if(getSupportFragmentManager().getBackStackEntryCount()>0){
	// getSupportFragmentManager().popBackStack();
	// }
	// else{
	// super.onBackPressed();
	// }
	// }

	public void addFragmentToStack(View v) {

		// Instantiate a new fragment.
		Fragment newFragment = new FragmentAnswerScard();

		// Add the fragment to the activity, pushing this transaction
		// on to the back stack.
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		// ft.setCustomAnimations(R.anim.slide_right_in,
		// 0,
		// R.anim.slide_right_out,
		// 0);
		ft.replace(R.id.exam_fl, newFragment);
		ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
		ft.addToBackStack(null);
		ft.commit();
		// startActivity(new Intent(this, FragmentMineActivity.class));
	}

	public static class MyAdapter extends FragmentStatePagerAdapter {
		private Context context;

		// public MyAdapter(FragmentManager fm) {
		// super(fm);
		// }
		public MyAdapter(FragmentManager fm, Context context) {
			super(fm);
			this.context = context;
		}

		@Override
		public int getCount() {
			return NUM_ITEMS;
		}

		@Override
		public Fragment getItem(int position) {
			Log.i(TAG, "MyAdapter_getItem:" + position);
			return ExamFragment.newInstance(position, context);
		}

		@Override
		public int getItemPosition(Object object) {
			// TODO Auto-generated method stub
			return POSITION_NONE;
		}
	}

	public static class ExamFragment extends Fragment {
		int mNum;

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View v = inflater.inflate(R.layout.fragment_exampager, container,
					false);
			View tv = v.findViewById(R.id.text);
			((TextView) tv).setText("Fragment #" + mNum);
			tv.setBackgroundDrawable(getResources().getDrawable(
					android.R.drawable.gallery_thumb));

			return v;
		}

		static ExamFragment newInstance(int num, Context context) {

			ExamFragment f = (ExamFragment) ExamFragment.instantiate(context,
					ExamFragment.class.getName());
			// ExamFragment f =new ExamFragment();
			f.mNum = num;
			// Supply num input as an argument.
			Bundle args = new Bundle();
			args.putInt("num", num);
			f.setArguments(args);

			return f;
		}
	}

	@Override
	protected void onResume() {
		Log.i(TAG, "onResume");
		super.onResume();
	}

	@Override
	protected void onPause() {
		Log.i(TAG, "onPause");
		super.onPause();
	}

	@Override
	protected void onStop() {
		Log.i(TAG, "onStop");
		super.onStop();
	}

	@Override
	public void onPageScrolled(int position, float positionOffset,
			int positionOffsetPixels) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageSelected(int position) {
		Log.i(TAG, "onPageSelected_position:" + position);
		currentpage = position;
		if (position == 0) {
			isLeftFragment = true;
		} else {
			isLeftFragment = false;
		}
	}

	@Override
	public void onPageScrollStateChanged(int state) {
		// TODO Auto-generated method stub

	}
	
}
