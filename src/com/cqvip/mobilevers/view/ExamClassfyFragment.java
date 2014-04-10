package com.cqvip.mobilevers.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.cqvip.mobilevers.R;
import com.cqvip.mobilevers.entity.PaperInfo;
import com.cqvip.mobilevers.ui.FragmentExamActivity;
import com.cqvip.mobilevers.ui.base.BaseFragment;



public class ExamClassfyFragment extends BaseFragment implements OnClickListener {
	
		private static final String TITLE = "title";
		private static final String SUBJECTID = "subjectid";
		private SectionsPagerAdapter mSectionsPagerAdapter;
		private ViewPager mViewPager;
		private int mposition;
		
		private RadioGroup rg_nav_content;
		private ImageView iv_nav_indicator;
		private String[] tabTitle; // 标题
		private LayoutInflater mInflater;
		private int indicatorWidth;
		private int currentIndicatorLeft = 0;
		private static String subjectId;
		private TextView tv_title;
		//private ImageView img_back;
		
		public static final String TAG="ExamClassfyFragment";
		
		
		public static ExamClassfyFragment newInstance(String num, String title) {
			ExamClassfyFragment f = new ExamClassfyFragment();

			// Supply num input as an argument.
			Bundle args = new Bundle();
			args.putString(SUBJECTID, num);
			args.putString(TITLE, title);
			f.setArguments(args);
			return f;
		}
		
		
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
			Log.i(TAG, "onCreateView");
			((FragmentExamActivity)getActivity()).tag=TAG;
			if (reuseView()) {
				if(mposition==1){
					((RadioButton) rg_nav_content.getChildAt(0))
					.performClick();
					((RadioButton) rg_nav_content.getChildAt(mposition))
					.performClick();
				}
				return view;
			}
			view = inflater.inflate(R.layout.activity_exam_classfy, container, false);
			String title = getArguments().getString(TITLE);
			subjectId = getArguments().getString(SUBJECTID);
			//img_back = (ImageView) view.findViewById(R.id.img_back);
			tv_title = (TextView) view.findViewById(R.id.tv_show_title);
			//img_back.setOnClickListener(this);
			tv_title.setText(title);
			initView(view,inflater);
			setListener();
		return view;
		}

		  private void setListener() {
			  mViewPager
				.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
					@Override
					public void onPageSelected(int position) {
						Log.i("ExamClassfyActivity", "onPageSelected");
						mposition = position;
						if (rg_nav_content != null
								&& rg_nav_content.getChildCount() > position) {
							((RadioButton) rg_nav_content.getChildAt(position))
									.performClick();
						}
						if (position == 0) {
							((FragmentExamActivity)getActivity()).isLeftFragment = true;
						} else {
							((FragmentExamActivity)getActivity()).isLeftFragment = false;
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

		private void initView(View view,LayoutInflater inflater) {

			tabTitle=getResources().getStringArray(R.array.tabtitle);
			rg_nav_content =  (RadioGroup) view.findViewById(R.id.rg_nav_content);
			iv_nav_indicator = (ImageView) view.findViewById(R.id.iv_nav_indicator);
			DisplayMetrics dm = new DisplayMetrics();
			getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
			indicatorWidth = dm.widthPixels / tabTitle.length;
			// TODO step0 初始化滑动下标的宽 根据屏幕宽度和可见数量 来设置RadioButton的宽度)
			LayoutParams cursor_Params = iv_nav_indicator.getLayoutParams();
			cursor_Params.width = indicatorWidth;// 初始化滑动下标的宽
			iv_nav_indicator.setLayoutParams(cursor_Params);
			mInflater = inflater;
			initNavigationHSV();
			mViewPager = (ViewPager) view.findViewById(R.id.pager);
			mSectionsPagerAdapter = new SectionsPagerAdapter(
					getFragmentManager());
			mViewPager.setAdapter(mSectionsPagerAdapter);
			mSectionsPagerAdapter.notifyDataSetChanged();
			
			
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
			//fragment下次进来是空白，因为没有销毁，不执行生命周期方法，所以保存fragment的引用在destroy时销毁。
			private SparseArray<ExamPaperListFragment> mPageReferenceMap = new SparseArray<ExamPaperListFragment>();

			@Override
			public Fragment getItem(int position) {
			
				ExamPaperListFragment fragment = ExamPaperListFragment.newInstance(position,subjectId);
				 mPageReferenceMap.put(position, fragment);
				return fragment;
			}

			@Override
			public int getCount() {
				// Show 3 total pages.
				return tabTitle.length;
			}
			
			@Override
			public Object instantiateItem(ViewGroup container, int position) {
				ExamPaperListFragment fragment = (ExamPaperListFragment) super.instantiateItem(container,
				            position);
				    mPageReferenceMap.put(position, fragment);
				    return fragment;
			}

			@Override
			public void destroyItem(ViewGroup container, int position,
					Object object) {
				super.destroyItem(container, position, object);
				 mPageReferenceMap.remove(position);
				Log.i("SectionsPagerAdapter","=======destroyItem=================");
			}

			public ExamPaperListFragment getFragment(int key) {
			    return mPageReferenceMap.get(key);
			}
			
			@Override
			public int getItemPosition(Object object) {
				return POSITION_NONE;
			}
		}

		
		
		@Override
		public void onDestroy() {
		
			FragmentTransaction  ft= getFragmentManager().beginTransaction();
			ft.remove(mSectionsPagerAdapter.getFragment(0));
			ft.remove(mSectionsPagerAdapter.getFragment(1));
			ft.commit();
		super.onDestroy();
		}
	

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.img_back:
			//getFragmentManager().popBackStack();
			break;

		default:
			break;
		}
		
	}
		
	

}
