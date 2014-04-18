package com.cqvip.mobilevers.entity;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PaperDetail {

		private String id;
        private String  name;
        private int  score;
        private int  type;
        private int questioncount;
        private int exampapertime;
        private String  year;
        private String updatetime;
        private boolean isFavor;
        private int teststatus;
        private int testquestionNum;
        private double testscore;
        
		public PaperDetail() {
		super();
		// TODO Auto-generated constructor stub
	    }
        
		public void setId(String id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public void setType(int type) {
		this.type = type;
	}

	public void setQuestioncount(int questioncount) {
		this.questioncount = questioncount;
	}

	public void setExampapertime(int exampapertime) {
		this.exampapertime = exampapertime;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}

	public void setFavor(boolean isFavor) {
		this.isFavor = isFavor;
	}

	public void setTeststatus(int teststatus) {
		this.teststatus = teststatus;
	}

	public void setTestquestionNum(int testquestionNum) {
		this.testquestionNum = testquestionNum;
	}

	public void setTestscore(double testscore) {
		this.testscore = testscore;
	}

	public void setTag_title(ArrayList<TagInfo> tag_title) {
		this.tag_title = tag_title;
	}
		
        public int getTeststatus() {
			return teststatus;
		}



		public int getTestquestionNum() {
			return testquestionNum;
		}



		public double getTestscore() {
			return testscore;
		}



		private ArrayList<TagInfo> tag_title;

        
        
        public String getId() {
			return id;
		}



		public String getName() {
			return name;
		}



		public int getScore() {
			return score;
		}



		public int getType() {
			return type;
		}



		public int getQuestioncount() {
			return questioncount;
		}



		public int getExampapertime() {
			return exampapertime;
		}



		public String getYear() {
			return year;
		}



		public String getUpdatetime() {
			return updatetime;
		}



		public ArrayList<TagInfo> getTag_title() {
			return tag_title;
		}



		public PaperDetail(JSONObject json)throws JSONException{
        	
        	id = json.getString("id");
        	name = json.getString("name");
        	score = json.getInt("score");
        	questioncount = json.getInt("questioncount");
        	exampapertime = json.getInt("exampapertime");
        	year = json.getString("year");
        	type = json.getInt("type");
        	updatetime = json.getString("updatetime");
        	isFavor = json.getBoolean("isfavorites");
        	teststatus = json.getInt("teststatus");
        	testquestionNum = json.getInt("testquestionNum");
        	testscore = json.getDouble("testquestionNum");
            JSONArray  array = json.getJSONArray("kclass");
            int len = array.length();
            if(len>0){
            	tag_title = new ArrayList<TagInfo>();
            	for(int i=0;i<array.length();i++){
            		 TagInfo tag = new TagInfo(array.getJSONObject(i));
            		 tag_title.add(tag);
            	}
            }
        }

		@Override
		public String toString() {
			return "PaperDetail [id=" + id + ", name=" + name + ", score="
					+ score + ", type=" + type + ", questioncount="
					+ questioncount + ", exampapertime=" + exampapertime
					+ ", year=" + year + ", updatetime=" + updatetime
					+ ", isFavor=" + isFavor + ", teststatus=" + teststatus
					+ ", testquestionNum=" + testquestionNum + ", testscore="
					+ testscore + ", tag_title=" + tag_title + "]";
		}



		public PaperDetail(String id, String name, int score, int type,
				int questioncount, int exampapertime, String year,
				String updatetime, boolean isFavor, ArrayList<TagInfo> tag_title) {
			super();
			this.id = id;
			this.name = name;
			this.score = score;
			this.type = type;
			this.questioncount = questioncount;
			this.exampapertime = exampapertime;
			this.year = year;
			this.updatetime = updatetime;
			this.isFavor = isFavor;
			this.tag_title = tag_title;
		}



		public boolean isFavor() {
			return isFavor;
		}



	


		
}
