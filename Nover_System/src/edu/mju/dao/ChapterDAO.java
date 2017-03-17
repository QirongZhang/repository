package edu.mju.dao;

import java.util.List;

import edu.mju.pojo.ChapterBean;

public interface ChapterDAO {
	public void insert(Object [] objectArray);

	public ChapterBean findById(String chapter_id);

	public List<ChapterBean> findList(String story_id);
}
