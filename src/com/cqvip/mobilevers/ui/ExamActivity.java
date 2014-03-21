package com.cqvip.mobilevers.ui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
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
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cqvip.mobilevers.R;
import com.cqvip.mobilevers.entity.TwoDimensionArray;
import com.cqvip.mobilevers.exam.Exam;
import com.cqvip.mobilevers.exam.Question;
import com.cqvip.mobilevers.exam.Subject;
import com.cqvip.mobilevers.exam.SubjectExam;
import com.cqvip.mobilevers.ui.base.BaseFragmentActivity;
import com.cqvip.mobilevers.view.ExamFragment;
import com.cqvip.mobilevers.view.FragmentAnswerScard;
import com.cqvip.mobilevers.view.ResultFragment;

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
	
	private LinearLayout lookanswer_ll;

	private Timer timer = null;
	private TimerTask task = null;
	private Handler handler = null;
	private Message msg = null;
	private int secondTotal;
	private TextView time_tv;

	//private String examPaperId;
	//private Map<String, String> gparams;
	public Exam exam;
	
	private  int clientShowCount = 0;//总题数
	private  int paperShowCount = 0;//总子题数
	private int subjectExamCount;//大题型种类数量
	private int score;//总分
	public static  int clientScore = 0;//用户得分
	//public ArrayList<SubjectExam> subjectExam_list=new ArrayList<SubjectExam>(); // 所有subject
//	public ArrayList<Integer> subjectExamCount_list=new ArrayList<Integer>(); // 所有subject
//	public ArrayList<Integer> startLitmitCount_List=new ArrayList<Integer>();//统计subject题目
	public ArrayList<Subject> subjects_list=new ArrayList<Subject>(); // 所有subject
	public ArrayList<Question> Question_list=new ArrayList<Question>(); // 所有question
	
	private int[][] all_position;
	public static int[][] done_position;//统计subject题目
	public static int[][] right_position;//统计正确题目
	public static int[][] wrong_position;//统计错误题目
	
	public ArrayList<Integer> cardCount_List=new ArrayList<Integer>();//答题卡题目
	
	
	public static SparseArray<ArrayList<String>> clientAnswer;
	
	
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
		
		exam = (Exam) getIntent().getSerializableExtra("exam");
		// Fragment newFragment = new FragmentExam();
		// FragmentTransaction ft =
		// getSupportFragmentManager().beginTransaction();
		// ft.add(R.id.exam_fl, newFragment).commit();
		mAdapter = new MyAdapter(getSupportFragmentManager(), context);
//		examPaperId=getIntent().getStringExtra(ConstantValues.EXAMPAPERID);
//		Log.i(TAG, examPaperId);
		initView();
		init();
		startCountTime();
		//大题
		SubjectExam[] subjectExams_array=exam.getExam2lists();
		//
		for (SubjectExam subjectExam : subjectExams_array) {
			//客户端显示题目数量
			int count = subjectExam.getQuestionNum();
			cardCount_List.add(count);//答题卡
			
		//	startLitmitCount_List.add(clientShowCount);
			clientShowCount += count;
			//所有试卷大题数量size与startLitmitCount_List相同
			Subject[] subjects=subjectExam.getExam3List();//当_questionNum为0时，判断
			if(subjects!=null){
			subjects_list.addAll(Arrays.asList(subjects));
			paperShowCount += subjects.length;
			}
			
		//	subjectExamCount_list.add(paperShowCount);
			//subjectExamCount++;
		}
		int mCount = subjectExams_array.length;
		done_position = new int[mCount][];
		right_position = new int[mCount][];
		wrong_position = new int[mCount][];
		for(int i=0;i<mCount;i++ ){
			done_position[i] = new int[subjectExams_array[i].getQuestionNum()];
			right_position[i] = new int[subjectExams_array[i].getQuestionNum()];
			wrong_position[i] = new int[subjectExams_array[i].getQuestionNum()];
		}
		
		for (Subject subject : subjects_list) {
			if(subject!=null){
			ArrayList<Question> lists = subject.getQuestion();
			//所有客户端显示question数量
			Question_list.addAll(lists);
			}
		}
		all_position = initDoubleDimensionalData();
		System.out.println("题目总数"+Question_list.size());
		mPager.setAdapter(mAdapter);
		
		clientAnswer = new SparseArray<ArrayList<String>>();
	}
	
	/**
	 * 根据
	 * @param list
	 * @return
	 */
	private int[][] initDoubleDimensionalData() {
		int a=0;
		int b=0;
		//把这个集合转换成二维数组，维度同size相同
		ArrayList<Integer> perCount_row = getCardCount_List();//所有题目（8,10,20,4,5）
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
	

	public int[][] getAll_position() {
		return all_position;
	}

	public void setAll_position(int[][] all_position) {
		this.all_position = all_position;
	}

	public ArrayList<Integer> getCardCount_List() {
		return cardCount_List;
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
		lookanswer_ll = (LinearLayout) findViewById(R.id.lookanswer_ll);
		lookanswer_ll.setOnClickListener(this);
		mPager = (ViewPager) findViewById(R.id.pager);
		mPager.setOnPageChangeListener(this);
		//mPager.setAdapter(mAdapter);
		// mPager.setOffscreenPageLimit(5);
		ViewGroup answercard = (ViewGroup) findViewById(R.id.answercard_ll);
		answercard.setOnClickListener(this);
		ViewGroup directory = (ViewGroup) findViewById(R.id.directory_ll);
		directory.setOnClickListener(this);
		ViewGroup handpaper = (ViewGroup) findViewById(R.id.handpaper_ll);
		handpaper.setOnClickListener(this);

		time_tv = (TextView) findViewById(R.id.time_tv);
		tv_item_count = (TextView) findViewById(R.id.tv_item_count);
		
		Button button = (Button) findViewById(R.id.goto_first);
		button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (currentpage > 0)
					mPager.setCurrentItem(--currentpage);
			}
		});
		button = (Button) findViewById(R.id.goto_last);
		button.setOnClickListener(new OnClickListener() {
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
			// TODO Auto-generated method stub
			return POSITION_NONE;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub

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
		tv_item_count.setText((position+1)+"|"+clientShowCount);
	}
	

	@Override
	public void onPageScrollStateChanged(int state) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.answercard_ll:
			TwoDimensionArray dimensionArray = new TwoDimensionArray();
			dimensionArray.setAllss(done_position);
			dimensionArray.setRightss(right_position);
			dimensionArray.setWrongss(wrong_position);
			Fragment newFragment = FragmentAnswerScard.newInstance(dimensionArray, context);
			addFragmentToStack(newFragment, R.id.exam_fl);
			break;
		case R.id.lookanswer_ll:
		ExamFragment fragment = mAdapter.getFragment(currentpage);
		fragment.viewAnswer();
			break;
		case R.id.directory_ll:
			Log.i("ExamActivity", "onClick_directory_ll");
			break;
		case R.id.handpaper_ll:
			Log.i("ExamActivity", "onClick_handpaper_ll");
			//交卷
			Fragment resultFragment = new ResultFragment();
			addFragmentToStack(resultFragment, R.id.exam_fl);
			try {
				task.cancel();
				task = null;
				timer.cancel();
				timer.purge();
				timer = null;
				handler.removeMessages(msg.what);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;

		default:
			break;
		}
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




}
