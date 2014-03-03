package com.cqvip.mobilevers.exam;

import java.io.IOException;
import java.util.Arrays;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParserException;

import com.cqvip.mobilevers.utils.PullParseXML;

/**
 * 试卷里面的包含大题，如，选择题，填空题，问答题
 * @author luojiang
 *
 */
public class SubjectExam {
	private String subjectTypeName;//大题名称
	private int questionNum;//题目数量
	private int scorePerQuestion;//每小题分数
	private int totalScore;//题目总分
	private Subject[] exam3List;
	
	public SubjectExam(JSONObject json)throws JSONException, IOException, XmlPullParserException{
		totalScore = json.getInt("_totalScore");
		scorePerQuestion = json.getInt("_scorePerQuestion");
		questionNum = json.getInt("_questionNum");
		subjectTypeName = json.getString("subjectTypeName");
		JSONArray array = json.getJSONArray("_epsubInfo");
		if(array.length()>0){
			exam3List = new Subject[array.length()];
			for(int i=0;i<array.length();i++)
			exam3List[i] =  getSubjecFormXml(array.getJSONObject(i));
		}
	}

	private Subject getSubjecFormXml(JSONObject json) throws IOException, XmlPullParserException, JSONException {
		String  xml = json.getString("_subXmlString");
		//System.out.println(xml);
		PullParseXML pullParse = new PullParseXML();
		Subject sub = pullParse.parseXml(xml);
		return sub;
	}

	
	
	@Override
	public String toString() {
		return "SubjectExam [questionNum=" + questionNum
				+ ", scorePerQuestion=" + scorePerQuestion + ", totalScore="
				+ totalScore + ", exam3List=" + Arrays.toString(exam3List)
				+ "]";
	}
	
}
