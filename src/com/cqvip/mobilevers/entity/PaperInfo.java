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
public class PaperInfo implements Serializable {

	/**
	 * 
	 */

	private static final long serialVersionUID = -5597714058808808547L;
	private String subjectid;
	private String name;
	private String kclassName;
	private int type;
	private int score;
	private String pulishyear;
	private String adddate;

	public PaperInfo(JSONObject json) throws JSONException {
		subjectid = json.getString("id");
		name = json.getString("name");
		type = json.getInt("type");
		score = json.getInt("score");
		kclassName = json.getString("kclassname");
		pulishyear = json.getString("year");
		adddate = json.getString("createtime");
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

	public int getType() {
		return type;
	}

	public int getScore() {
		return score;
	}

	public String getPulishyear() {
		return pulishyear;
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

	@Override
	public String toString() {
		return "PaperInfo [subjectid=" + subjectid + ", name=" + name
				+ ", kclassName=" + kclassName + ", type=" + type + ", score="
				+ score + ", pulishyear=" + pulishyear + ", adddate=" + adddate
				+ "]";
	}

	/**
	 * 解析收藏列表
	 * 
	 * @param array
	 * @return
	 * @throws JSONException
	 */
	public static List<PaperInfo> formList(JSONArray array)
			throws JSONException {
		List<PaperInfo> mtempList = new ArrayList<PaperInfo>();
		if (array != null && array.length() > 0) {
			for (int i = 0; i < array.length(); i++) {
				JSONObject obj = array.getJSONObject(i);
				PaperInfo detail = new PaperInfo(obj);
				mtempList.add(detail);
			}
			return mtempList;
		}
		return null;
	}

}
