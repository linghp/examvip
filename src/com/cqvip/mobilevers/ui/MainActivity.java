package com.cqvip.mobilevers.ui;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.cqvip.mobilevers.R;
import com.cqvip.mobilevers.ui.base.BaseActivity;

public class MainActivity extends BaseActivity {

	private ViewPager mTabPager;	
	private ImageView mTabImg;// 动画图片
	private ImageView mTab1,mTab2,mTab3,mTab4;
	private TextView mtv1,mtv2,mtv3,mtv4;
	private int zero = 0;// 动画图片偏移量
	private int currIndex = 0;// 当前页卡编号
	private int one;//单个水平动画位移
	private int two;
	private int three;
	
	private ListView listview; //listview列表
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		  mTabPager = (ViewPager)findViewById(R.id.tabpager);
	        mTabPager.setOnPageChangeListener(new MyOnPageChangeListener());
	        
	        mTab1 = (ImageView) findViewById(R.id.img_exam);
	        mTab2 = (ImageView) findViewById(R.id.img_search);
	        mTab3 = (ImageView) findViewById(R.id.img_mine);
	        mTab4 = (ImageView) findViewById(R.id.img_more);
	        mtv1 = (TextView) findViewById(R.id.tv_exam);
	        mtv2 = (TextView) findViewById(R.id.tv_search);
	        mtv3 = (TextView) findViewById(R.id.tv_mine);
	        mtv4 = (TextView) findViewById(R.id.tv_more);
	        mTabImg = (ImageView) findViewById(R.id.img_tab_now);
	        mTab1.setOnClickListener(new MyOnClickListener(0));
	        mTab2.setOnClickListener(new MyOnClickListener(1));
	        mTab3.setOnClickListener(new MyOnClickListener(2));
	        mTab4.setOnClickListener(new MyOnClickListener(3));
	        mtv1.setOnClickListener(new MyOnClickListener(0));
	        mtv2.setOnClickListener(new MyOnClickListener(1));
	        mtv3.setOnClickListener(new MyOnClickListener(2));
	        mtv4.setOnClickListener(new MyOnClickListener(3));
	        Display currDisplay = getWindowManager().getDefaultDisplay();//获取屏幕当前分辨率
	        int displayWidth = currDisplay.getWidth();
	        int displayHeight = currDisplay.getHeight();
	        one = displayWidth/4; //设置水平动画平移大小
	        two = one*2;
	        three = one*3;
	        //Log.i("info", "获取的屏幕分辨率为" + one + two + three + "X" + displayHeight);
	        
	        //InitImageView();//使用动画
	      //将要分页显示的View装入数组中
	        LayoutInflater mLi = LayoutInflater.from(this);
	        View view1 = mLi.inflate(R.layout.main_tab_exam, null);
	        View view2 = mLi.inflate(R.layout.main_tab_search, null);
	        View view3 = mLi.inflate(R.layout.main_tab_mine, null);
	        View view4 = mLi.inflate(R.layout.main_tab_more, null);
	      //每个页面的view数据
	        final ArrayList<View> views = new ArrayList<View>();
	        views.add(view1);
	        views.add(view2);
	        views.add(view3);
	        views.add(view4);
	      //填充ViewPager的数据适配器
	        PagerAdapter mPagerAdapter = new PagerAdapter() {
				
				@Override
				public boolean isViewFromObject(View arg0, Object arg1) {
					return arg0 == arg1;
				}
				
				@Override
				public int getCount() {
					return views.size();
				}

				@Override
				public void destroyItem(View container, int position, Object object) {
					((ViewPager)container).removeView(views.get(position));
				}
				
				//@Override
				//public CharSequence getPageTitle(int position) {
					//return titles.get(position);
				//}
				
				@Override
				public Object instantiateItem(View container, int position) {
					((ViewPager)container).addView(views.get(position));
					return views.get(position);
				}
			};
			
