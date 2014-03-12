package com.cqvip.mobilevers.view;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cqvip.mobilevers.R;
import com.cqvip.mobilevers.exam.Content;
import com.cqvip.mobilevers.exam.Question;
import com.cqvip.mobilevers.exam.Solution;
import com.cqvip.mobilevers.exam.Subject;
import com.cqvip.mobilevers.exam.SubjectExam;
import com.cqvip.mobilevers.ui.ExamActivity;
import com.cqvip.mobilevers.utils.SubjectType;
import com.cqvip.mobilevers.widget.ImageTextView;

public class ExamFragment extends Fragment{
	private static final String NUM_TAG = "num";
	private int position; //第几个fragment

//    private ArrayList<Subject> subjects_list;
//    public ArrayList<Integer> startLitmitCount_List;
    
	private ArrayList<SubjectExam> subjectExam_list=new ArrayList<SubjectExam>(); // 所有subject
	private ArrayList<Subject> subjects_list=new ArrayList<Subject>(); // 所有subject
	private ArrayList<Question> Question_list=new ArrayList<Question>(); // 所有question
	private ArrayList<Integer> subjectExamCount_list=new ArrayList<Integer>(); // 所有subject
	private ArrayList<Integer> startLitmitCount_List=new ArrayList<Integer>();//统计subject题目
    
    
    @Override
    public void onAttach(Activity activity) {
    	Log.i("ExamFragment", "onAttach"+position);
    	super.onAttach(activity);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	Log.i("ExamFragment", "onCreate"+position);
    	super.onCreate(savedInstanceState);
    }
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_exampager, container,false);
		position = getArguments().getInt(NUM_TAG);//fragment位置，及第几个Question
		Log.i("ExamFragment", "onCreateView"+position);
		subjectExamCount_list = ((ExamActivity)getActivity()).getSubjectExamCount_list();
		startLitmitCount_List =((ExamActivity)getActivity()).getStartLitmitCount_List();
		subjectExam_list = ((ExamActivity)getActivity()).getSubjectExam_list();
		subjects_list = ((ExamActivity)getActivity()).getSubjects_list();
		Question_list = ((ExamActivity)getActivity()).getQuestion_list();
		
		int current=0;//当前的第子题
		int currentSubject = 0;//当前大题
		boolean isfirst = false;
		boolean isfirstSubject = false;
		
		if(position==0){
			current=0;
			isfirst=true;
		}else{
		//遍历判断是第几子题目 
		for (int i = 0; i < startLitmitCount_List.size(); i++) {
		
			if(position==startLitmitCount_List.get(i)){
				current=i;//
				isfirst=true;
				break;
			}
		  }
		}
		
		//判断第几大题
		
		if(current == 0){
			currentSubject=0;
			isfirstSubject=true;
		}else{
		//遍历判断是第几子题目 
		for (int i = 0; i < subjectExamCount_list.size(); i++) {
		
			if(current==subjectExamCount_list.get(i)){
				currentSubject=i;//
				isfirstSubject=true;
				break;
			}
		  }
		}
		
		
		ImageTextView tv_title = (ImageTextView) v.findViewById(R.id.txt_exam_title);
		ImageTextView tv_answer = (ImageTextView) v.findViewById(R.id.correct_answer);
		ImageTextView tv_answerdesc = (ImageTextView) v.findViewById(R.id.anser_desc);
		
		//第几大题
		
		//第几子题
		Question question = Question_list.get(position);
		Content question_title = question.getTitle();
		Solution solution =  question.getSolution();
		Content answer = solution.getAnswer();
		Content answerdesc = solution.getAnswerDesc();
		//判断
		//获取当前小题的subject和大题subjectExam;
		Subject now_subject = subjects_list.get(current);
		//SubjectExam  now_subjectExam = subjectExam_list.get(currentSubject);
		//判断试题类型
		String sub_title = now_subject.getTitle().getContent();
		String sub_type = now_subject.getType();
		Log.i("subjet:",sub_type+sub_title);
		
		//判断小题的类型
		if(!isfirst){
		if (sub_type.equals(SubjectType.ShowStyle.SSS_SINGLE_SEL_MULTIRIGHTITEM
				.toString()))
			sub_type = SubjectType.ShowStyle.SSS_SINGLE_SEL
					.toString();

		if (sub_type.equals( SubjectType.ShowStyle.SSS_SINGLE_SEL
				.toString())
				|| sub_type.equals( SubjectType.ShowStyle.SSS_MULTI_SEL
						.toString())
				|| sub_type.equals( SubjectType.ShowStyle.SSS_SINGLE_MULTI_SEL
						.toString())
				|| sub_type.equals( SubjectType.ShowStyle.SSS_FILL
						.toString())
				|| sub_type.equals( SubjectType.ShowStyle.SSS_JUDGEMENT
						.toString())
				|| sub_type.equals( SubjectType.ShowStyle.SSS_TEXT_QUSTION
						.toString())
				|| sub_type.equals( SubjectType.ShowStyle.SSS_SIMPLE_SINGLE_SEL
						.toString())
				|| sub_type.equals( SubjectType.ShowStyle.SSS_SIMPLE_MULTI_SEL
						.toString())
				|| sub_type.equals( SubjectType.ShowStyle.SSS_SIMPLE_SINGLE_MULTI_SEL
						.toString())){
	
		tv_title.setText(sub_title);
		
		}else{
			if(question_title!=null){
				tv_title.setText(question_title);
			}
		}
		}
		tv_answer.setText(answer);
		System.out.println(answerdesc.getContent());
		tv_answerdesc.setText(answerdesc);
		tv_title.setBackgroundDrawable(getResources().getDrawable(
				android.R.drawable.gallery_thumb));
		return v;
	}

	public static ExamFragment newInstance(int num, Context context) {

		ExamFragment f = (ExamFragment) ExamFragment.instantiate(context,
				ExamFragment.class.getName());
		// ExamFragment f =new ExamFragment();
		//f.position = num;
		// Supply num input as an argument.
		Bundle args = new Bundle();
		args.putInt(NUM_TAG, num);
		f.setArguments(args);
		return f;
	}
}
