package com.cqvip.mobilevers.http;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class StreamUtil {
	public static byte[] getbytes(InputStream is){
		try{
			ByteArrayOutputStream out=new ByteArrayOutputStream();
			byte[] arr=new byte[1024];
			int len=-1;
			while((len=is.read(arr))!=-1){
				out.write(arr,0,len);
			}
			out.close();
			is.close();
			return out.toByteArray();
		}catch(Exception e){
			
		}
		
		return null;
	}
}
