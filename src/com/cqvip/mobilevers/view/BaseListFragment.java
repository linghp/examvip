package com.cqvip.mobilevers.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.toolbox.Volley;
import com.cqvip.mobilevers.exception.ErrorVolleyThrow;

public class BaseListFragment extends Fragment {
	protected ListView listview;
	protected View view;
	LayoutInflater mInflater;
	protected BaseAdapter mAdapter;
 	RequestQueue    mQueue;
 	ErrorVolleyThrow  volleyErrorListener;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mInflater = inflater;
		//listview = (ListView) view.findViewById(R.id.lst_next_classy);
		initListView();
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	private void initListView() {
		
	}

	protected void addFragmentToStack(Fragment newFragment,int layoutid) {
		FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
		ft.replace(layoutid, newFragment);
		ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
		ft.addToBackStack(null);
		ft.commit();
	}
}
