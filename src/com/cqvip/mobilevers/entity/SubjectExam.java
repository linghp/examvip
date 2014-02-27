package com.cqvip.mobilevers.entity;

import java.util.Arrays;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 包含的大题
 * @author luojiang
 *
 */
public class SubjectExam {
	private int questionNum;//题目数量
	private int scorePerQuestion;//每小题分数
	private int totalScore;//题目总分
	private Exam3[] exam3List;
	
	
	public int getQuestionNum() {
		return questionNum;
	}


	public void setQuestionNum(int questionNum) {
		this.questionNum = questionNum;
	}


	public int getScorePerQuestion() {
		return scorePerQuestion;
	}


	public void setScorePerQuestion(int scorePerQuestion) {
		this.scorePerQuestion = scorePerQuestion;
	}


	public int getTotalScore() {
		return totalScore;
	}


	public void setTotalScore(int totalScore) {
		this.totalScore = totalScore;
	}


	public Exam3[] getExam3List() {
		return exam3List;
	}


	public void setExam3List(Exam3[] exam3List) {
		this.exam3List = exam3List;
	}


	public SubjectExam(JSONObject json)throws JSONException{
		totalScore = json.getInt("_totalScore");
		scorePerQuestion = json.getInt("_scorePerQuestion");
		questionNum = json.getInt("_questionNum");
		
		JSONArray array = json.getJSONArray("_epsubInfo");
		if(array.length()>0){
			for(int i=0;i<array.length();i++)
			exam3List[i] =  new Exam3(array.getJSONObject(i));
		}
		
	}


	@Override
	public String toString() {
		return "SubjectExam [questionNum=" + questionNum
				+ ", scorePerQuestion=" + scorePerQuestion + ", totalScore="
				+ totalScore + ", exam3List=" + Arrays.toString(exam3List)
				+ "]";
	}
	
}
