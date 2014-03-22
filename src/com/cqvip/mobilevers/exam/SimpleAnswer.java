package com.cqvip.mobilevers.exam;

import java.io.Serializable;

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
	int score;
	
	public SimpleAnswer(String id, String answer, int score) {
		super();
		this.id = id;
		this.answer = answer;
		this.score = score;
	}
	public String getId() {
		return id;
	}
	public String getAnswer() {
		return answer;
	}
	public int getScore() {
		return score;
	}
	
	
}
