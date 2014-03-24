package com.cqvip.mobilevers.widget;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.util.AttributeSet;
import android.widget.TextView;

import com.cqvip.mobilevers.R;
import com.cqvip.mobilevers.exam.Content;

public class ImageTextView extends TextView {

	// 匹配图片[*]
	public static Pattern PIC_PATTERN = Pattern.compile("\\[([\\*]+)\\]",
			Pattern.CASE_INSENSITIVE);

	public ImageTextView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public ImageTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public ImageTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public void setText(Content content) {
		// TODO Auto-generated method stub
		setOutlineText(this, content.getContent(), content.isContainPic(),
				content.getImg());
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

	public void setOutlineText(TextView textView, String text,
			boolean isContainpic, ArrayList<String> imgs) {
		// 判断是否含有图片

		if (isContainpic) {
			// 含有图片
			// 1、使用这则表达式替换图片

			SpannableString spannable = new SpannableString(text);
			SpannableStringBuilder style = new SpannableStringBuilder(spannable);

			Matcher picMatcher = PIC_PATTERN.matcher(text);
			// 匹配图片
			while (picMatcher.find()) {
				try {

					// Bitmap bitmap = BitmapFactory.decodeFile(InfoHelper
					// .getEmotionPath() + emotionMatcher.group());
					Bitmap bitmap = BitmapFactory.decodeResource(
							getResources(), R.drawable.eg_book);
					// if (bitmap == null)
					// throw new NullPointerException();
					//
					Drawable drawable = new BitmapDrawable(bitmap);
					//
					drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
							drawable.getIntrinsicHeight());
					ImageSpan span = new ImageSpan(drawable,
							ImageSpan.ALIGN_BASELINE);
					style.setSpan(span, picMatcher.start(), picMatcher.end(),
							Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			textView.setText(style);
		} else if (text != null && text.startsWith("{{*HTML*}}")) {
			// 是否是HTML格式的
			int length = text.length();
			String trimHtml = text.substring("{{*HTML*}}".length(),length);
			textView.setText(Html.fromHtml(trimHtml));
		} else {// 不是html格式，不是图片
			textView.setText(text != null ? text : "");
		}
	}
	public void setOutlineText(TextView textView, String text,
			boolean isContainpic, ArrayList<String> imgs,String tips) {
		// 判断是否含有图片
		
		if (isContainpic) {
			// 含有图片
			// 1、使用这则表达式替换图片
			text = tips+text;
			SpannableString spannable = new SpannableString(text);
			SpannableStringBuilder style = new SpannableStringBuilder(spannable);

			Matcher picMatcher = PIC_PATTERN.matcher(text);
			// 匹配图片
			while (picMatcher.find()) {
				try {

					// Bitmap bitmap = BitmapFactory.decodeFile(InfoHelper
					// .getEmotionPath() + emotionMatcher.group());
					Bitmap bitmap = BitmapFactory.decodeResource(
							getResources(), R.drawable.eg_book);
					// if (bitmap == null)
					// throw new NullPointerException();
					//
					Drawable drawable = new BitmapDrawable(bitmap);
					//
					drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
							drawable.getIntrinsicHeight());
					ImageSpan span = new ImageSpan(drawable,
							ImageSpan.ALIGN_BASELINE);
					style.setSpan(span, picMatcher.start(), picMatcher.end(),
							Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			textView.setText(style);
		} else if (text != null && text.startsWith("{{*HTML*}}")) {
			// 是否是HTML格式的
			int length = text.length();
			String trimHtml = text.substring("{{*HTML*}}".length(),length);
			textView.setText(tips+Html.fromHtml(trimHtml));
		} else {// 不是html格式，不是图片
			textView.setText(tips+(text != null ? text : ""));
		}
	}
//	public static class ImageSpanAsyncLoad {
//		
//		Context mContext;
//		Drawable mDrawable = null;//默认显示图片
//		final LruCache<String,Drawable> drawCache = new LruCache<String, Drawable>(DateUtil.getCachSize());
//		
//		public ImageSpanAsyncLoad(Context context) {
//			mContext = context.getApplicationContext();
//			Resources resources = mContext.getResources();
//			mDrawable = resources.getDrawable(R.drawable.eg_book);
//			mDrawable.setCallback(null);
//			mDrawable.setBounds(0,0,mDrawable.getIntrinsicHeight(),mDrawable.getIntrinsicWidth());
//		}
//		public void displayImage(String path,SpannableStringBuilder builder,TextView textView,int[] local){
//			Drawable drawable = drawCache.get(path);
//			boolean isNeedDown = false;
//			if(drawable == null){//缓存无图片
//				isNeedDown = true;
//				drawable = mDrawable;
//			}
//			ImageSpan imageSpan = new ImageSpan(drawable,ImageSpan.ALIGN_BASELINE);
//			SpannableString spannableString = new SpannableString(path);
//			spannableString.setSpan(imageSpan,local[0],local[1],Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//			builder.append(spannableString);
//			
//			if(isNeedDown){
//					new AsyncTask<Object,Void,Drawable>(){
//						
//						String mPath;//下载地址
//						SpannableStringBuilder builder;//需要更新的builder
//						WeakReference<TextView> weakReference;//textview对象
//						int[] indexs;
//						@Override
//						protected Drawable doInBackground(Object... params) {//http 执行下载
//							
//							mPath = (String)params[0];
//							builder = (SpannableStringBuilder)params[1];
//							weakReference = new WeakReference<TextView>((TextView)params[2]);
//							indexs = (int[])params[3];//imagespan 的索引位置
//							Drawable drawable = mContext.getResources().getDrawable(R.drawable.eg_book);
//							drawable.setBounds(0, 0, 50, 50);
//							drawable.setCallback(null);
//							if(drawable != null)drawCache.put(mPath,drawable);
//							
//							return drawable;
//						}
//						@Override
//						protected void onPostExecute(Drawable drawable) {//更新到ui
//							if(drawable == null)return;
//							TextView textView = weakReference.get();
//							//如果不为null与当前的textview对象是等于需要更新的textview对象则进行更新
//							if(textView != null ){
//								ImageSpan imageSpan = new ImageSpan(drawable);
//								SpannableString spannableString = new SpannableString(mPath);
//								spannableString.setSpan(imageSpan, 0, spannableString.length(),Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//								builder.replace(indexs[0],indexs[1],spannableString);//直接替换之前位置的imagespan
//								textView.setText(builder);
//							}
//						}
//					}.execute(path,builder,textView,local);
//			}
//		}
//}
}
