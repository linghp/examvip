package com.cqvip.mobilevers.exam;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParserException;

import com.cqvip.mobilevers.utils.PullParseXML;

/**
 * 从xml解析返回的 试卷 实体类
 * @author luojiang
 *
 */
public class Subject {
	String type;
	String title;
	ArrayList<Question> question;
	boolean isTitleContainPic;
	ArrayList<String> pics;
	@Override
	public String toString() {
		return "Subject [type=" + type + ", title=" + title + ", question="
				+ question + ", isTitleContainPic=" + isTitleContainPic
				+ ", pics=" + pics + "]";
	}
	public Subject(String type, String title, ArrayList<Question> question,
			boolean isTitleContainPic, ArrayList<String> pics) {
		super();
		this.type = type;
		this.title = title;
		this.question = question;
		this.isTitleContainPic = isTitleContainPic;
		this.pics = pics;
	}

	
}
