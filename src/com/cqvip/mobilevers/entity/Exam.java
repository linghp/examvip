package com.cqvip.mobilevers.entity;

import java.util.Arrays;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Exam {
	
	private int examTime;
	private int score;
	private String _examPaperName;
	private SubjectExam[] subjectlists;
	
	public int getExamTime() {
		return examTime;
	}
	public void setExamTime(int examTime) {
		this.examTime = examTime;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public String get_examPaperName() {
		return _examPaperName;
	}
	public void set_examPaperName(String _examPaperName) {
		this._examPaperName = _examPaperName;
	}
	public SubjectExam[] getExam2lists() {
		return subjectlists;
	}
	public void setExam2lists(SubjectExam[] exam2lists) {
		this.subjectlists = exam2lists;
	}
	
	public Exam (JSONObject json)throws JSONException{
	
		
		examTime = json.getInt("_examPaperLimitTime");
		_examPaperName = json.getString("_examPaperName");
		score = json.getInt("_maxScore");
		JSONArray array = json.getJSONArray("_epstsInfo");
		if(array.length()>0){
			for(int i=0;i<array.length();i++)
			subjectlists[i] =  new SubjectExam(array.getJSONObject(i));
		}
	}
	@Override
	public String toString() {
		return "Exam [examTime=" + examTime + ", score=" + score
				+ ", _examPaperName=" + _examPaperName + ", subjectlists="
				+ Arrays.toString(subjectlists) + "]";
	}

	
}
