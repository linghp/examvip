package com.cqvip.mobilevers.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

	public static String formatYMD(String str) throws ParseException{
		  DateFormat fmt =new SimpleDateFormat("yyyy-MM-dd");            
          Date date;
		date = fmt.parse(str);
		return fmt.format(date);
	}
}
