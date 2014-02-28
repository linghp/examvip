package com.cqvip.mobilevers.utils;

import java.io.IOException;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.content.Context;
import android.util.Xml;

public class PullParseXML {
	private static final String ns = null;

	private Context context;

	public PullParseXML(Context context) {
		this.context = context;
	}

	public void parsXml(String xml) {
		XmlPullParser xmlParse = Xml.newPullParser();

		int eventType;
		try {
			eventType = xmlParse.getEventType();
			while (eventType != XmlPullParser.END_DOCUMENT) {
				switch (eventType) {
				case XmlPullParser.START_TAG:

					String tag = xmlParse.getName();
					if ("subject".equalsIgnoreCase(tag)) {

						String showType = xmlParse.getAttributeValue(null,
								"type");
						 

						if (showType == SubjectType.ShowStyle.SSS_SINGLE_SEL_MULTIRIGHTITEM
								.toString())
							showType = SubjectType.ShowStyle.SSS_SINGLE_SEL
									.toString();

						if (showType == SubjectType.ShowStyle.SSS_SINGLE_SEL
								.toString()
								|| showType == SubjectType.ShowStyle.SSS_MULTI_SEL
										.toString()
								|| showType == SubjectType.ShowStyle.SSS_SINGLE_MULTI_SEL
										.toString()
								|| showType == SubjectType.ShowStyle.SSS_FILL
										.toString()
								|| showType == SubjectType.ShowStyle.SSS_JUDGEMENT
										.toString()
								|| showType == SubjectType.ShowStyle.SSS_TEXT_QUSTION
										.toString()
								|| showType == SubjectType.ShowStyle.SSS_SIMPLE_SINGLE_SEL
										.toString()
								|| showType == SubjectType.ShowStyle.SSS_SIMPLE_MULTI_SEL
										.toString()
								|| showType == SubjectType.ShowStyle.SSS_SIMPLE_SINGLE_MULTI_SEL
										.toString()) {
							ShowSingleAnySubjectType(showType,xmlParse);
							// subScore, perQstScore, ref qstOrder, Response,
							// ef, isShowAnswer, isValidAnswer, isModifyScore,
							// isClientCalAnswer, ref ExamPaperGuideDiv,
							// userAnswer, isShowForm, userAnswerItems);
							System.out.println();
						} else if (showType == SubjectType.ShowStyle.SSS_SAMEITEMS_MULTI_SEL_COLL
								.toString()
								|| showType == SubjectType.ShowStyle.SSS_SAMEITEMS_SINGLE_MULTI_SEL_COLL
										.toString()
								|| showType == SubjectType.ShowStyle.SSS_SAMEITEMS_SINGLE_SEL_COLL
										.toString()) {

							System.out.println();
							ShowAnyQuestionCollSubject(showType,xmlParse);
						}else{
							
							ShowAnyQuestionCollSubject(showType,xmlParse);
						}
					}
					break;
				case XmlPullParser.END_TAG:
					break;
				default:
					break;
				}
				eventType = xmlParse.next();
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void ShowAnyQuestionCollSubject(String qstShowType,XmlPullParser xmlParse) {
		
		
		  if (qstShowType == SubjectType.ShowStyle.SSS_SINGLE_SEL.toString())
          {
                  ShowSingleSelQuestion(xmlParse);
          }
          else if (qstShowType == SubjectType.ShowStyle.SSS_MULTI_SEL.toString()
              || qstShowType == SubjectType.ShowStyle.SSS_SINGLE_MULTI_SEL.toString()
              )
          {
                  ShowMultiSelQuestion(xmlParse);
          }
          else if (qstShowType == SubjectType.ShowStyle.SSS_FILL.toString())
          {
                  ShowFillQuestion(xmlParse);
          }
          else if (qstShowType == SubjectType.ShowStyle.SSS_JUDGEMENT.toString())
          {
        	  ShowJudgementQuestion(xmlParse);                  
          }
          else if (qstShowType == SubjectType.ShowStyle.SSS_TEXT_QUSTION.toString())
          {
                  ShowTextQuestion(xmlParse);
          }
          else if(qstShowType == SubjectType.ShowStyle.SSS_SIMPLE_SINGLE_SEL.toString())
          {
        	  ShowSimpleSingleSelQuestion(xmlParse);
          }
          else if (qstShowType == SubjectType.ShowStyle.SSS_SIMPLE_MULTI_SEL.toString()
              || qstShowType == SubjectType.ShowStyle.SSS_SIMPLE_SINGLE_MULTI_SEL.toString())
          {
                  ShowSimpleMultiSelQuestion(xmlParse);
          }
		
		// TODO Auto-generated method stub
		
	}

	private void ShowSingleAnySubjectType(String showStyle,XmlPullParser xmlParse)throws XmlPullParserException, IOException  {

		
		int eventType;
			eventType = xmlParse.getEventType();
			while (eventType != XmlPullParser.END_DOCUMENT) {
				switch (eventType) {
				case XmlPullParser.START_TAG:

					String tag = xmlParse.getName();
					if ("question".equalsIgnoreCase(tag)) {
						showStyle = xmlParse.getAttributeValue(null,
								"type");
					}
					
					   if (showStyle == SubjectType.ShowStyle.SSS_SINGLE_SEL.toString())
		                {
		                    ShowSingleSelQuestion(xmlParse);
		                }
		                else if (showStyle == SubjectType.ShowStyle.SSS_MULTI_SEL.toString()
		                    || showStyle == SubjectType.ShowStyle.SSS_SINGLE_MULTI_SEL.toString())
		                {
		                    ShowMultiSelQuestion(xmlParse);
		                }
		                else if (showStyle == SubjectType.ShowStyle.SSS_FILL.toString())
		                {
		                    ShowFillQuestion(xmlParse);
		                }
		                else if (showStyle == SubjectType.ShowStyle.SSS_JUDGEMENT.toString())
		                {
		                    ShowJudgementQuestion(xmlParse);
		                }
		                else if (showStyle == SubjectType.ShowStyle.SSS_TEXT_QUSTION.toString())
		                {
		                    ShowTextQuestion(xmlParse);
		                }
		                else if (showStyle == SubjectType.ShowStyle.SSS_SIMPLE_SINGLE_SEL.toString())
		                {
		                    ShowSimpleSingleSelQuestion(xmlParse);
		                }
		                else if (showStyle == SubjectType.ShowStyle.SSS_SIMPLE_MULTI_SEL.toString()
		                    || showStyle == SubjectType.ShowStyle.SSS_SIMPLE_SINGLE_MULTI_SEL.toString())
		                {
		                    ShowSimpleMultiSelQuestion(xmlParse);
		                }
					
					

				
					break;
				case XmlPullParser.END_TAG: 
					break;
				default:
					break;
				}
				eventType = xmlParse.next();
			}
	}
	
	
	
	
	
	
	
	/**
	 * 解析简单单选
	 * @param xmlParse
	 */
	private void ShowSimpleSingleSelQuestion(XmlPullParser xmlParse) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * 解析多选
	 * @param xmlParse
	 */
	 private void ShowSimpleMultiSelQuestion(XmlPullParser xmlParse) {
		// TODO Auto-generated method stub
		
	}

	 /**
	  * 解析问答
	  * @param xmlParse
	  */
	private void ShowTextQuestion(XmlPullParser xmlParse) {
		// TODO Auto-generated method stub
		
	}
	/**
	 * 解析判断
	 * @param题 xmlParse
	 */
	private void ShowJudgementQuestion(XmlPullParser xmlParse) {
		// TODO Auto-generated method stub
		
	}
	/**
	 * 解析
	 * @param xmlParse
	 */
	private void ShowFillQuestion(XmlPullParser xmlParse) {
		// TODO Auto-generated method stub
		
	}
	/**
	 * 多选样式
	 * @param xmlParse
	 */
	private void ShowMultiSelQuestion(XmlPullParser xmlParse) {
		// TODO Auto-generated method stub
		
	}
	/**
	 * 单选样式
	 * @param xmlParse
	 */
	private void ShowSingleSelQuestion(XmlPullParser xmlParse) {
		// TODO Auto-generated method stub
		
	}

	// Processes title tags in the feed.
    private String readTitle(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "title");
        String title = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "title");
        return title;
    }

    // Processes link tags in the feed.
    private String readLink(XmlPullParser parser) throws IOException, XmlPullParserException {
        String link = "";
        parser.require(XmlPullParser.START_TAG, ns, "link");
        String tag = parser.getName();
        String relType = parser.getAttributeValue(null, "rel");
        if (tag.equals("link")) {
            if (relType.equals("alternate")) {
                link = parser.getAttributeValue(null, "href");
                parser.nextTag();
            }
        }
        parser.require(XmlPullParser.END_TAG, ns, "link");
        return link;
    }

    // Processes summary tags in the feed.
    private String readSummary(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "summary");
        String summary = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "summary");
        return summary;
    }
	
    // For the tags title and summary, extracts their text values.
    private String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        return result;
    }

    // Skips tags the parser isn't interested in. Uses depth to handle nested tags. i.e.,
    // if the next tag after a START_TAG isn't a matching END_TAG, it keeps going until it
    // finds the matching END_TAG (as indicated by the value of "depth" being 0).
    private void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
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
	

}
