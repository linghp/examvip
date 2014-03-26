package com.cqvip.mobilevers.exam;

import java.io.Serializable;

/**
 * 用户做题的情况，对，错，用时，得分
 * @author luojiang
 *
 */
public class ExamDoneInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8862801692697580132L;
	private int rightCount;
	private int wrongCount;
	private int doneCount;
	private double score;
	private int useTime;
	
	public ExamDoneInfo(int rightCount, int wrongCount, int doneCount,
			double score, int useTime) {
		super();
		this.rightCount = rightCount;
		this.wrongCount = wrongCount;
		this.doneCount = doneCount;
		this.score = score;
		this.useTime = useTime;
	}
	public int getRightCount() {
		return rightCount;
	}
	public int getWrongCount() {
		return wrongCount;
	}
	public int getDoneCount() {
		return doneCount;
	}
	public double getScore() {
		return score;
	}
	public int getUseTime() {
		return useTime;
	}
	@Override
	public String toString() {
		return "ExamDoneInfo [rightCount=" + rightCount + ", wrongCount="
				+ wrongCount + ", doneCount=" + doneCount + ", score=" + score
				+ ", useTime=" + useTime + "]";
	}
	
}
