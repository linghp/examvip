package com.cqvip.mobilevers.http;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import com.cqvip.mobilevers.config.ConstantValues;
import com.cqvip.mobilevers.utils.StreamTools;

public class HttpConnect {
	
	private static HttpClient client;
	private HttpConnect(){
		
	}
	/**
	 * 获取网络图片
	 * @param url
	 * @param parameters
	 * @return
	 */
	public static InputStream getImgformNet(String url,List<? extends NameValuePair> parameters) {
		HttpClient client = new DefaultHttpClient();
		String path = null;
		InputStream in = null;
		try {
		path = url;
		HttpGet get = new HttpGet(path);
		HttpResponse  response = client.execute(get);
		StatusLine stateline =response.getStatusLine();
		if(stateline.getStatusCode()==200){
			in = response.getEntity().getContent();
			return in;
		}else{
			return in;
		}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return null;
	}
	
	
	private static synchronized HttpClient getHttpclient() {
		if(client == null){
		HttpParams params = new BasicHttpParams();
		HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
		HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);
		SchemeRegistry registry = new SchemeRegistry();
		registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
		ClientConnectionManager ccm = new ThreadSafeClientConnManager(params, registry);
		HttpConnectionParams.setConnectionTimeout(params, ConstantValues.CONNECTION_TIMEOUT);
		HttpConnectionParams.setSoTimeout(params, ConstantValues.SOCKET_TIMEOUT);
		 client = new DefaultHttpClient(ccm, params);
		
		}
		return client;
		
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
