package com.cqvip.mobilevers.entity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.view.View;

public class PaperInfo {

	private String adddate;
	private String itemcount;
	private String desc;
	private String pulishyear;
	private String score;
	private String size;
	private String subjectid;
	private String spenttime;
	private String titile;

	public String getAdddate() {
		return adddate;
	}

	public void setAdddate(String adddate) {
		this.adddate = adddate;
	}

	public String getItemcount() {
		return itemcount;
	}

	public void setItemcount(String itemcount) {
		this.itemcount = itemcount;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getPulishyear() {
		return pulishyear;
	}

	public void setPulishyear(String pulishyear) {
		this.pulishyear = pulishyear;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getSubjectid() {
		return subjectid;
	}

	public void setSubjectid(String subjectid) {
		this.subjectid = subjectid;
	}

	public String getSpenttime() {
		return spenttime;
	}

	public void setSpenttime(String spenttime) {
		this.spenttime = spenttime;
	}

	public String getTitile() {
		return titile;
	}

	public void setTitile(String titile) {
		this.titile = titile;
	}

	@Override
	public String toString() {
		return "PaperInfo [adddate=" + adddate + ", itemcount=" + itemcount
				+ ", desc=" + desc + ", pulishyear=" + pulishyear + ", score="
				+ score + ", size=" + size + ", subjectid=" + subjectid
				+ ", spenttime=" + spenttime + ", titile=" + titile + "]";
	}

//	public static List<PaperInfo> parserJsonData(String data) {
//		List<PaperInfo> mtempList = new ArrayList<PaperInfo>();
//		try {
//			JSONObject js = new JSONObject(data);
//			JSONArray arrayList = js.getJSONArray("users");
//			for (int i = 0; i < arrayList.length(); i++) {
//				JSONObject obj = arrayList.getJSONObject(i);
//				PaperInfo detail = new PaperInfo();
//				detail.setId(obj.getString("id"));
//				detail.setTitle(obj.getString("title"));
//				detail.setCount(obj.getString("count"));
//
//				mtempList.add(detail);
//			}
//			// System.out.println(mtempList.size());
//			// System.out.println(mtempList.toString());
//			return mtempList;
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return null;
//	}

}
