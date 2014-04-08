package com.cqvip.mobilevers.ui;


import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.TabHost;
import android.widget.TextView;

import com.cqvip.mobilevers.R;
import com.cqvip.mobilevers.imgcache.ImageCache;

/**
 * 主菜单
 * @author luojiang
 *
 */
public class MainActivity extends TabActivity {
	private TabHost tabHost;
	private TextView main_tab_new_message;
	private TextView menuTitle;
	private ImageButton ib;
	   private static final String IMAGE_CACHE_DIR = "images";
	    private static final String EXTRA_IMAGE = "mextra_image";
	    public static  ImageCache.ImageCacheParams cacheParams;
		public static ImageCache imgCache;	
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.fragment_tabs_fragment);
		Context context = this;
		cacheParams  =  new ImageCache.ImageCacheParams(this, IMAGE_CACHE_DIR);
        cacheParams.setMemCacheSizePercent(0.125f); // Set memory cache to 25% of app memory
		imgCache = ImageCache.getInstance(context,cacheParams);
		
		
        tabHost= this.getTabHost();
        TabHost.TabSpec spec;
        Intent intent;
        
        intent=new Intent().setClass(this, FragmentExamActivity.class);
        spec=tabHost.newTabSpec("题库").setIndicator("题库").setContent(intent);
        tabHost.addTab(spec);
        
        intent=new Intent().setClass(this,FragmentSearchActivity.class);
        spec=tabHost.newTabSpec("搜索").setIndicator("搜索").setContent(intent);
        tabHost.addTab(spec);
        
        intent=new Intent().setClass(this, FragmentMineActivity.class);
        spec=tabHost.newTabSpec("我的").setIndicator("我的").setContent(intent);
        tabHost.addTab(spec);
        
        intent=new Intent().setClass(this, FragmentMoreActivity.class);
        spec=tabHost.newTabSpec("更多").setIndicator("更多").setContent(intent);
        tabHost.addTab(spec);
        
        tabHost.setCurrentTab(0);
        
        RadioGroup radioGroup=(RadioGroup) this.findViewById(R.id.main_tab_group);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				switch (checkedId) {
				case R.id.main_tab_vedio://客户服务
					tabHost.setCurrentTabByTag("题库");
					break;
				case R.id.main_tab_newInfo://新车快讯
					tabHost.setCurrentTabByTag("搜索");
					break;
				case R.id.main_tab_promotion://优惠促销
					tabHost.setCurrentTabByTag("我的");
					break;
				case R.id.main_tab_garage://透明车间
					tabHost.setCurrentTabByTag("更多");
					break;
				default:
					break;
				}
			}
		});
	}	
	
	public ImageCache getImgCache() {
		return imgCache;
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		android.os.Process.killProcess(android.os.Process.myPid());
	}
	
	//屏蔽回退键，当在子activity中按回退键时会响应此activity的回退事件finish掉程序，故在此屏蔽。
	public boolean onKeyDown(int keyCode, KeyEvent event) { 
	    if(keyCode == KeyEvent.KEYCODE_BACK) { //监控/拦截/屏蔽返回键 
	        return true; 
	    } 
	return super.onKeyDown(keyCode, event); 
	}
}
