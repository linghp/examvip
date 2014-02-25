package com.cqvip.mobilevers.entity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 主观题
 * @author luojiang
 *
 */
public class SubjectiveItem {


	private String questionid;//问题id
	private String type;//类型
	private Stitle stitle;//标题
	private String answer;//答案
	private String desc;//描述
	private String score;//分数
	private boolean iscontainchoices;
	private boolean iscontainsubjective;
	private Choice[] choices = null;
	private SubjectiveItem[] subs = null;
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
	public Stitle getStitle() {
		return stitle;
	}
	public void setStitle(Stitle stitle) {
		this.stitle = stitle;
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

	public boolean isIscontainchoices() {
		return iscontainchoices;
	}
	public void setIscontainchoices(boolean iscontainchoices) {
		this.iscontainchoices = iscontainchoices;
	}
	public boolean isIscontainsubjective() {
		return iscontainsubjective;
	}
	public void setIscontainsubjective(boolean iscontainsubjective) {
		this.iscontainsubjective = iscontainsubjective;
	}
	public Choice[] getChoices() {
		return choices;
	}
	public void setChoices(Choice[] choices) {
		this.choices = choices;
	}
	public SubjectiveItem[] getSubs() {
		return subs;
	}
	public void setSubs(SubjectiveItem[] subs) {
		this.subs = subs;
	}
	
	public SubjectiveItem(JSONObject json)throws JSONException{
		questionid = json.getString("questionid");
		type = json.getString("type");
		answer = json.getString("answer");
		desc = json.getString("desc");
		score = json.getString("score");
		iscontainchoices = json.getBoolean("iscontainchoices");
		iscontainsubjective = json.getBoolean("iscontainsubjective");
		stitle = new Stitle(json.getString("stitle"));
		if(iscontainchoices){
			JSONArray choiceArray = json.getJSONArray("choices");
			if(choiceArray!=null&&choiceArray.length()>0){
				for(int i=0;i<choiceArray.length();i++){
					choices[i]= new Choice(choiceArray.getJSONObject(i));
				}
			}
		}
		if(iscontainsubjective){
			JSONArray subsArray = json.getJSONArray("subs");
			if(subsArray!=null&&subsArray.length()>0){
				for(int i=0;i<subsArray.length();i++){
					subs[i]= new SubjectiveItem(subsArray.getJSONObject(i));
				}
			}
		}
	}
	
	
	
	
	
	
	
	
	
	
}
