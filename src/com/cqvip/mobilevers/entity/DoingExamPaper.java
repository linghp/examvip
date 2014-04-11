package com.cqvip.mobilevers.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * 用户做题的情况，对，错，用时，得分
 * @author luojiang
 *
 */
public class DoingExamPaper implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2602450706833938856L;
	/**
	 * 
	 */
	private String id;
	private String userid;
	private String exampaperid;
	private String exampapername;
	private int kclassid;
	private String createtime;
	
	
    public DoingExamPaper(JSONObject json) throws JSONException {
		id = json.getString("id").trim();
		userid = json.getString("userid").trim();
		exampaperid = json.getString("exampaperid").trim();
		exampapername = json.getString("exampapername").trim();
		kclassid = json.getInt("kclassid");
		createtime = json.getString("createtime").trim();
   	}
    
    
    	   
    /**
     * 	   
     * @param array
     * @return
     * @throws JSONException
     */
    public static List<DoingExamPaper> formList(JSONArray array)
			throws JSONException {
		List<DoingExamPaper> mtempList = new ArrayList<DoingExamPaper>();
		if (array != null && array.length() > 0) {
			for (int i = 0; i < array.length(); i++) {
				JSONObject obj = array.getJSONObject(i);
				DoingExamPaper detail = new DoingExamPaper(obj);
				mtempList.add(detail);
			}
			return mtempList;
		}
		return null;
	}



	public String getId() {
		return id;
	}



	public String getUserid() {
		return userid;
	}



	public String getExampaperid() {
		return exampaperid;
	}



	public String getExampapername() {
		return exampapername;
	}



	public int getKclassid() {
		return kclassid;
	}



	public String getCreatetime() {
		return createtime;
	} 
	public static List<DoingExamPaper> formList(JSONObject json)
			throws JSONException {
		JSONArray jsonarray = json.getJSONArray("result");
		JSONObject jsonObject = jsonarray.getJSONObject(0);
		JSONArray array = jsonObject.getJSONArray("exampaperlist");
		return formList(array);
	}



	@Override
	public String toString() {
		return "DoingExamPaper [id=" + id + ", userid=" + userid
				+ ", exampaperid=" + exampaperid + ", exampapername="
				+ exampapername + ", kclassid=" + kclassid + ", createtime="
				+ createtime + "]";
	}
	
}
