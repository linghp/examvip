package com.cqvip.mobilevers.entity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.toolbox.JsonObjectRequest;

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

	public static Paper parserJsonData(JSONObject js)throws JSONException{
		Paper p = new Paper();
			JSONArray array= js.getJSONArray("result");
			//真题
			if(array.length()==2){
			JSONObject obj1=array.getJSONObject(0);
			p.setReal(parserData(obj1));
			//模拟题
			JSONObject obj2=array.getJSONObject(1);
			p.setSimulate(parserData(obj2));			
			}else if(array.length()>0){
				JSONObject obj1=array.getJSONObject(0);
				p.setReal(parserData(obj1));
			}
			return p;
	}
	
	public static List<PaperInfo> parserData(JSONObject data)throws JSONException{
		List<PaperInfo> mtempList=new ArrayList<PaperInfo>();
			JSONArray arrayList= data.getJSONArray("exampaperlist");
			if(arrayList!=null&&arrayList.length()>0){
			mtempList = PaperInfo.formList(arrayList);
			return mtempList;
			}
		return null;
	}
	@Override
	public String toString() {
		return "Paper [real=" + real + ", simulate=" + simulate + "]";
	}
	
}
