package com.cqvip.mobilevers.view;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cqvip.mobilevers.R;
import com.cqvip.mobilevers.exam.Exam;
import com.cqvip.mobilevers.exam.Subject;
import com.cqvip.mobilevers.exam.SubjectExam;
import com.cqvip.mobilevers.ui.ExamActivity;
import com.cqvip.mobilevers.widget.ImageTextView;

public class ExamFragment extends Fragment{
	int mNum;
    private SubjectExam[] subjectlists;
    private ArrayList<Subject> subjects_list;
    public ArrayList<Integer> startLitmitCount_List;
    @Override
    public void onAttach(Activity activity) {
    	Log.i("ExamFragment", "onAttach"+mNum);
    	super.onAttach(activity);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	Log.i("ExamFragment", "onCreate"+mNum);
    	super.onCreate(savedInstanceState);
    }
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.i("ExamFragment", "onCreateView"+mNum);
		View v = inflater.inflate(R.layout.fragment_exampager, container,
				false);
		int current=0;//当前的第几大题
		 boolean isfirst=false;
		for (int i = 0; i < startLitmitCount_List.size(); i++) {
			if(mNum==0){
				current=0;
				isfirst=true;
				break;
			}
			if(mNum==startLitmitCount_List.get(i)){
				current=i;
				isfirst=true;
				break;
			}
		}
		subjectlists=((ExamActivity)getActivity()).exam.getExam2lists();
		subjects_list=((ExamActivity)getActivity()).subjects_list;
		
		ImageTextView tv = (ImageTextView) v.findViewById(R.id.text);
		
		if(isfirst){
		tv.setText(subjects_list.get(mNum).getQuestion().get(0).getSolution().getAnswerDesc(),"Fragment #"+mNum+"    "+subjectlists[current].toStringSimple()+"\n"+ subjects_list.get(mNum).getTitle());
		}else{
			 tv.setText(subjects_list.get(mNum).getQuestion().get(0).getSolution().getAnswerDesc(),"Fragment #"+mNum+"    "+ subjects_list.get(mNum).getTitle());	
		}
		tv.setBackgroundDrawable(getResources().getDrawable(
				android.R.drawable.gallery_thumb));
		return v;
	}

	public static ExamFragment newInstance(int num, Context context,ArrayList<Integer> startLitmitCount_List) {

		ExamFragment f = (ExamFragment) ExamFragment.instantiate(context,
				ExamFragment.class.getName());
		// ExamFragment f =new ExamFragment();
		f.mNum = num;
		// Supply num input as an argument.
		Bundle args = new Bundle();
		args.putInt("num", num);
		f.setArguments(args);
        f.startLitmitCount_List=startLitmitCount_List;
		return f;
	}
}
