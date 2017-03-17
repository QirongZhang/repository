package edu.mju.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.mju.dao.ChapterDAO;
import edu.mju.dao.imp.ChapterDAOImp;
import edu.mju.pojo.ChapterBean;

@Service(value = "chapterService")
public class ChapterService {
	@Autowired
	private ChapterDAO chapterDAO;
	
	public ChapterBean findById(String chapter_id) {
		return chapterDAO.findById(chapter_id);
	}
	
	public List<ChapterBean> findList(String story_id){
		return chapterDAO.findList(story_id);
	}
}
