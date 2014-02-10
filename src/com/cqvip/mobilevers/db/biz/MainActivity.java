package com.cqvip.mobilevers.db.biz;

import com.cqvip.mobilevers.R;
import com.cqvip.mobilevers.view.ExamItemAdapter;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.GridView;

public class MainActivity extends Activity {

	private GridView gridview;
	private ExamItemAdapter adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		String[] array = new String[100];
		for(int i=0;i<100;i++){
			array[i]=i+"";
		}
		gridview = (GridView) findViewById(R.id.gridView1);
		adapter = new ExamItemAdapter(this, array);
		gridview.setAdapter(adapter);
	}


}
