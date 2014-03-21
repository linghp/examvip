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
	private String id;//试题id
	private String type;// 试题类型
	private Content title;
	private ArrayList<Content> option;// 包含的显示内容
	private Solution solution; // 答案和答案解析
	private int itemCount = 0;// 如果题目为图片显示是，显示选项个数，默认值为 0
	
	private  Content sub_Title; //子题标题
	private String sebexam_Title;//大题标题
	private String  sub_Type;//subject类型
	int perscore;
	
	
	
	public Question(String id,String type, Content title, ArrayList<Content> option,
			Solution solution, int itemCount) {
		super();
		this.id = id;
		this.type = type;
		this.title = title;
		this.option = option;
		this.solution = solution;
		this.itemCount = itemCount;
	}

	public Question(String id, String type, Content title,
			ArrayList<Content> option, Solution solution, int itemCount,
			Content sub_Title, String sebexam_Title, String sub_Type,
			int perscore) {
		super();
		this.id = id;
		this.type = type;
		this.title = title;
		this.option = option;
		this.solution = solution;
		this.itemCount = itemCount;
		this.sub_Title = sub_Title;
		this.sebexam_Title = sebexam_Title;
		this.sub_Type = sub_Type;
		this.perscore = perscore;
	}

;	public String getId() {
		return id;
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
	

	public Content getSub_Title() {
		return sub_Title;
	}


	public String getSebexam_Title() {
		return sebexam_Title;
	}


	public String getSub_Type() {
		return sub_Type;
	}

	@Override
	public String toString() {
		return "Question [id=" + id + ", type=" + type + ", title=" + title
				+ ", option=" + option + ", solution=" + solution
				+ ", itemCount=" + itemCount + ", sub_Title=" + sub_Title
				+ ", sebexam_Title=" + sebexam_Title + ", sub_Type=" + sub_Type
				+ ", perscore=" + perscore + "]";
	}



}
