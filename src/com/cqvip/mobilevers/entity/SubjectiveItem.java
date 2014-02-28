package com.cqvip.mobilevers.entity;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 试题对象
 * @author luojiang
 *
 */
public class SubjectiveItem {


	private String questionid;//问题id
	private String type;//类型
	private String stitle;//标题
	private String answer;//答案
	private String[] options =null;
	private int itemCount;//试题数量
	private String desc;//描述
	private String score;//分数
	private boolean iscontainpic;//是否含有图片
	private boolean iscontainsubjective;//是否含有子题
	private SubjectiveItem[] subs = null;
	private String[] img = null;
	
	
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


	public String getStitle() {
		return stitle;
	}


	public void setStitle(String stitle) {
		this.stitle = stitle;
	}


	public String getAnswer() {
		return answer;
	}


	public void setAnswer(String answer) {
		this.answer = answer;
	}


	public String[] getOptions() {
		return options;
	}


	public void setOptions(String[] options) {
		this.options = options;
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


	public boolean isIscontainpic() {
		return iscontainpic;
	}


	public void setIscontainpic(boolean iscontainpic) {
		this.iscontainpic = iscontainpic;
	}


	public boolean isIscontainsubjective() {
		return iscontainsubjective;
	}


	public void setIscontainsubjective(boolean iscontainsubjective) {
		this.iscontainsubjective = iscontainsubjective;
	}


	public SubjectiveItem[] getSubs() {
		return subs;
	}


	public void setSubs(SubjectiveItem[] subs) {
		this.subs = subs;
	}


	public String[] getImg() {
		return img;
	}


	public void setImg(String[] img) {
		this.img = img;
	}


	public SubjectiveItem(JSONObject json)throws JSONException{
		
	}
	
	
	
	
	
	
	
	
	
	
}
