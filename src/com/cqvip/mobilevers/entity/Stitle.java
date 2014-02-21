package com.cqvip.mobilevers.entity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Stitle {

	private String title;
	private boolean iscontainpic;
	private String[] imgs;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public boolean isIscontainimg() {
		return iscontainpic;
	}
	public void setIscontainimg(boolean iscontainimg) {
		this.iscontainpic = iscontainimg;
	}
	public String[] getImgs() {
		return imgs;
	}
	public void setImgs(String[] imgs) {
		this.imgs = imgs;
	}
	public Stitle(String title, boolean iscontainimg, String[] imgs) {
		super();
		this.title = title;
		this.iscontainpic = iscontainimg;
		this.imgs = imgs;
	}
	
	public Stitle(String data) throws JSONException{
			JSONObject json = new JSONObject(data);
			title = json.getString("title");
			if(!json.isNull("iscontainimg")){
				iscontainpic = json.getBoolean("iscontainpic");
				JSONArray array = json.getJSONArray("imgs");
				if(array.length()>0){
					for(int i=0;i<array.length();i++){
						imgs[i]=array.get(i).toString();
					}
				}
			}
	}
}
