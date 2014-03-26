package com.cqvip.mobilevers.exam;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 保存用户答案类
 * @author luojiang
 *
 */
public class SimpleAnswer implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2816295618629039822L;
	String id;
	String answer;
	double score;
	
	public SimpleAnswer(String id, String answer, double score) {
		super();
		this.id = id;
		this.answer = answer;
		this.score = score;
	}
	public SimpleAnswer(JSONObject json) throws JSONException {
		id = json.getString("_id");
		answer = json.getString("_answer");
		score = json.getDouble("_score");
	}
	public static SimpleAnswer[] formList( JSONArray array) throws JSONException{
		SimpleAnswer[] lists =null;
		int len = array.length();
		if(array!=null&&len>0){
			lists = new SimpleAnswer[len];
		  for(int i=0;i<len;i++){
			  lists[i] = new SimpleAnswer(array.getJSONObject(i));
		  }
		  return lists;
		}else{
			return null;
		}
	}
	
	
	public String getId() {
		return id;
	}
	public String getAnswer() {
		return answer;
	}
	public double getScore() {
		return score;
	}
	@Override
	public String toString() {
		return "SimpleAnswer [id=" + id + ", answer=" + answer + ", score="
				+ score + "]";
	}
	
	
}
