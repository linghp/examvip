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
	private String subjectid;
	private String name;
	private String className;
	private String adddate;
	private String pulishyear;

	public String getSubjectid() {
		return subjectid;
	}
	public void setSubjectid(String subjectid) {
		this.subjectid = subjectid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAdddate() {
		return adddate;
	}
	public void setAdddate(String adddate) {
		this.adddate = adddate;
	}
	public String getPulishyear() {
		return pulishyear;
	}
	public void setPulishyear(String pulishyear) {
		this.pulishyear = pulishyear;
	}
	
	@Override
	public String toString() {
		return "PaperInfo [subjectid=" + subjectid + ", name=" + name
				+ ", className=" + className + ", adddate=" + adddate
				+ ", pulishyear=" + pulishyear + "]";
	}
	public  PaperInfo(JSONObject json)throws JSONException{
		name = json.getString("name");
		className = json.getString("kclassname");
		pulishyear = json.getString("year");
		subjectid = json.getString("id");
		adddate = json.getString("createtime");	}
	
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
