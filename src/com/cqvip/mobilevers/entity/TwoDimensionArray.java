package com.cqvip.mobilevers.entity;

import java.io.Serializable;
import java.util.ArrayList;

import android.util.SparseArray;

import com.cqvip.mobilevers.exam.SimpleAnswer;

public class TwoDimensionArray implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1093167893231613401L;
	private int[][] allss;//全部
	private int[][] rightss;//正确
	private int[][] wrongss;//错误
	private SparseArray<ArrayList<SimpleAnswer>> clientAnswers;
	public int[][] getAllss() {
		return allss;
	}
	public int[][] getRightss() {
		return rightss;
	}
	public int[][] getWrongss() {
		return wrongss;
	}
	public SparseArray<ArrayList<SimpleAnswer>> getClientAnswers() {
		return clientAnswers;
	}
	public TwoDimensionArray(int[][] allss, int[][] rightss, int[][] wrongss
			) {
		super();
		this.allss = allss;
		this.rightss = rightss;
		this.wrongss = wrongss;
	}
	public TwoDimensionArray(int[][] allss, int[][] rightss, int[][] wrongss,
			SparseArray<ArrayList<SimpleAnswer>> clientAnswers) {
		super();
		this.allss = allss;
		this.rightss = rightss;
		this.wrongss = wrongss;
		this.clientAnswers = clientAnswers;
	}
	
	



	
}
