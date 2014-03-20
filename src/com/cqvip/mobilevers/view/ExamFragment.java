package com.cqvip.mobilevers.view;

import java.util.ArrayList;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
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
import com.cqvip.mobilevers.config.ConstantValues;
import com.cqvip.mobilevers.exam.Content;
import com.cqvip.mobilevers.exam.Question;
import com.cqvip.mobilevers.exam.Solution;
import com.cqvip.mobilevers.ui.ExamActivity;
import com.cqvip.mobilevers.utils.SubjectType;
import com.cqvip.mobilevers.widget.ImageTextCheckBox;
import com.cqvip.mobilevers.widget.ImageTextView;

public class ExamFragment extends Fragment implements  OnCheckedChangeListener{
	private static final String NUM_TAG = "num";
	private static String TAG = "ExamFragment";
	private int position; //第几个fragment

//    private ArrayList<Subject> subjects_list;
//    public ArrayList<Integer> startLitmitCount_List;
    
	private ArrayList<Question> Question_list=new ArrayList<Question>(); // 所有question
//	private ArrayList<SubjectExam> subjectExam_list=new ArrayList<SubjectExam>(); // 所有subject
//	private ArrayList<Subject> subjects_list=new ArrayList<Subject>(); // 所有subject
//	private ArrayList<Integer> subjectExamCount_list=new ArrayList<Integer>(); // 所有subject
//	private ArrayList<Integer> startLitmitCount_List=new ArrayList<Integer>();//统计subject题目
    private LinearLayout decision,decision2,decision3;
    private TextView user_answer;
    private TextView tx_cue;//是否答对
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
    private String fillanswer;
    private int clientSingleChoose;
    private ArrayList<Integer> multiChoose = new ArrayList<Integer>();
    private String realAnswer;
    
    private int rightOrWrong = ConstantValues.ANSWER_UNDONG;//没做
    
    private boolean isTextType = false;
    private ColAndRow colAndRow;
	
    @Override
    public void onAttach(Activity activity) {
    	Log.i("ExamFragment", "================onAttach==============");
    	super.onAttach(activity);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	Log.i("ExamFragment", "=========onCreate========");
    	super.onCreate(savedInstanceState);
    }
    
    
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		Log.i("ExamFragment", "=========onConfigurationChanged========");
		super.onConfigurationChanged(newConfig);
	}
	@Override
	public void onHiddenChanged(boolean hidden) {
		Log.i("ExamFragment", "=========onHiddenChanged========");
		super.onHiddenChanged(hidden);
	}
	@Override
	public void onPause() {
		Log.i("ExamFragment", "=========onPause()========");
		super.onPause();
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_exampager, container,false);
		position = getArguments().getInt(NUM_TAG);//fragment位置，及第几个Question
		Log.i("ExamFragment", "onCreateView"+position);
		colAndRow = getItemPosition(position);
		Log.i(TAG,"row,col"+colAndRow.row+","+colAndRow.col);
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
		user_answer = (TextView) v.findViewById(R.id.user_answer);
		tx_cue = (TextView) v.findViewById(R.id.tx_cue);//对错
		decision = (LinearLayout) v.findViewById(R.id.decision);
		decision2 = (LinearLayout) v.findViewById(R.id.decision2);
		decision3 = (LinearLayout) v.findViewById(R.id.decision3);
		decision.setVisibility(View.GONE);
		decision2.setVisibility(View.GONE);
		decision3.setVisibility(View.GONE);
		
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
		
		//答案，用于比较
		realAnswer = solution.getAnswer().getContent();
		
