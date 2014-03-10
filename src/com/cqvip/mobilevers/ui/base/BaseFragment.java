package com.cqvip.mobilevers.ui.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.toolbox.Volley;
import com.cqvip.mobilevers.exception.ErrorVolleyThrow;
import com.cqvip.mobilevers.widget.CustomProgressDialog;
public class BaseFragment extends Fragment {
	
	protected RequestQueue mQueue;
	protected ErrorListener volleyErrorListener;
	protected CustomProgressDialog customProgressDialog;
	protected View view;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		mQueue = Volley.newRequestQueue(getActivity());
		customProgressDialog = CustomProgressDialog.createDialog(getActivity());
		volleyErrorListener = new  ErrorVolleyThrow(getActivity(), customProgressDialog);
	}
	
	/**
	 * 当回退后，fragment会执行oncreateview方法，这时会重新加载视图和获取网络数据，没有必要
	 * @return
	 */
	protected boolean reuseView(){
		if (view != null) {
			ViewGroup parent = (ViewGroup) view.getParent();
			if (parent != null) {
				parent.removeView(view);
			}
			return true;
		}
		return false;
	}
	
}
