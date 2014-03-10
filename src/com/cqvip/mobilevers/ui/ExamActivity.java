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

	private String examPaperId;
	private Map<String, String> gparams;
	public Exam exam;
	
	private  int countall;//总题数
	private int subjectExamCount;//大题型种类数量
	public ArrayList<Subject> subjects_list=new ArrayList<Subject>();
	private ArrayList<Integer> startLitmitCount_List=new ArrayList<Integer>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.i(TAG, isnight + "");
		super.onCreate(savedInstanceState);
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
		// Fragment newFragment = new FragmentExam();
		// FragmentTransaction ft =
		// getSupportFragmentManager().beginTransaction();
		// ft.add(R.id.exam_fl, newFragment).commit();
		mAdapter = new MyAdapter(getSupportFragmentManager(), context);
		examPaperId=getIntent().getStringExtra(ConstantValues.EXAMPAPERID);
		Log.i(TAG, examPaperId);
		initView();
		init();
		startCountTime();
		
		String url = ConstantValues.SERVER_URL + ConstantValues.GETEXAM_ADDR;
		getData(url, examPaperId);
	}

	private void getData(String url, String examPaperId) {
		getDataFromNet(url, examPaperId);
	}
	
	private void getDataFromNet(String url, String examPaperId) {
		customProgressDialog.show();
		gparams = new HashMap<String, String>();
		gparams.put(ConstantValues.EXAMPAPERID, examPaperId);
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
						exam = new Exam(json);
						if (exam != null) {
							SubjectExam[] subjectExams_array=exam.getExam2lists();
							for (SubjectExam subjectExam : subjectExams_array) {
								startLitmitCount_List.add(countall);
								countall+=subjectExam.getQuestionNum();
								subjects_list.addAll(Arrays.asList(subjectExam.getExam3List()));
								subjectExamCount++;
							}
							System.out.println(exam);
							mPager.setAdapter(mAdapter);
						}

					} else {
						// 登陆错误
						// TODO
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				Toast.makeText(ExamActivity.this, "无数据", Toast.LENGTH_LONG).show();
			}
		}
	};
	
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
				if (currentpage < countall - 1)
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
			return countall;
		}

		@Override
		public Fragment getItem(int position) {
			Log.i(TAG, "MyAdapter_getItem:" + position);
			return ExamFragment.newInstance(position, context,startLitmitCount_List);
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
