package com.cqvip.mobilevers.view;

import java.util.ArrayList;
import java.util.Arrays;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.cqvip.mobilevers.R;
import com.cqvip.mobilevers.adapter.AnswerscardListViewAdapter;
import com.cqvip.mobilevers.entity.TwoDimensionArray;
import com.cqvip.mobilevers.ui.ExamActivity;

/**
 * 答题卡类
 * @author luojiang
 *
 */
public class FragmentAnswerScard extends Fragment {
	private ListView mListView;
	private ArrayList<ArrayList<Integer>> mList_Gist = new ArrayList<ArrayList< Integer>>();//所有的记录，二维数组
	private ArrayList<Integer> rowIndexList = new ArrayList<Integer>();//记录行号
	private  ArrayList<Integer> perCount_row = new ArrayList<Integer>();//
	
	private int[][] allAnswer;
	
	public  static int screenWidth, screenHeight;//屏幕宽高
	public static int gridviewcolumnwidth;//单位大小
	public static final String TAG = "FragmentAnswerScard";
	private static final String NUM_TAG = "num";
	private int[][]  donelists ;//做过的题目
	
	
	/**
	 * 返回试题
	 * @param num
	 * @param context
	 * @return
	 */
	public static FragmentAnswerScard newInstance(TwoDimensionArray done_position, Context context) {

		FragmentAnswerScard f = (FragmentAnswerScard) FragmentAnswerScard.instantiate(context,
				FragmentAnswerScard.class.getName());
		Bundle args = new Bundle();
		args.putSerializable(NUM_TAG, done_position);
		f.setArguments(args);
		return f;
	}
	
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view=inflater.inflate(R.layout.fragment_answerscard, container,false);
		mListView = (ListView) view.findViewById(R.id.listview);
		donelists = ((TwoDimensionArray)getArguments().getSerializable(NUM_TAG)).getSs();
		Log.i(TAG,"lists:"+Arrays.toString(donelists[0])+Arrays.toString(donelists[1]));
		//initData();
		allAnswer = initDoubleDimensionalData();
		getscreeninfo();
		countgridviewcolumnwidth();
		FragmentManager fm = getFragmentManager();
		//AnswerscardListViewAdapter adapter = new AnswerscardListViewAdapter(getActivity(), rowIndexList,mList_Gist,fm);
		AnswerscardListViewAdapter adapter = new AnswerscardListViewAdapter(getActivity(),allAnswer,donelists,fm);
		mListView.setAdapter(adapter);
		return view;
	}
	
//	private int[][] formDoubleDimensionalData(ArrayList<Integer> array){
//		for(int i=0;i<array.size();i++)
//		
//		
//		return null;
//	}
//	public void initData() {
//		int b = 0;
//		int a = 0;
//		perCount_row = ((ExamActivity)getActivity()).getCardCount_List();//所有题目（8,10,20,4,5）
//		for (int i = 0; i < perCount_row.size(); i++) {
//			rowIndexList.add(i);//所有题目
//			ArrayList<Integer> temp_mGist = new ArrayList<Integer>();
//			b += a;
//			a = perCount_row.get(i);
//			for (int j = 1; j <= a; j++) {
//				temp_mGist.add(j + b);
//			}
//			mList_Gist.add(temp_mGist);
//		}
//
//		getscreeninfo();
//		countgridviewcolumnwidth();
//	}
	private int[][] initDoubleDimensionalData() {
		int a=0;
		int b=0;
		//把这个集合转换成二维数组，维度同size相同
		perCount_row = ((ExamActivity)getActivity()).getCardCount_List();//所有题目（8,10,20,4,5）
		int[][] mlists = new int[perCount_row.size()][];
		for (int i = 0; i < perCount_row.size(); i++) {
			a = perCount_row.get(i);
			mlists[i] = new int[a];
			for (int j = 1; j <= a; j++) {
				mlists[i][j-1] = j+b;
			}
			b += a;
		}
		return mlists;
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
		int gridviewcolumnall =dip2px(getResources().getDimension(R.dimen.answergridviewhorionspace))*(columnnumber-1);
		gridviewcolumnwidth=(screenWidth-answerlistviewpaddingall-gridviewcolumnall)/columnnumber;
		Log.i(TAG + " gridviewcolumnwidth", "answerlistviewpaddingall:"+answerlistviewpaddingall+" gridviewcolumnall:"+gridviewcolumnall+" gridviewcolumnwidth:"+gridviewcolumnwidth);
	}
	
    public int dip2px(float dpValue) {  
        final float scale = getResources().getDisplayMetrics().density;  
        return (int) (dpValue * scale + 0.5f);  
    }  
}
