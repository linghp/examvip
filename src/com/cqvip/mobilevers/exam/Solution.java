package com.cqvip.mobilevers.exam;


/**
 * 包含答案和答案解析
 * @author luojiang
 *
 */
public class Solution {
	Content answer;//答案，可能是图片或者文字
	Content answerDesc;//答案解析，图片或者文字
	
	public Solution(Content answer, Content answerDesc) {
		this.answer = answer;
		this.answerDesc = answerDesc;
	}

	@Override
	public String toString() {
		return "Solution [answer=" + answer + ", answerDesc=" + answerDesc
				+ "]";
	}
	
}
