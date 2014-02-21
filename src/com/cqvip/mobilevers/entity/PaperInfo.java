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
 * @author luojiang
 *
 */
public class PaperInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5597714058808808547L;
	private String adddate;
	private String itemcount;
	private String desc;
	private String pulishyear;
	private int score;
	private long size;
	private String subjectid;
	private String spenttime;
	private String titile;

	public String getAdddate() {
		return adddate;
	}

	public void setAdddate(String adddate) {
		this.adddate = adddate;
	}

	public String getItemcount() {
		return itemcount;
	}

	public void setItemcount(String itemcount) {
		this.itemcount = itemcount;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getPulishyear() {
		return pulishyear;
	}

	public void setPulishyear(String pulishyear) {
		this.pulishyear = pulishyear;
	}



	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public String getSubjectid() {
		return subjectid;
	}

	public void setSubjectid(String subjectid) {
		this.subjectid = subjectid;
	}

	public String getSpenttime() {
		return spenttime;
	}

	public void setSpenttime(String spenttime) {
		this.spenttime = spenttime;
	}

	public String getTitile() {
		return titile;
	}

	public void setTitile(String titile) {
		this.titile = titile;
	}

	@Override
	public String toString() {
		return "PaperInfo [adddate=" + adddate + ", itemcount=" + itemcount
				+ ", desc=" + desc + ", pulishyear=" + pulishyear + ", score="
				+ score + ", size=" + size + ", subjectid=" + subjectid
				+ ", spenttime=" + spenttime + ", titile=" + titile + "]";
	}
	

	public  PaperInfo(String data)throws JSONException{
		JSONObject json = new JSONObject(data);
		titile = json.getString("titile");
		itemcount = json.getString("itemcount");
		desc = json.getString("desc");
		pulishyear = json.getString("pulishyear");
		score = getInt("score",json);
		size = getLong("size",json);
		subjectid = json.getString("subjectid");
		spenttime = json.getString("spenttime");
		adddate = json.getString("adddate");
	}
	public  PaperInfo(JSONObject json)throws JSONException{
		titile = json.getString("titile");
		itemcount = json.getString("itemcount");
		desc = json.getString("desc");
		pulishyear = json.getString("pulishyear");
		score = getInt("score",json);
		size = getLong("size",json);
		subjectid = json.getString("subjectid");
		spenttime = json.getString("spenttime");
		adddate = json.getString("adddate");
	}
	
	 protected static int getInt(String key, JSONObject json) throws JSONException {
	        String str = json.getString(key);
	        if(null == str || "".equals(str)||"null".equals(str)){
	            return 0;
	        }
	        return Integer.parseInt(str);
	    }
	 protected static long getLong(String key, JSONObject json) throws JSONException {
	        String str = json.getString(key);
	        if(null == str || "".equals(str)||"null".equals(str)){
	            return 0;
	        }
	        return Long.parseLong(str);
	    }
	
	
	public static List<PaperInfo> formList(JSONArray array)throws JSONException {
		List<PaperInfo> mtempList = new ArrayList<PaperInfo>();
			if(array!=null&&array.length()>0){
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
