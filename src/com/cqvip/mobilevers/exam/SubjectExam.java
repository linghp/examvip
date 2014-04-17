package com.cqvip.mobilevers.exam;

import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParserException;

import android.text.TextUtils;
import android.util.Log;

import com.cqvip.mobilevers.utils.PullParseXML;

/**
 * 试卷里面的包含大题，如，选择题，填空题，问答题
 * @author luojiang
 *
 */
public class SubjectExam implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7033662467805212114L;
	private String subjectTypeName;//大题名称
	private int questionNum;//题目数量
	private double scorePerQuestion;//每小题分数
	private int totalScore;//题目总分
	private Subject[] exam3List;//大题下包含的小题
	
	public String getSubjectTypeName() {
		return subjectTypeName;
	} 

	public void setSubjectTypeName(String subjectTypeName) {
		this.subjectTypeName = subjectTypeName;
	}

	public int getQuestionNum() {
		return questionNum;
	}

	public void setQuestionNum(int questionNum) {
		this.questionNum = questionNum;
	}

	public double getScorePerQuestion() {
		return scorePerQuestion;
	}

	public void setScorePerQuestion(double scorePerQuestion) {
		this.scorePerQuestion = scorePerQuestion;
	}

	public int getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(int totalScore) {
		this.totalScore = totalScore;
	}

	public Subject[] getExam3List() {
		return exam3List;
	}

	public void setExam3List(Subject[] exam3List) {
		this.exam3List = exam3List;
	}

	public SubjectExam(JSONObject json)throws JSONException, IOException, XmlPullParserException{
		totalScore = json.getInt("_totalScore");
		double tmp = json.getDouble("_scorePerQuestion");
		double tmp2 = json.getDouble("_scorePerSubject");
		if(tmp>0){
			scorePerQuestion = tmp;
		}else{
			scorePerQuestion = tmp2;
		}
		questionNum = json.getInt("_questionNum");
		subjectTypeName = json.getString("_subjectTypeName");
		JSONArray array = json.getJSONArray("_epsubInfo");
		if(array.length()>0){
			exam3List = new Subject[array.length()];
			for(int i=0;i<array.length();i++)
			exam3List[i] =  getSubjecFormXml(array.getJSONObject(i),subjectTypeName,scorePerQuestion);
		}
	}

	private Subject getSubjecFormXml(JSONObject json,String subjectTypeName,double scorePerQuestion) throws IOException, XmlPullParserException, JSONException {
		String  xml = json.getString("_subXmlString");
		if(TextUtils.isEmpty(xml)){
			questionNum=0;
		}
		String createdate = json.getString("_createTime");
		String 	subId = json.getString("_id").trim();
		//Log.i("Subject","Subject:"+createdate+"subid"+subId);
		//System.out.println(xml);
		PullParseXML pullParse = new PullParseXML();
		Subject sub = pullParse.parseXml(xml,subjectTypeName,scorePerQuestion,createdate,subId);
		return sub;
	}

	@Override
	public String toString() {
		return "SubjectExam [subjectTypeName=" + subjectTypeName
				+ ", questionNum=" + questionNum + ", scorePerQuestion="
				+ scorePerQuestion + ", totalScore=" + totalScore
				+ ", exam3List=" + Arrays.toString(exam3List) + "]";
	}

	public String toStringSimple() {
		return "SubjectExam [subjectTypeName=" + subjectTypeName
				+ ", questionNum=" + questionNum + ", scorePerQuestion="
				+ scorePerQuestion + ", totalScore=" + totalScore
				+ "]";
	}
	
}
