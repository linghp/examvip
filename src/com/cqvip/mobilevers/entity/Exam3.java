package com.cqvip.mobilevers.entity;

import org.json.JSONException;
import org.json.JSONObject;

public class Exam3 {

	String title;
	
	public Exam3(JSONObject json)throws JSONException{
		String  xml = json.getString("_subXmlString");
		title = xml;
		
		
		
		
		
	}
	
	//解析xml
	public Choice parSubXml(String xml){
		
		
		
		
		
		return null;
	}
	

	
	
	
	
}
