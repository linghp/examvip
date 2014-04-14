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
					+ ", isFavor=" + isFavor + ", tag_title=" + tag_title + "]";
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
