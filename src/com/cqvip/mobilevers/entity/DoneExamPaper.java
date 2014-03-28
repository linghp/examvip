package com.cqvip.mobilevers.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.view.View;

/**
 * 试卷信息类
 * 
 * @author luojiang
 * 
 */
public class DoneExamPaper implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 393310532940371241L;
	private int type;// 类型
	private int score;// 总分
	private int clientScore;// 用户得分
	private String subjectid;
	private String name;
	private String kclassName;
	private String kclassid;
	private String adddate;

	public DoneExamPaper(JSONObject json) throws JSONException {
		subjectid = json.getString("exampaperid");
		name = json.getString("exampapername");
		clientScore = json.getInt("testscore");
		kclassName = json.getString("kclassname");
		kclassid = json.getString("kclassid");
		adddate = json.getString("exittime");
	}

	public DoneExamPaper(JSONObject json,String tag) throws JSONException {
		subjectid = json.getString("exampaperid");//试卷id
		name = json.getString("exampapername");//试卷名称
		type = json.getInt("kclassid");//所属类别id
		kclassName = json.getString("kclassname");//所属类别名称
		//pulishyear = json.getString("createtime");
		adddate = json.getString("modifytime");
	}
	
	public int getType() {
		return type;
	}

	public int getScore() {
		return score;
	}

	public int getClientScore() {
		return clientScore;
	}

	public String getSubjectid() {
		return subjectid;
	}

	public String getName() {
		return name;
	}

	public String getKclassName() {
		return kclassName;
	}

	public String getKclassid() {
		return kclassid;
	}

	public String getAdddate() {
		return adddate;
	}

	protected static int getInt(String key, JSONObject json)
			throws JSONException {
		String str = json.getString(key);
		if (null == str || "".equals(str) || "null".equals(str)) {
			return 0;
		}
		return Integer.parseInt(str);
	}

	protected static long getLong(String key, JSONObject json)
			throws JSONException {
		String str = json.getString(key);
		if (null == str || "".equals(str) || "null".equals(str)) {
			return 0;
		}
		return Long.parseLong(str);
	}

	public static List<DoneExamPaper> formList(JSONArray array)
			throws JSONException {
		List<DoneExamPaper> mtempList = new ArrayList<DoneExamPaper>();
		if (array != null && array.length() > 0) {
			for (int i = 0; i < array.length(); i++) {
				JSONObject obj = array.getJSONObject(i);
				DoneExamPaper detail = new DoneExamPaper(obj);
				mtempList.add(detail);
			}
			return mtempList;
		}
		return null;
	}

	public static List<DoneExamPaper> formList(JSONObject json)
			throws JSONException {
		JSONArray jsonarray = json.getJSONArray("result");
		JSONObject jsonObject = jsonarray.getJSONObject(0);
		JSONArray array = jsonObject.getJSONArray("exampaperlist");
		return formList(array);
	}

	public static List<DoneExamPaper> formList_GetFavorites(JSONArray array)
			throws JSONException {
		List<DoneExamPaper> mtempList = new ArrayList<DoneExamPaper>();
		if (array != null && array.length() > 0) {
			for (int i = 0; i < array.length(); i++) {
				JSONObject obj = array.getJSONObject(i);
				DoneExamPaper detail = new DoneExamPaper(obj,"GetFavorites");
				mtempList.add(detail);
			}
			return mtempList;
		}
		return null;
	}
	
	@Override
	public String toString() {
		return "DoneExamPaper [type=" + type + ", score=" + score
				+ ", clientScore=" + clientScore + ", subjectid=" + subjectid
				+ ", name=" + name + ", kclassName=" + kclassName
				+ ", kclassid=" + kclassid + ", adddate=" + adddate + "]";
	}

}
