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
import com.cqvip.mobilevers.R;
import com.cqvip.mobilevers.exception.ErrorVolleyThrow;

public class BaseListFragment extends Fragment {
	protected ListView listview;
	protected View view;
	LayoutInflater mInflater;
	protected BaseAdapter mAdapter;
	RequestQueue mQueue;
	ErrorVolleyThrow volleyErrorListener;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mInflater = inflater;
		// listview = (ListView) view.findViewById(R.id.lst_next_classy);
		initListView();
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	private void initListView() {

	}

	/**
	 * 添加fragment到栈
	 * 
	 * @param newFragment
	 *            fragment
	 * @return return void
	 */
	protected void addFragmentToStack(Fragment newFragment, int layoutid) {
		FragmentTransaction ft = getActivity().getSupportFragmentManager()
				.beginTransaction();
		ft.setCustomAnimations(R.anim.slide_right_in, R.anim.slide_left_out,
				R.anim.slide_left_in, R.anim.slide_right_out);
		ft.replace(layoutid, newFragment);
		ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
		ft.addToBackStack(null);
		ft.commit();
	}

	/**
	 * 当回退后，fragment会执行oncreateview方法，这时会重新加载视图和获取网络数据，没有必要
	 * 
	 * @return
	 */
	protected boolean reuseView() {
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
