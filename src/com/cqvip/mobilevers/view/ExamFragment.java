package com.cqvip.mobilevers.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.xmlpull.v1.XmlPullParser;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.cqvip.mobilevers.R;
import com.cqvip.mobilevers.exam.Content;
import com.cqvip.mobilevers.exam.Question;
import com.cqvip.mobilevers.exam.Solution;
import com.cqvip.mobilevers.ui.ExamActivity;
import com.cqvip.mobilevers.utils.SubjectType;
import com.cqvip.mobilevers.widget.ImageTextCheckBox;
import com.cqvip.mobilevers.widget.ImageTextView;

public class ExamFragment extends Fragment implements  OnCheckedChangeListener{
	private static final String NUM_TAG = "num";
	private int position; //第几个fragment

//    private ArrayList<Subject> subjects_list;
//    public ArrayList<Integer> startLitmitCount_List;
    
	private ArrayList<Question> Question_list=new ArrayList<Question>(); // 所有question
//	private ArrayList<SubjectExam> subjectExam_list=new ArrayList<SubjectExam>(); // 所有subject
//	private ArrayList<Subject> subjects_list=new ArrayList<Subject>(); // 所有subject
//	private ArrayList<Integer> subjectExamCount_list=new ArrayList<Integer>(); // 所有subject
//	private ArrayList<Integer> startLitmitCount_List=new ArrayList<Integer>();//统计subject题目
    
    private TextView page_title,tv_back;
    private LinearLayout ll_main,ll_title;
    private Content contentTitle;
    private ImageTextView itvTitle;
    private EditText et_client_answer;
    private LinearLayout mulitiple_chose_group;
    private ArrayList<ImageTextCheckBox> check_list;
    private static final char[]  ALPHABET = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T'};
    private static final String[] TRUEFALSE = {"正确","错误"};
    private String type;
    private boolean isMultiCheck = false;
    private static Map<Integer, Boolean> isSelected;
    private int singleChoose;
    private String fillanswer;
    private ArrayList<Integer> multiChoose = new ArrayList<Integer>();
	
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
//		subjectExamCount_list = ((ExamActivity)getActivity()).getSubjectExamCount_list();
//		startLitmitCount_List =((ExamActivity)getActivity()).getStartLitmitCount_List();
//		subjectExam_list = ((ExamActivity)getActivity()).getSubjectExam_list();
//		subjects_list = ((ExamActivity)getActivity()).getSubjects_list();
		Question_list = ((ExamActivity)getActivity()).getQuestion_list();
		ll_main = (LinearLayout) v.findViewById(R.id.ll_main);
		ll_title = (LinearLayout) v.findViewById(R.id.ll_title);
		page_title = (TextView) v.findViewById(R.id.txt_viewtitle);
		page_title = (TextView) v.findViewById(R.id.txt_viewtitle);
		tv_back = (TextView) v.findViewById(R.id.txt_title_back);
		et_client_answer = (EditText) v.findViewById(R.id.et_answer);
		ll_main.setVisibility(View.VISIBLE);
		ll_title.setVisibility(View.GONE);
		page_title.setVisibility(View.GONE);
		et_client_answer.setVisibility(View.GONE);
    		
		itvTitle = 	(ImageTextView) v.findViewById(R.id.sub_title);
		ImageTextView tv_title = (ImageTextView) v.findViewById(R.id.txt_exam_title);
		ImageTextView tv_answer = (ImageTextView) v.findViewById(R.id.correct_answer);
		ImageTextView tv_answerdesc = (ImageTextView) v.findViewById(R.id.anser_desc);
		mulitiple_chose_group = (LinearLayout) v.findViewById(R.id.mulitiple_chose_group);
		//第几大题
		
		//第几子题
		Question question = Question_list.get(position);
		Content question_title = question.getTitle();
		Solution solution =  question.getSolution();
		Content answer = solution.getAnswer();
		Content answerdesc = solution.getAnswerDesc();
		type = question.getType();
		//判断
		//获取当前小题的subject和大题subjectExam;
		//SubjectExam  now_subjectExam = subjectExam_list.get(currentSubject);
		//判断试题类型
		Content sub_title = question.getSub_Title();
		String subExam_title = question.getSebexam_Title();
		String sub_type = question.getSub_Type();
		
		Log.i("sub_type",sub_type);
		Log.i("sub_Title","tttt:"+sub_title.getContent());
		
