package com.cqvip.mobilevers.view;

import java.util.ArrayList;
import java.util.Random;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.cqvip.mobilevers.R;
import com.cqvip.mobilevers.adapter.AnswerscardListViewAdapter;

public class FragmentAnswerScard extends Fragment {
	private ListView mListView;
	private ArrayList<ArrayList<Integer>> mList_Gist = new ArrayList<ArrayList< Integer>>();
	private ArrayList<Integer> rowIndexList = new ArrayList<Integer>();
	public  static int screenWidth, screenHeight;
	public static int gridviewcolumnwidth;
	public static final String TAG = "FragmentAnswerScard";
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view=inflater.inflate(R.layout.fragment_answerscard, container,false);
		mListView = (ListView) view.findViewById(R.id.listview);
		initData();
		AnswerscardListViewAdapter adapter = new AnswerscardListViewAdapter(getActivity(), rowIndexList,mList_Gist);
		mListView.setAdapter(adapter);
		return view;
	}
	
	int b = 0;
	int a = 0;

	public void initData() {
		for (int i = 0; i < 5; i++) {
			rowIndexList.add(i);
			ArrayList<Integer> temp_mGist = new ArrayList<Integer>();
			Random random = new Random();
			b += a;
			a = random.nextInt(30);
			Log.i("MainActivity", a + "");
			for (int j = 1; j <= a; j++) {
				temp_mGist.add(j + b);
			}
			mList_Gist.add(temp_mGist);
			// mList.add(hashmap);
		}

		getscreeninfo();
		countgridviewcolumnwidth();
	}

	public void getscreeninfo() {
		DisplayMetrics dm = new DisplayMetrics();
		dm = getResources().getDisplayMetrics();

		float density = dm.density; // 屏幕密度（像素比例：0.75/1.0/1.5/2.0）
		int densityDPI = dm.densityDpi; // 屏幕密度（每寸像素：120/160/240/320）
		float xdpi = dm.xdpi;
		float ydpi = dm.ydpi;

		Log.e(TAG + "  DisplayMetrics", "xdpi=" + xdpi + "; ydpi=" + ydpi);
		Log.e(TAG + "  DisplayMetrics", "density=" + density + "; densityDPI="
				+ densityDPI);

		screenWidth = dm.widthPixels; // 屏幕宽（像素，如：480px）
		screenHeight = dm.heightPixels; // 屏幕高（像素，如：800px）

		Log.e(TAG + "  DisplayMetrics(111)", "screenWidth=" + screenWidth
				+ "; screenHeight=" + screenHeight);
	}
	
	void countgridviewcolumnwidth(){
		int answerlistviewpaddingall=dip2px(getResources().getDimension(R.dimen.answerlistviewpadding)*2);
		int columnnumber=getResources().getInteger(R.integer.answergridviewcolumnnumber);
		int gridviewcolumnall =dip2px(getResources().getDimension(R.dimen.answergridviewspace))*(columnnumber-1);
		gridviewcolumnwidth=(screenWidth-answerlistviewpaddingall-gridviewcolumnall)/columnnumber;
		Log.i(TAG + " gridviewcolumnwidth", "answerlistviewpaddingall:"+answerlistviewpaddingall+" gridviewcolumnall:"+gridviewcolumnall+" gridviewcolumnwidth:"+gridviewcolumnwidth);
	}
	
    public int dip2px(float dpValue) {  
        final float scale = getResources().getDisplayMetrics().density;  
        return (int) (dpValue * scale + 0.5f);  
    }  
}
