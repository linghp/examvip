package com.cqvip.mobilevers.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.toolbox.Volley;
import com.cqvip.mobilevers.R;
import com.cqvip.mobilevers.exception.ErrorVolleyThrow;
import com.cqvip.mobilevers.ui.base.BaseFragmentActivity;
import com.cqvip.mobilevers.view.ExamDetailFragment;
import com.cqvip.mobilevers.view.ExamPaperListFragment;
import com.cqvip.mobilevers.widget.CustomProgressDialog;

public class ExamClassfyActivity extends BaseFragmentActivity implements ExamPaperListFragment.NextCallbacks{

	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a
	 * {@link android.support.v4.app.FragmentPagerAdapter} derivative, which
	 * will keep every loaded fragment in memory. If this becomes too memory
	 * intensive, it may be best to switch to a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	private SectionsPagerAdapter mSectionsPagerAdapter;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	private ViewPager mViewPager;
	private int position;
	
	private RadioGroup rg_nav_content;
	private ImageView iv_nav_indicator;
	private String[] tabTitle; // 标题
	private LayoutInflater mInflater;
	private int indicatorWidth;
	private int currentIndicatorLeft = 0;
	private static String subjectId;
	
	private  RequestQueue mQueue;
	private  ErrorListener volleyErrorListener;
	private  CustomProgressDialog customProgressDialog;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_exam_classfy);
		subjectId = getIntent().getStringExtra("subjectId");
		mQueue=Volley.newRequestQueue(this);
		customProgressDialog=CustomProgressDialog.createDialog(this);
		volleyErrorListener = new  ErrorVolleyThrow(this, customProgressDialog);
		
		initView();
		setListener();

	}
	  private void setListener() {
		  mViewPager
			.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
				@Override
				public void onPageSelected(int position) {
					Log.i("ExamClassfyActivity", "onPageSelected");
					ExamClassfyActivity.this.position=position;
					if (rg_nav_content != null
							&& rg_nav_content.getChildCount() > position) {
						((RadioButton) rg_nav_content.getChildAt(position))
								.performClick();
					}
					if (position == 0) {
						isLeftFragment = true;
					} else {
						isLeftFragment = false;
					}
				}
			});
	
	rg_nav_content
	.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			if (rg_nav_content.getChildAt(checkedId) != null) {
				// 滑动动画
				TranslateAnimation animation = new TranslateAnimation(
						currentIndicatorLeft,
						((RadioButton) rg_nav_content
								.getChildAt(checkedId)).getLeft(),
						0f, 0f);
				animation.setInterpolator(new LinearInterpolator());
				animation.setDuration(100);
				animation.setFillAfter(true);
				// 滑块执行位移动画
				iv_nav_indicator.startAnimation(animation);
				mViewPager.setCurrentItem(checkedId); // ViewPager
														// 跟随一起 切换
				// 记录当前 下标的距最左侧的 距离
				currentIndicatorLeft = ((RadioButton) rg_nav_content
						.getChildAt(checkedId)).getLeft();
			}
		}
	});
		
	}


	public  ErrorListener getVolleyErrorListener() {
		return volleyErrorListener;
	}


	public RequestQueue getRequestQueue(){
	    	return mQueue;
	    }

	    public CustomProgressDialog getCustomDialog(){
	    	return customProgressDialog;
	    }

	private void initView() {

		tabTitle=getResources().getStringArray(R.array.tabtitle);
		rg_nav_content = (RadioGroup) findViewById(R.id.rg_nav_content);
		iv_nav_indicator = (ImageView) findViewById(R.id.iv_nav_indicator);
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		indicatorWidth = dm.widthPixels / tabTitle.length;
		// TODO step0 初始化滑动下标的宽 根据屏幕宽度和可见数量 来设置RadioButton的宽度)
		LayoutParams cursor_Params = iv_nav_indicator.getLayoutParams();
		cursor_Params.width = indicatorWidth;// 初始化滑动下标的宽
		iv_nav_indicator.setLayoutParams(cursor_Params);
		mInflater = (LayoutInflater) this
				.getSystemService(LAYOUT_INFLATER_SERVICE);
		initNavigationHSV();
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getSupportFragmentManager());

		
		
		mViewPager.setAdapter(mSectionsPagerAdapter);
		
		
	}

	private void initNavigationHSV() {
		rg_nav_content.removeAllViews();
		for (int i = 0; i < tabTitle.length; i++) {
			RadioButton rb = (RadioButton) mInflater.inflate(
					R.layout.nav_radiogroup_item, null);
			if (i == 0) {
				rb.setChecked(true);
			}
			rb.setId(i);
			rb.setText(tabTitle[i]);
			rb.setLayoutParams(new LayoutParams(indicatorWidth,
					LayoutParams.MATCH_PARENT));
			rg_nav_content.addView(rb);
		}
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
		
			Fragment fragment = ExamPaperListFragment.newInstance(position,subjectId);
			return fragment;
		}

		@Override
		public int getCount() {
			// Show 3 total pages.
			return tabTitle.length;
		}

	}

	@Override
	public void onItemNextSelected(String id) {
		Fragment newFragment = ExamDetailFragment.newInstance(id);
		this.addFragmentToStack(newFragment, R.id.itemlist_fl);
		
	}
}
