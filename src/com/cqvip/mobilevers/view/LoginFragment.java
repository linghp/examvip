package com.cqvip.mobilevers.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request.Method;
import com.android.volley.Response.Listener;
import com.cqvip.mobilevers.R;
import com.cqvip.mobilevers.adapter.ExamPaperAdapter;
import com.cqvip.mobilevers.config.ConstantValues;
import com.cqvip.mobilevers.db.OneLevelTypeDao;
import com.cqvip.mobilevers.db.User;
import com.cqvip.mobilevers.db.UserDao;
import com.cqvip.mobilevers.entity.Paper;
import com.cqvip.mobilevers.entity.PaperInfo;
import com.cqvip.mobilevers.http.HttpUtils;
import com.cqvip.mobilevers.http.VersStringRequest;
import com.cqvip.mobilevers.ui.FragmentExamActivity;
import com.cqvip.mobilevers.ui.FragmentMineActivity;
import com.cqvip.mobilevers.ui.base.BaseFragment;

public class LoginFragment extends BaseFragment {
	private EditText name_et;
	private EditText password_et;
	private UserDao userDao;
	private SQLiteDatabase db;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.i("SearchExamFragment", "onCreateView");
		if (reuseView()) {
			return view;
		}
		view = inflater.inflate(R.layout.login, null);
		initview(view);
		
		userDao = ((FragmentMineActivity) getActivity()).userDao;
		db = ((FragmentMineActivity) getActivity()).db;
		return view;
	}

	private void initview(View v) {
		name_et = (EditText) v.findViewById(R.id.et_username);
		password_et = (EditText) v.findViewById(R.id.et_password);
		
		v.findViewById(R.id.txtbtn_login).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				login();
			}
		});
	}

	private void login(){
		String name=name_et.getText().toString().trim();
		String pwd=password_et.getText().toString().trim();
		if(!validate(name,getResources().getString(R.string.need_username))){
			return;
		}
		if(!validate(pwd,getResources().getString(R.string.need_pwd))){
			return;
		}
		Map<String, String> params = new HashMap<String, String>();
		params.put("username", name);
		params.put("password", pwd);
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
			try {
				User user =User.parserJsonData(response);
				if (user!=null) {
                   userDao.insert(user);
   			    SharedPreferences localUsers = getActivity().getSharedPreferences("mobilevers", getActivity().MODE_PRIVATE);
   				Editor editor = localUsers.edit();
   				editor.putString("username", user.getName());
   				editor.putString("userid", user.getUserid());
   				editor.commit();
						Log.i("database", "存储成功");
						Toast.makeText(getActivity(), "登陆成功", 0).show();
					getFragmentManager().popBackStack();
					((FragmentMineActivity)getActivity()).loginUI();
				} else {
					// 提示登陆失败
					Toast.makeText(getActivity(), "登陆失败", 0).show();
				}
			} catch (Exception e) {
				Toast.makeText(getActivity(), "登陆失败", 0).show();
				return;
			}
		}
	};
}
