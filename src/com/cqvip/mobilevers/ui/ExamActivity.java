package com.cqvip.mobilevers.ui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.util.SparseArray;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cqvip.mobilevers.R;
import com.cqvip.mobilevers.entity.TwoDimensionArray;
import com.cqvip.mobilevers.exam.BaseExamInfo;
import com.cqvip.mobilevers.exam.Exam;
import com.cqvip.mobilevers.exam.Question;
import com.cqvip.mobilevers.exam.SeriSqareArray;
import com.cqvip.mobilevers.exam.SimpleAnswer;
import com.cqvip.mobilevers.exam.Subject;
import com.cqvip.mobilevers.exam.SubjectExam;
import com.cqvip.mobilevers.ui.base.BaseFragmentActivity;
import com.cqvip.mobilevers.utils.DateUtil;
import com.cqvip.mobilevers.view.ExamFragment;
import com.cqvip.mobilevers.view.FragmentAnswerScard;

public class ExamActivity extends BaseFragmentActivity implements
		OnPageChangeListener, OnClickListener{

	final static String TAG = "ExamActivity";
	//static final int NUM_ITEMS = 10;
	MyAdapter mAdapter;
	private Context context;
	ViewPager mPager;
	int currentpage = 0;
	public static boolean isnight = false;
	private TextView tv_item_count;
	
	private Timer timer = null;
	private TimerTask task = null;
	private Handler handler = null;
	private Message msg = null;
	private int secondTotal;
	private TextView time_tv;
	private TextView tips_viewSubTitle;
	private ImageView tv_back;

	//private String examPaperId;
	//private Map<String, String> gparams;
	public Exam exam;
	
	private  int clientShowCount = 0;//总题数
	private int subjectExamCount;//大题型种类数量
	private int paperScore;
	private String paperId;
	private String paperName;
	private int paperTime;
	private BaseExamInfo baseExamInfo;
	public static  int clientScore = 0;//用户得分
	public static boolean isShowAnswer = false;
	public ArrayList<Subject> subjects_list=new ArrayList<Subject>(); // 所有subject
	public ArrayList<Question> Question_list=new ArrayList<Question>(); // 所有question
	
	private int[][] all_position;
	public static int[][] done_position;//统计subject题目
	public static int[][] right_position;//统计正确题目
	public static int[][] wrong_position;//统计错误题目
	private boolean isHandleOver = false;
	private boolean isRightWrong = false;
	private boolean isOnshowing = false;
	
	public ArrayList<Integer> cardCount_List=new ArrayList<Integer>();//答题卡题目
	
	
	public static SeriSqareArray<SimpleAnswer> clientAnswer;
	private TwoDimensionArray dimension;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//模式设置
		if (isnight) {
			this.setTheme(R.style.ThemeNight);
		} else {
			this.setTheme(R.style.ThemeDefault);
		}
		setContentView(R.layout.activity_exam);
		context = this;
		if (savedInstanceState != null) {
			secondTotal = savedInstanceState.getInt("secondTotal");
		}
		Bundle intent = getIntent().getBundleExtra("bundle");
		exam = (Exam) intent.getSerializable("exam");
		dimension = (TwoDimensionArray) intent.getSerializable("dimen");
		//clientAnswer =  intent.getSparseParcelableArray("answer");
		mAdapter = new MyAdapter(getSupportFragmentManager(), context);
//		examPaperId=getIntent().getStringExtra(ConstantValues.EXAMPAPERID);
//		Log.i(TAG, examPaperId);
		initView();
		init();
		startCountTime();
		//大题

//		//
		SubjectExam[] subjectExams_array=exam.getExam2lists();
		for (SubjectExam subjectExam : subjectExams_array) {
			//客户端显示题目数量
			int count = subjectExam.getQuestionNum();
			cardCount_List.add(count);//答题卡
			Subject[] subjects=subjectExam.getExam3List();//当_questionNum为0时，判断
			if(subjects!=null){
			subjects_list.addAll(Arrays.asList(subjects));
			}
		}
		
		for (Subject subject : subjects_list) {
			if(subject!=null){
			ArrayList<Question> lists = subject.getQuestion();
			//所有客户端显示question数量
			Question_list.addAll(lists);
			}
		}
		clientShowCount  = Question_list.size();
		
		if(dimension!=null){
			all_position = dimension.getAllss();
			done_position = dimension.getDoness();
			right_position = dimension.getRightss();
			wrong_position = dimension.getWrongss();
			clientAnswer = dimension.getClientAnswers();
			
			Log.i(TAG,"don"+Arrays.toString(all_position));
			Log.i(TAG,"right"+Arrays.toString(right_position));
			Log.i(TAG,Arrays.toString(wrong_position));
			for(int i=0;i<clientAnswer.size();i++){
			Log.i(TAG,"answer:"+clientAnswer.get(i));
			System.out.println(clientAnswer.get(i));
			}
		}else{
		all_position = DateUtil.initDoubleDimensionalData(cardCount_List);
	
		int mCount = subjectExams_array.length;
		done_position = new int[mCount][];
		right_position = new int[mCount][];
		wrong_position = new int[mCount][];
		for(int i=0;i<mCount;i++ ){
			done_position[i] = new int[subjectExams_array[i].getQuestionNum()];
			right_position[i] = new int[subjectExams_array[i].getQuestionNum()];
			wrong_position[i] = new int[subjectExams_array[i].getQuestionNum()];
			clientAnswer = new SeriSqareArray<SimpleAnswer>();
		}
		}
		
		
		mPager.setAdapter(mAdapter);
		
		
		paperId = intent.getString("id");
		paperName = exam.get_examPaperName();
		paperScore = exam.getScore();
		paperTime = exam.getExamTime();
		baseExamInfo = new BaseExamInfo(paperId,paperTime, paperName, paperScore,clientShowCount);
		
	}
	

	public int[][] getAll_position() {
		return all_position;
	}

	public void setAll_position(int[][] all_position) {
		this.all_position = all_position;
	}

	public ArrayList<Integer> getCardCount_List() {
		return cardCount_List;
	}

	public BaseExamInfo getBaseExamInfo() {
		return baseExamInfo;
	}

	public ArrayList<Subject> getSubjects_list() {
		return subjects_list;
	}

	public int getSubjectExamCount() {
		return subjectExamCount;
	}

	public ArrayList<Question> getQuestion_list() {
		return Question_list;
	}




	// 计时
	private void startCountTime() {
		task = new TimerTask() {
			@Override
			public void run() {
				if (null == msg) {
					msg = new Message();
				} else {
					msg = Message.obtain();
				}
				msg.what = 1;
				handler.sendMessage(msg);
			}
		};
		timer = new Timer(true);
		timer.schedule(task, 1000, 1000);
	}

	private void init() {
		handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case 1:
					secondTotal++;
					int second = secondTotal % 60;
					int minTotal = (secondTotal / 60);
					int hour = 0,
					minute = 0;
					minute = minTotal;
					if (minTotal >= 60) {
						hour = minTotal / 60;
						minute = minute % 60;
					}
					time_tv.setText(String.format("%1$02d:%2$02d:%3$02d", hour,
							minute, second));
					break;
				default:
					break;
				}
				super.handleMessage(msg);
			}
		};
	}

	private void initView() {
		tips_viewSubTitle = (TextView) findViewById(R.id.tv_show_subtitle);
		tips_viewSubTitle.setText(getResources().getString(R.string.btn_show_subtitle));
	//	lookanswer_ll.setVisibility(View.GONE);
		tv_back = (ImageView) findViewById(R.id.img_back);
		tv_back.setOnClickListener(this);
		tips_viewSubTitle.setOnClickListener(this);
		mPager = (ViewPager) findViewById(R.id.pager);
		mPager.setOnPageChangeListener(this);
		//mPager.setAdapter(mAdapter);
		// mPager.setOffscreenPageLimit(5);
		TextView answercard = (TextView) findViewById(R.id.tv_show_card);
		answercard.setOnClickListener(this);
		TextView showAnswer = (TextView) findViewById(R.id.tv_show_anwer);
		showAnswer.setOnClickListener(this);
		TextView handpaper = (TextView) findViewById(R.id.tv_exam_handle);
		handpaper.setOnClickListener(this);

		time_tv = (TextView) findViewById(R.id.time_tv);
		tv_item_count = (TextView) findViewById(R.id.tv_item_count);
		
		ImageView button_back = (ImageView) findViewById(R.id.goto_back);
		button_back.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (currentpage > 0)
					mPager.setCurrentItem(--currentpage);
			}
		});
		ImageView button_enter = (ImageView) findViewById(R.id.goto_next);
		button_enter.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (currentpage < clientShowCount - 1)
					mPager.setCurrentItem(++currentpage);
			}
		});
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		outState.putInt("secondTotal", secondTotal);
		super.onSaveInstanceState(outState);
	}

	public  class MyAdapter extends FragmentStatePagerAdapter {
		private Context context;

		// public MyAdapter(FragmentManager fm) {
		// super(fm);
		// }
		//private Map<Integer ,ExamFragment> mPageReferenceMap;
		private SparseArray<ExamFragment> mPageReferenceMap = new SparseArray<ExamFragment>();
		
		public MyAdapter(FragmentManager fm, Context context) {
			super(fm);
			this.context = context;
		//	mPageReferenceMap = new HashMap<Integer, ExamFragment>();
		}

		@Override
		public int getCount() {
			return clientShowCount;
		}

		@Override
		public Fragment getItem(int position) {
			//Log.i(TAG, "MyAdapter_getItem:" + position);
			ExamFragment myFragment = ExamFragment.newInstance(position,context);
			    mPageReferenceMap.put(position, myFragment);
			return myFragment;
		}

		
		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			ExamFragment fragment = (ExamFragment) super.instantiateItem(container,
			            position);
			    mPageReferenceMap.put(position, fragment);
			    return fragment;
		}

		public ExamFragment getFragment(int key) {
		    return mPageReferenceMap.get(key);
		}
		
		@Override
		public int getItemPosition(Object object) {
			return POSITION_NONE;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			super.destroyItem(container, position, object);
			 mPageReferenceMap.remove(position);
		}
		
	}

	@Override
	protected void onResume() {
		Log.i(TAG, "onResume");
		super.onResume();
	}

	@Override
	protected void onPause() {
		Log.i(TAG, "onPause");
		super.onPause();
	}

	@Override
	protected void onStop() {
		Log.i(TAG, "onStop");
		super.onStop();
	}

	@Override
	public void onPageScrolled(int position, float positionOffset,
			int positionOffsetPixels) {

	}

	@Override
	public void onPageSelected(int position) {
		Log.i(TAG, "onPageSelected_position:" + position);
		currentpage = position;
		if (position == 0) {
			isLeftFragment = true;
		} else {
			isLeftFragment = false;
		}
		isOnshowing = false;
		tips_viewSubTitle.setText(getResources().getString(R.string.btn_show_subtitle));
		tv_item_count.setText((position+1)+"|"+clientShowCount);
	}
	

	@Override
	public void onPageScrollStateChanged(int state) {

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.img_back://返回
			//是否退出考试
			if(!isShowAnswer){
		Dialog dialog =	new AlertDialog.Builder(this)
            .setTitle("退出考试")
            .setMessage("你还未交卷，确定要退出考试")
            .setPositiveButton(R.string.alert_dialog_confirm, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    finish();
                }
            })
            .setNegativeButton(R.string.alert_dialog_cancel, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                   dialog.dismiss();
                }
            })
            .create();
			dialog.show();
			}
			break;
		case R.id.tv_show_card://查看答题卡
			isRightWrong = isShowAnswer;
			isHandleOver = false;
			showAnswerCard(isHandleOver,isRightWrong);
			break;
		case R.id.tv_show_subtitle:
			ExamFragment fragment = mAdapter.getFragment(currentpage);
			if(isOnshowing){
				tips_viewSubTitle.setText(getResources().getString(R.string.btn_show_subtitle));
				fragment.hideSubjectTitle();
				isOnshowing = false;
			}else{
				fragment.showSubjectTitle();
				tips_viewSubTitle.setText(getResources().getString(R.string.btn_hide_subtitle));
				isOnshowing = true;
			}
			break;
		case R.id.tv_show_anwer:
			ExamFragment mfragment = mAdapter.getFragment(currentpage);
			mfragment.viewAnswer();
			break;
		case R.id.tv_exam_handle:
			if(!isShowAnswer){
			isHandleOver = true;
			//交卷
			TwoDimensionArray resultArray = new TwoDimensionArray(done_position,right_position,wrong_position,clientAnswer);
			Fragment
			resultFragment = FragmentAnswerScard.newInstance(resultArray, context,isHandleOver,isRightWrong,secondTotal);
			addFragmentToStack(resultFragment, R.id.exam_fl);
			try {
				task.cancel();
				task = null;
				timer.cancel();
				timer.purge();
				timer = null;
				handler.removeMessages(msg.what);
			} catch (Exception e) {
				e.printStackTrace();
			}
			}else{
				Toast.makeText(context, "您已完成交卷", Toast.LENGTH_SHORT).show();
			}
			break;

		default:
			break;
		}
	}

	public void showAnswerCard(boolean isHandleOver,boolean isRightWrong) {
		TwoDimensionArray dimensionArray = new TwoDimensionArray(done_position,right_position,wrong_position);
		Fragment newFragment = FragmentAnswerScard.newInstance(dimensionArray, context,isHandleOver,isRightWrong);
		addFragmentToStack(newFragment, R.id.exam_fl);
	}

	public void addFragmentToStack(Fragment newFragment, int layoutid) {
		FragmentTransaction ft = fManager.beginTransaction();
		ft.setCustomAnimations(R.anim.slide_up_in, R.anim.blank, R.anim.blank,
				R.anim.slide_up_out);
		ft.replace(layoutid, newFragment);
		// ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
		ft.addToBackStack(null);
		ft.commit();
	}

	
	public void updateView(String id){
		int position = Integer.parseInt(id)-1;
		mPager.setCurrentItem(position);
	}
	@Override
	public void onBackStackChanged() {
		super.onBackStackChanged();
				
	}


	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		
		if(!isShowAnswer){
		if(keyCode==KeyEvent.KEYCODE_BACK){
			
			Dialog dialog =	new AlertDialog.Builder(this)
            .setTitle("退出考试")
            .setMessage("你还未交卷，确定要退出考试")
            .setPositiveButton(R.string.alert_dialog_confirm, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {

                    finish();
                    
                    //保存用户答案
                    
                    //clientAnswer;
                    
                    
                    
                    
                }
            })
            .setNegativeButton(R.string.alert_dialog_cancel, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                   dialog.dismiss();
                }
            })
            .create();
			dialog.show();
		}
		}
		return super.onKeyDown(keyCode, event);
		
		
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		isShowAnswer = false;
	}

}
