package edu.mju.dao;

import java.util.List;
import java.util.Map;

import edu.mju.pojo.MenuBean;


public interface TbMenuDAO {

	public void addMenu(MenuBean menuBean, String select_parent_id);

//	public void delMenu(String menu_id) ;
//	public List<Map<String, Object>> getMenu_treeList(String userid);
	public boolean isSameMenuName(String menu_id);
//	MenuBean findMenuBeanById(String menu_id);
//	public List<Map<String, Object>> findMenu();
//	void saveMenu(MenuBean menuBean, String select_parent_id);
}	
