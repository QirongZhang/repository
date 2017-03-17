package edu.mju.dao;

import java.util.List;







import edu.mju.pojo.AdminBean;

public interface TbAdminDAO {

	public List<AdminBean> adminList();

	public void addAdmin(AdminBean adminBean, String admin_select_role);

	public AdminBean findAdminBeanById(String admin_id);

	public void saveAdmin(AdminBean adminBean, String admin_select_roleid);

	public void delete(String admin_id);
	
}