//		Log.i("sub_type",sub_type);
//		Log.i("Question_type",type);
//		Log.i("sub_Title","tttt:"+sub_title.getContent());
		
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
			
		}else if (sub_type.equals(SubjectType.ShowStyle.SSS_TEXT_QUSTION.toString())){
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
		
		setPreSingChoice();
		
	}
	/**
	 * fragment回收后，设置用户已经答过的答案
	 */
	private void setPreSingChoice() {
		//判断是否已经答过
		if(ExamActivity.done_position[colAndRow.row][colAndRow.col]>0){
			//用户已经做过
			Log.i(TAG,"==========DONE=============");
			ArrayList<String> answlist = ExamActivity.clientAnswer.get(position);
			if(answlist!=null&&!answlist.isEmpty()){
				Log.i(TAG,"==========ANSWER============="+answlist.get(0));
				//设置上选择的答案
				check_list.get(Integer.parseInt(answlist.get(0))).setChecked(true);
			}
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
		
		//判断是否已经答过
				setPreMultiChoice();
		
	}
	/**
	 * fragment回收后，设置用户已经答过的答案
	 */
	private void setPreMultiChoice() {
		if(ExamActivity.done_position[colAndRow.row][colAndRow.col]>0){
			//用户已经做过
			ArrayList<String> answString = ExamActivity.clientAnswer.get(position);
			if(answString!=null&&!answString.isEmpty()){
				//设置上选择的答案
				for(int i=0;i<answString.size();i++){
				check_list.get(Integer.parseInt(answString.get(i))).setChecked(true);
				}
			}
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
			
			setPreSingChoice();
			
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
		 setPreMultiChoice();
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
			
			setPreSingChoice();
		}
	
	/**
	 * 解析填空
	 * @param xmlParse
	 */
	private void ShowFillQuestion() {
		isTextType = true;
		et_client_answer.setVisibility(View.VISIBLE);
		mulitiple_chose_group.setVisibility(View.GONE);
		setPreTextQuestion();
//		et_client_answer.setOnKeyListener(new EditText.OnKeyListener(){
//			@Override
//			public boolean onKey(View v, int keyCode, KeyEvent event) {
//				String clientanswer = et_client_answer.getText().toString();
//				Log.i(TAG,"answer:"+clientanswer);
//				Log.i(TAG,"answer:"+clientanswer);
//				if(!TextUtils.isEmpty(clientanswer)){
//					fillanswer = clientanswer;
//					rightOrWrong = ConstantValues.ANSWER_DONG;
//					setSigndone(ExamActivity.done_position);
//					ArrayList<String> array = new ArrayList<String>();
//					array.add(clientanswer);
//					ExamActivity.clientAnswer.append(position, array);
//				}else{
//					fillanswer = null;
//					rightOrWrong = ConstantValues.ANSWER_UNDONG;
//					removeSignDone(ExamActivity.done_position);
//					ExamActivity.clientAnswer.append(position, null);
//				}
//				return false;
//			}});
		et_client_answer.addTextChangedListener(new TextWatcher() {           
            @Override  
            public void onTextChanged(CharSequence s, int start, int before, int count) {  
            }  
              
            @Override  
            public void beforeTextChanged(CharSequence s, int start, int count,  
                    int after) {                  
            }  
              
            @Override  
            public void afterTextChanged(Editable s) {   
            	String clientanswer = et_client_answer.getText().toString();
				if(!TextUtils.isEmpty(clientanswer)){
					fillanswer = clientanswer;
					rightOrWrong = ConstantValues.ANSWER_DONG;
					setSigndone(ExamActivity.done_position);
					ArrayList<String> array = new ArrayList<String>();
					array.add(clientanswer);
					ExamActivity.clientAnswer.append(position, array);
				}else{
					fillanswer = null;
					rightOrWrong = ConstantValues.ANSWER_UNDONG;
					removeSignDone(ExamActivity.done_position);
					ExamActivity.clientAnswer.append(position, null);
				}
            }  
        });  
		
		
	}
	private void setPreTextQuestion() {
		if(ExamActivity.done_position[colAndRow.row][colAndRow.col]>0){
			//用户已经做过
			ArrayList<String> answString = ExamActivity.clientAnswer.get(position);
			if(answString!=null&&!answString.isEmpty()){
				//设置上选择的答案
				et_client_answer.setText(answString.get(0));
			}
		}
	}
	 /**
	  * 解析问答
	  * @param xmlParse
	  */
	private void ShowTextQuestion() {
		isTextType = true;
		et_client_answer.setVisibility(View.VISIBLE);
		mulitiple_chose_group.setVisibility(View.GONE);
		setPreTextQuestion();
		et_client_answer.addTextChangedListener(new TextWatcher() {           
            @Override  
            public void onTextChanged(CharSequence s, int start, int before, int count) {  
            }  
              
            @Override  
            public void beforeTextChanged(CharSequence s, int start, int count,  
                    int after) {                  
            }  
              
            @Override  
            public void afterTextChanged(Editable s) {   
            	String clientanswer = et_client_answer.getText().toString();
				if(!TextUtils.isEmpty(clientanswer)){
					fillanswer = clientanswer;
					rightOrWrong = ConstantValues.ANSWER_DONG;
					setSigndone(ExamActivity.done_position);
					ArrayList<String> array = new ArrayList<String>();
					array.add(clientanswer);
					ExamActivity.clientAnswer.append(position, array);
				}else{
					fillanswer = null;
					rightOrWrong = ConstantValues.ANSWER_UNDONG;
					removeSignDone(ExamActivity.done_position);
					ExamActivity.clientAnswer.append(position, null);
				}
            }  
        }); 
	}
	
	public void viewAnswer(){
		if(decision.getVisibility()==View.GONE){
			decision.setVisibility(View.VISIBLE);
		}else{
			decision.setVisibility(View.GONE);
		}
		if(decision2.getVisibility()==View.GONE){
			decision2.setVisibility(View.VISIBLE);
		}else{
			decision2.setVisibility(View.GONE);
		}
		if(decision3.getVisibility()==View.GONE){
			decision3.setVisibility(View.VISIBLE);
		}else{
			decision3.setVisibility(View.GONE);
		}
	}
	
	/**
	 * 返回试题
	 * @param num
	 * @param context
	 * @return
	 */
	public static ExamFragment newInstance(int num, Context context) {
		ExamFragment f = (ExamFragment) ExamFragment.instantiate(context,
				ExamFragment.class.getName());
		Bundle args = new Bundle();
		args.putInt(NUM_TAG, num);
		args.putString("TAG",num+"");
		f.setArguments(args);
		return f;
	}
	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		 //单选
		if(!isMultiCheck){
		if(isChecked){
			if(validateMultiChoosed()){
			check_list.get(clientSingleChoose).toggle();
			}
			for(int i =0;i<check_list.size();i++){
				if(check_list.get(i).isChecked()){
					clientSingleChoose = i;
				}
			}
			
			//显示答案
			user_answer.setText(ALPHABET[clientSingleChoose]+"");
			//显示对错
			if(validateAnswer(clientSingleChoose)){
			tx_cue.setText("恭喜您，答对了");
			rightOrWrong = ConstantValues.ANSWER_RIGHT;
			setSignRightdone(ExamActivity.right_position,ExamActivity.wrong_position);
			}else{
				setSignWrongdone(ExamActivity.right_position,ExamActivity.wrong_position);
				rightOrWrong = ConstantValues.ANSWER_WRONG;
				
				tx_cue.setText("对不起，答错了");
			}
			setSigndone(ExamActivity.done_position);//记录已经做过
			ArrayList<String> array = new ArrayList<String>();
			array.add(clientSingleChoose+"");
			//ExamActivity.clientAnswer.append(position, array);
			//ExamActivity.clientAnswer.setValueAt(position, array);
			ExamActivity.clientAnswer.append(position, array);
			
		}else{
			user_answer.setText("");
			tx_cue.setText("");
			rightOrWrong = ConstantValues.ANSWER_UNDONG;
			removeSignDone(ExamActivity.done_position);
			Log.i(TAG,"=====remove=============="+position);
			ExamActivity.clientAnswer.append(position, null);
			//signDone();
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
			 if(!multiChoose.isEmpty()){
			 user_answer.setText(formString(multiChoose));
			 if(validateMultiAnswer(multiChoose)){
					tx_cue.setText("恭喜您，答对了");
					rightOrWrong = ConstantValues.ANSWER_RIGHT;
					setSignRightdone(ExamActivity.right_position,ExamActivity.wrong_position);
				}else{
					setSignWrongdone(ExamActivity.right_position,ExamActivity.wrong_position);
					rightOrWrong = ConstantValues.ANSWER_WRONG;
						tx_cue.setText("对不起，答错了");
					}
			    setSigndone(ExamActivity.done_position);
			    ArrayList<String> array = new ArrayList<String>();
			    for(int i=0;i<multiChoose.size();i++){
				array.add(multiChoose.get(i).toString());
			    }
				ExamActivity.clientAnswer.append(position, array);
			 }else{
				 removeSignDone(ExamActivity.done_position);
				 Log.i(TAG,"=====remove=============="+position);
				 ExamActivity.clientAnswer.append(position, null);
				 rightOrWrong = ConstantValues.ANSWER_UNDONG;
				 user_answer.setText("");
				 tx_cue.setText("");
			 }
		 }
	}
	/**
	 * 移除答题
	 */
	private void removeSignDone(int[][] array) {
		array[colAndRow.row][colAndRow.col] = 0;
	}
	/**
	 * 记录已经答过
	 * @param array
	 */
	private void setSigndone(int[][] array) {
		array[colAndRow.row][colAndRow.col] = position+1;
	}
	/**
	 * 记录正确答案
	 * @param rightArray
	 * @param wrongArray
	 */
	private void setSignRightdone(int[][] rightArray,int[][] wrongArray) {
		removeSignDone(wrongArray);
		rightArray[colAndRow.row][colAndRow.col] = position+1;
	}
	/**
	 * 记录错误答案
	 * @param rightArray
	 * @param wrongArray
	 */
	private void setSignWrongdone(int[][] rightArray,int[][] wrongArray) {
		removeSignDone(rightArray);
		wrongArray[colAndRow.row][colAndRow.col] = position+1;
	}
	
	private boolean validateMultiAnswer(ArrayList<Integer> multiChoose2) {
		StringBuilder builder = new StringBuilder();
		for(int i=0;i<multiChoose2.size();i++){
			int k = multiChoose2.get(i);
			builder.append((k+1)+"");
			if(i!=multiChoose2.size()){
				builder.append(",");
			}
		}
		String result = builder.toString();
		Log.i(TAG,"result:"+result);
		return realAnswer.equals(result);
	}
	private boolean validateAnswer(int clientSingleChoose2) {
		if(realAnswer!=null){
			int answer = Integer.parseInt(realAnswer);
			if((clientSingleChoose2+1) == answer){
				return true;
			}
		}
		return false;
	}
	/**
	 * A,B,c
	 * @param multiChoose2
	 * @return
	 */
	private String formString(ArrayList<Integer> multiChoose2) {
		StringBuilder builder = new StringBuilder();
		for(int i=0;i<multiChoose2.size();i++){
			int k = multiChoose2.get(i);
			builder.append(ALPHABET[k]+"");
			if(i!=multiChoose2.size()){
				builder.append(",");
			}
		}
		return builder.toString();
	}
	@Override
	public void onStop() {
		super.onStop();
//		//保存用户答案
//		String clientanswer = et_client_answer.getText().toString();
//		if(TextUtils.isEmpty(clientanswer)){
//			if(isTextType){
//				rightOrWrong = ConstantValues.ANSWER_UNDONG;
//				removeSignDone(ExamActivity.done_position);
//				ExamActivity.clientAnswer.append(position, null);
//			}
//			fillanswer = null;
//		}
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
	
	
	private class ColAndRow{
		int row;//行
		int col;//列
		public ColAndRow(int row,int col){
			this.col = col;
			this.row = row;
		}
	}
	/**
	 * 根据position获取下标
	 * @param position
	 * @return
	 */
	public ColAndRow getItemPosition(int position){
		ColAndRow cr = null;
		int[][] tmpArray = ((ExamActivity)getActivity()).getAll_position();
		for(int i=0;i<tmpArray.length;i++){
			for(int j=0;j<tmpArray[i].length;j++){
				if(tmpArray[i][j]==(position+1)){
					cr = new ColAndRow(i, j);
				}
			}
		}
		return cr;
	}
	
	
}
