package com.cqvip.mobilevers.entity;

import java.io.Serializable;
import java.util.Arrays;

import com.cqvip.mobilevers.exam.SeriSqareArray;
import com.cqvip.mobilevers.exam.SimpleAnswer;

public class TwoDimensionArray implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6279262223081946978L;
	private int[][] allss;//全部
	private int[][] doness;//做过的
	private int[][] rightss;//正确
	private int[][] wrongss;//错误
	private SeriSqareArray<SimpleAnswer> clientAnswers;
	
	public int[][] getAllss() {
		return allss;
	}
	public int[][] getDoness() {
		return doness;
	}
	public int[][] getRightss() {
		return rightss;
	}
	public int[][] getWrongss() {
		return wrongss;
	}
	public SeriSqareArray<SimpleAnswer> getClientAnswers() {
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
			SeriSqareArray<SimpleAnswer> clientAnswers) {
		super();
		this.allss = allss;
		this.rightss = rightss;
		this.wrongss = wrongss;
		this.clientAnswers = clientAnswers;
	}
	public TwoDimensionArray(int[][] allss, int[][] doness, int[][] rightss,
			int[][] wrongss, SeriSqareArray<SimpleAnswer> clientAnswers) {
		super();
		this.allss = allss;
		this.doness = doness;
		this.rightss = rightss;
		this.wrongss = wrongss;
		this.clientAnswers = clientAnswers;
	}
	public TwoDimensionArray(int[][] allss, int[][] doness, int[][] rightss,
			int[][] wrongss) {
		super();
		this.allss = allss;
		this.doness = doness;
		this.rightss = rightss;
		this.wrongss = wrongss;
		
	}
	@Override
	public String toString() {
		return "TwoDimensionArray [allss=" + Arrays.toString(allss)
				+ ", doness=" + Arrays.toString(doness) + ", rightss="
				+ Arrays.toString(rightss) + ", wrongss="
				+ Arrays.toString(wrongss) + ", clientAnswers=" + clientAnswers
				+ "]";
	}
	
	



	
}
