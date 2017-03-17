package edu.mju.dao;

import java.util.List;
import java.util.Map;

import edu.mju.pojo.RoleBean;


public interface TbRoleDAO {
	public List<RoleBean> findRoleList();

	public List<Map<String, Object>> pageList(String searchSQL, String orderColumn,
			int start, int length, String orderDir);

	public String findModelCount(String searchSQL);

	public List<Map<String, Object>> findMenu_treeList(String role_id, String view_type);

	public void addRole(RoleBean roleBean, String role_select_menuid);

	public RoleBean getRoleBeanById(String role_id);

	public void updateRole(RoleBean roleBean, String role_select_menuid);

	public void deleteRole(String role_id);
}
