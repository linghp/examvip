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

public class ImageTextView extends TextView{
	
	
	// 匹配图片[*]
		public static Pattern PIC_PATTERN = Pattern.compile(
				"\\[([\\*]+)\\]",
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

	public void setText(String text,boolean isContainpic,ArrayList<String> imgs) {
		// TODO Auto-generated method stub
		setOutlineText(this, text,isContainpic,imgs);;
	}
	
	public void setOutlineText(TextView textView, String text,boolean isContainpic,ArrayList<String> imgs) {
		//判断是否含有图片
		
		if(isContainpic){
			//含有图片
			//1、使用这则表达式替换图片
			
			SpannableString spannable = new SpannableString(text);
			SpannableStringBuilder style = new SpannableStringBuilder(spannable);
			
			Matcher picMatcher = PIC_PATTERN.matcher(text);
			// 匹配图片
			while (picMatcher.find()) {
				try {

					// Bitmap bitmap = BitmapFactory.decodeFile(InfoHelper
					// .getEmotionPath() + emotionMatcher.group());
					 Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.eg_book);
					// if (bitmap == null)
					// throw new NullPointerException();
					//
					 Drawable drawable = new BitmapDrawable(bitmap);
					//
					 drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight()); 
					 ImageSpan span = new ImageSpan(drawable,
					 ImageSpan.ALIGN_BASELINE);
					 style.setSpan(span, picMatcher.start(),
					 picMatcher.end(),Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			textView.setText(style);
		}else if(text!=null&&text.startsWith("{{*HTML*}}")){
			//是否是HTML格式的
			System.out.println("进来了");
			textView.setText(Html.fromHtml(text));
		}else{//不是html格式，不是图片
			textView.setText(text!=null?text:"");
		}
	}
	
	
	
}
