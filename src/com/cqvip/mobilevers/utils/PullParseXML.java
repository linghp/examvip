package com.cqvip.mobilevers.utils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.R.xml;
import android.text.TextUtils;
import android.util.Log;
import android.util.Xml;

import com.cqvip.mobilevers.exam.Content;
import com.cqvip.mobilevers.exam.Question;
import com.cqvip.mobilevers.exam.Solution;
import com.cqvip.mobilevers.exam.Subject;
/**
 * 解析返回问题里面的xml，参加xml接口描述文档
 * @author luojiang
 *
 */
public class PullParseXML {
	private static final String ns = null;
	public PullParseXML() {
		super();
	}
	public  Subject parseXml(String xml,String subjectTypeName,int scorePerQuestion) throws IOException, XmlPullParserException {
		Subject sub = null;
		XmlPullParser xmlParse = Xml.newPullParser();
		if(!TextUtils.isEmpty(xml)){
		InputStream in = new ByteArrayInputStream(xml.getBytes("utf-8"));
		xmlParse.setInput(in, "utf-8");
		xmlParse.nextTag();
		sub = ShowAllSubjectQuestion(xmlParse,subjectTypeName,scorePerQuestion);
		}
		return sub;
	}
	
	public Subject ShowAllSubjectQuestion(XmlPullParser xmlParse,String subjectTypeName,int scorePerQuestion)throws IOException, XmlPullParserException {
		String type = null;
		ArrayList<Question> questions = new ArrayList<Question>();
		ArrayList<Question> allquestion = new ArrayList<Question>();
		String title = null;;
		Question question = null;
		String count = null;
		ArrayList<String> pics = new ArrayList<String>();
		boolean isTitleContainPic = false;
		xmlParse.require(XmlPullParser.START_TAG, ns, "Subject");
		type = xmlParse.getAttributeValue(null, "type");
	    while (xmlParse.next() != XmlPullParser.END_TAG) {
	        if (xmlParse.getEventType() != XmlPullParser.START_TAG) {
	            continue;
	        }
	        String name = xmlParse.getName();
	        // Starts by looking for the entry tag
	        if (name.equals("Title")) {
	        	//读取标题
	        	title = readText(xmlParse);
	        }else if(name.equals("SelItemCount")){
	        	 count = readText(xmlParse);
	        }else if(name.equals("Attach")){
	        	//读取图片
	        	isTitleContainPic = true;
	        	String pic = readText(xmlParse);
	        	pics.add(pic);
	        } else if(name.equals("Question")){
	        	//读取问题
	        	if(count!=null){
	        	question = 	readQuestion(xmlParse,count);
	        	}else{
	        	question = readQuestion(xmlParse);
	        	}
	        	questions.add(question);
	        }else{
	        	skip(xmlParse);
	        }
	    }  
	    //设置子题目
	    Content sub_title = new Content(pics, title, isTitleContainPic);
	    //子类型
	    String sub_Type = type;
	    
	    for(int i=0;i<questions.size();i++){
	    	Question ques =questions.get(i);
	    	Question allqu = new Question(ques.getId(),ques.getType(), ques.getTitle(), ques.getOption(),
	    			ques.getSolution(), ques.getItemCount(), sub_title, subjectTypeName, sub_Type,scorePerQuestion);
	    	allquestion.add(allqu);
	    }
	    
		return new Subject(type,new Content(pics, title, isTitleContainPic),allquestion);
	}
	
