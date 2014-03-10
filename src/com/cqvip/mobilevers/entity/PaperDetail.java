package com.cqvip.mobilevers.entity;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PaperDetail {

		private String id;
        private String  name;
        private int  score;
        private int questioncount;
        private int exampapertime;
        private String  year;
        private String updatetime;
        private ArrayList<TagInfo> tag_title;

        
        
        public PaperDetail(JSONObject json)throws JSONException{
        	
        	id = json.getString("id");
        	name = json.getString("name");
        	score = json.getInt("score");
        	questioncount = json.getInt("questioncount");
        	exampapertime = json.getInt("exampapertime");
        	year = json.getString("year");
        	updatetime = json.getString("updatetime");
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
					+ score + ", questioncount=" + questioncount
					+ ", exampapertime=" + exampapertime + ", year=" + year
					+ ", updatetime=" + updatetime + ", tag_title=" + tag_title
					+ "]";
		}



		public PaperDetail(String id, String name, int score,
				int questioncount, int exampapertime, String year,
				String updatetime, ArrayList<TagInfo> tag_title) {
			super();
			this.id = id;
			this.name = name;
			this.score = score;
			this.questioncount = questioncount;
			this.exampapertime = exampapertime;
			this.year = year;
			this.updatetime = updatetime;
			this.tag_title = tag_title;
		}
        
}
