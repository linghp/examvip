package com.cqvip.mobilevers.widget;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.cqvip.mobilevers.R;
import com.cqvip.mobilevers.exam.Content;
import com.cqvip.mobilevers.http.HttpConnect;
import com.cqvip.mobilevers.ui.MainActivity;

import android.R.integer;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.util.AttributeSet;
import android.widget.CheckBox;
import android.widget.TextView;

public class ImageTextCheckBox  extends CheckBox{
	// 匹配图片[*]
		public static Pattern PIC_PATTERN = Pattern.compile("\\[([\\*]+)\\]",
				Pattern.CASE_INSENSITIVE);

		public ImageTextCheckBox(Context context) {
			super(context);
			// TODO Auto-generated constructor stub
		}
		public ImageTextCheckBox(Context context, AttributeSet attrs) {
			super(context, attrs);
			// TODO Auto-generated constructor stub
		}	
	public ImageTextCheckBox(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public void setText(String pretext,Content content) {
		// TODO Auto-generated method stub
		setOutlineText(this, content.getContent(), content.isContainPic(),
				content.getImg(),pretext);
	}

	public void setText(String text, boolean isContainpic,
			ArrayList<String> imgs) {
		// TODO Auto-generated method stub
		setOutlineText(this, text, isContainpic, imgs);
	}
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		
	}
	
	
	public void setOutlineText(TextView textView, String text,
			boolean isContainpic, ArrayList<String> imgs) {
		// 判断是否含有图片
		// 含有图片
		if (isContainpic) {
			SpannableString spannable;
			if (text != null && text.startsWith("{{*HTML*}}")) {
				int length = text.length();
				String trimHtml = text.substring("{{*HTML*}}".length(), length);
				Spanned picText = Html.fromHtml(trimHtml);
				spannable = checkPic(picText+"",imgs);
			} else {
				spannable = checkPic(text,imgs);
			}
			// 1、使用这则表达式替换图片
			SpannableStringBuilder style = new SpannableStringBuilder(spannable);
			Matcher picMatcher = PIC_PATTERN.matcher(spannable);
			// 匹配图片
			int i = 0;
			int j = 0;
			while (picMatcher.find()) {
				int start = picMatcher.start();
				int end = picMatcher.end();
				try {
					int[] array = new int[] { start, end };
					new AsyncTask<Object, Void, Drawable>() {

						String path;
						SpannableStringBuilder builder;
						TextView textView1;
						int[] indexs;

						@Override
						protected Drawable doInBackground(Object... params) {
							path = (String) params[0];
							builder = (SpannableStringBuilder) params[1];
							textView1 = (TextView) params[2];
							indexs = (int[]) params[3];

							Bitmap bitmap = null;
							InputStream is = null;
							if(MainActivity.imgCache.getBitmapFromMemCache(path)!=null){
								bitmap = MainActivity.imgCache.getBitmapFromMemCache(path).getBitmap();
							}else if(MainActivity.imgCache.getBitmapFromDiskCache(path)!=null){
								bitmap = MainActivity.imgCache.getBitmapFromDiskCache(path);
							}else{//从网络获取
							is = HttpConnect.getImgformNet(path, null);
							if(is!=null){
							bitmap = BitmapFactory.decodeStream(is);
							}
							}
							return new BitmapDrawable(bitmap);
						}

						@Override
						protected void onPostExecute(Drawable result) {
							super.onPostExecute(result);
							if (result == null) {
								Bitmap bitmap = BitmapFactory.decodeResource(
										getResources(), R.drawable.eg_fail);
								result = new BitmapDrawable(bitmap);
							}else{
								MainActivity.imgCache.addBitmapToCache(path,  ((BitmapDrawable)result));
							}
							result.setBounds(0, 0,
									result.getIntrinsicWidth() * 2,
									result.getIntrinsicHeight() * 2);
							ImageSpan span2 = new ImageSpan(result,
									ImageSpan.ALIGN_BASELINE);
							SpannableString spannable2 = new SpannableString(
									"aaa");
							spannable2.setSpan(span2, 0, spannable2.length(),
									Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
							builder.replace(indexs[0], indexs[1], spannable2);
							textView1.setText(builder);
						}
					}.execute(imgs.get(i++), style, textView, array);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} else if (text != null && text.startsWith("{{*HTML*}}")) {
			// 是否是HTML格式的
			int length = text.length();
			String trimHtml = text.substring("{{*HTML*}}".length(), length);
			textView.setText(Html.fromHtml(trimHtml));
		} else {// 不是html格式，不是图片
			textView.setText(text != null ? text : "");
		}
	}

	public void setOutlineText(TextView textView, String text,
			boolean isContainpic, ArrayList<String> imgs, String tips) {
		// 判断是否含有图片

		if (isContainpic) {
			// 含有图片
			SpannableString spannable;
			if (text != null && text.startsWith("{{*HTML*}}")) {
				int length = text.length();
				String trimHtml = text.substring("{{*HTML*}}".length(), length);
				Spanned picText = Html.fromHtml(trimHtml);
				spannable =	checkPic(tips + picText,imgs);
			} else {
				text = tips + text;
				spannable =	checkPic(text,imgs);
			}
			SpannableStringBuilder style = new SpannableStringBuilder(spannable);

			Matcher picMatcher = PIC_PATTERN.matcher(spannable);
			
		
			// 匹配图片
			int i = 0;
			int j = 0;
			while (picMatcher.find()) {
				int start = picMatcher.start();
				int end = picMatcher.end();
				try {
					int[] array = new int[] { start, end };
					new AsyncTask<Object, Void, Drawable>() {

						String path;
						SpannableStringBuilder builder;
						TextView textView1;
						int[] indexs;

						@Override
						protected Drawable doInBackground(Object... params) {
							path = (String) params[0];
							builder = (SpannableStringBuilder) params[1];
							textView1 = (TextView) params[2];
							indexs = (int[]) params[3];

							Bitmap bitmap = null;
							InputStream is = null;
							if(MainActivity.imgCache.getBitmapFromMemCache(path)!=null){
								bitmap = MainActivity.imgCache.getBitmapFromMemCache(path).getBitmap();
							}else if(MainActivity.imgCache.getBitmapFromDiskCache(path)!=null){
								bitmap = MainActivity.imgCache.getBitmapFromDiskCache(path);
							}else{//从网络获取
							is = HttpConnect.getImgformNet(path, null);
							if(is!=null){
							bitmap = BitmapFactory.decodeStream(is);
							}
							}
							return new BitmapDrawable(bitmap);
						}

						@Override
						protected void onPostExecute(Drawable result) {
							super.onPostExecute(result);
							if (result == null) {
								Bitmap bitmap = BitmapFactory.decodeResource(
										getResources(), R.drawable.eg_fail);
								result = new BitmapDrawable(bitmap);
							}else{
								MainActivity.imgCache.addBitmapToCache(path,  ((BitmapDrawable)result));
							}
							result.setBounds(0, 0,
									result.getIntrinsicWidth() * 2,
									result.getIntrinsicHeight() * 2);
							ImageSpan span2 = new ImageSpan(result,
									ImageSpan.ALIGN_BASELINE);
							SpannableString spannable2 = new SpannableString(
									"aaa");
							spannable2.setSpan(span2, 0, spannable2.length(),
									Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
							builder.replace(indexs[0], indexs[1], spannable2);
							textView1.setText(builder);
						}

					}.execute(imgs.get(i++), style, textView, array);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} else if (text != null && text.startsWith("{{*HTML*}}")) {
			// 是否是HTML格式的
			int length = text.length();
			String trimHtml = text.substring("{{*HTML*}}".length(), length);
			textView.setText(tips + Html.fromHtml(trimHtml));
		} else {// 不是html格式，不是图片
			textView.setText(tips + (text != null ? text : ""));
		}
	}

	private SpannableString checkPic(String picText, ArrayList<String> imgs) {
		Matcher picMatcher = PIC_PATTERN.matcher(picText);
		if(!picMatcher.find()){
				StringBuilder builder = new StringBuilder(picText);
				for(int i=0;i<imgs.size();i++){
					builder.append("[*]");
				}
				return new SpannableString(builder.toString());
			}else{
				return new SpannableString(picText);
			}
		
	}
	
	
	
}