	/**
	 * 读取Question
	 * @param xmlParse
	 * @return Question 
	 * @throws IOException
	 * @throws XmlPullParserException
	 */
    private Question readQuestion(XmlPullParser xmlParse) throws IOException, XmlPullParserException {
    	String type = null;
    	Solution s = null ;
    	String id = null;
    	xmlParse.require(XmlPullParser.START_TAG, ns, "Question");
    	type = xmlParse.getAttributeValue(null, "type");
    	id = xmlParse.getAttributeValue(null,"Id");
    	ArrayList<Content> lists = new ArrayList<Content>();
    	String title = null;
    	boolean isContainPic = false;
    	ArrayList<String> imgs = new ArrayList<String>();
    	Content content = null;
    	int itemCount = 0;
	    while (xmlParse.next() != XmlPullParser.END_TAG) {
	        if (xmlParse.getEventType() != XmlPullParser.START_TAG) {
	            continue;
	        }
	        String name = xmlParse.getName();
	        if (name.equals("Item")) {
	        	//读取item 内容
	        	Content item = readAnswer(xmlParse, "Item");
	        	lists.add(item);
	        }else if(name.equals("Content")){
	        	title = readText(xmlParse);
	        } else if(name.equals("SelItemCount")){
	        	String count = readText(xmlParse);
	        	itemCount = Integer.parseInt(count);
	        }else if(name.equals("Solution")){
	        	//读取解析
	        	s = readSolution(xmlParse);
	        }else if(name.equals("Attach")){
	        	isContainPic = true;
				String tpimg = readText(xmlParse);
				imgs.add(tpimg);
	        }else{
	        	skip(xmlParse);
	        }
	    } 
	    if(title!=null||!imgs.isEmpty()){
	    	content = new Content(imgs, title, isContainPic);
	    }
	    
	    return new Question(id,type,content,lists,s,itemCount);
    }
    /**
	 * 读取Question
	 * @param xmlParse
	 * @return Question 
	 * @throws IOException
	 * @throws XmlPullParserException
	 */
    private Question readQuestion(XmlPullParser xmlParse,String sub_count) throws IOException, XmlPullParserException {
    	String type = null;
    	Solution s = null ;
    	String id = null;
    	xmlParse.require(XmlPullParser.START_TAG, ns, "Question");
    	type = xmlParse.getAttributeValue(null, "type");
    	id = xmlParse.getAttributeValue(null,"Id");
    	ArrayList<Content> lists = new ArrayList<Content>();
    	String title = null;
    	boolean isContainPic = false;
    	ArrayList<String> imgs = new ArrayList<String>();
    	Content content = null;
    	int itemCount = 0;
	    while (xmlParse.next() != XmlPullParser.END_TAG) {
	        if (xmlParse.getEventType() != XmlPullParser.START_TAG) {
	            continue;
	        }
	        String name = xmlParse.getName();
	        if (name.equals("Item")) {
	        	//读取item 内容
	        	Content item = readAnswer(xmlParse, "Item");
	        	lists.add(item);
	        }else if(name.equals("Content")){
	        	title = readText(xmlParse);
	        } else if(name.equals("SelItemCount")){
	        	String count = readText(xmlParse);
	        	itemCount = Integer.parseInt(count);
	        }else if(name.equals("Solution")){
	        	//读取解析
	        	s = readSolution(xmlParse);
	        }else if(name.equals("Attach")){
	        	isContainPic = true;
				String tpimg = readText(xmlParse);
				imgs.add(tpimg);
	        }else{
	        	skip(xmlParse);
	        }
	    } 
	    if(title!=null||!imgs.isEmpty()){
	    	content = new Content(imgs, title, isContainPic);
	    }
	    if(itemCount == 0){
	    itemCount = Integer.parseInt(sub_count); 
	    }
	    return new Question(id,type,content,lists,s,itemCount);
    }
	/**
	 * 读取问题解析，答案和描述
	 * @param xmlParse
	 * @return Solution
	 * @throws IOException
	 * @throws XmlPullParserException
	 */
	private Solution readSolution(XmlPullParser xmlParse)throws IOException, XmlPullParserException {

		
		xmlParse.require(XmlPullParser.START_TAG, ns, "Solution");
		Content answer = null;
		Content answerDesc = null;
	    while (xmlParse.next() != XmlPullParser.END_TAG) {
	        if (xmlParse.getEventType() != XmlPullParser.START_TAG) {
	            continue;
	        }
	        String name = xmlParse.getName();
	        if (name.equals("Answer")) {
	        	//读取答案
	        	answer = readAnswer(xmlParse,"Answer");
	        } else if(name.equals("AnswerDesc")){
	        	//读取解析
	        	answerDesc = readAnswer(xmlParse,"AnswerDesc");
	        }else{
	        	skip(xmlParse);
	        }
	    }  
	    return new Solution(answer, answerDesc);
	   
    }
		

	/**
	 * 读取答案,如果含有attach的TAG则包含有图片
	 * @param xmlParse
	 * @param tag
	 * @return
	 * @throws IOException
	 * @throws XmlPullParserException
	 */
	private Content readAnswer(XmlPullParser xmlParse,String tag)throws IOException, XmlPullParserException {
		boolean isContainPic = false;
		ArrayList<String> imgs = new ArrayList<String>();
		String content = null;
		xmlParse.require(XmlPullParser.START_TAG, ns, tag);
		   while (xmlParse.next() != XmlPullParser.END_TAG) {
		        if (xmlParse.getEventType() != XmlPullParser.START_TAG) {
		            continue;
		        }
		        String name = xmlParse.getName();
		        if (name.equals("Content")) {
		        	//读取答案
		        	String tpcontent = readText(xmlParse);
					content = tpcontent;
		        } else if(name.equals("Attach")){
		        	//读取解析
		        	isContainPic = true;
					String tpimg = readText(xmlParse);
					imgs.add(tpimg);
		        }else{
		        	skip(xmlParse);
		        }
		    }  
	        return new Content(imgs, content, isContainPic) ;
	}


