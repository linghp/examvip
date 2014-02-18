package com.cqvip.mobilevers.ui.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.toolbox.Volley;
import com.cqvip.mobilevers.exception.ErrorVolleyThrow;

public class BaseFragment extends Fragment {
	protected RequestQueue mQueue;
	protected ErrorListener volleyErrorListener;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		mQueue = Volley.newRequestQueue(getActivity());
		volleyErrorListener = new  ErrorVolleyThrow(getActivity(), null);
		
		
	}
	
 
	
}
