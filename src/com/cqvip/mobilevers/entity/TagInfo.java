package com.cqvip.mobilevers.entity;

import org.json.JSONException;
import org.json.JSONObject;

public class TagInfo {
	private String id;
	private String tag;
	
	public String getId() {
		return id;
	}
	public String getTag() {
		return tag;
	}
	@Override
	public String toString() {
		return "TagInfo [id=" + id + ", tag=" + tag + "]";
	}
	public TagInfo(String id, String tag) {
		super();
		this.id = id;
		this.tag = tag;
	}
	public  TagInfo(JSONObject json)throws JSONException{
		id = json.getString("ID");
		tag = json.getString("Name");
	}
	
}
