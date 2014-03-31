package com.cqvip.mobilevers.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import android.util.Log;

import com.cqvip.mobilevers.config.ConstantValues;

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
	 
		public static String formPicUrl(String time, String subId, String attachId, String orgName){
			long mTime = Long.parseLong(time.replace("/Date(", "").replace(")/", "").substring(0, 13));
			TimeZone zone=TimeZone.getTimeZone("GMT+0800");
			  Calendar c=Calendar.getInstance(zone);
			  c.setTimeInMillis(mTime);
			  Date date=c.getTime();
			  String year = date.getYear()+1900+"";
			  String moth = (date.getMonth() + 1 < 10 ? (date.getMonth() + 1) : date.getMonth() + 1)+"";
			  String day =  (date.getDate() < 10 ? date.getDate() : date.getDate()) +"";
			  String name = orgName.substring(orgName.lastIndexOf("."),orgName.length());
			  String resutl = ConstantValues.PICURL+"\\"+year+"\\"+moth+"\\"+day+"\\"+subId+"\\"+attachId+name;
			  Log.i("formPicUrl","result:"+resutl);
			return resutl;
		}
		
}
