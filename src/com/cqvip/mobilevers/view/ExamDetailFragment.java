package com.cqvip.mobilevers.view;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request.Method;
import com.android.volley.Response.Listener;
import com.cqvip.mobilevers.R;
import com.cqvip.mobilevers.config.ConstantValues;
import com.cqvip.mobilevers.entity.PaperDetail;
import com.cqvip.mobilevers.entity.PaperInfo;
import com.cqvip.mobilevers.entity.TagInfo;
import com.cqvip.mobilevers.exam.Exam;
import com.cqvip.mobilevers.exam.Question;
import com.cqvip.mobilevers.exam.Subject;
import com.cqvip.mobilevers.exam.SubjectExam;
import com.cqvip.mobilevers.http.HttpUtils;
import com.cqvip.mobilevers.http.VersStringRequest;
import com.cqvip.mobilevers.ui.ExamActivity;
import com.cqvip.mobilevers.ui.base.BaseFragment;
import com.cqvip.mobilevers.utils.DateUtil;

public class ExamDetailFragment extends BaseFragment implements OnClickListener{

	private static final String DETAL_NAME = "name";
	private static final String DETAL_ID = "id";
	private TextView  tTitle,tTag,tyear,tadddate,ttotal,tscroe,ttime,tsize;
	private String subjectid;
	private Map<String, String> gparams;
	private TextView tv_title;
	private ImageView img_back;
	
	
	  public static ExamDetailFragment newInstance(String name,String id) {
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
		if(reuseView()) {
			return view;
		}
		//Log.i(TAG, "onCreateView");
		view = inflater.inflate(R.layout.paper_info, null);
		tv_title = (TextView) view.findViewById(R.id.tv_show_title);
		tv_title.setText("试卷摘要");
		img_back = (ImageView) view.findViewById(R.id.img_back);
		img_back.setOnClickListener(this);
	
		subjectid = getArguments().getString(DETAL_ID);
		String title = getArguments().getString(DETAL_NAME);
		getDataFromNet(subjectid);
		
		View startExam_btn=(Button) view.findViewById(R.id.btn_exam);
		startExam_btn.setOnClickListener(this);
		
		//访问网络
		//if(HttpUtils.isMobileDataEnable(context))
		
		tTitle = (TextView) view.findViewById(R.id.txt_p_title);
		tTitle.setText(title);
		tTag = (TextView) view.findViewById(R.id.txt_p_tag);
		tyear = (TextView) view.findViewById(R.id.txt_p_year);
		tadddate = (TextView) view.findViewById(R.id.txt_p_adddata);
		ttotal = (TextView) view.findViewById(R.id.txt_p_total);
		tscroe = (TextView) view.findViewById(R.id.txt_p_score);
		ttime = (TextView) view.findViewById(R.id.txt_p_time);
//		tsize = (TextView) view.findViewById(R.id.txt_p_size);
//			
//		tyear.setText(info.getPulishyear());
//		tadddate.setText(info.getAdddate());
//		ttotal.setText(info.getItemcount());
//		tscroe.setText(info.getScore()+"");
//		ttime.setText(info.getSpenttime());
//		tsize.setText(info.getSize()/1024+"KB");
		
		return view;
	}
	

