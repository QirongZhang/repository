package edu.mju.dao;

import java.util.List;

import edu.mju.pojo.StoryBean;

public interface StoryDAO {

	public List<StoryBean> findList(String category_id);

	public StoryBean findById(String chapter_id);
	
}
