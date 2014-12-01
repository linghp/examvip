package com.cqvip.mobilevers.view;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

import com.android.volley.Request.Method;
import com.android.volley.Response.Listener;
import com.cqvip.mobilevers.R;
import com.cqvip.mobilevers.config.ConstantValues;
import com.cqvip.mobilevers.db.User;
import com.cqvip.mobilevers.db.UserDao;
import com.cqvip.mobilevers.entity.Organization;
import com.cqvip.mobilevers.http.HttpUtils;
import com.cqvip.mobilevers.http.VersStringRequest;
import com.cqvip.mobilevers.ui.FragmentMineActivity;
import com.cqvip.mobilevers.ui.SortOganActivity;
import com.cqvip.mobilevers.ui.base.BaseFragment;

public class LoginFragment extends BaseFragment implements OnEditorActionListener, OnClickListener {
	public static final String TAG = "LoginFragment";
	private EditText name_et;
	private EditText password_et;
	private TextView organ_et;
	private UserDao userDao;
	private SQLiteDatabase db;
	private List<Organization> lists;
	private int organCode = -1;
	private boolean isSelect = false;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (reuseView()) {
			return view;
		}
		view = inflater.inflate(R.layout.login, null);
		initview(view);
		
		userDao = ((FragmentMineActivity) getActivity()).userDao;
		db = ((FragmentMineActivity) getActivity()).db;
		//getDateFromNet();
		
		return view;
	}

	
	

	private void initview(View v) {
		name_et = (EditText) v.findViewById(R.id.et_username);
		password_et = (EditText) v.findViewById(R.id.et_password);
		organ_et = (TextView) v.findViewById(R.id.et_organ);
		organ_et.setOnClickListener(this);
		password_et.setOnEditorActionListener(this);
		
		v.findViewById(R.id.txtbtn_login).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				login();
			}
		});
	}

	private void login(){
		if(!isSelect){
			Toast.makeText(getActivity(), "请选择机构", Toast.LENGTH_SHORT).show();
			return;
		}
		String name=name_et.getText().toString().trim();
		String pwd=password_et.getText().toString().trim();
		if(!validate(name,getResources().getString(R.string.need_username))){
			return;
		}
		if(!validate(pwd,getResources().getString(R.string.need_pwd))){
			return;
		}
	    Log.i("login", name+","+pwd+","+organCode+","+"URL:"+ConstantValues.SERVER_URL
				+ ConstantValues.LOGIN_ADDR);
		Map<String, String> params = new HashMap<String, String>();
		params.put("username", name);
		params.put("password", pwd);
		params.put("organizationCodeId", organCode+"");
		requestVolley(params, ConstantValues.SERVER_URL
				+ ConstantValues.LOGIN_ADDR, backlistener, Method.POST);
		customProgressDialog.show();
	}
	
	private boolean validate(String trim,String msg) {
		if(TextUtils.isEmpty(trim)){
			Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
			return false;
		}else{
			return true;
		}
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

	private Listener<String> backlistener = new Listener<String>() {
		@Override
		public void onResponse(String response) {
			// TODO Auto-generated method stub
			if(customProgressDialog!=null&&customProgressDialog.isShowing())
			customProgressDialog.dismiss();
			Log.i("login",response);
			try {
				User user =User.parserJsonData(response);
				if (user!=null) {
                   userDao.insert(user);
   			    SharedPreferences localUsers = getActivity().getSharedPreferences("mobilevers", getActivity().MODE_PRIVATE);
   				Editor editor = localUsers.edit();
   				editor.putString("username", user.getName());
   				editor.putString("realname", user.getRealname());
   				editor.putString("userid", user.getUserid());
   				editor.commit();
					//	Log.i("database", "存储成功"+user.getUserid());
						Toast.makeText(getActivity(), getString(R.string.tips_login_sucess), Toast.LENGTH_SHORT).show();
					getFragmentManager().popBackStack();
					((FragmentMineActivity)getActivity()).loginUI();
				} else {
					// 提示登陆失败
					Toast.makeText(getActivity(), getString(R.string.tips_login_fail_detail), Toast.LENGTH_SHORT).show();
				}
			} catch (Exception e) {
			Log.w("LoginFragment", e.getMessage());
				Toast.makeText(getActivity(), getString(R.string.tips_login_fail), Toast.LENGTH_SHORT).show();
				return;
			}
		}
	};
	@Override
	public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
		switch (actionId) {
		case EditorInfo.IME_ACTION_NEXT:
			//隐藏键盘
			InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
			if (imm.isActive()) {
				imm.hideSoftInputFromWindow(password_et.getWindowToken(), 0);
			}
			break;
		default:
			break;
		}
		return false;
	}

	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.et_organ:
			Intent intent = new Intent(getActivity(),SortOganActivity.class);
			startActivityForResult(intent, 1);
			break;

		default:
			break;
		}
	}
	

	public void updateview(String defOrganName, int defOrganCode) {
		 organ_et.setText("您的机构："+ defOrganName);
	}
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		Log.i("login","requestCode"+requestCode);
			if(requestCode==1&&resultCode==-1){
			String	defOrganName = data.getStringExtra("organName");
			if(!TextUtils.isEmpty(defOrganName)){
				isSelect = true;
			}else{
				isSelect = false;
			}
			int defOrganCode = data.getIntExtra("organId",-1);
				updateview(defOrganName,defOrganCode);
				organCode = defOrganCode;
		}
	}

}