  /**
   * 读取Tag后面的内容
   * @param parser
   * @return
   * @throws IOException
   * @throws XmlPullParserException
   */
    private String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        return result;
    }

    /**
     * 跳过tag,真到遇到END_TAG
     * @param parser
     * @throws XmlPullParserException
     * @throws IOException
     */
    private void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;//计数器
        while (depth != 0) {
            switch (parser.next()) {
            case XmlPullParser.END_TAG:
                    depth--;
                    break;
            case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }
//	public void parsXml(String xml) {
//	XmlPullParser xmlParse = Xml.newPullParser();
//
//	int eventType;
//	try {
//		eventType = xmlParse.getEventType();
//		while (eventType != XmlPullParser.END_DOCUMENT) {
//			switch (eventType) {
//			case XmlPullParser.START_TAG:
//				String tag = xmlParse.getName();
//				if ("subject".equalsIgnoreCase(tag)) {
//					String showType = xmlParse.getAttributeValue(null,
//							"type");
//					if (showType == SubjectType.ShowStyle.SSS_SINGLE_SEL_MULTIRIGHTITEM
//							.toString())
//						showType = SubjectType.ShowStyle.SSS_SINGLE_SEL
//								.toString();
//
//					if (showType == SubjectType.ShowStyle.SSS_SINGLE_SEL
//							.toString()
//							|| showType == SubjectType.ShowStyle.SSS_MULTI_SEL
//									.toString()
//							|| showType == SubjectType.ShowStyle.SSS_SINGLE_MULTI_SEL
//									.toString()
//							|| showType == SubjectType.ShowStyle.SSS_FILL
//									.toString()
//							|| showType == SubjectType.ShowStyle.SSS_JUDGEMENT
//									.toString()
//							|| showType == SubjectType.ShowStyle.SSS_TEXT_QUSTION
//									.toString()
//							|| showType == SubjectType.ShowStyle.SSS_SIMPLE_SINGLE_SEL
//									.toString()
//							|| showType == SubjectType.ShowStyle.SSS_SIMPLE_MULTI_SEL
//									.toString()
//							|| showType == SubjectType.ShowStyle.SSS_SIMPLE_SINGLE_MULTI_SEL
//									.toString()) {
//						ShowSingleAnySubjectType(showType,xmlParse);
//						// subScore, perQstScore, ref qstOrder, Response,
//						// ef, isShowAnswer, isValidAnswer, isModifyScore,
//						// isClientCalAnswer, ref ExamPaperGuideDiv,
//						// userAnswer, isShowForm, userAnswerItems);
//						System.out.println();
//					} else if (showType == SubjectType.ShowStyle.SSS_SAMEITEMS_MULTI_SEL_COLL
//							.toString()
//							|| showType == SubjectType.ShowStyle.SSS_SAMEITEMS_SINGLE_MULTI_SEL_COLL
//									.toString()
//							|| showType == SubjectType.ShowStyle.SSS_SAMEITEMS_SINGLE_SEL_COLL
//									.toString()) {
//
//						System.out.println();
//						ShowAnyQuestionCollSubject(showType,xmlParse);
//					}else{
//						
//						ShowAnyQuestionCollSubject(showType,xmlParse);
//					}
//				}
//				break;
//			case XmlPullParser.END_TAG:
//				break;
//			default:
//				break;
//			}
//			eventType = xmlParse.next();
//		}
//
//	} catch (Exception e) {
//		e.printStackTrace();
//	}
//
//}

//private void ShowAnyQuestionCollSubject(String qstShowType,XmlPullParser xmlParse) throws IOException, XmlPullParserException {
//	  if (qstShowType == SubjectType.ShowStyle.SSS_SINGLE_SEL.toString())
//      {
//              ShowSingleSelQuestion(xmlParse);
//      }
//      else if (qstShowType == SubjectType.ShowStyle.SSS_MULTI_SEL.toString()
//          || qstShowType == SubjectType.ShowStyle.SSS_SINGLE_MULTI_SEL.toString()
//          )
//      {
//              ShowMultiSelQuestion(xmlParse);
//      }
//      else if (qstShowType == SubjectType.ShowStyle.SSS_FILL.toString())
//      {
//              ShowFillQuestion(xmlParse);
//      }
//      else if (qstShowType == SubjectType.ShowStyle.SSS_JUDGEMENT.toString())
//      {
//    	  ShowJudgementQuestion(xmlParse);                  
//      }
//      else if (qstShowType == SubjectType.ShowStyle.SSS_TEXT_QUSTION.toString())
//      {
//              ShowTextQuestion(xmlParse);
//      }
//      else if(qstShowType == SubjectType.ShowStyle.SSS_SIMPLE_SINGLE_SEL.toString())
//      {
//    	  ShowSimpleSingleSelQuestion(xmlParse);
//      }
//      else if (qstShowType == SubjectType.ShowStyle.SSS_SIMPLE_MULTI_SEL.toString()
//          || qstShowType == SubjectType.ShowStyle.SSS_SIMPLE_SINGLE_MULTI_SEL.toString())
//      {
//              ShowSimpleMultiSelQuestion(xmlParse);
//      }
//}
//private void ShowSingleAnySubjectType(String showStyle,XmlPullParser xmlParse)throws XmlPullParserException, IOException  {
//	int eventType;
//		eventType = xmlParse.getEventType();
//		while (eventType != XmlPullParser.END_DOCUMENT) {
//			switch (eventType) {
//			case XmlPullParser.START_TAG:
//
//				String tag = xmlParse.getName();
//				if ("question".equalsIgnoreCase(tag)) {
//					showStyle = xmlParse.getAttributeValue(null,
//							"type");
//				}
//				
//				   if (showStyle == SubjectType.ShowStyle.SSS_SINGLE_SEL.toString())
//	                {
//	                    ShowSingleSelQuestion(xmlParse);
//	                }
//	                else if (showStyle == SubjectType.ShowStyle.SSS_MULTI_SEL.toString()
//	                    || showStyle == SubjectType.ShowStyle.SSS_SINGLE_MULTI_SEL.toString())
//	                {
//	                    ShowMultiSelQuestion(xmlParse);
//	                }
//	                else if (showStyle == SubjectType.ShowStyle.SSS_FILL.toString())
//	                {
//	                    ShowFillQuestion(xmlParse);
//	                }
//	                else if (showStyle == SubjectType.ShowStyle.SSS_JUDGEMENT.toString())
//	                {
//	                    ShowJudgementQuestion(xmlParse);
//	                }
//	                else if (showStyle == SubjectType.ShowStyle.SSS_TEXT_QUSTION.toString())
//	                {
//	                    ShowTextQuestion(xmlParse);
//	                }
//	                else if (showStyle == SubjectType.ShowStyle.SSS_SIMPLE_SINGLE_SEL.toString())
//	                {
//	                    ShowSimpleSingleSelQuestion(xmlParse);
//	                }
//	                else if (showStyle == SubjectType.ShowStyle.SSS_SIMPLE_MULTI_SEL.toString()
//	                    || showStyle == SubjectType.ShowStyle.SSS_SIMPLE_SINGLE_MULTI_SEL.toString())
//	                {
//	                    ShowSimpleMultiSelQuestion(xmlParse);
//	                }
//				break;
//			case XmlPullParser.END_TAG: 
//				break;
//			default:
//				break;
//			}
//			eventType = xmlParse.next();
//		}
//}
    /**
	 * 解析单选
	 * @param xmlParse
	 */
	private void ShowSimpleSingleSelQuestion(XmlPullParser xmlParse) {
		
	}
	/**
	 * 解析多选
	 * @param xmlParse
	 */
	 private void ShowSimpleMultiSelQuestion(XmlPullParser xmlParse) {
		
	}

	 /**
	  * 解析问答
	  * @param xmlParse
	  */
	private void ShowTextQuestion(XmlPullParser xmlParse) {
		
	}
	/**
	 * 解析判断
	 * @param题 xmlParse
	 */
	private void ShowJudgementQuestion(XmlPullParser xmlParse) {
		
	}
	/**
	 * 解析
	 * @param xmlParse
	 */
	private void ShowFillQuestion(XmlPullParser xmlParse) {
		
	}
	/**
	 * 多选样式
	 * @param xmlParse
	 */
	private void ShowMultiSelQuestion(XmlPullParser xmlParse) {
		
	}
	/**
	 * 单选样式
	 * @param xmlParse
	 */
	private void ShowSingleSelQuestion(XmlPullParser xmlParse) {
		
	}
}
