package com.cqvip.mobilevers.entity;

import org.json.JSONException;
import org.json.JSONObject;

/**返回错误
 * @author luojiang
 *
 */
public class ErrorResult {

	private String error;
	private int errorcode;
	private String request;
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public int getErrorcode() {
		return errorcode;
	}
	public void setErrorcode(int errorcode) {
		this.errorcode = errorcode;
	}
	public String getRequest() {
		return request;
	}
	public void setRequest(String request) {
		this.request = request;
	}
	
	public ErrorResult(JSONObject json)throws JSONException{
		request = json.getString("request");
		errorcode = json.getInt("ererocode");
		error = json.getString("error");
		
	}
}
