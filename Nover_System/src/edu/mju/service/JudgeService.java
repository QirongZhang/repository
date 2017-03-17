package edu.mju.service;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Service;

import edu.mju.util.JdbcUtil;

@Service(value = "judgeService")
public class JudgeService extends JdbcDaoSupport {

	@Autowired
	public void setDataSourceX(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	/**
	 * 判断是否是管理员
	 * 
	 * @param admin_id
	 * @return
	 */
	public boolean isAdmin(String admin_id) {
		StringBuffer sql = new StringBuffer();
		sql.append("Select menu_id From ns_user_menu ");
		sql.append("Where menu_id in(");
		sql.append("Select menu_id From ns_user_role_menu ");
		sql.append("Where role_id In ( ");
		sql.append("Select role_id From ns_user_admin_role ");
		sql.append("Where admin_id In ( ");
		sql.append("Select admin_id From ns_user_admin ");
		sql.append("Where admin_id = " + admin_id + "))) ");

		QueryRunner runner = new QueryRunner();
		Connection conn = null;
		boolean flag = false;
		List<Map<String, Object>> menu_idList = new ArrayList<Map<String, Object>>();
		try {
			conn = JdbcUtil.getConn();
			menu_idList = (List<Map<String, Object>>) runner.query(conn,
					sql.toString(), new MapListHandler());
			for (Map<String, Object> menu_idrow : menu_idList) {
				String menu_id = (String) menu_idrow.get("menu_id");
				int id = Integer.valueOf(menu_id.substring(0, 1));
				if (id == 9) {
					flag = true;
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtils.closeQuietly(conn);
		}
		return flag;
	}

	/**
	 * 判断角色名是否相同
	 */
	public boolean isSamRoleName(String role_name) {
		StringBuffer sql = new StringBuffer();

		sql.append("Select * From ns_user_role ");
		sql.append("Where role_name = '" + role_name + "'");
		QueryRunner runner = new QueryRunner();
		Connection conn = null;
		boolean flag = false;
		List<Map<String, Object>> roleList = new ArrayList<Map<String, Object>>();
		try {
			conn = JdbcUtil.getConn();
			roleList = runner.query(conn, sql.toString(), new MapListHandler());
			if (!roleList.isEmpty() || roleList.size() != 0) {
				flag = true;
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			DbUtils.closeQuietly(conn);
		}
		return flag;
	}

	/**
	 * 判断是否是超级管理员(根据用户id)
	 * 
	 * @param user_id
	 * @return
	 */
	public boolean isSuperAdmin(String admin_id, String role_id) {
		StringBuffer sql = new StringBuffer();
		sql.append("Select * From ns_user_menu ");
		sql.append("	Where menu_id in(");
		sql.append("		Select menu_id From ns_user_role_menu ");
		if (role_id != null && !role_id.equals("") && !role_id.equals("null")) {
			sql.append("Where role_id = " + role_id + ")");
		} else {
			sql.append("		Where role_id In ( ");
			sql.append("			Select role_id From ns_user_admin_role ");
			sql.append("			Where admin_id In ( ");
			sql.append("				Select admin_id From ns_user_admin ");
			sql.append("				Where admin_id = " + admin_id + "))) ");
		}
		QueryRunner runner = new QueryRunner();
		Connection conn = null;
		boolean flag = false;
		int i = 0;
		List<Map<String, Object>> menuList = new ArrayList<Map<String, Object>>();
		try {
			conn = JdbcUtil.getConn();
			menuList = (List<Map<String, Object>>) runner.query(conn,
					sql.toString(), new MapListHandler());
			for (Map<String, Object> menurow : menuList) {
				String menu_id = (String) menurow.get("menu_id");
				String isleaf = (String) menurow.get("isleaf");
				int id = Integer.valueOf(menu_id.substring(0, 1));
				if (id == 9 && isleaf.equals("1")) {
					i++;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtils.closeQuietly(conn);
		}
		System.out.println("i = " + i);
		if (i == 8) {
			flag = true;
		}
		return flag;
	}

	/**
	 * 判断是否是在使用(根据角色id)
	 * 
	 * @param user_id
	 * @return
	 */
	public boolean roleIsUse(String role_id) {
		StringBuffer sql = new StringBuffer();
		sql.append("Select user_id From ns_user_admin_role ");
		sql.append("Where role_id = " + role_id + "");
		QueryRunner runner = new QueryRunner();
		Connection conn = null;
		boolean flag = false;
		List<Map<String, Object>> userList = new ArrayList<Map<String, Object>>();

		try {
			conn = JdbcUtil.getConn();
			userList = (List<Map<String, Object>>) runner.query(conn,
					sql.toString(), new MapListHandler());
			if (!userList.isEmpty() || userList.size() != 0) {
				flag = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtils.closeQuietly(conn);
		}
		return flag;
	}
}
