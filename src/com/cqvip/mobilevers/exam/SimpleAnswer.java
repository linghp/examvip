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
	public SimpleAnswer(String id, String answer) {
		super();
		this.id = id;
		this.answer = answer;
	}
	public String getId() {
		return id;
	}
	public String getAnswer() {
		return answer;
	}
	
}
