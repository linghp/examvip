package com.cqvip.mobilevers.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class StreamTools {

	/**
	 * ����ת����string
	 * 
	 * @param in
	 * @return
	 */
	public static String readInputStream(InputStream in) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int len = 0;
		byte[] buffer = new byte[1024];
		try {
			while ((len = in.read(buffer)) != -1) {
				baos.write(buffer,0,len);
			}
			
			byte[] result = baos.toByteArray();
			// return new String(result,"gbk");
			return new String(result);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (baos != null) {
				try {
					baos.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (in != null) {
					try {
						in.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
			return null;
	}
}
