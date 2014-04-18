package com.cqvip.mobilevers.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request.Method;
import com.android.volley.Response.Listener;
import com.cqvip.mobilevers.R;
import com.cqvip.mobilevers.adapter.AnswerscardListViewAdapter;
import com.cqvip.mobilevers.config.ConstantValues;
import com.cqvip.mobilevers.entity.TwoDimensionArray;
import com.cqvip.mobilevers.exam.BaseExamInfo;
import com.cqvip.mobilevers.exam.ExamDoneInfo;
import com.cqvip.mobilevers.exam.SimpleAnswer;
import com.cqvip.mobilevers.http.HttpUtils;
import com.cqvip.mobilevers.http.VersStringRequest;
import com.cqvip.mobilevers.ui.ExamActivity;
import com.cqvip.mobilevers.ui.base.BaseFragment;
import com.cqvip.mobilevers.utils.AnswerUtils;

/**
 * 答题卡类
 * @author luojiang
 *
 */
public class FragmentAnswerScard extends BaseFragment implements OnClickListener {
	private ListView mListView;
	private ArrayList<ArrayList<Integer>> mList_Gist = new ArrayList<ArrayList< Integer>>();//所有的记录，二维数组
	private ArrayList<Integer> rowIndexList = new ArrayList<Integer>();//记录行号
	private  ArrayList<Integer> perCount_row = new ArrayList<Integer>();//
	
	private int[][] allAnswer;
	
	public  static int screenWidth, screenHeight;//屏幕宽高
	public static int gridviewcolumnwidth;//单位大小
	public static final String TAG = "FragmentAnswerScard";
	private static final String NUM_TAG = "num";
	private static final String HANDLEOVER = "handle"; //是否交卷
	private static final String RIGHTWRONG = "right";//是否显示对错
	private static final String USETIME = "time";//是否显示对错
	private int[][]  donelists ;//做过的题目
	private int[][]  rightlists ;//做过正确的题目
	private int[][]  wronglists ;//做过的错误题目
	private SparseArray<SimpleAnswer> clientAnswers;
	
	private boolean isHandleOver;
	private boolean isShowRightWrong;
	
	private TextView tv_handleover;
	private TextView txt_card_tips;
	private ImageView img_back;
	private Map<String,String> gparams;
	private BaseExamInfo baseExamInfo;
	
	private double clientGetScore;//用户得分
	private int rightCount;//对的个数
	private int wrongCount;//错的个数
	private int doneCount;//做题的个数
	private int useTime;//耗时
	
	private ExamDoneInfo examDoneInfo;//耗时
	
	private ExamActivity activity;
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
	/**
	 * 返回试题
	 * @param num
	 * @param context
	 * @return
	 */
	public static FragmentAnswerScard newInstance(TwoDimensionArray done_position, Context context,boolean isHandleover,boolean isShowrightWrong) {
		
		FragmentAnswerScard f = (FragmentAnswerScard) FragmentAnswerScard.instantiate(context,
				FragmentAnswerScard.class.getName());
		Bundle args = new Bundle();
		args.putSerializable(NUM_TAG, done_position);
		args.putBoolean(HANDLEOVER, isHandleover);
		args.putBoolean(RIGHTWRONG, isShowrightWrong);
		f.setArguments(args);
		return f;
	}
	/**
	 * 返回试题
	 * @param num
	 * @param context
	 * @return
	 */
	public static FragmentAnswerScard newInstance(TwoDimensionArray done_position, Context context,boolean isHandleover,boolean isShowrightWrong,int costTime) {
		
		FragmentAnswerScard f = (FragmentAnswerScard) FragmentAnswerScard.instantiate(context,
				FragmentAnswerScard.class.getName());
		Bundle args = new Bundle();
		args.putSerializable(NUM_TAG, done_position);
		args.putBoolean(HANDLEOVER, isHandleover);
		args.putBoolean(RIGHTWRONG, isShowrightWrong);
		args.putInt(USETIME, costTime);
		f.setArguments(args);
		return f;
	}
	
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		this.activity=(ExamActivity)activity;
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view=inflater.inflate(R.layout.fragment_answerscard, container,false);
		mListView = (ListView) view.findViewById(R.id.listview);
		tv_handleover = (TextView) view.findViewById(R.id.tv_hanle_examover);
		txt_card_tips = (TextView) view.findViewById(R.id.txt_card_tips);
		img_back = (ImageView) view.findViewById(R.id.img_back);
		img_back.setOnClickListener(this);
		Bundle bundle = getArguments();
		TwoDimensionArray tmpTow= ((TwoDimensionArray)bundle.getSerializable(NUM_TAG));
		donelists = tmpTow.getAllss();
		rightlists = tmpTow.getRightss();
		wronglists = tmpTow.getWrongss();
		
