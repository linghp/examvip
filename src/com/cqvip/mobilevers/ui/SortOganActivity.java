package com.cqvip.mobilevers.ui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request.Method;
import com.android.volley.Response.Listener;
import com.cqvip.mobilevers.R;
import com.cqvip.mobilevers.config.ConstantValues;
import com.cqvip.mobilevers.entity.Organization;
import com.cqvip.mobilevers.http.HttpUtils;
import com.cqvip.mobilevers.http.VersStringRequest;
import com.cqvip.mobilevers.sortlistview.CharacterParser;
import com.cqvip.mobilevers.sortlistview.ClearEditText;
import com.cqvip.mobilevers.sortlistview.PinyinComparator;
import com.cqvip.mobilevers.sortlistview.SideBar;
import com.cqvip.mobilevers.sortlistview.SideBar.OnTouchingLetterChangedListener;
import com.cqvip.mobilevers.sortlistview.SortAdapter;
import com.cqvip.mobilevers.sortlistview.SortModel;
import com.cqvip.mobilevers.ui.base.BaseActivity;

public class SortOganActivity  extends BaseActivity {
	private ListView sortListView;
	private SideBar sideBar;
	private TextView dialog;
	private SortAdapter adapter;
	private ClearEditText mClearEditText;
	
	/**
	 * 汉字转换成拼音的类
	 */
	private CharacterParser characterParser;
	private List<SortModel> SourceDateList;
	
