package com.cqvip.mobilevers.view;

import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.Html;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.cqvip.mobilevers.R;
import com.cqvip.mobilevers.config.ConstantValues;
import com.cqvip.mobilevers.exam.Content;
import com.cqvip.mobilevers.exam.Question;
import com.cqvip.mobilevers.exam.SimpleAnswer;
import com.cqvip.mobilevers.exam.Solution;
import com.cqvip.mobilevers.ui.ExamActivity;
import com.cqvip.mobilevers.utils.SubjectType;
import com.cqvip.mobilevers.widget.ImageTextCheckBox;
import com.cqvip.mobilevers.widget.ImageTextView;

public class ExamFragment extends Fragment implements  OnCheckedChangeListener{
	private static final String NUM_TAG = "num";
	private static String TAG = "ExamFragment";
	private static final char[]  ALPHABET = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T'};
	private static final String[] TRUEFALSE = {"正确","错误"};
	
	private LinearLayout decision,decision2,decision3,decision4; //答案
    private TextView user_answer;
    private TextView tx_rightwrong;//是否答对
    private TextView tx_perscore;//分数
    private TextView page_title;//材料，
    private TextView subject_title;//题干，
    private LinearLayout ll_main;//试题主体
    private LinearLayout ll_title;//材料
    private LinearLayout mulitiple_chose_group;//选项
    private ImageTextView itvTitle; //标题
    private EditText et_client_answer;//输入框
   
    private int position; //第几个fragment
    private int clientSingleChoose;//用户选择
    private int rightOrWrong = ConstantValues.ANSWER_UNDONG;//状态，四种做，没做，对，错
    private double perScore;//每小题分数
    
    private String type;//question type;
    private String id;//question id;
    private String realAnswer;//参考答案
   
    private boolean isMultiCheck = false;//是否多选
    private boolean isTextType = false;//是否客观题
    private boolean isJudgeType = false;//是否是判断题
    
    private ArrayList<Question> Question_list=new ArrayList<Question>(); // 所有question
    private ArrayList<ImageTextCheckBox> check_list;//选项
    private ArrayList<Integer> multiChoose = new ArrayList<Integer>();
   
    
    private Content contentTitle;  //标题内容
    private ColAndRow colAndRow;//position坐标
	
