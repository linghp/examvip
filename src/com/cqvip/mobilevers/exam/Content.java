package com.cqvip.mobilevers.exam;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *  显示内容，要么是文字，要么是图片，多张图片
 * @author luojiang
 *
 */
public class Content implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -9005569241465663917L;
	ArrayList<String> img; //图片，可能包含多张
	String content;//内容
	boolean isContainPic;//是否包含图片
	
	public ArrayList<String> getImg() {
		return img;
	}
	public String getContent() {
		return content;
	}
	public boolean isContainPic() {
		return isContainPic;
	}
	public Content(ArrayList<String> img, String content, boolean isContainPic) {
		super();
		this.img = img;
		this.content = content;
		this.isContainPic = isContainPic;
	}
	@Override
	public String toString() {
		return "Content [img=" + img + ", content=" + content
				+ ", isContainPic=" + isContainPic + "]";
	}
}
