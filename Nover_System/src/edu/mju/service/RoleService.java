package edu.mju.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.mju.dao.TbRoleDAO;
import edu.mju.pojo.RoleBean;

@Service(value = "roleService")
public class RoleService {

	@Autowired
	private TbRoleDAO tbRoleDAO;

	public void setTbRoleDAO(TbRoleDAO tbRoleDAO) {
		this.tbRoleDAO = tbRoleDAO;
	}

	public List<RoleBean> findRoleList() {
		List<RoleBean> roleList = tbRoleDAO.findRoleList();
		return roleList;
	}

	public List<Map<String, Object>> pageList(String searchSQL,
			String orderColumn, int start, int length, String orderDir) {
		return tbRoleDAO.pageList(searchSQL, orderColumn, start, length,
				orderDir);
	}

	public String findModelCount(String searchSQL) {
		return tbRoleDAO.findModelCount(searchSQL);
	};

	public List<Map<String, Object>> findMenu_TreeList(String role_id,
			String view_type) {
		return tbRoleDAO.findMenu_treeList(role_id, view_type);
	}

	public void addRole(RoleBean roleBean, String role_select_menuid) {
		tbRoleDAO.addRole(roleBean, role_select_menuid);
	}

	public RoleBean getRoleBeanById(String role_id) {
		return tbRoleDAO.getRoleBeanById(role_id);
	}

	public void updateRole(RoleBean roleBean, String role_select_menuid) {
		tbRoleDAO.updateRole(roleBean, role_select_menuid);
	}

	public void deleteRole(String role_id) {
		tbRoleDAO.deleteRole(role_id);
	}
}