	/**
	 * 根据拼音来排列ListView里面的数据类
	 */
	private PinyinComparator pinyinComparator;
	//数据源
	private List<Organization> lists;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sort_mian_view);
		initViews();
	}

	private void initViews() {
		//实例化汉字转拼音类
		characterParser = CharacterParser.getInstance();
		
		pinyinComparator = new PinyinComparator();
		
		sideBar = (SideBar) findViewById(R.id.sidrbar);
		dialog = (TextView) findViewById(R.id.dialog);
		sideBar.setTextView(dialog);
		mClearEditText = (ClearEditText) findViewById(R.id.filter_edit);
		hideKeybord();
		//设置右侧触摸监听
		sideBar.setOnTouchingLetterChangedListener(new OnTouchingLetterChangedListener() {
			
			@Override
			public void onTouchingLetterChanged(String s) {
				//该字母首次出现的位置
				int position = adapter.getPositionForSection(s.charAt(0));
				if(position != -1){
					sortListView.setSelection(position);
				}
				
			}
		});
		
		sortListView = (ListView) findViewById(R.id.country_lvcountry);
		sortListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				//这里要利用adapter.getItem(position)来获取当前position所对应的对象
				String name = ((SortModel)adapter.getItem(position)).getName();
				Organization organ = getformlist(name);
				if(organ!=null){
				Intent intent = new Intent();
				intent.putExtra("organName", organ.getOrganName());
				intent.putExtra("organId", organ.getOrganCode());
				setResult(RESULT_OK, intent);
				}else{
					setResult(RESULT_CANCELED);
				}
				finish();
				//Toast.makeText(getApplication(), organ.getOrganName()+organ.getOrganCode(), Toast.LENGTH_SHORT).show();
			}

			private Organization getformlist(String name) {
				for(Organization org:lists){
					if(org.getOrganName().equals(name)){
						return org;
					}
				}
				return null;
			}
		});
		//获取数据
		getDataFromNet();
		
		
	}
	
	//获取数据
	private void getDataFromNet() {
		customProgressDialog.show();
		Listener<String> listner;
			listner = backlistenerOrgan;
		requestVolley(null, ConstantValues.SERVER_URL
				+ ConstantValues.GetAllDistinctOrganizationCodeList, listner, Method.POST);
	}


	private void requestVolley(final Map<String, String> gparams, String url,
			Listener<String> listener, int post) {
		VersStringRequest mys = new VersStringRequest(post, url, listener,
				volleyErrorListener) {
			protected Map<String, String> getParams()
					throws com.android.volley.AuthFailureError {
				return gparams;
			};
		};
		mys.setRetryPolicy(HttpUtils.setTimeout());
		mQueue.add(mys);

	}
	
	/**
	 * 获取试卷详细信息
	 */
	private Listener<String> backlistenerOrgan = new Listener<String>() {
		@Override
		public void onResponse(String response) {
			if (customProgressDialog != null
					&& customProgressDialog.isShowing())
				customProgressDialog.dismiss();
			// 解析结果
			if (response != null) {
				try {
					JSONObject json = new JSONObject(response);
					
						// 判断
						if (json.isNull("error")) {
							// 返回正常
							 lists = Organization.formList(json);
							 SourceDateList = filledData(lists);
								
								// 根据a-z进行排序源数据
								Collections.sort(SourceDateList, pinyinComparator);
								adapter = new SortAdapter(SortOganActivity.this, SourceDateList);
								sortListView.setAdapter(adapter);
								
							
								
								//根据输入框输入值的改变来过滤搜索
								mClearEditText.addTextChangedListener(new TextWatcher() {
									
									@Override
									public void onTextChanged(CharSequence s, int start, int before, int count) {
										//当输入框里面的值为空，更新为原来的列表，否则为过滤数据列表
										filterData(s.toString());
									}
									
									@Override
									public void beforeTextChanged(CharSequence s, int start, int count,
											int after) {
										
									}
									
									@Override
									public void afterTextChanged(Editable s) {
									}
								});
								
								hideKeybord(); 
							 
							
						} else {
							// 获取失败
							// TODO
						}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			} else {
				// Toast.makeText(getActivity(), "无数据",
				// Toast.LENGTH_LONG).show();
			}
		}
	};



	/**
	 * 为ListView填充数据
	 * @param date
	 * @return
	 */
	private List<SortModel> filledData(String [] date){
		List<SortModel> mSortList = new ArrayList<SortModel>();
		
		for(int i=0; i<date.length; i++){
			SortModel sortModel = new SortModel();
			sortModel.setName(date[i]);
			//汉字转换成拼音
			String pinyin = characterParser.getSelling(date[i]);
			String sortString = pinyin.substring(0, 1).toUpperCase();
			
			// 正则表达式，判断首字母是否是英文字母
			if(sortString.matches("[A-Z]")){
				sortModel.setSortLetters(sortString.toUpperCase());
			}else{
				sortModel.setSortLetters("#");
			}
			
			mSortList.add(sortModel);
		}
		return mSortList;
		
	}
	/**
	 * 为ListView填充数据
	 * @param date
	 * @return
	 */
	private List<SortModel> filledData(List<Organization> date){
		List<SortModel> mSortList = new ArrayList<SortModel>();
		
		for(int i=0; i<date.size(); i++){
			SortModel sortModel = new SortModel();
			sortModel.setName(date.get(i).getOrganName());
			//汉字转换成拼音
			String pinyin = characterParser.getSelling(date.get(i).getOrganName());
			String sortString = pinyin.substring(0, 1).toUpperCase();
			
			// 正则表达式，判断首字母是否是英文字母
			if(sortString.matches("[A-Z]")){
				sortModel.setSortLetters(sortString.toUpperCase());
			}else{
				sortModel.setSortLetters("#");
			}
			
			mSortList.add(sortModel);
		}
		return mSortList;
		
	}
	
	/**
	 * 根据输入框中的值来过滤数据并更新ListView
	 * @param filterStr
	 */
	private void filterData(String filterStr){
		List<SortModel> filterDateList = new ArrayList<SortModel>();
		
		if(TextUtils.isEmpty(filterStr)){
			filterDateList = SourceDateList;
		}else{
			filterDateList.clear();
			for(SortModel sortModel : SourceDateList){
				String name = sortModel.getName();
				if(name.indexOf(filterStr.toString()) != -1 || characterParser.getSelling(name).startsWith(filterStr.toString())){
					filterDateList.add(sortModel);
				}
			}
		}
		// 根据a-z进行排序
		Collections.sort(filterDateList, pinyinComparator);
		adapter.updateListView(filterDateList);
	}
	private void hideKeybord() {
		InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
		if (imm.isActive()) {
			imm.hideSoftInputFromWindow(mClearEditText.getWindowToken(), 0);
		}
	}
	
}
