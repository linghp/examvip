package com.cqvip.mobilevers.exam;

import java.io.Serializable;

/**
 * 基本试卷信息
 * @author luojiang
 *
 */
public class BaseExamInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 458653360598788616L;
	private String id;
	private int needTime;
	private String name;
	private int score;
	private int allItemCount;
	public BaseExamInfo(String id, int needTime, String name, int score,
			int allItemCount) {
		super();
		this.id = id;
		this.needTime = needTime;
		this.name = name;
		this.score = score;
		this.allItemCount = allItemCount;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getId() {
		return id;
	}
	public int getNeedTime() {
		return needTime;
	}
	public String getName() {
		return name;
	}
	public int getScore() {
		return score;
	}
	public int getAllItemCount() {
		return allItemCount;
	}
	
	
	
	
}
