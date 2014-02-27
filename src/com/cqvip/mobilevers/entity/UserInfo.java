package com.cqvip.mobilevers.entity;

import org.json.JSONException;
import org.json.JSONObject;

public class UserInfo {

	private String userid;
	private String username;
	private String access;
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getAccess() {
		return access;
	}
	public void setAccess(String access) {
		this.access = access;
	}
		
	public  UserInfo(JSONObject json)throws JSONException{
		access = json.getString("code");
		JSONObject user = json.getJSONObject("userinfo");
		userid = user.getString("userid");
		username = user.getString("name");
	}
}
