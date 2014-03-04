package com.cqvip.mobilevers.exam;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParserException;

import com.cqvip.mobilevers.utils.PullParseXML;

/**
 * 从xml解析返回的 每一道具体试题  （包含标题 和问题和答案，解析）实体类
 * @author luojiang
 *
 */
public class Subject {
	String type;//类型
	Content title;//标题
	ArrayList<Question> question;//问题和答案以及解析
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
