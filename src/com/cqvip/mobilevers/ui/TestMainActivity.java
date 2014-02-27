package com.cqvip.mobilevers.ui;

import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request.Method;
import com.android.volley.Response.Listener;
import com.android.volley.Response;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;
import com.cqvip.mobilevers.R;
import com.cqvip.mobilevers.adapter.ExamAClassfyAdapter;
import com.cqvip.mobilevers.config.ConstantValues;
import com.cqvip.mobilevers.entity.Exam;
import com.cqvip.mobilevers.entity.ExamInfo;
import com.cqvip.mobilevers.ui.base.BaseActivity;

public class TestMainActivity extends BaseActivity {

	
	private Map<String, String> gparams;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test_main);
	}

	// 2db21d4bab0e40478a65a32bb9e94f45    /Service1.asmx/GetExamPaperInfo
	private Listener<String> backlistener = new Listener<String>() {
		@Override
		public void onResponse(String response) {
			try {
				JSONObject json = new JSONObject(response);
				if(!json.isNull("_userScore")){
				Exam exam = new Exam(json);
				System.out.println(exam.toString());
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
			StringRequest mys = new StringRequest(method, addr, bl, volleyErrorListener) {

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
	
	public void getXml(View v){
		
		requestVolley(ConstantValues.SERVER_URL+"Service1.asmx/GetExamPaperInfo", backlistener, Method.POST);
		
		
		

	}
}
