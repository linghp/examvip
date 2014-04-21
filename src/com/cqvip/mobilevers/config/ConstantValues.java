package com.cqvip.mobilevers.config;


public class ConstantValues {
	public static final String aurl="http://192.168.20.214:8080/web/AServlet";
	public static final String burl="http://192.168.20.214:8080/web/BServlet";
	public static final String curl="http://192.168.20.214:8080/web/CServlet";
	public static final String durl="http://192.168.20.214:8080/web/DServlet";
	public static final String paperurl="http://192.168.20.214:8080/web/PServlet";
	
	public static final String EXAMTYPEURL="http://192.168.20.75:3000/Service1.asmx/GetKnowledgeClassList";
	public static final String EXAMPAPERID= "examPaperId";//试卷id
	
	public static final String SERVER_URL="http://192.168.20.75:3000/";
	public static final String LOGIN_ADDR="Service1.asmx/Login";
	public static final String LOGINOUT_ADDR="Service1.asmx/LoginOut";
	public static final String ADDFAVOREXAM_ADDR="";
	public static final String GETEXAMPAPER_ADDR="Service1.asmx/GetExamPaperList";
	public static final String GETEXAM_ADDR="Service1.asmx/GetExamPaperInfo";
	public static final String GetKnowledgeClassList_ADDR="Service1.asmx/GetKnowledgeClassList";
	public static final String GET_DETAIL_PAPERINFO="Service1.asmx/GetExamPaperBaseInfo";
	public static final String SEARCH_PAPERINFO="Service1.asmx/SearchExamPaperList";
	public static final String SAVEEXAMANSWER="Service1.asmx/SaveExamAnswer";
	public static final String GETPASTEXAMINFO="Service1.asmx/GetExamPaperAdnAnswerInfo";
	public static final String GETMYPASTEXAMLIST="Service1.asmx/GetExamScoreList";
	public static final String ADDFAVORITESEXAMPAPER="Service1.asmx/AddFavoritesExamPaper";
	public static final String DELETEFAVORITESEXAMPAPER="Service1.asmx/DeleteFavoritesExamPaper";
	public static final String GETFAVORITESEXAMPAPERLIST="Service1.asmx/GetFavoritesExamPaperList";
	public static final String GetUserCurrExamPaperList="Service1.asmx/GetUserCurrExamPaperList";
	public static final String DeleteUserTestScore="Service1.asmx/DeleteUserTestScore";
	
	
	public static final String DBNAME="MobileVers.db";
	
	
	public static final String PICURL="http://vers.cqvip.com/UI/AttachFilePick.aspx?path=/ExamPaperRes";
	
	
	public static final String HTMLTAG="{{*HTML*}}";
	
	
	public static final int TYPE_EXAM_REAL=2;// 真题
	public static final int TYPE_EXAM_SIMULATE=1;//模拟
	
	public static final int GETFIRSTPAGE = 1;//上一页
	public static final int GETNEXTPAGE = 2;//下一页
	public static final int DEFAULYPAGESIZE = 15;//默认一次去15条
	public static final int DEFAULSEVERVALUE = -1;//默认参数
	public static final int DEFAULSTATUSINFO = 1;//默认 获取我正在考的试卷，
	public static final int STATUSINFO_DONE = 2;//默认 获取我正在考的试卷，
	
	public static final int END_HANDLEEXAM = 1;//提交试卷，
	public static final int END_DOINGEXAM = -1;//默认 获取我正在考的试卷，
	
	public static final int BEGIN_RESTAR = 1;// 重做
	public static final int BGEIN_CONTINUE = -1;//继续做，或者查看答案
	
	public static final int toutiao_id=1;
	public static final int yule_id=2;
	public static final int tiyu_id=3;
	public static final int caijing_id=4;
	
	public static final int appOpen=0;
	public static final int refresh=1;
	public static final int load=2;
	
	
	public static final int ANSWER_UNDONG = 0;
	public static final int ANSWER_DONG = 1;
	public static final int ANSWER_RIGHT = 2;
	public static final int ANSWER_WRONG = 3;
	
	
	public static final int CONNECTION_TIMEOUT = 30000;
	public static final int SOCKET_TIMEOUT = 30000;
	public static final int DEFAULTCHOICESCOUNT = 4;//默认选项个数
	
	public static final int DONG_PAPER = 1;
	public static final int DOING_PAPER = 2;
	public static final int FAVORITE_PAPER = 3;
	/**接口定义teststatus*/
	public static final int ITESTSTATUS_UNDO = 0;//没做
	public static final int ITESTSTATUS_DOING = 1;//正在做
	public static final int ITESTSTATUS_DONE = 2;//做过
	public static final int SHOWFAVOR = 1;
	public static final int SHOWDONEEXAM = 2;
	public static final int SHOWDOING = 3;
	
}
