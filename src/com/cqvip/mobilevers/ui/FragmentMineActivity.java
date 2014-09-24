package com.cqvip.mobilevers.ui;

import java.util.Timer;
import java.util.TimerTask;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cqvip.mobilevers.MyApplication;
import com.cqvip.mobilevers.R;
import com.cqvip.mobilevers.config.ConstantValues;
import com.cqvip.mobilevers.db.UserDao;
import com.cqvip.mobilevers.ui.base.BaseMainFragmentActivity;
import com.cqvip.mobilevers.utils.Utils;
import com.cqvip.mobilevers.view.DoneExamPaperListFragment;
import com.cqvip.mobilevers.view.ExamDetailFragment;
import com.cqvip.mobilevers.view.ExamDetailFragment.I_ExamDetail;
import com.cqvip.mobilevers.view.LoginFragment;

/**
 * 我的模块
 * 
 * @author luojiang
 * 
 */
public class FragmentMineActivity extends BaseMainFragmentActivity implements I_ExamDetail{

	private static final String TAG = "FragmentMineActivity";
	private TextView hellotome;
	public UserDao userDao;
	public SQLiteDatabase db;
	private Button login_btn;
	private boolean islogin;
	private boolean isclick;// 防止双击
	
	public boolean isfavorite=true;
	// private SharedPreferences localUsers;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_tab_mine);
		getDB();
		SharedPreferences localUsers = getSharedPreferences("mobilevers",
				MODE_PRIVATE);
		String userid = localUsers.getString("userid", "0");
		if (!userid.equals("0")) {
			Log.i("USERID", userid);
			islogin = true;
			loginUI();
		}
	}

	/**
	 * 我做过的试卷
	 * @param view
	 */
	public void mypassedexam(View view){
		String userid=null;
		if((userid=Utils.checkUserid(this))==null){
			return;
		}
       DoneExamPaperListFragment newFragment = DoneExamPaperListFragment.newInstance(userid, getString(R.string.DoneExamPaper), ConstantValues.GetUserCurrExamPaperList,ConstantValues.DONG_PAPER);
       addFragmentToStack(newFragment, android.R.id.content,DoneExamPaperListFragment.TAG);
	}	
	/**
	 * 我正在做的试卷
	 * @param view
	 */
	public void mydoingexam(View view){
		String userid=null;
		if((userid=Utils.checkUserid(this))==null){
			return;
		}
		DoneExamPaperListFragment newFragment = DoneExamPaperListFragment.newInstance(userid, getString(R.string.DoingExamPaper), ConstantValues.GetUserCurrExamPaperList,ConstantValues.DOING_PAPER);
		addFragmentToStack(newFragment, android.R.id.content,DoneExamPaperListFragment.TAG);
	}	
	
	/**
	 * 我收藏的试卷
	 * @param view
	 */
	public void myfavorite(View view){
		String userid=null;
		if((userid=Utils.checkUserid(this))==null){
			return;
		}
       DoneExamPaperListFragment newFragment = DoneExamPaperListFragment.newInstance(userid,getString(R.string.favoriteExamPaper),ConstantValues.GETFAVORITESEXAMPAPERLIST,ConstantValues.FAVORITE_PAPER);
       addFragmentToStack(newFragment, android.R.id.content,DoneExamPaperListFragment.TAG);
	}	
	
	private void getDB() {
		userDao = ((MyApplication) getApplication()).daoSession.getUserDao();
		db = ((MyApplication) getApplication()).db;
	}

	public void exitorlogin(View v) {
		// 防止双击
		TimerTask task = new TimerTask() {
				@Override
				public void run() {
					isclick=false;
				}
			};
			Timer timer = new Timer(true);
			timer.schedule(task, 1000);
			
		if (!isclick) {
			isclick = true;
			if (islogin) {
				SharedPreferences localUsers = getSharedPreferences(
						"mobilevers", MODE_PRIVATE);
				Editor editor = localUsers.edit();
				editor.putString("userid", "0");
				editor.putString("username", "0");
				editor.commit();
				islogin = false;
				userDao.deleteAll();
				logoutUI();
				return;
			}
			Fragment newFragment = new LoginFragment();
			addFragmentToStack(newFragment, android.R.id.content,"");
		}
	}

	public void loginUI() {
		// User user=null;
		// List<User> users = userDao.loadAll();
		// if (users != null && users.size() == 1) {
		// user = users.get(0);
		// }
		// if (user != null) {
		SharedPreferences localUsers = getSharedPreferences("mobilevers",
				MODE_PRIVATE);
		String username = localUsers.getString("realname", "0");
		hellotome = (TextView) findViewById(R.id.hellotome);
		login_btn = (Button) findViewById(R.id.login_btn);
		hellotome.setText(username+",您好！");
		login_btn.setText("注销");
		islogin = true;
		sync_updateview(true);
	}

	/**
	 * 同步
	 */
	private void sync_updateview(boolean b) {
		FragmentExamActivity fragmentExamActivity=(FragmentExamActivity) ((MainActivity)getParent()).getLocalActivityManager().getActivity("题库");
		ExamDetailFragment examDetailFragment=(ExamDetailFragment) fragmentExamActivity.fManager.findFragmentByTag(ExamDetailFragment.TAG);
		if(examDetailFragment!=null){
			examDetailFragment.updateview(b);
		}
	}

	public void logoutUI() {
		hellotome.setText(getString(R.string.tips_unlogin));
		login_btn.setText(getString(R.string.btn_login));	
		sync_updateview(false);
	}

	@Override
	public void delfavorite() {
		isfavorite=false;
	}
}
