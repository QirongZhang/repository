package edu.mju.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.mju.dao.TbAdminDAO;
import edu.mju.dao.imp.TbAdminDAOImp;
import edu.mju.pojo.AdminBean;

@Service(value = "adminService")
public class AdminService {
	
	@Autowired
	private TbAdminDAO tbAdminDAO;

	public List<AdminBean> adminlist() throws Exception {
		List<AdminBean> adminlist = tbAdminDAO.adminList();
		return adminlist;
	}

	public void addAdmin(AdminBean adminBean, String admin_select_role) {
		tbAdminDAO.addAdmin(adminBean, admin_select_role);
	}
	
	public AdminBean findAdminBeanById(String admin_id) {
		return tbAdminDAO.findAdminBeanById(admin_id);
	}

	public void saveAdmin(AdminBean adminBean, String admin_select_roleid) {
		tbAdminDAO.saveAdmin(adminBean, admin_select_roleid);
	}
	
	public void deleteAdmin(String admin_id) {
		tbAdminDAO.delete(admin_id);
	}
	
//
//	public boolean isAdmin(String user_id) {
//		return tbUserDAOImp.isAdmin(user_id);
//	}
//
//	public List<UserBean> queryByLike(String user_id, String user_name,
//			String section) {
//		return tbUserDAOImp.queryByLike(user_id, user_name, section);
//	}
}
