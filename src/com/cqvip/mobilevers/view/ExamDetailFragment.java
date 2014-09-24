package com.cqvip.mobilevers.view;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request.Method;
import com.android.volley.Response.Listener;
import com.cqvip.mobilevers.R;
import com.cqvip.mobilevers.config.ConstantValues;
import com.cqvip.mobilevers.entity.PaperDetail;
import com.cqvip.mobilevers.entity.TagInfo;
import com.cqvip.mobilevers.entity.TwoDimensionArray;
import com.cqvip.mobilevers.exam.Exam;
import com.cqvip.mobilevers.exam.Question;
import com.cqvip.mobilevers.exam.SeriSqareArray;
import com.cqvip.mobilevers.exam.SimpleAnswer;
import com.cqvip.mobilevers.exam.Subject;
import com.cqvip.mobilevers.exam.SubjectExam;
import com.cqvip.mobilevers.http.HttpUtils;
import com.cqvip.mobilevers.http.VersStringRequest;
import com.cqvip.mobilevers.ui.ExamActivity;
import com.cqvip.mobilevers.ui.FragmentExamActivity;
import com.cqvip.mobilevers.ui.FragmentMineActivity;
import com.cqvip.mobilevers.ui.FragmentSearchActivity;
import com.cqvip.mobilevers.ui.MainActivity;
import com.cqvip.mobilevers.ui.base.BaseFragment;
import com.cqvip.mobilevers.ui.base.BaseMainFragmentActivity;
import com.cqvip.mobilevers.utils.DateUtil;
import com.cqvip.mobilevers.utils.Utils;

public class ExamDetailFragment extends BaseFragment implements OnClickListener {

	private static final String DETAL_NAME = "name";
	private static final String DETAL_ID = "id";
	private TextView tTitle, tTag, tyear, tadddate, ttotal, tscroe, ttime,
			tsize, favorite_tv;
	
	private TextView tTestprogress;
	private Button btn_continue,btn_begin;
	public String subjectid;
	private Map<String, String> gparams;
	private TextView tv_title;
	private int[][] done_position;
	private int[][] right_position;
	private int[][] wrong_position;

	private ArrayList<Integer> allAnswerPostion;
	private ArrayList<Integer> rightAnswerPostion;
	private ArrayList<Integer> wrongAnswerPostion;
	private TwoDimensionArray dimension = null;
	private SeriSqareArray<SimpleAnswer> clientAnswer;
	public final static String TAG="ExamDetailFragment";
	public final static String TAG2="ExamDetailFragment2";
	private int finalposition = 0;
	private int status = 0;
	private boolean isConinue = false;//是否重做
	// private ImageView img_back;
	
	PaperDetail paper;
	private boolean isfavorite_final=true;//最终的收藏状态
	private boolean isfavorite_init=false;//初始的收藏状态
	private int isfavorite_initcount=0;//
	static final private int GET_CODE = 0;

	public interface I_ExamDetail{
		public void delfavorite();
	}
	
	public static ExamDetailFragment newInstance(String name, String id) {
		ExamDetailFragment f = new ExamDetailFragment();

		// Supply num input as an argument.
		Bundle args = new Bundle();
		args.putString(DETAL_NAME, name);
		args.putString(DETAL_ID, id);
		f.setArguments(args);
		return f;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		((BaseMainFragmentActivity)getActivity()).tag=TAG;
		if (reuseView()) {
			return view;
		}
		// Log.i(TAG, "onCreateView");
		view = inflater.inflate(R.layout.paper_info, null);
		tv_title = (TextView) view.findViewById(R.id.tv_show_title);
		tv_title.setText("试卷摘要");
		// img_back = (ImageView) view.findViewById(R.id.img_back);
		// img_back.setOnClickListener(this);

		subjectid = getArguments().getString(DETAL_ID);
		//Log.i(TAG, subjectid);
		String title = getArguments().getString(DETAL_NAME);
		getDataFromNet(subjectid);
		// 访问网络
		// if(HttpUtils.isMobileDataEnable(context))
	//	tTestscore = (TextView) view.findViewById(R.id.txt_paper_testscore);
		tTestprogress= (TextView) view.findViewById(R.id.txt_paper_testprogress);
		btn_begin = (Button) view.findViewById(R.id.btn_exam_begin);
		btn_continue = (Button) view.findViewById(R.id.btn_exam_continue);
		btn_begin.setOnClickListener(this);
		btn_continue.setOnClickListener(this);
		tTitle = (TextView) view.findViewById(R.id.txt_p_title);
		tTitle.setText(title);
		tTag = (TextView) view.findViewById(R.id.txt_p_tag);
		tyear = (TextView) view.findViewById(R.id.txt_p_year);
		tadddate = (TextView) view.findViewById(R.id.txt_p_adddata);
		ttotal = (TextView) view.findViewById(R.id.txt_p_total);
		tscroe = (TextView) view.findViewById(R.id.txt_p_score);
		ttime = (TextView) view.findViewById(R.id.txt_p_time);
		favorite_tv = (TextView) view.findViewById(R.id.favorite_tv);
		//ll_testscore=view.findViewById(R.id.ll_testscore);
		favorite_tv.setOnClickListener(this);
		// tsize = (TextView) view.findViewById(R.id.txt_p_size);
		//
		// tyear.setText(info.getPulishyear());
		// tadddate.setText(info.getAdddate());
		// ttotal.setText(info.getItemcount());
		// tscroe.setText(info.getScore()+"");
		// ttime.setText(info.getSpenttime());
		// tsize.setText(info.getSize()/1024+"KB");
		
		
		

		return view;
	}

@Override
public void onAttach(Activity activity) {
	// TODO Auto-generated method stub
	super.onAttach(activity);
}
	