		int options = question.getItemCount();
		//判断小题的类型
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
		tv_title.setText((position+1)+"、",sub_title);
		ArrayList<Content> Alloption =question.getOption();
		//显示选项
		showAllKindsQuestion(sub_type, options, Alloption);
		
		}else{
//				//显示材料
				contentTitle = sub_title;//获取到子题材料内容
				page_title.setVisibility(View.VISIBLE);
				tv_title.setText((position+1)+"、",question_title);
				ShowAnyQuestionCollSubject(question);
				
		}
		
		page_title.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				ll_main.setVisibility(View.GONE);
				ll_title.setVisibility(View.VISIBLE);
				itvTitle.setText(contentTitle);
			}
		});
		tv_back.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				ll_main.setVisibility(View.VISIBLE);
				ll_title.setVisibility(View.GONE);
			}
		});
		
		
		tv_answer.setText(answer);
		System.out.println(answerdesc.getContent());
		tv_answerdesc.setText(answerdesc);
		tv_title.setBackgroundDrawable(getResources().getDrawable(
				android.R.drawable.gallery_thumb));
		return v;
	}
	/**
	 * 显示所有题目
	 * @param sub_type
	 * @param options
	 * @param Alloption
	 */
	private void showAllKindsQuestion(String sub_type, int options,
			ArrayList<Content> Alloption) {
		if(sub_type.equals( SubjectType.ShowStyle.SSS_SINGLE_SEL
				.toString())){
			//显示单选
			ShowSingleSelQuestion(Alloption);
		}else  if (sub_type.equals(SubjectType.ShowStyle.SSS_MULTI_SEL.toString())
                || sub_type.equals(SubjectType.ShowStyle.SSS_SINGLE_MULTI_SEL.toString())){
			//多选
			ShowMultiSelQuestion(Alloption);
		}else if (sub_type.equals(SubjectType.ShowStyle.SSS_FILL.toString())){
			//填空
			ShowFillQuestion();
			
		}else if (sub_type.equals(SubjectType.ShowStyle.SSS_JUDGEMENT.toString())){
			//判断
			ShowJudgementQuestion();
			
		}else if (sub_type.equals(SubjectType.ShowStyle.SSS_TEXT_QUSTION)){
			//问答
			ShowTextQuestion();
			
			
		}else if (sub_type.equals(SubjectType.ShowStyle.SSS_SIMPLE_SINGLE_SEL.toString())){
			//简单单选
			ShowSimpleSingleSelQuestion(options);
			
			
		}else if (sub_type.equals(SubjectType.ShowStyle.SSS_SIMPLE_MULTI_SEL.toString())||
				sub_type.equals(SubjectType.ShowStyle.SSS_SIMPLE_SINGLE_MULTI_SEL.toString())){
			//简单多选
			ShowSimpleMultiSelQuestion(options);
		}
	}
	/**
	 * 显示各种试题集
	 * @param question
	 */
	private void ShowAnyQuestionCollSubject(Question question) {
			String type_question = question.getType();
			int itemCount = question.getItemCount();
			ArrayList<Content> options = question.getOption();
			showAllKindsQuestion(type_question,itemCount,options);
	}
	/**
	 * 单选样式
	 * @param alloption
	 */
	private void ShowSingleSelQuestion(ArrayList<Content> alloption) {
		check_list = new ArrayList<ImageTextCheckBox>();
		isMultiCheck = false;
		for(int i=0;i<alloption.size();i++){
			ImageTextCheckBox ck = new ImageTextCheckBox(getActivity());
			//ck.setOnClickListener(this);
			ck.setOnCheckedChangeListener(this);
			ImageView img = new ImageView(getActivity());
			LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
			img.setLayoutParams(params);
			img.setImageResource(R.drawable.list_line);
			ck.setButtonDrawable(R.drawable.eg_radio_big);
			ck.setText(ALPHABET[i]+"、",alloption.get(i));
			mulitiple_chose_group.addView(ck);
			mulitiple_chose_group.addView(img);
			check_list.add(ck);
		}
		
	}
	/**
	 * 多选样式
	 * @param xmlParse
	 */
	private void ShowMultiSelQuestion(ArrayList<Content> alloption) {
		check_list = new ArrayList<ImageTextCheckBox>();
		isMultiCheck = true;
		for(int i=0;i<alloption.size();i++){
			ImageTextCheckBox ck = new ImageTextCheckBox(getActivity());
			//ck.setOnClickListener(this);
			ck.setOnCheckedChangeListener(this);
			ImageView img = new ImageView(getActivity());
			LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
			img.setLayoutParams(params);
			img.setImageResource(R.drawable.list_line);
			ck.setButtonDrawable(R.drawable.eg_checkbox);
			ck.setText(ALPHABET[i]+"、",alloption.get(i));
			mulitiple_chose_group.addView(ck);
			mulitiple_chose_group.addView(img);
			check_list.add(ck);
		}
		
	}
	 /**
		 * 解析简单单选
		 * @param xmlParse
		 */
		private void ShowSimpleSingleSelQuestion(int options) {
			check_list = new ArrayList<ImageTextCheckBox>();
			isMultiCheck = false;
			for(int i=0;i<options;i++){
				ImageTextCheckBox ck = new ImageTextCheckBox(getActivity());
				//ck.setOnClickListener(this);
				ck.setOnCheckedChangeListener(this);
				ImageView img = new ImageView(getActivity());
				LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
				img.setLayoutParams(params);
				img.setImageResource(R.drawable.list_line);
				ck.setButtonDrawable(R.drawable.eg_radio_big);
				ck.setText(ALPHABET[i]+"");
				mulitiple_chose_group.addView(ck);
				mulitiple_chose_group.addView(img);
				check_list.add(ck);
			}
			
			
			
		}
	
	/**
	 * 解析简单多选
	 * @param xmlParse
	 */
	 private void ShowSimpleMultiSelQuestion(int options) {
		 check_list = new ArrayList<ImageTextCheckBox>();
		 isMultiCheck = true;
		 for(int i=0;i<options;i++){
				ImageTextCheckBox ck = new ImageTextCheckBox(getActivity());
				//ck.setOnClickListener(this);
				ck.setOnCheckedChangeListener(this);
				ImageView img = new ImageView(getActivity());
				LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
				img.setLayoutParams(params);
				img.setImageResource(R.drawable.list_line);
				ck.setButtonDrawable(R.drawable.eg_checkbox);
				ck.setText(ALPHABET[i]);
				mulitiple_chose_group.addView(ck);
				mulitiple_chose_group.addView(img);
				check_list.add(ck);
			}
	}
	 
	 /**
		 * 解析判断
		 * @param题 xmlParse
		 */
	private void ShowJudgementQuestion() {
		check_list = new ArrayList<ImageTextCheckBox>();
			isMultiCheck = false;
			for(int i=0;i<2;i++){
				ImageTextCheckBox ck = new ImageTextCheckBox(getActivity());
				ck.setOnCheckedChangeListener(this);
				ImageView img = new ImageView(getActivity());
				LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
				img.setLayoutParams(params);
				img.setImageResource(R.drawable.list_line);
				ck.setButtonDrawable(R.drawable.eg_radio_big);
				ck.setText(TRUEFALSE[i]);
				mulitiple_chose_group.addView(ck);
				mulitiple_chose_group.addView(img);
				check_list.add(ck);
			}
			
			
		}
	
	/**
	 * 解析填空
	 * @param xmlParse
	 */
	private void ShowFillQuestion() {
		et_client_answer.setVisibility(View.VISIBLE);
		mulitiple_chose_group.setVisibility(View.GONE);
		
	}
	 /**
	  * 解析问答
	  * @param xmlParse
	  */
	private void ShowTextQuestion() {
		et_client_answer.setVisibility(View.VISIBLE);
		mulitiple_chose_group.setVisibility(View.GONE);
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
	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		
		System.out.println("isCheck"+isChecked);
		
		 //单选
		if(!isMultiCheck){
		if(isChecked){
			if(validateMultiChoosed()){
			check_list.get(singleChoose).toggle();
			}
			for(int i =0;i<check_list.size();i++){
				if(check_list.get(i).isChecked()){
					singleChoose = i;
				}
			}
		}	
		 }else{
			 //多选
			multiChoose.clear();
			 for(int i =0;i<check_list.size();i++){
					if(check_list.get(i).isChecked()){
						multiChoose.add(i);
						//System.out.println(multiChoose);
						//Log.i("multichoose",multiChoose+"");
					}
				}
		 }
	}
	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		//保存用户答案
		String clientanswer = et_client_answer.getText().toString();
		if(!TextUtils.isEmpty(clientanswer)){
			fillanswer = clientanswer;
		}else{
			fillanswer = null;
		}
		
	}
	
	/**
     * 判断是否有选中
     * @return
     */
	private boolean validateMultiChoosed() {
		int j = 0;
		for(int i =0;i<check_list.size();i++){
			if(check_list.get(i).isChecked()){
				j++;
			}
		}
		if(j>=2){
			return true;
		}
		return false;
	}
	
	
}
