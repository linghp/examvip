package com.cqvip.mobilevers.entity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Paper {
	
	private List<PaperInfo> real;
	private List<PaperInfo> simulate;
	public List<PaperInfo> getReal() {
		return real;
	}
	public void setReal(List<PaperInfo> real) {
		this.real = real;
	}
	public List<PaperInfo> getSimulate() {
		return simulate;
	}
	public void setSimulate(List<PaperInfo> simulate) {
		this.simulate = simulate;
	}

	public static Paper parserJsonData(String data){
		Paper p = new Paper();
		try{
			JSONObject js = new JSONObject(data);
			JSONArray array= js.getJSONArray("result");
			//真题
			JSONObject obj1=array.getJSONObject(0);
			p.setReal(parserData(obj1, "real"));
			
			//模拟题
			JSONObject obj2=array.getJSONObject(1);
			p.setSimulate(parserData(obj2, "simulate"));			
		
			return p;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	public static List<PaperInfo> parserData(JSONObject data,String tag)throws JSONException{
		List<PaperInfo> mtempList=new ArrayList<PaperInfo>();
			JSONArray arrayList= data.getJSONArray(tag);
			if(arrayList!=null&&arrayList.length()>0){
			mtempList = PaperInfo.formList(arrayList);
			return mtempList;
			}
		return null;
	}

}
