package edu.mju.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.mju.dao.TbMenuDAO;
import edu.mju.pojo.MenuBean;

@Service(value = "menuService")
public class MenuService {
	
	@Autowired
	private TbMenuDAO menuDAO ;
	
	public void addMenu(MenuBean menuBean, String select_parent_id) {
		menuDAO.addMenu(menuBean, select_parent_id);
	}
	
	public boolean isSameMenuName(String menu_id) {
		return menuDAO.isSameMenuName(menu_id);
	}
	
	
//	public List<Map<String, Object>> findMenu() {
//		List<Map<String, Object>> menuList = menuDAO.findMenu();
//		return menuList;
//	}
//	
//	public MenuBean findMenuBeanById(String menu_id) {
//		return menuDAOImp.findMenuBeanById(menu_id);
//	}
//	
//	
//	public void delMenu(String menu_id) {
//		menuDAOImp.delMenu(menu_id);
//	}
//	
//	public void saveMenu(MenuBean menuBean, String select_parent_id) {
//		menuDAOImp.saveMenu(menuBean, select_parent_id);
//	}
//	

//	
//	public boolean MenuIsUse(String menu_id) {
//		return menuDAOImp.MenuIsUse(menu_id);
//	}
}
