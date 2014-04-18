package com.cqvip.mobilevers.exam;

import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParserException;

/**
 * 试卷对象
 * Exam(试题)->>SubjectExam(各类大题)->>Subjcet(所有小题)
 * @author luojiang
 *
 */
public class Exam implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1314424987386293407L;
	private int examTime;//考试时间
	private int score;//总分
	private String _examPaperName;//试题名称
	private SubjectExam[] subjectlists;//包含的大题
	private SimpleAnswer[] answerlists;//用户答案
	
	public SubjectExam[] getSubjectlists() {
		return subjectlists;
	}
	public SimpleAnswer[] getAnswerlists() {
		return answerlists;
	}
	public void setAnswerlists(SimpleAnswer[] answerlists) {
		this.answerlists = answerlists;
	}
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
	
	public Exam (JSONObject json)throws JSONException, IOException, XmlPullParserException{
	
		
		examTime = json.getInt("_examPaperLimitTime");
		_examPaperName = json.getString("_examPaperName");
		score = json.getInt("_maxScore");
		JSONArray tmparray = json.getJSONArray("_userAnsItem");
		answerlists = SimpleAnswer.formList(tmparray);
		JSONArray array = json.getJSONArray("_epstsInfo");
		if(array.length()>0){
			subjectlists = new SubjectExam[array.length()];
			for(int i=0;i<array.length();i++)
			subjectlists[i] =  new SubjectExam(array.getJSONObject(i));
		}
	}
	@Override
	public String toString() {
		return "Exam [examTime=" + examTime + ", score=" + score
				+ ", _examPaperName=" + _examPaperName + ", subjectlists="
				+ Arrays.toString(subjectlists) + ", answerlists="
				+ Arrays.toString(answerlists) + "]";
	}
	
	
}
