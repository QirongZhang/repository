package edu.mju.dao;

import java.util.List;

import edu.mju.pojo.CategoryBean;
import edu.mju.pojo.ChapterBean;

public interface CategoryDAO {

	List<CategoryBean> findList();

	ChapterBean findById(String category_id);
	
}
