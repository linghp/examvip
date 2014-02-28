package com.cqvip.mobilevers.ui;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request.Method;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.StringRequest;
import com.cqvip.mobilevers.R;
import com.cqvip.mobilevers.config.ConstantValues;
import com.cqvip.mobilevers.entity.Exam;
import com.cqvip.mobilevers.http.VersStringRequest;
import com.cqvip.mobilevers.ui.base.BaseActivity;

public class TestMainActivity extends BaseActivity {

	
	private Map<String, String> gparams;
	private TextView tv;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test_main);
		tv = (TextView) findViewById(R.id.tv1);
	}

	// 2db21d4bab0e40478a65a32bb9e94f45    /Service1.asmx/GetExamPaperInfo
	private Listener<String> backlistener = new Listener<String>() {
		@Override
		public void onResponse(String response) {
			try {
				JSONObject json = new JSONObject(response);
				if(!json.isNull("_userScore")){
				Exam exam = new Exam(json);
				//System.out.println(exam.toString());
				tv.setText(exam.toString());
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
			// TODO Auto-generated method stub
//			if(customProgressDialog!=null&&customProgressDialog.isShowing())
//			customProgressDialog.dismiss();
//			try {
//				List<ShortBook> lists = ShortBook.formList(sendtype, response);
//				if (lists != null && !lists.isEmpty()) {
//					cache = new BitmapCache(Tool.getCachSize());
//					adapter = new AdvancedBookAdapter(context, lists, new ImageLoader(mQueue, cache));
//					gridview_abook.setAdapter(adapter);
//				}
//			} catch (Exception e) {
//				onError(2);
//				return;
//		}
		}
	};
	
	private void requestVolley(String addr, Listener<String> bl, int method) {
		try {
			VersStringRequest mys = new		VersStringRequest(method, addr, bl, volleyErrorListener) {

				protected Map<String, String> getParams()
						throws com.android.volley.AuthFailureError {
					return gparams;
				};
			};
			mQueue.add(mys);
			//mQueue.start();
		} catch (Exception e) {
			//onError(2);
		}
	}
		
	public void getxml(View v){
		
		gparams = new HashMap<String, String>() ;
		gparams.put("examPaperId", "4cc4160c59de4f41a38711368fe2e864");
		System.out.println(ConstantValues.SERVER_URL+ConstantValues.GETEXAM_ADDR);
		requestVolley(ConstantValues.SERVER_URL+ConstantValues.GETEXAM_ADDR, backlistener, Method.POST);
		
		
		

	}
}
