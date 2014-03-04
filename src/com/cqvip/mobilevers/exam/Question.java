package com.cqvip.mobilevers.exam;

import java.util.ArrayList;
/**
 * 每一道小题，包含选项，解答，和答案解析
 * @author luojiang
 *
 */
public class Question {
	String type;//试题类型
	ArrayList<Content> option;//包含的显示内容
	Solution solution; //答案和答案解析
	int itemCount = 0;//如果题目为图片显示是，显示选项个数，默认值为 0
	public Question(String type, ArrayList<Content> option,
			Solution solution, int itemCount) {
		super();
		this.type = type;
		this.option = option;
		this.solution = solution;
		this.itemCount = itemCount;
	}
	@Override
	public String toString() {
		return "Question [type=" + type + ", option=" + option
				+ ", solution=" + solution + ", itemCount=" + itemCount
				+ "]";
	}
}