	public void updateview(boolean islogin){
		getDataFromNet(subjectid);
	}
	
	private void getDataFromNet(String subjectid) {
		customProgressDialog.show();
		gparams = new HashMap<String, String>();
		// 获取用户的userID
		SharedPreferences localUsers = getActivity().getSharedPreferences(
				"mobilevers", getActivity().MODE_PRIVATE);
		String userid = localUsers.getString("userid", "0");
		gparams.put("userId", userid);
		gparams.put(ConstantValues.EXAMPAPERID, subjectid);
		//Log.i(TAG, "subjectid:"+subjectid);
		requestVolley(gparams, ConstantValues.SERVER_URL
				+ ConstantValues.GET_DETAIL_PAPERINFO, backlistener,
				Method.POST);

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
	
	/**
	 * 获取试卷详细信息
	 */
	private Listener<String> backlistener = new Listener<String>() {
		@Override
		public void onResponse(String response) {
			if (customProgressDialog != null
					&& customProgressDialog.isShowing())
				customProgressDialog.dismiss();
			// 解析结果
			if (response != null) {
				try {
					JSONObject json = new JSONObject(response);
					//Log.i(TAG, response);
					// 判断
					if (json.isNull("error")) {
						// 返回正常
						paper = new PaperDetail(json);
						setView(paper);

					} else {
						// 登陆错误
						// TODO
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				// Toast.makeText(getActivity(), "无数据",
				// Toast.LENGTH_LONG).show();
			}
		}

		public void setView(PaperDetail paper) throws ParseException {
			tyear.setText(paper.getYear());
			tadddate.setText(DateUtil.formatYMD(paper.getUpdatetime()));
			ttotal.setText(paper.getQuestioncount() + "题");
			tscroe.setText(paper.getScore() + "分");
			ttime.setText(paper.getExampapertime() + "分钟");
			tTag.setText(getString(paper.getTag_title()));
			//Log.i(TAG, "isFavor: "+paper.isFavor());
			if(isfavorite_final=paper.isFavor()){
				favorite_tv_drawable(R.drawable.sc2);
			}else{
				favorite_tv_drawable(R.drawable.sc1);
			}
			if(isfavorite_initcount++==0){
				isfavorite_init=paper.isFavor();
			}
			//Log.i(TAG, "isfavorite_initcount: "+isfavorite_initcount);
			status = paper.getTeststatus();
			//Log.i(TAG, "status"+status);
			switch (status) {
			case ConstantValues.ITESTSTATUS_UNDO:
				//没有做
				setUndoView(paper);
				break;
			case ConstantValues.ITESTSTATUS_DOING:
				//正在做
				setDoingView(paper);
				break;

			default:
				//已经做过的
				setDoneView(paper);
				break;
			}
			
			
		}

		private String getString(ArrayList<TagInfo> tag_title) {
			StringBuilder bulBuilder = new StringBuilder();
			for (int i = 0; i < tag_title.size(); i++) {
				bulBuilder.append(tag_title.get(i).getTag());
				int k = tag_title.size();
				if (i < k - 1) {
					bulBuilder.append(">>");
				}
			}
			return bulBuilder.toString();
		}
	};

	private void setUndoView(PaperDetail paper) {
		btn_continue.setVisibility(View.GONE);
		btn_begin.setText("开始做题");
		tTestprogress.setVisibility(View.GONE);
		//ll_testscore.setVisibility(View.GONE);
		status = ConstantValues.ITESTSTATUS_UNDO;
		//tTestscore.setVisibility(View.GONE);
	}
	private void setDoneView(PaperDetail paper) {
		btn_continue.setText("查看答案");
		btn_begin.setText("重新做题");
		btn_continue.setVisibility(View.VISIBLE);
		tTestprogress.setVisibility(View.VISIBLE);
		//ll_testscore.setVisibility(View.VISIBLE);
		
		status = ConstantValues.ITESTSTATUS_DONE;
		if(status==ConstantValues.ITESTSTATUS_DOING){
		tTestprogress.setText("做卷进度："+paper.getTestquestionNum()+"|"+paper.getQuestioncount());
		}else if(status == ConstantValues.ITESTSTATUS_DONE){
		tTestprogress.setText("得分："+DateUtil.formDouble(paper.getTestscore())+"分");
		}
	}

	private void setDoingView(PaperDetail paper) {
		btn_continue.setText("继续做题");
		btn_begin.setText("重新做题");
		btn_continue.setVisibility(View.VISIBLE);
		tTestprogress.setVisibility(View.VISIBLE);
		//ll_testscore.setVisibility(View.GONE);
		tTestprogress.setText("做卷进度："+paper.getTestquestionNum()+"|"+paper.getQuestioncount());
		status = ConstantValues.ITESTSTATUS_DOING;
	}

	
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == GET_CODE) {
            if (resultCode == getActivity().RESULT_CANCELED) {
          //Log.i(TAG, "cancelled");
            }else if(resultCode == 5){
            	if(!isConinue){
            	setUndoView(paper);
            	}
            }else {
            	if(data!=null){
            		double clientGetScore=data.getDoubleExtra("clientGetScore", -1);
            		int doneCount=data.getIntExtra("doneCount", 0);
            		if(clientGetScore!=-1){
            			paper.setTestscore(clientGetScore);
            			paper.setTestquestionNum(doneCount);
            			setDoneView(paper);
            			//Log.i(TAG, "clientGetScore:"+clientGetScore);
            		}else{
            			paper.setTestquestionNum(doneCount);
            			setDoingView(paper);
            			//Log.i(TAG, "doneCount:"+doneCount);
            		}
            	}
            }
        }
    }
	
	@Override
	public void onClick(View v) {
		String userid = null;
		switch (v.getId()) {
		case R.id.favorite_tv:
			toFavorite();
			break;
		case R.id.btn_exam_begin:
			isConinue = false;
			if ((userid = Utils.checkUserid(getActivity())) != null) {
				String url = ConstantValues.SERVER_URL
						+ ConstantValues.GETPASTEXAMINFO;
				getData(url, subjectid, userid,ConstantValues.BEGIN_RESTAR);
			}
			break;
		case R.id.btn_exam_continue:
			isConinue = true;
			if ((userid = Utils.checkUserid(getActivity())) != null) {
				String url = ConstantValues.SERVER_URL
						+ ConstantValues.GETPASTEXAMINFO;
				getData(url, subjectid, userid,ConstantValues.BGEIN_CONTINUE);
			}
			break;
		default:
			break;
		}

	}

	

	private void toFavorite() {
		String userid = null;
		if ((userid = Utils.checkUserid(getActivity())) == null) {
			return;
		}
		customProgressDialog.show();
		String url = ConstantValues.SERVER_URL
				+ ConstantValues.ADDFAVORITESEXAMPAPER;
		gparams = new HashMap<String, String>();
		gparams.put("userId", userid);
		gparams.put("examPaperId", subjectid);
		requestVolley(url, back_favorite_ls, Method.POST);
	}
	
	private void deleteFavorite() {
		String userid = null;
		if ((userid = Utils.checkUserid(getActivity())) == null) {
			return;
		}
		String url = ConstantValues.SERVER_URL
				+ ConstantValues.DELETEFAVORITESEXAMPAPER;
		gparams = new HashMap<String, String>();
		gparams.put("userId", userid);
		gparams.put("examPaperId", subjectid);
		requestVolley(url, back_deletefavorite_ls, Method.POST);
	}

	private void getData(String url, String examPaperId, String userId,int type) {
		getDataFromNet(url, examPaperId, userId,type);
	}

	private void getDataFromNet(String url, String examPaperId, String userId,int type) {

		customProgressDialog.show();
		gparams = new HashMap<String, String>();
		gparams.put(ConstantValues.EXAMPAPERID, examPaperId);
		gparams.put("userId", userId);
		if(type==ConstantValues.BEGIN_RESTAR){
			gparams.put("restart", ConstantValues.BEGIN_RESTAR+"");
		}else{
			gparams.put("restart", ConstantValues.BGEIN_CONTINUE+"");
		}
		requestVolley(url, back_ls, Method.POST);
	}

	private void requestVolley(String addr, Listener<String> bl, int method) {
		try {
			VersStringRequest mys = new VersStringRequest(method, addr, bl,
					volleyErrorListener) {

				protected Map<String, String> getParams()
						throws com.android.volley.AuthFailureError {
					return gparams;
				};
			};
			mys.setRetryPolicy(HttpUtils.setTimeout());
			mQueue.add(mys);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Listener<String> back_ls = new Listener<String>() {

		@Override
		public void onResponse(String response) {
			if (customProgressDialog != null
					&& customProgressDialog.isShowing())
				customProgressDialog.dismiss();
			// 解析结果
			if (response != null) {
				try {
					JSONObject json = new JSONObject(response);
					// 判断
					if (json.isNull("error")) {
						// 返回正常
						Exam exam = new Exam(json);
						// 判断是是否有答案，组装好答案
						ArrayList<Subject> subjects_list = new ArrayList<Subject>(); // 所有subject
						ArrayList<Question> Question_list = new ArrayList<Question>();
						ArrayList<Integer> cardCount_List = new ArrayList<Integer>();// 答题卡题目

						SubjectExam[] subjectExams_array = exam.getExam2lists();// 获取大题数量
						for (SubjectExam subjectExam : subjectExams_array) {

							int count =  getQuestonCount( subjectExam);
							cardCount_List.add(count);// 答题卡
							Subject[] subjects = subjectExam.getExam3List();// 当_questionNum为0时，判断
							if (subjects != null) {
								subjects_list.addAll(Arrays.asList(subjects));
							}
						}
						for (Subject subject : subjects_list) {
							if (subject != null) {
								ArrayList<Question> lists = subject
										.getQuestion();
								Question_list.addAll(lists);
							}
						}

						int[][] all_position = DateUtil
								.initDoubleDimensionalData(cardCount_List);// 初始化答题卡的二维结构
						int mCount = subjectExams_array.length;
						done_position = new int[mCount][];
						right_position = new int[mCount][];
						wrong_position = new int[mCount][];
						for (int i = 0; i < mCount; i++) {
							done_position[i] = new int[ getQuestonCount( subjectExams_array[i])];
							right_position[i] = new int[getQuestonCount( subjectExams_array[i])];
							wrong_position[i] = new int[getQuestonCount( subjectExams_array[i])];
						}
						// 获取答案
						SimpleAnswer[] answers = exam.getAnswerlists();
						if (answers != null && answers.length > 0) {

							clientAnswer = new SeriSqareArray<SimpleAnswer>();
							// 初始化对错
							getAllposition(answers, Question_list);// (1,5,6,7)
							// 初始化位置
							formTwoDimetion(all_position);

							dimension = new TwoDimensionArray(all_position,
									done_position, right_position,
									wrong_position, clientAnswer);

						}
						
						if (exam != null) {
							Intent intent = new Intent(getActivity(),
									ExamActivity.class);
							Bundle bundle = new Bundle();
							bundle.putSerializable("exam", exam);
							bundle.putString("id", subjectid);
							intent.putExtra("bundle", bundle);
							if(isConinue){
								bundle.putSerializable("dimen", dimension);
								if(status==ConstantValues.ITESTSTATUS_DOING){
								intent.putExtra("final", finalposition);
								}else{
								intent.putExtra("final", 0);
								}
								
								intent.putExtra("status", status);
							}else{
								bundle.putSerializable("dimen", null);	
								intent.putExtra("final", 0);
								intent.putExtra("status", 0);
							}
							 startActivityForResult(intent, GET_CODE);
						}
					} else {
						// 登陆错误
						// TODO
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				Toast.makeText(getActivity(), "无数据", Toast.LENGTH_LONG).show();
			}
		}

		private int getQuestonCount(SubjectExam subjectExam) {
			int count = 0;
			Subject[] subs = subjectExam.getExam3List();
			if(subs.length>0){
			for(int i=0;i<subs.length;i++){
				if(subs[i]!=null&&subs[i].getQuestion()!=null){
				int mSize = subs[i].getQuestion().size();
				count += mSize;
				}
			}
			}
			return count;
		}

		private void formTwoDimetion(int[][] allpostion) {

			for (int i = 0; i < allAnswerPostion.size(); i++) {
				int postion = allAnswerPostion.get(i);
				ColAndRow colAndRow = getItemPosition(postion, allpostion);
				done_position[colAndRow.row][colAndRow.col] = postion + 1;
			}
			for (int i = 0; i < rightAnswerPostion.size(); i++) {
				int postion = rightAnswerPostion.get(i);
				ColAndRow colAndRow = getItemPosition(postion, allpostion);
				right_position[colAndRow.row][colAndRow.col] = postion + 1;
			}
			for (int i = 0; i < wrongAnswerPostion.size(); i++) {
				int postion = wrongAnswerPostion.get(i);
				ColAndRow colAndRow = getItemPosition(postion, allpostion);
				wrong_position[colAndRow.row][colAndRow.col] = postion + 1;
			}

		}

		private ColAndRow getItemPosition(int position, int[][] allpostion) {
			ColAndRow cr = null;

			for (int i = 0; i < allpostion.length; i++) {
				for (int j = 0; j < allpostion[i].length; j++) {
					if (allpostion[i][j] == (position + 1)) {
						cr = new ColAndRow(i, j);
					}
				}
			}
			return cr;
		}

		private void getAllposition(SimpleAnswer[] answers,
				ArrayList<Question> question_list) {
			allAnswerPostion = new ArrayList<Integer>();
			rightAnswerPostion = new ArrayList<Integer>();
			wrongAnswerPostion = new ArrayList<Integer>();

			for (int i = 0; i < answers.length; i++) {
				SimpleAnswer ans = answers[i];
				String an_id = ans.getId();
				double score = ans.getScore();
				for (int j = 0; j < question_list.size(); j++) {
					String ques_id = question_list.get(j).getId();
					if (an_id.equals(ques_id)) {//
						allAnswerPostion.add(j);
						clientAnswer.append(j, ans);
						if (score > 0) {
							rightAnswerPostion.add(j);
						} else {
							wrongAnswerPostion.add(j);
						}
					}
				}
			}
			SimpleAnswer finalans = answers[answers.length-1];
			
				String an_id = finalans.getId();
				for (int j = 0; j < question_list.size(); j++) {
					String ques_id = question_list.get(j).getId();
					if (an_id.equals(ques_id)) {//
					    finalposition = j;
					}
			}
		}
	};

	private class ColAndRow{
		int row;//行
		int col;//列
		public ColAndRow(int row,int col){
			this.col = col;
			this.row = row;
		}
	}

	private Listener<String> back_favorite_ls = new Listener<String>() {

		@Override
		public void onResponse(String response) {
			// 解析结果
			if (response != null) {
				try {
					JSONObject json = new JSONObject(response);
					boolean isfavorite = json.getBoolean("status");
					if (isfavorite) {
						if (customProgressDialog != null
								&& customProgressDialog.isShowing())
							customProgressDialog.dismiss();
						Toast.makeText(
								getActivity(),
								getActivity().getString(
										R.string.favorite_success),
								Toast.LENGTH_SHORT).show();
						favorite_tv_drawable(R.drawable.sc2);
						isfavorite_final=true;
						sync_updateview();
					} else {
						deleteFavorite();
					}
				} catch (Exception e) {
					e.printStackTrace();
					Toast.makeText(getActivity(),
							getActivity().getString(R.string.favorite_fail),
							Toast.LENGTH_SHORT).show();
				}
			} else {
				Toast.makeText(getActivity(),
						getActivity().getString(R.string.favorite_fail),
						Toast.LENGTH_SHORT).show();
			}
		}
	};
	
	private Listener<String> back_deletefavorite_ls = new Listener<String>() {

		@Override
		public void onResponse(String response) {
			if (customProgressDialog != null
					&& customProgressDialog.isShowing())
				customProgressDialog.dismiss();
			// 解析结果
			if (response != null) {
				try {
					JSONObject json = new JSONObject(response);
					boolean isfavorite = json.getBoolean("status");
					if (isfavorite) {
						Toast.makeText(
								getActivity(),
								getActivity().getString(
										R.string.deletefavorite_success),
								Toast.LENGTH_SHORT).show();
						favorite_tv_drawable(R.drawable.sc1);
						isfavorite_final=false;
						sync_updateview();
					} else {

					}
				} catch (Exception e) {
					e.printStackTrace();
				//	Log.i("back_deletefavorite_ls", response);
					Toast.makeText(getActivity(),
							getActivity().getString(R.string.deletefavorite_fail),
							Toast.LENGTH_SHORT).show();
				}
			} else {
				Toast.makeText(getActivity(),
						getActivity().getString(R.string.deletefavorite_fail),
						Toast.LENGTH_SHORT).show();
			}
		}
	};

	/**
	 * 同步
	 */
	private void sync_updateview() {
		//从题库的试卷摘要点击收藏按钮，我的试卷摘要的同步
		FragmentExamActivity fragmentExamActivity=(FragmentExamActivity) ((MainActivity)getActivity().getParent()).getLocalActivityManager().getActivity("题库");
		FragmentMineActivity fragmentMineActivity=(FragmentMineActivity) ((MainActivity)getActivity().getParent()).getLocalActivityManager().getActivity("我的");
		ExamDetailFragment examDetailFragment_exam=null;
		ExamDetailFragment examDetailFragment_mine=null;
		if(fragmentExamActivity!=null){
		examDetailFragment_exam=(ExamDetailFragment) fragmentExamActivity.fManager.findFragmentByTag(ExamDetailFragment.TAG);
		}
		if(fragmentMineActivity!=null){
		examDetailFragment_mine=(ExamDetailFragment) fragmentMineActivity.fManager.findFragmentByTag(ExamDetailFragment.TAG2);
		}
		boolean temp_bool=false;
		if(examDetailFragment_mine!=null&&examDetailFragment_exam!=null&&this.getTag().equals(TAG2)&&subjectid.equals(examDetailFragment_mine.subjectid)){
			examDetailFragment_exam.updateview(true);
			//Log.i("DoneExamPaperListFragment", subjectid+"----"+examDetailFragment.subjectid);
		}else if(examDetailFragment_exam!=null&&examDetailFragment_mine!=null&&this.getTag().equals(TAG)&&subjectid.equals(examDetailFragment_exam.subjectid)){
			examDetailFragment_mine.updateview(true);
			temp_bool=true;
		}
		//从题库的试卷摘要点击收藏按钮,收藏列表的同步
		if(fragmentMineActivity!=null&&!temp_bool&&this.getTag().equals(TAG)){
			DoneExamPaperListFragment doneExamPaperListFragment=(DoneExamPaperListFragment) fragmentMineActivity.fManager.findFragmentByTag(DoneExamPaperListFragment.TAG);
		    if(doneExamPaperListFragment!=null){
		    	doneExamPaperListFragment.updataFromExamDetailFragment(isfavorite_final, subjectid);
		}
		}
	}
	
//	private void sync_updateview_onpause() {
//		FragmentMineActivity fragmentMineActivity=(FragmentMineActivity) ((MainActivity)getActivity().getParent()).getLocalActivityManager().getActivity("我的");
//		if(fragmentMineActivity!=null&&!temp_bool&&this.getTag().equals(TAG)){//
//			DoneExamPaperListFragment doneExamPaperListFragment=(DoneExamPaperListFragment) fragmentMineActivity.fManager.findFragmentByTag(DoneExamPaperListFragment.TAG);
//		    if(doneExamPaperListFragment!=null){
//		    	doneExamPaperListFragment.updataFromExamDetailFragment(isfavorite_final, subjectid);
//		}
//		}
//	 }
	private void favorite_tv_drawable(int id) {
		Drawable drawable = getActivity().getResources().getDrawable(
				id);
		// / 这一步必须要做,否则不会显示.
		drawable.setBounds(0, 0, drawable.getMinimumWidth(),
				drawable.getMinimumHeight());
		favorite_tv.setCompoundDrawables(drawable, null, null, null);
	}

	@Override
	public void onPause() {
		super.onPause();
		//Log.i(TAG, "onPause");
		//sync_updateview_onpause();
	}
	
	@Override
	public void onDestroyView() {
		if(!isfavorite_final){
		if(!FragmentSearchActivity.class.isInstance(getActivity()))	{
		((I_ExamDetail)getActivity()).delfavorite();
		}
		}
		super.onDestroyView();
	}
}
