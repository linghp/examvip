package com.cqvip.mobilevers.ui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONException;
import org.json.JSONObject;

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
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request.Method;
import com.android.volley.Response.Listener;
import com.cqvip.mobilevers.R;
import com.cqvip.mobilevers.adapter.ExamBClassfyAdapter;
import com.cqvip.mobilevers.config.ConstantValues;
import com.cqvip.mobilevers.db.TwoLevelType;
import com.cqvip.mobilevers.exam.Exam;
import com.cqvip.mobilevers.exam.Question;
import com.cqvip.mobilevers.exam.Subject;
import com.cqvip.mobilevers.exam.SubjectExam;
import com.cqvip.mobilevers.http.HttpUtils;
import com.cqvip.mobilevers.http.VersStringRequest;
import com.cqvip.mobilevers.ui.base.BaseFragmentActivity;
import com.cqvip.mobilevers.view.ExamFragment;
import com.cqvip.mobilevers.view.FragmentAnswerScard;

public class ExamActivity extends BaseFragmentActivity implements
		OnPageChangeListener, OnClickListener {

	final static String TAG = "ExamActivity";
	//static final int NUM_ITEMS = 10;
	MyAdapter mAdapter;
	private Context context;
	ViewPager mPager;
	int currentpage = 0;
	public static boolean isnight = false;

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
	
	public ArrayList<SubjectExam> subjectExam_list=new ArrayList<SubjectExam>(); // 所有subject
	public ArrayList<Subject> subjects_list=new ArrayList<Subject>(); // 所有subject
	public ArrayList<Question> Question_list=new ArrayList<Question>(); // 所有question
	public ArrayList<Integer> subjectExamCount_list=new ArrayList<Integer>(); // 所有subject
	public ArrayList<Integer> startLitmitCount_List=new ArrayList<Integer>();//统计subject题目
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
			startLitmitCount_List.add(clientShowCount);
			clientShowCount += subjectExam.getQuestionNum();
			//所有试卷大题数量size与startLitmitCount_List相同
			subjects_list.addAll(Arrays.asList(subjectExam.getExam3List()));
			
			subjectExamCount_list.add(paperShowCount);
			paperShowCount += subjectExam.getExam3List().length;
			
			//subjectExamCount++;
		}
		
		for (Subject subject : subjects_list) {
			if(subject!=null){
			ArrayList<Question> lists = subject.getQuestion();
			//所有客户端显示question数量
			Question_list.addAll(lists);
			}
		}
		
		System.out.println("题目总数"+Question_list.size());
		mPager.setAdapter(mAdapter);
		
	}
	
	
	

	public ArrayList<SubjectExam> getSubjectExam_list() {
		return subjectExam_list;
	}

	public ArrayList<Subject> getSubjects_list() {
		return subjects_list;
	}

	public int getSubjectExamCount() {
		return subjectExamCount;
	}

	public ArrayList<Integer> getSubjectExamCount_list() {
		return subjectExamCount_list;
	}



	public ArrayList<Integer> getStartLitmitCount_List() {
		return startLitmitCount_List;
	}

	public void setStartLitmitCount_List(ArrayList<Integer> startLitmitCount_List) {
		this.startLitmitCount_List = startLitmitCount_List;
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
		public MyAdapter(FragmentManager fm, Context context) {
			super(fm);
			this.context = context;
		}

		@Override
		public int getCount() {
			return clientShowCount;
		}

		@Override
		public Fragment getItem(int position) {
			Log.i(TAG, "MyAdapter_getItem:" + position);
			return ExamFragment.newInstance(position,context);
		}

		@Override
		public int getItemPosition(Object object) {
			// TODO Auto-generated method stub
			return POSITION_NONE;
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
	}

	@Override
	public void onPageScrollStateChanged(int state) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.answercard_ll:
			Fragment newFragment = new FragmentAnswerScard();
			addFragmentToStack(newFragment, R.id.exam_fl);
			break;
		case R.id.directory_ll:
			Log.i("ExamActivity", "onClick_directory_ll");
			break;
		case R.id.handpaper_ll:
			Log.i("ExamActivity", "onClick_handpaper_ll");
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

}
