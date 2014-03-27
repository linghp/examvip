package com.cqvip.mobilevers.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DateUtil {

	public static String formatYMD(String str) throws ParseException{
		  DateFormat fmt =new SimpleDateFormat("yyyy-MM-dd");            
          Date date;
		date = fmt.parse(str);
		return fmt.format(date);
	}
	
	 public static int getCachSize(){
		 int size = (int) Math.round(0.125 * Runtime.getRuntime().maxMemory());
		 return size;
	 }
	 
	 
	 /**
		 * 构造二维数组，供答题卡显示
		 * @param list //所有题目（8,10,20,4,5）
		 * @return
		 */
		public static  int[][] initDoubleDimensionalData(ArrayList<Integer> lists) {
			int a=0;
			int b=0;
			int[][] mlists = new int[lists.size()][];
			for (int i = 0; i < lists.size(); i++) {
				a = lists.get(i);
				mlists[i] = new int[a];
				for (int j = 1; j <= a; j++) {
					mlists[i][j-1] = j+b;
				}
				b += a;
			}
			return mlists;
		}
	 
}
