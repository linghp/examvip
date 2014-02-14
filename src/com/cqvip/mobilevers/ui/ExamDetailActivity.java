package com.cqvip.mobilevers.ui;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.cqvip.mobilevers.R;

public class ExamDetailActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.paper_info);
		final ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.DISPLAY_SHOW_TITLE);
		actionBar.setTitle("试卷详情");
		// Show the Up button in the action bar.
		actionBar.setDisplayHomeAsUpEnabled(true);
	}
	public void begintest(View v){
		startActivity(new Intent(this,ExamActivity.class));
	}
}
	