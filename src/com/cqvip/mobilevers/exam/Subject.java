package com.cqvip.mobilevers.exam;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParserException;

import com.cqvip.mobilevers.utils.PullParseXML;

/**
 * 从xml解析返回的 每一道具体试题 （包含标题 和问题和答案，解析）实体类
 * 
 * @author luojiang
 * 
 */
public class Subject implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8623714460809516006L;
	String type;// 类型
	Content title;// 标题
	ArrayList<Question> question;// 问题和答案以及解析

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Content getTitle() {
		return title;
	}

	public void setTitle(Content title) {
		this.title = title;
	}

	public ArrayList<Question> getQuestion() {
		return question;
	}

	public void setQuestion(ArrayList<Question> question) {
		this.question = question;
	}

	@Override
	public String toString() {
		return "Subject [type=" + type + ", title=" + title + ", question="
				+ question + "]";
	}

	public Subject(String type, Content title, ArrayList<Question> question) {
		super();
		this.type = type;
		this.title = title;
		this.question = question;
	}

}
