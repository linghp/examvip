package com.cqvip.mobilevers.ui;

import com.cqvip.mobilevers.R;
import com.cqvip.mobilevers.R.layout;
import com.cqvip.mobilevers.view.ExamItemAdapter;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.GridView;

/**
 * 答题卡
 * @author luojiang
 *
 */
public class CardExamActivity extends Activity {

	private GridView gridview;
	private ExamItemAdapter adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_card_exam);
		String[] array = new String[100];
		for(int i=0;i<100;i++){
			array[i]=i+"";
		}
		gridview = (GridView) findViewById(R.id.gridView1);
		adapter = new ExamItemAdapter(this, array);
		gridview.setAdapter(adapter);
	}


}