	private LayoutInflater cur_inflater;
	
//    @Override
//    public void onAttach(Activity activity) {
//    	Log.i("ExamFragment", "================onAttach==============");
//    	super.onAttach(activity);
//    }
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//    	Log.i("ExamFragment", "=========onCreate========");
//    	super.onCreate(savedInstanceState);
//    }
//    
//    
//	@Override
//	public void onConfigurationChanged(Configuration newConfig) {
//		Log.i("ExamFragment", "=========onConfigurationChanged========");
//		super.onConfigurationChanged(newConfig);
//	}
//	@Override
//	public void onHiddenChanged(boolean hidden) {
//		Log.i("ExamFragment", "=========onHiddenChanged========");
//		super.onHiddenChanged(hidden);
//	}
//	@Override
//	public void onPause() {
//		Log.i("ExamFragment", "=========onPause()========");
//		super.onPause();
//	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		cur_inflater = inflater;
		View v = inflater.inflate(R.layout.fragment_exampager, container,false);
		position = getArguments().getInt(NUM_TAG);//fragment位置，及第几个Question
		Log.i("ExamFragment", "onCreateView"+position);
		colAndRow = getItemPosition(position);
		Log.i(TAG,"row,col"+colAndRow.row+","+colAndRow.col);
		Question_list = ((ExamActivity)getActivity()).getQuestion_list();
		ll_main = (LinearLayout) v.findViewById(R.id.ll_main);
		ll_title = (LinearLayout) v.findViewById(R.id.ll_title);
		page_title = (TextView) v.findViewById(R.id.txt_viewtitle);
		subject_title = (TextView) v.findViewById(R.id.txt_subject_title);
		et_client_answer = (EditText) v.findViewById(R.id.et_answer);
		user_answer = (TextView) v.findViewById(R.id.user_answer);
		tx_perscore = (TextView) v.findViewById(R.id.tx_perscore);//分值
		tx_rightwrong = (TextView) v.findViewById(R.id.tx_right_wrong);//对错
		decision = (LinearLayout) v.findViewById(R.id.decision);
		decision2 = (LinearLayout) v.findViewById(R.id.decision2);
		decision3 = (LinearLayout) v.findViewById(R.id.decision3);
		decision4 = (LinearLayout) v.findViewById(R.id.decision4);
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
		Log.i("EXAM","answer:"+answer+"\ntype"+type);
		id = question.getId();
		perScore = question.getPerscore();
		//Log.i("EXAM","id:"+id+"type:"+type);
		//判断
		//获取当前小题的subject和大题subjectExam;
		//SubjectExam  now_subjectExam = subjectExam_list.get(currentSubject);
		//判断试题类型
		Content sub_title = question.getSub_Title();
		String subExam_title = question.getSebexam_Title();
		String sub_type = question.getSub_Type();
		//perScore = question.get
		//答案，用于比较
		realAnswer = solution.getAnswer().getContent();
		//显示答题题干，判断是否有html
		Spanned mtitle = formSubTitle(subExam_title);
		subject_title.setText(mtitle);
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
				page_title.setText(" 查看材料>>");
				tv_title.setText((position+1)+"、",question_title);
				ShowAnyQuestionCollSubject(question);
				
		}
		
		
		page_title.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(ll_main.getVisibility()==View.VISIBLE){
				
				ll_main.setVisibility(View.GONE);
				ll_title.setVisibility(View.VISIBLE);
				page_title.setText("返回>>");
				itvTitle.setText(contentTitle);
				}else{
				page_title.setText("查看材料>>");
				ll_main.setVisibility(View.VISIBLE);
				ll_title.setVisibility(View.GONE);
				}
				
				
			}
		});
		
		tx_perscore.setText(perScore+"分");
		
		if(answer.isContainPic()){
			tv_answer.setText(answer);
		}else{
			if(isTextType){//填空，问答
			tv_answer.setText(answer);
			}else if(isJudgeType){//判断
				tv_answer.setText(formTrueFasle(answer));
			}else{
				//选择
			tv_answer.setText(formABC(answer));	
			}
		}
		tv_answerdesc.setText(answerdesc);
		tv_title.setBackgroundDrawable(getResources().getDrawable(
				android.R.drawable.gallery_thumb));
		if(ExamActivity.isShowAnswer){
			if(!isTextType){
			decision.setVisibility(View.VISIBLE);
			decision2.setVisibility(View.VISIBLE);
			decision3.setVisibility(View.VISIBLE);
			decision4.setVisibility(View.VISIBLE);
			}else{
			decision2.setVisibility(View.VISIBLE);
			decision3.setVisibility(View.VISIBLE);
			decision4.setVisibility(View.VISIBLE);
			}
		}else{
		decision.setVisibility(View.GONE);
		decision2.setVisibility(View.GONE);
		decision3.setVisibility(View.GONE);
		decision4.setVisibility(View.GONE);
		}
		return v;
	}
	
	
	private String formTrueFasle(Content answer) {
		String str = answer.getContent().trim();
		if(!str.equals("UNKNOWN")&&!str.equals("-1")){
			if(str.equals("TRUE")){
				return TRUEFALSE[0];
			}else{
				return TRUEFALSE[1];
			}
		}
		return "";
	}


	private Spanned formSubTitle(String text) {
		String htmlTag = ConstantValues.HTMLTAG;
	if(	text != null && text.startsWith(htmlTag)){
		String result = text.substring(htmlTag.length(),text.length());
		return Html.fromHtml(result);
	}else{
		return new SpannedString(text);
	}
	}


	/**
	 * 处理答案样式
	 * @param answer
	 * @return
	 */
	private String formABC(Content answer) {
		String str = answer.getContent().trim();
		StringBuilder builder = new StringBuilder();
		if(!str.equals("UNKNOWN")){
			Log.i(TAG,"====================="+str);
		String[] array = str.split(",");
		int mLength = array.length;
		if(mLength>0){
			for(int i=0;i<array.length;i++){
			int choice =Integer.parseInt(array[i].trim());
			if(choice>0){
			builder.append(ALPHABET[choice-1]);
				if(i!=mLength-1){
					builder.append(",");
				}
			}else{
				return "";
			}
			}
		}
		}else{
			return "";
		}
		return builder.toString();
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
			ImageTextCheckBox ck = (ImageTextCheckBox) cur_inflater.inflate(R.layout.mycheckbox, null);
			ImageView img = new ImageView(getActivity());
			commonStyle(i, R.drawable.eg_radio_big, ck, img);
			ck.setText(ALPHABET[i]+"、",alloption.get(i));
			mulitiple_chose_group.addView(ck);
			mulitiple_chose_group.addView(img);
			check_list.add(ck);
		}
		
		setPreSingChoice();
		
	}


	/**
	 * 五个方法的共同部分提取
	 * @param i
	 * @param drawableid
	 * @param ck
	 * @param img
	 */
	private void commonStyle(int i, int drawableid,ImageTextCheckBox ck,ImageView img) {
		ck.setOnCheckedChangeListener(this);
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
		img.setLayoutParams(params);
		img.setImageResource(R.drawable.list_line);
		img.setScaleType(ScaleType.FIT_XY);
		ck.setButtonDrawable(drawableid);
		if(ExamActivity.isnight){
		ck.setTextColor(getResources().getColor(R.color.examnightcolor));
		}
	}
	/**
	 * fragment回收后，设置用户已经答过的答案
	 */
	private void setPreSingChoice() {
		//判断是否已经答过
		if(ExamActivity.done_position[colAndRow.row][colAndRow.col]>0){
			//用户已经做过
			Log.i(TAG,"==========DONE=============");
			SimpleAnswer answlist = ExamActivity.clientAnswer.get(position);
			if(answlist!=null){
				//Log.i(TAG,"==========ANSWER============="+answlist.get(0));
				//设置上选择的答案
				String ans = answlist.getAnswer();
				if(!TextUtils.isEmpty(ans)){
				
				check_list.get(Integer.parseInt(ans)-1).setChecked(true);
				}
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
			ImageTextCheckBox ck = (ImageTextCheckBox) cur_inflater.inflate(R.layout.mycheckbox, null);
			ImageView img = new ImageView(getActivity());
			commonStyle(i, R.drawable.eg_checkbox, ck, img);
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
			SimpleAnswer answString = ExamActivity.clientAnswer.get(position);
			if(answString!=null){
				
				String ans = answString.getAnswer();
				//设置上选择的答案
				if(!TextUtils.isEmpty(ans)){
					String[] ary = ans.split(",");
					for(int i=0;i<ary.length;i++){
						check_list.get(Integer.parseInt(ary[i])-1).setChecked(true);
					}
					
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
				ImageTextCheckBox ck = (ImageTextCheckBox) cur_inflater.inflate(R.layout.mycheckbox, null);
				ImageView img = new ImageView(getActivity());
				commonStyle(i, R.drawable.eg_radio_big, ck, img);
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
				ImageTextCheckBox ck = (ImageTextCheckBox) cur_inflater.inflate(R.layout.mycheckbox, null);
				ImageView img = new ImageView(getActivity());
				commonStyle(i, R.drawable.eg_checkbox, ck, img);
				ck.setText(ALPHABET[i]+"");
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
		isJudgeType = true;
		check_list = new ArrayList<ImageTextCheckBox>();
			isMultiCheck = false;
			for(int i=0;i<2;i++){
				ImageTextCheckBox ck = (ImageTextCheckBox) cur_inflater.inflate(R.layout.mycheckbox, null);
				ImageView img = new ImageView(getActivity());
				commonStyle(i, R.drawable.eg_radio_big, ck, img);
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
					rightOrWrong = ConstantValues.ANSWER_DONG;
					setSigndone(ExamActivity.done_position);
					SimpleAnswer ans = new SimpleAnswer(id, clientanswer,0);
					ExamActivity.clientAnswer.append(position, ans);
				}else{
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
			SimpleAnswer answString = ExamActivity.clientAnswer.get(position);
			if(answString!=null){
				//设置上选择的答案
				String ans = answString.getAnswer();
				if(!TextUtils.isEmpty(ans)){
				et_client_answer.setText(ans);
				}
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
					rightOrWrong = ConstantValues.ANSWER_DONG;
					setSigndone(ExamActivity.done_position);
					SimpleAnswer ans = new SimpleAnswer(id, clientanswer,0);
					ExamActivity.clientAnswer.append(position, ans);
				}else{
					rightOrWrong = ConstantValues.ANSWER_UNDONG;
					removeSignDone(ExamActivity.done_position);
					ExamActivity.clientAnswer.append(position, null);
				}
            }  
        }); 
	}
	
	public void viewAnswer(){
//		decision.setVisibility(View.VISIBLE);
//		decision2.setVisibility(View.VISIBLE);
//		decision3.setVisibility(View.VISIBLE);
		if(decision.getVisibility()==View.GONE){
			if(!isTextType){
			decision.setVisibility(View.VISIBLE);
			}
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
		if(decision4.getVisibility()==View.GONE){
			decision4.setVisibility(View.VISIBLE);
		}else{
			decision4.setVisibility(View.GONE);
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
			if(isJudgeType){
				user_answer.setText(TRUEFALSE[clientSingleChoose]+"");
			}else{
			user_answer.setText(ALPHABET[clientSingleChoose]+"");
			}
			double tmpscore ;
			//显示对错
			if(validateAnswer(clientSingleChoose)){
			doAnswerRight();
			 tmpscore = perScore;
			}else{
				doAnswerWrong();
			 tmpscore = 0;
			}
			SimpleAnswer ans = new SimpleAnswer(id, (clientSingleChoose+1)+"",tmpscore);
			setSigndone(ExamActivity.done_position);//记录已经做过
			ExamActivity.clientAnswer.append(position, ans);
			Log.i(TAG, "=======674======="+position);
		}else{
			user_answer.setText("");
			tx_rightwrong.setText("");
			rightOrWrong = ConstantValues.ANSWER_UNDONG;
			removeSignDone(ExamActivity.done_position);
			ExamActivity.clientAnswer.append(position, null);
			Log.i(TAG, "=======681======="+position);
		}	
		 }else{
			 //多选
			multiChoose.clear();
			 for(int i =0;i<check_list.size();i++){
					if(check_list.get(i).isChecked()){
						multiChoose.add(i);
					}
				}
			 if(!multiChoose.isEmpty()){
			 user_answer.setText(formString(multiChoose));
			 //ArrayList<SimpleAnswer> array = new ArrayList<SimpleAnswer>();
			 double score ;
			 if(validateMultiAnswer(multiChoose)){
					doAnswerRight();
					score = perScore;
				}else{
					doAnswerWrong();
					score = 0;
					}
			    setSigndone(ExamActivity.done_position);
			    String answers = formMultAnswer(multiChoose);
			    SimpleAnswer ans = new SimpleAnswer(id, answers,score);                  
			    
			    
				ExamActivity.clientAnswer.append(position, ans);
			 }else{
				 removeSignDone(ExamActivity.done_position);
				 ExamActivity.clientAnswer.append(position, null);
				 rightOrWrong = ConstantValues.ANSWER_UNDONG;
				 user_answer.setText("");
				 tx_rightwrong.setText("");
			 }
		 }
	}


	private String formMultAnswer(ArrayList<Integer> multiChoose2) {
		StringBuilder builder = new StringBuilder();
		for(int i=0;i<multiChoose2.size();i++){
			
			builder.append((multiChoose2.get(i)+1)+"");
			if(i!=multiChoose2.size()-1){
				builder.append(",");
			}
		}
		return builder.toString();
	}


	private void doAnswerWrong() {
		rightOrWrong = ConstantValues.ANSWER_WRONG;
		tx_rightwrong.setTextColor(getResources().getColor(R.color.red));	
		tx_rightwrong.setText(getResources().getString(R.string.tips_wrong));
		setSignWrongdone(ExamActivity.right_position,ExamActivity.wrong_position);
	}


	private void doAnswerRight() {
		tx_rightwrong.setTextColor(getResources().getColor(R.color.gree_deep));	
		tx_rightwrong.setText(getResources().getString(R.string.tips_right));
		rightOrWrong = ConstantValues.ANSWER_RIGHT;
		setSignRightdone(ExamActivity.right_position,ExamActivity.wrong_position);
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
		if(realAnswer!=null){
		StringBuilder builder = new StringBuilder();
		for(int i=0;i<multiChoose2.size();i++){
			int k = multiChoose2.get(i);
			builder.append((k+1)+"");
			if(i!=multiChoose2.size()){
				builder.append(",");
			}
		}
		String result = builder.toString();
		return realAnswer.equals(result);
		}else{
			return false;
		}
	}
	private boolean validateAnswer(int clientSingleChoose2) {
		if(realAnswer!=null){
			if(isJudgeType){
				return clientSingleChoose2==0;
			}
		   return realAnswer.equals((clientSingleChoose2+1)+"");
		}else{
			return false;
		}
	}
	/**
	 * 多选答案
	 * @param multiChoose2
	 * @return
	 */
	private String formString(ArrayList<Integer> multiChoose2) {
		StringBuilder builder = new StringBuilder();
		for(int i=0;i<multiChoose2.size();i++){
			int k = multiChoose2.get(i);
			builder.append(ALPHABET[k]+"");
			if(i!=multiChoose2.size()-1){
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


	public void showSubjectTitle() {
		subject_title.setVisibility(View.VISIBLE);
	}
	public void hideSubjectTitle() {
		subject_title.setVisibility(View.GONE);
	}
	
	
}
