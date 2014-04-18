package com.cqvip.mobilevers.utils;

import android.util.SparseArray;

import com.cqvip.mobilevers.exam.SimpleAnswer;

public class AnswerUtils {


	/**
	 * 组织答案
	 * @param clientAnswers2
	 * @return
	 */
	public static  String formResult(
			SparseArray<SimpleAnswer> answers) {
		StringBuilder builder = new StringBuilder();
		int key=0;
		for(int i=0;i<answers.size();i++){
			key = answers.keyAt(i);
			SimpleAnswer firstAnswer = answers.get(key);
			if(firstAnswer!=null){
			String mAnswer = firstAnswer.getAnswer();
			double score = firstAnswer.getScore();
			String questionId = firstAnswer.getId();
			StringBuilder mbuilder = new StringBuilder();
			mbuilder.append(questionId);
			mbuilder.append("<*%*%*%>");
			mbuilder.append(mAnswer);
			mbuilder.append("<*%*%*%>");
			mbuilder.append(score+"");
			mbuilder.append("<!%!%!%>");
			builder.append(mbuilder.toString());
			}
		}
		return builder.toString();
	}
	
	public static  int getItemNum(
			SparseArray<SimpleAnswer> answers) {
		int num = 0;
		int key=0;
		for(int i=0;i<answers.size();i++){
			key = answers.keyAt(i);
			SimpleAnswer firstAnswer = answers.get(key);
			if(firstAnswer!=null){
				num++;
			}
		}
		return num;
	}
	
}
