package com.cqvip.mobilevers.entity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 机构
 * @author luojiang
 *
 */
public class Organization {
	private String organName;
	private int organCode;
	public String getOrganName() {
		return organName;
	}
	public int getOrganCode() {
		return organCode;
	}
	public Organization(JSONObject json) throws JSONException{
	organName =	json.getString("OrganizationName");
	organCode =	json.getInt("ID");
	}
	
	public static List<Organization> formList(JSONObject json)throws JSONException {
		JSONArray array = json.getJSONArray("organizationcode");
		List<Organization> mtempList = new ArrayList<Organization>();
		if (array != null && array.length() > 0) {
			for (int i = 0; i < array.length(); i++) {
				JSONObject obj = array.getJSONObject(i);
				Organization detail = new Organization(obj);
				mtempList.add(detail);
			}
			return mtempList;
		}
		return null;
	}
	@Override
	public String toString() {
		return "Organization [organName=" + organName + ", organCode="
				+ organCode + "]";
	}

	
}