		isHandleOver = bundle.getBoolean(HANDLEOVER);
		isShowRightWrong = bundle.getBoolean(RIGHTWRONG);
		//Log.i(TAG,"lists:"+Arrays.toString(donelists[0])+Arrays.toString(donelists[1]));
		//initData();
		int unfinished = findUnfinished(donelists);
		
		if(isHandleOver){
			txt_card_tips.setVisibility(View.VISIBLE);
			useTime = bundle.getInt(USETIME);
			String tips = getTips(unfinished);
			txt_card_tips.setText(tips);
			tv_handleover.setVisibility(View.VISIBLE);
			clientAnswers = tmpTow.getClientAnswers();
		}else{
			useTime = 0;
			tv_handleover.setVisibility(View.GONE);
			txt_card_tips.setVisibility(View.GONE);
		}
			tv_handleover.setOnClickListener(this);
		
		allAnswer = initDoubleDimensionalData();
		getscreeninfo();
		countgridviewcolumnwidth();
		FragmentManager fm = getFragmentManager();
		//AnswerscardListViewAdapter adapter = new AnswerscardListViewAdapter(getActivity(), rowIndexList,mList_Gist,fm);
		AnswerscardListViewAdapter adapter = new AnswerscardListViewAdapter(getActivity(),allAnswer,donelists,rightlists,wronglists,fm,isShowRightWrong);
		mListView.setAdapter(adapter);
		return view;
	}
	
	private String getTips(int unfinished) {
			if(unfinished==0){
			return "您已经答完所有题目！";
			}else{
				return "您还有"+unfinished+"道题未做，确定要交卷？";
			}
		}
	private int findUnfinished(int[][] donelists2) {
			int count = 0;
			for(int i=0;i<donelists2.length;i++){
				for(int j=0;j<donelists2[i].length;j++){
					 if(donelists2[i][j]==0){
						 count++;
					 }
				}
			}
			return count;
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
		//Log.i(TAG + " gridviewcolumnwidth", "answerlistviewpaddingall:"+answerlistviewpaddingall+" gridviewcolumnall:"+gridviewcolumnall+" gridviewcolumnwidth:"+gridviewcolumnwidth);
	}
	
    public int dip2px(float dpValue) {  
        final float scale = getResources().getDisplayMetrics().density;  
        return (int) (dpValue * scale + 0.5f);  
    }
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_hanle_examover:
			 //clientAnswers,发送数据到服务器
			sendResultToServer();
			((ExamActivity) getActivity()).cancelTime();
			//跳转
		case R.id.img_back:
			getFragmentManager().popBackStack();
			break;

		default:
			break;
		}
	}
	
	
	/**
	 * 组装答案，样式如1,2,3,4 
	 * @param perAnswer
	 */
	private String getAnswer(ArrayList<SimpleAnswer> perAnswer) {
		StringBuilder answer = new StringBuilder();
		for(int j=0;j<perAnswer.size();j++){
			String mAnswer = perAnswer.get(j).getAnswer();
			answer.append(Integer.parseInt(mAnswer)+"");
			if(j!=perAnswer.size()-1){
				answer.append(",");
			}
		}
		return answer.toString();
	}
	private void sendResultToServer() {
		customProgressDialog.show();
		baseExamInfo = ((ExamActivity)getActivity()).getBaseExamInfo();
	    rightCount = getCount(rightlists);
		wrongCount = getCount(wronglists);
		doneCount = getCount(donelists);
		clientGetScore = getTotalScore(clientAnswers);//算出分数 
		examDoneInfo = new ExamDoneInfo(rightCount, wrongCount, doneCount, clientGetScore, useTime);
		String result = AnswerUtils.formResult(clientAnswers);
		//Log.i(TAG,"result:"+result);
		
		 SharedPreferences localUsers =	getActivity().getSharedPreferences("mobilevers", getActivity().MODE_PRIVATE);
		String userId = localUsers.getString("userid", "0");
		if(!userId.equals("0")){
		gparams = new HashMap<String, String>();
		gparams.put("userId", userId);
		gparams.put("examPaperId", baseExamInfo.getId());
		//Log.i(TAG,"examPaperId:"+baseExamInfo.getId());
		gparams.put("userAnswer", result);
		gparams.put("isEnd", ConstantValues.DEFAULTISEND+"");
		//Log.i(TAG,"userAnswer:"+result);
		requestVolley(gparams, ConstantValues.SERVER_URL + ConstantValues.SAVEEXAMANSWER,
				backlistener, Method.POST);
		}else{
			if(customProgressDialog!=null&&customProgressDialog.isShowing())
				customProgressDialog.dismiss();
			Toast.makeText(getActivity(), "请先登录", Toast.LENGTH_LONG).show();
		}
		
	}



	private int getCount(int[][] lists) {
		int count = 0;
		for(int i=0;i<lists.length;i++){
			for(int j=0;j<lists[i].length;j++){
				if(lists[i][j]>0){
					count++;
				}
			}
		}
		return count;
	}
	private void requestVolley(final Map<String, String> gparams, String url,
			Listener<String> listener, int post) {
		VersStringRequest mys = new VersStringRequest(post, url, listener, volleyErrorListener) {
			protected Map<String, String> getParams()
					throws com.android.volley.AuthFailureError {
				return gparams;
			};
		};
		mys.setRetryPolicy(HttpUtils.setTimeout());
		mQueue.add(mys);

	}
	private  Listener<String> backlistener = new Listener<String>() {
		@Override
		public void onResponse(String response) {
			if(customProgressDialog!=null&&customProgressDialog.isShowing())
			customProgressDialog.dismiss();
			//解析结果
			//Log.i(TAG, "============:"+response);
			if (response != null) {
			try {
				JSONObject json = new JSONObject(response);
				//判断
				if(json.isNull("error")){
					//返回正常
				boolean  res = 	json.getBoolean("status");
				if(activity!=null){
					if(res){
					Toast.makeText(activity, "交卷成功",
							Toast.LENGTH_LONG).show();
					activity.setResult(activity.RESULT_OK, (new Intent()).putExtra("clientGetScore", clientGetScore+""));
					}else{
						Toast.makeText(activity, "交卷失败",
								Toast.LENGTH_LONG).show();
					}
				}	
					
				}else {
					//登陆错误
					//TODO
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		 } else {
				Toast.makeText(getActivity(), "交卷失败",
				Toast.LENGTH_LONG).show();
			}
			if(activity!=null){
				activity.getSupportFragmentManager().popBackStack();
				ResultFragment newFragment = ResultFragment.newInstance(baseExamInfo,examDoneInfo);
				activity.addFragmentToStack(newFragment,R.id.exam_fl,activity.FRGMENT_HANDEDPAPER);
			}
		}
	};
	private double getTotalScore(SparseArray<SimpleAnswer> answers) {
		double total = 0 ;
		for(int i=0;i<answers.size();i++){
			SimpleAnswer perAnswer =  answers.get(i);
			if(perAnswer!=null){
			total+= perAnswer.getScore();
			Log.i(TAG, "getScore:"+perAnswer.getScore());
			}
		}
		//Log.i(TAG, "getTotalScore:"+total);
		return total;
	}  
}
