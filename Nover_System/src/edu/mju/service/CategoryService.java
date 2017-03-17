package edu.mju.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.mju.dao.CategoryDAO;
import edu.mju.dao.ChapterDAO;
import edu.mju.dao.imp.ChapterDAOImp;
import edu.mju.pojo.CategoryBean;
import edu.mju.pojo.ChapterBean;

@Service(value = "categoryService")
public class CategoryService {
	@Autowired
	private CategoryDAO categoryDAO;
	
	public ChapterBean findById(String category_id) {
		return categoryDAO.findById(category_id);
	}
	
	public List<CategoryBean> findList() {
		return categoryDAO.findList();
	}
}
