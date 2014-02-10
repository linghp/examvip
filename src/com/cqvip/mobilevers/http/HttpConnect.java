package com.cqvip.mobilevers.http;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import com.cqvip.mobilevers.utils.StreamTools;

public class HttpConnect {
	public static String getNews(String url,List<? extends NameValuePair> parameters) {
		System.out.println("=======HttpConnect============"+url);
		StringBuilder sb = new StringBuilder();
		HttpClient client = new DefaultHttpClient();
		String path = null;
		try {
			path = url;
		HttpGet get = new HttpGet(path);

		HttpResponse  response = client.execute(get);
		StatusLine stateline =response.getStatusLine();
		if(stateline.getStatusCode()==200){
			InputStream in = response.getEntity().getContent();
			String str = StreamTools.readInputStream(in);
			System.out.println("reuslt"+str);
			return str;
		}else{
			return null;
		}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 获取图片资源
	 * @param url
	 * @return
	 */
	public static Bitmap getImage(String address,String filename){
		File file=new File(Environment.getExternalStorageDirectory()+"/cqvipversexam",filename);
		try{
			FileOutputStream out=new FileOutputStream(file);
			URL url=new URL(address);
			
			HttpURLConnection conn=(HttpURLConnection) url.openConnection();
			conn.setReadTimeout(5000);
			conn.setRequestMethod("GET");
			if(conn.getResponseCode()==200){
				InputStream is=conn.getInputStream();
				byte[]result=StreamUtil.getbytes(is);
				out.write(result);
				
				Bitmap bitmap=BitmapFactory.decodeStream(is);
				return bitmap;
			}
		}catch(Exception e){
			
		}
		
		return null;
	}
}
