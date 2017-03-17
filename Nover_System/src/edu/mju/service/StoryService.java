package edu.mju.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.mju.dao.CategoryDAO;
import edu.mju.dao.ChapterDAO;
import edu.mju.dao.StoryDAO;
import edu.mju.dao.imp.ChapterDAOImp;
import edu.mju.pojo.CategoryBean;
import edu.mju.pojo.ChapterBean;
import edu.mju.pojo.StoryBean;

@Service(value = "storyService")
public class StoryService {
	@Autowired
	private StoryDAO storyDAO;
	
	public StoryBean findById(String chapter_id) {
		return storyDAO.findById(chapter_id);
	}
	
	public List<StoryBean> findList(String category_id) {
		return storyDAO.findList(category_id);
	}
}
