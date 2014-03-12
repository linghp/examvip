package com.cqvip.mobilevers.exam;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 每一道小题，包含选项，解答，和答案解析
 * 
 * @author luojiang
 * 
 */
public class Question implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7932089273676021492L;
	String type;// 试题类型
	Content title;
	ArrayList<Content> option;// 包含的显示内容
	Solution solution; // 答案和答案解析
	int itemCount = 0;// 如果题目为图片显示是，显示选项个数，默认值为 0

	public Question(String type, Content title, ArrayList<Content> option,
			Solution solution, int itemCount) {
		super();
		this.type = type;
		this.title = title;
		this.option = option;
		this.solution = solution;
		this.itemCount = itemCount;
	}

	public String getType() {
		return type;
	}

	public Content getTitle() {
		return title;
	}

	public ArrayList<Content> getOption() {
		return option;
	}

	public Solution getSolution() {
		return solution;
	}

	public int getItemCount() {
		return itemCount;
	}

	@Override
	public String toString() {
		return "Question [type=" + type + ", title=" + title + ", option="
				+ option + ", solution=" + solution + ", itemCount="
				+ itemCount + "]";
	}

}
