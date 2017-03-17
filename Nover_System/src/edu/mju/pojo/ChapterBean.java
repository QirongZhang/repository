package edu.mju.pojo;

import java.sql.Date;

public class ChapterBean {
	private String chapter_id;
	private String story_id;
	private String chapter_name;
	private String word_count;
	private Date publish_time;
	public String getChapter_id() {
		return chapter_id;
	}
	public void setChapter_id(String chapter_id) {
		this.chapter_id = chapter_id;
	}
	public String getStory_id() {
		return story_id;
	}
	public void setStory_id(String story_id) {
		this.story_id = story_id;
	}
	public String getChapter_name() {
		return chapter_name;
	}
	public void setChapter_name(String chapter_name) {
		this.chapter_name = chapter_name;
	}
	public String getWord_count() {
		return word_count;
	}
	public void setWord_count(String word_count) {
		this.word_count = word_count;
	}
	public Date getPublish_time() {
		return publish_time;
	}
	public void setPublish_time(Date publish_time) {
		this.publish_time = publish_time;
	}
	
}
