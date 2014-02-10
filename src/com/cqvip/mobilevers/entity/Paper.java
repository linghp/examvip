package com.cqvip.mobilevers.entity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class Paper {
	
	private List<ExamInfo> real;
	private List<ExamInfo> simulate;
	public List<ExamInfo> getReal() {
		return real;
	}
	public void setReal(List<ExamInfo> real) {
		this.real = real;
	}
	public List<ExamInfo> getSimulate() {
		return simulate;
	}
	public void setSimulate(List<ExamInfo> simulate) {
		this.simulate = simulate;
	}

	public static Paper parserJsonData(String data){
		Paper p = new Paper();
		try{
			JSONObject js = new JSONObject(data);
			JSONArray array= js.getJSONArray("result");
			//真题
			JSONObject obj1=array.getJSONObject(0);
			p.setReal(parserData(obj1, "users1"));
			
			//模拟题
			JSONObject obj2=array.getJSONObject(1);
			p.setSimulate(parserData(obj2, "users2"));			
		
			return p;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	public static List<ExamInfo> parserData(JSONObject data,String tag){
		List<ExamInfo> mtempList=new ArrayList<ExamInfo>();
		try{
			
			JSONArray arrayList= data.getJSONArray(tag);
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
