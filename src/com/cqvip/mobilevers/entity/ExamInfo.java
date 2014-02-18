package com.cqvip.mobilevers.entity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.view.View;

public class ExamInfo {
	
	private String id;
	private String title;
	private  String count;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}
	@Override
	public String toString() {
		return "ExamInfo [id=" + id + ", title=" + title + ", count=" + count
				+ "]";
	}
	
	

	public static List<ExamInfo> parserJsonData(String data){
		List<ExamInfo> mtempList=new ArrayList<ExamInfo>();
		try{
			JSONObject js = new JSONObject(data);
			JSONArray arrayList= js.getJSONArray("users");
			for(int i=0;i<arrayList.length();i++){
				JSONObject obj=arrayList.getJSONObject(i);
				ExamInfo detail=new ExamInfo();
				detail.setId(obj.getString("id"));
				detail.setTitle(obj.getString("title"));
				detail.setCount(obj.getString("count"));
				
				mtempList.add(detail);
			}
			//System.out.println(mtempList.size());
			//System.out.println(mtempList.toString());
			return mtempList;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	
}
