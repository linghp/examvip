package com.cqvip.mobilevers.entity;

import java.io.Serializable;

public class TwoDimensionArray implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1093167893231613401L;
	private int[][] allss;
	private int[][] rightss;
	private int[][] wrongss;
	public int[][] getAllss() {
		return allss;
	}
	public void setAllss(int[][] allss) {
		this.allss = allss;
	}
	public int[][] getRightss() {
		return rightss;
	}
	public void setRightss(int[][] rightss) {
		this.rightss = rightss;
	}
	public int[][] getWrongss() {
		return wrongss;
	}
	public void setWrongss(int[][] wrongss) {
		this.wrongss = wrongss;
	}


	
}