	private void getDataFromNet(String subjectid) {
		customProgressDialog.show();
		gparams = new HashMap<String, String>();
		//获取用户的userID
		 SharedPreferences localUsers =	getActivity().getSharedPreferences("mobilevers", getActivity().MODE_PRIVATE);
	     String userId = localUsers.getString("userid", "0");
	     if(!userId.equals("0")){
	    	 
	     }else{
	    	 
	     }
		
		
		gparams.put(ConstantValues.EXAMPAPERID, subjectid);
		
		
		requestVolley(gparams, ConstantValues.SERVER_URL + ConstantValues.GET_DETAIL_PAPERINFO,
				backlistener, Method.POST);
		
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
			if (response != null) {
			try {
				JSONObject json = new JSONObject(response);
				//判断
				if(json.isNull("error")){
					//返回正常
					PaperDetail paper = new PaperDetail(json);
					setView(paper);

				}else {
					//登陆错误
					//TODO
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		 } else {
//				Toast.makeText(getActivity(), "无数据",
//						Toast.LENGTH_LONG).show();
			}
		}

		private void setView(PaperDetail paper) throws ParseException {
			tyear.setText(paper.getYear());
			tadddate.setText(DateUtil.formatYMD(paper.getUpdatetime()));
			ttotal.setText(paper.getQuestioncount()+"题");
			tscroe.setText(paper.getScore()+"分");
			ttime.setText(paper.getExampapertime()+"分钟");
			tTag.setText(getString(paper.getTag_title()));
			
		}

		private String getString(ArrayList<TagInfo> tag_title) {
			StringBuilder bulBuilder = new StringBuilder();
			for(int i=0;i<tag_title.size();i++){
				bulBuilder.append(tag_title.get(i).getTag());
				int k = tag_title.size();
				if(i<k-1){
					bulBuilder.append(">>");
				}
			}
			return bulBuilder.toString();
		}
	};  
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.img_back:
			   getFragmentManager().popBackStack();
			break;
		case R.id.btn_exam:
			String url = ConstantValues.SERVER_URL + ConstantValues.GETEXAM_ADDR;
			getData(url, subjectid);
		default:
			break;
		}
		
	}

	private void getData(String url, String examPaperId) {
		getDataFromNet(url, examPaperId);
	}
	
	private void getDataFromNet(String url, String examPaperId) {
		customProgressDialog.show();
		gparams = new HashMap<String, String>();
		gparams.put(ConstantValues.EXAMPAPERID, examPaperId);
		Log.i("EXAM",examPaperId);
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
			// onError(2);
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
						//判断是是否有答案，组装好答案
//						ArrayList<Subject> subjects_list=new ArrayList<Subject>(); // 所有subject
//						ArrayList<Question> Question_list=new ArrayList<Question>();
//						ArrayList<Integer> cardCount_List=new ArrayList<Integer>();//答题卡题目
//						
//						SubjectExam[] subjectExams_array=exam.getExam2lists();//获取大题数量
//						for (SubjectExam subjectExam : subjectExams_array) {
//							
//							int count = subjectExam.getQuestionNum();
//							cardCount_List.add(count);//答题卡	
//							Subject[] subjects=subjectExam.getExam3List();//当_questionNum为0时，判断
//							if(subjects!=null){
//							subjects_list.addAll(Arrays.asList(subjects));
//							}
//						}
//						for (Subject subject : subjects_list) {
//							if(subject!=null){
//							ArrayList<Question> lists = subject.getQuestion();
//							Question_list.addAll(lists);
//							}
//						}
						
						
						
						
						if (exam != null) {
							
							Intent intent=new Intent(getActivity(),ExamActivity.class);
							intent.putExtra("exam", exam);
							intent.putExtra("id", subjectid);
							startActivity(intent);
							
//							SubjectExam[] subjectExams_array=exam.getExam2lists();
//							for (SubjectExam subjectExam : subjectExams_array) {
//								startLitmitCount_List.add(countall);
//								countall+=subjectExam.getQuestionNum();
//								subjects_list.addAll(Arrays.asList(subjectExam.getExam3List()));
//								subjectExamCount++;
//							}
//							System.out.println("subjects_list.size()"+subjects_list.size());
//							mPager.setAdapter(mAdapter);
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
	};
		
	  
	 @Override
	public void onDestroyView() {
		 Log.i("ExamDetailFragment", "onDestroyView");
		super.onDestroyView();
	}
	 @Override
	public void onDestroy() {
		 Log.i("ExamDetailFragment", "onDestroy");
		super.onDestroy();
	}
	  @Override
	public void onDetach() {
		  Log.i("ExamDetailFragment", "onDetach");
		super.onDetach();
	}
}
	
