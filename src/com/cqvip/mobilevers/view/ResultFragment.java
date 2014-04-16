package com.cqvip.mobilevers.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.cqvip.mobilevers.R;
import com.cqvip.mobilevers.exam.BaseExamInfo;
import com.cqvip.mobilevers.exam.ExamDoneInfo;
import com.cqvip.mobilevers.ui.ExamActivity;

/**
 * 交卷显示界面
 * 
 * @author luojiang
 * 
 */

public class ResultFragment extends Fragment implements OnClickListener {

	private static final String BASEINFO = "base";
	private static final String SCORE = "score";

	private TextView tx_testresult_tiltle;
	private TextView tx_testresult_score;
	private TextView tx_testresult_totalscore;
	private TextView tx_testresult_time;
	private TextView tx_testresult_totalcount;
	private TextView tx_testresult_percent;
	private BaseExamInfo baseExamInfo;
	private ExamDoneInfo examDoneInfo;
	private ImageView img_back;

	public static ResultFragment newInstance(BaseExamInfo baseInfo,
			ExamDoneInfo doneInfo) {
		ResultFragment f = new ResultFragment();

		// Supply num input as an argument.
		Bundle args = new Bundle();
		args.putSerializable(BASEINFO, baseInfo);
		args.putSerializable(SCORE, doneInfo);
		f.setArguments(args);
		return f;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		ExamActivity.isShowAnswer = true;
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		// View v = inflater.inflate(R.layout.fragment_result_exam, null);
		View view = inflater.inflate(R.layout.fragment_result_exam, container,
				false);
		Bundle bundle = getArguments();
		baseExamInfo = (BaseExamInfo) bundle.getSerializable(BASEINFO);
		examDoneInfo = (ExamDoneInfo) bundle.getSerializable(SCORE);

		img_back = (ImageView) view.findViewById(R.id.img_back);
		img_back.setOnClickListener(this);
		tx_testresult_tiltle = (TextView) view
				.findViewById(R.id.tx_testresult_tiltle);
		tx_testresult_score = (TextView) view
				.findViewById(R.id.tx_testresult_score);
		tx_testresult_totalscore = (TextView) view
				.findViewById(R.id.tx_testresult_totalscore);
		tx_testresult_time = (TextView) view
				.findViewById(R.id.tx_testresult_time);
		tx_testresult_totalcount = (TextView) view
				.findViewById(R.id.tx_testresult_totalcount);
		tx_testresult_percent = (TextView) view
				.findViewById(R.id.tx_testresult_percent);

		tx_testresult_tiltle.setText(baseExamInfo.getName());
		tx_testresult_score.setText("您的得分：" + examDoneInfo.getScore());
		tx_testresult_totalscore.setText("试卷总分" + baseExamInfo.getScore());
		tx_testresult_time.setText("做卷时间："
				+ formTime(examDoneInfo.getUseTime()));
		tx_testresult_totalcount.setText("试题总数：共"
				+ baseExamInfo.getAllItemCount() + "道，" + "已做"
				+ examDoneInfo.getDoneCount() + "道");
		tx_testresult_percent.setText("正确率："
				+ getPercent(examDoneInfo.getRightCount(),
						examDoneInfo.getWrongCount()) + "（答对"
				+ examDoneInfo.getRightCount() + "道，答错"
				+ examDoneInfo.getWrongCount() + "道）");

		Button viewAnsewer = (Button) view.findViewById(R.id.btn_view_desc);
		viewAnsewer.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				getFragmentManager().popBackStack();
				((ExamActivity) getActivity()).updateView(1 + "lookansweranalysis");
			}
		});

		return view;
	}

	private String getPercent(double right, double wrong) {
		double cent = right / (right + wrong);
		int percent = (int) (cent * 100);
		String result = percent + "%";
		return result;
	}

	private String formTime(int useTime) {

		int second = useTime % 60;
		int minTotal = (useTime / 60);
		int hour = 0, minute = 0;
		minute = minTotal;
		if (minTotal >= 60) {
			hour = minTotal / 60;
			minute = minute % 60;
		}
		return getStandrad(hour, minTotal, second);
	}

	private String getStandrad(int hour, int minTotal, int second) {
		String time = hour + "小时" + minTotal + "分" + second + "秒";
		return time;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.img_back:

			((ExamActivity) getActivity()).getSupportFragmentManager()
					.popBackStack();
			getActivity().finish();

			break;

		default:
			break;
		}

	}

}
