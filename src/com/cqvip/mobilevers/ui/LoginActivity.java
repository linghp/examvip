package com.cqvip.mobilevers.ui;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request.Method;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.StringRequest;
import com.cqvip.mobilevers.R;
import com.cqvip.mobilevers.config.ConstantValues;
import com.cqvip.mobilevers.http.HttpUtils;
import com.cqvip.mobilevers.ui.base.BaseActivity;

public class LoginActivity extends BaseActivity {

	private Map<String, String> gparams;
	private EditText et_username,et_pwd; 
	private String name,pwd;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.login);
		et_username = (EditText) findViewById(R.id.et_username);
		et_pwd = (EditText) findViewById(R.id.et_password);
		
		String username = et_username.getText().toString();
		String password = et_pwd.getText().toString();
		
		//if(validate())
		
	}
	
	
	public void login(View view){
		
		if(!validate(et_username.getText().toString().trim(),getResources().getString(R.string.need_username))){
			return;
		}
		if(!validate(et_pwd.getText().toString().trim(),getResources().getString(R.string.need_pwd))){
			return;
		}
		name = et_username.getText().toString().trim();
		pwd = et_pwd.getText().toString();
		
		customProgressDialog.show();
		gparams=new HashMap<String, String>();			
		gparams.put("username", name);
		gparams.put("password", pwd);
		
		requestVolley(ConstantValues.SERVER_URL + ConstantValues.LOGIN_ADDR,
				back_ls, Method.POST);
	

	}
	
	
	private boolean validate(String trim,String msg) {
		if(TextUtils.isEmpty(trim)){
			Toast.makeText(this, msg,Toast.LENGTH_SHORT).show();;
			return false;
		}else{
			
			return true;
		}
	}
	
	private Listener<String> back_ls = new Listener<String>() {

		@Override
		public void onResponse(String response) {
			// TODO Auto-generated method stub
			if(customProgressDialog!=null&&customProgressDialog.isShowing())
			customProgressDialog.dismiss();
			//解析结果
			System.out.println(response);
			try {
				JSONObject json = new JSONObject(response);
				//判断
				if(json.isNull("error")){
					//返回正常
					
					
				}else {
					//登陆错误
					
					
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//存入数据库
			
			
				
			
			
//			try {
//				Result res = new Result(response);
//				if (res.getSuccess()) {
//					GlobleData.islogin = true;
//					User user = new User(response);
//					GlobleData.userid = user.getCardno();
//					GlobleData.readerid = user.getReaderno();
//					GlobleData.cqvipid = user.getVipuserid()+"";
//					editor.putString("readercardid",GlobleData.userid);
//					editor.commit();
//					MUser muser = new MUser();
//					muser.setCardno(user.getCardno());
//					muser.setReaderno(user.getReaderno());
//					muser.setPwd(pwd);
//					muser.setName(user.getName());
//					muser.setCqvipid(user.getVipuserid()+"");
//					if (dao == null) {
//						dao = new MUserDao(ActivityDlg.this);
//					}
//					try {
//						// dao.delInfo(muser.getCardno());
//						dao.saveInfo(muser);
//						Log.i("database", "存储成功");
//					} catch (DaoException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//					// if (dialog.isShowing()) {
//					// dialog.dismiss();
//					// }
//					// 提示登陆成功
////					Tool.ShowMessages(this, "登陆成功");
//					winexit(0);
//				} else {
//					GlobleData.islogin = false;
//					// dialog.dismiss();
//					// 提示登陆失败
//					Tool.ShowMessages(ActivityDlg.this, res.getMessage());
//				}
//			} catch (Exception e) {
//			   onError(2);
//				return;
//			}
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
			mys.setRetryPolicy(HttpUtils.setTimeout());
			mQueue.add(mys);
		} catch (Exception e) {
			//onError(2);
		}
	}
	
	
	
	
}
