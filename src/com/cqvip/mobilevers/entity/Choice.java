package com.cqvip.mobilevers.entity;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 选择题类
 * @author luojiang
 *
 */
public class Choice {

	private String questionid;//问题id
	private String type;//问题类型
	private String[] option;//选项
	private String answer;//答案
	private String desc;//描述
	private String score;//分数
	private Stitle stitle;//标题
	

	public String getQuestionid() {
		return questionid;
	}
	public void setQuestionid(String questionid) {
		this.questionid = questionid;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String[] getOption() {
		return option;
	}
	public void setOption(String[] option) {
		this.option = option;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}
	public Stitle getStitle() {
		return stitle;
	}
	public void setStitle(Stitle stitle) {
		this.stitle = stitle;
	}

	public Choice(String data)throws JSONException{
		JSONObject json = new JSONObject(data);
		stitle = new Stitle(json.getString("stitle"));
		questionid = json.getString("questionid");
		type = json.getString(type);
		answer = json.getString(answer);
		desc = json.getString(desc);
		score = json.getString(score);
		JSONArray array = json.getJSONArray("option");
		if(array.length()>0){
			for(int i=0;i>array.length();i++){
				option[i]= array.get(i).toString();
			}
		}else{
			option = null;
		}
	}
	public Choice(JSONObject json)throws JSONException{
		stitle = new Stitle(json.getString("stitle"));
		questionid = json.getString("questionid");
		type = json.getString(type);
		answer = json.getString(answer);
		desc = json.getString(desc);
		score = json.getString(score);
		JSONArray array = json.getJSONArray("option");
		if(array.length()>0){
			for(int i=0;i>array.length();i++){
				option[i]= array.get(i).toString();
			}
		}else{
			option = null;
		}
	}
	public List<Choice> formList(String data) throws JSONException{
		
		
		
		//TODO
		
		
		
		return null;
	}
	
}