			mTabPager.setAdapter(mPagerAdapter);
		
	}
	 /**
		 * 头标点击监听
		 */
		public class MyOnClickListener implements View.OnClickListener {
			private int index = 0;

			public MyOnClickListener(int i) {
				index = i;
			}
			@Override
			public void onClick(View v) {
				mTabPager.setCurrentItem(index);
			}
		};
	    
		 /* 页卡切换监听(原作者:D.Winter)
		 */
		public class MyOnPageChangeListener implements OnPageChangeListener {
			@Override
			public void onPageSelected(int arg0) {
				Animation animation = null;
				switch (arg0) {
				case 0:
					mTab1.setImageDrawable(getResources().getDrawable(R.drawable.tab_weixin_pressed));
					if (currIndex == 1) {
						animation = new TranslateAnimation(one, 0, 0, 0);
						mTab2.setImageDrawable(getResources().getDrawable(R.drawable.tab_weixin_normal));
					} else if (currIndex == 2) {
						animation = new TranslateAnimation(two, 0, 0, 0);
						mTab3.setImageDrawable(getResources().getDrawable(R.drawable.tab_weixin_normal));
					}
					else if (currIndex == 3) {
						animation = new TranslateAnimation(three, 0, 0, 0);
						mTab4.setImageDrawable(getResources().getDrawable(R.drawable.tab_weixin_normal));
					}
					break;
				case 1:
					mTab2.setImageDrawable(getResources().getDrawable(R.drawable.tab_weixin_pressed));
					if (currIndex == 0) {
						animation = new TranslateAnimation(zero, one, 0, 0);
						mTab1.setImageDrawable(getResources().getDrawable(R.drawable.tab_weixin_normal));
					} else if (currIndex == 2) {
						animation = new TranslateAnimation(two, one, 0, 0);
						mTab3.setImageDrawable(getResources().getDrawable(R.drawable.tab_weixin_normal));
					}
					else if (currIndex == 3) {
						animation = new TranslateAnimation(three, one, 0, 0);
						mTab4.setImageDrawable(getResources().getDrawable(R.drawable.tab_weixin_normal));
					}
					break;
				case 2:
					mTab3.setImageDrawable(getResources().getDrawable(R.drawable.tab_weixin_pressed));
					if (currIndex == 0) {
						animation = new TranslateAnimation(zero, two, 0, 0);
						mTab1.setImageDrawable(getResources().getDrawable(R.drawable.tab_weixin_normal));
					} else if (currIndex == 1) {
						animation = new TranslateAnimation(one, two, 0, 0);
						mTab2.setImageDrawable(getResources().getDrawable(R.drawable.tab_weixin_normal));
					}
					else if (currIndex == 3) {
						animation = new TranslateAnimation(three, two, 0, 0);
						mTab4.setImageDrawable(getResources().getDrawable(R.drawable.tab_weixin_normal));
					}
					break;
				case 3:
					mTab4.setImageDrawable(getResources().getDrawable(R.drawable.tab_weixin_pressed));
					if (currIndex == 0) {
						animation = new TranslateAnimation(zero, three, 0, 0);
						mTab1.setImageDrawable(getResources().getDrawable(R.drawable.tab_weixin_normal));
					} else if (currIndex == 1) {
						animation = new TranslateAnimation(one, three, 0, 0);
						mTab2.setImageDrawable(getResources().getDrawable(R.drawable.tab_weixin_normal));
					}
					else if (currIndex == 2) {
						animation = new TranslateAnimation(two, three, 0, 0);
						mTab3.setImageDrawable(getResources().getDrawable(R.drawable.tab_weixin_normal));
					}
					break;
				}
				currIndex = arg0;
				animation.setFillAfter(true);// True:图片停在动画结束位置
				animation.setDuration(150);
				mTabImg.startAnimation(animation);
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		}
		
		public void next(View v){
			
			
		}

}
